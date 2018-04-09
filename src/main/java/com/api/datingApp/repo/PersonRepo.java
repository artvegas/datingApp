package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Person;

public interface PersonRepo extends JpaRepository<Person, String> {

	 public List<Person> findBySsn(String ssn);
	 
	 public List<Person> findByEmail(String email);
	
}