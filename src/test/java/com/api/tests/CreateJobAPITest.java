package com.api.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;


import static com.api.constants.Role.*;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;

public class CreateJobAPITest {

	@Test	
	public void createJobAPITest() {
		
		Customer customer=new Customer("Taarun", "Purusothaman", "9823329021", "", "taarun96@gmail.com", "");
		
		CustomerAddress customerAddress=new CustomerAddress("c 304", "Jupiter", "MG road", "Bangur Nagar", "Goregaon West", "411039", "India", "Maharashtra");
		CustomerProduct customerProduct=new CustomerProduct("2025-04-06T18:30:00.000Z", "12721421225407", "12721421225407", "12721421225407", "2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems=new Problems(1, "Battery Issue");
		Problems[] problemsArray=new Problems[1];
		problemsArray[0]=problems;
		CreateJobPayload createJobPayload =new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		
		
		
		
		    given()
		        .spec(SpecUtil.requestSpecWithAuth(FD, createJobPayload))
		        .body(createJobPayload)
		    .when()
		        .post("/job/create")
		    .then()
		        .spec(SpecUtil.responseSpec_OK());
		}
		
}
