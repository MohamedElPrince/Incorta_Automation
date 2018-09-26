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

public class NewUI_MakeACopy {

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
		newUI_allContentPage.assert_makeACopy_popup_notDisplayed();
	
		newUI_allContentPage.navigate_toURL();
		newUI_allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Automation_Folder_ToCopyDashboardToIt"));

		newUI_folderPage = new NewUI_Content_Folder(driver);
		newUI_folderPage.Assert_DashboardNotExist(New_DashboardName);
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
