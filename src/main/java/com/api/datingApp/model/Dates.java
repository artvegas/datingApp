package com.api.datingApp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "date") 
public class Dates {
	@EmbeddedId
	private DateKey dateKey;
	@ManyToOne
	@JoinColumn(name="CustRep", referencedColumnName="SSN")
	private Employee employee;
	@Column(name = "BookingFee")
	private Double bookingFee;
	@Column(name = "Location")
	private String location;
	@Column(name = "Comments")
	private String comments;
	@Column(name = "User1Rating")
	private Double user1Rating;
	@Column(name = "User2Rating")
	private Double user2Rating;
	@Column(name = "status")
	private int status;
	
	public Dates() {
		
	}

	

	public Dates(DateKey dateKey, Employee employee, Double bookingFee, String location, String comments,
			Double user1Rating, Double user2Rating, int status) {
		super();
		this.dateKey = dateKey;
		this.employee = employee;
		this.bookingFee = bookingFee;
		this.location = location;
		this.comments = comments;
		this.user1Rating = user1Rating;
		this.user2Rating = user2Rating;
		this.status = status;
	}



	public DateKey getDateKey() {
		return dateKey;
	}

	public void setDateKey(DateKey dateKey) {
		this.dateKey = dateKey;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Double getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(Double bookingFee) {
		this.bookingFee = bookingFee;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getUser1Rating() {
		return user1Rating;
	}

	public void setUser1Rating(Double user1Rating) {
		this.user1Rating = user1Rating;
	}

	public Double getUser2Rating() {
		return user2Rating;
	}

	public void setUser2Rating(Double user2Rating) {
		this.user2Rating = user2Rating;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


}
