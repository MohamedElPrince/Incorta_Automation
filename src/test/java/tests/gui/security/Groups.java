package tests.gui.security;

import org.apache.http.auth.UsernamePasswordCredentials;
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
import pageObjectModels.modules.security.Security_Users;


@Epic("Incorta -> Security -> Groups")
	public class Groups 
	{
		// Declaring web-driver and excel reader instances
		WebDriver driver;
		ExcelFileManager testDataReader;
		
		// Declaring Page Objects that will be used in the tests
		Security_Groups groupsPage;
		Security_Users usersPage;
		Login_Login loginPage;
		Main_Skeleton pageHeader;
		
		
		//Declaring Variables that will be used in below tests
		String name;
		String tenant;
		String username;
		String password;
		
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
			loginPage.Login(testDataReader.getCellData(tenant), testDataReader.getCellData(username), testDataReader.getCellData(password));
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
