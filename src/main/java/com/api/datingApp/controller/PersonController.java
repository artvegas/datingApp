package com.api.datingApp.controller;

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

import com.api.datingApp.model.Person;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
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
		return new ServerResponse<Person>(200, "OK", persons);
	}
}
