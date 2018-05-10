package tests.gui.security;

import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import pageObjectModels.modules.login.Login_Login;
import pageObjectModels.modules.main.Main_Skeleton;
import pageObjectModels.modules.security.Security_Users;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class Users {

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;

	//// Page Objects
	Login_Login loginPage;
	Security_Users usersPage;
	Main_Skeleton mainPage;
	
	//// Test Cases
	@Test
	public void createNewUser() {
		mainPage = new Main_Skeleton(driver);
		mainPage.Click_securityTab();
		mainPage.Click_add();
		
		usersPage = new Security_Users(driver);
	}

	
	///// Testng Annotations
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
