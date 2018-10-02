package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.NewUI_AllContent_Dashboard_AnalyzeInsight;
import pageObjectModels.content.NewUI_Content;
import pageObjectModels.content.NewUI_Content_Dashboard;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.login.NewUI_SignOut;
import pageObjectModels.main.NewUI_Header;
import pageObjectModels.main.Skeleton;

@Epic("incorta -> Content -> Set_As_Default_Dashboard")

public class NewUI_Set_As_Default_DashboardTest {

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] UserData;

	String FolderName;
	String DashboardName;
	String InsightName;

	//// Page Objects
	NewUI_Login loginPage;
	NewUI_SignOut newSignOutPage;
	NewUI_Header newHeaderObject;
	Skeleton mainPage;
	NewUI_Content allContentPage;
	NewUI_Content_Dashboard dashboardPage;
	NewUI_AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;

	//// Test Cases
	@Test(priority = 1, description = "C82144 - Chrome: Set As Default Dashboard: Testing that the icon is displayed in the dashboard")
	@Description("Given I am logged in, When I navigate to the dashboard page, And I check the set As defuault icon, icon is displayed.")
	@Severity(SeverityLevel.CRITICAL)
	public void setAsDefault_DashboardPin_Displayed() {
		// LogIn:
		// ----------
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));
		// --------------------Navigate to dashboard----------------------------
		allContentPage = new NewUI_Content(driver);
		allContentPage.navigate_toURL();
		allContentPage.click_on_folder_dashboard(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Displayed", "Data1"));
		// --------------------AssertSetAsDefault_DashboardPin_Displayed-------------
		dashboardPage = new NewUI_Content_Dashboard(driver);
		dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Displayed", "Data1"));
		dashboardPage.assert_SetAsDefault_DashboardPin_Displayed();
	}

	@Test(priority = 2, description = "C82145 - Chrome: Set As Default Dashboard: Testing that the icon is displayed in the dashboard and  dashboard \"set as default\" should be displayed after login")
	@Description("Given I am logged in, When I navigate to the dashboard page, And I click on  the set as default icon, icon is displayed and work correctly,dashoard set as a default dashboard.")
	@Severity(SeverityLevel.CRITICAL)
	public void setAsDefault_DashboardPin_Displayed_AndWork_Correctly() {

		// LogIn:
		// ----------
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		// --------------------Navigate to dashboard----------------------------

		allContentPage = new NewUI_Content(driver);
		allContentPage.navigate_toURL();
		allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Work", "Data1"));

		dashboardPage = new NewUI_Content_Dashboard(driver);
		dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Work", "Data1"));

		// ----- Click on set as default dashboard
		dashboardPage.clickOn_SetAsDefault_Dashboardpin();

		// LogOut and login again to check that it works as expected
		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.expandUserMenu();
		newHeaderObject.signOut();

		// check that you signout successfully
		newSignOutPage = new NewUI_SignOut(driver);
		newSignOutPage.assert_signOutMessageHeaderAndBodyAreCorrect();
		// ------------------------

		// Login to check the dashboard
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Work", "Data1"));

	}

	@Test(priority = 3, description = "C82179 - Chrome: Testing that when hovering over \"Set as default\" tooltip says: 'Set as Default.'")
	@Description("Given I am logged in, When I navigate to the dashboard page, And I hover on  the set as default icon, tooltip is displayed and and says [ set as default ]")
	@Severity(SeverityLevel.CRITICAL)
	public void setAsDefault_DashboardPin_Displayed_tooltip_check() {

		// LogIn:
		// ----------
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		// --------------------Navigate to dashboard----------------------------

		allContentPage = new NewUI_Content(driver);
		allContentPage.navigate_toURL();
		allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Set_As_Default_Dashboard_Pin_tooltip", "Data1"));

		dashboardPage = new NewUI_Content_Dashboard(driver);
		dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_tooltip", "Data1"));

		// ----- hover on set as default dashboard tooltip to check text
		dashboardPage.check_SetAsDefaultDashboardPin_tooltip();

	}

	@Test(priority = 4, description = "C82146 - Chrome:Set As Default Dashboard: Removing default dashboard")
	@Description("Given I am logged in , When I navigate to the dashboard page, And I click on  the set as default pin, then login again and navigate back and reclick on set as default dashboard, set as default dashboard removed and dashboard unsited as a default ]")
	@Severity(SeverityLevel.CRITICAL)
	public void remove_SetAsDefault_DashboardPin() {

		// LogIn:
		// ----------
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		// --------------------Navigate to dashboard----------------------------

		allContentPage = new NewUI_Content(driver);
		allContentPage.navigate_toURL();
		allContentPage
				.click_on_folder_dashboard(testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Work", "Data1"));

		dashboardPage = new NewUI_Content_Dashboard(driver);
		dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Work", "Data1"));

		// ----- Click on set as default dashboard
		dashboardPage.clickOn_SetAsDefault_Dashboardpin();

		// LogOut and login again to check that it works as expected
		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.expandUserMenu();
		newHeaderObject.signOut();

		// check that you signout successfully
		newSignOutPage = new NewUI_SignOut(driver);
		newSignOutPage.assert_signOutMessageHeaderAndBodyAreCorrect();
		// ------------------------

		// Login to check the dashboard
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		dashboardPage.assert_dashboardName_isCorrect(
				testDataReader.getCellData("Set_As_Default_Dashboard_Pin_Work", "Data1"));

		// click on set as default dashboard again to remove the default dashbaord
		dashboardPage.clickOn_SetAsDefault_Dashboardpin();

		// LogOut and login again to check that it works as expected
		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.expandUserMenu();
		newHeaderObject.signOut();

		// check that you signout successfully
		newSignOutPage = new NewUI_SignOut(driver);
		newSignOutPage.assert_signOutMessageHeaderAndBodyAreCorrect();
		// ------------------------

		// Login to check the dashboard
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));
		
		newHeaderObject.assert_sectionHeader_isSelected("Content");
		

	}

	//// TestNGAnnotations
	@BeforeMethod
	public void beforeMethod() {

	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "Set_As_Default_Dashboard_NewUI_Content/TestData.xlsx");
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
