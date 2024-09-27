package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//import customexception.FrameWorkException;

public class ElementUtil {

	// This class provided some generic functions
	// to interact (perform user actions) on the
	// elements displayed on a web page like click,
	// sendKeys, drag and drop etc.

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) { // created constructor of this class
											// to supply the driver value
											// to the methods created below
		this.driver = driver;
		this.act = new Actions(driver);
		this.jsUtil = new JavaScriptUtil(driver);
	}

	// Generic function to find an element
//	public WebElement getElement(By locator) {
//		WebElement element = null;
//
//		// Try-catch block to check if an element
//		// is found by supplied locator or not.
//		// If not the function will throw exception
//		// else it will return required element.
//		try {
//			element = driver.findElement(locator);
//		} catch (NoSuchElementException e) {
//			System.out.println("getting element exception...plz check your locator");
//			e.printStackTrace();
//			try {
//				// Wait for the element for few seconds
//				Thread.sleep(2000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//
//			// try for the element again
//			element = driver.findElement(locator);
//		}
//
//		return element;

	// WE DON'T REQUIRE THIS TRY-CATCH BLOCK WAY TO WAIT
	// FOR THE ELEMENT AND CREATE IT WHEN AVAILABLE NOW
	// BECAUSE WE HAVE ALREADY IMPLEMENTED EXPLICIT WAIT
	// UTIL TO FIND ELEMENT (See new getElement
	// with highlight condition below)
