package com.api.datingApp.controller;

import com.api.datingApp.model.Employee;
import com.api.datingApp.repo.EmployeeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@GetMapping(value="/all")
	@ResponseBody
	public Iterable<Employee> getAll(){
		return employeeRepo.findAll();
	}
	
}
