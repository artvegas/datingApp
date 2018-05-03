package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.datingApp.model.Profile;
import com.api.datingApp.model.SuggestedBy;

public interface SuggestedByRepo extends JpaRepository<SuggestedBy,Profile>{
	
	@Query("Select d from SuggestedBy d where d.suggestedById.profile1.profileId = ?1")
	public List<SuggestedBy> findbyProfileId1(String profileId);
	
	@Query("Select d from SuggestedBy d where d.suggestedById.profile2.profileId = ?1")
	public List<SuggestedBy> findbyProfileId2(String profileId);
}