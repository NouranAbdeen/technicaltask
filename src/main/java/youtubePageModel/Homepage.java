package youtubePageModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;


public class Homepage {
	
	@FindBy(css = "#search-input > input")
	private WebElement searchText;
	@FindBy(id = "search-icon-legacy")
	private WebElement searchButton;
	
	//Initializes all the web elements
	public Homepage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Searches for a certain title
	public void EnterSearch(String title)
	{
		searchText.sendKeys(title);
	}
	
	//Clicks on the search button
	public void SearchClick()
	{
		searchButton.sendKeys(Keys.ENTER);
		searchButton.sendKeys(Keys.ENTER);
	}	
}
