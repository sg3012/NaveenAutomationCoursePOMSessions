package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
    private By logoutLink = By.linkText("Logout");
    private By accntHeaders = By.cssSelector("div#content h2"); 
	private By searchTxtBox = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccountsPageTitle() {
		return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 
				AppConstants.SHORT_TIME_OUT );
	}
	
	public String getAccountsPageURL() {
		return eleUtil.waitForURLContains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION
				, AppConstants.SHORT_TIME_OUT);
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, 
				AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeader() {
		List<WebElement> headersEleList = eleUtil.waitForElementsVisible(accntHeaders, 
				AppConstants.MEDIUM_TIME_OUT);
		List<String> headersValueList = new ArrayList<String>();
		for(WebElement e : headersEleList) {
			String header = e.getText();
			headersValueList.add(header);
		}
		System.out.println("Actual Acc Page Headers are ===>" + headersValueList);
		return headersValueList;
	}
	
	public int getAccountsPageHeaderCount() {
		return eleUtil.waitForElementsVisible(accntHeaders, 
				AppConstants.MEDIUM_TIME_OUT).size();
	}
	
	public SearchResultsPage doSearch(String searchKey){
		WebElement searchBoxEle = eleUtil.waitForElementVisible(searchTxtBox, 
				AppConstants.MEDIUM_TIME_OUT);
		searchBoxEle.clear();
		searchBoxEle.sendKeys(searchKey);
		eleUtil.doClick(searchIcon);
		// Return the search page object
		// because we are landing on the
		// search page after performing 
		// search on accounts page
		
		return new SearchResultsPage(driver);
		// This is also called Test
		// Driven Development (TDD) because
		// we will create/develop new Page classes
		// as soon as we return that page class
		// object from existing class
		// and now we want to write Tests for new
		// Page class. Meaning, develop new pages
		// as soon as you require new test cases to
		// be written.
	}
}
