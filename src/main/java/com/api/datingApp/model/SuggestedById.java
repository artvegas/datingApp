package com.api.datingApp.model;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class SuggestedById implements Serializable {
	
	@ManyToOne
    @JoinColumn(name="CustRep",referencedColumnName="SSN")
	Employee CustRep;
	
	@ManyToOne
	@JoinColumn(name="Profile1" , referencedColumnName="ProfileID")
	Profile profile1;
	
	@ManyToOne
	@JoinColumn(name="Profile2" , referencedColumnName="ProfileID")
	Profile profile2;
	
	@Column(name="Date_Time")
	Timestamp SuggestedBy_Time;
	
	
	public SuggestedById() {
		super();
	}

	public SuggestedById(Employee custRep, Profile profile1, Profile profile2, Timestamp suggestedBy_Time) {
		super();
		CustRep = custRep;
		this.profile1 = profile1;
		this.profile2 = profile2;
		SuggestedBy_Time = suggestedBy_Time;
	}

	public Employee getCustRep() {
		return CustRep;
	}

	public void setCustRep(Employee custRep) {
		CustRep = custRep;
	}

	public Profile getProfile1() {
		return profile1;
	}

	public void setProfile1(Profile profile1) {
		this.profile1 = profile1;
	}

	public Profile getProfile2() {
		return profile2;
	}

	public void setProfile2(Profile profile2) {
		this.profile2 = profile2;
	}

	public Timestamp getSuggestedBy_Time() {
		return SuggestedBy_Time;
	}

	public void setSuggestedBy_Time(Timestamp suggestedBy_Time) {
		SuggestedBy_Time = suggestedBy_Time;
	}

}