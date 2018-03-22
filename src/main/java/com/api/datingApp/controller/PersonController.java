package com.api.datingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Person;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.repo.PersonRepo;

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
		personRepo.save(person);
		return new ServerResponse(200, "OK");
	}
}