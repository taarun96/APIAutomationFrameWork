package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	
	/*
	private static final String DB_URL = EnvUtil.getValue("DB_URL");
	private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
	private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");
	*/
	
	private  static Connection conn;  //volatile anything happens to this , all threads be aware of it
	private static boolean isVaultUp = true;
	
	private static final String DB_URL = loadSecret("DB_URL");
    private static final String DB_USERNAME = loadSecret("DB_USERNAME");
    private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");

    public static String loadSecret(String key) {
        String value = null;
        // Value will get its value from either Vault or Env

        if (isVaultUp) {
            value = VaultDBConfig.getSecret(key);

            if (value == null) { // When something is wrong with Vault!
                System.err.println("Vault is Down!! or some issue with Vault");
                isVaultUp = false;
            } else {
                System.out.println("READING VALUE FROM VAULT......");
                return value; // Coming from Vault!!
            }
        }

        // We need to pick up data from Env!!
        System.out.println("READING VALUE FROM ENV......");
        value = EnvUtil.getValue(key);
        return value;
    }
	
	
	
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	private static int MAXIMUM_SIZE_POOL=Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static int MINIMUM_IDLE_COUNT=Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static int CONNECTION_TIMEOUT_IN_SECS=Integer.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static int IDLE_TIMEOUT_SECS=Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_SECS"));
	private static int MAX_LIFE_TIME_IN_MINS=Integer.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");
	private DatabaseManager() {
		
	}
	

	
	
	
	public synchronized static void iniatializePool()  {
		if(hikariDataSource==null) {   //first check which all threads will enter
			synchronized(DatabaseManager.class) {
				if(hikariDataSource==null) {  //only one connection checks for first connecction only
					hikariConfig = new HikariConfig();
                    hikariConfig.setJdbcUrl(DB_URL);
                    hikariConfig.setUsername(DB_USERNAME);
                    hikariConfig.setPassword(DB_PASSWORD);
                    
                    hikariConfig.setMaximumPoolSize(MAXIMUM_SIZE_POOL);
                    hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
                    hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000);
                    hikariConfig.setIdleTimeout(Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_SECS")) * 1000);
                    hikariConfig.setMaxLifetime(Integer.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS")) * 60 * 1000);
                    hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);

		            hikariDataSource = new HikariDataSource(hikariConfig);
			}
			}
		}
			
	}
	
	public static Connection getConnection() throws SQLException {
	    Connection connection = null;
	    if(hikariDataSource==null) {
	    	iniatializePool();
	    }
	    else if(hikariDataSource.isClosed()) {
	    	throw new SQLException("Hikari Data Source is Closed");
	    }

	        connection = hikariDataSource.getConnection();

	    return connection;
	}
}
