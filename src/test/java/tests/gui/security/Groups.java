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


@Epic("Incorta -> Security -> Groups")
	public class Groups 
	{
		// Declaring web-driver and excel reader instances
		WebDriver driver;
		ExcelFileManager testDataReader;
		
		// Declaring Page Objects that will be used in the tests
		Security_Groups groupsPage;
		Login_Login loginPage;
		Main_Skeleton mainPage;
		
		
		//Declaring Variables that will be used in below tests
		String newGroupName;
		String groupNameToBeSelected = "Abdelsalam_groupCheckBox";//When creating group --> It's name will be "Abdelsalam_group" without "CheckBox".

		@Test(priority = 1, description = "TC_C474 - Create New Group.")
		@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and click on the "+" and add Group name and description, Click 'Add User' Button. Then, A new group will be added to group list.")
		@Severity(SeverityLevel.CRITICAL)
		public void createNewGroup() 
		{
			groupsPage = new Security_Groups(driver);
			mainPage = new Main_Skeleton(driver);
			groupsPage.Navigate_toURL();
			mainPage.Click_add();
			newGroupName = groupsPage.AddNewGroup();
			groupsPage.Navigate_toURL();
			groupsPage.Assert_groupIsDisplayed(newGroupName);
		}
		
		//Prerequisite --> Login using Admin || A predefined group [groupNameToBeSelected] is needed to be created to be used when deleting group.
		@Test(priority = 3, description = "TC_C467 - Delete Group.")
		@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and select any groups, Click on delete. Then groups is deleted.")
		@Severity(SeverityLevel.CRITICAL)
		public void DeleteGroup() 
		{
			groupsPage = new Security_Groups(driver);
			groupsPage.Navigate_toURL();
			groupsPage.ClickOnGroupCheckBox(groupNameToBeSelected);
			mainPage = new Main_Skeleton(driver);
			mainPage.Click_actions();
			mainPage.Select_fromDropdownMenu("Delete selection");
			groupsPage.ClickOnDeleteButton();
			groupsPage.Assert_groupIsNotDisplayed(groupNameToBeSelected);
		}
		
		@BeforeClass
		public void beforeClass() {
			System.setProperty("testDataFilePath",
			System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
			testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
			driver = BrowserFactory.getBrowser(testDataReader);
			loginPage = new Login_Login(driver);
			loginPage.Navigate_toURL();
			loginPage.Login(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"), testDataReader.getCellData("Password"));
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
