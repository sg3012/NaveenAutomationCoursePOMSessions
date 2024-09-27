package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportWithScreenshots {
	// This class explains how we could
	// attach screenshots to the Extent
	// Reports.

	// Screenshots in the Extent reports
	// could be attached at 2 levels:
	// Test Level
	// Log level
	
	// DIFFERENCE Between addScreenCaptureFromPath() from ExtentTest class
	// and createScreenCaptureFromPath() from MediaEntityBuilder class:
	// The main difference between both the methods is addScreenCaptureFromPath()
	// is used to attach the screenshots at the Test level (right at the top
	// before any logs) whereas createScreenCaptureFromPath() is used to
	// attach screenshots with every log/test step.

	static WebDriver driver;

	public static void main(String args[]) {

		ExtentReports extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/testreport.html");
		extentReports.attachReporter(sparkReporter);
		
		// ATTACHING SCREENSHOT(s) AT THE TEST LEVEL:
		// 1. First open the browser and perform some
		// tests using Selenium:
		driver = new ChromeDriver();
		driver.get("https://www.google.com/");
		
		// 2. Capture the required screenshots:
		String screenshotPath = getScreenshot();
		
		// 3. Attach screenshot:
		extentReports.createTest("Screenshot Test 1", "This is a Demo "
				+ "Test for attaching screenshot at TEST level")
		// The 2nd parameter in the createTest method above
		// is the Test Description which will be
		// displayed below the TimeStamps 
		// in the right side section of the report.
		// Test name will be displayed above the timestamp.
			.info("This is an Info Message1")
			.info("This is an info Message2")
			.info("This is an info Message3")
			.addScreenCaptureFromPath(screenshotPath, "Google Home page");
		
		// 3. Attaching multiple screenshots:
		extentReports.createTest("Screenshot Test 2", "This is a Demo "
						+ "Test for attaching screenshot at TEST level")
					.info("This is an Info Message")
					.addScreenCaptureFromPath(screenshotPath, "Google Home page1")
			 		.addScreenCaptureFromPath(screenshotPath, "Google Home page2")
			 		.addScreenCaptureFromPath(screenshotPath, "Google Home page3")
			 		.addScreenCaptureFromPath(screenshotPath, "Google Home page4")
			 		.addScreenCaptureFromPath(screenshotPath, "Google Home page5");
			
		// ATTACHING SCREENSHOT(s) AT THE LOG LEVEL:
		
		// Attaching screenshot only at log level:
		extentReports.createTest("Screenshot Test 3", "This is a Demo "
				+ "Test for attaching screenshot at LOG level")
			.info("This is an Info Message")
			.fail(MediaEntityBuilder
			.createScreenCaptureFromPath(screenshotPath, "Google Home Page").build());
		
		// Attaching screenshot+String message at log level:
		extentReports.createTest("Screenshot Test 4", "This is a Demo "
						+ "Test for attaching screenshot at LOG level")
					.info("This is an Info Message")
					.fail("This test is failed here: ",MediaEntityBuilder
					.createScreenCaptureFromPath(screenshotPath, "Google Home Page").build());
		
		// Attaching screenshot+Exception at log level:
		Throwable t = new Throwable("Arithmetic Exception Occurred: "
				+ "Divide by Zero..refer screenshot below");
		extentReports.createTest("Screenshot Test 5", "This is a Demo "
										+ "Test for attaching screenshot at LOG level")
					.info("This is an Info Message")
					.fail(t, MediaEntityBuilder
					.createScreenCaptureFromPath(screenshotPath, "Google Home Page").build());
				
		// NOTE: Don't forget to call the build method
		// at last because:
		// This is the method which will actually attach the screenshot
		
		// This is the method which is returning Media class instance
		// which fail() method only accepts. If we don't call build method 
		// then createScreenCaptureFromPath will return MediaEntityBuilder's 
		// instance which will throw a compile time error at fail method.
		
		extentReports.flush();
		driver.quit();
	}

	public static String getScreenshot() {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
