package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerProductDBModel;

public class DemoDaoRunner {
	
	public static void main(String[] args) {
		CustomerProductDBModel customerProductDBModel=CustomerProductDao.getProductInfoFromDB(255567);
		System.out.println(customerProductDBModel);
    }

}
