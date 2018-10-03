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

public class NewUI_RenameDashboard {

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

	@Test(priority = 1, description = "C83005 - Chrome: Dashboard : Rename a dashboard is working.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name and I click on Rename. Then Dashboard will be renamed.")
	@Severity(SeverityLevel.NORMAL)
	public void RenameDashboard_Working() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage
				.Click_Folder_Dashboard_Properties(testDataReader.getCellData("Automation_Dashboard_Rename"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

		newUI_allContentPage.renameDashboard_typeNewDashboardName(
				testDataReader.getCellData("Automation_Dashboard_Rename_Renamed"));
		newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

		newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
		newUI_allContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_Renamed"));
	}

	// Automation_Dashboard_Rename_SpecialCharacters
	@Test(priority = 2, description = "C83007 - Chrome: Dashboard : Rename with special characters / numbers.")
	@Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [Special Characters] and I click on Rename. Then Dashboard will be renamed.")
	@Severity(SeverityLevel.NORMAL)
	public void RenameDashboard_WithSpecialCharacters_Working() {
		newUI_allContentPage = new NewUI_Content(driver);
		newUI_allContentPage.navigate_toURL();

		newUI_allContentPage.Click_Folder_Dashboard_Properties(
				testDataReader.getCellData("Automation_Dashboard_Rename_SpecialCharacters"));
		newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

		newUI_allContentPage.renameDashboard_typeNewDashboardName(
				testDataReader.getCellData("Automation_Dashboard_Rename_SpecialCharacters_Renamed"));
		newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

		newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
		newUI_allContentPage.Assert_DashboardExist(
				testDataReader.getCellData("Automation_Dashboard_Rename_SpecialCharacters_Renamed"));
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "renameDashboard_newUI/TestData.xlsx");
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
