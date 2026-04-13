package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.pojo.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecUtil {
    //static method!!
    
    public static RequestSpecification requestSpec() {
        // To take care of the common request sections (methods)
        RequestSpecification request = new RequestSpecBuilder()
            .setBaseUri(getProperty("BASE_URI"))
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.URI)
            .log(LogDetail.METHOD)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .build();
            
        return request;
    }
    
    
    public static RequestSpecification requestSpecWithAuth(Role role) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(getProperty("BASE_URI"))
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            // Fetches token based on the role provided (e.g., FD, ADMIN)
            .addHeader("Authorization", AuthTokenProvider.getToken(role))
            .log(LogDetail.URI)
            .log(LogDetail.METHOD)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .build();
            
        return requestSpecification;
    }
    
    
    
    public static RequestSpecification requestSpecWithAuth(Role role,Object payload) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(getProperty("BASE_URI"))
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            // Fetches token based on the role provided (e.g., FD, ADMIN)
            .addHeader("Authorization", AuthTokenProvider.getToken(role))
            .setBody(payload)
            .log(LogDetail.URI)
            .log(LogDetail.METHOD)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .build();
            
        return requestSpecification;
    }
 // Method for requests with a body (POST/PUT/PATCH)
    public static RequestSpecification requestSpec(Object payload) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(getProperty("BASE_URI"))
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .setBody(payload)
            .log(LogDetail.URI)
            .log(LogDetail.METHOD)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .build();
            
        return requestSpecification;
    }
    

    
 // For Common Response Validations
    public static ResponseSpecification responseSpec() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectResponseTime(Matchers.lessThan(10000L))
            .log(LogDetail.ALL)
            .build();
            
        return responseSpecification;
    }
    
    
    public static ResponseSpecification responseSpec_OK() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectStatusCode(200)
            .expectResponseTime(Matchers.lessThan(1000L)) // 1 second threshold
            .log(LogDetail.ALL)
            .build();
            
        return responseSpecification;
    }

 // 4. Response Spec for JSON Responses
    public static ResponseSpecification responseSpec_JSON(int statusCode) {
        return new ResponseSpecBuilder()
            .expectStatusCode(statusCode)
            .expectContentType(ContentType.JSON)
            .expectResponseTime(Matchers.lessThan(1000L))
            .log(LogDetail.ALL)
            .build();
    }

    // 5. Response Spec for PLAIN TEXT Responses
    public static ResponseSpecification responseSpec_TEXT(int statusCode) {
        return new ResponseSpecBuilder()
            .expectStatusCode(statusCode)
            .expectResponseTime(Matchers.lessThan(1000L))
            .log(LogDetail.ALL)
            .build();
    }
}