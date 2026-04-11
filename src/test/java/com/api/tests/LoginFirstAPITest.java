package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginFirstAPITest {

	@Test
	public void loginTest() {

		System.out.println("------->" + System.getProperty("env"));
		UserCredentials userCredentials = new UserCredentials("iamfd", "password");
		given().baseUri(getProperty("BASE_URI")).and().contentType(ContentType.JSON).and().accept(ContentType.JSON)
				.and().body(userCredentials).log().uri().log().method().log().headers().log().body().when()
				.post("login").then().log().all().statusCode(200).time(lessThan(1000L)).and()
				.body("message", equalTo("Success")).and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
	}

}
