package com.api.datingApp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Likes {
	
	@EmbeddedId LikeKey likeKey;
	@Column(name = "Date_Time")
	private Date date_time;
	
	public Likes() {
		
	}

	public Likes(LikeKey likeKey, Date date_time) {
		super();
		this.likeKey = likeKey;
		this.date_time = date_time;
	}

	public LikeKey getLikeKey() {
		return likeKey;
	}

	public void setLikeKey(LikeKey likeKey) {
		this.likeKey = likeKey;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	
	
	
	
}
