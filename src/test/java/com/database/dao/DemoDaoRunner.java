package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {
	
	public static void main(String[] args) throws SQLException {
        // Fetching data from the DB using the DAO
        CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
        
        System.out.println(customerDBData);
        System.out.println(customerDBData.getFirst_name());
        System.out.println(customerDBData.getEmail_id());
        System.out.println(customerDBData.getMobile_number());

        // Creating an expected Customer object (Mocking API response data)
        Customer customer = new Customer("Shakira", "Turcotte", "636-701-5043", "", "Freda_Hagenes@yahoo.com", "");
        
        System.out.println(customer.first_name());
        
        // Asserting that the DB data matches the expected data
        Assert.assertEquals(customerDBData.getFirst_name(), customer.first_name());
    }

}
