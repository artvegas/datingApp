package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Employee;
import com.api.datingApp.model.LikeKey;
import com.api.datingApp.model.Likes;
import com.api.datingApp.model.Person;

public interface EmployeeRepo extends JpaRepository<Employee, Person> {
	public List<Employee> findBySsn(String ssn);

	public List<Employee> findByRole(String string);
}