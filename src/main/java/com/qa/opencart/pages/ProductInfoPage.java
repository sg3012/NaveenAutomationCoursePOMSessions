package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails li");
	private By productQty = By.name("quantity");
	private By addToCartBtn = By.id("button-cart");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	// We have used index based xpaths for productMetaData and productPriceData
	// because there are very less chances that the positions
	// of product meta data and price will change. Meaning,
	// the change in positions is less frequent and
	// meta data will display at 1st position and 
	// price will display at the 2nd position most of the time.
	private Map<String,String> mapOfAllProdData; 

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		return eleUtil.doElementGetText(productHeader);
	}
	
	public int getProductImagesCount() {
		int actProductImagesCount = eleUtil.waitForElementsVisible(productImages, 
				AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("total product images for: " 
				+ getProductHeaderValue() + "===>" + actProductImagesCount);
		return actProductImagesCount;
	}
	
	// Below is the metaData we have to Split:
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
	// If a user calls this method directly, he will get NPE
	// because the reference variable "mapOfAllProdData"
	// to hashMap is pointing to null because it is
	// initially pointing when declared at the class level
	// as no object for HashMap has been created and initalized.
	// So, to avoid NPE we can call getProductData method
	// and make this method as private.
	private void getProductMetaData() {
		List<WebElement> prodMetaDataEleList = eleUtil.waitForElementsVisible(productMetaData, 
				AppConstants.MEDIUM_TIME_OUT);
		// Map<String,String> mapOfMetaData = new HashMap<String,String>();
		for(WebElement e : prodMetaDataEleList) {
			String metaTxt = e.getText();
			String key = metaTxt.split(":")[0].trim(); // We have
			// used the trim method here to remove/trim one extra space 
			// (if they introduce in future) and be on the safer side
			// after the value displayed before colon, in the above
			// metaData that we are splitting.
			String value = metaTxt.split(":")[1].trim(); // We have
			// used the trim method here to remove/trim one extra space
			// before the value displayed after colon in the above
			// metaData that we are splitting.
			mapOfAllProdData.put(key, value);
		}
		// return mapOfMetaData;
	}
	
	// This is the price data we have to split:
	// $2,000.00
	// Ex Tax: $2,000.00
    // We could declare this method also as private
	// to avoid NPE
	private void getProductPricingData() {
		List<WebElement> prodPriceEleList = eleUtil.waitForElementsVisible(productPriceData, 
				AppConstants.MEDIUM_TIME_OUT);
		// Map<String,String> mapOfPriceData = new HashMap<String,String>();
		
		// So, as we have only 2 elements in the product price data
		// we will not use a loop here. Instead we will 
		// use the individual elements one by one ( 1st we use $2,000.0
		// and then we use Ex Tax: $2,000.00) and work with them like below:
		
		// For the 1st element (0th one) we will capture it from the list
		// using get() and then capture it's
		// text using getText() directly
		// and store it in a variable trimming any leading or
		// trailing spaces, because we don't have a colon
		// here to split() it.
		String actPrice = prodPriceEleList.get(0).getText().trim();
		
		// For the 2nd element (2nd one) we will capture it from the list
		// using get(), get it's text using getText(), split it
		// on the basis of colon, store the values before and after
		// colon in the respective variables trimming any leading or
		// trailing spaces.
		String extTax = prodPriceEleList.get(1).getText().split(":")[0].trim();
		String extTaxValue = prodPriceEleList.get(1).getText().split(":")[1].trim();
		
		// Put the String values of 1st and 2nd elements
		// in the key and value format in the map
		// we have defined
		mapOfAllProdData.put("price", actPrice); // In
		// cases where we don't have a key
		// for a corresponding to be stored in a Map
		// then we could decide our own custom key
		// like we have done for the value actPrice.
		
		mapOfAllProdData.put(extTax, extTaxValue);
		
		// return mapOfPriceData;
	}
	
	// This Method will give
	// all the product data including product name, meta data,
	// image count and price at once
	public Map<String,String> getProductdata() {
		mapOfAllProdData = new HashMap<String, String>(); 
		// HashMap will not return the values in the same order in which they were
		// entered. To get the data in the same order in which it
		// was entered we will use the LinkedHashMap like below:
		
		// mapOfAllProdData = new LinkedHashMap<String, String>();
		
		// If we want to maintain the alphabetical order
		// of inserted data the we have to use Tree map like below:
		// NOTE: TreeMap sorts the data on the basis of 
		// keys in ascending order by default i.e.
		// the key which is coming 1st alphabetically will
		// be displayed first along with the corresponding value
		// and then key and it's value coming after the previous
		// key
		// mapOfAllProdData = new TreeMap<String, String>();
		
		// Store the product title/name also in the map
		mapOfAllProdData.put("productheader", getProductHeaderValue()); // As
		// we don't have a key for product name also, so we have defined
		// our own custom key
		mapOfAllProdData.put("productimages", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPricingData();
		return mapOfAllProdData;
	}
}
