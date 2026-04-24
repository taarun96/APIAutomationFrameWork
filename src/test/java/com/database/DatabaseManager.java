package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);

	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_SECS"));
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	private static Connection conn; // Any update that happens to this conn variable!
	// all threads will be aware of it!!!
	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");



	public static String loadSecret(String key) {
		String value = null;
	

		if (isVaultUp) {
			value = VaultDBConfig.getSecret(key);

			if (value == null) {// When something is wrong with Vault!
				LOGGER.error("Vault is Down!! or some issue with Vault");
				isVaultUp = false;
			} else {
				System.out.println();
				LOGGER.info("READING VALUE FOR KEY {} FROM VAULT......",key);
				return value; // Coming from Vault!!
			}
		}
	
		LOGGER.info("READING VALUE FROM ENV......");

		value = EnvUtil.getValue(key);
		return value;
	}

	private DatabaseManager() {

	}

	private static void intializePool() {

		if (hikariDataSource == null) { // First Check which all the parallel threads will enter
			LOGGER.warn("Database Connection is not available.... Creating HikariDataSource");

			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) { // ONLY and only for the first Connection Request
					HikariConfig hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000);// 10 secs
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_SECS * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS * 60 * 1000);// 30 MINS 30*60*1000
					hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);
					hikariDataSource = new HikariDataSource(hikariConfig);
					LOGGER.info("Hikari Datasource created!!!");

				}
			}

		}

	}

	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if (hikariDataSource == null) {
			LOGGER.info("Intializing the DataBase Connection using HikariCP");

			intializePool(); // Automatic Intialization of HikariDataSource
		}

		else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");

			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}

		connection = hikariDataSource.getConnection();

		return connection;

	}
}