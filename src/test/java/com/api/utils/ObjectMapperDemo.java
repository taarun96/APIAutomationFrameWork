package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.api.request.model.UserCredentials;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperDemo {
	
	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("testdata/demo.json");
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		
		UserCredentials[] userCredentialsArray=objectMapper.readValue(is,UserCredentials[].class);
		
		List<UserCredentials> userCredentials=Arrays.asList(userCredentialsArray);
		userCredentials.iterator();
		
	/*	UserCredentials userCredentials=objectMapper.readValue(is,UserCredentials.class);
		
		System.out.println(userCredentials);
		
		System.out.println(userCredentials.username());
		
		*/
		
				
	}

}
