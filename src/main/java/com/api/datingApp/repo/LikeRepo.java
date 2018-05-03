package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.LikeKey;
import com.api.datingApp.model.Likes;
import com.api.datingApp.model.Profile;

public interface LikeRepo extends JpaRepository<Likes, String> {
	public List<Likes> findByLikeKey(LikeKey likeKey);
	public List<Likes> findByLikeKeyLiker(Profile profile);
}
