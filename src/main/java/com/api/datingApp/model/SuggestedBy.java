package com.api.datingApp.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class SuggestedBy {

	
	@EmbeddedId
	SuggestedById suggestedById;

	
	public SuggestedBy(SuggestedById id) {
		super();
		this.suggestedById = id;
	}

	public SuggestedBy() {
		super();
	}

	public SuggestedById getId() {
		return suggestedById;
	}

	public void setId(SuggestedById id) {
		this.suggestedById = id;
	}
	
	
	
}