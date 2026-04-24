package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.services.JobService;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;
@Listeners(com.listeners.APITestListener.class)
public class CreateJobAPITestwithFakeData {
	private CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";
	private JobService jobService;

	@BeforeMethod(description = "Creating createjob api request payload and instantiating the JobService")
	public void setup() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
		jobService = new JobService();
	}

	@Test(description = "Verifying if create job api is able to create Inwrranty job", groups = { "api", "regression",
			"smoke" })

	public void createJobAPITest() {

		int customerId = jobService.createJob(Role.FD, createJobPayload).then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_")).extract().body().jsonPath().getInt("data.tr_customer_id");
	

		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);
		Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name());
		Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
		CustomerAddressDBModel customerAddressFromDB=CustomerAddressDao.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());
		
		Assert.assertEquals (customerAddressFromDB.getFlat_number(),createJobPayload.customer_address().flat_number());
	
		Assert.assertEquals (customerAddressFromDB.getApartment_name(),createJobPayload.customer_address().apartment_name());
		Assert.assertEquals (customerAddressFromDB.getArea(),createJobPayload.customer_address().area());
		Assert.assertEquals (customerAddressFromDB.getLandmark(),createJobPayload.customer_address().landmark());
		Assert.assertEquals (customerAddressFromDB.getState(),createJobPayload.customer_address().state());
		Assert.assertEquals (customerAddressFromDB.getStreet_name(),createJobPayload.customer_address().street_name());
		Assert.assertEquals (customerAddressFromDB.getCountry(),createJobPayload.customer_address().country());
		Assert.assertEquals (customerAddressFromDB.getPincode(),createJobPayload.customer_address().pincode());

		JobHeadModel jobHeadDataFromDB=JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(),createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(),createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(),createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(),createJobPayload.mst_platform_id());
	}

}