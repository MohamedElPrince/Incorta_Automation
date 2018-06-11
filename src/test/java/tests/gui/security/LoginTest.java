package tests.gui.security;

import org.testng.annotations.Test;
import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.content.AllContent_Dashboard_AnalyzeInsight;
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;
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
			SchemaList schemasPage;
			SchemaList_SchemaView schemasViewPage;
			AllContent allContentPage;
			AllContent_Dashboard dashboardPage;
			AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;

		// Declaring public variables that will be shared between tests
			String NewSchemaName = "Abdelsalam_Automation_Schema";
			String NewSchemaDescription = "Created by a script in class RolesTest.java";
			String ExistingSchemaNAME = "Abdelsalan_Automation_Schema"; //Existing Schema to be used as predefined
			String NewDataSourceTableName; //Table name to be used in assertion
			String LoadDataSchema = "LoadDataSchema1";//Predefined Schema with data source to load data into it.
			String ShareSchema = "ShareSchema";
			String UserToShareWith = "automation_user_1526821504534";
			String NewFolderName;
			String FolderNameToDelete = "ahmed";
			String FolderNameToShare = "Folder";
			String UserToShareWithFolder = "Abdelsalam_User";
			String FolderToBeRenamed = "New Folder";
			String RenameFolderName = "NewFolderRenamed";
			String NewDashboardName = "New Dashboard";
			String SchemaNameForInsight = "Automation_Schema_1525933948339";
			String FolderNameToBeMoved = "MoveFolder";
			String FolderNameToMoveTo = "Mohamed";
			String DashboardNameToBeDeleted = "H_A_L";
			String DashboardToBeShared = "SendDashboard";
					
		
		//Prerequisites, Analyzer user
		@Test(priority = 9, description = "TC C60531_1 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on create new folder. Then new folder will be created successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_CreateFolder() 
		{
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
					testDataReader.getCellData("Password", "Data1"));
			
			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();
			
			mainPage = new Skeleton(driver);
			mainPage.Click_add();
			mainPage.Select_fromDropdownMenu("Create Folder");
			NewFolderName = allContentPage.SetNewFolderName();
			allContentPage.Assert_folder_Dashboard_IsDisplayed(NewFolderName);
		}
		
		//Prerequisites, Analyzer user + Folder to be deleted
		@Test(priority = 10, description = "TC C60531_2 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click delete. Then folder will be deleted successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_DeleteFolder() 
		{
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
					testDataReader.getCellData("Password", "Data1"));
					
			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();
								
			allContentPage.Click_Folder_Dashboard_Properties(FolderNameToDelete);
			allContentPage.Click_FolderProperties_ManageFolderButtons("deleteFolder");
			allContentPage.Click_Folder_Dashboard_Properties_ManageFolderButtons_ConfirmationButtonsForDelete("Delete");
			allContentPage.Assert_folder_Dashboard_IsNotDisplayed(FolderNameToDelete);
		}
		
		//Prerequisites, Analyzer user + Folder to be shared + User to share with
		@Test(priority = 11, description = "TC C60531_3 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click share and select any person to share with. Then folder will be shared successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_ShareFolder() 
		{
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
					testDataReader.getCellData("Password", "Data1"));
					
			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();
			
			allContentPage.Click_Folder_Dashboard_Properties(FolderNameToShare);
			allContentPage.Click_FolderProperties_ManageFolderButtons("shareFolder");
			
			allContentPage.Folder_Sharing_SearchAndSelectUsers(UserToShareWithFolder);
			
			schemasViewPage = new SchemaList_SchemaView(driver);
			schemasViewPage.Schema_Sharing_ClickOnUserPermission("Can Edit");
			schemasViewPage.Click_Save_Button();
			schemasViewPage.Assertion_UserCanEdit(UserToShareWithFolder, "Can Edit");
		}
		
		@BeforeMethod
		public void beforeMethod()
		{
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
