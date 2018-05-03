package com.api.datingApp.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.BlindDate;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.SuggestedBy;
import com.api.datingApp.repo.BlindDateRepo;
import com.api.datingApp.repo.EmployeeRepo;
import com.api.datingApp.repo.ProfileRepo;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value="/blindDates")
public class BlindDateController {
	@Autowired
	private BlindDateRepo blindDateRepo;
	
	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@GetMapping(value="/all")
	@ResponseBody
	public ServerResponse<BlindDate> getAllBlindDates(){
		return new ServerResponse<BlindDate>(200,"ok",blindDateRepo.findAll());
	}
	
	
	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse addNewUser(@RequestBody BlindDate date) {
		List<Profile> profiles1 = profileRepo.findByProfileId(date.getId().getProfileA().getProfileId());
		List<Profile> profiles2 = profileRepo.findByProfileId(date.getId().getProfileB().getProfileId());
		Profile profile1 = profiles1.get(0);
		Profile profile2 = profiles2.get(0);
		if(profile1.getUser().getSsn().equals(profile2.getUser().getSsn())) {
			return new  ServerResponse<SuggestedBy>(205,"Profiles are from the same user. User cannot set date with themself.");
		}
		
		Date newDate = new Date();
        Timestamp date1 = new java.sql.Timestamp(newDate.getTime());
        date.getId().setBlindDate_Time(date1);;
		blindDateRepo.save(date);
		return new ServerResponse<SuggestedBy>(200,"ok");
	}
	
	@GetMapping(value="/{profileId}")
	@ResponseBody
	public ServerResponse addNewUser(@PathVariable("profileId") String profileId) {
		List<Profile> profiles = profileRepo.findByProfileId(profileId);
		List<BlindDate> dates1 = blindDateRepo.findByBlindDateIdProfileA(profiles.get(0));
		List<BlindDate> dates2 = blindDateRepo.findByBlindDateIdProfileB(profiles.get(0));
		dates1.addAll(dates2);
		return new ServerResponse<BlindDate>(200,"ok", dates1);
	}
	
}