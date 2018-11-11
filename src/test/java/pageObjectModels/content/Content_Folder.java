package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.validation.Assertions;

public class Content_Folder {
    //// Variables
    private WebDriver driver;

    //// Elements
    private By body_dashboardName;

    private By body_folderDashboardProperties_Button;
    private By body_dashboardProperties_listOption;
    private By popup_deleteDashboard_confirmContent = By.xpath("//div[@class='ant-confirm-content']/div");
    private By popup_deleteDashboard_delete_Button = By.xpath("//button[contains(.,'Delete')]");
    private By deleteDashboard_confirmationMessage = By.xpath(
	    "//div[@class='ant-notification-notice-description'][contains(.,\"You've successfully deleted one dashboard.\")]");
    private By popup_deleteDashboard_delete_Button_loading = By
	    .xpath("//button[contains(.,’Delete’)][@class=‘ant-btn ant-btn-primary ant-btn-loading']");
    private By body_dashboardName_folderName;

    private By subHeader_folderName;

    //// Functions
    public Content_Folder(WebDriver driver) {
	this.driver = driver;
    }

    public void Assert_DashboardExist(String DashboardName) {
	body_dashboardName = By.xpath("//div[@class='inc-card-title']//span[text()= '" + DashboardName + "']");
	Assertions.assertElementExists(driver, body_dashboardName, true);
    }

    public void Assert_DashboardNotExist(String DashboardName) {
	body_dashboardName = By.xpath("//div[@class='inc-card-title']//span[text()= '" + DashboardName + "']");
	Assertions.assertElementExists(driver, body_dashboardName, false);
    }

    public void click_dashboardFolder_properties(String FolderName) {
	body_folderDashboardProperties_Button = By.xpath("//div[@class='inc-card-title']//span[text()= '" + FolderName
		+ "']//parent::div//following-sibling::div//button");
	ElementActions.click(driver, body_folderDashboardProperties_Button);
    }

    public void Click_DashboardProperties_ManageDashboardButtons(String Actions) {
	body_dashboardProperties_listOption = By.xpath(
		"//div[contains(@class,'ant-dropdown')][not(contains(@class,'ant-dropdown-hidden'))]//span[text()='"
			+ Actions + "']");
	ElementActions.click(driver, body_dashboardProperties_listOption);
    }

    public void assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(String DashboardName) {
	Assertions.assertElementAttribute(driver, popup_deleteDashboard_confirmContent, "text",
		"Heads up! Deleting " + DashboardName + " can't be undone.", 1, true);
    }

    public void deleteDashboard_popup_click_confirmationButton_delete() {
	ElementActions.click(driver, popup_deleteDashboard_delete_Button);
    }

    public void assert_deleteDashboard_popup_confirmationMessageDisplayed() {
	Assertions.assertElementExists(driver, popup_deleteDashboard_delete_Button_loading, false);
	Assertions.assertElementExists(driver, deleteDashboard_confirmationMessage, true);
    }

    public void assert_dashboard_folder_notExist(String DashboardFolderName) {
	BrowserActions.refreshCurrentPage(driver);
	body_dashboardName_folderName = By.xpath("//span[text()='" + DashboardFolderName + "']");
	Assertions.assertElementExists(driver, body_dashboardName_folderName, false);
    }

    public void assert_folderName_correct(String FolderName) {
	subHeader_folderName = By.xpath("//h1[contains(.,'" + FolderName + "')]");
	Assertions.assertElementExists(driver, subHeader_folderName, true);
    }

}