package com.api.datingApp.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class DateKey implements Serializable {
	@ManyToOne
	@JoinColumn(name="Profile1", referencedColumnName="ProfileId")
	private Profile profile1;
	@ManyToOne
	@JoinColumn(name="Profile2", referencedColumnName="ProfileId")
	private Profile profile2;
	@Column(name = "Date_Time")
	private Date dateTime;
	
	public DateKey() {
		
	}

	public DateKey(Profile profile1, Profile profile2, Date dateTime) {
		super();
		this.profile1 = profile1;
		this.profile2 = profile2;
		this.dateTime = dateTime;
	}
	
	public String getDateTimeString() throws ParseException {
		return this.dateTime.toString();

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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
