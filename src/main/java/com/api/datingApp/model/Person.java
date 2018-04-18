package com.api.datingApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity 
public class Person {
	@Id
	@Column(name = "SSN")
	private String ssn;
	@Column(name = "Password")
	private String password;
	@Column(name = "FirstName")
	private String firstName;
	@Column(name = "LastName")
	private String lastName;
	@Column(name = "Street")
	private String street;
	@Column(name = "City")
	private String city;
	@Column(name = "State")
	private String state;
	@Column(name = "Zipcode")
	private int zipcode;
	@Column(name = "Email")
	private String email;
	@Column(name = "Telephone")
	private String telephone;

	public Person() {
		
	}
	
	public Person(String ssn, String password, String firstName, String lastName, String street, String city, String state,
			int zipcode, String email, String telephone) {
		super();
		this.ssn = ssn;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.email = email;
		this.telephone = telephone;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "Person [ssn=" + ssn + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + ", email=" + email + ", telephone="
				+ telephone + "]";
	}
	
	public static String formatSSN(String ssn) {
		if(ssn.length() != 9) {
			return "";
		}else {
			String formattedSSN = "";
			for(int i = 0; i < 9; i++) {
				if( i == 4 || i == 7) {
					formattedSSN += '-';
				}else {
					formattedSSN += ssn.charAt(i);
				}
			}
			return formattedSSN;
		}
	}
	
}
