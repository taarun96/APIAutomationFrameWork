package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constants.Role.*;


public class UserDetailAPITest {
	@Test
    public void userDetailsAPITest() throws IOException {
        
        given()
            .spec(SpecUtil.requestSpecWithAuth(FD)) 
        .when()
            .get("userdetails") 
        .then()
            .spec(SpecUtil.responseSpec_OK()) 
            .and()
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
    }

}
