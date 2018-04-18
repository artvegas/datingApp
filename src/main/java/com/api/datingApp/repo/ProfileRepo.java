package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Profile;
import com.api.datingApp.model.User;

public interface ProfileRepo extends JpaRepository<Profile, String> {

	 public List<Profile> findByUser(User User);
	 
	
}