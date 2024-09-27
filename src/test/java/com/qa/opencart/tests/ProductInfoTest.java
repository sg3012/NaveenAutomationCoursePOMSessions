package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("EPIC - 102: Design of the Product Info page for open cart app")
@Story("US - 202: Implement Product Info page features for open cart app")
public class ProductInfoTest extends BaseTest {
	
	// [NOTE] In Latest practice we avoid storing test
	// data in the excel sheets and read it through
	// some 3rd party library like Apache POI
	// like we used to, due to following disadvantages:
	
	// Sometimes when the excel sheets containing test data
	// becomes too large and become corrupted. In that
	// we have to create a new excel from scratch if
	// we don't have the backups already created. Plus,
	// we have to maintain extra copies of excel files unnecessary.
	
	// If one of your team member has deleted some rows of test data from the excel
	// then you have to create those rows again.
	
	// If one of the team member is not using excel they
	// are using some other utility for creating spreadsheets
	// then they have to install excel on their system.
	
	// If excel is not allowed to be installed in any machine
	// or require some special approvals then you can't
	// use excel sheets at all and in turn the POI API.
	
	// Because of the above disadvantages we prefer to maintain
	// the test data in our test scripts only these days with
	// full control over our test class with no 3rd party dependency.
	
	// We can use Still use Excel sheets to maintain and consume
	// the test data if there is a requirement from the customer
	// to run the test scripts on a lot of TEST DATA. For e.g.
	// 100-200 sets/rows of products on an e-commerce app.

	@BeforeClass
	public void prodInfoSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password")); // login to app
		// and land on accounts page
		// In every setUp method for a particular
		// page we will only login to the
		// application and navigate to the very
		// 1st landing page (Accounts page here).
		// We will call the respective page action method for  landing to 
		// the subsequent pages in the respective page test class method
		// like we are doing in productHeaderTest() method here
		
		// @Dataprovider annotation DOES NOT
		// WORK with any @Before or @After annotations
		// it will only work for @Test. Meaning,
		// we can only fetch the data from
		// Dataprovider annotation only inside
		// @Test annotation not any other annotation.
		
	}
	
	@DataProvider
	public Object[][] productHeaderTestData() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"macbook","MacBook Air"},
			{"imac","iMac"},
			{"samsung","Samsung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "productHeaderTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResPage = accountsPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		String actProductHeader = productInfoPage.getProductHeaderValue();
		Assert.assertEquals(actProductHeader, productName);
	}
	
	// Dataprovider supplying the data
	// to the tests directly from within
	// the test class
//	@DataProvider
//	public Object[][] productImagesTestData() { // We have created this separate data provider for 
//		// productImagesCountTest and not re-used the one for productHeaderTest
//		// because data provider for productHeaderTest was returning array
//		// with 2 columns and data provider for productImagesCountTest
//		// should return array with 3 columns.
//		return new Object[][] {
//			{"macbook","MacBook Pro",4},
//			{"macbook","MacBook Air",4},
//			{"imac","iMac",3},
//			{"samsung","Samsung SyncMaster 941BW",1},
//			{"samsung","Samsung Galaxy Tab 10.1",7}
//		};
//	}
	
	// Dataprovider supplying the data
	// to tests from the excel sheet
	@DataProvider
	public Object[][] productImagesTestData() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
	}

	// Test case fetching the data from the dataprovider
	// written directly in the test class
//	@Test(dataProvider = "productImagesTestData")
//	public void productImagesCountTest(String searchKey, String productName, int expProductImagesCount) {
//		searchResPage = accountsPage.doSearch(searchKey);
//		productInfoPage = searchResPage.selectProduct(productName);
//		int actProdImagesCount = productInfoPage.getProductImagesCount();
//		Assert.assertEquals(actProdImagesCount,expProductImagesCount);
//	}
	
	// Test case fetching the data from the dataprovider
	// fetching the data from Excel sheet(s)
	@Test(dataProvider = "productImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, String expProductImagesCount) {
		searchResPage = accountsPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		int actProdImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actProdImagesCount,Integer.parseInt(expProductImagesCount));
		// Converted expProductImagesCount to Integer because
		// it is coming in String format from the excel sheet. 
	}
	
	@Test
	public void productInfoTest() {
		searchResPage = accountsPage.doSearch("macbook");
		productInfoPage = searchResPage.selectProduct("MacBook Pro");
		Map<String,String> productActualData = productInfoPage.getProductdata();
		System.out.println(productActualData);
		// Now asserting respective values agains each key
		// in the returned map of product details/data
//		Assert.assertEquals(productActualData.get("Brand"),"Apple11");
//		Assert.assertEquals(productActualData.get("Availability"),"In Stock");
//		Assert.assertEquals(productActualData.get("price"),"$2,000.00");
//		Assert.assertEquals(productActualData.get("productheader"),"MacBook Pro");
//		Assert.assertEquals(productActualData.get("productimages"),"4");
//		Assert.assertEquals(productActualData.get("Product Code"),"Product 18");
//		Assert.assertEquals(productActualData.get("Reward Points"),"800");
		
		// Now, this test case is not following the AAA (Act,Arrange, Assert)
		// pattern until above line i.e., every test case
		// should have only single assert. But here we have
		// more than 1 assert for a single test. There is one
		// major problem with multiple asserts for a single test case
		// which is if any of the assertions before the last one
		// gets failed in the sequence then control will not go
		// to the next assertion(s) and test will be terminated
		// immediately. So, we wouldn't
		// be able to validate further assertions if any
		// of the previous assertion(s) get failed which
		// is wrong. So, there are 2 problems here:
		// We have multiple assertions for single test
		// If any previous assertion is failed further
		// assertions are not getting checked.
		// Now, how to fix these problems?
		// Ans: 1. Either create a separate test method
		// for every assertion we need to check which
		// not a very good solution as every assertion in this 
		// test method represent the same test i.e., product info.
		// So, writing separate test cases for the same test
		// with different assertions is not a good design.
		// 2. Use SOFT ASSERT and for multiple assertions
		// and use HARD ASSERT only when last or main assertion
		// is left. This is a better solution.
		
		// SOFT-ASSERT:
		softAssert.assertEquals(productActualData.get("Brand"),"Apple");
		softAssert.assertEquals(productActualData.get("Availability"),"In Stock");
		softAssert.assertEquals(productActualData.get("price"),"$2,000.00");
		softAssert.assertEquals(productActualData.get("productheader"),"MacBook Pro");
		softAssert.assertEquals(productActualData.get("productimages"),"4");
		softAssert.assertEquals(productActualData.get("Product Code"),"Product 18");
		softAssert.assertEquals(productActualData.get("Reward Points"),"800");
		// softAssert.assertAll(); // assertAll() method
		// will apply final assertion on all previous assertions
		// and give a consolidated result of how many validations/assertions FAILED 
		// in all the assertions?
		// So, applying assertAll at the very last is MUST. Otherwise
		// if any of the assertion(s) in the soft assert series is getting
		// failed we will still get passed result for the test in the console.
		// In the above soft assert series the 1st and the last one
		// got failed and it will give the same in the result.
		
		// When to use Hard Assert and when to use Soft Assert?
		// Ans: When we have ONLY 1 expected result to be validated as 
		// part of a test case then we apply Hard Assert. But
		// when we have MORE THAN 1 expected results to be validated as part
		// a single test then we will apply Soft Asserts.
	}
	
	
}
