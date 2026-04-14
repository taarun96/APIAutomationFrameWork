package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
	
	
	private CSVReaderUtil() {
		
	}
	
	public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {

	    // Using Thread.currentThread() to get the resource from the classpath
	    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
	    InputStreamReader isr = new InputStreamReader(is);
	    CSVReader csvReader = new CSVReader(isr);

	    // Using CsvToBeanBuilder to map the CSV columns to the Java Bean
	    CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
	            .withType(bean)
	            .withIgnoreEmptyLine(true)
	            .build();

	    List<T> list = csvToBean.parse();
	    return list.iterator();
	}
}
