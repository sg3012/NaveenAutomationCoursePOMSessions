package com.qa.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	// This class explains how we could
	// implement the Retry logic for our failed
	// test cases in our automation framework

	// [INTERVIEW]: Which is the listener that
	// listens to the failed tests and retries them?
	// IRetryAnalyzer is the interface/listener which provides
	// Retry function to implement Retry logic in the framework.

	// We could implement the retry logic in 2 ways:
	// 1. At test level (before every test annotation in the test class)
	// 2. At run time ( when running the framework as a whole)

	int counter = 0; // Starting point when retries have not started yet
	int retryLimit = 3; // maximum retries limit

	@Override
	public boolean retry(ITestResult iTestresult) {
		if (!iTestresult.isSuccess()) { // Check if test is not succeeded
			if (counter < retryLimit) { // check if retryLimit count is reached
				counter++; // increase the counter by 1
				iTestresult.setStatus(ITestResult.FAILURE); // Mark the test as Failed
				return true; // tells TestNG to re-run the test
			}
			else {
				iTestresult.setStatus(ITestResult.FAILURE); // if retryLimit is reached, test
				// is marked as failed
			}
		}
		else {
			iTestresult.setStatus(ITestResult.SUCCESS); // if test passes, TestNG marks it as passed
		}
		return false;
	}

}
