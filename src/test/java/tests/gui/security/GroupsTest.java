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
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.security.Groups;


@Epic("Incorta -> Security -> Groups")
	public class GroupsTest 
	{
		// Declaring web-driver and excel reader instances
		WebDriver driver;
		ExcelFileManager testDataReader;
		
		// Declaring Page Objects that will be used in the tests
		Groups groupsPage;
		Login loginPage;
		Skeleton mainPage;
		
		
		//Declaring Variables that will be used in below tests
		String name;

		@Test(priority = 1, description = "TC_C474 - Create New Group.")
		@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and click on the "+" and add Group name and description, Click 'Add User' Button. Then, A new group will be added to group list.")
		@Severity(SeverityLevel.CRITICAL)
		public void createNewGroup() 
		{
			groupsPage = new Groups(driver);
			mainPage = new Skeleton(driver);
			groupsPage.Navigate_toURL();
			mainPage.Click_add();
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
