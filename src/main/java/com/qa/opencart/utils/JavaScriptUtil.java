package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	// This class shows Generic utility functions
	// for automating user actions using JAVASCRIPTEXECUTOR
	// Interface of Selenium
	
	// We will utilize the flash method of this class in
	// the ElementUtil utility because the actual 
	// and generic code for interacting with any web element
	// is written inside this utility. So, we will utilize
	// the flash method at respective places only in this
	// utility and then call the method(s) inside the respective
	// page classes instead of writing the repetitive 
	// flash method calls again and again in the page classes.

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) this.driver; // We have used driver via this keyword here
												// to use the value of class level global driver variable
												// instead of the local method level driver variable.
	}

	// Generic function to get the title of the current focused window
	// by using JavaScriptExecutor features.
	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}

	// Generic function to go back from the current web page
	// by using JavaScriptExecutor features.
	public void goBackWithJS() {
		js.executeScript("history.go(-1);");
	}

	// Generic function to go forward from the current web page
	// by using JavaScriptExecutor features.
	public void goForwardWithJS() {
		js.executeScript("history.go(1);");
	}

	// Generic function to refresh the current web page
	// by using JavaScriptExecutor features.
	public void pageRefreshWithJS() {
		js.executeScript("history.go(0);");
	}

	// Generic method to generate simple JS Alert on a web page
	// using JavascriptExecutor
	public void generateJSAlert(String msg) {
		js.executeScript("alert('" + msg + "');");
		try {
			Thread.sleep(1600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// DONOT FORGET TO ACCEPT OR DISMISS THE POP-UP (LIKE BELOW) AS IT IS GENRATED
		// BECAUSE IT WILL BLOCK YOUR AUTOMATION

		driver.switchTo().alert().accept();
	}

	// Generic method to generate simple JS Alert on a web page
	// using JavascriptExecutor
	public void generateJSConfirm(String msg) {
		js.executeScript("confirm('" + msg + "');");
		try {
			Thread.sleep(1600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// DONOT FORGET TO ACCEPT OR DISMISS THE POP-UP (LIKE BELOW) AS IT IS GENRATED
		// BECAUSE IT WILL BLOCK YOUR AUTOMATION

		driver.switchTo().alert().accept();
	}

	// Generic method to generate simple JS Alert on a web page
	// using JavascriptExecutor
	public void generateJSPrompt(String msg, String promptInputString) {
		js.executeScript("prompt('" + msg + "');");
		try {
			Thread.sleep(1600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Alert alert = driver.switchTo().alert();
		alert.sendKeys(promptInputString);

		// DONOT FORGET TO ACCEPT OR DISMISS THE ALERT (LIKE BELOW) AS IT IS GENRATED
		// BECAUSE IT WILL BLOCK YOUR AUTOMATION

		alert.accept();
	}

	// Generic method to display all the textual
	// content on a web page using JavascriptExecutor feature
	public String getPageInnerTextByJS() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	// Generic method to scroll to the bottom of the page
	// using JavaScriptExecutor feature
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		// The parameter to executeScript method in double quotes is the actual
		// javascript code to scroll to the bottom of the page
	}

	// We can scroll to the bottom of the page using the JS code
	// in the above scrollPageDown method only
	// to a page where the page height is fixed i.e, static height and there is an
	// end to the height
	// i.e., the bottom.
	// For the pages having infinite height or infinite scrolling
	// like linkedin feed, facebook feed, zomato feed etc. we cannot use this
	// method.

	// Generic method to scroll to the top of the page
	// using JavaScriptExecutor feature
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
		// The parameter to executeScript method in double quotes is the actual
		// javascript code to scroll to the bottom of the page
	}

	// Generic method to scroll down to a specific height (LESS THAN BOTTOM)
	// of the page using JavaScriptExecutor feature
	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0,'" + height + "')");
	}

	// Generic method to scroll down to the middle of the page height
	// of the page using JavaScriptExecutor feature
	public void scrollPageDownTheMiddle() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2)");
	}

	// Generic method to scroll page up to a specific element
	// until its visible
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		// Here the index number 0 in the arguments[] array
		// in the JS code present in double quotes above
		// corresponds to the first parameter passed after
		// comma in the complete body of executeScript() method,
		// which is actually the second parameter of executeScript(). If we pass the
		// index
		// number as 1 it will correspond to the 2nd parameter
		// after comma and so on.
	}

	// Generic method to zoom In/Out on a web page
	// on Chrome, Edge and Safari browsers only.
	public void zoomChromeEdgeSafari(String zoomPercentage) {
		js.executeScript("document.body.style.zoom = '" + zoomPercentage + "%'");
	}

	// Generic method to zoom In/Out on a web page
	// on Firefox browser only.
	public void zoomFirefox(String zoomPercentage) {
		js.executeScript("document.body.style.MozTransform = 'scale("+zoomPercentage+")'");
	}
	
	// USE CASES OF ZOOM IN/OUT :
	// We can use this feature in the case where we are doing automating scenarios during 
	// responsive testing on a web app and want to check if the elements have proper UI
	// or not when user zooms in/out a page.
	
	// Generic method to draw border over any web element on a page
	// with a border 3 pixels wide and Red in color
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	// Generic method to highlight/flash an element with
	// different colors than its background color
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		// getcssValue() method in selenium fetches the CSS properties
		// of a web element like backgroundColor, height, width, font-family etc.
		// The backgroundColor property value will be returned in the form
		// of rgb format like rgb(0,255,0) not the color name like 'Red'.
		
		// start a loop to interate through the changeColor
		// method and change the color of the desired WebElement
		// any number of times
		for(int i = 0; i<10; i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgcolor, element);
		}
	}
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"'", element);
		// Above parameter in the executeScript method in double
		// quotes is the syntax for changing color of an element.
		try {
			Thread.sleep(20);
		}
		catch(InterruptedException e) {
			
		}
	}
	
	// USE CASES OF DRAW BORDER OVER AN ELEMENT:
	// We can use this feature when a test is failed and we want to
	// take a screenshot highlighting the element which is
	// causing the test to failed.
}
