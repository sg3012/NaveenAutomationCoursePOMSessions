package com.qa.opencart.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer{
	
	// IAnnotationTransformer is the transformer
	// that attaches the Retry listener
	// to the test annotations at the run time
	// so that the failed tests could be retried
	// the number of times mentioned in the RetryListerner
	// logic
	
	// [INTERVIEW]: Which is the transformer that
	// dynamically attaches the retry listener
	// at the run time to the test annotations?
	// IAnnotationTransformer is the transformer
	
	// Difference between transformer and listeners
	// in TestNG?
	// Listeners : Listeners are used to listen to the 
	// events like test pass/fail, suite pass/fail, test start/end
	// etc. as soon as they are completed
	
	// Transformers: They are used to modify the test annotations
	// at the run time by attaching different features to them like
	// listeners, changing timeouts, Data-providers etc.
	
	// Below is the method which has the logic of
	// attaching the retry listener to the test cases
	// at the run time
	@Override
	public void transform(ITestAnnotation annotation, 
			Class testClass, 
			Constructor testConstructor, 
			Method testMethod) {
		annotation.setRetryAnalyzer(Retry.class); // This line of code
		// is responsible for attaching the retry analyzer listener
		// at the run time to the tests
	}	
}
