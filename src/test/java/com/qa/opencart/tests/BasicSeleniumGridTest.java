package com.qa.opencart.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BasicSeleniumGridTest {

	public static void main(String[] args) throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setBrowserName("chrome");
		caps.setVersion("72");
		caps.setCapability("maxInstances", 2);

		// 2) Point to your Grid Hub
		URL hubUrl = new URL("http://localhost:4444/wd/hub");
		// 3) Create RemoteWebDriver
		WebDriver driver = new RemoteWebDriver(hubUrl, caps);

		// 4) Configure timeouts
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		// 5) Navigate and interact
		driver.get("https://www.google.com");

		// Locate the search box, type query and submit
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("Selenium Grid 3");
		searchBox.submit();

		// Wait a bit for results
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 6) Simple verification
		String title = driver.getTitle();
		if (title.toLowerCase().contains("selenium grid 3")) {
			System.out.println("Search succeeded: " + title);
		} else {
			System.err.println("Unexpected title: " + title);
		}
	}

}
