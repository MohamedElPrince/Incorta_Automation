package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;

@Epic("Pagination Epic.")
public class AllContent_DashboardPagination {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login loginPage;
	AllContent allContentPage;
	Skeleton mainPage;
	AllContent_Dashboard dashboardPage;

	// Declaring public variables that will be shared between tests
	String paginationDashboardName, paginationInsightName;

	// before suite
	// user is already logged in
	// load sales schema
	// rename then import dashboard with settings (page size, type: pivot table, and
	// components)

	// dashboard name will be dynamic > store dashboard name
	// [paginationDashboardName]
	// pagination Insight Name??

	@Test(priority = 1, description = "C77296 - Chrome: Table Insight: Verify that the pagination UI is displayed correctly.")
	@Description("When I navigate to the target dashboard, then the correct UI elements (arrows and numbers) should exist")
	@Severity(SeverityLevel.NORMAL)
	public void verifyPaginationUiExists() {
		paginationDashboardName = "Pivot_Pagination Dashboard - Copy"; // to be removed
		paginationInsightName = "7amada"; // to be removed

		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(paginationDashboardName);

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(paginationDashboardName);
		dashboardPage.Assert_insightName(paginationInsightName);

		dashboardPage.Pagination_VerifyThat_UiElementsExist();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "pagination/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		// to be handled
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.Assert_correctVersionNumberIsDisplayed();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
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
