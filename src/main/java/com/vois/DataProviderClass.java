package com.vois;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
	
	//Run "YoutubeTesting.xml" as TestNG Suite to enable the parallel execution of each item in the data provider
	@DataProvider(name = "TestData", parallel = true)
	public static Object[][] TestData()
	{
		//Returns an object with the test data required for the three test cases
		return new Object[][]
				{
					{"Selenium Tutorial", 2},
					{"Test Automation Tutorial", 2},
					{"Selenium Tutorial", 9}
				};
	}
}
