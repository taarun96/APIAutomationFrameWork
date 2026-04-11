package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CountAPITest {
	@Test
	public void countAPITest() {
		given().baseUri(getProperties("BASE_URI")).and().header("Authorization", getToken(FD)).when()
				.get("/dashboard/count").then().log().all().statusCode(200).and().body("message", equalTo("Success"))
				.time(lessThan(1000L)).body("data", notNullValue()).body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", not(everyItem(blankOrNullString())))
				.body("data.key",containsInAnyOrder("created_today", "pending_fst_assignment", "pending_for_delivery"))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
	}
	
	
	@Test
	public void countAPITest_MissingAuthToken() {
	    given()
	        .baseUri(getProperties("BASE_URI"))
	        .and()
	        .log().uri()
	        .log().method()
	        .log().headers()
	        .when()
	        .get("/dashboard/count")
	        .then()
	        .log().all()
	        .statusCode(401);
	}
}
