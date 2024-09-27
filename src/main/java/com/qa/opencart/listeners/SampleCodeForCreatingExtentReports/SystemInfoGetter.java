package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SystemInfoGetter {

	// This class explains how we could get the browser
	// version of any browser using Selenium
	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
		// Get the browser name:
		System.out.println(capabilities.getBrowserName());
		
		// Get the browser version:
		System.out.println(capabilities.getBrowserVersion());
		
		driver.quit();
		
		// Get the list of all system related
		// properties/environment on the console:
		System.getProperties().list(System.out);
		
		System.out.println("--------------------");
		
		// Get the OS name and OS version:
		System.out.println("OS Name: " + System.getProperty("os.name"));
		System.out.println("OS Version: " + System.getProperty("os.version"));
		
		//Get JAVA version:
		System.out.println("Java Version: " + System.getProperty("java.version"));
		
		
	}

}
