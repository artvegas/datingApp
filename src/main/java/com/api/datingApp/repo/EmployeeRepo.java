package com.api.datingApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Employee;
import com.api.datingApp.model.Person;

public interface EmployeeRepo extends JpaRepository<Employee, Person> {

}