package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	// This class defines the constant values
	// which are rarely going to be changed
	// throughout the application
	
	// These constant variables are defined static
	// because we don't want to create Object of
	// AppConstants
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	
	public static final String ACCOUNTS_PAGE_TITLE = "My Account";
	public static final String ACCOUNTS_PAGE_URL_FRACTION = "route=account/account";
	public static final int ACCOUNTS_PAGE_HEADERS_COUNT = 4;
	
	public static final List<String> EXPECTED_ACC_PAGE_HEADERS_LIST = Arrays.asList("My Account","My Orders"
																	,"My Affiliate Account","Newsletter");
	public static final String REGISTER_PAGE_TITLE = "Register Account";
	
	public static final String USER_REGISTER_SUCCESS_MSG = "Your Account Has Been Created!";
	
	// ******************** Default TimeOut Values *****************//
	public static final int SHORT_TIME_OUT = 5;
	public static final int MEDIUM_TIME_OUT = 10;
	public static final int LONG_TIME_OUT = 15;
	
	// **************** Sheet Names ****************//
	public static final String REGISTER_SHEET_NAME = "register";
	public static final String PRODUCT_SHEET_NAME = "product";
	public static final String LOGIN_SHEET_NAME = "login";
	
	

}
