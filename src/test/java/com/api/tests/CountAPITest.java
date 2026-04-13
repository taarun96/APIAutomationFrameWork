package com.api.tests;

import static com.api.constants.Role.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.*;

import static com.api.utils.SpecUtil.*;

public class CountAPITest {
	@Test(description="Verify if CountAPITest API responses are shown correctly",groups= {"api","regression","smoke"})
	public void countAPITest() {
		given().spec(requestSpecWithAuth(FD)) 
		.when()
				.get("/dashboard/count")
				.then()
				.spec(responseSpec_OK())
				.and()	
				.body("data", notNullValue()).body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", not(everyItem(blankOrNullString())))
				.body("data.key",containsInAnyOrder("created_today", "pending_fst_assignment", "pending_for_delivery"))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
	}
	
	
	@Test(description="Verify if correct status code is displayed for Count API for "
			+ "invalid Auth token",groups= {"negative","api","regression","smoke"})
	public void countAPITest_MissingAuthToken() {
	    given()
	        .spec(requestSpec())
	        .when()
	        .get("/dashboard/count")
	        .then()
	        .spec(responseSpec_TEXT(401));
	}
}
