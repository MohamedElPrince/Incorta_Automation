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

		dashboardPage.Pagination_Verify_UiElementsExist();
	}

	@Test(priority = 2, description = "C77297 - Chrome: Table Insight: Verify that user can navigate to Next Page.", dependsOnMethods = {
			"verifyPaginationUiExists" })
	@Description("When I navigate to the target dashboard, and I assert that the next button exists, and I click the next button, Then the first result displayed will be (the last result +1)")
	@Severity(SeverityLevel.NORMAL)
	public void assertThatNextButtonWorks() {
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

		// first page = ^1 -
		// last page = number of number$
		// next page = ^(last record of current page + 1) -
		// previous page = - (first record of current page -1) of
		dashboardPage.Pagination_AssertThatNextButtonWorksAsExpected();
	}

	@Test(priority = 3, description = "C77298 - Chrome: Table Insight: Verify that user can navigate to Last Page.", dependsOnMethods = {
	"verifyPaginationUiExists" })
	@Description("When I navigate to the target dashboard, and I assert that the Last Page button exists, and I click the Last Page button, Then the Last Page result displayed will be (the total of records)")
	@Severity(SeverityLevel.NORMAL)
	public void assertThatLastPageButtonWorks() {
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
	
	dashboardPage.Pagination_AssertThatLastButtonWorksAsExpected();
	}
	
	@Test(priority = 4, description = "C77300 - Chrome: Table Insight: Verify that user can return back to first page.")
	@Description("When I navigate to the target dashboard, Given that I am not in the first page, And if I found that its the first page, I navigate to last page"
			+ " and next I click the first button, Then the first record result displayed will be ( 1 ) sample: ( 1 - No. of TotalNumber)")
	@Severity(SeverityLevel.NORMAL)
	public void assertThatFirstButtontWorks() {
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

		dashboardPage.Pagination_AssertThatFirstButtontWorksAsExpected();
	}
	

	@Test(priority = 5, description = "C77299 - Chrome: Table Insight: Verify that user can navigate to previous page")
	@Description("When I navigate to the target dashboard, and I assert that the Previous Page button exists, and I click the Previous Page button, Then the Previous Page result is displayed correctly")
	@Severity(SeverityLevel.NORMAL)

	public void assert_Pagination_Previous_Button() {
		
		paginationDashboardName = "Pivot_Pagination Dashboard - Copy"; // to be removed
		paginationInsightName = "7amada"; // to be removed

		// Assert navigation to allContent page
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		// Search for dashboard name in search bar and open it
		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(paginationDashboardName);

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(paginationDashboardName);
		dashboardPage.Assert_insightName(paginationInsightName);
		
		// Assert previous button is displayed and functional
		dashboardPage.Pagination_AssertThatPreviousButtonWorksAsExpected();
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