package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.Role;
import com.api.constants.ServiceLocation;
import com.api.constants.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.response.model.CreateJobResponseModel;
import com.api.services.JobService;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import com.database.model.MapJobProblemModel;

public class CreateJobAPIWithDBValidationWithResponseModelTest {
	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;
	private JobService jobService;
	@BeforeMethod(description = "Creating createjob api request payload and instantiating the Job Service")
	public void setup() {
		customer = new Customer("Jatin", "Shharma", "7045663552", "", "jatinvsharma@gmail.com", "");
		customerAddress = new CustomerAddress("D 404", "Vasant Galaxy", "Bangur nagar", "Inorbit", "Mumbai", "411039",
				"India", "Maharashtra");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "236630332043167",
				"236630332043167", "236630332043167", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");

		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemList);
		jobService = new JobService();
	}

	@Test(description = "Verifying if create job api is able to create Inwrranty job", groups = { "api", "regression",
			"smoke" })

	public void createJobAPITest() {

		CreateJobResponseModel createJobResponseModel = jobService.createJob(Role.FD, createJobPayload)
				.then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. "))
				.body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"))
				.extract().as(CreateJobResponseModel.class);
		
		System.out.println(createJobResponseModel);
		System.out.println("----------------------------------");
		System.out.println();
		int customerId = createJobResponseModel.getData().getTr_customer_id();

		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		System.out.println(customerDataFromDB);

		Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());
		Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());
		System.out.println("----------------------------------");

		System.out.println();
		CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao
				.getCustomerAddressData(customerDataFromDB.getTr_customer_address_id());

		Assert.assertEquals(customerAddressFromDB.getFlat_number(), customerAddress.flat_number());

		Assert.assertEquals(customerAddressFromDB.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressFromDB.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressFromDB.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressFromDB.getState(), customerAddress.state());
		Assert.assertEquals(customerAddressFromDB.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressFromDB.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressFromDB.getPincode(), customerAddress.pincode());

		int productId = createJobResponseModel.getData().getTr_customer_product_id();

		CustomerProductDBModel customerProductDBData = CustomerProductDao.getProductInfoFromDB(productId);
		Assert.assertEquals(customerProductDBData.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDBData.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDBData.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDBData.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDBData.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductDBData.getMst_model_id(), customerProduct.mst_model_id());

		JobHeadModel jobHeadDataFromDB=JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(),createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(),createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(),createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(),createJobPayload.mst_platform_id());

		

		int tr_job_head_id = createJobResponseModel.getData().getId();
		MapJobProblemModel jobDataFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);
		Assert.assertEquals(jobDataFromDB.getMst_problem_id(),createJobPayload.problems().get(0).id());
		Assert.assertEquals(jobDataFromDB.getRemark(),createJobPayload.problems().get(0).remark());
	}

}