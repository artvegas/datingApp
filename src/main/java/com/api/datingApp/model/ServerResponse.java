package com.api.datingApp.model;

public class ServerResponse {
	private int statusCode;
	private String status;
	private Object object;
	
	public ServerResponse(int statusCode, String status, Object object) {
		super();
		this.statusCode = statusCode;
		this.status = status;
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
