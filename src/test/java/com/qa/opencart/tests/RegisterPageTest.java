package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	// Setup method for performing
	// tests on register page
	@BeforeClass
	public void regSetUp() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	// Below method will generate random/unique
	// email IDs using current system time
	// differing by milliseconds
	public String getRandomEmailId() {
		return "openauto" + System.currentTimeMillis() + "@open.com";
		// currentTimeMillis is a method that returns
		// a new system time in milliseconds
		// every time it is called.
	}

	// Dataprovider supplying the data
	// to the tests directly from within
	// the test class
//	@DataProvider
//	public Object[][] getUserRegData() {
//		return new Object[][] {
//			{"Pooja", "Jain", "9090909090", "test@1234", "yes"},
//			{"Shubham", "Goel", "9090909011", "shubh@1234", "no"},
//			{"Mitaj", "Karthik", "9090909012", "Mitaj@1234", "yes"}
//		};
//	}

	// Dataprovider supplying the data
	// to tests from the excel sheet
	@DataProvider
	public Object[][] getUserRegSheetData() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		// This method from ExcelUtil
		// is fetching the data from the excel sheet
	}

//	// Test case fetching the data from the dataprovider
//	// written directly in the test class
//	@Test(dataProvider = "getUserRegData")
//	public void userRegisterTest(String firstName, String lastName, String phone, 
//			String pwd, String subscribe) {
//		Assert.assertTrue(registerPage.registerUser(firstName, lastName, 
//				getRandomEmailId(), phone, pwd, subscribe));
//	}

	// Test case fetching the data from the dataprovider
	// fetching the data from Excel sheet(s)
	@Test(dataProvider = "getUserRegSheetData")
	public void userRegisterTest(String firstName, String lastName, String phone, 
			String pwd, String subscribe) {
		Assert.assertTrue(registerPage.registerUser(firstName, lastName, 
				getRandomEmailId(), phone, pwd, subscribe));
	}
}
