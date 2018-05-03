package com.api.datingApp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Dates;
import com.api.datingApp.model.Employee;
import com.api.datingApp.model.Person;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.model.returnTypes.report;
import com.api.datingApp.model.returnTypes.saleReport_TotalFee_CustRep;
import com.api.datingApp.model.returnTypes.saleReport_TotalFee_User;
import com.api.datingApp.repo.BlindDateRepo;
import com.api.datingApp.repo.DateRepo;
import com.api.datingApp.repo.EmployeeRepo;
import com.api.datingApp.repo.LikeRepo;
import com.api.datingApp.repo.PersonRepo;
import com.api.datingApp.repo.ProfileRepo;
import com.api.datingApp.repo.SuggestedByRepo;
import com.api.datingApp.repo.UserRepo;
import com.api.datingApp.secruity.PasswordAuthentication;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/employees")
public class EmployeeController {
	
	
	@Autowired
	PersonRepo personRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	ProfileRepo profileRepo;
	
	@Autowired
	DateRepo dateRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	LikeRepo likesRepo;
	
	@Autowired
	SuggestedByRepo suggestedByRepo;
	
	@Autowired
	BlindDateRepo blindDateRepo;

	
	@GetMapping(value="/all")
	@ResponseBody
	public ServerResponse<Employee> getAllEmployees(){
        return new ServerResponse<Employee>(200,"ok",employeeRepo.findAll());
    }
	

	
	
	

	

	
	
	/*
	 * when try to add a employee:
	 * case1:from nothing 
	 * case2:from existing person 
	 * case3:from existing person and user
	 * case4:from existing person and employee
	 * case5:from existing person and employee and user
	 * 
	 * front-end should handle all the incorrect info of input employee
	 * server will assume that all the info of input employee will be correct and can be save into database
	 * 
	 * status: checked
	 * */
	@PostMapping(value="/add/custRep")
	@ResponseBody
	public ServerResponse addCR(@RequestBody Employee employee) {
		if(!employeeRepo.findBySsn(employee.getSsn()).isEmpty()) {
			return new ServerResponse(204,"There is an employee with the given ssn in the system.");
		}
		
		if(!userRepo.findBySsn(employee.getSsn()).isEmpty()) {
			return new ServerResponse(204,"There is an user with given ssn in the system.");
		}
		
		if(!this.personRepo.findBySsn(employee.getSsn()).isEmpty()) {
			//this should not happen, every person in database should be a user or a employee at any moment
			return new ServerResponse(500,"server side error");
		}
		
		if(!this.personRepo.findByEmail(employee.getPerson().getEmail()).isEmpty()) {
			return new ServerResponse(204,"There is already an account with given email.");
		}
		PasswordAuthentication passwordAuth = new PasswordAuthentication();
		Person saved= employee.getPerson();
		saved.setPassword(passwordAuth.hash(saved.getPassword().toCharArray()));
		employee.setStartDate(new java.sql.Date(new java.util.Date().getTime()));
		employee.setPerson(saved);
		employee.setRole("CustRep");
		System.out.println(saved);
		System.out.println(employee);
		personRepo.save(saved);
		employeeRepo.save(employee);
		return new  ServerResponse(200,"ok");
	}
	
