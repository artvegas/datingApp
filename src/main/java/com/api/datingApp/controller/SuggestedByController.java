package com.api.datingApp.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Employee;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.SuggestedBy;
import com.api.datingApp.model.SuggestedById;
import com.api.datingApp.repo.EmployeeRepo;
import com.api.datingApp.repo.ProfileRepo;
import com.api.datingApp.repo.SuggestedByRepo;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/suggestedBys")
public class SuggestedByController {
	@Autowired
	private SuggestedByRepo suggestedByRepo;
	
	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	
	@GetMapping(value="/all")
	@ResponseBody
	public ServerResponse<SuggestedBy> getAllSuggestedBys(){
		return new ServerResponse<SuggestedBy>(200,"ok",suggestedByRepo.findAll());
	}
	
	/*
	 * Produce a list of profiles as date suggestions for a given profile
	 * */
	@GetMapping(value="/{profileId}")
	@ResponseBody
	public ServerResponse suggestionForOneProfile(@PathVariable("profileId") String profileId) {
		List<SuggestedBy> rt= this.suggestedByRepo.findbyProfileId1(profileId);
		rt.addAll(this.suggestedByRepo.findbyProfileId2(profileId));
		return new ServerResponse<SuggestedBy>(200,"ok",rt);
	}
	
	@PostMapping(value="/{profileId1}/{profileId2}/{ssn}")
	@ResponseBody
	public ServerResponse<SuggestedBy> createSuggestion(@PathVariable("profileId1") String profileId1, 
			@PathVariable("profileId2") String profileId2, @PathVariable("ssn") String ssn) {
		List<Profile> profiles1 = profileRepo.findByProfileId(profileId1);
		List<Profile> profiles2 = profileRepo.findByProfileId(profileId2);
		Profile profile1 = profiles1.get(0);
		Profile profile2 = profiles2.get(0);
		if(profile1.getUser().getSsn().equals(profile2.getUser().getSsn())) {
			return new  ServerResponse<SuggestedBy>(205,"Profiles are from the same user. User cannot set date with themself.");
		}
		List<Employee> employees = employeeRepo.findBySsn(ssn);
		Employee employee = employees.get(0);
		Date newDate = new Date();
        Timestamp date1 = new java.sql.Timestamp(newDate.getTime());
        SuggestedById id = new SuggestedById(employee, profile1, profile2, date1);
		SuggestedBy rt = new SuggestedBy();
		rt.setId(id);
		System.out.println(employee.getPerson().getFirstName());
		System.out.println(profile1.getUser().getPerson().getFirstName());
		System.out.println(profile2.getUser().getPerson().getFirstName());
		System.out.println(date1.toString());
		System.out.println(rt.toString());
		suggestedByRepo.save(rt);
		return new ServerResponse<SuggestedBy>(200,"ok");
	}
	
}

