package com.qa.opencart.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Attachment;

public class TestAllureListener implements ITestListener {

	// Returns the name of the test method
	// which is executed in the test class
	
	// When Allure reports are generated in Allure-results
	// folder there will be file attached in this folder
	// ending with the name attachment. If you try to open
	// that attachment with any text editor from
	// IDE it will be in a non-readable format because
	// we are generating the screenshots in the form
	// of a byte array in this class (see saveScreenshotPNG method below).
	
	// DO NOT implement and enable so many reporting mechanisms
	// in your framework using Listeners. Because listeners
	// run in background every time a test suite runs and 
	// if you have so many listeners enabled for different reports
	// in your framework then it will make your test execution SLOW.
	// So it's a good practice to discuss and adopt only one
	// listener after discussion with the whole QA/mgmnt. team.
	
	// Steps to convert all the JSON files generated by allure
	// to a meaningful HTML report:
	// --> Install and configure allure reports command line
	// on your windows using the link: https://allurereport.org/docs/install/
	// --> Open the terminal respective to your OS
	// --> Then navigate to your project root directory using the terminal
	// --> Run the command: allure serve allure-results/ (allure-results
	// is the folder name in which the JSON files are generated
	// by allure under your project root) and it will automatically
	// start a server and open the report automatically on that
	// server IP address.
	
	// The test is marked with BROKEN state in the report when it
	// is getting failed due to locator issue whereas it will be marked
	// with FAILED state in the report if it is getting failed due to
	// failing assertion.
	
	// The value parameter in the Attachment annotation
	// of Allure can take both hardcoded and parameterized
	// values. Hard-coded values could be provided directly
	// as String enclosed in double-quotes and parameterized
	// values could be provided like {0}, {1} etc in which
	// 0 means 1st parameter in the function below this annotation,
	// 1 means 2nd parameter in the function below this annotation
	// etc. Now, this annotation will take whatever will be the 
	// value for the parameters in the below functions via
	// this curly-brace format.
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	// Takes image screenshot of any test case of
	// your choice and Attach it to the report
	// using Attachment annotation of Allure
	@Attachment(value = "Page Screenshot", type = "image/png") // Syntax for adding images
	// to tests in Allure reports
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	// Attach a textual info/log to any test case of
	// your choice in the report using Attachment
	// annotation of Allure
	@Attachment(value = "{0}", type = "text/plain") // Syntax for adding plain text to
	// tests in Allure reports
	// Here, {0} means first parameter in the below method
	public static String saveTextLog(String message) {
		return message;
	}

	@Attachment(value = "{0}", type = "text/html") // Syntax for adding HTML text to
	// tests in Allure reports
	public static String saveHTML(String html) {
		return html;
	}

	@Override
	public void onStart(ITestContext iTestcontext) {
		System.out.println("I am in onStart method " + iTestcontext.getName());
	}

	@Override
	public void onFinish(ITestContext iTestcontext) {
		System.out.println("I am in onFinish method " + iTestcontext.getName());
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start!");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed!");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed!");
		Object testClass = iTestResult.getInstance();

		// Capturing the screenshot for failed test
		// and attaching in the report
		if (DriverFactory.getDriver() instanceof WebDriver) {
			System.out.println("Screenshot captured for test case: " + getTestMethodName(iTestResult));
			saveScreenshotPNG(DriverFactory.getDriver());
		}

		// Log an information about the failed test
		// in the plain text format in the report
		saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped!");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}
