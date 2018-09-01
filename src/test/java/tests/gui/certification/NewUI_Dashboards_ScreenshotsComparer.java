package tests.gui.certification;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.NewUI_Content;
import pageObjectModels.content.NewUI_Content_Dashboard;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.login.NewUI_SignOut;
import pageObjectModels.main.NewUI_Header;

@Epic("incorta New UI Certification - Dashboards_ScreenshotsComparer.")
public class NewUI_Dashboards_ScreenshotsComparer {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;
	int totalDashboardsCounter = 0;
	int totalFoldersCounter = 0;
	int totalInsightsCounter = 0;

	// Declaring Page Objects that will be used throughout the test
	NewUI_Login newLoginPage;
	NewUI_SignOut newSignOutPage;
	NewUI_Header newHeaderObject;
	NewUI_Content newContentPage;
	NewUI_Content_Dashboard newDashboardPage;

	// Declaring public variables that will be shared between tests

	// Test Method / Dashboard Crawler
	@Test(priority = 1, description = "Dashboard Crawler...")
	@Description("Crawl all dashboards in all root and subfolders of a certain tenant, and take screenshots.")
	@Severity(SeverityLevel.CRITICAL)
	public void dashboardCrawler() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();
		newContentPage.changeCatalogView("Card");

		crawlDashboards();

		ReportManager.log("Total Folders Crawled: [" + totalFoldersCounter + "], Total Dashboards Crawled: ["
				+ totalDashboardsCounter + "], and Total Insights Crawled: [" + totalInsightsCounter + "].");
	}

	public void crawlDashboards() {
		crawlSubFoldersInCurrentFolder();
	}

	private void crawlSubFoldersInCurrentFolder() {
		crawlDashboardsInCurrentFolder();
		newContentPage = new NewUI_Content(driver);
		int foldersCount = newContentPage.cardView_countFolders();
		if (foldersCount > 0) {
			for (int folderIndex = 1; folderIndex <= foldersCount; folderIndex++) {
				totalFoldersCounter++;
				newContentPage = new NewUI_Content(driver);
				newContentPage.cardView_navigate_toContentTableFolder(folderIndex);
				crawlSubFoldersInCurrentFolder(); // for multiple layers of nested folder
				BrowserActions.navigateBack(driver);
				BrowserActions.refreshCurrentPage(driver);
			}
		}
	}

	private void crawlDashboardsInCurrentFolder() {
		BrowserActions.refreshCurrentPage(driver);
		newContentPage = new NewUI_Content(driver);
		int dashboardsCount = newContentPage.cardView_countDashboards();
		if (dashboardsCount > 0) {
			for (int dashboardIndex = 1; dashboardIndex <= dashboardsCount; dashboardIndex++) {
				totalDashboardsCounter++;
				newContentPage.cardView_navigate_toContentTableDashboard(dashboardIndex);
				newDashboardPage = new NewUI_Content_Dashboard(driver);
				newDashboardPage.assert_dashboardName_isCorrect(".*");
				crawlInsightsInCurrentDashboard();
				BrowserActions.navigateBack(driver);
			}
		}
	}

	private void crawlInsightsInCurrentDashboard() {
		BrowserActions.refreshCurrentPage(driver);
		newDashboardPage = new NewUI_Content_Dashboard(driver);
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
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "dashboards_ScreenshotsComparer/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		newLoginPage = new NewUI_Login(driver);
		newLoginPage.navigate_toURL();
		newLoginPage.verify_correctVersionNumberIsDisplayed();
		newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
	}

	@AfterMethod
	public void afterMethod() {
		ReportManager.getTestLog();
	}

	@AfterClass
	public void afterClass() {
		BrowserFactory.closeAllDrivers();
		ReportManager.getFullLog();
	}

}
