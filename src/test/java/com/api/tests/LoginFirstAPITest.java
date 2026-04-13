package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.*;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginFirstAPITest {

	@Test
	public void loginTest() {

		System.out.println("------->" + System.getProperty("env"));
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");

		given().spec(SpecUtil.requestSpec(userCredentials)).
		when().post("login").then().spec(SpecUtil.responseSpec_OK())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginFirstResponseSchema.json"));

	}

}
