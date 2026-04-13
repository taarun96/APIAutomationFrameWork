package com.api.tests;

import static com.api.constants.Role.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.*;

import static com.api.utils.SpecUtil.*;

public class MasterAPITest {

	@Test(description="Verify if Master API response is shown correctly",groups= {"api","regression","smoke"})
	public void masterAPITest() {
		given().spec(requestSpecWithAuth(FD))

				.when().post("master").then().spec(responseSpec_OK())

			    .body("data", notNullValue()).body("data", hasKey("mst_oem")).body("data", hasKey("mst_model"))
				.body("$", hasKey("message")).body("$", hasKey("data")).body("data.mst_oem.size()", equalTo(2))
				.body("data.mst_oem.size()", greaterThan(0)).body("data.mst_oem.id", everyItem(notNullValue()))
				.body("data.mst_oem.name", everyItem(notNullValue()))
				.body(matchesJsonSchemaInClasspath("response-schema/MasterResponseSchema.json"));
	}

	@Test(description="Verify if correct status code is displayed for Master API for "
			+ "invalid Auth token",groups= {"negative","api","regression","smoke"})
	public void masterAPITest_MissingAuthToken() {
		given().spec(requestSpec()).when().post("master").then().log().all()
				.spec(responseSpec_TEXT(401));
	}
}
