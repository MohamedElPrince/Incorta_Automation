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
	String Automation_Pivot_PaginationDashboard_PageSize20, paginationInsightName;

	// before suite
	// user is already logged in
	// load sales schema
	// rename then import dashboard with settings (page size, type: pivot table, and
	// components)

	// dashboard name will be dynamic > store dashboard name
	// [Automation_Pivot_PaginationDashboard_PageSize20]
	// pagination Insight Name??

	@Test(priority = 1, description = "C77296 - Chrome: Table Insight: Verify that the pagination UI is displayed correctly.")
	@Description("When I navigate to the target dashboard, then the correct UI elements (arrows and numbers) should exist")
	@Severity(SeverityLevel.NORMAL)
	public void verifyPaginationUiExists() {
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));
		dashboardPage.pagination_verify_uiElementsExist();
	}

	@Test(priority = 2, description = "C77297 - Chrome: Table Insight: Verify that user can navigate to Next Page.", dependsOnMethods = {
			"verifyPaginationUiExists" })
	@Description("When I navigate to the target dashboard, and I assert that the next button exists, and I click the next button, Then the first result displayed will be (the last result +1)")
	@Severity(SeverityLevel.NORMAL)
	public void assertThatNextButtonWorks() {
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		// first page = ^1 -
		// last page = number of number$
		// next page = ^(last record of current page + 1) -
		// previous page = - (first record of current page -1) of
		dashboardPage.pagination_assertThatNextButtonWorksAsExpected();
	}

	@Test(priority = 3, description = "C77298 - Chrome: Table Insight: Verify that user can navigate to Last Page.", dependsOnMethods = {
			"verifyPaginationUiExists" })
	@Description("When I navigate to the target dashboard, and I assert that the Last Page button exists, and I click the Last Page button, Then the Last Page result displayed will be (the total of records)")
	@Severity(SeverityLevel.NORMAL)
	public void assertThatLastPageButtonWorks() {
	allContentPage = new AllContent(driver);
	allContentPage.Navigate_toURL();
	allContentPage.Assert_allContentTabIsSelected();
	
	mainPage = new Skeleton(driver);
	mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
	
	dashboardPage = new AllContent_Dashboard(driver);
	dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
	dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));
	
	dashboardPage.pagination_assertThatLastButtonWorksAsExpected();
	}
	
	@Test(priority = 4, description = "C77300 - Chrome: Table Insight: Verify that user can return back to first page.")
	@Description("When I navigate to the target dashboard, Given that I am not in the first page, And if I found that its the first page, I navigate to last page"
			+ " and next I click the first button, Then the first record result displayed will be ( 1 ) sample: ( 1 - No. of TotalNumber)")
	@Severity(SeverityLevel.NORMAL)
	public void assertThatFirstButtontWorks() {
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		dashboardPage.pagination_assertThatFirstButtontWorksAsExpected();
	}

	@Test(priority = 5, description = "C77299 - Chrome: Table Insight: Verify that user can navigate to previous page")
	@Description("When I navigate to the target dashboard, and I assert that the Previous Page button exists, and I click the Previous Page button, Then the Previous Page result is displayed correctly")
	@Severity(SeverityLevel.NORMAL)

	public void assert_Pagination_Previous_Button() {
		// Assert navigation to allContent page
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		// Search for dashboard name in search bar and open it
		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		dashboardPage.pagination_assertThatPreviousButtonWorksAsExpected();
	}

	//Prerequisite: Dashboard [Automation_Pivot_Pagination_Dashboard] created + Insight with pagination created [Automation_Insight] + pagination on page should have more than one page
	@Test(priority = 6, description = "C77301 - Chrome: Table Insight: Verify that when reaching the end of the set, the buttons on the right should be disabled and vice-versa")
	@Description("When I navigate to the target dashboard, and I assert that the first arrow button disabled, and I click the next Page button, Then the all buttons is enabled")
	@Severity(SeverityLevel.NORMAL)
	public void assert_Pagination_Disabled_Buttons() {
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));
		dashboardPage.Pagination_AssertThatFirstArrow_Disabled();

		dashboardPage.Pagination_NavigateToNextPage();
		dashboardPage.Pagination_AssertThatFirstButton_Enabled();
		dashboardPage.Pagination_AssertThatPreviousButton_Enabled();
		dashboardPage.Pagination_AssertThatNextButton_Enabled();
		dashboardPage.Pagination_AssertThatLastButton_Enabled();
		
		dashboardPage.Pagination_NavigateToLastPage_Assert_LastButtonDisabled();
		
	}

	//Prerequisite: Dashboard [Automation_Pivot_Pagination_Dashboard] created + Insight with pagination created [Automation_Insight] + Update page size to be 5
	@Test(priority = 7, description = "C77476 - Chrome: Table insight: Verify that the pagination starts with 1.")
	@Description("When I navigate to the target dashboard, Then The Numbering of the pagination is correctly displayed")
	@Severity(SeverityLevel.NORMAL)
	public void assert_PaginationNumbering_Correct() {
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		dashboardPage.Pagination_AssertThatPaginationStartsWithNumber1();
		dashboardPage.Pagination_Assert_NumberOfRowsEqualTo_LastRecordInCurrentPageInPagination();
		dashboardPage.pagination_assertThatLastButtonWorksAsExpected();

	}

	//Prerequisite: Dashboard [Automation_Pagination_Dashboard_PageSize] created without changing page size in it + Insight with pagination created [Automation_Insight]
	//Pagination_Assert_PageSize function is not working which causing next TC to fail
	@Test(priority = 8, description = "C77488 - Chrome: Table insight: Check that 'Page Size' Setting is displayed correctly and has default value '1000'.")
	@Description("When I navigate to the target dashboard,and I click on use table insights and I click on settings, Then The page size will by default 1000")
	@Severity(SeverityLevel.NORMAL)
	public void assert_Pagination_PageSizeIs1000ByDefault() {

		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_DefaultPageSize"));
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_DefaultPageSize"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		dashboardPage.Pagination_ClickOnEditInsight();
		mainPage.Open_SettingsList();
		dashboardPage.Pagination_Assert_PageSize(testDataReader.getCellData("DefaultPageSize"));
	}

	//Prerequisite: Dashboard [Automation_Pagination_Dashboard_PageSize] created without changing page size in it + Insight with pagination created [Automation_Insight]
	@Test(priority = 9, description = "C77489 - Chrome: Table insight: check that 'Page Size' can be changed and saved correctly.")
	@Description("When I navigate to the target dashboard,and I click on use table insights and I click on settings and I update the page size to 10, Then The page size will be 10 in the table.")
	@Severity(SeverityLevel.NORMAL)
	public void assert_Pagination_PageSizeCanBeChangedAndSaved() {

		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_PageSize20"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		Pagination_AddPageSize_AssertPageSizeIsCorrect(testDataReader.getCellData("PageSize"));
		Pagination_AddPageSize_AssertPageSizeIsCorrect(testDataReader.getCellData("PageSize5"));
	}

	//Prerequisite: Dashboard [Automation_Pagination_Dashboard_PageSize] created without changing page size in it + Insight with pagination created [Automation_Insight]
	@Test(priority = 10, description = "C77490 - Chrome: Table insight: Check that 'Page Size' value is Positive integer number only.")
	@Description("When I navigate to the target dashboard,and I click on use table insights and I click on settings and I update the page size to non positive integer value, Then The page size will not accept it and will return to the default value.")
	@Severity(SeverityLevel.NORMAL)
	public void assert_Pagination_PageSize_DoNotAccept_NonPositiveIntegerValue() {

		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_DefaultPageSize"));
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Pivot_PaginationDashboard_DefaultPageSize"));
		dashboardPage.assert_insightName(testDataReader.getCellData("paginationInsightName"));

		dashboardPage.Pagination_ClickOnEditInsight();
		mainPage.Open_SettingsList();
		dashboardPage.Pagination_AddPageSize(testDataReader.getCellData("NonPositiveIntegerPageSize"));
		dashboardPage.Pagination_Assert_PageSizeNotEquelToNumberOfRowsInTable(testDataReader.getCellData("NonPositiveIntegerPageSize"));
	}

	

	public void Pagination_AddPageSize_AssertPageSizeIsCorrect(String PageSize)
	{
		dashboardPage.Pagination_ClickOnEditInsight();
		mainPage = new Skeleton(driver);
		mainPage.Open_SettingsList();

		dashboardPage.Pagination_AddPageSize(PageSize);
		mainPage.Click_doneAndRefresh();
		
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Pagination_Assert_NumberOfRowsEqualTo_LastRecordInCurrentPageInPagination();
		
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "pagination/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		// to be handled
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.Verify_correctVersionNumberIsDisplayed();
		loginPage.UserLogin(testDataReader.getCellData("TenantDemo"), testDataReader.getCellData("Username"),
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