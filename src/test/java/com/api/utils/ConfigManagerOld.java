package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOld {
	
	
	private static Properties prop = new Properties();
	
	
	private ConfigManagerOld() {
		
	}
	static {
	
		
		File configFile = new File(System.getProperty("user.dir") + File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+
		File.separator+"config.properties");
		
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configFile);
			try {
				prop.load(fileReader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
}
	
	
		public static String getProperties(String key) {
		return prop.getProperty(key);
		}
}
