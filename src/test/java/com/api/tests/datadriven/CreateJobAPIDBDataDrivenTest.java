package com.api.tests.datadriven;

import static com.api.constants.Role.*;
import static com.api.utils.DateTimeUtil.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.*;

import org.testng.annotations.*;

import com.api.constants.*;

import com.api.request.model.*;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPIDBDataDrivenTest {



	@Test(description = "Verify if CreateJob API response is shown correctly", groups = { "api", "regression",
			"data","csv" },
			dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "CreateJobAPIDBDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {

		given().spec(requestSpecWithAuth(FD, createJobPayload)).body(createJobPayload).when().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
	}

}
