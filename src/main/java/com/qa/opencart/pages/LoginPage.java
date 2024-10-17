package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	// This class explains how to design the page
	// classes in an automation framework

	// Page classes are not responsible for creating and
	// initializing the WebDrivers. They just contains page
	// actions and some other components. The responsibility
	// of initializing the webDriver is with DriverFactory
	// JAVA class.

	// Page classes are also called PAGE LIBRARIES or PAGE OBJECT.

	// Page class's variables are declared as private to prevent
	// any other class apart from the page class itself from
	// manipulating those variables.
	
	// DO NOT extend BaseTest in Page classes (like LoginPage)
	// because the properties BaseTest class has are
	// only useful for Test classes extending it i.e.,
	// every page test ( like LoginPageTest ) 
	// is a type of test or BaseTest but
	// not every Page is a Test. So,
	// creating inheritance between
	// Page and BaseTest is Unnecessary.

	private WebDriver driver;
	private ElementUtil eleUtil;

	// We need following things / components
	// in the Page classes:

	// 1. private By locators - for web page elements
	private By emailID = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By invalidLoginErrMsg = By.cssSelector("div.alert.alert-danger");
	

	// 2. public Page Constructor - for creating page object using driver reference
	// to access page methods along with creating and initializing ElementUtil Object
	// with driver
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. public Page actions / methods - show casing the user actions on a web page
	@Step("....getting login page title....") // This step annotation is coming from allure reports.
	// This annotation corresponds to every test step in a test case.
	// Because this annotation defines test steps that is why we
	// should write this annotation at the page methods' level 
	// in the page classes as every page method defines a page action/test step
	// in a test case.
	public String getLoginPageTitle() {
		// String title = driver.getTitle();
		
		// String title = eleUtil.waitForTitleIs("Account Login", 5); // This Login
		// page title (given in double quotes) and the time out value (5) 
		// are kind of CONSTANTS which are rarely going to be changed.
		// So, we will remove these hard-coded values
		// and initialize these constants in AppConstants.java class/file and
		// write them like below:
		
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, 
				AppConstants.SHORT_TIME_OUT);
		
		System.out.println("Login Page Title is: " + title);
		
		return title;
	}

	@Step("....getting login page url....")
	public String getLoginPageURL() {
//		String url = driver.getCurrentUrl();
		
		//String url = eleUtil.waitForURLContains("route=account/login", 5);
		// This Login
		// page URL fraction (given in double quotes) and timeout value (5) 
		// are kind of CONSTANTS which are rarely going to be changed.
		// So, we will remove these hard-coded values
		// and initialize these constants in AppConstants.java class/file and
		// write them like below

		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, 
				AppConstants.SHORT_TIME_OUT);
		
		System.out.println("Login Page URL is: " + url);
		
		return url;
	}

	@Step("....is forgot pwd link exist or not....")
	public boolean isForgotPwdLinkExist() {
		// return driver.findElement(forgotPwdLink).isDisplayed();
		
		//return eleUtil.waitForElementVisible(forgotPwdLink, 10).isDisplayed();
		// This timeout value (10) is also a kind of CONSTANT
		// and rarely going to change. SO, we will also
		// remove this hard-coded value from here
		// and define it in AppConstants.java and write it like
		// below:
		
		return eleUtil.waitForElementVisible(forgotPwdLink, 
				AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("....login to app with username: {0} and password: {1}....")
	// These parameters passed in the step annotation are again provided
	// under allure reports which correspond to the index of the parameters
	// of the method above which the step is written. Meaning, 0
	// corresponds to 1st parameter, 1 corresponds to 2nd parameter and 
	// so on.
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("App creds are: " + userName + ":" + pwd);
		//eleUtil.waitForElementVisible(emailID, 10).sendKeys(userName);
		
		eleUtil.waitForElementVisible(emailID,
				AppConstants.MEDIUM_TIME_OUT).sendKeys(userName);
		
		eleUtil.doSendKeys(password, pwd);
		
		eleUtil.doClick(loginBtn);
		
		return new AccountsPage(driver);
	}
	
	public String doNegativeLogin(String userName, String pwd) {
		System.out.println("App creds are: " + userName + ":" + pwd);
		//eleUtil.waitForElementVisible(emailID, 10).sendKeys(userName);
		eleUtil.waitForElementVisible(emailID,
				AppConstants.MEDIUM_TIME_OUT).clear();
		
		eleUtil.waitForElementVisible(emailID,
				AppConstants.MEDIUM_TIME_OUT).sendKeys(userName);
		
		eleUtil.getElement(password).clear();
		
		eleUtil.doSendKeys(password, pwd);
		
		eleUtil.doClick(loginBtn);
		
		String errMsg = eleUtil.waitForElementVisible(invalidLoginErrMsg, 
				AppConstants.SHORT_TIME_OUT).getText();
		
		return errMsg;
	}
	
	@Step("....navigating to the register page....")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementVisible(registerLink, 
				AppConstants.SHORT_TIME_OUT).click();
		
		return new RegisterPage(driver);
	}
	
//	public ForgotPwdPage navigateToForgotPwdPage() {
//		eleUtil.waitForElementVisible(forgotPwdLink, 
//				AppConstants.SHORT_TIME_OUT).click();
//		return new ForgotPwdPage();
//	}

}
