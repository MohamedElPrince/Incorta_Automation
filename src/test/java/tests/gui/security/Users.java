package tests.gui.security;

import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.modules.login.Login_Login;
import pageObjectModels.modules.main.Main_Skeleton;
import pageObjectModels.modules.security.Security_Users;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

@Epic("incorta > Security > Users.")

public class Users {

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;
	String TempUser = "Test_User";
	String Picture = "ProfilePicture.jpg";

	//// Page Objects
	Login_Login loginPage;
	Security_Users usersPage;
	Main_Skeleton mainPage;
	
	//// Test Cases
	@Test(priority = 1, description = "C478 - Create User")
	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createNewUser() {
		usersPage = new Security_Users(driver);
		usersPage.Navigate_toURL();
		
		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();
		
		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);
	}
	
	@Test(priority = 3, description = "C16118 - User profile picture")
	@Description("Given I am logged in, When I navigate to the security.users page, And I change profile picture of existing user, And I save changes, Then the new profile picture sill be displayed")
	@Severity(SeverityLevel.CRITICAL)
	public void changeProfilePicture() {
		usersPage = new Security_Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Click_name(TempUser);
		usersPage.UploadProfilePicture(Picture);
	}

	
	//// Testng Annotations
	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
		loginPage = new Login_Login(driver);
		loginPage.Navigate_toURL();
		loginPage.Login(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
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
