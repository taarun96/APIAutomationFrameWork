package com.api.tests;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CountAPITest {
	@Test
	public void countAPITest() {
		given().spec(SpecUtil.requestSpecWithAuth(FD)) 
		.when()
				.get("/dashboard/count")
				.then()
				.spec(SpecUtil.responseSpec_OK())
				.and()	
				.body("data", notNullValue()).body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", not(everyItem(blankOrNullString())))
				.body("data.key",containsInAnyOrder("created_today", "pending_fst_assignment", "pending_for_delivery"))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
	}
	
	
	@Test
	public void countAPITest_MissingAuthToken() {
	    given()
	        .spec(SpecUtil.requestSpec())
	        .when()
	        .get("/dashboard/count")
	        .then()
	        .spec(SpecUtil.responseSpec_TEXT(401));
	}
}
