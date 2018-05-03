package com.api.datingApp.model.returnTypes;

import java.util.Date;

public class report {

	private  Date time;
	private String location;
	private double rating;
	
	
	public report(Date time, String location, double rating) {
		super();
		this.time = time;
		this.location = location;
		this.rating = rating;
	}
	
	public report(Date time, String location) {
		super();
		this.time = time;
		this.location = location;
	}
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	
	
	
	
	
}