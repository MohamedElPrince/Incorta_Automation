package tests.gui.security;

import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class LoginTest {
  
		// Declaring web-driver and excel reader instances
			WebDriver driver;
			ExcelFileManager testDataReader;
			
		// Declaring Page Objects that will be used in the tests
			Login loginPage;
			Skeleton mainPage;
						
		//Declaring Variables that will be used in below tests
	
		@Test(priority = 1, description = "TC_C60554 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, only scheduler and content tab will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithAnalyzer() 
		{

		}

		@BeforeClass
		public void beforeClass() {
			System.setProperty("testDataFilePath",
					System.getProperty("testDataFolderPath") + "Login/TestData.xlsx");
					testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
					driver = BrowserFactory.getBrowser(testDataReader);
					loginPage = new Login(driver);
					loginPage.Navigate_toURL();
					loginPage.UserLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"), testDataReader.getCellData("Password"));
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
