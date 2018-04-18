package com.api.datingApp.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Account {
	
	@EmbeddedId AccountId account;
	@ManyToOne
	@JoinColumn(name="ownerSSN", referencedColumnName="SSN")
	private User user;

	public Account() {
		super();
	}
	
	
	public Account(AccountId account, User user) {
		super();
		this.account = account;
		this.user = user;
	}


	public AccountId getAccount() {
		return account;
	}

	public void setAccount(AccountId account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static String generateId(String ssn, String cardNumber, String acctName) {
		String id = "";
		int num = 0;
		for(int i = 0; i < cardNumber.length(); i++) {
			num += (cardNumber.charAt(i) * acctName.charAt(i % acctName.length()) 
					* ssn.charAt(i % ssn.length()));
		}
		
		id = Integer.toString(num);
		return id;
	}
}
