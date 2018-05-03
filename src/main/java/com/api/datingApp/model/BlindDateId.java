package com.api.datingApp.model;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class BlindDateId implements Serializable {

	

	@ManyToOne
	@JoinColumn(name="ProfileA", referencedColumnName="ProfileID")
	Profile profileA;
	
	@ManyToOne
	@JoinColumn(name="ProfileB", referencedColumnName="ProfileID")
	Profile profileB;
	
	@ManyToOne
	@JoinColumn(name="ProfileC", referencedColumnName="ProfileID")
	Profile profileC;
	
	@Column(name="Date_Time")
	Timestamp BlindDate_Time;

	
	
	public BlindDateId(Profile profileA, Profile profileB, Profile profileC, Timestamp blindDate_Time) {
		super();
		this.profileA = profileA;
		this.profileB = profileB;
		this.profileC = profileC;
		BlindDate_Time = blindDate_Time;
	}

	public BlindDateId() {
		super();
	}
	
	public Profile getProfileA() {
		return profileA;
	}

	public void setProfileA(Profile profileA) {
		this.profileA = profileA;
	}

	public Profile getProfileB() {
		return profileB;
	}

	public void setProfileB(Profile profileB) {
		this.profileB = profileB;
	}

	public Profile getProfileC() {
		return profileC;
	}

	public void setProfileC(Profile profileC) {
		this.profileC = profileC;
	}

	public Timestamp getBlindDate_Time() {
		return BlindDate_Time;
	}

	public void setBlindDate_Time(Timestamp blindDate_Time) {
		BlindDate_Time = blindDate_Time;
	}


	
	
}