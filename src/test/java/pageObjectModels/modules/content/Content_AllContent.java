package pageObjectModels.modules.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.io.ExcelReader;

public class Content_AllContent {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content_allContent");

	//// Elements
	By header_allContentTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='All Content']");
	By body_folder_link;
	By body_folderProperties_link;
	By body_dashboard_link;
	By body_dashboardProperties_link;

	//// Functions
	public Content_AllContent(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_allContentTabIsSelected() {
		CustomAssertions.cAssertElementAttribute(driver, header_allContentTabHeader_link, "class", "selectedTab", true);
	}

	// Assert_folderIsDisplayed
	// Assert_dashboardIsDisplayed

	// Click_folder
	// Click_folderProperties
	// Click_dashboard
	// Click_dashboardProperties
}
