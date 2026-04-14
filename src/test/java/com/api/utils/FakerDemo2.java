package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDemo2 {

	private static String COUNTRY="INDIA";
    public static void main(String[] args) {
        
        // Initializing Faker with Indian Locale for region-specific data
        Faker faker = new Faker(new Locale("en-IND"));

        // 1. Generating Fake Customer Data
        String fname = faker.name().firstName();
        String lname = faker.name().lastName();
        String mobileNumber = faker.numerify("70########");
        String alternateMobileNumber = faker.numerify("70########");
        String customerEmailAddress = faker.internet().emailAddress();
        String altCustomerEmailAddress = faker.internet().emailAddress();

        // Create Customer Object
        Customer customer = new Customer(fname, lname, mobileNumber, alternateMobileNumber, customerEmailAddress, altCustomerEmailAddress);
        System.out.println(customer);

        // 2. Generating Fake Address Data
        String flatNumber = faker.numerify("###");
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().streetName();
        String area = faker.address().streetName();
        String pinCode = faker.numerify("######"); 
        String state = faker.address().state();

        // Create CustomerAddress Object
        CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pinCode, COUNTRY, state);
        System.out.println(customerAddress);
        
        
     // 1. Using DateTimeUtil to get a date from 10 days ago in ISO format
     // This replaces the hardcoded "2025-04-06T..." string
     String dop = DateTimeUtil.getTimeWithDaysAgo(10);

     // 2. Generating Product Details
     String imeiSerialNumber = faker.numerify("###############"); // 15 digits for IMEI
     String popUrl = faker.internet().url();
     int product_id = 1;
     int mst_model_id = 1;

     // 3. Creating the CustomerProduct Object with updated fields
     CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, product_id, mst_model_id);

     System.out.println(customerProduct);
     
     
    


     String fakeRemark = faker.lorem().sentence(10);

     
     Random random = new Random();
     int randomProblemId = random.nextInt(26) + 1; 

     System.out.println("Random Problem ID: " + randomProblemId);

     Problems problems = new Problems(randomProblemId, fakeRemark);

     System.out.println(problems);
     
     
     List<Problems> problemList=new ArrayList<Problems>();
     problemList.add(problems);
     
     CreateJobPayload payload=new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
     System.out.println(payload);
     
    }
}