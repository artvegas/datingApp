package com.api.datingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Account;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.repo.AccountRepo;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/accounts")
public class AccountController {
	
	@Autowired
	AccountRepo accountRepo;

	
	@GetMapping(value="/all")
	@ResponseBody
	public Iterable<Account> getAll(){
		return accountRepo.findAll();
	}
	
	@GetMapping(value="/")
	@ResponseBody
	public ServerResponse getPerson(@RequestBody String ssn) {
		User user = new User();
		user.setSsn(ssn);
		Account account = accountRepo.findByUser(user).get(0);
		if(account == null) {
			return new ServerResponse(212, "User With Ssn Does Not Exist.");
		}
		
		return new ServerResponse(200, "OK", account);
	}
	
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewAccount(@RequestBody Account account) {
		accountRepo.save(account);
		return new ServerResponse(200, "OK");
	}

}
