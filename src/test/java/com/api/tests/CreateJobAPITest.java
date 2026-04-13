package com.api.tests;

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

public class CreateJobAPITest {

	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Create the request payload for Create Job API")
	public void setup() {
		Customer customer = new Customer("Taarun", "Purusothaman", "9823329021", "", "taarun96@gmail.com", "");
		// Customer customer=new Customer();
		CustomerAddress customerAddress = new CustomerAddress("c 304", "Jupiter", "MG road", "Bangur Nagar",
				"Goregaon West", "411039", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "18971421225407",
				"18971421225407", "18971421225407", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemList = new ArrayList();
		problemList.add(problems);
		 createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemList);
	}
	
	
	
	@Test(description="Verify if CreateJob API response is shown correctly",groups= {"api","regression","smoke"})
	public void createJobAPITest() {

		

		given().spec(requestSpecWithAuth(FD, createJobPayload)).body(createJobPayload).when()
				.post("/job/create").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
	}

}
