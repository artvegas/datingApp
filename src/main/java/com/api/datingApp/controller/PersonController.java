package com.api.datingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Person;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.repo.PersonRepo;
import com.api.datingApp.secruity.PasswordAuthentication;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/persons")
public class PersonController {
	@Autowired
	private PersonRepo personRepo;
	
	@GetMapping(value="/all")
	@ResponseBody 
    public Iterable<Person> getAll(){
        return personRepo.findAll();
    }
	
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewPerson(@RequestBody Person person) {
		
		if(!personRepo.findBySsn(person.getSsn()).isEmpty()) {
			return new ServerResponse(210, "User With SSN Already Exsists.");
		}else {
			if(!personRepo.findByEmail(person.getEmail()).isEmpty()) {
				return new ServerResponse(211, "User With Email Already Exsists.");
			}
		}
		
		PasswordAuthentication passwordAuth = new PasswordAuthentication();
		char[] password = person.getPassword().toCharArray();
		person.setPassword(passwordAuth.hash(password));
		
		personRepo.save(person);
		return new ServerResponse(200, "OK");
	}
	
	@PostMapping(value="/login")
	@ResponseBody
	public ServerResponse login(@RequestBody Person person) {
		
		Person personToCheck = personRepo.findByEmail(person.getEmail()).get(0);
		if(personToCheck == null) {
			return new ServerResponse(212, "User With Email Does Not Exist.");
		}else {
			PasswordAuthentication passwordAuth = new PasswordAuthentication();
			char[] password = person.getPassword().toCharArray();
			if(!passwordAuth.authenticate(password, personToCheck.getPassword())) {
				return new ServerResponse(213, "No user found with given email and password.");
			}
		}
		return new ServerResponse(200, "OK");
	}
}
