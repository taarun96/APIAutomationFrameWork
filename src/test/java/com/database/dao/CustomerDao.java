package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {
    
	// Using '?' as a placeholder for the parameter
    private static final String CUSTOMER_DETAIL_QUERY = """
            SELECT * from tr_customer where id=?
            """;

    public static CustomerDBModel getCustomerInfo(int customerId) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        
        // Creating a PreparedStatement instead of a regular Statement
        PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
        
        // Mapping the customerId to the first '?' in the query
        preparedStatement.setInt(1, customerId);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        CustomerDBModel customerDBModel = null;
        while(resultSet.next()) {
            System.out.println(resultSet.getString("first_name"));
            System.out.println(resultSet.getString("email_id"));
         // Line 32-34: Mapping the database row to the Java Object
            customerDBModel = new CustomerDBModel(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("mobile_number"),
                resultSet.getString("mobile_number_alt"),
                resultSet.getString("email_id"),
                resultSet.getString("email_id_alt"));
        }
        
        return customerDBModel;
    }
}