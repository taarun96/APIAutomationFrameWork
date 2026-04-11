package com.api.tests;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperties;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

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
		given().baseUri(getProperties("BASE_URI")).and().header(authHeader).and().accept(ContentType.JSON).log().uri()
				.log().method().log().body().log().headers().when().get("userdetails").then().log().all()
				.statusCode(200).time(lessThan(1000L)).and()
				.body("message", equalTo("Success")).and().body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));

	}
}
