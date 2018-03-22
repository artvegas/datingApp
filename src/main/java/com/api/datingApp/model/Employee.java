package com.api.datingApp.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Employee{
	
	@Id
	@Column(name = "SSN")
	private String ssn;
	@OneToOne
	@PrimaryKeyJoinColumn(name="SSN", referencedColumnName="SSN")
	private Person person;
	@Column(name = "role")
	private String role;
	@Column(name = "StartDate")
	private Date startDate;
	@Column(name = "HourlyRate")
	private long hourlyRate;
	
	public Employee() {
		
	}

	public Employee(Person person, String role, Date startDate, long hourlyRate) {
		super();
		this.person = person;
		this.role = role;
		this.startDate = startDate;
		this.hourlyRate = hourlyRate;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public long getHourlyRate() {
		return hourlyRate;
	}


	public void setHourlyRate(long hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
}
