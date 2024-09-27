package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportWithMultipleRports {
	// This class explains how to create
	// multiple test reports corresponding to
	// a single test suite execution. One
	// of these reports will be corresponding to
	// only passed tests, one corresponding to
	// only failed tests, one mapped to only
	// skipped tests, one mapped to only warning
	// tests and one to only Exception throwing tests
	
	public static void main(String args[]) {

		ExtentReports extentReports = new ExtentReports();
		// In order to create multiple reports
		// for a single test suite execution we
		// need to create multiple ExtentSparkReporter type
		// objects as shown below:
		
		// 1. Create multiple objects of ExtentSparkReporter
		ExtentSparkReporter sparkReporterAll = new ExtentSparkReporter("./reports/AllTestsReport.html");
		
		ExtentSparkReporter sparkReporterFail = new ExtentSparkReporter("./reports"
				+ "/FailedTestsReport.html");
		// 2. Filter out all the failed tests from the failed tests'
		// ExtentSparkReporter object ref like below:
		sparkReporterFail.filter().statusFilter()
			.as(new Status[] {Status.FAIL}).apply();
		// in the above line of code Status is an Enum defined in the ExtentReports
		// library. The as function takes an array of status Enum and we know
		// that in JAVA when we create an array of Enum with literals we
		// have to provide the literals of the Enum type only by using Enum name (status here)
		// like Status.FAIL
		// Also, DON'T forget to call apply() method at the last of this method chain
		// other failed tests will not be filtered.
		
		ExtentSparkReporter sparkReporterSkipAndWarning = new ExtentSparkReporter("./reports"
				+ "/SkipAndWarningTestsReport.html");
		// 3. Filter out all the skipped and warning tests from the failed tests'
		// ExtentSparkReporter object ref like below:
		sparkReporterSkipAndWarning.filter().statusFilter()
			.as(new Status[] {
					Status.SKIP,
					Status.WARNING
			}).apply();
		
		// 4. Attach all those ExtentSparkReporter object references 
		// containing all the reports to the single
		// extentReports object so that it could contain
		// all the reports in a single object as shown below:
		extentReports.attachReporter(sparkReporterAll,sparkReporterFail,sparkReporterSkipAndWarning);
		
		extentReports.createTest("Test 1", "Sample Test 1")
			.assignAuthor("Shubham") 
			.assignCategory("Smoke") 
			.assignDevice("Chrome 97") 
			.pass("This test is passed");
		
		extentReports.createTest("Test 2", "Sample Test 2")
		.assignAuthor("Rado")
		.assignCategory("Sanity") 
		.assignDevice("Edge 90")
		.fail("This test is failed");
		
		extentReports.createTest("Test 3", "Sample Test 3")
		.assignDevice("Firefox 64")
		.assignCategory("Regression") 
		.skip("This test is skipped");
		
		extentReports.createTest("Test 4", "Sample Test 4")
		.assignAuthor("Shubham")
		.assignAuthor("Rado")
		.assignCategory("Smoke")
		.assignCategory("Regression")
		.assignDevice("Chrome 97")
		.assignDevice("Chrome 99")
		.warning("This is a warning test");
		
		extentReports.createTest("Test 5", "Sample Test 5")
		.assignAuthor("Shubham", "Rado", "Akan")
		.assignCategory("Smoke", "Regression", "Sanity")
		.assignDevice("Chrome 97","Chrome 99", "Firefox 64")
		.pass("This test is passed");
		
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
