package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import com.dataproviders.api.bean.UserBean;


public class LoginAPIDataDrivenTest {


	
	
	@Test(description="Verify if loginAPI is working for imafd user",groups= {"regression","smoke","datadriven"},
			dataProviderClass=com.dataproviders.DataProviderUtils.class,
			dataProvider="LoginAPIDataProvider")
	public void loginTest(UserBean userbean) {

		System.out.println("------->" + System.getProperty("env"));
		

		given().spec(requestSpec(userbean)).
		when().post("login").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/LoginFirstResponseSchema.json"));

	}

}
