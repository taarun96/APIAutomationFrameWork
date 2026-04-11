package com.api.utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private AuthTokenProvider() {

	}

	public static String getToken(Role role) {
		
		UserCredentials userCredentials = null;
		if (role==FD) {
		    userCredentials = new UserCredentials("iamfd", "password");
		} 
		else if (role==SUP) {
		    userCredentials = new UserCredentials("iamsup", "password");
		} 
		else if (role==ENG) {
		    userCredentials = new UserCredentials("iameng", "password");
		} 
		else if (role==QC) {
		    userCredentials = new UserCredentials("iamqc", "password");
		}
		else {
			  userCredentials = new UserCredentials("iamfd", "password");
		}

		String token = given().baseUri(ConfigManager.getProperties("BASE_URI")).and().contentType(ContentType.JSON)
				.and().accept(ContentType.JSON).and().body(userCredentials).log().uri().log()
				.method().log().headers().log().body().when().post("login").then().log().ifValidationFails()
				.statusCode(200).time(lessThan(1000L)).body("message", equalTo("Success")).and().extract().body()
				.jsonPath().getString("data.token");

		return token;
	}

}
