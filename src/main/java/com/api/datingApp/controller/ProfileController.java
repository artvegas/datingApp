package com.api.datingApp.controller;

import java.util.Calendar;
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

import com.api.datingApp.model.Person;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.model.User;
import com.api.datingApp.repo.PersonRepo;
import com.api.datingApp.repo.ProfileRepo;
import com.api.datingApp.secruity.PasswordAuthentication;

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
			
			List<Person> person = personRepo.findBySsn(ssn);
			String profileId = person.get(0).getFirstName();
			String profileName = profile.getProfileName();
			for(int i = 0; i < 20 - profileId.length(); i++) {
				if(i % 2 == 0) {
					int index = i % profileName.length();
					if(profileName.charAt(index) != ' ') {
						profileId += profileName.charAt(index);
					}
				}else {
					char rand = (char) (Math.random() % 99);
					profileId += rand;
				}
			}
			profile.setProfileId(profileId);
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			profile.setCreationDate(date);
			profile.setLastModDate(date);
			profileRepo.save(profile);
			return new ServerResponse<Profile>(200, "OK");
		}
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
	
}
