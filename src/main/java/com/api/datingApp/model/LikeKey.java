package com.api.datingApp.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class LikeKey implements Serializable {
	@ManyToOne
	@JoinColumn(name="Liker", referencedColumnName="ProfileId")
	private Profile liker;
	@ManyToOne
	@JoinColumn(name="Likee", referencedColumnName="ProfileId")
	private Profile likee;
	
	public LikeKey() {
		
	}
	
	public LikeKey(Profile liker, Profile likee) {
		super();
		this.liker = liker;
		this.likee = likee;
	}



	public Profile getLiker() {
		return liker;
	}
	public void setLiker(Profile liker) {
		this.liker = liker;
	}
	public Profile getLikee() {
		return likee;
	}
	public void setLikee(Profile likee) {
		this.likee = likee;
	}
	
	
}
