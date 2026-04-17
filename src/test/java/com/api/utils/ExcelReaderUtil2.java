package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {

	private ExcelReaderUtil2() {

	}

	public static <T> Iterator<T> loadTestData(String fileName,String sheetName, Class<T> clazz) {
	    // APACHE POI OOXML LIB
	    InputStream is = Thread.currentThread().getContextClassLoader()
	            .getResourceAsStream(fileName);
	    XSSFWorkbook myWorkBook = null;
	    try {
	        myWorkBook = new XSSFWorkbook(is);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    // Focus on the Sheet
	    XSSFSheet mySheet = myWorkBook.getSheet(sheetName);//LoginTestData

	    List<T> dataList=Poiji.fromExcel(mySheet, clazz);
	    return dataList.iterator();
	}
}
