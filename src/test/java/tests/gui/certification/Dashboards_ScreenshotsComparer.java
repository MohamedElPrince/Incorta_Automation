package tests.gui.certification;

import java.io.File;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserActions;
import com.shaft.browser.BrowserFactory;
import com.shaft.image.ImageProcessingActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.Content;
import pageObjectModels.content.Content_Dashboard;
import pageObjectModels.login.Login;
import pageObjectModels.login.SignOut;
import pageObjectModels.main.Header;

@Epic("incorta New UI Certification - Dashboards_ScreenshotsComparer.")
public class Dashboards_ScreenshotsComparer {
    // Declaring web-driver and excel reader instances
    WebDriver driver;
    ExcelFileManager testDataReader;
    int totalDashboardsCounter = 0;
    int totalFoldersCounter = 0;
    int totalInsightsCounter = 0;
    boolean isRegularFolder = false;

    // Declaring Page Objects that will be used throughout the test
    Login newLoginPage;
    SignOut newSignOutPage;
    Header newHeaderObject;
    Content newContentPage;
    Content_Dashboard newDashboardPage;

    // Declaring public variables that will be shared between tests

    // Test Method / Dashboard Crawler
    @Test(priority = 1, description = "TC001 - Dynamically Crawl all Folders, Dashboards, and Insights.")
    @Description("Crawl all dashboards in all root and subfolders of a certain tenant, and take screenshots.")
    @Severity(SeverityLevel.CRITICAL)
    public void dashboardCrawler() {
	newContentPage = new Content(driver);
	newContentPage.navigate_toURL();

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");
	// newContentPage.changeCatalogView("Card");

	crawlDashboards();
    }

    @Test(priority = 2, description = "TC002 - Compare newly taken screenshots against refrence images.")
    public void compareFolders() {
	String refrenceFolderPath = System.getProperty("testDataFolderPath") + "dashboards_ScreenshotsComparer/20181024-233430";

	String testDirectoryPath = System.getProperty("allureResultsFolderPath") + "screenshots/";
	File testDirectory = new File(testDirectoryPath);
	String[] testFolders = testDirectory.list();
	Arrays.sort(testFolders);

	String testFolderPath = System.getProperty("allureResultsFolderPath") + "screenshots/" + testFolders[testFolders.length - 1];

	ImageProcessingActions.compareImageFolders(refrenceFolderPath, testFolderPath, 98);
    }

    public void crawlDashboards() {
	crawlSubFoldersInCurrentFolder();
    }

    private void crawlSubFoldersInCurrentFolder() {
	crawlDashboardsInCurrentFolder();
	newContentPage = new Content(driver);
	int foldersCount = newContentPage.cardView_countFolders();
	if (foldersCount > 0) {
	    for (int folderIndex = 1; folderIndex <= foldersCount; folderIndex++) {
		totalFoldersCounter++;
		newContentPage = new Content(driver);
		newContentPage.cardView_navigate_toContentTableFolder(folderIndex);
		crawlSubFoldersInCurrentFolder(); // for multiple layers of nested folder
		BrowserActions.navigateBack(driver);
		BrowserActions.refreshCurrentPage(driver);
	    }
	}
    }

    private void crawlDashboardsInCurrentFolder() {
	if (isRegularFolder) {
	    BrowserActions.refreshCurrentPage(driver);
	}
	isRegularFolder = true;
	newContentPage = new Content(driver);
	int dashboardsCount = newContentPage.cardView_countDashboards();
	if (dashboardsCount > 0) {
	    for (int dashboardIndex = 1; dashboardIndex <= dashboardsCount; dashboardIndex++) {
		totalDashboardsCounter++;
		newContentPage.cardView_navigate_toContentTableDashboard(dashboardIndex);
		newDashboardPage = new Content_Dashboard(driver);
		// newDashboardPage.waitForDashboardToFullyLoad(); //called inside the
		// constructor
		newDashboardPage.reportcurrentDashboardURL();
		newDashboardPage.verify_dashboardName_matches(".*");
		crawlInsightsInCurrentDashboard();
		BrowserActions.navigateBack(driver);
	    }
	}
    }

    private void crawlInsightsInCurrentDashboard() {
	// BrowserActions.refreshCurrentPage(driver);
	newDashboardPage = new Content_Dashboard(driver);
	int insightsCount = newDashboardPage.countInsights();
	if (insightsCount > 0) {
	    for (int insightIndex = 1; insightIndex <= insightsCount; insightIndex++) {
		totalInsightsCounter++;
		newDashboardPage.assert_insightContent_isDisplayed(insightIndex);
	    }
	}
    }

    @BeforeClass
    public void beforeClass() {
	System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "dashboards_ScreenshotsComparer/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	driver = BrowserFactory.getBrowser(testDataReader);
	// BrowserActions.setWindowSize(driver, 1920, 1080);

	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.verify_correctVersionNumberIsDisplayed();
	// newLoginPage.userLogin("demo", "admin", "admin");
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"), testDataReader.getCellData("Password"));
    }

    @AfterMethod
    public void afterMethod() {
	ReportManager.getTestLog();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	ReportManager.log("Total Folders Crawled: [" + totalFoldersCounter + "], Total Dashboards Crawled: [" + totalDashboardsCounter + "], and Total Insights Crawled: [" + totalInsightsCounter + "].");

	BrowserFactory.closeAllDrivers();
	ReportManager.getFullLog();
    }

}
