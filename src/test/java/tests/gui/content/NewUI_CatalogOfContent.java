package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.NewUI_Content;
import pageObjectModels.content.NewUI_Content_Dashboard;
import pageObjectModels.content.NewUI_Content_Dashboard_AnalyzeInsight;
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

	NewUI_Content_Dashboard_AnalyzeInsight analyzeInsightPage;
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

	@Test(priority = 1, groups = "groupA", description = "C82751 - Chrome: Testing That the user can search for a specific Dashboard owned by him")
	@Description("When I navigate to the content screen, and I search for dashboard created by me.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_CreatedBySameUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();
		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage
				.assert_dashboardName_isCorrect(testDataReader.getCellData("Automation_Dashboard_CatalogOfContent"));
	}

	@Test(priority = 2, groups = "groupA", description = "C82753 - Chrome: Testing that the user cansearch for a specificdashboard Sharedwith him as'View'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared view with me.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_SharedAsViewWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedView"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedView"));
	}

	@Test(priority = 3, groups = "groupA", description = "C82757 - Chrome: Testing that the user cansearch for a specificdashboard Sharedwith him as'Share'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared share with me.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_SharedAsShareWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedShare"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedShare"));
	}

	@Test(priority = 4, groups = "groupA", description = "C82758 - Chrome: Testing that the user cansearch for a specificdashboard Sharedwith him as'Edit'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared view withme.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_SharedAsEditWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedEdit"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SharedEdit"));
	}

	@Test(priority = 5, groups = "groupA", description = "C82759 - Chrome: Testing that the user cansearch for a dashboardcreated inside nested folders")
	@Description("When I navigate to the content screen, and I search for dashboard created inside a nested folder.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_CreatedInsideNestedFolder() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_InsideNestedFolder"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_InsideNestedFolder"));
	}

	@Test(priority = 6, groups = "groupA", description = "C82760 - Chrome: Testing that the user cansearch for a dashboardcreated with Arabic name")
	@Description("When I navigate to the content screen, and I search fordashboard created with Arabic name.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithArabicName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Arabic"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Arabic"));
	}

	@Test(priority = 7, groups = "groupA", description = "C82761 - Chrome: Testing that the user cansearch for a dashboardcreated withSpecial Characters name")
	@Description("When I navigate to the content screen, and I search for dashboard created with special characters name.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithSpecialCharactersName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SpecialCharacters"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_SpecialCharacters"));
	}

	@Test(priority = 8, groups = "groupA", description = "C82762 - Chrome: Testing that the user cansearch for a dashboardcreated with French Characters")
	@Description("When I navigate to the content screen, and I search for dashboard created with French name.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithFrenchName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_French"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_French"));
	}

	@Test(priority = 9, groups = "groupA", description = "C82763 - Chrome: Testing that the user cansearch for a dashboardcreated with Chinese characters")
	@Description("When I navigate to the content screen, and I search for dashboard created with Chinese name.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_WithChineseName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));
	}

	@Test(priority = 10, groups = "groupA", description = "C82768 - Chrome: Testing that the usercan search fora dashboardwhen thepage is in'List'view")
	@Description("When I navigate to the content screen, and I search for dashboard created in list view.Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_FromListView() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.changeCatalogView("table");

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Chinese"));
	}

	@Test(priority = 1, groups = "groupA", description = "C82769 - Chrome: Testing that when searching for a deleted dashboard,no data will be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard after deleting it.Then dashboard is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_Deleted() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.changeCatalogView("Card");
		newUI_allContentPage.click_dashboardFolder_properties_fromGridView(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Deleted"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newUI_allContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newUI_allContentPage.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newUI_allContentPage
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newUI_allContentPage.catalog_searchAndAssertResultNotExist_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Deleted"));
	}

	@Test(priority = 12, groups = "groupA", description = "C82770 - Chrome: Testing that whensearching for a dashboardthat isnot sharedwith the user,no datawill be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard created by admin and not shared.Then dashboard is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_NotSharedWithAnalyzer() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAndAssertResultNotExist_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_NotShared"));
	}

	@Test(priority = 13, groups = "groupA", description = "C82771 - Chrome: Testing that whensearching for a dashboardthat isnot sharedwith the user,no datawill be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard created by adminand not shared.Then dashboard is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboard_DashboardNotCreated() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAndAssertResultNotExist_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_DashboardNotCreated"));
	}

	@Test(priority = 14, groups = "groupA", description = "C82773 - Chrome: Testing That the user can search for a specific Folder owned by him")
	@Description("When I navigate to the content screen, and I search for dashboard created by me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_CreatedBySameUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage
				.assert_dashboardName_isCorrect(testDataReader.getCellData("Automation_Folder_CatalogOfContent"));
	}

	@Test(priority = 15, groups = "groupA", description = "C82774 - Chrome: Testing that the user can search for a specific Folder Shared with him as 'View'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared view with me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_SharedAsViewWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SharedView"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SharedView"));
	}

	@Test(priority = 16, groups = "groupA", description = "Chrome: Testing that the user can search for a specific Folder Shared with him as 'Share'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared share with me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_SharedAsShareWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SharedShare"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SharedShare"));
	}

	@Test(priority = 17, groups = "groupA", description = "C82776 - Chrome: Testing that the user can search for a specific Folder Shared with him as 'Edit'")
	@Description("When I navigate to the content screen, and I search for dashboard Shared view with me. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_SharedAsEditWithUser() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SharedEdit"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SharedEdit"));
	}

	@Test(priority = 18, groups = "groupA", description = "C82777 - Chrome: Testing that the user can search for a Folder created inside nested folders")
	@Description("When I navigate to the content screen, and I search for dashboard created inside a nested folder. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_CreatedInsideNestedFolder() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_InsideNestedFolder"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_InsideNestedFolder"));
	}

	@Test(priority = 19, groups = "groupA", description = "C82778 - Chrome: Testing that the user can search for a Folder created with Arabic name")
	@Description("When I navigate to the content screen, and I search for dashboard created with Arabic name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_WithArabicName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Arabic"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Arabic"));
	}

	@Test(priority = 20, groups = "groupA", description = "C82779 - Chrome: Testing that the user can search for a Folder created with Special Characters name")
	@Description("When I navigate to the content screen, and I search for dashboard created with special characters name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_WithSpecialCharactersName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SpecialCharacters"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_SpecialCharacters"));
	}

	@Test(priority = 21, groups = "groupA", description = "C82780 - Chrome: Testing that the user can search for a Folder created with French Characters")
	@Description("When I navigate to the content screen, and I search for dashboard created with French name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_WithFrenchName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_French"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_French"));
	}

	@Test(priority = 22, groups = "groupA", description = "C82781 - Chrome: Testing that the user can search for a Folder created with Chinese characters")
	@Description("When I navigate to the content screen, and I search for dashboard created with Chinese name. Then dashboard is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_WithChineseName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Chinese"));
	}

	@Test(priority = 23, groups = "groupA", description = "C82786 - Chrome: Testing that the user can search for a Folder when the page is in 'List' view")
	@Description("When I navigate to the content screen, and I search for folder created in list view.Then Folder is found successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_FromListView() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.changeCatalogView("table");

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Chinese"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Chinese"));
	}

	@Test(priority = 24, groups = "groupA", description = "C82787 - Chrome: Testing that when searching for a deleted Folder , no data will be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard after deleting it.Then Folder is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_Deleted() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.changeCatalogView("Card");
		newUI_allContentPage.click_dashboardFolder_properties_fromGridView(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Deleted"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newUI_allContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newUI_allContentPage = new NewUI_Content(driver);

		newUI_allContentPage.assert_splashNotificationMessage_equalsExpected("Folder Deleted");
		newUI_allContentPage
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one folder.");
		newUI_allContentPage.catalog_searchAndAssertResultNotExist_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_Deleted"));
	}

	@Test(priority = 25, groups = "groupA", description = "C82788 - Chrome: Testing that when searching for a Folder that is not shared with the user, no data will be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard created by admin and not shared.Then dashboard is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_NotSharedWithAnalyzer() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAndAssertResultNotExist_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_NotShared"));
	}

	@Test(priority = 26, groups = "groupA", description = "C82789 - Chrome: Testing that when searching for a Folder that doesn't exist , no data will be displayed")
	@Description("When I navigate to the content screen, and I search for dashboard created by admin and not shared.Then dashboard is not found.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolder_FolderNotCreated() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAndAssertResultNotExist_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_FolderNotCreated"));
	}

	@Test(priority = 27, groups = "groupA", description = "C81618 - Chrome: Details : Testing that the dashboard details are displayed")
	@Description("When I navigate to the content screen, and I open dashboard and I click on details. Then details section is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_DashboardScreen_DetailsSectionDisplayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_DashboardDetailsSection"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.click_detailsButton();
		newUI_dashboardPage.assert_detailsSectionContentDisplayed(
				testDataReader.getCellData("This information is for Automation testing purposes."));
	}

	@Test(priority = 28, groups = "groupA", description = "C81617 - Chrome: Details : Testing that the dashboard detail 'Owned by' is displayed")
	@Description("When I navigate to the content screen, and I open dashboard and I click on details. Then Owned By detail is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_DashboardScreen_DetailsSection_OwnedByDisplayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		String dashboardName = newUI_allContentPage.addNewCatalogItem("dashboard");

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(dashboardName);
		newUI_dashboardPage.click_detailsButton();
		newUI_dashboardPage.assert_detailsSection_ownedByDisplayed(testDataReader.getCellData("Username"));
	}

	@Test(priority = 29, groups = "groupA", description = "C81619 - Chrome: Details : Testing that the dashboard detail 'Last modified' is displayed")
	@Description("When I navigate to the content screen, and I open dashboard and I click on details. Then Modified By detail is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_DashboardScreen_DetailsSection_ModifiedByDisplayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_DashboardDetailsSection_ModifiedBy"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.insightSettings_clickOnMenuItem("Edit");

		analyzeInsightPage = new NewUI_Content_Dashboard_AnalyzeInsight(driver);
		analyzeInsightPage.setInsightName();

		subHeaderObject = new NewUI_Skeleton(driver);
		subHeaderObject.Click_done();

		newUI_allContentPage.navigate_toURL();
		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_DashboardDetailsSection_ModifiedBy"));

		newUI_dashboardPage.click_detailsButton();
		newUI_dashboardPage.assert_detailsSection_modifiedByDisplayed(testDataReader.getCellData("Username"));
	}

	@Test(priority = 30, groups = "groupA", description = "C81620 - Chrome: Details : Testing that the dashboard detail 'Modified by' is displayed")
	@Description("When I navigate to the content screen, and I open dashboard and I click on details. Then Last Modified detail is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_DashboardScreen_DetailsSection_LastModifiedDisplayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_DashboardDetailsSection_ModifiedBy"));

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.click_detailsButton();
		newUI_dashboardPage.assert_detailsSection_lastModifiedDisplayed();
	}

	@Test(priority = 31, groups = "groupA", description = "C82772_1 - Chrome: Testing that All user types can search for dashboards in content page")
	@Description("When I login with Analyzer, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_AnalyzerUser_CanSearchForDashboards() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAndAssertResultsDisplayed_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_TestingSearchWithAllUsers"));
	}

	@Test(priority = 32, groups = "groupA", description = "C82790_1 - Chrome: Testing that All user types can search for Folder in content page")
	@Description("When I login with Analyzer, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_AnalyzerUser_CanSearchForFolders() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.assert_folderName_correct(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));
	}

	@Test(priority = 33, groups = "groupA", description = "C83986 - Chrome: Testing that in case the number of folders / Dashboards under a folder is 1, the grammar is correct.")
	@Description("When I navigate to the content page, Then I'll find that folders written under them how many folders and dahsboards with the right grammer.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_RightGrammerAndNumberOfFoldersAndDashboardsWrittenUnderFolderName_IsOne() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.assert_folderCardDescription_grammerAndCountCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_GrammerAndCountUnderFolder"),
				testDataReader.getCellData("FoldersIsOneAndDashboardsIsOne"));
	}

	@Test(priority = 34, groups = "groupA", description = "C83987 - Chrome: Testing that the meta data displayed under a folder reflects the correct # of folders and dashboards.")
	@Description("When I navigate to the content page, Then I'll find that folders written under them how many folders and dahsboards with the right grammer.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_RightGrammerAndNumberOfFoldersAndDashboardsWrittenUnderFolderName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.assert_folderCardDescription_grammerAndCountCorrect(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_GrammerAndCountUnderFolder_Number2"),
				testDataReader.getCellData("FoldersIsZeroAndDashboardsIsTwo"));
	}

	@Test(priority = 35, groups = "groupA", description = "C83988 - Chrome: Testing that User can sort dashboard by Owner.")
	@Description("When I navigate to the content page, and I sort dahsboards by owner, Then dashboard witll be sorted successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_Sort_ByOwner() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Sort"));

		newUI_allContentPage.click_onSorting("Owner");
		newUI_allContentPage.assert_sortingIsCorrect("Owner",
				testDataReader.getCellData("SortDashboardByOwner", "Data1"),
				testDataReader.getCellData("SortDashboardByOwner", "Data2"),
				testDataReader.getCellData("SortDashboardByOwner", "Data3"));
	}

	@Test(priority = 36, groups = "groupA", description = "C83989 - Chrome: Testing that user can sort dashboard by Name.")
	@Description("When I navigate to the content page, and I sort dahsboards by NAme, Then dashboard witll be sorted successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_Sort_ByName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Sort"));

		newUI_allContentPage.click_onSorting("Name");
		newUI_allContentPage.assert_sortingIsCorrect("Name", testDataReader.getCellData("SortDashboardByName", "Data1"),
				testDataReader.getCellData("SortDashboardByName", "Data2"),
				testDataReader.getCellData("SortDashboardByName", "Data3"));
	}

	@Test(priority = 37, groups = "groupA", description = "C83990 - Chrome: Testing that user can sort dashboard by Last Modified.")
	@Description("When I navigate to the content page, and I sort dahsboards by Last Modified, Then dashboard witll be sorted successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_Sort_ByLastModified() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_Sort"));

		newUI_allContentPage.click_onSorting("Last Modified");
		newUI_allContentPage.assert_sortingIsCorrect("Modified By",
				testDataReader.getCellData("SortDashboardByLastModified", "Data1"),
				testDataReader.getCellData("SortDashboardByLastModified", "Data2"),
				testDataReader.getCellData("SortDashboardByLastModified", "Data3"));
	}
	/// =================/=================/=================/=================

	@Test(priority = 38, description = "C83985_2 - Chrome: Testing That the profile pic of the dashboard/Folder owner is displayed when searching.")
	@Description("When I login, And I search for dashboard. Then search result will display profile picture of the creator of the dashboard.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForDashboards_ProfilePictureDisplayedInSearchBar() {
		logIn("Data6");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndAssert_resultsDisplayProfilePicture_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_ProfilePicture"));
		logOut();
	}

	@Test(priority = 39, groups = "group", description = "C82772_2 - Chrome: Testing that All user types can search for dashboards in content page")
	@Description("When I login with Admin, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_AdminUserCanSearchForDashboards() {
		logIn("Data2");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndAssertResultsDisplayed_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 40, description = "C82772_3 - Chrome: Testing that All user types can search for dashboards in content page")
	@Description("When I login with Individual, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_IndividualUserCanSearchForDashboards() {
		logIn("Data3");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndAssertResultsDisplayed_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 41, description = "C82772_4 - Chrome: Testing that All user types can search for dashboards in content page")
	@Description("When I login with Schema Manager, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SchemaManagerUserCanSearchForDashboards() {
		logIn("Data4");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndAssertResultsDisplayed_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 42, description = "C82772_5 - Chrome: Testing that All user types can search for dashboards in content page")
	@Description("When I login with normal user, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_userUserCanSearchForDashboards() {
		logIn("Data5");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndAssertResultsDisplayed_contentSearchBox(
				testDataReader.getCellData("Automation_Dashboard_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 43, description = "C82790_2 - Chrome: Testing that All user types can search for Folder in content page")
	@Description("When I login with Admin, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_AdminUserCanSearchForFolders() {
		logIn("Data2");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.assert_folderName_correct(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 44, description = "C82790_3 - Chrome: Testing that All user types can search for Folder in content page")
	@Description("When I login with Individual, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_IndividualUserCanSearchForFolders() {
		logIn("Data3");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.assert_folderName_correct(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 45, description = "C82790_4 - Chrome: Testing that All user types can search for Folder in content page")
	@Description("When I login with Schema Manager, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SchemaManagerUserCanSearchForFolders() {
		logIn("Data4");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.assert_folderName_correct(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	@Test(priority = 46, description = "C83985_1 - Chrome: Testing That the profile pic of the dashboard/Folder owner is displayed when searching.")
	@Description("When I login, And I search for Folder. Then search result will display profile picture of the creator of the Folder.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_SearchForFolders_ProfilePictureDisplayedInSearchBar() {
		logIn("Data6");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAndAssert_resultsDisplayProfilePicture_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_ProfilePicture"));
		logOut();
	}

	@Test(priority = 47, description = "C82790_5 - Chrome: Testing that All user types can search for Folder in content page")
	@Description("When I login with normal user, And I search for dashboard. Then search is working normally.")
	@Severity(SeverityLevel.NORMAL)
	public void CatalogOfContent_userUserCanSearchForFolders() {
		logIn("Data5");
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.catalog_searchAssertAndOpenResults_contentSearchBox(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.assert_folderName_correct(
				testDataReader.getCellData("Automation_Folder_CatalogOfContent_TestingSearchWithAllUsers"));
		logOut();
	}

	/// =================/=================/=================/=================
	public void logIn(String ColumnName) {
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", ColumnName),
				testDataReader.getCellData("Username", ColumnName), testDataReader.getCellData("Password", ColumnName));
	}

	public void logOut() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.expandUserMenu();
		newHeaderObject.signOut();
		loginPage = new NewUI_Login(driver);
		loginPage.goToSignInPage_fromSignOutPage();
	}

	@BeforeGroups("groupA")
	public void beforeGroup() {
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
	}

	@AfterGroups("groupA")
	public void afterGroup() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.expandUserMenu();
		newHeaderObject.signOut();
		loginPage = new NewUI_Login(driver);
		loginPage.goToSignInPage_fromSignOutPage();
		loginPage.navigate_toURL();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "catalogOfContent_newUI/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
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
