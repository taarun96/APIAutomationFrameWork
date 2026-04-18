package com.database.model;

public class DemoRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CustomerDBModel customer = new CustomerDBModel(
	            "Jatin", 
	            "Sharma", 
	            "9876543210", 
	            "", 
	            "jatin@example.com", 
	            "jatin.alt@example.com"
	        );
		
		System.out.println(customer);
		
	}	

}
