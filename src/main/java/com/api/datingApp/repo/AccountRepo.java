package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.datingApp.model.Account;
import com.api.datingApp.model.User;

public interface AccountRepo extends JpaRepository<Account, User> {
	public List<Account> findByUser(User user);
}