	@PostMapping(value="/add/manager")
	@ResponseBody
	public ServerResponse addManager(@RequestBody Employee employee) {
		if(!employeeRepo.findBySsn(employee.getSsn()).isEmpty()) {
			return new ServerResponse(204,"There is an employee with the given ssn in the system.");
		}
		
		if(!userRepo.findBySsn(employee.getSsn()).isEmpty()) {
			return new ServerResponse(204,"There is an user with given ssn in the system.");
		}
		
		if(!this.personRepo.findBySsn(employee.getSsn()).isEmpty()) {
			//this should not happen, every person in database should be a user or a employee at any moment
			return new ServerResponse(500,"server side error");
		}
		
		if(!this.personRepo.findByEmail(employee.getPerson().getEmail()).isEmpty()) {
			return new ServerResponse(204,"There is already an account with given email.");
		}
		PasswordAuthentication passwordAuth = new PasswordAuthentication();
		Person saved= employee.getPerson();
		saved.setPassword(passwordAuth.hash(saved.getPassword().toCharArray()));
		employee.setStartDate(new java.sql.Date(new java.util.Date().getTime()));
		employee.setPerson(saved);
		employee.setRole("Manager");
		System.out.println(saved);
		System.out.println(employee);
		personRepo.save(saved);
		employeeRepo.save(employee);
		return new  ServerResponse(200,"ok");
	}
	
	/*
	 * info: delete employee base on input ssn
	 * 
	 * status: checked
	 * */
	@PostMapping(value="/delete")
	@ResponseBody
	public ServerResponse<Employee> deleteCR(@RequestBody Employee employee) {
		String ssn = employee.getSsn();
		List<Person> persons = personRepo.findBySsn(ssn);
		if(persons.isEmpty()) {
			return new ServerResponse<Employee>(204,"An employee with this ssn does not exist in databases");
		}
		Person person = persons.get(0);
		
		if(employeeRepo.findBySsn(ssn).isEmpty()) {
			return new ServerResponse<Employee>(204,"An employee with this ssn does not belong to a employee");
		}
		personRepo.delete(employee.getPerson());
		return new  ServerResponse<Employee>(200,"ok");
	}
	
	
	/*
	 * such employee should exist in database, if not, then error
	 * 
	 * the employee should not modify the ssn
	 * if the new email does not exist in database then the employee can modify it, otherwise error
	 * 
	 * input employee should have correct info , should not have case such like employee.person.ssn != employee.ssn etc.
	 * 
	 * status: checked
	 * */
	@PostMapping(value="/update")
	@ResponseBody
	public ServerResponse updateCR(@RequestBody Employee employee) {
		
		List<Employee> employees = this.employeeRepo.findBySsn(employee.getSsn());
		if(employees.isEmpty()) {
			return new ServerResponse(204,"such employee does not exist in database");
		}
		
		Employee employeeRT = employees.get(0);
		if(!this.personRepo.findByEmail(employee.getPerson().getEmail()).isEmpty() && (employee.getPerson().getEmail().compareTo(employeeRT.getPerson().getEmail())!=0)) {
			System.out.println(employeeRT.getPerson().getEmail());
			System.out.println(employee.getPerson().getEmail());
			//  such new email exist already
			return new ServerResponse(203,"New email already in use.");
		}
		Person person = employee.getPerson();
		if(!employeeRT.getPerson().getPassword().equals(employee.getPerson().getPassword())) {
			PasswordAuthentication passwordAuth = new PasswordAuthentication();
			person.setPassword(passwordAuth.hash(person.getPassword().toCharArray()));
		}
		
		this.personRepo.save(person);
		
		employee.setPerson(person);
		this.employeeRepo.save(employee);
		return new  ServerResponse<Employee>(200,"ok",this.employeeRepo.findBySsn(employee.getSsn()));
	}
	
	
	/*
	 * this should return a report 
	 * 
	 * what kind of report do you need
	 * 
	 * status: done
	 * */
	@GetMapping(value="/saleReport/{year}/{month}")
	@ResponseBody
	public ServerResponse<saleReport_TotalFee_CustRep> findByMonth(@PathVariable("month") String tempMonth,@PathVariable("year") String tempYear){
		int month = Integer.parseInt(tempMonth);
		int year = Integer.parseInt(tempYear);
        java.sql.Timestamp thedate1 = new java.sql.Timestamp(year-1900,month-1,1,00,00,00,00);
        java.sql.Timestamp thedate2 = new java.sql.Timestamp(year-1900,month-1+1,1,00,00,00,00);
		return new ServerResponse<saleReport_TotalFee_CustRep>(200,"ok",dateRepo.generateReportByCalendarDate(thedate1,thedate2));	
	}
	
