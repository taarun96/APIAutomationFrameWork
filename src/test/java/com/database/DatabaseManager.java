package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManager {
	
	
	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	
	
	private DatabaseManager() {
		
	}
	private static Connection conn;
	public synchronized static void createConnection() throws SQLException {
		if(conn==null) {
		 conn=DriverManager.getConnection(DB_URL, DB_USER_NAME,
				DB_PASSWORD);
		}
		System.out.println(conn);
	}
}
