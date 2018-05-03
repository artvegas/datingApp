package com.api.datingApp.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.api.datingApp.model.Dates;
import com.api.datingApp.model.Profile;
import com.api.datingApp.model.returnTypes.report;
import com.api.datingApp.model.returnTypes.saleReport_TotalFee_CustRep;
import com.api.datingApp.model.returnTypes.saleReport_TotalFee_User;
import com.api.datingApp.model.DateKey;

public interface DateRepo extends JpaRepository<Dates, String>, JpaSpecificationExecutor<Dates> {
	public List<Dates> findByDateKey(DateKey dateKey);
	
	@Query("Select "
			+"new com.api.datingApp.model.returnTypes.saleReport_TotalFee_CustRep( SUM(d.bookingFee) , d.employee ) "
			+"From Dates d "
			+ "Where d.dateKey.dateTime Between ?1 And ?2"
			+ " Group by d.employee")
	public List<saleReport_TotalFee_CustRep> generateReportByCalendarDate(java.sql.Timestamp ts1,java.sql.Timestamp ts2);

	
	@Query("Select d From Dates d Where d.dateKey.dateTime Between ?1 And ?2")
	public List<Dates> findByCalendarDate(Timestamp thedate1, Timestamp thedate2);
	
	@Query("Select d From Dates d Where "
			+ "(d.dateKey.profile1.user.person.firstName = ?1 And d.dateKey.profile1.user.person.lastName = ?2) "
			+ "Or "
			+ "(d.dateKey.profile2.user.person.firstName = ?1 And d.dateKey.profile2.user.person.lastName = ?2)")
	public List<Dates> findByUserName(String FN,String LN);
	
	
	@Query("Select "
			+"new com.api.datingApp.model.returnTypes.saleReport_TotalFee_CustRep( SUM(d.bookingFee) as t , d.employee ) "
			+"From Dates d "
			+"Group by d.employee "
			+ "Order by t DESC")
	public List<saleReport_TotalFee_CustRep> TotalRevenueList_perCR();
	
	
	@Query("Select "
			+"new com.api.datingApp.model.returnTypes.saleReport_TotalFee_User( SUM(d.bookingFee) as t , d.dateKey.profile1.user ) "
			+"From Dates d "
			+"Group by d.dateKey.profile1.user "
			+ "Order by t DESC")
	public List<saleReport_TotalFee_User> TotalRevenueList_perUser();
	
	@Query("Select "
			+"new com.api.datingApp.model.returnTypes.saleReport_TotalFee_User( SUM(d.bookingFee) as t ) "
			+"From Dates d "
			+"Where d.dateKey.dateTime Between ?1 And ?2")
	public List<saleReport_TotalFee_User> TotalRevenueByDate(java.sql.Timestamp ts1,java.sql.Timestamp ts2);
	
	
	@Query("SELECT d.dateKey.profile1 AS profile FROM Dates d WHERE d.dateKey.profile2.profileId = ?1")
	public List<Profile> profile1sDatedOneProfile(String profileId);
	
	@Query("SELECT d.dateKey.profile2 AS profile FROM Dates d WHERE d.dateKey.profile1.profileId = ?1")
	public List<Profile> profile2sDatedOneProfile(String profileId);
	
	@Query("SELECT "
			+" new com.api.datingApp.model.returnTypes.report( d.dateKey.dateTime , d.location , SUM(d.user1Rating + d.user2Rating)  )"
			+ " FROM Dates d "
			+ " Order by d.user1Rating+d.user2Rating DESC")
	public List<report> highest_rating_dates();
	
	
	@Query("Select d From Dates d Where d.employee.ssn =?1")
	public List<Dates> findByCustRepSsn(String ssn);

	public List<Dates> findByStatus(int i);
	
}
