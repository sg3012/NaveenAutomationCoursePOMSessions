package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	public static final String TEST_DATA_SHEET_PATH = "./src/test/resources"
			+ "/testdata/OpenCartTestData.xlsx"; // The test data sheet (excel sheet)
	// path is declared as final and static because it is never going
	// to change on the system the tests are running and we also
	// don't need to create any object of the class to access and use this path.
	// Meaning the path will not several properties for different excel files
	// and it is going to be same, that is why we will not create object
	// of this class
	
	// NOTE IMPORTANT: Don't Forget to put a single quote 
	// in any cell in the Excel just before
	// the start of the data which is a large
	// number. Because when Apache POI
	// reads a long number it automatically
	// converts it into exponential form
	// like 9.88741254E7 and when we consume
	// this data and enter into a form
	// which excepts pure numeric format
	// the application may throw a validation
	// message for invalid data.
	
	// IMPORTANT: Don't create separate excel sheets for
	// every test class because it will take
	// a lot of efforts to maintain the excel sheets
	// which in turn takes a lot of time. Plus
	// Excel allows to create a maximum of 255 sheets
	// only inside a workbook.
	
	public static Workbook book;
	public static Sheet sheet;
	
	// Below method will fetch the data from
	// the required excel sheet name which
	// is a part of the complete excel workbook
	public static Object[][] getTestData(String sheetName) {

		System.out.println("reading the data from sheet: "+sheetName);
		
		// This method will also return a 2-D object array
		// because we will consume this method in
		// the data provider and data provider
		// always takes data in 2-D array format
		Object data[][] = null; // initially there is no
		// data in the object array. So it
		// is pointing to null (meaning no data)
		
		// Create FileInputStream class object
		// using the excel sheet path provided in
		// its constructor
		try {
			// Read the excel file using FileInputStream
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
			
			// Initialize the 2-D object array
			// for the required rows and columns
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
//			getLastRowNum() : This method returns the last row index
//			having some data in the sheet. Meaning, the row number
//			it returns is index based not position based.
//			
//			getLastCellNum() : This method returns the last cell index + 1
//			having some data in the sheet. Meaning, the cell number
//			it returns is position based not index based.
			
			// read the data from the sheet
			// in the form of rows and columns
			// using nested loops
			for(int i = 0; i<sheet.getLastRowNum(); i++) {
				for(int j = 0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString(); // We
					// have converted the data returned from the excel
					// to JAVA string format because the data
					// returned is in the Excel String format.
					
				    // The 2-D array we will create here
					// for the data provider is not similar to
					// the excel sheet in look. We will start
					// filling the data in this array starting
					// in 0th row and 0th column. But we will
					// read the data from the sheet starting
					// from 1st row and 1st column because
					// the 0th row contains only the column headers.
					
					// getCell() : This method returns the data written
					// in a specific column, row coordinate numbers. 
					// For e.g. Data written at row 1 and column 0.
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return data;
	}
}
