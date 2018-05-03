package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.api.datingApp.model.Profile;
import com.api.datingApp.model.User;

public interface ProfileRepo extends JpaRepository<Profile, String>, JpaSpecificationExecutor<Profile> {

	 public List<Profile> findByUser(User User);
	 public List<Profile> findByProfileId(String profileId);
	 
	
}