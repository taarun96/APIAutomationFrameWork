package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Faker faker=new Faker(new Locale("en-IND"));
		String firstName=faker.name().firstName();
		String lastName=faker.name().lastName();
		System.out.println(firstName);
		System.out.println(lastName);
		
		
		// 1. Generating a random 10-digit number
        System.out.println(faker.number().digits(10));

        // 2. Generating random Address components
        String buildingNumber = faker.address().buildingNumber();
        System.out.println(buildingNumber);

        System.out.println(faker.address().streetAddress());
        System.out.println(faker.address().streetName());
        System.out.println(faker.address().city());

        // 3. Using numerify() to create a specific phone number pattern
        // The '#' symbol is replaced by a random digit (0-9)
        System.out.println(faker.numerify("704#######"));
        System.out.println(faker.numerify("704#######"));
        System.out.println(faker.numerify("704#######"));
        System.out.println(faker.numerify("704#######"));
        System.out.println(faker.numerify("704#######"));
        
        System.out.println(faker.internet().emailAddress());
	}

}
