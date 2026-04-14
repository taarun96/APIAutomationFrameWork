package com.demo.csv;

import com.opencsv.bean.CsvBindByName;

public class UserPOJO {
	@CsvBindByName(column="username")
	private String x;
	@CsvBindByName(column="password")
	private String y;
	
	public UserPOJO() {
		
	}
	public UserPOJO(String x, String y) {
		super();
		this.x = x;
		this.y = y;
	}
	public String getUsername() {
		return x;
	}
	public void setUsername(String username) {
		this.x = x;
	}
	public String getPassword() {
		return y;
	}
	public void setPassword(String password) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "UserPOJO [username=" + x + ", password=" + y + "]";
	}
	
	

}
