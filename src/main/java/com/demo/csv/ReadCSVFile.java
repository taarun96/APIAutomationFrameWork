package com.demo.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {
	public static void main(String[] args) throws IOException, CsvException {
		
		
		/*
		
        String csvPath = "/Users/taarunpurusothaman/eclipse-workspace/RestAssuredAutomationFramework/src/main/resources/testData/LoginCreds.csv";
        
        File csvFile = new File(csvPath);
        
        // FileReader is used to read the file as a stream of characters
        FileReader fr = null;
		try {
			fr = new FileReader(csvFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    CSVReader csvReader = new CSVReader(fr); 
        
        */
        
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCreds.csv");

        InputStreamReader isr=new InputStreamReader(is);	
        
        // CSVReader is the OpenCSV class that parses the file
        CSVReader csvReader = new CSVReader(isr); 
        
        List<String[] >dataList=csvReader.readAll();
        
        
        for(String[] dataArray:dataList)
        {
        	for(String data:dataArray) {
        	System.out.print(data+" ");
        }
        	System.out.println("");
		
	}
}
}