//	}

	/**
	 * Generic method to find an element and highlight if the highlight condition is
	 * true
	 * 
	 * @param locator
	 * @return The desired web element
	 */
	@Step("....getting element for locator: {0}....")
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	// Generic function to input string in any plain text fields
	@Step("....entering value {1} in locator: {0}....")
	public void doSendKeys(By locator, String keys) {
		// call the getElement function here
		// to stop the repetitive to calls to it
		// in the actual automation script.

		if (keys == null) {
			System.out.println("Value cannot be null while using sendKeys method");
			// throw new FrameWorkException("VALUECANNOTBENULL");
		}
		getElement(locator).sendKeys(keys);
	}

	// Generic function to click on any webelement
	public void doClick(By locator) {
		getElement(locator).click();
	}

	// Generic function to fetch the text on a web element
	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text is ====> " + eleText);
		return eleText;
	}

	// Generic method to create web element using Link's text
	public WebElement getLinkElementByText(String linkText) {
		return driver.findElement(By.linkText(linkText));
	}

	// Generic method to check whether an element is displayed on a web page or not
	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	// Generic method to fetch and HTML element's attribute value
	public String getElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	// Generic function to check if a Web Element is disabled or not:
	public boolean checkElementIsDisabled(By locator) {
		String disabledValue = getElement(locator).getAttribute("disabled");
		if (disabledValue.equals("true")) {
			return true;
		}
		return false;
	}

	// Generic function to get a collection of web elements
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	// Generic function to fetch count of a collection of elements
	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	// Generic function to get a List of the text displayed on a list of web
	// elements (FOR SIMILAR KIND OF ELEMENTS ONLY)
	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	// Generic function to get a List of any attribute value displayed for a list of
	// web
	// elements (FOR SIMILAR KIND OF ELEMENTS ONLY)
	public List<String> getElementsAttrList(By locator, String attr) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String attrVal = e.getAttribute(attr);
			if (!attrVal.isEmpty()) {
				eleAttrList.add(attrVal);
			}
		}
		return eleAttrList;
	}

	// Generic method to find all links on a page and click on the Desired Link
	public void clickOnLink(By locator, String linkText) {
		List<WebElement> linksList = getElements(locator);

		System.out.println("Total number of links:" + linksList.size());

		for (WebElement e : linksList) {

			String text = e.getText();

			System.out.println(text);

			if (text.equals(linkText)) {

				e.click();

				break; // this break is applied here because
						// once the Marathi link on the page is clicked,
						// the page will be refreshed and
						// selenium will not be able to find other link elements
						// and gives StaleElementReferenceException
			}
		}

	}

	// Generic function to search in any search box and click
	// on the desired link from the Suggestions list that is displayed
	public void doSearchAndClick(By searchlocator, By searchSuggLocator, String searchKeyword, String suggToClick)
			throws InterruptedException {
		doSendKeys(searchSuggLocator, searchKeyword);

		Thread.sleep(4000); // ALWAYS put wait after entering the search keyword
		// whenever you are automating
		// a search box and you have to click from the suggestions list.
		// But the suggestions are coming after a few seconds.

		List<WebElement> suggestionsList = getElements(searchSuggLocator);

		System.out.println(suggestionsList.size());

		for (WebElement e : suggestionsList) {
			String suggestionText = e.getText();
			System.out.println(suggestionText);
			if (suggestionText.contains(suggToClick)) {
				e.click();
				break;
			}
		}
	}

	// Generic function to find whether an element is
	// mandatory on a web page or not
	public boolean checkElementIsMandatory(String elementLabel) {
		String script = "return window.getComputedStyle(document" + ".querySelector(\"label[for='input-" + elementLabel
				+ "']\"),'::before')" + ".getPropertyValue('content')";

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String mandatoryStarText = jse.executeScript(script).toString();
		System.out.println(mandatoryStarText);
		if (mandatoryStarText.contains("*")) {
			return true;
		}
		return false;
	}

	// ************************* Drop Down Utils **************************//

	// Generic custom function to select a value from select drop-down using index
	// of the element
	public void doSelectDropdownByIndex(By locator, int index) {
		if (index < 0) {
			System.out.println("Pls pass the right (+ve) index");
			return; // only writing return in any code block and return type void
					// means the same in JAVA. Simply writing return means return nothing
					// i.e, void and come out of the function body
		}

		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	// Generic custom function to select a value from select drop-down using visible
	// text of the element
	public void doSelectDropdownByVisibleText(By locator, String visibleText) {
		if (visibleText == null) {
			System.out.println("Pls pass the right visible text and it cannot be null");
		}

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	// Generic custom function to select a value from select drop-down using value
	// attribute of the element
	public void doSelectDropdownByValue(By locator, String valueAttr) {
		if (valueAttr == null) {
			System.out.println("Pls pass the right value of value attribute. It cannot be null");
		}

		Select select = new Select(getElement(locator));
		select.selectByValue(valueAttr);
	}

	// Generic function to get the count of values from a drop-down
	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	// Generic function to get all the actual values from a drop-down in a list
	public List<String> getDropdownTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsElementList = select.getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionsElementList) {
			String optionValue = e.getText();
			System.out.println(optionValue);
			optionsTextList.add(optionValue);
		}
		return optionsTextList;
	}

	// Generic method to click any value in a drop-down but
	// WITHOUT using any select class methods
	public void doClickDropDownValue(By locator, String dropDownValue) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	// Generic method to click any value in a drop-down but
	// WITHOUT using the select class at all
	public void doClickDropDownValueUsingLocator(By locator, String dropDownValue) {
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	// ************************** Actions Utils ***************************//

	// Generic method to click on an element using actions class
	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	// Generic method to enter value in an I/P field using actions class
	public void doActionsSendKeys(By locator, String keys) {
		act.sendKeys(getElement(locator), keys).perform();
	}

	// Generic method to right click on an item , open a context menu
	// and click on any of the menu items
	public void clickOnRightClickMenuItem(By contextMenuLocator, String menuItemValue) {
		act.contextClick(getElement(contextMenuLocator)).perform();
		By menuItemLocator = By.xpath("//*[text()='" + menuItemValue + "']");
		// We are using * instead of htmltag
		// in this xpath because * in xpath
		// means an element with any type
		// of html tag but with certain
		// properties
		// So, here this xpath interprets all the elements that having a text
		// irrespective of
		// the htmltag.
		doClick(menuItemLocator);
	}

	// Generic method to handle exactly 2 level menus like header navigation
	// and perform actions like click, mouse hover the sub-menu links etc.
	public void multiLevelMenuHandling(By level1MenuLocator, By level2MenuLocator) throws InterruptedException {
		act.moveToElement(getElement(level1MenuLocator)).perform();
		Thread.sleep(1500);
		doClick(level2MenuLocator);
	}

	// Generic method to handle exactly 4 level menus like header navigation
	// and perform actions like click, mouse hover the sub-menu links etc.
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4)
			throws InterruptedException {
		getElement(level1Locator).click();
		Thread.sleep(1500);
		act.moveToElement(getLinkElementByText(level2)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkElementByText(level3)).perform();
		Thread.sleep(1500);
		getLinkElementByText(level4).click();
	}

	// Generic method to handle exactly 3 level menus like header navigation
	// and perform actions like click, mouse hover the sub-menu links etc.
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException {
		getElement(level1Locator).click();
		Thread.sleep(1500);
		act.moveToElement(getLinkElementByText(level2)).perform();
		Thread.sleep(1500);
		getLinkElementByText(level3).click();
	}

	// ********************* WAIT Utils************************//

	/**
	 * Generic function to wait for an element based on if its present in the HTML
	 * code (DOM) of a page or not, irrespective of whether its visible on the page
	 * or not
	 * 
	 * @param locator
	 * @param timeOut
	 * @return WebElement
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Generic function to wait for an element based on if its present in the HTML
	 * code (DOM) of a page or not, irrespective of whether its visible on the page
	 * or not.It will wait till the waiting timeout and keep on trying to locate the
	 * element after the time interval given as pollingTime.
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return WebElement
	 */
	public WebElement waitForElementPresence(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Generic function to wait for an element based on if its visible on the page
	 * or not. Visible on page means the element is not only displayed on the page
	 * but also have a height and width should be greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return WebElement
	 */
	@Step("....waiting for element is visible for locator: {0} with timeout: {1}....")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	/**
	 * Generic function to wait for an element based on if its visible on the page
	 * or not. It will wait till the waiting timeout and keep on trying to locate
	 * the element after the time interval given as pollingTime. Visible on page
	 * means the element is not only displayed on the page but also have a height
	 * and width should be greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return WebElement to be located
	 */
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Generic method to wait for some part of the right title to appear and return
	 * it
	 * 
	 * @param partOfTitle
	 * @param timeOut
	 * @return String Title
	 */

	public String waitForTitleContains(String partOfTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(partOfTitle))) {
				return driver.getTitle();
			} else {
				System.out.println(partOfTitle + " title value is not present......");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(partOfTitle + " title value is not present......");
			return null;
		}
	}

	/**
	 * Generic method to wait for whole part of the right title to appear and return
	 * it
	 * 
	 * @param titleValue
	 * @param timeOut
	 * @return String Title
	 */
	@Step("....waiting for the title and capture the title....")
	// We could also define the step annotations in
	// our generic utility methods also because the generic
	// utility methods also correspond to a step in a test case
	// like this one which is a generic method to wait
	// for a page title in a test case execution.
	public String waitForTitleIs(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle();
			} else {
				System.out.println(titleValue + " title value is not present......");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(titleValue + " title value is not present......");
			return null;
		}
	}

	/**
	 * Generic method to wait for some part of the right title to appear and return
	 * it
	 * 
	 * @param partOfUrl
	 * @param timeOut
	 * @return String URL
	 */
	@Step("....waiting for the page url and fetching it....url fraction: {0}")
	public String waitForURLContains(String partOfUrl, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(partOfUrl))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(partOfUrl + " URL value is not present......");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(partOfUrl + " URL value is not present......");
			return null;
		}
	}

	/**
	 * Generic method to wait for whole part of the right title to appear and return
	 * it
	 * 
	 * @param urlValue
	 * @param timeOut
	 * @return String URL
	 */
	public String waitForURLIs(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlValue + " URL value is not present......");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlValue + " URL value is not present......");
			return null;
		}
	}

	/**
	 * Generic method to wait for JS Alerts. It also creates, returns and switches
	 * to the alert automatically
	 * 
	 * @param timeOut
	 * @return Alert class type reference
	 */
	public Alert waitForJsAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Generic method to check if the number of browser window pop-ups expected to
	 * be opened after clicking a link are correct and actually opened or not
	 * 
	 * @param timeOut
	 * @param numberOfWindowsToBe
	 * @return true or false based on if the number of windows opened are matching
	 *         the expected number of windows to be opened or not.
	 */
	public boolean waitForNumberOfBrowserWindows(int timeOut, int numberOfWindowsToBe) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));
	}

	/**
	 * Generic method to wait for a frame element using its By locator value. This
	 * method also switches automatically to the frame if found
	 * 
	 * @param frameLocator
	 * @param timeOut
	 * @return nothing
	 */
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	/**
	 * Generic method to wait for a frame element using its integer index value.
	 * This method also switches automatically to the frame if found
	 * 
	 * @param frameIndex
	 * @param timeOut
	 * @return nothing
	 */
	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	/**
	 * Generic method to wait for a frame element using its string name or id value.
	 * This method also switches automatically to the frame if found
	 * 
	 * @param FrameNameorId
	 * @param timeOut
	 * @return nothing
	 */
	public void waitForFrameByFrameNameorId(String frameNameorId, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameorId));
	}

	/**
	 * Generic method to wait for a frame element using its WebElement value. This
	 * method also switches automatically to the frame if found
	 * 
	 * @param frameElement
	 * @param timeOut
	 * @return nothing
	 */
	public void waitForFrameByFrameElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	/**
	 * Generic function to wait for AT LEAST ONE web element on a web page based on
	 * if it is actually visible in a List on the web page or not. If it is there
	 * then only this method will return the complete list of web elements
	 * 
	 * @param locator
	 * @param timeOut
	 * @return List of WebElements
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * Generic function to wait for ALL the web elements on a web page based on if
	 * they ALL are present in a List in the HTML code (DOM) of a page as well as
	 * its visible on the page. If it is there then only this method will return the
	 * complete list of elements. Visibility means the elements are not only visible
	 * on the page but also have a height and width Greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return List of WebElements
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * Generic method to wait for an element to be visible as well as enabled on a
	 * web page and then click on it once it is visible and click-able.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return Nothing
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	// ********************** FluentWait Utils*****************************//

	/**
	 * Generic function to wait for an element using Fluent Wait mechanism based on
	 * if its present in the HTML code (DOM) of a page or not, irrespective of
	 * whether its visible on the page or not.It will wait till the waiting timeout
	 * and keep on trying to locate the element after the time interval given as
	 * pollingTime.
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return Webelement to perform some action
	 */
	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class) // ignoring
				// staleelementrefexception in case the page refreshes
				// and element becomes stale. Selenium will ignore the
				// exception, locate and recreate the element again automatically.
				.withMessage("-----time out is done.....element is not found....." + locator);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Generic function to wait for an element using Fluent Wait mechanism based on
	 * if its visible on the page or not. It will wait till the waiting timeout and
	 * keep on trying to locate the element after the time interval given as
	 * pollingTime. Visible on page means the element is not only displayed on the
	 * page but also have a height and width should be greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @param pollingTime
	 * @return Webelement to perform some action
	 */
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("-----time out is done.....element is not found....." + locator);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Generic method to wait for JS Alerts using Fluent Wait mechanism. It will
	 * wait till the waiting timeout and keep on trying to locate the element after
	 * the time interval given as pollingTime. It also creates, returns and switches
	 * to the alert automatically
	 * 
	 * @param timeOut
	 * @param pollingTime
	 * @return Alert type reference to perform some action
	 */
	public Alert waitForJsAlertWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoAlertPresentException.class)
				// Here we will ignore NoAlertPresentException instead of NoSuchElementException
				// because
				// when Alert is not present on a page and we are locating it we will get
				// NoAlertPresentException not NoSuchElementException
				.withMessage("-----time out is done.....JS Alert is not found.....");
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Generic method to wait for a frame element using Fluent Wait Mechanism based
	 * on its string name or id value. This method also switches automatically to
	 * the frame if found
	 * 
	 * @param FrameNameorId
	 * @param timeOut
	 * @param pollingTime
	 * @return nothing
	 */
	public void waitForFrameWithFluentWait(String frameNameorId, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchFrameException.class)
				.withMessage("-----time out is done...Frame is not found...with name or id: " + frameNameorId);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameorId));
	}

	// Generic method to wait for an input web element using Fluent Wait
	// Mechanism and enter value in it
	public void waitForElementAndEnterValue(By locator, int timeOut, int pollingTime, String value) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchFrameException.class)
				.withMessage("TimeOut is done...Element not found..." + locator)
				.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
	}

	// Generic method to wait for a web element using Fluent Wait
	// Mechanism and click on it
	public void waitForElementAndClick(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchFrameException.class)
				.withMessage("TimeOut is done...Element not found..." + locator)
				.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
	}

	// **************** Custom Wait *******************//

	// Generic method implementing Custom Fluent Wait like
	// mechanism to wait for an element and perform action
	// if found within the waiting time limit else throw exception
	// Default polling time is 500ms
	public WebElement retryingElement(By locator, int timeOut) {

		WebElement element = null; // Default value of an element will be null as it's
		// non-primitive type.

		int attempts = 0; // Variable to track how many attempts / retries
		// are performed while locating an element.

		while (attempts < timeOut) {
			try { // try for the element. If it is found in the first
					// attempt only then break try-catch and the loop in turn
					// else go to catch block and handle it accordingly.

				element = getElement(locator); // if element is immediately available here
				// then below line will be printed and loop will be break BUT if
				// element is not available in first attempt then this line
				// will throw NoSuchElementException. To handle that we will use try-catch
				// mechanism
				// and enclose above and below lines in try-catch blocks.
				System.out.println("Element is found...." + locator + " in attempt" + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Element is not found...." + locator + " in attempt" + attempts);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				attempts++;
			}

		}
		if (element == null) {
			System.out.println("Element is not found...tried for" + timeOut + " secs " + " with the interval of " + 500
					+ " milli secs ");
		}
		return element;
	}

	// Generic method implementing Custom Fluent Wait like
	// mechanism to wait for an element and perform action
	// if found within the waiting time limit else throw exception
	// User can give their own custom pollingTime in this method
	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

		WebElement element = null;
		int attempts = 0;
		while (attempts < timeOut) {
			try {

				element = getElement(locator);
				System.out.println("Element is found...." + locator + " in attempt" + attempts);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("Element is not found...." + locator + " in attempt" + attempts);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				attempts++;
			}

		}
		if (element == null) {
			System.out.println("Element is not found...tried for" + timeOut + " secs " + " with the interval of "
					+ pollingTime + " millisecs ");
		}
		return element;
	}

	// Generic function to check if the document's / page's ready state is Complete
	// (Meaning DOM and all it's sub-resources are completely loaded) or not.
	// And if it is then return true otherwise return false
	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"))
				.toString(); // The JS
		// written as the parameter of jsReturnsValue method will return either true or
		// false. We have applied
		// toString at the end of the statement because the until method will return a
		// JAVAScript Object
		// formatted data (true or false)

		return Boolean.parseBoolean(flag);
	}
}
