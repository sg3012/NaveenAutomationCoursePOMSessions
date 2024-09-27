package com.qa.opencart.factory;

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
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
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
		return eo;
	}
}
