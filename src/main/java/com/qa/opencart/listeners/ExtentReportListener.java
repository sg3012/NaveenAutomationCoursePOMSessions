package com.qa.opencart.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.opencart.factory.DriverFactory;

public class ExtentReportListener implements ITestListener{
	// This class explains how we could implement Extent
	// reports in Selenium framework using TestNG listeners
	
	// Version 7.0.x onwards testNG has updated their internal code 
	// to JDK 8.0 which provided a feature to provide implementation
	// of methods in Interfaces. So, when you implement ITestListener
	// interface from TestNG library you will not get an error red 
	// line under the Class name to implement unimplemented methods.
	// Because from version 7.0.x onwards testNG developers have provided
	// a method body to all the methods inside the ITestListener interface.
	// Hence, no method in the interface is unimplemented.
	// Now to override the unimplemented methods from the
	// Eclipse UI in one go, please follow the below steps:
	// Right click anywhere inside the class >> mouse hover
	// source option in the context menu >> click override/implement
	// option >> choose what all methods you want to override under
	// interface name from override/implement methods pop-up
	// that opens up
	private static final String OUTPUT_FOLDER = "./reports/"; 
	private static final String FILE_NAME = "TestExecutionReport.html";
	
	private static ExtentReports extentReports;
	private static ExtentReports extent = init();
	ThreadLocal<ExtentTest> tltest = new ThreadLocal<ExtentTest>();

	// Set initial and global report
	// file Configuration
	private static ExtentReports init() {
		Path path = Paths.get(OUTPUT_FOLDER);
		if(!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(OUTPUT_FOLDER+FILE_NAME);
		extentReports.attachReporter(sparkReporter);
		sparkReporter.config().setReportName("Open Cart Test Automation Results");
		sparkReporter.config().setTheme(Theme.STANDARD);
		extentReports.setSystemInfo("OS", "Windows");
		extentReports.setSystemInfo("OS Version", "11");
		extentReports.setSystemInfo("Author", "Shubham Gupta");
		extentReports.setSystemInfo("Build#", "1.1");
		extentReports.setSystemInfo("Team", "OpenCart QA Team");
		extentReports.setSystemInfo("Customer Name", "Open Cart - NAL");
		extentReports.setSystemInfo("ENV NAME", System.getProperty("env"));
		
		return extentReports;
	}
	
	// This method takes time in milliseconds
	// in JAVA long format and returns
	// it in the JAVA DATE format
	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	// onStart() method indicates the start
	// of a Test Suite
	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test Suite started!");
	}
	
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test Suite ending!");
		extent.flush();
		tltest.remove();
	}

	// onTestStart() method indicates the start
	// of a particular Test case (@Test)
	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid+1, last);
		
		System.out.println(methodName+" started!");
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		
		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		extentTest.assignCategory(className);
		
		tltest.set(extentTest); // Here set method
		// passes the ExtentTest type of object to
		// threadlocal's object as it is of type ExtentTest.
		// Hence, passing the ExtentTest Object to the
		// Threadlocal copy of this thread.
		tltest.get().getModel().setStartTime(getTime(result.getStartMillis()));
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+" passed!");
		tltest.get().pass("Test Passed");
//		tltest.get().pass(MediaEntityBuilder
//					.createScreenCaptureFromPath(DriverFactory
//					.getScreenshot(methodName), methodName).build());
		tltest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+" failed!");
		tltest.get().fail(result.getThrowable(), 
				MediaEntityBuilder
					.createScreenCaptureFromPath(DriverFactory
						.getScreenshot(methodName), methodName).build());
		tltest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println(methodName+" skipped!");
		tltest.get().skip(result.getThrowable(), 
				MediaEntityBuilder
					.createScreenCaptureFromPath(DriverFactory
						.getScreenshot(methodName), methodName).build());
		tltest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage for "
				+result.getMethod().getMethodName());
	}
	
}
