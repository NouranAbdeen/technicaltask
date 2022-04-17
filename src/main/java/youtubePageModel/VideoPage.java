package youtubePageModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VideoPage {
	
	@FindBy(css = "#container > h1 > yt-formatted-string")
	private WebElement videoTitle;
	
	//Initializing all the web elements
	public VideoPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Function return the title of the video currently running
	public String getVideoTitle()
	{
		return videoTitle.getAttribute("outerText").toString();
	}
		
}
