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
	
	//// Prerequisites
	/**
	 * Manually Add user with name "Test_User" till automating test data 
	 */

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;
	String TempUser = "Test_User";

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
	
	@Test(priority = 2, description = "C471 - Delete Users")
	@Description("Given I am logged in, When I navigate to the security.users page, And I select existing user, And I delete this selected user, Then user will not be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUser() {
		usersPage = new Security_Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Select_nameCheckbox(TempUser); // manually created user till be automated as prerequisites 
		mainPage = new Main_Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");
		usersPage.ConfirmUserDeletion();
		usersPage.Assert_nameIsNotDisplayed(TempUser);
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
