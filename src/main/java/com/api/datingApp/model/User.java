package com.api.datingApp.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class User{
	@Id
	@Column(name = "SSN")
	private String ssn;
	@OneToOne
	@PrimaryKeyJoinColumn(name="SSN", referencedColumnName="SSN")
	private Person person;
	@Column(name = "PPP")
	private String PPP;
	@Column(name = "Rating")
	private int rating;
	@Column(name = "DateOfLastAct")
	private Date dateOfLastAct;
	
	public User() {
		
	}
	
	public User(Person person, String pPP, int rating, Date dateOfLastAct) {
		super();
		this.person = person;
		PPP = pPP;
		this.rating = rating;
		this.dateOfLastAct = dateOfLastAct;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getPPP() {
		return PPP;
	}

	public void setPPP(String pPP) {
		PPP = pPP;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getDateOfLastAct() {
		return dateOfLastAct;
	}

	public void setDateOfLastAct(Date dateOfLastAct) {
		this.dateOfLastAct = dateOfLastAct;
	}

	@Override
	public String toString() {
		return "User [ssn=" + ssn + ", person=" + person + ", PPP=" + PPP + ", rating=" + rating + ", dateOfLastAct="
				+ dateOfLastAct + "]";
	}
	
	
}
