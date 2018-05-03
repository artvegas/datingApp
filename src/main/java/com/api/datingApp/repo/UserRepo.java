package com.api.datingApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.datingApp.model.Person;
import com.api.datingApp.model.User;
import com.api.datingApp.model.returnTypes.customer_mailing_list;

public interface UserRepo extends JpaRepository<User, Person> {
	public List<User> findBySsn(String ssn);
	
	@Query("select Count(u) from User u where u.person.zipcode = ?1")
	 int findBytest(int zipcode);
	 
	 
	 @Query("SELECT w FROM User w ORDER BY w.dateOfLastAct DESC")
	 public List<User> findMostActiveUsers();
	 

	 @Query("Select d from User d Order By d.rating DESC ")
	 public List<User> findListOfHightestRatingUser();
	 
	 @Query("Select new com.api.datingApp.model.returnTypes.customer_mailing_list(CONCAT(d.person.street, ' ',  d.person.city, ' ', d.person.state, ' ', d.person.zipcode) as Address,  d.person.email, d.person.telephone,  d.person.firstName, d.person.lastName,d.ssn)"
	 		+ " from User d")
	 public List<customer_mailing_list> customerMailingList();

	public List<User> findByPerson(Person person);
}