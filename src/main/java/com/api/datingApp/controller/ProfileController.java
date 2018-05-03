package com.api.datingApp.controller;

import java.util.Calendar;
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

import com.api.datingApp.ProfileSpecification;
import com.api.datingApp.model.Person;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.SearchCriteria;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.repo.PersonRepo;
import com.api.datingApp.repo.ProfileRepo;

@RestController
@RequestMapping(value="/profiles")
public class ProfileController {
	
	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private PersonRepo personRepo;
	
	@GetMapping(value="/all")
	@ResponseBody 
    public ServerResponse<Profile> getAll(){
		try {
			List<Profile> profiles = profileRepo.findAll();
			return new ServerResponse<Profile>(200, "OK",profiles);
		}catch(Exception e) {
			return new ServerResponse<Profile>(500, e.getMessage());
		}
    }
	
	@GetMapping(value="/{ssn}")
	@ResponseBody
	public ServerResponse<Profile> getProfileByPerson(@PathVariable("ssn") String ssn) {
		User user = new User();
		user.setSsn(ssn);
		List<Profile> profiles = profileRepo.findByUser(user);
		if(profiles.isEmpty()) {
			return new ServerResponse<Profile>(212, "User With SSN Does Not Exist.");
		}
		
		return new ServerResponse<Profile>(200, "OK", profiles);
	}
	
	@PostMapping(value="/add/{ssn}")
	@ResponseBody
	public ServerResponse<Profile> addNewProfile(@RequestBody Profile profile, @PathVariable("ssn") String ssn) {
		
		if(personRepo.findBySsn(ssn).isEmpty()) {
			return new ServerResponse<Profile>(211, "User With SSN Does Not Exsist.");
		}else {
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			profile.setCreationDate(date);
			profile.setLastModDate(date);
			profileRepo.save(profile);
			return new ServerResponse<Profile>(200, "OK");
		}
	}
	
	@PostMapping(value="/delete")
	@ResponseBody
	public ServerResponse<Profile> deleteProfile(@RequestBody Profile profile) {
		profileRepo.delete(profile);
		return new ServerResponse<Profile>(200, "OK");
	}
	
	@PostMapping(value="/save/{ssn}")
	@ResponseBody
	public ServerResponse<Profile> saveProfile(@RequestBody Profile profile, @PathVariable("ssn") String ssn) {
		
		if(personRepo.findBySsn(ssn).isEmpty()) {
			return new ServerResponse<Profile>(211, "User With SSN Does Not Exsist.");
		}else {
			
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			profile.setLastModDate(date);
			profileRepo.save(profile);
			return new ServerResponse<Profile>(200, "OK");
		}
	}
	
	@PostMapping(value="/find")
	@ResponseBody
	public ServerResponse<Profile> findProfiles(@RequestBody Profile profile) {
		String ageStart = (profile.getDatingAgeRangeStart() - 1) + "";
		String ageEnd = (profile.getDatingAgeRangeEnd() + 1) + "";
		String ssn = profile.getUser().getSsn();
		ProfileSpecification ageStartSpec = new ProfileSpecification(new SearchCriteria("age", ">", ageStart));
		ProfileSpecification ageEndSpec = new ProfileSpecification(new SearchCriteria("age", "<", ageEnd));
		ProfileSpecification userSpec = new ProfileSpecification(new SearchCriteria("user", "!=", profile.getUser()));
		ProfileSpecification citySpec = null;
		ProfileSpecification stateSpec = null;
		ProfileSpecification streetSpec = null;
		ProfileSpecification zipcodeSpec = null;
		ProfileSpecification genderSpec = null;
		ProfileSpecification weightSpec = null;
		ProfileSpecification heightSpec = null;
		ProfileSpecification hairColorSpec = null;
		ProfileSpecification hobbiesSpec = null;
		ProfileSpecification mSpec = null;
		ProfileSpecification fSpec = null;
		if(profile.getM_f() != "") {
			genderSpec =  new ProfileSpecification(new SearchCriteria("m_f", "=", profile.getM_f()));
		}else {
			mSpec =  new ProfileSpecification(new SearchCriteria("m_f", "=", "m"));
			fSpec = new ProfileSpecification(new SearchCriteria("m_f", "=", "f"));
		}
		if(profile.getWeight() != 0.0) {
			weightSpec =  new ProfileSpecification(new SearchCriteria("weight", ":", profile.getWeight()));
		}
		if(profile.getHeight() != 0.0) {
			heightSpec =  new ProfileSpecification(new SearchCriteria("height", ":", profile.getHeight()));
		}
		if(profile.getHairColor() != "") {
			hairColorSpec =  new ProfileSpecification(new SearchCriteria("hairColor", ":", profile.getHairColor()));
		}
		if(profile.getHobbies() != "") {
			hobbiesSpec = new ProfileSpecification(new SearchCriteria("hobbies", ":", profile.getHobbies()));
		}
		if(profile.getUser().getPerson().getCity() != "") {
			citySpec = new ProfileSpecification(new SearchCriteria("user", "city", profile.getUser().getPerson().getCity()));
		}
		if(profile.getUser().getPerson().getState() != "") {
			stateSpec = new ProfileSpecification(new SearchCriteria("user", "state", profile.getUser().getPerson().getState()));
		}
		if(profile.getUser().getPerson().getStreet() != "") {
			streetSpec = new ProfileSpecification(new SearchCriteria("user", "street", profile.getUser().getPerson().getStreet()));
		}
		if(profile.getUser().getPerson().getZipcode() != 0) {
			zipcodeSpec = new ProfileSpecification(new SearchCriteria("user", "zipcode", profile.getUser().getPerson().getZipcode()));
		}
		
		List<Profile> profiles = profileRepo.findAll(Specifications.where(ageStartSpec)
				.and(userSpec).and(ageEndSpec).and(genderSpec).
				and(weightSpec).and(heightSpec).and(hairColorSpec)
				.and(hobbiesSpec).and(citySpec).and(stateSpec)
				.and(streetSpec).and(zipcodeSpec).or(fSpec).or(mSpec));
		if(profiles.isEmpty()) {
			return new ServerResponse<Profile>(500, "Interna Server Error");
		}else {
			return new ServerResponse<Profile>(200, "OK", profiles);
		}
		
		
		
		
		
	}
	
}
