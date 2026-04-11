package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	
	private static Properties prop = new Properties();
	private static String path="config/config.properties";
	
	private ConfigManager() {
		
	}
	static {
	
		String env=System.getProperty("env","qa");
		env=env.toLowerCase().trim();
		System.out.println("Running tests in environment: "+env);
		
		switch (env) {
		case "qa":
			path="config/config.qa.properties";
			break;
		case "dev":
			path="config/config.dev.properties";
			break;
		case "uat":
			path="config/config.uat.properties";
			break;
		default:
			path="config/config.qa.properties";
		}
	
		InputStream input=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if(input==null) {
			throw new RuntimeException("Unable to find config.properties file in classpath"+path);
		}
		
		try {
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
}
	
	
		public static String getProperties(String key) {
		return prop.getProperty(key);
		}
}
