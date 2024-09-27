package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productResults = By.cssSelector("div.product-layout");

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getSearchResultsCount() {
		return eleUtil.waitForElementsVisible(productResults, AppConstants.MEDIUM_TIME_OUT).size();
	}
	
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.clickElementWhenReady(By.linkText(productName), AppConstants.MEDIUM_TIME_OUT); 
		// We are creating the dynamic locator as above because
		// the product name displayed on the tile wil
		// be different for every product in the search
		// result. So, we shouldn't create locator
		// at the class level using hard-coded product
		// names as we will have to maintain many 
		// locators if we have to select a number of
		// products ( let's say if we have to select
		// 10 products then we have to maintain 10 locators).
		return new ProductInfoPage(driver);
		
	}
	
	
	
}
