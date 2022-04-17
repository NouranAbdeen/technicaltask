package youtubePageModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage {
	
	//VERY bad practice to use xpath, but I struggled with these elements specifically and time was running out.
	@FindBy(xpath = "/html/body/ytd-app/div[1]/ytd-page-manager/ytd-search/div[1]/ytd-two-column-search-results-renderer/div/ytd-section-list-renderer/div[1]/div[2]/ytd-search-sub-menu-renderer/div[1]/div/ytd-toggle-button-renderer/a")
	private WebElement filtersButton;
	@FindBy(xpath = "/html/body/ytd-app/div[1]/ytd-page-manager/ytd-search/div[1]/ytd-two-column-search-results-renderer/div/ytd-section-list-renderer/div[1]/div[2]/ytd-search-sub-menu-renderer/div[1]/iron-collapse/div/ytd-search-filter-group-renderer[2]/ytd-search-filter-renderer[1]/a")
	private WebElement videosFilter;
	@FindBy(xpath = "/html/body/ytd-app/div[1]/ytd-page-manager/ytd-search/div[1]/ytd-two-column-search-results-renderer/div/ytd-section-list-renderer/div[2]/ytd-item-section-renderer/div[3]")
	private WebElement videoRendererContainer;
	
	//Initializing all the web elements
	public SearchResultsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Applies the "Video" filter
	public void applyVideosFilter()
	{
		filtersButton.click();
		videosFilter.click();
	}
	
	//Gets the title of the video according to index
	public String GetVideoTitle(int videoIndex)
	{
		return videoRendererContainer.findElements(By.cssSelector("ytd-video-renderer")).get(videoIndex).findElement(By.cssSelector("h3 > a")).getAttribute("title");
	}
		
	//Selects a video from the search results according to the given video index
	public void selectVideo(int videoIndex)
	{
		videoRendererContainer.findElements(By.cssSelector("ytd-video-renderer")).get(videoIndex).findElement(By.cssSelector("h3 > a")).click();
	}
}
