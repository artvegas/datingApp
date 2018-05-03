package com.api.datingApp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.datingApp.model.Likes;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.ServerResponse;
import com.api.datingApp.repo.LikeRepo;
import com.api.datingApp.repo.ProfileRepo;

@RestController
@RequestMapping(value="/likes")
public class LikeController {

	@Autowired
	LikeRepo likesRepo;
	
	@Autowired
	ProfileRepo profileRepo;

	@PostMapping(value="/add")
	@ResponseBody
	public ServerResponse<Likes> like(@RequestBody Likes like) {
		if(profileRepo.findByProfileId(like.getLikeKey().getLiker().getProfileId()).isEmpty() || 
			profileRepo.findByProfileId(like.getLikeKey().getLikee().getProfileId()).isEmpty()	) {
			return new ServerResponse<Likes>(205, "Invalid profileIds.");
		}
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		like.setDate_time(date);
		likesRepo.save(like);
		return new ServerResponse<Likes>(200, "OK");
	}
	
	@PostMapping(value="/{profileId}")
	@ResponseBody
	public ServerResponse<Likes> getAllLikesForLiker(@PathVariable("profileId") String profileId) {
		if(profileId != null || profileId != "") {
			List<Profile> profiles = profileRepo.findByProfileId(profileId);
			if(!profiles.isEmpty()) {
				return new ServerResponse<Likes>(200, "OK", likesRepo.findByLikeKeyLiker(profiles.get(0)));
			}
		}
		
		return new ServerResponse<Likes>(205, "Invalid input");
	}
	
}
