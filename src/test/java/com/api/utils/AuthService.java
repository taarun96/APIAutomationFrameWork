package com.api.utils;

import com.api.request.model.UserCredentials;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthService {
    // 1. Define the endpoint
    private static final String LOGIN_ENDPOINT = "login";


    // 2. Define the action
    public Response login(UserCredentials credentials) {
        Response response= RestAssured.given()
                .body(credentials)
                .when()
                .post(LOGIN_ENDPOINT);
        
        return response;
    }
}