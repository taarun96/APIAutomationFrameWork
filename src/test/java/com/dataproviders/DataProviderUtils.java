package com.dataproviders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil2;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import com.opencsv.exceptions.CsvException;

public class DataProviderUtils {
	
	
	@DataProvider(name="LoginAPIDataProvider",parallel=true)
	public static Iterator<UserBean> loginAPIDataProvider() throws IOException, CsvException {
		
			return CSVReaderUtil.loadCSV("testData/LoginCreds.csv",UserBean.class);
	

	}
	
	
	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
	    
	    // 1. Load the CSV data into an Iterator of Beans
	    Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", 
	            CreateJobBean.class);

	    List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
	    CreateJobBean tempBean;
	    CreateJobPayload tempPayload;

	    // 2. Iterate through the beans and map them to Payloads
	    while (createJobBeanIterator.hasNext()) {
	        tempBean = createJobBeanIterator.next();
	        
	        // Using the Mapper class to convert the Flat CSV Bean into a Nested JSON Payload
	        tempPayload = CreateJobBeanMapper.mapper(tempBean);
	        
	        payloadList.add(tempPayload);
	    }

	    // 3. Return the iterator for TestNG to consume
	    return payloadList.iterator();
	}
	
	
	
	
	@DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobFakeDataProvider() {
		String fakerCount = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}
	
	
	@DataProvider(name="LoginAPIJSONDataProvider",parallel=true)
	public static Iterator<UserCredentials> loginAPIJSONDataProvider() throws IOException, CsvException {
		
			return JsonReaderUtil.loadJSON("testData/LoginAPITestData.json",UserCredentials[].class);
	

	}
	
	@DataProvider(name="CreateJobAPIJSONDataProvider",parallel=true)
	public static Iterator<CreateJobPayload> createJobAPIJSONDataProvider() throws IOException, CsvException {
		
			return JsonReaderUtil.loadJSON("testData/CreateJobAPITestData.json",CreateJobPayload[].class);
	

	}
	
	
	
	@DataProvider(name="LoginAPIExcelDataProvider",parallel=true)
	public static Iterator<UserBean> createJobAPIExcelDataProvider() throws IOException, CsvException {
		
			return ExcelReaderUtil2.loadTestData("testdata/PhoenixTestData.xlsx","LoginTestData",UserBean.class);
	

	}
	
	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIExcelDataProvider() {
	    Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("testData/PhoenixTestData.xlsx", 
	            "CreateJobTestData", CreateJobBean.class);

	    List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
	    CreateJobBean tempBean;
	    CreateJobPayload tempPayload;
	    while (iterator.hasNext()) {
	        tempBean = iterator.next();
	        tempPayload = CreateJobBeanMapper.mapper(tempBean);
	        payloadList.add(tempPayload);
	    }

	    return payloadList.iterator();
	}
	
	
	}

