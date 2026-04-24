package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.Detail;
import com.api.services.DashboardService;
@Listeners(com.listeners.APITestListener.class)
public class DetailsAPITest {

	private DashboardService dashboardService;
	private Detail detailPayload;
	
	@BeforeMethod(description = "Instantiating the Dashboard service and creating detail payload")
	public void setup() {
		dashboardService = new DashboardService();
		detailPayload = new Detail("created_today");
	}
	
	@Test(description = "Verify if Details API is working properly", groups = {"api","smoke","e2e"})
	
	public void detailAPITest() {
		dashboardService.details(FD, detailPayload)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"));
	}
	
	
}