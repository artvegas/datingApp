package com.api.datingApp.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class BlindDate {

	@EmbeddedId
	BlindDateId blindDateId;

	public BlindDate() {
		super();
	}
	
	public BlindDate(BlindDateId id) {
		super();
		this.blindDateId = id;
	}

	public BlindDateId getId() {
		return blindDateId;
	}

	public void setId(BlindDateId id) {
		this.blindDateId = id;
	}
	
	
}