package com.api.datingApp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Employee;
import com.api.datingApp.model.Person;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.repo.EmployeeRepo;
import com.api.datingApp.repo.PersonRepo;
import com.api.datingApp.repo.UserRepo;
import com.api.datingApp.secruity.PasswordAuthentication;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/persons")
public class PersonController {
	@Autowired
	private PersonRepo personRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@GetMapping(value="/all")
	@ResponseBody 
    public ServerResponse<Person> getAll(){
		try {
			List<Person> persons = personRepo.findAll();
			return new ServerResponse<Person>(200, "OK",persons);
		}catch(Exception e) {
			return new ServerResponse<Person>(500, e.getMessage());
		}
    }
	
	
	@GetMapping(value="/{ssn}")
	@ResponseBody
	public ServerResponse<Person> getPerson(@PathVariable("ssn") String ssn) {
		List<Person> persons = personRepo.findByEmail(ssn);
		if(persons.isEmpty()) {
			return new ServerResponse<Person>(212, "User With SSN Does Not Exist.");
		}
		
		return new ServerResponse<Person>(200, "OK", persons);
	}
	
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse<Person> addNewPerson(@RequestBody Person person) {
		
		if(!personRepo.findBySsn(person.getSsn()).isEmpty()) {
			return new ServerResponse<Person>(210, "User With SSN Already Exsists.");
		}else {
			if(!personRepo.findByEmail(person.getEmail()).isEmpty()) {
				return new ServerResponse<Person>(211, "User With Email Already Exsists.");
			}
		}
		
		PasswordAuthentication passwordAuth = new PasswordAuthentication();
		char[] password = person.getPassword().toCharArray();
		person.setPassword(passwordAuth.hash(password));
		
		User user = new User();
		user.setSsn(person.getSsn());
		user.setPerson(person);
		user.setDateOfLastAct(null);
		user.setRating(-1);
		user.setPPP("User-user");
		
		personRepo.save(person);
		userRepo.save(user);
		return new ServerResponse<Person>(200, "OK");
	}
	
	@PostMapping(value="/updatePassword/{ssn}/{password}")
	@ResponseBody
	public ServerResponse<Person> updatePassword(@PathVariable("ssn") String ssn, @PathVariable("password") String pass) {
		
		List<Person> persons = personRepo.findBySsn(ssn);
		if(persons.isEmpty()) {
			return new ServerResponse<Person>(500, "error");
		}
		Person update = persons.get(0);
		
		PasswordAuthentication passwordAuth = new PasswordAuthentication();
		char[] password = pass.toCharArray();
		update.setPassword(passwordAuth.hash(password));
		
		User user = new User();
		user.setSsn(update.getSsn());
		user.setPerson(update);
		user.setDateOfLastAct(null);

		personRepo.save(update);
		userRepo.save(user);
		return new ServerResponse<Person>(200, "OK");
	}
	
	@PostMapping(value="/login")
	@ResponseBody
	public ServerResponse<Person> login(@RequestBody Person person) {
		
		List<Person> persons = personRepo.findByEmail(person.getEmail());
		if(persons.isEmpty()) {
			return new ServerResponse<Person>(212, "User With Email Does Not Exist.");
		}else {
			Person personToCheck = persons.get(0);
			PasswordAuthentication passwordAuth = new PasswordAuthentication();
			char[] password = person.getPassword().toCharArray();
			if(!passwordAuth.authenticate(password, personToCheck.getPassword())) {
				return new ServerResponse<Person>(213, "No user found with given email and password.");
			}
		}
		List<User> users = userRepo.findByPerson(persons.get(0));
		User user = users.get(0);
		user.setDateOfLastAct(new Date());
		//personRepo.save(persons.get(0));
		//user.setPerson(persons.get(0));
		userRepo.save(user);
		
		return new ServerResponse<Person>(200, "OK", persons);
	}
	
	@PostMapping(value="/login/manager")
	@ResponseBody
	public ServerResponse<Person> loginManager(@RequestBody Person person) {
		
		List<Person> persons = personRepo.findByEmail(person.getEmail());
		if(persons.isEmpty()) {
			return new ServerResponse<Person>(212, "User With Email Does Not Exist.");
		}else {
			List<Employee> employees = employeeRepo.findBySsn(persons.get(0).getSsn());
			if(employees.isEmpty()) {
				return new ServerResponse<Person>(215, "User With Email is not an employee.");
			}
			if(!employees.get(0).getRole().equals("Manager")) {
				return new ServerResponse<Person>(215, "User With Email is not a manager.");
			}
			Person personToCheck = persons.get(0);
			PasswordAuthentication passwordAuth = new PasswordAuthentication();
			char[] password = person.getPassword().toCharArray();
			if(!passwordAuth.authenticate(password, personToCheck.getPassword())) {
				return new ServerResponse<Person>(213, "No user found with given email and password.");
			}
		}
		return new ServerResponse<Person>(200, "OK", persons);
	}
	
	@PostMapping(value="/login/custRep")
	@ResponseBody
	public ServerResponse<Person> loginCustRep(@RequestBody Person person) {
		
		List<Person> persons = personRepo.findByEmail(person.getEmail());
		if(persons.isEmpty()) {
			return new ServerResponse<Person>(212, "User With Email Does Not Exist.");
		}else {
			List<Employee> employees = employeeRepo.findBySsn(persons.get(0).getSsn());
			if(employees.isEmpty()) {
				return new ServerResponse<Person>(215, "User With Email is not a manager.");
			}
			if(!employees.get(0).getRole().equals("CustRep")) {
				return new ServerResponse<Person>(215, "User With Email is not a customer representative.");
			}
			Person personToCheck = persons.get(0);
			PasswordAuthentication passwordAuth = new PasswordAuthentication();
			char[] password = person.getPassword().toCharArray();
			if(!passwordAuth.authenticate(password, personToCheck.getPassword())) {
				return new ServerResponse<Person>(213, "No user found with given email and password.");
			}
		}
		return new ServerResponse<Person>(200, "OK", persons);
	}
}
