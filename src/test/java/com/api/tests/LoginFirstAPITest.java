package com.api.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class LoginFirstAPITest {

	private UserCredentials userCredentials ;
	
	@BeforeMethod(description="Create the request payload for login API")
	public void setup() {
		userCredentials	= new UserCredentials("iamfd", "password");
	}
	
	
	
	@Test(description="Verify if loginAPI is working for imafd user",groups= {"regression","smoke","api"})
	public void loginTest() {

		System.out.println("------->" + System.getProperty("env"));
		

		given().spec(requestSpec(userCredentials)).
		when().post("login").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/LoginFirstResponseSchema.json"));

	}

}
