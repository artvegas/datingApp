package com.api.datingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Account;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.repo.AccountRepo;

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
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewAccount(@RequestBody Account account) {
		accountRepo.save(account);
		return new ServerResponse(200, "OK");
	}

}
