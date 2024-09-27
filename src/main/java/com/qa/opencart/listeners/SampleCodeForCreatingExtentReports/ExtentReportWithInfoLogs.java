package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportWithInfoLogs {
	// This class explains how we could log 
	// different types of info in the extent reports
	// like:
	// Making some info text in the Reports as BOLD or ITALIC
	
	// Writing the XML or JSON response of an API in the reports
	
	// Writing a collection of data like a drop-down
	// as part of a test script, into the test reports
	
	// Highlight any particular log message from a group
	// of messages in the reports
	
	// Log the exceptions coming during a test execution in the reports

	public static void main(String args[]) {

		ExtentReports extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/testreport.html");
		extentReports.attachReporter(sparkReporter);
		
		extentReports.createTest("Text based Test")
			.log(Status.INFO, "info 1") 
			.log(Status.INFO,"<b>info 2</b>" ) // make the text bold in the report log
			.log(Status.INFO,"<i>info 3</i>" ) // make the text italic in the report log
			.log(Status.INFO,"<b><i>info 4</i></b>" );// make the text bold as well as italic in the report log
		
		String xmlData = "<menu id=\"file\" value=\"File\">\r\n"
				+ "  <popup>\r\n"
				+ "    <menuitem value=\"New\" onclick=\"CreateNewDoc()\" />\r\n"
				+ "    <menuitem value=\"Open\" onclick=\"OpenDoc()\" />\r\n"
				+ "    <menuitem value=\"Close\" onclick=\"CloseDoc()\" />\r\n"
				+ "  </popup>\r\n"
				+ "</menu>";
		
		String jsonData = "{\"menu\": {\r\n"
				+ "  \"id\": \"file\",\r\n"
				+ "  \"value\": \"File\",\r\n"
				+ "  \"popup\": {\r\n"
				+ "    \"menuitem\": [\r\n"
				+ "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\r\n"
				+ "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\r\n"
				+ "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\r\n"
				+ "    ]\r\n"
				+ "  }\r\n"
				+ "}}";
		
//		extentReports.createTest("XML based Test")
//			.log(Status.INFO, xmlData);
		// If you use this code to log xml data in the
		// report then it will not be displayed at
		// all on the report.
		
//		extentReports.createTest("JSON based Test")
//			.log(Status.INFO, jsonData);
		// If you use this code to log json data in the
		// report then it will be displayed in the report
		// but not properly formatted. It will displayed
		// as plain string without proper line
		// breaks and indentation.
		
		// To properly log and align the XML and JSON
		// data in the reports use the below code blocks:
		// CODE TO PROPERLY LOG XML DATA
		extentReports
			.createTest("XML based Test")
			.info(MarkupHelper.createCodeBlock(xmlData, CodeLanguage.XML));
		// CODE TO PROPERLY LOG JSON DATA
		extentReports
			.createTest("JSON based Test")
			.info(MarkupHelper.createCodeBlock(jsonData, CodeLanguage.JSON));
		// NOTE: "createCodeBlock" is the method to log
		// the info in the form of a programming
		// language code
		
		// Logging List, Map and Set type of data in the reports
		
		// First Creating List, Map and set type of data
		// (We can also fetch this data from a file):
		List<String> listData = new ArrayList<String>();
		listData.add("Yadagiri");
		listData.add("Jeevan");
		listData.add("Raj");
		
		Map<Integer,String> mapData = new HashMap<Integer,String>();
		mapData.put(101,"Yadagiri");
		mapData.put(102,"Jeevan");
		mapData.put(103,"Raj");
		
		Set<Integer> setData = mapData.keySet();
		
		// Log the data:
		// For LIST:
		extentReports
			.createTest("List based Test")
			.info(MarkupHelper.createOrderedList(listData))
			.info(MarkupHelper.createUnorderedList(listData));
		// For MAP:
		extentReports
			.createTest("Map based Test")
			.info(MarkupHelper.createOrderedList(mapData))
			.info(MarkupHelper.createUnorderedList(mapData));
		// For SET:
		extentReports
			.createTest("Set based Test")
			.info(MarkupHelper.createOrderedList(setData))
			.info(MarkupHelper.createUnorderedList(setData));
		
		// NOTE: 
		// --> createOrderedList is the method
		// to log the data as numbered points
		// for any JAVA Collection in the reports.
		
		// --> createUnOrderedList is the method
		// to log the data as non-numbered or bullet 
		// points for any JAVA Collection in the reports.
		
		// Logging a specific highlighted text in the reports
		// out of the whole text
		extentReports
			.createTest("Highlight log Test")
			.info("This is not a highlighted text")
			.info(MarkupHelper.createLabel("This is a highlighted text", ExtentColor.RED));
		// NOTE: createLabel is method to create highlighted text in the reports
		
		// Logging Exceptions in the reports
		// 1. When exception is thrown in the code
		try {
			int a = 5/0;
		} catch (Exception e) {
			extentReports
				.createTest("Exception Test1")
				.fail(e);
		}
		
		// 2. Creating a custom exception
		Throwable t = new RuntimeException("This is a custom exception");
		extentReports
			.createTest("Exception Test2")
			.fail(t);
		
		extentReports
			.createTest("Exception Test3")
			.fail(t);
		// NOTE[IMPORTANT]: Whenever you are logging
		// an exception in the report it means
		// the test has actually failed. So, DON'T
		// FORGET 2 points below while creating reports
		// using TestNG listeners:
		// 1. If you are logging an exception this log
		// level will always be a part of FAILED test listener
		
		// 2. You MUST use the log level FAIL to mark the overall 
		// test status as FAILED.
		
		extentReports.flush();
	}

}
