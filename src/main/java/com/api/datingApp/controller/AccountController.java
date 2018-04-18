package com.api.datingApp.controller;

import java.util.Calendar;
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
	public ServerResponse<Account> getAll(){
		try {
			List<Account> accounts = accountRepo.findAll();
			return new ServerResponse<Account>(200, "OK",accounts);
		}catch(Exception e) {
			return new ServerResponse<Account>(500, e.getMessage());
		}
		
	}
	
	@GetMapping(value="/{ssn}")
	@ResponseBody
	public ServerResponse<Account> getAccounts(@PathVariable("ssn") String ssn) {
		User user = new User();
		user.setSsn(ssn);
		List<Account> accounts = accountRepo.findByUser(user);
		if(accounts.isEmpty()) {
			return new ServerResponse<Account>(212, "User With Ssn Does Not Exist.");
		}
		
		return new ServerResponse<Account>(200, "OK", accounts);
	}
	
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse<Account> addNewAccount(@RequestBody Account account) {
//		try {
//			accountRepo.save(account);
//			return new ServerResponse<Account>(200, "OK", account);
//		}catch(Exception e){
//			return new ServerResponse<Account>(500, e.getMessage());
//		}
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		account.getAccount().setAcctCreationDate(date);
		account.getAccount().setAcctNum(Account.generateId(account.getUser().getSsn(), account.getAccount().getCardNumber(), account.getAccount().getAcctName()));
		accountRepo.save(account);
		return new ServerResponse<Account>(200, "OK");
	}

}
