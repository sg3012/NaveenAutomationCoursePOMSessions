package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

// Syntax for writing listeners at the
// test class level
//@Listeners(ExtentReportListener.class)


@Epic("EPIC - 100: Design of the login page for open cart app")// Epic is the annotation
// provided by allure reports to define the Epics as per Agile methodology.
// These are the same Epics we create in our project mgmnt. tools like
// JIRA or Quality center or Azure etc. This Epic could be used as a category
// in the allure reports under which stories will display
@Story("US - 200: Implement login page features for open cart app")
//Story is the annotation
//provided by allure reports to define the Stories as per Agile methodology.
//These are the same Stories we create in our project mgmnt. tools like
//JIRA or Quality center or Azure etc. This Story could be used as a category
//in the allure reports under which test cases will display

// Stories and Epic annotations should be defines at the class
// level

public class LoginPageTest extends BaseTest {

	// This class explains how to design
	// test cases using TestNG in automation
	// framework

	@Description("login page title test......") // Description
	// is another annotation given by Allure reports which we could add
	// to our test cases. This is the same description which we give
	// to our test case in any test case mgmnt. tool.
	@Severity(SeverityLevel.NORMAL) // Severity annotation
	// is also provided by Allure reports. This is the 
	// same Severity which we give to our test case in 
	// any test case mgmnt. tool. 
	// We should define the annotations severity and description
	// at the test case level
	@Test (priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	// DEFINING RETRY LOGIC AT TEST LEVEL
	// The code written after comma in the test annotation below
	// is what we call as retry listener logic at the test level
//	@Test (priority = 1, retryAnalyzer = com.qa.opencart.listeners
//	.Retry.class)
//	public void loginPageTitleTest() {
//		String actTitle = loginPage.getLoginPageTitle();
//		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
//	}
	
	@Description("login page url test......")
	@Severity(SeverityLevel.NORMAL)
	@Test (priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("check forgot pwd link exists on login page......")
	@Severity(SeverityLevel.CRITICAL)
	@Test (priority = 3)
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@DataProvider
	public Object[][] getNegativeLoginTestData() {
		return ExcelUtil.getTestData(AppConstants.LOGIN_SHEET_NAME);
	}
	
//	@Test (priority = 4, dataProvider = "getNegativeLoginTestData", enabled = false)
//	public void negativeLoginTest(String userName, String password) {
//		String actErrMsg = loginPage.doNegativeLogin(userName, password);
//		Assert.assertTrue(actErrMsg.contains("Warning: No match for E-Mail Address and/or Password"));
//	}
	
	@Description("check user is able to login to open cart app with valid credentials......")
	@Severity(SeverityLevel.BLOCKER)
	@Test (priority = 5)
	public void loginTest() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accountsPage.isLogoutLinkExist(), true);
	}
	
	@Description("checking naveen test")
	@Severity(SeverityLevel.MINOR)
	@Test (priority = 6)
	public void naveenTest() {
		Assert.assertEquals(true,false);
	}
	
}
