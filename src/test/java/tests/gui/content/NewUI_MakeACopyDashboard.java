package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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

public class NewUI_MakeACopyDashboard {

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

	NewUI_Content newUI_allContentPage;
	NewUI_AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;
	NewUI_Content_Dashboard newUI_dashboardPage;
	NewUI_Content_Dashboard_SendDashboard sendDashboardPage;
	NewUI_Content_Dashboard_ScheduleDashboard scheduleDashboardPage;
	NewUI_Content_Folder newUI_folderPage;

	NewUI_Groups groupsPage;
	NewUI_SchemaLoads schedulerSchemaLoadsPage;
	NewUI_Dashboards schedulerDashboardsPage;
	NewUI_Header newHeaderObject;
	NewUI_Content newContentPage;
	NewUI_Groups_Group groups_groupPage;

	// Declaring public variables that will be shared between tests

	@Test(priority = 1, description = "C82973 - Chrome: Testing that 'Make a copy' is displayed in the dashboard's options menu")
	@Description("When I navigate to the content screen, and I click on dashboard properties. I'll find 'Make a copy' button exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyButton_Displayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.assert_dashboardProperties_manageDashboardButtons_displayed("Make a Copy");
	}

	@Test(priority = 2, description = "C82974 - Chrome: Testing that Make a copy Window appears when user clicks on 'Make a copy' in the dashboard's options menu")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy. 'Make a copy' popup will open.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyPopupScreen_Displayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_popup_displayed();
	}

	@Test(priority = 3, description = "C82975 - Chrome: Testing That 'Make a Copy' window contents are displayed successfully")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy. 'Make a copy' screen content is displayed successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyPopupScreen_ScreenContentDisplayed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_popupScreen_screenContentDisplayed();
	}

	@Test(priority = 4, description = "C82988 - Chrome: Testing the 'Copy' option")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and I select a folder to copy to and I click 'Copy'. Then Dashboard is copied successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Check_CopyOption_WorkNormally() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_Copy"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		String New_DashboardName = newUI_allContentPage.makeACopy_getDashboard_newName();

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyButton();

		// Before this step, Page have been refreshed. Need to check why it's
		// refreshing.
		// [ReportManager] [getCurrentURL] successfully performed. With the following
		// test data [http://35.184.27.139:9091/incorta/#/catalog]. @26-09-2018
		// 14:47:08.0596 PM
		// [ReportManager] [refreshCurrentPage] successfully performed. @26-09-2018
		// 14:47:10.0642 PM
		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.Assert_DashboardExist(New_DashboardName);
	}

	@Test(priority = 5, description = "C82988 - Chrome: Testing the 'Copy & Open' option")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and I select a folder to copy to and I click 'Copy & Open'. Then Dashboard is copied and opened successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Check_CopyAndOpenOption_WorkNormally() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_CopyAndOpen"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		String New_DashboardName = newUI_allContentPage.makeACopy_getDashboard_newName();

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(New_DashboardName);

		newUI_allContentPage.navigate_toURL();
		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.Assert_DashboardExist(New_DashboardName);
	}

	// Issue in assertion element not exist for Make A Copy screen
	// java.lang.AssertionError: Assertion Failed; an unhandled exception occured.
	@Test(priority = 6, description = "C82990 - Chrome: Testing the 'Cancel' option")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and I select a folder to copy to and I click 'Copy & Open'. Then Dashboard is copied and opened successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Check_CancelOption_WorkNormally() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_CancelCopy"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		String New_DashboardName = newUI_allContentPage.makeACopy_getDashboard_newName();

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCancelButton();

		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.assert_makeACopy_popup_notDisplayed();

		newUI_allContentPage.navigate_toURL();
		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.Assert_DashboardNotExist(New_DashboardName);
	}

	@Test(priority = 7, description = "C82976 - Chrome: Name the copied dashboard with a long name")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and add a long name to the copied dashbaord. Then It will be copied with It's displayed name normally")
	@Severity(SeverityLevel.NORMAL)
	public void Change_CopiedDashboardName_WithLongName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_CopyDashboard_LongDashboardName"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.makeACopy_addDashboard_newName(testDataReader.getCellData("LongDashboardName"));

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(testDataReader.getCellData("LongDashboardName"));
	}

	// Need to check function [assert_dashboardName_isCorrect] as assertion is not
	// working with special characters.
	// java.lang.AssertionError: Assertion Failed; an unhandled exception occured.
	@Test(priority = 8, description = "C82977 - Chrome: Name the copied dashboard with special characters / numbers")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and add a special characters and numbers name to the copied dashbaord. Then It will be copied with It's displayed name normally")
	@Severity(SeverityLevel.NORMAL)
	public void Change_CopiedDashboardName_WithSpecialCharactersAndNumbers() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_CopyDashboard_SpecialCharactersDashboardName"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage
				.makeACopy_addDashboard_newName(testDataReader.getCellData("SpecialCharactersDashboardName"));

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage
				.assert_dashboardName_isCorrect(testDataReader.getCellData("SpecialCharactersDashboardName"));
	}

	@Test(priority = 9, description = "C82978 - Chrome: Name the copied dashboard with Arabic name")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and add a Arabic name to the copied dashbaord. Then It will be copied with It's displayed name normally")
	@Severity(SeverityLevel.NORMAL)
	public void Change_CopiedDashboardName_WithArabicDashboardName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_CopyDashboard_ArabicName"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.makeACopy_addDashboard_newName(testDataReader.getCellData("ArabicDashboardName"));

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(testDataReader.getCellData("ArabicDashboardName"));
	}

	@Test(priority = 10, description = "C82979 - Chrome: Name the copied dashboard with french name")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and add a french name to the copied dashbaord. Then It will be copied with It's displayed name normally")
	@Severity(SeverityLevel.NORMAL)
	public void Change_CopiedDashboardName_WithFrenchDashboardName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_CopyDashboard_FrenchName"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.makeACopy_addDashboard_newName(testDataReader.getCellData("FrenchDashboardName"));

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(testDataReader.getCellData("FrenchDashboardName"));
	}

	@Test(priority = 11, description = "C82980 - Chrome: Name the copied dashboard with Chinese name")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and add a Chinese name to the copied dashbaord. Then It will be copied with It's displayed name normally")
	@Severity(SeverityLevel.NORMAL)
	public void Change_CopiedDashboardName_WithChineseDashboardName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_CopyDashboard_ChineseName"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.makeACopy_addDashboard_newName(testDataReader.getCellData("ChineseDashboardName"));

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(testDataReader.getCellData("ChineseDashboardName"));
	}

	@Test(priority = 12, description = "C82981 - Chrome: Testing the folders displayed in the pop up")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy. Then 'created and shared' folders will be displayed in the popup")
	@Severity(SeverityLevel.NORMAL)
	public void Check_CreatedAndSharedFolders_DisplayedNormally() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage
				.assert_makeACopy_foldersExist(testDataReader.getCellData("Automation_Folder_SharedEdit_ToAnalyzer"));
		newUI_allContentPage
				.assert_makeACopy_foldersExist(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
	}

	@Test(priority = 13, description = "C82983 - Chrome: Copying a dashboard in a folder shared with user as 'View'")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and copy dashboard to folder shared as 'View'. Then dashboared will not be shared as button is dimmed. ")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_FolderSharedAsView_ButtonDimmed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_folderButtonDisabled(
				testDataReader.getCellData("Automation_Folder_SharedView_ToAnalyzer"));
	}

	@Test(priority = 14, description = "C82984 - Chrome: Copying a dashboard in a folder shared with user as 'Edit'")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and copy dashboard to folder shared as 'Edit'. Then dashboared will not be shared as button is dimmed. ")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_FolderSharedAsEdit_ButtonNotDimmed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_folderButtonEnabled(
				testDataReader.getCellData("Automation_Folder_SharedEdit_ToAnalyzer"));

		String New_DashboardName = newUI_allContentPage.makeACopy_getDashboard_newName();
		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(New_DashboardName);
	}

	@Test(priority = 15, description = "C82985 - Chrome: Copying a dashboard in a folder shared with user as 'Share'")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy and copy dashboard to folder shared as 'Share'. Then dashboared will not be shared as button is dimmed. ")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_FolderSharedAsShare_ButtonDimmed() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_folderButtonDisabled(
				testDataReader.getCellData("Automation_Folder_SharedShare_ToAnalyzer"));
	}

	@Test(priority = 16, description = "C82986 - Chrome: Testing that folders can expand and collapse in the pop up")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy. Then folders will not be shared as button is dimmed. ")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyScreen_FoldersCollapseAndExpand() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_MakeACopyOptionDisplayed"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.makeACopy_expandIconFolders(
				testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"), "plus");
		newUI_allContentPage.assert_makeACopy_foldersInsideFolderExist(
				testDataReader.getCellData("Automation_Folder_Inside_AutomationFolder"));

		newUI_allContentPage.makeACopy_expandIconFolders(
				testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"), "minus");
		
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.assert_makeACopy_foldersInsideFolderNotExist(
				testDataReader.getCellData("Automation_Folder_Inside_AutomationFolder"));
	}

	@Test(priority = 17, description = "C82987 - Chrome: Testing that user can search for folders in the pop up")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy And I search for specific folder and select it. Then folder is found and selected successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ContantPage_Search_WorkingNormally() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_CopyAndOpen"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

		newUI_allContentPage.assert_makeACopy_defaultSelectedFolder(
				testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

	}

	@Test(priority = 18, description = "C82991 - Chrome: Testing that user can search for copied dashboard from the content page search bar")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy And I copy a dashboard and I search for the copied dashboard in the content page. Then folder will be found normally.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ContantPage_SearchForCopiedFolder_WorkingNormally() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_CopiedAndFoundInSearch"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		String New_DashboardName = newUI_allContentPage.makeACopy_getDashboard_newName();

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage.makeACopy_clickCopyButton();

		newUI_allContentPage.makeACopy_searchContentPageAndAssertAndClick(New_DashboardName);

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(New_DashboardName);
	}

	@Test(priority = 19, description = "C82999 - Chrome: Naming a copied dashboard with an existing dashboard name in the same folder")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy And I copy a dashboard and I search for the copied dashboard in the content page. Then folder will be found normally.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyScreen_CannotCopyWithDuplicateDashboardName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_DuplicateError"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));
		newUI_allContentPage
				.makeACopy_addDashboard_newName(testDataReader.getCellData("Automation_Dashboard_DuplicateError Copy"));
		newUI_allContentPage.makeACopy_clickCopyButton();

		newUI_allContentPage.assert_makeACopy_errorIsDisplayed_DuplicateDashboardName();
	}

	@Test(priority = 20, description = "C83004 - Chrome: Naming a copied dashboard with an existing dashboard name in a different folder")
	@Description("When I navigate to the content screen, and I click on dashboard properties --> Make a Copy And I copy a dashboard 1 in folder 2 with the name of dashboard 2 wich already exist in folder 1. Then folder will be copied normally.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyScreen_CanCopyDashboardWithSameNameInDifferentFolders() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_Copy1"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.makeACopy_addDashboard_newName(testDataReader.getCellData("Automation_Dashboard_Copy2"));

		newUI_allContentPage.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_Copy2"));

		newUI_allContentPage.makeACopy_clickCopyButton();

		newUI_allContentPage.click_on_folder_dashboard(testDataReader.getCellData("Automation_Folder_Copy2"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Copy2"));
	}

	@Test(priority = 21, description = "C83000 - Chrome: Copying a dashboard that has bookmarks / filters")
	@Description("When I navigate to the dashboared properties and I copy it to folder. Then Dashboard will be copied with filters and prompts successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyScreen_CopyDashboardWithFiltersAndBookmarks() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_Copy_WithBookMarksAndFilters"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage
				.makeACopy_searchAndSelectFolder(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

		String New_DashboardName = newUI_allContentPage.makeACopy_getDashboard_newName();

		newUI_allContentPage.makeACopy_clickCopyAndOpenButton();

		newUI_dashboardPage = new NewUI_Content_Dashboard(driver);
		newUI_dashboardPage.assert_dashboardName_isCorrect(New_DashboardName);

		newUI_dashboardPage.click_bookmarkButton();
		newUI_dashboardPage.assert_bookmarksExist("Filter [Months]");

		newUI_dashboardPage.click_filterButton();
		newUI_dashboardPage.assert_filterApplied("Months");
	}

	@Test(priority = 22, description = "C83200 - Chrome: Verify that When Copying a dashboard , The default selection will be the folder they are currently in")
	@Description("When I navigate to the dashboared properties and I copy it to folder. Then Dashboard will be copied with filters and prompts successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyScreen_DefaultSelectionWillBeFolderTheyCurrentlyIn() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_CopyAndOpen"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_defaultSelectedFolder("Content");
	}

	@Test(priority = 23, description = "C83201 - Chrome: Verify that when copying a dashboard , The default name for the copy will be: 'Objectname Copy'")
	@Description("When I navigate to the dashboared properties and I copy it to folder. Then Dashboard will be copied with the name of 'DashboardName + Copy'")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MakeACopyScreen_DashboardCopiedWithSpecificName() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_Copy_NamingConvention"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

		newUI_allContentPage.assert_makeACopy_dashboardNewName_dashboardNamePlusCopyWord(
				testDataReader.getCellData("Automation_Dashboard_Copy_NamingConvention"));
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "makeACopy_newUI/TestData.xlsx");
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
