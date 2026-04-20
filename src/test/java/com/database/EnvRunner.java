package com.database;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvRunner {
	public static void main(String[] args) {
        // Load the .env file
        Dotenv dotenv = Dotenv.load();
        
        // Retrieve the password using the key defined in your .env file
        String data = dotenv.get("DB_PASSWORD");
        
        // Output the retrieved credential
        System.out.println(data);
    }
}
