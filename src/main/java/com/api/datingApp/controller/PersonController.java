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
	
	@CrossOrigin(origins="*")
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewPerson(@RequestBody Person person) {
		
		if(!personRepo.findBySsn(person.getSsn()).isEmpty()) {
			return new ServerResponse(210, "User With SSN Already Exsists");
		}else {
			if(!personRepo.findByEmail(person.getEmail()).isEmpty()) {
				return new ServerResponse(210, "User With Email Already Exsists");
			}
		}
		
		personRepo.save(person);
		return new ServerResponse(200, "OK");
	}
}