	@GetMapping(value="/users/all")
	@ResponseBody
	public ServerResponse<User> findAllUsers(){
		List<User> users = userRepo.findAll();
		if(users.isEmpty()) {
			return new ServerResponse<User>(500, "Internal Server Error");
		}
		
		return new ServerResponse<User>(200, "OK", users);
	}
	
	/*
	 * refer to SM4-1
	 * 
	 * return list of date base on calendar date
	 * 
	 * status: done
	 * */
	@GetMapping(value="/dates/{bgyear}/{bgmonth}/{bgday}/{edyear}/{edmonth}/{edday}")
	@ResponseBody
	public ServerResponse<Dates> findDateByCalendarDate(@PathVariable("bgyear") int year,@PathVariable("bgmonth") int month,@PathVariable("bgday") int day,@PathVariable("edyear") int edyear,@PathVariable("edmonth") int edmonth,@PathVariable("edday") int edday) {
        java.sql.Timestamp thedate1 = new java.sql.Timestamp(year-1900,month-1,day,00,00,00,00);
        java.sql.Timestamp thedate2 = new java.sql.Timestamp(edyear-1900,edmonth-1,edday,00,00,00,00);
		return new ServerResponse<Dates>(200,"ok",dateRepo.findByCalendarDate(thedate1,thedate2));
	}
	
	@GetMapping(value="/dates/report/{bgyear}/{bgmonth}/{bgday}/{edyear}/{edmonth}/{edday}")
	@ResponseBody
	public ServerResponse<saleReport_TotalFee_User> revenueByDate(@PathVariable("bgyear") int year,@PathVariable("bgmonth") int month,@PathVariable("bgday") int day,@PathVariable("edyear") int edyear,@PathVariable("edmonth") int edmonth,@PathVariable("edday") int edday) {
        java.sql.Timestamp thedate1 = new java.sql.Timestamp(year-1900,month-1,day,00,00,00,00);
        java.sql.Timestamp thedate2 = new java.sql.Timestamp(edyear-1900,edmonth-1,edday,00,00,00,00);
		return new ServerResponse<saleReport_TotalFee_User>(200,"ok",dateRepo.TotalRevenueByDate(thedate1,thedate2));
	}
	
	
	/*
	 * refer to SM4-2
	 * 
	 * return list of date base on user name
	 * 
	 * status: done
	 * */
	@GetMapping(value="/dates/{firstname}/{lastname}")
	@ResponseBody
	public ServerResponse<Dates> findDateByUserName(@PathVariable("firstname") String firstname,@PathVariable("lastname") String lastname) {
		return new ServerResponse<Dates>(200,"ok",dateRepo.findByUserName(firstname,lastname));
	}
	
	
	@GetMapping(value="/List_TotalRevenue_per_cr")
	@ResponseBody
	public ServerResponse<saleReport_TotalFee_CustRep> TotalRevenueListPerCR() {
		return new ServerResponse<saleReport_TotalFee_CustRep>(200,"ok",dateRepo.TotalRevenueList_perCR());
	}
	
	/*refer to SM-6
	 * 
	 * return the most productive CR
	 * 
	 * status: done
	 * 
	 * */
	@GetMapping(value="/List_TotalRevenue_per_cr/best")
	@ResponseBody
	public ServerResponse<saleReport_TotalFee_CustRep> findMostProductiveCR() {
		List<saleReport_TotalFee_CustRep> result = dateRepo.TotalRevenueList_perCR();
		if(result.isEmpty()) {
			return new ServerResponse<saleReport_TotalFee_CustRep>(500, "Internal Server Error");
		}
		return new ServerResponse<saleReport_TotalFee_CustRep>(200,"ok", result);
	}
	
	
	
