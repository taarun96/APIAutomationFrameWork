package com.api.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtil {
	private static Dotenv dotenv;

    static {
        // Loads the .env file from the project root
        dotenv = Dotenv.load();
    }

    // Private constructor to prevent instantiation
    private EnvUtil() {
    }
    
    public static String getValue(String varName) {
    	System.out.println("******Secrets Reading from .env file****");
        return dotenv.get(varName);
    }
}
