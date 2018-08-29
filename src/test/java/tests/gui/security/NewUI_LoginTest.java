package tests.gui.security;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.main.NewUI_Header;
import pageObjectModels.main.Skeleton;
import pageObjectModels.security.Users;

@Epic("Incorta -> Login")
public class NewUI_LoginTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used in the tests
	NewUI_Login loginPage;
	NewUI_Header newHeaderObject;
	Skeleton subHeaderObject;
	AllContent allContentPage;
	Users usersPage;

	// Declaring public variables that will be shared between tests
	String[] newUserData;

	// Prerequisites, Manually created 'Analyzer User' [User name/Pass:
	// AbdelsalamAnalyzer/AbdelsalamAnalyzer1]
	@Test(priority = 1, description = "TC C60554_1 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, Then only scheduler and content tab will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithAnalyzer() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Content");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Scheduler");
	}

	// Prerequisites, Manually created 'Individual User' [User name/Pass:
	// AbdelsalamIndividual/AbdelsalamIndividual1]
	@Test(priority = 2, description = "TC C60554_2 - Users permissions - Individual User")
	@Description("When I log in with Individual User, Then only scheduler and content tab will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithIndividual() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data2"),
				testDataReader.getCellData("Username", "Data2"), testDataReader.getCellData("Password", "Data2"));

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Content");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Scheduler");
	}

	// Prerequisites, Manually created 'Normal User' [User name/Pass:
	// AbdelsalamUser/AbdelsalamUser1]
	@Test(priority = 3, description = "TC C60554_3 - Users permissions - Normal User")
	@Description("When I log in with a normal User, Then only scheduler and content tab will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithNormalUser() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data4"),
				testDataReader.getCellData("Username", "Data4"), testDataReader.getCellData("Password", "Data4"));

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Content");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Scheduler");
	}

	// Prerequisites, Manually created 'Schema Manager' [User name/Pass:
	// AbdelsalamSchemaManager/AbdelsalamSchemaManager1]
	@Test(priority = 4, description = "TC C60554_4 - Users permissions - Schema Manager User")
	@Description("When I log in with Schema Manager User. Then only scheduler, content, Data Sources, Business Schema and Schemas tabs will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithSchemaManager() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Content");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Scheduler");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Data");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Business Schema");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Schema");
	}

	// Prerequisites, Manually created 'User Manager' [User name/Pass:
	// AbdelsalamUserManager/AbdelsalamUserManager1]
	@Test(priority = 5, description = "TC C60554_5 - Users permissions - User Manager")
	@Description("When I log in with User Manager User. Then only scheduler, content and security tabs will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithUserManager() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data5"),
				testDataReader.getCellData("Username", "Data5"), testDataReader.getCellData("Password", "Data5"));

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Content");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Scheduler");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Security");
	}

	// Prerequisites, Manually created 'Super User' [User name/Pass:
	// AbdelsalamSuper/AbdelsalamSuper1]
	@Test(priority = 6, description = "TC C60554_6 - Users permissions - SUPER user")
	@Description("When I log in with Super User. Then all tabs will exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithSuperUser() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data6"),
				testDataReader.getCellData("Username", "Data6"), testDataReader.getCellData("Password", "Data6"));

		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Content");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Scheduler");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Security");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Data");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Business Schema");
		newHeaderObject.assert_oneSectionHeader_isDisplayed("Schema");
	}

	@Test(priority = 7, description = "C1275 - Successful Login")
	@Description("When I navigate to the login page, And I login using valid credentials Then login is done successfully and all content tab page is displayed")
	@Severity(SeverityLevel.CRITICAL)
	public void assertLoginWorksAsExpected() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data7"),
				testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));
		
		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.verify_allSectionHeaders_areDisplayed();
		newHeaderObject.assert_sectionHeader_isSelected("Content");
	}

	/*
	 * Login Using New Created User, Need to be logged in with Admin/User manager
	 * account, because user is created first then login with the new account
	 * created
	 */
	@Test(priority = 8, description = "C60950 - Login using new user created.")
	@Description("When I login with new created user, Then I will be redirected to change password page, And I will logged in successfully")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingNewlyCreatedUserAccount() {
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data7"),
				testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));

		// Create New User
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();

		subHeaderObject = new Skeleton(driver);
		subHeaderObject.Click_add();

		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);

		// Navigate to login page, and login using the new created user.
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant"), newUserData[0], newUserData[1]);

		// Actions for first time login
		loginPage.firstTimeLogin(newUserData[1], newUserData[0], newUserData[0]); // take user name as input for the new
																					// password
		newHeaderObject = new NewUI_Header(driver);
		newHeaderObject.assert_sectionHeader_isSelected("Content");
	}

	@BeforeMethod
	public void beforeMethod() {
		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "security_newUI/TestData.xlsx");
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