	/*refer to SM-7
	 * 
	 * return the user who pay most
	 * 
	 * status: yet
	 * 
	 * this alg assume that dor every date, profile1 pay all the bookingFee and profile2 does not pay at all
	 * */
	@GetMapping(value="/List_TotalRevenue_per_user/best")
	@ResponseBody
	public ServerResponse<saleReport_TotalFee_User> findBestUserBaseOnFee() {
		return new ServerResponse<saleReport_TotalFee_User>(200,"ok",this.dateRepo.TotalRevenueList_perUser());
	}
	
	
	/*
	 * refer to SM-8
	 * list of most active user
	 * 
	 * status: done
	 * 
	 * */
	@GetMapping(value="/list_active_users/{number}")
	@ResponseBody
	public ServerResponse<User> findListOfMostActiveUser(@PathVariable("number") int number){
		if(number <=0) {
			return new ServerResponse(406,"input number is less than zero");
		}
		List<User> datas = userRepo.findMostActiveUsers();
		int num = Math.min(number,datas.size());
		List<User> rtlist =  datas.subList(0, num);
		return new ServerResponse<User>(200,"ok",rtlist);
	}
	
	
	/*
	 * refer to SM-10
	 * list of highest rating user
	 * 
	 * status: done
	 * 
	 * */
	@GetMapping(value="/list_hightest_rating_users/{number}")
	@ResponseBody
	public ServerResponse<User> findListOfHightestRatingUser(@PathVariable("number") int number){
		if(number <=0) {
			return new ServerResponse(406,"input number is less than zero");
		}
		List<User> datas = this.userRepo.findListOfHightestRatingUser();
		int num = Math.min(number,datas.size());
		List<User> rtlist =  datas.subList(0, num);
		return new ServerResponse<User>(200,"ok",rtlist);
	}
	
	/*
	 * refer to SM-9
	 * list of highest rating user
	 * 
	 * status: done
	 * 
	 * */
	@GetMapping(value="/list_profiles_dated_one_profile/{profileId}")
	@ResponseBody
	public ServerResponse<Profile> findListOfProfileWhoDatedParticularProfile(@PathVariable("profileId") String profileId){
		List<Profile> list = dateRepo.profile2sDatedOneProfile(profileId);
		list.addAll(dateRepo.profile1sDatedOneProfile(profileId));
		return new ServerResponse<Profile>(200,"ok",list);
	}
	
	@GetMapping(value="/profile")
	@ResponseBody
	public ServerResponse<Profile> findCustomersWhoDatedUser(){
		
		List<Profile> profiles = profileRepo.findAll();
		return new ServerResponse<Profile>(200, "OK", profiles);
	}
	
	
	/*
	 * refer to SM-11
	 * list of highest rating calendar date
	 * 
	 * this alg calculate the rating base on two users rating for that date
	 * 
	 * include the location, could be the report for highest rate Dating location list
	 * 
	 * status: done
	 * 
	 * */
	@GetMapping(value="/report_highest_rating_dates/{number}")
	@ResponseBody
	public ServerResponse<report> findHighestRatedCalendar(@PathVariable("number") int number){
		if(number <=0) {
			return new ServerResponse(406,"input number is less than zero");
		}
		List<report> datas = this.dateRepo.highest_rating_dates();
		int num = Math.min(number,datas.size());
		List<report> rtlist =  datas.subList(0, num);
		return new ServerResponse<report>(200,"ok",rtlist);
	}
	
	@GetMapping(value="/dates")
	@ResponseBody
	public ServerResponse<Dates> getDatesToReserve(){
		List<Dates> dates = dateRepo.findByStatus(0);
		return new ServerResponse<Dates>(200,"ok",dates);
	}
	
	@PostMapping(value="dates/record")
	public ServerResponse<Dates> updateDate(@RequestBody Dates date){
		date.setStatus(1);
		dateRepo.save(date);
		return new ServerResponse<Dates>(200, "OK");
	}
	
	
}
	