package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("EPIC - 101: Design of the Accounts page for open cart app")
@Story("US - 201: Implement Accounts page features for open cart app")
public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() { // This setup method is written
		// so that it executes first and login to the app >>
		// landing to the account page and then perform
		// tests on account page using AccountsPage Object. Otherwise, we will not
		// be able to perform any test on account page.
		
		// Also, we have not logged into the app
		// using "doLogin" method in the BaseTest itself
		// because if we log in the base test itself
		// first of BeforeTest will execute and user
		// gets logged into the app. But while performing
		// tests on login page user doesn't need to be
		// logged in so the test scripts written on login
		// page will be impacted by this. Secondly, on
		// register page also user doesn't need to be logged in
		// to perform the tests. So, the tests for Register page
		// will also be impacted. That is why we have used @BeforeClass
		// in the page test classes itself and logged in to the app
		// only for the page where user must be logged in.
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accountsPage.getAccountsPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accPageURLTest() {
		String actURL = accountsPage.getAccountsPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION));
	}
	
	@Test
	public void isLogOutLinkExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist()); 
	}
	
	@Test
	public void accPageHeadersCountTest() {
		int actAccPageHeadersCount = accountsPage.getAccountsPageHeaderCount();
		System.out.println("Actual Acc Page Headers count = " + actAccPageHeadersCount);
		Assert.assertEquals(actAccPageHeadersCount,AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actAccPageHeadersList = accountsPage.getAccountsPageHeader();
		Assert.assertEquals(actAccPageHeadersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] searchTestData() {
		return new Object[][]{
			{"macbook",3},
			{"imac", 1},
			{"samsung", 2},
			{"canon", 1}
		};
	}
	
	@Test(dataProvider = "searchTestData")
	public void searchTest(String searchKey, int expProductCount){
		searchResPage = accountsPage.doSearch(searchKey);
		int actProductCount = searchResPage.getSearchResultsCount();
		Assert.assertEquals(actProductCount,expProductCount);
		// We will perform all tests related
		// to searching a product (search functionality) 
		// from Accounts page only because we are
		// landed on the searchResultsPage from Accounts page
		// only. So, we will perform product search
		// for different products and verify their counts
		// from AccountsPage only.
	}
}
