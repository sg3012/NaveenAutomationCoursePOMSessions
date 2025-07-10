package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author Shubham Gupta
 */
public class DriverFactory {
	// This class explains how we
	// could configure and create different browser
	// instances and supply/return to our test cases
	// based on different browser names ( or
	// the browser on which we want to run our
	// tests on ).
	// This class will only create a PLAIN driver instance (that
	// actually launches a browser). It will not create a driver
	// with different options like headless, Incognito or
	// some other capabilities / options.

	// Thread local class in JAVA provides a local/separate
	// copy of WebDriver instance to a thread which is getting executed
	// at any given point in time. This way the threads never clash
	// with each other's driver and reach in deadlock state during
	// parallel execution
	
	// [IMPORTANT INTERVIEW]: Which option we use with the maven
	// command to run the test scripts on a particular environment?
	// Ans: -D. It's important to note that this option is case sensitive
	// so it is always upper-case D. This option is part of maven install
	// command
	
	// [IMPORTANT NOTE]: The option D with maven install command
	// is used to pass the different environment variables with
	// different values like environment 'stage', browser 'chrome'.
	// So, we could use it like mvn install -Denv="stage" -Dbrowser="chrome"
	
	// [IMPORTANT]: getProperty() method of Properties class is used to read
	// environment properties/values from the Properties files whereas
	// getProperty() method of System class is used to read 
	// environment properties/values from the Maven commands passed
	// from either command line or Eclipse UI
	
