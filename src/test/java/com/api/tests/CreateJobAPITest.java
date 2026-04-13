package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.ServiceLocation;
import com.api.constants.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {

	@Test	
	public void createJobAPITest() {
		
		Customer customer=new Customer("Taarun", "Purusothaman", "9823329021", "", "taarun96@gmail.com", "");
		//Customer customer=new Customer();
		CustomerAddress customerAddress=new CustomerAddress("c 304", "Jupiter", "MG road", "Bangur Nagar", "Goregaon West", "411039", "India", "Maharashtra");
		CustomerProduct customerProduct=new CustomerProduct(getTimeWithDaysAgo(10),
				"16971421225407", "16971421225407", "16971421225407", getTimeWithDaysAgo(10),
				Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());
		Problems problems=new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemList=new ArrayList();
		problemList.add(problems);
		CreateJobPayload createJobPayload =new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(),
				Warranty_Status.IN_WARRANTY.getCode(), 
				OEM.GOOGLE.getCode(), 
				customer, customerAddress, customerProduct, problemList);
		
		
		
		
		
		    given()
		        .spec(SpecUtil.requestSpecWithAuth(FD, createJobPayload))
		        .body(createJobPayload)
		    .when()
		        .post("/job/create")
		    .then()
		        .spec(SpecUtil.responseSpec_OK())
		        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		        .body("message", equalTo("Job created successfully. "))
		        .body("data.mst_service_location_id", equalTo(1))
		        .body("data.job_number", startsWith("JOB_"));
		}
		
}
