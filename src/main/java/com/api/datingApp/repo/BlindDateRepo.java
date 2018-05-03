package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.BlindDate;
import com.api.datingApp.model.Profile;

public interface BlindDateRepo extends JpaRepository<BlindDate,String>{

	List<BlindDate> findByBlindDateIdProfileA(Profile profile);

	List<BlindDate> findByBlindDateIdProfileB(Profile profile);

}