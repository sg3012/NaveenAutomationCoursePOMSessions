package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportWithEnvInfo {
	// This class explains how we could
	// Add System and Environment info
	// in the Extent Reports like:
	// OS
	// RAM
	// ROM
	// Browser
	// APP URL
	
	public static void main(String args[]) {
		WebDriver driver = new ChromeDriver();
		Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
		// Get the browser name:
		System.out.println(capabilities.getBrowserName());
		
		// Get the browser version:
		System.out.println(capabilities.getBrowserVersion());

		ExtentReports extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/testreport.html");
		extentReports.attachReporter(sparkReporter);
		
		// We have configured the below info like
		// theme, Reportname, Reporttitle etc
		// using ExtentSparkReporter instance because
		// this info can be different for different reports, if
		// we are generating multiple reports. And we know
		// that the ExtentSparkReporter class works only 
		// on a single report generated at a time.
		sparkReporter.config().setTheme(Theme.DARK);
		
		sparkReporter.config().setReportName("Report name");
		
		sparkReporter.config().setDocumentTitle("Report title");
		
		sparkReporter.config().setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
		
		sparkReporter.config().setCss(".badge-primary{background-color:#f50808}");
		
		sparkReporter.config().setJs("document.getElementsByClassName('logo')[0]"
				+ ".style.display='none'");
		
		// We have configured the below info like
		// Os name and version, Java Version etc
		// using ExtentReports instance because
		// this is the system info on which the tests are running
		// which will be common for all the reports i.e.,
		// this info is going to be same for all the reports
		// most of the time, if we are generating multiple reports. 
		// And we know that the ExtentReports class works on all
		// the generated reports at once.
		extentReports.setSystemInfo("OS", System.getProperty("os.name"));
		
		extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
		
		extentReports.setSystemInfo("Browser name and version",capabilities.getBrowserName()+":"
		+capabilities.getBrowserVersion());
		
		extentReports.setSystemInfo("App URL", "https://naveenautomationlabs.com/opencart");
		
		extentReports.setSystemInfo("Username", "sgnight30@gmail.com");
		
		extentReports.setSystemInfo("Password", "Test@1234");
		
		extentReports.createTest("Test 1", "Sample Test 1")
			.assignAuthor("Shubham") 
			.assignCategory("Smoke") 
			.assignDevice("Chrome 97") 
			.pass("This test is passed");
		
		extentReports.createTest("Test 2", "Sample Test 2")
		.assignAuthor("Rado")
		.assignCategory("Sanity") 
		.assignDevice("Edge 90")
		.fail("This test is failed");
		
		extentReports.createTest("Test 3", "Sample Test 3")
		.assignDevice("Firefox 64")
		.assignCategory("Regression") 
		.fail("This test is failed");
		
		extentReports.createTest("Test 4", "Sample Test 4")
		.assignAuthor("Shubham")
		.assignAuthor("Rado")
		.assignCategory("Smoke")
		.assignCategory("Regression")
		.assignDevice("Chrome 97")
		.assignDevice("Chrome 99")
		.pass("This test is passed");

		extentReports.createTest("Test 5", "Sample Test 5")
		.assignAuthor("Shubham", "Rado", "Akan")
		.assignCategory("Smoke", "Regression", "Sanity")
		.assignDevice("Chrome 97","Chrome 99", "Firefox 64")
		.pass("This test is passed");
		
		String authors[] = {"Shubham", "Rado", "Akan"};
		String categories[] = {"Smoke", "Sanity"};
		String devices[] = {"Chrome 97","Chrome 99", "Firefox 64"};
		
		extentReports.createTest("Test 6", "Sample Test 6")
		.assignAuthor(authors)
		.assignCategory(categories)
		.assignDevice(devices)
		.pass("This test is passed");
		
		extentReports.flush();
		driver.quit();
	}

}
