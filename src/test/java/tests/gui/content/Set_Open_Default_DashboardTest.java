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
import pageObjectModels.content.NewUI_Content_Dashboard_AnalyzeInsight;
import pageObjectModels.content.NewUI_Content;
import pageObjectModels.content.NewUI_Content_Dashboard;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.login.NewUI_SignOut;
import pageObjectModels.main.NewUI_Header;
import pageObjectModels.main.Skeleton;

@Epic("incorta -> Security -> Users")

public class Set_Open_Default_DashboardTest {

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
    NewUI_Content_Dashboard_AnalyzeInsight analyzeInsightPage;

    //// Test Cases
    @Test(priority = 1, description = "C82144 - Chrome: Set As Default Dashboard: Testing that the icon is displayed in the dashboard")
    @Description("Given I am logged in, When I navigate to the dashboard page, And I check the set defuault icon, icon is displayed.")
    @Severity(SeverityLevel.CRITICAL)
    public void setDefault_DashboardPin_Displayed() {

	allContentPage = new NewUI_Content(driver);
	allContentPage.navigate_toURL();
	allContentPage.click_on_folder_dashboard("1- Top and Bottom 10 Sales");

	dashboardPage = new NewUI_Content_Dashboard(driver);
	dashboardPage.assert_dashboardName_isCorrect("1- Top and Bottom 10 Sales");
	dashboardPage.assert_SetAsDefault_DashboardPin_Displayed();
    }

    @Test(priority = 2, description = "C82144 - Chrome: Set As Default Dashboard: Testing that the icon is displayed in the dashboard and work correctly")
    @Description("Given I am logged in, When I navigate to the dashboard page, And I check the set defuault icon, icon is displayed and work correctly.")
    @Severity(SeverityLevel.CRITICAL)
    public void setDefault_DashboardPin_Displayed_AndWork_Correctly() {

	allContentPage = new NewUI_Content(driver);
	allContentPage.navigate_toURL();
	allContentPage.click_on_folder_dashboard("1- Top and Bottom 10 Sales");

	dashboardPage = new NewUI_Content_Dashboard(driver);
	dashboardPage.assert_dashboardName_isCorrect("1- Top and Bottom 10 Sales");
	dashboardPage.assert_SetAsDefault_DashboardPin_Displayed();

	dashboardPage.click_SetAsDefaultPinIcon(true);

	newHeaderObject = new NewUI_Header(driver);
	newHeaderObject.expandUserMenu();
	newHeaderObject.signOut();

	// newSignOutPage = new NewUI_SignOut(driver);
	// newSignOutPage.assert_signOutMessageHeaderAndBodyAreCorrect();
	loginPage.navigate_toURL();
	loginPage.userLogin(testDataReader.getCellData("Tenant", "Data7"), testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));

	dashboardPage.assert_dashboardName_isCorrect("1- Top and Bottom 10 Sales");

    }

    //// TestNGAnnotations
    @BeforeMethod
    public void beforeMethod() {

    }

    @BeforeClass
    public void beforeClass() {
	System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "Content_NewUI_Set_Open_Default_Dashboard/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	driver = BrowserFactory.getBrowser(testDataReader);

	loginPage = new NewUI_Login(driver);
	loginPage.navigate_toURL();
	loginPage.userLogin(testDataReader.getCellData("Tenant", "Data7"), testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));
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
