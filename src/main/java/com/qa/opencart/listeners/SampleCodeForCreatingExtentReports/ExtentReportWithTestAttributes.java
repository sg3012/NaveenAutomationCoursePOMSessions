package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportWithTestAttributes {
	// This class explains how we could
	// assign different attributes
	// to our test cases like:
	// Author
	// Category
	// Device
	
	public static void main(String args[]) {

		ExtentReports extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/testreport.html");
		extentReports.attachReporter(sparkReporter);
		
		extentReports.createTest("Test 1", "Sample Test 1")
			.assignAuthor("Shubham") // Assigning test Author
			.assignCategory("Smoke") // Assigning Test Category
			.assignDevice("Chrome 97") // Assigning device
		// name to test. Here device could be anything like
		// browser, Laptop models, OS version etc.
			.pass("This test is passed");
		
		extentReports.createTest("Test 2", "Sample Test 2")
		.assignAuthor("Rado")
		.assignCategory("Sanity") 
		.assignDevice("Edge 90")
		.fail("This test is failed");
		
		extentReports.createTest("Test 3", "Sample Test 3")
		.assignDevice("Firefox 64")
		.assignCategory("Regression") 
		.fail("This test is failed");
		
		// Assigning multiple Authors,devices and
		// categories to a single test
		extentReports.createTest("Test 4", "Sample Test 4")
		.assignAuthor("Shubham")
		.assignAuthor("Rado")
		.assignCategory("Smoke")
		.assignCategory("Regression")
		.assignDevice("Chrome 97")
		.assignDevice("Chrome 99")
		.pass("This test is passed");
		
		// Instead of calling these methods (assignAuthor, assignCategory,
		// assignDevice) multiple times to assign multiple authors,
		// categories and devices to tests we can also
		// provide these parameters (authors, categories, devices)
		// multiple times in a single method call. Because
		// these methods also accept multiple string parameters
		// like shown below:
		// METHOD 1 (PASS SEPARATE STRINGS):
		extentReports.createTest("Test 5", "Sample Test 5")
		.assignAuthor("Shubham", "Rado", "Akan")
		.assignCategory("Smoke", "Regression", "Sanity")
		.assignDevice("Chrome 97","Chrome 99", "Firefox 64")
		.pass("This test is passed");
		
		//METHOD 2 (PASS AN ARRAY OF STRING):
		String authors[] = {"Shubham", "Rado", "Akan"};
		String categories[] = {"Smoke", "Sanity"};
		String devices[] = {"Chrome 97","Chrome 99", "Firefox 64"};
		
		extentReports.createTest("Test 6", "Sample Test 6")
		.assignAuthor(authors)
		.assignCategory(categories)
		.assignDevice(devices)
		.pass("This test is passed");
		
		Throwable t = new RuntimeException("This is a custom exception");
		extentReports
		.createTest("Exception test 2")
		.fail(t);
		
		extentReports.flush();
	}

}
