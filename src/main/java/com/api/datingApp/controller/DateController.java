package com.api.datingApp.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.DatingSpecification;
import com.api.datingApp.model.Dates;
import com.api.datingApp.model.Employee;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.SearchCriteria;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.repo.DateRepo;
import com.api.datingApp.repo.EmployeeRepo;
import com.api.datingApp.repo.ProfileRepo;

@RestController
@RequestMapping(value="/dates")
public class DateController {

	@Autowired
	DateRepo dateRepo;
	
	@Autowired
	ProfileRepo profileRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse<Dates> like(@RequestBody Dates date) {
		date.setEmployee(null);
		date.setBookingFee(0.0);
		date.setStatus(9);
		
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	@GetMapping(value="/upcoming/{profileId}")
	@ResponseBody
	public ServerResponse<Dates> upcomingDatesForProfileId(@PathVariable("profileId") String profileId) {
		List<Profile> profiles = profileRepo.findByProfileId(profileId);
		if(!profiles.isEmpty()) {
			DatingSpecification profile1Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile1", profileId));
			DatingSpecification profile2Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile2", profileId));
			Date today = new Date();
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			today.setDate(today.getDate() - 1);
			DatingSpecification endDateSpec = new DatingSpecification(
					new SearchCriteria("dateKey","date<", today));
			DatingSpecification statusSpec = new DatingSpecification(
					new SearchCriteria("status","=", 1));
			DatingSpecification statusSpec2 = new DatingSpecification(
					new SearchCriteria("status","=", 0));
			List<Dates> dates = dateRepo.findAll(Specifications.where(statusSpec).and(profile1Spec)
					.or(profile2Spec).and(endDateSpec));
			List<Dates> dates2 = dateRepo.findAll(Specifications.where(statusSpec2).and(profile1Spec)
					.or(profile2Spec).and(endDateSpec));
			dates.addAll(dates2);
			return new ServerResponse<Dates>(200, "OK", dates);
		}
		return new ServerResponse<Dates>(205, "Invalid inputs");
	}
	
	@GetMapping(value="/today/{profileId}")
	@ResponseBody
	public ServerResponse<Dates> datesTodayForProfileId(@PathVariable("profileId") String profileId) {
		List<Profile> profiles = profileRepo.findByProfileId(profileId);
		if(!profiles.isEmpty()) {
			DatingSpecification profile1Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile1", profileId));
			DatingSpecification profile2Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile2", profileId));
			DatingSpecification statusSpec = new DatingSpecification(
					new SearchCriteria("status","=", 1));
			DatingSpecification statusSpec2 = new DatingSpecification(
					new SearchCriteria("status","=", 0));
			Date today = new Date();
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			Date tomorrow = new Date();
			tomorrow.setHours(0);
			tomorrow.setMinutes(0);
			tomorrow.setSeconds(0);
			tomorrow.setDate(today.getDate() + 1);
			DatingSpecification dateTime = new DatingSpecification(
					new SearchCriteria("dateKey","date>", today));
			DatingSpecification dateTime2 = new DatingSpecification(
					new SearchCriteria("dateKey","date<", tomorrow));
			List<Dates> dates = dateRepo.findAll(Specifications.where(dateTime).and(profile1Spec)
					.or(profile2Spec).and(dateTime2)
					.and(statusSpec));

			
			return new ServerResponse<Dates>(200, "OK", dates);
		}
		return new ServerResponse<Dates>(205, "Invalid inputs");
	}
	
	
	
	
	@GetMapping(value="/past/{profileId}")
	@ResponseBody
	public ServerResponse<Dates> pastDatesForProfileId(@PathVariable("profileId") String profileId){
		List<Profile> profiles = profileRepo.findByProfileId(profileId);
		if(!profiles.isEmpty()) {
			DatingSpecification profile1Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile1", profileId));
			DatingSpecification profile2Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile2", profileId));
			Date today = new Date();
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			DatingSpecification pastSpec = new DatingSpecification(
					new SearchCriteria("dateKey","date<", today));
			DatingSpecification statusSpec = new DatingSpecification(
					new SearchCriteria("status","=", 2));
			List<Dates> dates = dateRepo.findAll(Specifications.where(pastSpec)
					.and(profile1Spec).or(profile2Spec).and(statusSpec));
			return new ServerResponse<Dates>(200, "OK", dates);
		}
		return new ServerResponse<Dates>(205, "Invalid inputs");
	}
	
	@GetMapping(value="/requests/{profileId}")
	@ResponseBody
	public ServerResponse<Dates> requestForProfileId(@PathVariable("profileId") String profileId){
		List<Profile> profiles = profileRepo.findByProfileId(profileId);
		if(!profiles.isEmpty()) {
			DatingSpecification profile1Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile2", profileId));
			DatingSpecification statusSpec = new DatingSpecification(
					new SearchCriteria("status","=", 9));
			List<Dates> dates = dateRepo.findAll(Specifications.where(profile1Spec).and(statusSpec));
			return new ServerResponse<Dates>(200, "OK", dates);
		}
		return new ServerResponse<Dates>(205, "Invalid inputs");
	}
	
	@GetMapping(value="/requests/sent/{profileId}")
	@ResponseBody
	public ServerResponse<Dates> requestSentByProfileId(@PathVariable("profileId") String profileId){
		List<Profile> profiles = profileRepo.findByProfileId(profileId);
		if(!profiles.isEmpty()) {
			DatingSpecification profile1Spec = new DatingSpecification(
					new SearchCriteria("dateKey","profile1", profileId));
			DatingSpecification statusSpec = new DatingSpecification(
					new SearchCriteria("status","=", 9));
			List<Dates> dates = dateRepo.findAll(Specifications.where(profile1Spec).and(statusSpec));
			return new ServerResponse<Dates>(200, "OK", dates);
		}
		return new ServerResponse<Dates>(205, "Invalid inputs");
	}
	
	@PostMapping(value="/accept")
	@ResponseBody
	public ServerResponse<Dates> acceptDateRequest(@RequestBody Dates date){
		date.setStatus(0);
//		List<Employee> employees = employeeRepo.findByRole("CustRep");
//		int randomEmployee = (int)(Math.random() * 1000) % employees.size();
//		date.setEmployee(employees.get(randomEmployee));
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	@PostMapping(value="/reject")
	@ResponseBody
	public ServerResponse<Dates> rejectDateRequest(@RequestBody Dates date){
		date.setStatus(-1);
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	@PostMapping(value="/complete")
	@ResponseBody
	public ServerResponse<Dates> finishDate(@RequestBody Dates date){
		Date today = new Date();
		if(date.getDateKey().getDateTime().compareTo(today) == 1) {
			return new ServerResponse<Dates>(500, "Internal Server Error");
		}
		date.setStatus(2);
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	@PostMapping(value="/cancel")
	@ResponseBody
	public ServerResponse<Dates> cancelDate(@RequestBody Dates date){
		date.setStatus(-2);
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}

	@PostMapping(value="/add/comment")
	@ResponseBody
	public ServerResponse<Dates> addComment(@RequestBody Dates date){
		Employee employee = new Employee();
		employee.setHourlyRate(date.getEmployee().getHourlyRate());
		employee.setPerson(date.getEmployee().getPerson());
		employee.setStartDate(date.getEmployee().getStartDate());
		employee.setRole(date.getEmployee().getRole());
		employee.setSsn(date.getEmployee().getSsn());
		employeeRepo.save(employee);
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	@PostMapping(value="/rate")
	@ResponseBody
	public ServerResponse<Dates> rateDate(@RequestBody Dates date){
		Employee employee = new Employee();
		employee.setHourlyRate(date.getEmployee().getHourlyRate());
		employee.setPerson(date.getEmployee().getPerson());
		employee.setStartDate(date.getEmployee().getStartDate());
		employee.setRole(date.getEmployee().getRole());
		employee.setSsn(date.getEmployee().getSsn());
		employeeRepo.save(employee);
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	
	
	
	
}
