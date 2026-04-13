package com.api.tests;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.annotations.*;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.constants.Role.*;


public class UserDetailAPITest {
	@Test(description="Verify if UserDetails API are shown correctly",groups= {"api","regression","smoke"})
    public void userDetailsAPITest() throws IOException {
        
        given()
            .spec(requestSpecWithAuth(FD)) 
        .when()
            .get("userdetails") 
        .then()
            .spec(responseSpec_OK()) 
            .and()
            .body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
    }

}
