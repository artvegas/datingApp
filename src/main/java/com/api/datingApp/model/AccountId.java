package com.api.datingApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class AccountId implements Serializable{

	@Column(name = "CardNumber")
	private String cardNumber;
	@Column(name = "AcctNum")
	private String acctNum;
	@Column(name = "AcctName")
	private String acctName;
	@Column(name = "AcctCreationDate")
	private Date acctCreationDate;
	
	
	public Date getAcctCreationDate() {
		return acctCreationDate;
	}
	public void setAcctCreationDate(Date acctCreationDate) {
		this.acctCreationDate = acctCreationDate;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}
	
	
}
