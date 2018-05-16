package tests.gui.security;

import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.security.Users;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

@Epic("incorta > Security > Users.")

public class UsersTest{

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;

	//// Page Objects
	Login loginPage;
	Users usersPage;
	Skeleton mainPage;
	
	//// Test Cases
	@Test(priority = 1, description = "C478 - Create User")
	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createNewUser() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
		
		mainPage = new Skeleton(driver);
		mainPage.Click_add();
		
		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);
	}

	
	//// Testng Annotations
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
