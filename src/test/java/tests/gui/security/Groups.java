package tests.gui.security;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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
import pageObjectModels.modules.security.Security_Groups;


@Epic("incorta Certification Path.")
	public class Groups 
	{
		// Declaring web-driver and excel reader instances
		WebDriver driver;
		ExcelFileManager testDataReader;
		
		// Page Objects
		Security_Groups groupsPage;
		Login_Login loginPage;
		Main_Skeleton pageHeader;
		
		//Variables
		String name;
		String description = "New group created by automation script";
		String tenant = "demo";
		String username = "admin";
		String password = "admin";
		
		@Test(priority = 2, description = "TC_C474 - Create New Group.")
		@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and click on the "+" and add Group name and description, Click 'Add User' Button. Then, A new group will be added to group list.")
		@Severity(SeverityLevel.CRITICAL)
		public void createNewGroup() 
		{
			groupsPage = new Security_Groups(driver);
			pageHeader = new Main_Skeleton(driver);
			groupsPage.Navigate_toURL();
			pageHeader.Click_add();
			//groupsPage.AddNewGroup(name, description);
			name = groupsPage.AddNewGroup();
			groupsPage.Navigate_toURL();
			groupsPage.Assert_groupIsDisplayed(name);
		}


		@BeforeClass
		public void beforeClass() {
			System.setProperty("testDataFilePath",
			System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
			testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
			driver = BrowserFactory.getBrowser(testDataReader);
			loginPage = new Login_Login(driver);
			loginPage.Navigate_toURL();
			loginPage.Login(tenant, username, password);
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
