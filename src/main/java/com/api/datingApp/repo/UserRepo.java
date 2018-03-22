package com.api.datingApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Person;
import com.api.datingApp.model.User;

public interface UserRepo extends JpaRepository<User, Person> {

}