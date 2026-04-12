package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.*;

import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {

	@Test
	public void masterAPITest() {
		given().spec(SpecUtil.requestSpecWithAuth(FD))

				.when().post("master").then().spec(SpecUtil.responseSpec_OK())

			    .body("data", notNullValue()).body("data", hasKey("mst_oem")).body("data", hasKey("mst_model"))
				.body("$", hasKey("message")).body("$", hasKey("data")).body("data.mst_oem.size()", equalTo(2))
				.body("data.mst_oem.size()", greaterThan(0)).body("data.mst_oem.id", everyItem(notNullValue()))
				.body("data.mst_oem.name", everyItem(notNullValue()))
				.body(matchesJsonSchemaInClasspath("response-schema/MasterResponseSchema.json"));
	}

	@Test
	public void masterAPITest_MissingAuthToken() {
		given().spec(SpecUtil.requestSpec()).when().post("master").then().log().all()
				.spec(SpecUtil.responseSpec_TEXT(401));
	}
}
