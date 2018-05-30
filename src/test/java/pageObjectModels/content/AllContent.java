package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class AllContent {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content_allContent");

	//// Elements
	By header_allContentTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='All Content']");
	By body_folder_link;
	By body_folderProperties_link;
	By body_dashboard_link;
	By body_dashboardProperties_link;
	By body_folderName_link;

	By popup_newDashboard_dashboardName_textBox = By.name("reportName");
	By popup_newDashboard_create_button = By.xpath("//button[@type='submit'][normalize-space()='Create']");

	By popup_newFolder_folderName = By.className("inputGroup newItemModal");
	
	//// Functions
	public AllContent(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_allContentTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_allContentTabHeader_link, "class", "selectedTab", true);
	}

	// Assert_folderIsDisplayed
	public void Assert_dashboardIsDisplayed(String name) {
		body_dashboard_link = By.xpath(" //section[@id=\"content\"]//a[@title='" + name + "']");
		Assertions.assertElementExists(driver, body_dashboard_link, true);
	}

	// Click_folder
	// Click_folderProperties
	// Click_dashboard
	// Click_dashboardProperties

	public String setNewDashboardName() {
		String newDashboardName = "Automation" + "_Dashboard_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_newDashboard_dashboardName_textBox, newDashboardName);
		ElementActions.click(driver, popup_newDashboard_create_button);
		return newDashboardName;
	}

	public void AddText_NewFolder(String FolderName)
	{
		ElementActions.type(driver,popup_newFolder_folderName , FolderName);
	}
	
	public void Assert_folderIsDisplayed(String FolderName)
	{
		body_folderName_link = By.xpath("//a[contains(string(),'"+FolderName+"')]");
		Assertions.assertElementExists(driver, body_folderName_link, true);
	}
}
