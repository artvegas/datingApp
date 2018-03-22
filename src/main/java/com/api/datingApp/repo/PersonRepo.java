package com.api.datingApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Person;

public interface PersonRepo extends JpaRepository<Person, String> {

}