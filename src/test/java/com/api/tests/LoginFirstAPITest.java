package com.api.tests;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.AuthService;

import io.restassured.response.Response;


public class LoginFirstAPITest {

	private UserCredentials userCredentials ;
    private AuthService authService;
	@BeforeMethod(description="Create the request payload for login API")
	public void setup() {
		userCredentials	= new UserCredentials("iamfd", "password");
		authService=new AuthService();
	}
	
	
	
	@Test(description="Verify if loginAPI is working for imafd user",groups= {"regression","smoke","api"})
	public void loginTest() {

		System.out.println("------->" + System.getProperty("env"));
		
		authService.login(userCredentials).
		then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/LoginFirstResponseSchema.json"));

	}

}
