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
import pageObjectModels.security.Groups;

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
			Groups groupsPage;		
		//Declaring Variables that will be used in below tests
		
		@Test(priority = 1, description = "TC C60554_1 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, only scheduler and content tab will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithAnalyzer() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
					testDataReader.getCellData("Password", "Data1"));
			
			mainPage = new Skeleton(driver);
			mainPage.AssertElementExist_Sidemenu("contentItem");
			mainPage.AssertElementExist_Sidemenu("schedulerItem");
		}
		
		@Test(priority = 2, description = "TC C60554_2 - Users permissions - Individual User")
		@Description("When I log in with Individual User, only scheduler and content tab will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithIndividual() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data2"), testDataReader.getCellData("Username", "Data2"), 
				testDataReader.getCellData("Password", "Data2"));
			
			mainPage = new Skeleton(driver);
			mainPage.AssertElementExist_Sidemenu("contentItem");
			mainPage.AssertElementExist_Sidemenu("schedulerItem");
		}

		@Test(priority = 3, description = "TC C60554_3 - Users permissions - Schema Manager User")
		@Description("When I log in with Schema Manager User, only scheduler, content, Data Sources, Business Schema and Schema Item tabs will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithSchemaManager() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
				testDataReader.getCellData("Password", "Data3"));
			
			mainPage = new Skeleton(driver);
			mainPage.AssertElementExist_Sidemenu("contentItem");
			mainPage.AssertElementExist_Sidemenu("schedulerItem");
			mainPage.AssertElementExist_Sidemenu("dataSourcesItem");
			mainPage.AssertElementExist_Sidemenu("businessSchemaItem");
			mainPage.AssertElementExist_Sidemenu("schemaItem");
		}

		@BeforeMethod
		public void beforeMethod()
		{
			//Need To add as a predefined --> Analyzer User to be defined before executing TC C60554_1 || User name/Pass: AbdelsalamAnalyzer/AbdelsalamAnalyzer1.
			//Need To add as a predefined --> Analyzer User to be defined before executing TC C60554_2 || User name/Pass: AbdelsalamIndividual/AbdelsalamIndividual1.
			//Need To add as a predefined --> Analyzer User to be defined before executing TC C60554_3 || User name/Pass: AbdelsalamSchemaManager/AbdelsalamSchemaManager1.
			loginPage = new Login(driver);
			loginPage.Navigate_toURL();
		}
		
		@BeforeClass
		public void beforeClass() {
			System.setProperty("testDataFilePath",System.getProperty("testDataFolderPath") + "security/TestData.xlsx");
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
