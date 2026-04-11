package com.api.tests;

import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.Test;

import com.api.constants.Role;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;


public class userDetailsAPITest {
	@Test
	public void userDetailsAPITest() {

		Header authHeader = new Header("Authorization",
				getToken(Role.FD));
		given().baseUri(getProperty("BASE_URI")).and().header(authHeader).and().accept(ContentType.JSON).log().uri()
				.log().method().log().body().log().headers().when().get("userdetails").then().log().all()
				.statusCode(200).time(lessThan(1000L)).and()
				.body("message", equalTo("Success")).and().body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));

	}
}
