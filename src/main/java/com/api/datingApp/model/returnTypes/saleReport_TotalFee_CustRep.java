package com.api.datingApp.model.returnTypes;

import com.api.datingApp.model.Employee;

public class saleReport_TotalFee_CustRep {
	
	private double BookingFees;
	
	private Employee CustRep;
	
	public saleReport_TotalFee_CustRep(double bookingFees, Employee custRep) {
		super();
		BookingFees = bookingFees;
		CustRep = custRep;
	}
	public double getBookingFees() {
		return BookingFees;
	}
	public void setBookingFees(double bookingFees) {
		BookingFees = bookingFees;
	}
	public Employee getCustRep() {
		return CustRep;
	}
	public void setCustRep(Employee custRep) {
		CustRep = custRep;
	}

}