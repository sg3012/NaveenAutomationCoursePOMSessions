package com.qa.opencart.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	// This class creates the driver instances based
	// on browser names and different capabilities
	// or options supplied. Options could be "headless",
	// incognito or some other options.
	
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	private Properties prop;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) { // We have converted
			// the value returned from getProperty method to boolean
			// because everything key and value in the properties
			// file is a plain string. So getProperty() will also
			// return String. But if-else blocks only takes
			// boolean values that is why we have converted the returned
			// String to boolean
			co.addArguments("--headless=new");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) { 
			co.addArguments("--incognito");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
			//co.setCapability("enableVNC", true); // enableVNC means
			// enabling the visual properties of the browser on remote
			// infrastructure to true. So that we could see actual
			// opening of the browser and see the running tests
			// on remote machines. If we don't set it we will not
			// see any browser opening on the remote machine
			// co.setBrowserVersion(prop.getProperty("browserversion").trim()); // Setting
			// the browserversion using the same variable
			// from the properties file which is set at run time
			// in the base test. We are setting browser version capability
			// also so that we could run our tests on cross browser
			// versions in parallel.
			
			// Below lines of code are used to configure
			// Selenoid with different capabilities like
			// screen resolution for running tests, 
			// enable VNC to true so that we could
			// see our running tests in real-time etc
//			Map<String,Object> selenoidOptions = new HashMap<>();
//			selenoidOptions.put("screenResolution", "1280x1024x24");
//			selenoidOptions.put("enableVNC", true);
//			// This test name capability will be read
//			// here from the properties file to be
//			// supplied as the test name visible
//			// on the selenoid container running the
//			// test
//			selenoidOptions.put("name", prop.getProperty("testname"));
//			co.setCapability("selenoid:options", selenoidOptions);
		}
		
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) { // We have converted
			// the value returned from getProperty method to boolean
			// because everything key and value in the properties
			// file is a plain string. So getProperty() will also
			// return String. But if-else blocks only takes
			// boolean values that is why we have converted the returned
			// String to boolean
			fo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) { 
			fo.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
//			fo.setCapability("enableVNC", true);
		    //fo.setBrowserVersion(prop.getProperty("browserversion").trim());
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		eo.addArguments("--edge-skip-compat-layer-relaunch"); // This argument is added to prevent below
		// exception from occurring while executing tests on local Edge browser:
//		org.openqa.selenium.SessionNotCreatedException: Could not start a new session. 
//		Response code 500. Message: session not created: probably user data directory is 
//		already in use, please specify a unique value for --user-data-dir argument, 
//		or don't use --user-data-dir
		if(Boolean.parseBoolean(prop.getProperty("headless"))) { // We have converted
			// the value returned from getProperty method to boolean
			// because everything key and value in the properties
			// file is a plain string. So getProperty() will also
			// return String. But if-else blocks only takes
			// boolean values that is why we have converted the returned
			// String to boolean
			eo.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) { 
			eo.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "MicrosoftEdge"); // Pass MicrosoftEdge only here as the 
			// capability, otherwise the remote execution on Edge browser will not work
			// using Selenium Grid on Docker
			//eo.setCapability("enableVNC", true);
		}
		return eo;
	}
}
