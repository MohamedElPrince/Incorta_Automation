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
import pageObjectModels.content.NewUI_Content_Dashboard_AnalyzeInsight;
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

public class NewUI_DeleteDashboard {

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
	NewUI_Content newContentPage;
	NewUI_Groups_Group groups_groupPage;

	// Declaring public variables that will be shared between tests

	@Test(priority = 1, description = "C84203 - Testing that 'Delete Dashboard' Option is displayed in Grid View.")
	@Description("When I navigate to the content screen, and I click on dashboard properties from Grid View. Then Delete button will be displayed normally.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_DeleteButton_ExistInGridView() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_ForVerification"));
		newContentPage.assert_dashboardProperties_manageDashboardButtons_displayed("Delete");
	}

	@Test(priority = 2, description = "C84204 - Testing that 'Delete Dashboard' Option is displayed in List View.")
	@Description("When I navigate to the content screen, and I click on dashboard properties from List View. Then Delete button will be displayed normally.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_DeleteButton_ExistInTableView() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.changeCatalogView("list");
		newContentPage.click_dashboardFolder_properties_fromListView(
				testDataReader.getCellData("Automation_Dashboard_Delete_ForVerification"));
		newContentPage.assert_dashboardProperties_manageDashboardButtons_displayed("Delete");
	}

	@Test(priority = 3, description = "C84206 - Testing that When you click on 'Delete' option Delete Modal will be displayed.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete. Then Delete screen will be displayed normally.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_DeleteScreen_OpenedNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_ForVerification"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_screenContentDisplayed(
				testDataReader.getCellData("Automation_Dashboard_Delete_ForVerification"));
	}

	@Test(priority = 4, description = "C84207 - Testing that 'Delete' action is working.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_DeleteButton_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newContentPage.assert_dashboard_folder_notExist(testDataReader.getCellData("Automation_Dashboard_Delete"));
	}

	@Test(priority = 5, description = "C84208 - Testing that Never Mind action is working.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_NeverMindButton_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_NeverMind"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.deleteDashboard_popup_click_confirmationButton_neverMind();

		newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_NeverMind"));
	}

	@Test(priority = 6, description = "C84209 - Testing that when deleting a dashboard a notification msg will be displayed.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then notificatiob message will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_ConfirmationMessageDisplayed() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_ConfirmationMessage"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
	}

	@Test(priority = 7, description = "C84212 - Testing that User can delete a dashboard with Arabic Name.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [Arabic] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_Arabic_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_Arabic"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_dashboardNameIsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_Arabic"));
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newContentPage
				.assert_dashboard_folder_notExist(testDataReader.getCellData("Automation_Dashboard_Delete_Arabic"));
	}

	@Test(priority = 8, description = "C84213 - Testing that use can delete a dashboard with Special Characters.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [Special Characters] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_SpecialCharacters_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_SpecialCharecters"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_SpecialCharecters"));
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newContentPage.assert_dashboard_folder_notExist(
				testDataReader.getCellData("Automation_Dashboard_Delete_SpecialCharecters"));
	}

	@Test(priority = 9, description = "C84215 - Testing that user can delete a dashboard were the data is not loaded.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [with no insights] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_NoInsights_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_NoInsights"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_NoInsights"));
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newContentPage
				.assert_dashboard_folder_notExist(testDataReader.getCellData("Automation_Dashboard_Delete_NoInsights"));
	}

	@Test(priority = 10, description = "C84215 - Testing that user can delete a dashboard were the data is not loaded.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [with schema not loaded] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_SchemaNotLoaded_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_SchemaNotLoaded"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_SchemaNotLoaded"));
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newContentPage.assert_dashboard_folder_notExist(
				testDataReader.getCellData("Automation_Dashboard_Delete_SchemaNotLoaded"));
	}

	@Test(priority = 11, description = "C84216 - Testing that user can delete a dashboard with Share with Edit Access.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [Shared Edit] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_SharedEdit_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_SharedEdit"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_SharedEdit"));
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newContentPage
				.assert_dashboard_folder_notExist(testDataReader.getCellData("Automation_Dashboard_Delete_SharedEdit"));
	}

	@Test(priority = 12, description = "C84217 - Testing that user cannot delete a dashboard shared with Share Access.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [Shared Share] will not be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_SharedShare_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_SharedShare"));
		newContentPage.assert_dashboardProperties_manageDashboardButtons_notExist("Delete");
	}

	@Test(priority = 13, description = "C84218 - Testing that user cannot delete a dashboard share with View Access.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [Shared View] will not be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_SharedView_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_SharedView"));
		newContentPage.assert_dashboardProperties_manageDashboardButtons_notExist("Delete");
	}

	@Test(priority = 14, description = "C84219 - Testing that user can delete a dashboard created under a folder.")
	@Description("When I navigate to the content screen, and I click on folder -> dashboard properties -> Delete and I click on delete button. Then dashboard [under folder] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_CreatedUnderFolder_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_on_folder_dashboard(testDataReader.getCellData("Automation_Folder_Delete"));
		newUI_folderPage = new NewUI_Content_Folder(driver);

		newUI_folderPage.click_dashboardFolder_properties(
				testDataReader.getCellData("Automation_Dashboard_Delete_UnderFolder"));
		newUI_folderPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newUI_folderPage.assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_UnderFolder"));
		newUI_folderPage.deleteDashboard_popup_click_confirmationButton_delete();

		newUI_folderPage.assert_deleteDashboard_popup_confirmationMessageDisplayed();
		newUI_folderPage.assert_dashboard_folder_notExist(
				testDataReader.getCellData("Automation_Dashboard_Delete_UnderFolder"));
	}

	@Test(priority = 15, description = "C84221 - Testing that Delete Modal can handle a dashboard with Long name.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Delete and I click on delete button. Then dashboard [with long name] will be deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteDashboard_LongName_WorkingNormally() {
		newContentPage = new NewUI_Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.click_dashboardFolder_properties_fromCardView(
				testDataReader.getCellData("Automation_Dashboard_Delete_LongName"));
		newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
		newContentPage.assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(
				testDataReader.getCellData("Automation_Dashboard_Delete_LongName"));
		newContentPage.deleteDashboard_popup_click_confirmationButton_delete();

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Deleted");
		newHeaderObject
				.assert_splashNotificationDescription_equalsExpected("You've successfully deleted one dashboard.");
		newContentPage
				.assert_dashboard_folder_notExist(testDataReader.getCellData("Automation_Dashboard_Delete_LongName"));
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "deleteDashboard_newUI/TestData.xlsx");
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
