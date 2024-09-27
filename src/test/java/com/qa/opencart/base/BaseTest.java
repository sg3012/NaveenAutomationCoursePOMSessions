package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	
	// This class defines the cross-browser logic 
	// to open browsers in different cases along with
	// initializing the WebDriver.
	
	WebDriver driver;
	protected Properties prop; // We have declared the properties
	// reference at the global/class level so that we could use this
	// reference variable to read the properties
	// file's values in the classes apart from this class
	// Which are assigned to "prop" variable using "initProp"
	// method of DriverFactory class.
	// For e.g. - to read username and password values
	// from the properties file in LoginPageTest class.
	protected LoginPage loginPage; // We have made loginPage
	// reference as protected because we want to inherit this
	// property in the BaseTest's child classes which
	// are located outside the BaseTest's package which
	// only "protected" access modifier could give.
	protected AccountsPage accountsPage;
	protected SearchResultsPage searchResPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	DriverFactory df;
	
	protected SoftAssert softAssert; // We
	// have created the softassert class reference variable
	// here but not for Assert class because soft assert
	// class requires it's object to be created to access it's
	// method but all the methods of Assert class are static in
	// nature so they don't require an object to access them. 
	// Also, we have made the SoftAssert ref. as protected
	// so that we can access it inside the child classes
	// of BaseTest in a different than baseTest.
	@BeforeTest
	public void setUp() {
		df = new DriverFactory();
		prop = df.initProp();
		driver = df.initDriver(prop); 
		loginPage = new LoginPage(driver); // We have initialized the login
		// page constructor in the Base class because when
		// the frameworks starts executing LoginPage will be
		// the very first page to be invoked and it requires
		// driver reference to be initialized with correct value
		// so that when we call LoginPage methods for user actions
		// using Selenium , it doesn't give us NPE.
		// Also, we can't create and initialize login page
		// constructor in the LoginPage test because if we
		// do so we need webdriver reference to be initialized
		// with correct value which is there in the BaseTest not in
		// LoginPageTest.
		softAssert = new SoftAssert();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