	// The set() method of ThreadLocal class passes required type of
	// object which ThreadLocal's object requires. Here, ThreadLocal
	// is of type WebDriver then set() method will add WebDriver's
	// object in the thread-local copy of the current thread.

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	URI uri;
	URL url;
	public static String highlight; // We have
	// used the highlight property in the Diverfactory
	// class because this class serves a driver
	// with different options/properties
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

//	public WebDriver initDriver(String browserName) { // As this method
	// reads the values of properties like browser name, URL, headless
	// etc from the properties file, we could pass the whole
	// properties class reference as a parameter here which
	// contains all the values of the properties
	// in a single object instead of passing
	// a long list of comma separated properties
	// as the parameters of this function (see below).
	/**
	 * This method launches a web browser instance by initializing the driver
	 * according to the browser name supplied.
	 * 
	 * @param browserName
	 * @return Web browser/driver instance according to browser name
	 */
	public WebDriver initDriver(Properties prop) {
		// Reading the browser environment value from properties file
		   String browserName = prop.getProperty("browser");
		
		// Reading the browser environment value from mvn install command 
		// either via command line // or eclipse UI
		// MVN command to pass the browser name at run time: 
		// mvn clean install -Dbrowser="<browser-of-your-choice>"
//		String browserName = System.getProperty("browser");
		System.out.println("browser name is: " + browserName);
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight");

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				// CODE BLOCK FOR RUNNING TESTS ON REMOTE
				// SELENIUM GRID
				// run tests on remote
				init_remoteDriver("chrome");
			}
			else {
				// run tests on local
				// driver = new ChromeDriver(optionsManager.getChromeOptions()); // Don't
				// initialize the webdriver instance like this now. Initialize
				// it using Threadlocal class using the syntax as mentioned below:
				tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				// Here, the set method takes the WebDriver interface
				// reference but we are passing the ChromeDriver's Object
				// which is fine because ChromeDriver is also a type
				// WebDdriver in Selenium hierarchy. We could also pass
				// ChromeDriver class reference here because of the same reason.
				// Now, we can initialize the driver for firefox, edge, safari using the
				// same threadlocal syntax like above.
			}
			break;
		case "firefox":
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox");
			}
			else {
				tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		case "edge":
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("edge");
			}
			else {
				tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
		case "safari":
			tldriver.set(new SafariDriver());
			break;
		default:
			System.out.println("Plz pass the right browser...." + browserName);
			break;
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	/**
	 * Generic method to initialize the web driver on
	 * remote machines like Selenium GRID nodes
	 * @param browserName
	 */
	private void init_remoteDriver(String browserName) {

		System.out.println("Running tests on GRID with browser: "+browserName);
		
		try {
			switch (browserName.toLowerCase()) {
			case "chrome":
				uri = new URI(prop.getProperty("huburl"));
				url = uri.toURL();
				tldriver.set(new RemoteWebDriver(
						url, optionsManager.getChromeOptions()));
				System.out.println("Chrome Browser version: " +((RemoteWebDriver)getDriver())
						.getCapabilities().getBrowserVersion());
				break;
			case "firefox":
				uri = new URI(prop.getProperty("huburl"));
				url = uri.toURL();
				tldriver.set(new RemoteWebDriver(
						url, optionsManager.getFirefoxOptions()));
				System.out.println("Firefox Browser version: " +((RemoteWebDriver)getDriver())
						.getCapabilities().getBrowserVersion());
				break;
			case "edge":
				uri = new URI(prop.getProperty("huburl"));
				url = uri.toURL();
				tldriver.set(new RemoteWebDriver(
						url, optionsManager.getEdgeOptions()));
				System.out.println("Edge Browser version: " +((RemoteWebDriver)getDriver())
						.getCapabilities().getBrowserVersion());
				break;
			default:
				System.out.println("Wrong browser info....cannot run on "
						+ "Grid remote machine");
				break;
			}
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	
	}

	public static WebDriver getDriver() {
		return tldriver.get();
		// We have created this method because
		// the initDriver method above returns the
		// WebDriver type reference but we are
		// initializing the threadlocal class
		// type WebDiver instance in it instead
		// of WebDriver type instance. Now,
		// the get() method in ThreadLocal
		// class returns the WebDriver type
		// of reference as a WRAPPER over
		// ThreadLocal type of WebDriver i.e. having
		// ThreadLocal properties.
		// Now, we will call this getDriver() method
		// in the initDriver() method to get
		// the WebDriver type driver (SEE INITDRIVER() ABOVE).
	}

	/**
	 * This method reads a properties file to fetch all the environment variables'
	 * values and returns the values to the user.
	 * 
	 * @return Properties class Object holding all the keys and theirs values in the
	 *         respective properties file.
	 */
	public Properties initProp() {
		
       
		// mvn clean install -Denv="qa" --> This command will supply
		// the environment variable on which to run the
		// test cases at the run time. Basically using this command
		// we are running our test scripts using MAVEN.
		
		// Where, env is the environment variable name. It can be any
		// name like "test", "sg" etc.
		
		// ################ CONFIGURING MAVEN BUILD COMMANDS ##############
		
		// There are 2 ways we can CONFIGURE THE MAVEN COMMAND 
		// the variable value of which will be supplied to 
		// getProperty() in this method and run our tests on 
		// the desired env (RUN CONFIGURATIONS):
		// 1. Configure this command in Eclipse >> Run the tests using Eclipse UI
		// by supplying this command
		// 2. Supply this command using command line interface (CMD in Windows) >> 
	    // Run the tests
		
		// USING ECLIPSE UI:
		// STEPS TO CREATE RUN CONFIGURATIONS IN ECLIPSE:
		// --> Right click on the project root folder
		// --> Mouse Hover Run as option from the context menu >> click
		//     Maven build... (3 dots) option
		// --> In the dialog that opens enter this command in the Goals field
		// --> If nothing else to provide in any other field then
		//     click apply. Your Run configuration is ready and 
		// you can click on Run button to run it.
		
		// NOTE: If you don't give the environment value with the command
		// using -D option like "-Denv = 'qa'" and you only give the command
		// clean install then the env value will be passed as null in the code
		// script consuming this command. If you have any logic associated
		// with the null env value being passed in your code then your scripts
		// will run automatically i.e., if the env value is null then
		// run all the scripts on QA environment by default (like we have done
		// below).
		
		// --------------- OR, IF YOU DON'T WANT TO PASS THE ENV VALUE
		// IN THE COMMAND ITSELF YOU CAN DO IT FROM THE 
		// UI ITSELF VIA THE FOLLOWING STEPS------------------------
		// --> Right click on the project root folder
		// --> Mouse Hover Run as option from the context menu >> click
		//     Maven build... (3 dots) option
		// --> In the dialog that opens enter only the command "clean install" 
		//     in the Goals field
		// --> Now, click Add button and enter the values in the "Name"
		//     and "Value" fields. This will add the env variable name and it's value
		// --> You can then click on Apply and your configuration is ready. You can
		// then click on Run button to run it.
		
		// PASSING ENV VALUE USING COMMAND LINE:
		// PREREQUISITES: Download and Install Maven on windows
		// using the link: https://mkyong.com/maven/how-to-install-maven-in-windows/
		// STEPS TO CONFIGURE MAVEN BUILD COMMANDS USING CMD:
		// --> Open the CMD on your machine
		// --> Navigate to the project root directory
		// using cd command
		// --> Execute the command 'mvn clean install -D<envvariablename>="<envvalue>"'
		
		// PASSING BROWSER VALUE USING COMMAND LINE:
		// Prerequisites and steps 1,2 remain same as above
		// --> Execute the following command:
		// mvn clean install -D<browservariablename>="<browservalue>" at last.
	
		
		// ################ CONFIGURING MAVEN TO RUN TESTS 
		// ################ USING TESTNG RUNNER ###############
		
		// When we run our tests using Maven, it looks for the directory 
		// src\test\java and executes all the test classes inside it one by one
		// and not from the test runner file (testng.xml), by DEFAULT.
		// But if we want Maven to pick and run the tests from the runner
		// file (testng.xml) instead then we have to do the following steps:
		
		// [NOTE-PRECONDITION]: Before executing below steps make sure
		// you have configured the 'Maven clean install
		// command' to supply the env values to the test scripts
		// either through Eclipse UI or command line.
		// To make Maven pick your tests from the runner file,
		// compile and run them we will use 2 plugins in
		// our pom.xml file, Maven SUREFIRE Plug-in
		// and Maven COMPILER Plug-in.
		
		// Maven COMPILER plug-in: It is used to compile
		// our JAVA code written under src\main\java
		// and src\test\java.
		// Maven SUREFIRE plug-in: It is used to create
		// a new JVM process and trigger the testNG library
		// to run our tests
		
		// STEPS:
		// --> Now, add these plug-ins under Plug-ins --> Plugin
		// in your pom.xml file
		
		// --> In the maven-surefire-plugin section
		// add one more tag called <suiteXmlFiles>. Under
		// this tag add <suiteXmlFile>. Provide the path
		// of your test runner file as a value of <suiteXmlFile> 
		// tag, starting from the src folder.
		
		// --> Now, right click on the project root
		// folder. Mouse hover run as option >> Click
		// Run configurations >> choose any configuration
		// you have already created under section "Configuring
		// Maven Build commands" >> Click Run. 
		// Above steps are also called MAVEN INTEGRATION WITH TESTNG
		
		// NOTE[IMPORTANT]:
		// Whenever we issue a Maven command to clean,install and run our project
		// it will go the following places in the following sequence:
		// 1. First, it will go the pom.xml file
		// 2. Second, it will go to the dependencies section
		// and check whether all the required dependencies are
		// are downloaded correctly or not. If not then it will
		// download them automatically.
		// 3. Third, it will go to the plugins section
		// and look for the maven-compiler plugin. Using the compiler
		// plugin it will compile the JAVA code.
		// 4. Fourth, it will go to 
		// look for the maven-surefire plugin. The
		// maven will trigger surefire plugin to look
		// for the testNG.xml file path using the path we have
		// provided under <suiteXmlfile>.
		// 5. Fifth, maven will go the testNG.xml file
		// and run it using surefire plugin
		
		// [IMPORTANT]: How Maven connects with the getProperty() method
		// and tells to it what environment variable to use? 
		// Or, How System.getProperty() knows it has to pick
		// the environment variable passed from Maven command line
		// or Eclipse UI?
		// Ans: Whenever we write the command "mvn clean install -D<envvariablename>
		// ='<envvariablevalue>'" via command line or from Eclipse UI, Maven
		// executes the commands to set the environment variables temporarily
		// for that run session only respective to the OS (like set for WINDOWS)
		// internally. Now, System class in JAVA looks for any properties
		// that are available on your local system like Environment variable
		// in this case. Once Maven sets the environment variables 
		// in our system's environment variables , it picks
		// the respective environment variable with the corresponding name
		// ('env', in this case)
		

		FileInputStream ip = null;
		prop = new Properties();

		String envName = System.getProperty("env");
		System.out.println("env name is: " + envName);

		try {
			// if no env is given
			if (envName == null) {
				System.out.println("no env is given...hence running it on QA env by default");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			}

			// if env is given
			else {
				// Instead of writing a separate try-catch block
				// for the fileinputstream object creation lines
				// below and create a repetitive code we will
				// enclose this whole if-else structure in a single
				// try-catch block.
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					System.out.println("please pass the right env name..." + envName);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	/**
	 * generic method to take screenshots
	 * during automation scripts' execution
	 */
	public static String getScreenshot(String methodName) {
		// TakesScreenshot is the Interface having 
		// the method getScreenshotAs().
		// RemoteWebDriver is the class implementing TakesScreenshot interface
		// and it's method getScreenshotAs().
		
		// As we have top casted the Webdriver reference to point
		// to ChromeDriver() class object we can only
		// access the methods available inside the WebDriver interface
		// through WebDriver reference not the methods of TakesScreenshot interface
		// because the reference variable is of type WebDriver
		// not TakesScreenshot. So, if we try to access methods
		// of TakesScreenshot via WebDriver reference then we will
		// get an exception because reference type check is Failed.
		// So, to access the methods of TakesScreenshot we have to type-cast
		// our WebDriver reference variable to point to TakesScreenshot reference
		// like below:
		
		TakesScreenshot ts = (TakesScreenshot)getDriver();
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshots/"+ methodName +"_"+
				System.currentTimeMillis()+".png";
		// user.dir parameter in getProperty() method will
		// will return the Project root folder (the top folder
		// in the project hierarchy ) name from
		// the current file in which this method is called.
		
		File destination = new File(path);
		// We have converted the String path
		// to File format path variable
		// because copy method below
		// takes only File type of parameters
		// to copy the source file when the screenshot is
		// taken in to the destination which is
		// also a file.
		try {
			FileHandler.copy(srcFile, destination);
			// [IMPORTANT]: This copy() method will not create the screenshot
			// path (as shown by the "path" variable above) automatically.
			// We have to create it manually. If it is not created
			// and we run our tests then this method will throw
			// IOException on the console every time the getScreenshot()
			// method is called for any test.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}
