package com.api.datingApp.model;

import java.util.List;

public class ServerResponse<T> {
	private int statusCode;
	private String status;
	private List<T> object;
	
	
	
	public ServerResponse(int statusCode, String status, List<T> object) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.object = object;
	}
	
	public ServerResponse(int statusCode, String status, T object) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.object.add(object);
		
	}
	
	public List<T> getObject() {
		return object;
	}
	
	public void setObject(List<T> object) {
		this.object = object;
	}
	public ServerResponse(int statusCode, String status) {
		super();
		this.statusCode = statusCode;
		this.status = status;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
