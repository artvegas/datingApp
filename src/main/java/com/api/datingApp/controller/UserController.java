package com.api.datingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Employee;
import com.api.datingApp.model.Person;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.model.returnTypes.customer_mailing_list;
import com.api.datingApp.repo.PersonRepo;
import com.api.datingApp.repo.UserRepo;
import com.api.datingApp.secruity.PasswordAuthentication;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PersonRepo personRepo;
	
	@GetMapping(value="/all")
	@ResponseBody 
    public ServerResponse<User> getAll(){
        return new ServerResponse<User>(200, "OK", userRepo.findAll());
    }
	
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewUser(@RequestBody User user) {
		if(!userRepo.findBySsn(user.getSsn()).isEmpty()) {
			return new ServerResponse(204,"There is an employee with the given ssn in the system.");
		}
		
		
		if(!this.personRepo.findBySsn(user.getSsn()).isEmpty()) {
			//this should not happen, every person in database should be a user or a employee at any moment
			return new ServerResponse(500,"server side error");
		}
		
		if(!this.personRepo.findByEmail(user.getPerson().getEmail()).isEmpty()) {
			return new ServerResponse(204,"There is already an account with given email.");
		}
		PasswordAuthentication passwordAuth = new PasswordAuthentication();
		Person saved= user.getPerson();
		saved.setPassword(passwordAuth.hash(saved.getPassword().toCharArray()));
		user.setDateOfLastAct(new java.sql.Date(new java.util.Date().getTime()));
		user.setPerson(saved);
		user.setPPP("User-User");
		personRepo.save(saved);
		userRepo.save(user);
		return new  ServerResponse(200,"ok");
	}
	
	@PostMapping(value="/update")
	@ResponseBody
	public ServerResponse<User> updateUser(@RequestBody User user) {
		
		List<User> users = this.userRepo.findBySsn(user.getSsn());
		if(users.isEmpty()) {
			return new ServerResponse<User>(204,"This user does not exist in database");
		}
		
		User userRt = users.get(0);
		if(!this.personRepo.findByEmail(user.getPerson().getEmail()).isEmpty() && (user.getPerson().getEmail().compareTo(userRt.getPerson().getEmail())!=0)) {
			System.out.println(userRt.getPerson().getEmail());
			System.out.println(user.getPerson().getEmail());
			//  such new email exist already
			return new ServerResponse<User>(203,"New email already in use.");
		}
		Person person = user.getPerson();
		if(!userRt.getPerson().getPassword().equals(user.getPerson().getPassword())) {
			PasswordAuthentication passwordAuth = new PasswordAuthentication();
			person.setPassword(passwordAuth.hash(person.getPassword().toCharArray()));
		}
		
		this.personRepo.save(person);
		
		userRt.setPerson(person);
		this.userRepo.save(user);
		return new  ServerResponse<User>(200,"ok",this.userRepo.findBySsn(userRt.getSsn()));
	}
	
	@PostMapping(value="/delete")
	@ResponseBody
	public ServerResponse<Employee> deleteUser(@RequestBody User user) {
		String ssn = user.getSsn();
		List<Person> persons = personRepo.findBySsn(ssn);
		if(persons.isEmpty()) {
			return new ServerResponse<Employee>(204,"An user with this ssn does not exist in databases");
		}
		Person person = persons.get(0);
		
		if(userRepo.findBySsn(ssn).isEmpty()) {
			return new ServerResponse<Employee>(204,"An user with this ssn does not belong to a employee");
		}
		personRepo.delete(user.getPerson());
		return new  ServerResponse<Employee>(200,"ok");
	}
	
	@GetMapping(value="/mailingList")
	@ResponseBody
	public ServerResponse<customer_mailing_list> mailingList() {
		return new ServerResponse<customer_mailing_list>(200,"ok",this.userRepo.customerMailingList());
	}
	

}


