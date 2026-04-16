package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

public class LoginAPIJSONDataDrivenTest {

	@Test(description = "Verify if loginAPI is working for iamfd user", groups = { "regression", "smoke", "datadriven",
			"csv" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "LoginAPIJSONDataProvider")
	public void loginTest(UserCredentials userbean) {

		System.out.println("------->" + System.getProperty("env"));

		given().spec(requestSpec(userbean)).when().post("login").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/LoginFirstResponseSchema.json"));

	}

}
