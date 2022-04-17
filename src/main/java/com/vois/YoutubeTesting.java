package com.vois;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import youtubePageModel.*;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.diagnostics.logging.Logger;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class YoutubeTesting {
	
	private String reportsPath;
	private String screenshotsPath;
	private int tcCounter;
	private ExtentReports extentReport;
	private ExtentTest test;
	private Screenshot Screenshot_webele;

			
	@BeforeClass
	public void beforeClass() {
		//Get the current directory 
		String currentDir = System.getProperty("user.dir");
		
		//Concatenate the current directory with the path of the chromedriver.exe which is located inside the project
	    String chromeDriverLocation = currentDir + "\\resources\\chromedriver_win32\\chromedriver.exe";
	    
	    //Set the system property "webdriver.chrome.driver" with the location of the Chrome driver
	    System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
	    
	    //Set the path for the generated extent reports and the screenshots
	    reportsPath = currentDir + "\\Reports";
	    screenshotsPath = currentDir + "\\Screenshots";
	    
	    //Set the test case counter to 1;
	    tcCounter = 1;
	    
		//Initialize the extend report
	    extentReport = new ExtentReports(reportsPath + "\\YoutubeTesting_Report.html",true);    
	}
	
	
	@AfterClass
	public void afterClass() {
		extentReport.flush();
	}
	
	
	@Test(dataProvider = "TestData", dataProviderClass = DataProviderClass.class)
    public synchronized void VideoTitleValidation(String keyword, int videoIndex) throws InterruptedException, IOException {
		//Two strings to hold the values of the titles in the search results and when the video is played
		String videoTitle_searchResults;
		String videoTitle_videoPage;
		
		//Create an object of each Page Factory Model
		Homepage youtubeHomepage_obj;
		SearchResultsPage searchResultspage_obj;
		VideoPage videoPage_obj;
		
		//Initialize the test object
		test =extentReport.startTest("VideoTitleValidation_" + Integer.toString(tcCounter));
		
		//Increment the test case number, if it reaches 3, reset it back to 1
		if(tcCounter == 3) tcCounter = 1;
		else			   tcCounter++;

		//Print the thread id of the test case on the console and in the report to show that each instance is run on a seperate thread
		System.out.println("Thread ID: " + Thread.currentThread().getId());
		
		WebDriver driver = ThreadGuard.protect(new ChromeDriver());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        test.log(LogStatus.INFO, "Launching the browser");
				
        driver.get("https://www.youtube.com");  
        test.log(LogStatus.INFO, "Opening Youtube.com");
        
		youtubeHomepage_obj = new Homepage(driver);
		test.log(LogStatus.INFO, "Initialize the youtubeHomepage_obj with the driver");
		
		youtubeHomepage_obj.EnterSearch(keyword);
		test.log(LogStatus.INFO, "Enter '"+keyword+"' in search bar");

		youtubeHomepage_obj.SearchClick();
		test.log(LogStatus.INFO, "Click the Search button");
		
		searchResultspage_obj = new SearchResultsPage(driver);
		test.log(LogStatus.INFO, "Initialize the searchResultspage_obj with the driver");
		Thread.sleep(500);

		searchResultspage_obj.applyVideosFilter();
		test.log(LogStatus.INFO, "Apply Type = 'Video' Filter on the search results");
		Thread.sleep(500);

		videoTitle_searchResults = searchResultspage_obj.GetVideoTitle(videoIndex);
		test.log(LogStatus.INFO, "Save the title of video with index (" + Integer.toString(videoIndex+1) + "): " + "'" + videoTitle_searchResults + "'");
		
		searchResultspage_obj.selectVideo(videoIndex);
		test.log(LogStatus.INFO, "Choose the video with index: "+ Integer.toString(videoIndex+1));
		videoPage_obj = new VideoPage(driver);
		//Should use an implicit wait for the element to appear but I could not get it to work so this sleep command is to prevent failure.
		Thread.sleep(500);
				
		videoTitle_videoPage = videoPage_obj.getVideoTitle();
		test.log(LogStatus.INFO, "Save the title of the video playing: '" + videoTitle_videoPage + "'");
				
		//Compare the two titles to each other
		try 
		{
			Assert.assertEquals(videoTitle_searchResults , videoTitle_videoPage);
			test.log(LogStatus.PASS, "The video title in the search results matches the title when the video is selected");
			
			Screenshot_webele = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
		    ImageIO.write(Screenshot_webele.getImage(),"png",new File(screenshotsPath + "\\VideoTitleValidation" + Integer.toString(tcCounter)+"_PASS.jpeg"));		

		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "The video title in the search results doesn't match the title when the video is selected");
			
			Screenshot_webele = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
		    ImageIO.write(Screenshot_webele.getImage(),"png",new File(screenshotsPath + "\\VideoTitleValidation"+Integer.toString(tcCounter)+"_FAIL.jpeg"));		
	    
		}
		//End the test
		extentReport.endTest(test);

		driver.quit();

    }
}
