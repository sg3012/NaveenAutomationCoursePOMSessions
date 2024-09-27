package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportBasic {

	public static void main(String args[]) {
		
		ExtentReports extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/testreport.html");
		extentReports.attachReporter(sparkReporter);
		
		// Creating test manually, attach in the report
		// and log various statuses to the tests
		// using various log levels
		extentReports.createTest("Test 1")
			.log(Status.INFO, "info 1") // INFO logs will provide additional info apart
		    // from pass, fail, skip
			.log(Status.INFO,"info 2" )
			.log(Status.INFO,"info 3" )
			.log(Status.PASS,"Pass" ) // Log level pass will indicate the test is passed
			.log(Status.WARNING,"Warning" ) // Log level Warning will indicate the test has warning
			.log(Status.WARNING,"Warning" ) 
			.log(Status.SKIP,"skip" ) // Log level Warning will indicate the test is skipped
			.log(Status.FAIL,"fail" ) // Log level Fail will indicate the test is Failed
			.log(Status.PASS,"fail" );
		// Below is the precedence order for all the log levels
		// in Extent reports from highest to lowest:
		// FAIL > SKIP > WARNING > PASS > INFO
		// Meaning:
		// --> If we have provided log level FAIL along with any other log
		// levels in our test anywhere then the overall test status will be
		// FAILED
		// --> If we have provided log level SKIP along with any other log
		// levels except FAIL in our test anywhere then the overall test 
		// status will be SKIPPED
		// --> If we have provided log level WARNING along with any other log
		// levels except FAIL,SKIP in our test anywhere then the overall test 
		// status will be WARNING
		// --> If we have provided log level PASS along with any other log
		// levels except FAIL,SKIP,WARNING in our test anywhere then 
		// the overall test status will be PASSED
		// --> If we have provided log level INFO ONLY and not FAIL,SKIP, 
		// WARNING,PASS in our test anywhere then the overall test status 
		// will be PASS
			
		extentReports.flush(); // [IMPORTANT]: Flush
		// method is actually responsible for
		// creating the extent reports in the
		// mentioned path. So, DON'T forget
		// to use this method when you are done
		// with writing ALL the reporting code. Otherwise,
		// it will not create the desired report.
	}

}
