package com.qa.opencart.listeners.SampleCodeForCreatingExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportWithAdditionalConfig {
	// This class explains how we could
	// Change or provide additional
	// configuration inside the Extent reports.
	// We can do the following:
	
	// Change the theme of the report from
	// standard to dark or dark to standard
	
	// Change the report name if multiple reports
	// are generated
	
	// Change the document title
	// Change the date and time stamp format in the report
	// Apply CSS to provide custom colors in the report
	// Run JavaScript to do additional tasks
	
	// To do the required config changes the reports
	// we will apply the changes to the ExtentSparkReporter Object
	// not the Extent report one. Because SparkReporter is the one
	// which is helping us to actually create the reports
	
	// Another reason we should make changes in the Extentsparkreporter object only is
	// if we apply changes to the ExtentReports object using it's object reference
	// then the changes will be applied to all the reports that are generated instead
	// of the report we want to apply the changes. But using the ExtentsparkReporter object
	// we could make the changes to only the report that we want to.
	public static void main(String args[]) {

		ExtentReports extentReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./reports/testreport.html");
		extentReports.attachReporter(sparkReporter);
		
		// Change the theme in the report:
		sparkReporter.config().setTheme(Theme.DARK);
		
		// Change report name:
		sparkReporter.config().setReportName("Report name");
		
		// Change the report title:
		sparkReporter.config().setDocumentTitle("Report title");
		
		// Change date and time stamp format (to IST):
		sparkReporter.config().setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
		
		// Apply the CSS to change the some colors
		// [NOTE]: To apply the custom CSS in the Extent reports
		// first go to the required element in the browser dev tools >>
		// go to the styles in the right side window >> look for the 
		// class on which the styles like background color, font style,
		// font-size etc. are applied and then use the same class in the
		// JAVA code for the reports to make the changes like below:
		sparkReporter.config().setCss(".badge-primary{background-color:#f50808}"); // here
		// badge-primary is the css class used to provide background color
		// to the element by the Developer (DON'T FORGET to use
		// Dot (.) before class name while using CSS code )
		
		// Apply the custom JavaScript to remove/hide
		// logo of Extent Reports on the report
		// Below is the code:
		sparkReporter.config().setJs("document.getElementsByClassName('logo')[0]"
				+ ".style.display='none'");
		//[NOTE]: Here getElementsByClassName method
		// will give you the element by it's class name 
		// Class name is the parameter of this method.
		// Now, this method could return a list of elements
		// in the browser console with respective index.
		// All we need to do is identify the index of desired element
		// (here, index 0) and call style.display and provide
		// the value 'none' to hide the element like above.
		
		// Instead of calling the config() method with 
		// ExtentSparkReporter object as method chaining
		// we could also shorten the code like below:
		// Create the Object of ExtentSparkReporterConfig:
//		ExtentSparkReporterConfig config = sparkReporter.config();
		
		// Call the required methods using ExtentSparkReporterConfig object ref:
//		config.setTheme(Theme.DARK);		
//		config.setReportName("Report name");	
//		config.setDocumentTitle("Report title");		
//		config.setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
//		config.setCss(".badge-primary{background-color:#f50808}");
//		config.setJs("document.getElementsByClassName('logo')[0].style.display='none';");
		
		// We can also pass all this config data as mentioned above
		// through a JSON or XML file. 
		// What is the need of passing through JSON or XML file?
		// Ans: Sometimes in the projects we share our automation project
		// to some other teams as JAR files. We know the JAR files are non-editable
		// Now if we have provided the config data directly in the JAVA code
		// then team members will not be able modify that data. So,
		// in order to be able to edit this config data by other team
		// members we should not include this code in the JAVA file only.
		// Instead we can store this data in a separate JSON file
		// and we could provide this JSON file separately to the
		// other team members to make config changes. 
		// Similarly we could also store this data in a XML file
		// instead of JSON and share this file with other
		// team members. Below is the code show-casing the use
		// of both JSON and XMl files:
		
		// Load config data using JSON files:
//		try {
//			sparkReporter.loadJSONConfig(new File("./src/test/resources/config/extent-reports-config.json"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// Load config data using XML files:
//		try {
//			sparkReporter.loadXMLConfig(new File("./src/test/resources/config/extent-reports-config.xml"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
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
	}

}
