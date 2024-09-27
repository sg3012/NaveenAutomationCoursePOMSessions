package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By phone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']");
	// Normalize space is an xpath function
	// that is used to eliminate any space
	// in the text written on the web elements.
	// Here the label element has a text " Yes" or " No" with
	// one space after it.
	
	private By continueBtn = By.xpath("//input[@value='Continue']");
	private By privacyPolicyCheckBox = By.name("agree");
	private By successMsg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public String getRegisterPageTitle() {
		return eleUtil.waitForTitleIs(AppConstants.REGISTER_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
	} 
	
	public boolean registerUser(String firstName, String lastName, String email, 
			String phone, String pwd, String subscribe) {
		
		eleUtil.waitForElementVisible(this.firstName, 
				AppConstants.MEDIUM_TIME_OUT).sendKeys(firstName);
		
		eleUtil.doSendKeys(this.lastName, lastName);
		
		eleUtil.doSendKeys(this.email,email);
		
		eleUtil.doSendKeys(this.phone,phone);
		
		eleUtil.doSendKeys(this.password,pwd);
		
		eleUtil.doSendKeys(this.confirmPassword,pwd);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(privacyPolicyCheckBox);
		
		eleUtil.doClick(continueBtn);
		
		String successMsg = eleUtil.waitForElementVisible(this.successMsg, 
				AppConstants.MEDIUM_TIME_OUT).getText();
		
		System.out.println(successMsg);
		
		if(successMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MSG)) {
			eleUtil.doClick(logoutLink); // Logout
			// from the application after fetching
			// and validating the success msg.
			// So, that the control is immediately
			// on the Register page when
			// script is ready to create a new user
			eleUtil.doClick(registerLink);
			// Click on the register link to navigate to register page
			// after log out because we don't land on the register page
			// just after user logs out.
			return true;
		}
		
		return false;
	}

}
