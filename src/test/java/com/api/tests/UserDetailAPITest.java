package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.UserService;
@Listeners(com.listeners.APITestListener.class)
public class UserDetailAPITest {

	private UserService userService;

	@BeforeMethod(description = "Setting up the UserService instance")
	public void setup() {
		userService = new UserService();

	}

	@Test(description = "Verify if the Userdetails API response is shown correctly", groups = { "api", "smoke",
			"regression" })
	public void userDetailsAPITest() throws IOException {

		userService.userDetails(FD).then().spec(responseSpec_OK()).and()
				.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));

	}
}