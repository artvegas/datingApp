package com.api.datingApp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Profile {
	@Id
	@Column(name = "ProfileId")
	private String profileId;
	@ManyToOne
	@JoinColumn(name="OwnerSSN", referencedColumnName="SSN")
	private User user;
	@Column(name = "DatingAgeRangeStart")
	private int datingAgeRangeStart;
	@Column(name = "DatingAgeRangeEnd")
	private int datingAgeRangeEnd;
	@Column(name = "DatinGeoRange")
	private int datingGeoRange;
	@Column(name = "M_F")
	private String m_f;
	@Column(name = "Age")
	private String age;
	@Column(name = "Hobbies")
	private String hobbies;
	@Column(name = "Height")
	private double height;
	@Column(name = "Weight")
	private double weight;
	@Column(name = "HairColor")
	private String hairColor;
	@Column(name = "CreationDate")
	private Date creationDate;
	@Column(name = "LastModDate")
	private Date lastModDate;
	@Column(name = "ProfileName")
	private String profileName;
	
	public Profile() {
		
	}

	public Profile(String profileId, User user, int datingAgeRangeStart, int datingAgeRangeEnd, int datingGeoRange,
			String m_f, String age, String hobbies, double height, double weight, String hairColor, Date creationDate,
			Date lastModDate, String profileName) {
		super();
		this.profileId = profileId;
		this.user = user;
		this.datingAgeRangeStart = datingAgeRangeStart;
		this.datingAgeRangeEnd = datingAgeRangeEnd;
		this.datingGeoRange = datingGeoRange;
		this.m_f = m_f;
		this.age = age;
		this.hobbies = hobbies;
		this.height = height;
		this.weight = weight;
		this.hairColor = hairColor;
		this.creationDate = creationDate;
		this.lastModDate = lastModDate;
		this.profileName = profileName;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getDatingAgeRangeStart() {
		return datingAgeRangeStart;
	}

	public void setDatingAgeRangeStart(int datingAgeRangeStart) {
		this.datingAgeRangeStart = datingAgeRangeStart;
	}

	public int getDatingAgeRangeEnd() {
		return datingAgeRangeEnd;
	}

	public void setDatingAgeRangeEnd(int datingAgeRangeEnd) {
		this.datingAgeRangeEnd = datingAgeRangeEnd;
	}

	public int getDatingGeoRange() {
		return datingGeoRange;
	}

	public void setDatingGeoRange(int datingGeoRange) {
		this.datingGeoRange = datingGeoRange;
	}

	public String getM_f() {
		return m_f;
	}

	public void setM_f(String m_f) {
		this.m_f = m_f;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public String[] getHobbiesList() {
		String[] listOfHobbies = this.hobbies.split(",");
		return listOfHobbies;
		
	}
	
}
