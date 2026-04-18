package com.database.model;

import com.database.dao.CustomerAddressDao;

public class DemoDaoRunner {

	public static void main(String[] args) {
		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao.getCustomerAddressData(255174);
        System.out.println(customerAddressDBModel);
		
	}	

}
