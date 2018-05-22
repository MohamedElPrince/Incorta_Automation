package tests.gui.security;

import org.testng.annotations.Test;
import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.data.DataSources;
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
			DataSources dataSourcesPage;
	
		//Below Test cases is for Users login			
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
		
		@Test(priority = 3, description = "TC C60554_3 - Users permissions - Normal User")
		@Description("When I log in with a normal User, only scheduler and content tab will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithUser() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data4"), testDataReader.getCellData("Username", "Data4"), 
				testDataReader.getCellData("Password", "Data4"));
			
			mainPage = new Skeleton(driver);
			mainPage.AssertElementExist_Sidemenu("contentItem");
			mainPage.AssertElementExist_Sidemenu("schedulerItem");
		}
		
		@Test(priority = 4, description = "TC C60554_4 - Users permissions - Schema Manager User")
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
		
		@Test(priority = 5, description = "TC C60554_5 - Users permissions - User Manager User")
		@Description("When I log in with user Manager User, only scheduler, content and security tabs will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithUserManager() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data5"), testDataReader.getCellData("Username", "Data5"), 
				testDataReader.getCellData("Password", "Data5"));
			
			mainPage = new Skeleton(driver);
			mainPage.AssertElementExist_Sidemenu("contentItem");
			mainPage.AssertElementExist_Sidemenu("schedulerItem");
			mainPage.AssertElementExist_Sidemenu("securityItem");
		}
		
		@Test(priority = 6, description = "TC C60554_6 - Users permissions - SUPER user")
		@Description("When I log in with Super User, all tabs will exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void LoginWithSuperUser() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data6"), testDataReader.getCellData("Username", "Data6"), 
				testDataReader.getCellData("Password", "Data6"));
			
			mainPage = new Skeleton(driver);
			mainPage.AssertElementExist_Sidemenu("contentItem");
			mainPage.AssertElementExist_Sidemenu("schedulerItem");
			mainPage.AssertElementExist_Sidemenu("securityItem");
			mainPage.AssertElementExist_Sidemenu("dataSourcesItem");
			mainPage.AssertElementExist_Sidemenu("schemaItem");
			mainPage.AssertElementExist_Sidemenu("businessSchemaItem");
		}
		
		//*******************************************Under Construction********************************************
		//Below Test cases is for Users permissions.
		@Test(priority = 7, description = "TC C60535_6 - Schema Manager Permissions ")
		@Description("When I log in with Schema manager user, then i'll be able to create a new data source.")
		@Severity(SeverityLevel.CRITICAL)
		public void SchemaManager_Permissions_NewDataSource()
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
				testDataReader.getCellData("Password", "Data3"));
			
			mainPage = new Skeleton(driver);
			mainPage.Click_Element_Sidemenu("dataSourcesItem");
			
			dataSourcesPage= new DataSources(driver);
			dataSourcesPage.Assert_dataSourcesTabIsSelected();
			mainPage.Click_add();
			//dataSourcesPage.AddDataSource("MySQL");
			//dataSourcesPage.Assert_dataSourceCreationWasSuccessful("MySQL");
		}
		
		@BeforeMethod
		public void beforeMethod()
		{
			/*Need To add below as a predefined:
			Analyzer User to be defined before executing TC C60554_1 || User name/Pass: AbdelsalamAnalyzer/AbdelsalamAnalyzer1.
			Analyzer User to be defined before executing TC C60554_2 || User name/Pass: AbdelsalamIndividual/AbdelsalamIndividual1.
			Analyzer User to be defined before executing TC C60554_4 || User name/Pass: AbdelsalamSchemaManager/AbdelsalamSchemaManager1.
			Analyzer User to be defined before executing TC C60554_3 || User name/Pass: AbdelsalamUser/AbdelsalamUser1.
			Analyzer User to be defined before executing TC C60554_5 || User name/Pass: AbdelsalamUserManager/AbdelsalamUserManager1.
			Analyzer User to be defined before executing TC C60554_6 || User name/Pass: AbdelsalamSuper/AbdelsalamSuper1.
			*/
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
