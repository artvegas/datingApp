package com.api.datingApp.model.returnTypes;

import com.api.datingApp.model.User;

public class saleReport_TotalFee_User {

	private double TotalFee;
	private User user;
	
	public saleReport_TotalFee_User(double totalFee, User user) {
		super();
		this.TotalFee = totalFee;
		this.user = user;
	}
	
	public saleReport_TotalFee_User(double totalFee) {
		super();
		this.TotalFee = totalFee;
	}
	
	public double getTotalFee() {
		return TotalFee;
	}

	public void setTotalFee(double totalFee) {
		TotalFee = totalFee;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}