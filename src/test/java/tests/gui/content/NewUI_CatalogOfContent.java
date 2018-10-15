package tests.gui.content;

import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.NewUI_AllContent_Dashboard_AnalyzeInsight;
import pageObjectModels.content.NewUI_Content;
import pageObjectModels.content.NewUI_Content_Dashboard;
import pageObjectModels.content.NewUI_Content_Dashboard_ScheduleDashboard;
import pageObjectModels.content.NewUI_Content_Dashboard_SendDashboard;
import pageObjectModels.content.NewUI_Content_Folder;
import pageObjectModels.data.NewUI_DataSources;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.main.NewUI_Header;
import pageObjectModels.main.NewUI_Skeleton;
import pageObjectModels.scheduler.NewUI_Dashboards;
import pageObjectModels.scheduler.NewUI_SchemaLoads;
import pageObjectModels.schemas.NewUI_SchemaList;
import pageObjectModels.schemas.NewUI_SchemaList_SchemaView;
import pageObjectModels.security.NewUI_Groups;
import pageObjectModels.security.NewUI_Groups_Group;
import pageObjectModels.security.NewUI_Users;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class NewUI_CatalogOfContent {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	NewUI_Login loginPage;
	NewUI_Skeleton subHeaderObject;
	NewUI_DataSources dataSourcesPage;
	NewUI_SchemaList schemasPage;
	NewUI_SchemaList_SchemaView schemasViewPage;
	NewUI_Users usersPage;

	NewUI_AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;
	NewUI_Content_Dashboard newUI_dashboardPage;
	NewUI_Content_Dashboard_SendDashboard sendDashboardPage;
	NewUI_Content_Dashboard_ScheduleDashboard scheduleDashboardPage;
	NewUI_Content_Folder newUI_folderPage;

	NewUI_Groups groupsPage;
	NewUI_SchemaLoads schedulerSchemaLoadsPage;
	NewUI_Dashboards schedulerDashboardsPage;
	NewUI_Header newHeaderObject;
	NewUI_Content newUI_allContentPage;
	NewUI_Groups_Group groups_groupPage;

	// Declaring public variables that will be shared between tests

	@Test(priority = 1, description = "C82751 - Chrome: Testing That the user can search for a specific Dashboard owned by him")
	@Description("When I navigate to the content screen, and I search for dashboard created by me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_CreatedBySameUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage
				.assert_dashboardName_isCorrect(testDataReader.getCellData("Automation_Dashboard_CatalogOfContent"));
	}

	@Test(priority = 2, description = "C82753 - Chrome: Testing that the user can search for a specific dashboard Shared with him as 'View'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared view with me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_SharedAsViewWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedView"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedView"));
	}

	@Test(priority = 3, description = "C82757 - Chrome: Testing that the user can search for a specific dashboard Shared with him as 'Share'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared share with me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_SharedAsShareWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedShare"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedShare"));
	}

	@Test(priority = 4, description = "C82758 - Chrome: Testing that the user can search for a specific dashboard Shared with him as 'Edit'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared view with me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_SharedAsEditWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedEdit"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedEdit"));
	}

	@Test(priority = 5, description = "C82759 - Chrome: Testing that the user can search for a dashboard created inside nested folders")
	@Description("When I navigate to the content screen, and I search for dashboard created inside a nested folder. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_CreatedInsideNestedFolder() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_InsideNestedFolder"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_InsideNestedFolder"));
	}

	@Test(priority = 6, description = "C82760 - Chrome: Testing that the user can search for a dashboard created with Arabic name")
	@Description("When I navigate to the content screen, and I search for dashboard created with Arabic name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithArabicName() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Arabic"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Arabic"));
	}

	@Test(priority = 7, description = "C82761 - Chrome: Testing that the user can search for a dashboard created with Special Characters name")
	@Description("When I navigate to the content screen, and I search for dashboard created with special characters name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithSpecialCharactersName() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SpecialCharacters"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SpecialCharacters"));
	}

	@Test(priority = 8, description = "C82762 - Chrome: Testing that the user can search for a dashboard created with French Characters")
	@Description("When I navigate to the content screen, and I search for dashboard created with French name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithFrenchName() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_French"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_French"));
	}

	@Test(priority = 9, description = "C82763 - Chrome: Testing that the user can search for a dashboard created with Chinese characters")
	@Description("When I navigate to the content screen, and I search for dashboard created with Chinese name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithChineseName() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));
	}

	@Test(priority = 10, description = "C82768 - Chrome: Testing that the user can search for a dashboard when the page is in 'List' view")
	@Description("When I navigate to the content screen, and I search for dashboard created in list view. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_FromListView() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.changeCatalogView("table");

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));
	}

	@Test(priority = 11, description = "C82769 - Chrome: Testing that when searching for a deleted dashboard , no data will be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard after deleting it. Then dashboard is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_Deleted() {
		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.catalog_searchAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));
	}

	@BeforeMethod
	public void beforeMethod() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "catalogOfContent_newUI/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
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
