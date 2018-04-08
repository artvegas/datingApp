package com.api.datingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.repo.UserRepo;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping(value="/all")
	@ResponseBody 
    public Iterable<User> getAll(){
        return userRepo.findAll();
    }
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewUser(@RequestBody User user) {
		user.setSsn(user.getPerson().getSsn());
		userRepo.save(user);
		return new ServerResponse(200, "OK");
	}
}
