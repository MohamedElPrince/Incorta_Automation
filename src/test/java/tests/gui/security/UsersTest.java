package tests.gui.security;

import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.login.Login;
import pageObjectModels.login.Logout;
import pageObjectModels.main.Skeleton;
import pageObjectModels.security.Users;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

@Epic("incorta > Security > Users.")

public class UsersTest {

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;

	//// Page Objects
	Login loginPage;
	Logout logoutpage;
	Users usersPage;
	Skeleton mainPage;
	AllContent allContentPage;

	//// Test Cases
	@Test(priority = 1, description = "C478 - Create User")
	@Description("Given I am logged in, When I navigate to the security.users page,"
			+ " And I create a new user, And I navigate back to the security.users page,"
			+ " Then the new user will be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createNewUser() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();

		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);
	}


	// Login Using New Created Account @farid
	@Test(description = "C60950 - Login using new user created.", dependsOnMethods = { "createNewUser" })
	@Description("Given user is created,then I logged out after creating the new user,"
			+ "then Navigate to login page, then login with the new user created credintials, "
			+ "After enter the credintials Auto redirected to change password, edit the ditailes,"
			+ " then check that user after successful logged in redirected to content section ")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingNewlyCreatedUserAccount() {
		logout(); // Logout from current account "To make sure that the you are logout"
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), newUserData[0], newUserData[1]);
		// Actions for first time login
		String newPassword = "Automation";
		loginPage.FirstTimeLogin(newUserData[1], newPassword, newPassword);
		// Storing the newly created password
		newUserData[1] = newPassword;
		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
	}


	
	// logout Test Case from  @farid

	public void logout() {
		mainPage = new Skeleton(driver);
		mainPage.Select_fromUserMenu("Logout");

		logoutpage = new Logout(driver);
		logoutpage.Assert_logoutMessageHeaderAndBodyAreCorrect();
	}

	

	//// Testing Annotations
	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
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
