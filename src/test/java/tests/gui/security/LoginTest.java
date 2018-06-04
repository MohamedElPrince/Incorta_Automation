package tests.gui.security;

import org.testng.annotations.Test;
import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
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
 
		// Declaring public variables that will be shared between tests
			String NewSchemaName = "Abdelsalam_Automation_Schema";
			String NewSchemaDescription = "Created by a script in class LoginTest.java";
			String ExistingSchemaNAME = "Abdelsalan_Automation_Schema"; //Existing Schema to be used as predefined
			String NewDataSourceTableName; //Table name to be used in assertion
			String LoadDataSchema = "LoadDataSchema1";//Predefined Schema with data source to load data into it.
			String ShareSchema = "ShareSchema";
			String UserToShareWith = "automation_user_1526821504534";
			String NewFolderName = "AutomationFolder";
			String FolderNameToDelete = "ahmed";
			String FolderNameToShare = "Folder";
					
//		//Prerequisites, Analyzer User [User name/Pass: AbdelsalamAnalyzer/AbdelsalamAnalyzer1]
//		@Test(priority = 1, description = "TC C60554_1 - Users permissions - Analyzer User")
//		@Description("When I log in with Analyzer User, Then only scheduler and content tab will be exist.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void LoginWithAnalyzer() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
//					testDataReader.getCellData("Password", "Data1"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.AssertElementExist_Sidemenu("contentItem");
//			mainPage.AssertElementExist_Sidemenu("schedulerItem");
//		}
//		//Prerequisites, Individual User [User name/Pass: AbdelsalamIndividual/AbdelsalamIndividual1]
//		@Test(priority = 2, description = "TC C60554_2 - Users permissions - Individual User")
//		@Description("When I log in with Individual User, Then only scheduler and content tab will be exist.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void LoginWithIndividual() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data2"), testDataReader.getCellData("Username", "Data2"), 
//				testDataReader.getCellData("Password", "Data2"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.AssertElementExist_Sidemenu("contentItem");
//			mainPage.AssertElementExist_Sidemenu("schedulerItem");
//		}
//		//Prerequisites, Normal User [User name/Pass: AbdelsalamSchemaManager/AbdelsalamSchemaManager1]
//		@Test(priority = 3, description = "TC C60554_3 - Users permissions - Normal User")
//		@Description("When I log in with a normal User, Then only scheduler and content tab will be exist.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void LoginWithNormalUser() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data4"), testDataReader.getCellData("Username", "Data4"), 
//				testDataReader.getCellData("Password", "Data4"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.AssertElementExist_Sidemenu("contentItem");
//			mainPage.AssertElementExist_Sidemenu("schedulerItem");
//		}
//		//Prerequisites, Schema Manager User to be defined + User name/Pass: AbdelsalamUser/AbdelsalamUser1.
//		@Test(priority = 4, description = "TC C60554_4 - Users permissions - Schema Manager User")
//		@Description("When I log in with Schema Manager User. Then only scheduler, content, Data Sources, Business Schema and Schema Item tabs will be exist.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void LoginWithSchemaManager()
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
//				testDataReader.getCellData("Password", "Data3"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.AssertElementExist_Sidemenu("contentItem");
//			mainPage.AssertElementExist_Sidemenu("schedulerItem");
//			mainPage.AssertElementExist_Sidemenu("dataSourcesItem");
//			mainPage.AssertElementExist_Sidemenu("businessSchemaItem");
//			mainPage.AssertElementExist_Sidemenu("schemaItem");
//		}
//		//Prerequisites, User Manager User [User name/Pass: AbdelsalamUserManager/AbdelsalamUserManager1]
//		@Test(priority = 5, description = "TC C60554_5 - Users permissions - User Manager User")
//		@Description("When I log in with User Manager User. Then only scheduler, content and security tabs will be exist.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void LoginWithUserManager() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data5"), testDataReader.getCellData("Username", "Data5"), 
//				testDataReader.getCellData("Password", "Data5"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.AssertElementExist_Sidemenu("contentItem");
//			mainPage.AssertElementExist_Sidemenu("schedulerItem");
//			mainPage.AssertElementExist_Sidemenu("securityItem");
//		}
//		//Prerequisites, Super User [User name/Pass: AbdelsalamSuper/AbdelsalamSuper1]
//		@Test(priority = 6, description = "TC C60554_6 - Users permissions - SUPER user")
//		@Description("When I log in with Super User. Then all tabs will exist.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void LoginWithSuperUser() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data6"), testDataReader.getCellData("Username", "Data6"), 
//				testDataReader.getCellData("Password", "Data6"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.AssertElementExist_Sidemenu("contentItem");
//			mainPage.AssertElementExist_Sidemenu("schedulerItem");
//			mainPage.AssertElementExist_Sidemenu("securityItem");
//			mainPage.AssertElementExist_Sidemenu("dataSourcesItem");
//			mainPage.AssertElementExist_Sidemenu("schemaItem");
//			mainPage.AssertElementExist_Sidemenu("businessSchemaItem");
//		}
//		
//		//Prerequisites, Schema Manager user + Connection credentials to data source
//		@Test(priority = 7, description = "TC C60535_1 - Schema Manager Permissions ")
//		@Description("When I log in with Schema manager user and click add data source, then a new data source will be created.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void SchemaManager_Permissions_NewDataSource()
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
//				testDataReader.getCellData("Password", "Data3"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.Click_Element_Sidemenu("dataSourcesItem");
//			
//			dataSourcesPage= new DataSources(driver);
//			dataSourcesPage.Assert_dataSourcesTabIsSelected();
//			
//			mainPage.Click_add();
//			
//			String MyDataSourceName = dataSourcesPage.AddDataSource("MySQL");
//			dataSourcesPage.Assert_dataSourceCreationWasSuccessful(MyDataSourceName);
//			dataSourcesPage.Assert_nameIsDisplayed(MyDataSourceName);
//		}	
//		//Prerequisites, Schema Manager user + Test data defined for creating new schema 
//		@Test(priority = 8, description = "TC C60535_2 - Schema Manager Permissions ")
//		@Description("When I log in with Schema manager user and click add to create a new schema, then a new schema will be created")
//		@Severity(SeverityLevel.CRITICAL)
//		public void SchemaManager_Permissions_CreateSchema()
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
//				testDataReader.getCellData("Password", "Data3"));
//			
//			mainPage = new Skeleton(driver);
//			mainPage.Click_Element_Sidemenu("schemaItem");
//			mainPage.Click_add();
//			mainPage.Select_fromDropdownMenu("Create Schema");
//			
//			schemasPage = new SchemaList(driver);
//			schemasPage.createNewSchema(NewSchemaName, NewSchemaDescription);
//			
//			mainPage.Click_Element_Sidemenu("schemaItem");
//			schemasPage.Assert_schemaNameIsDisplayed(NewSchemaName);
//		}
//		//Prerequisites, Schema Manager User + Data Source available + New schema available [ExistingSchema] 
//		@Test(priority = 9, description = "TC C60535_3 - Schema Manager Permissions")
//		@Description("Given I've a data source and schema, When I log in with Schema manager user, and go to schema wizard, Then data source will be added to the schema.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void SchemaManager_Permissions_AddDataSourceToSchema()
//		{	
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
//				testDataReader.getCellData("Password", "Data3"));
//			
//			//Create new data source as a prerequisite.
//			mainPage = new Skeleton(driver);
//			mainPage.Click_Element_Sidemenu("dataSourcesItem");			
//			
//			dataSourcesPage= new DataSources(driver);
//			dataSourcesPage.Assert_dataSourcesTabIsSelected();
//			
//			mainPage.Click_add();
//			
//			String ExistingDataSourceName = dataSourcesPage.AddDataSource("MySQL");
//			dataSourcesPage.Assert_dataSourceCreationWasSuccessful(ExistingDataSourceName);
//			dataSourcesPage.Assert_nameIsDisplayed(ExistingDataSourceName);
//			
//			//Add Data Source to schema
//			mainPage.Click_Element_Sidemenu("schemaItem");
//			
//			schemasPage = new SchemaList(driver);
//			schemasPage.Click_schemaName(ExistingSchemaNAME);
//			
//			mainPage.Click_add();
//			mainPage.Select_fromDropdownMenu("Schema Wizard");
//
//			schemasViewPage = new SchemaList_SchemaView(driver);
//			schemasViewPage.Wizard_AddDataSourceTable(ExistingDataSourceName, true, "MySQL",testDataReader.getCellData("DatabaseTableName"));
//			
//			NewDataSourceTableName = schemasViewPage.GetNewestTableName();
//			schemasViewPage.Assert_tableNameIsDisplayed(NewDataSourceTableName);
//		}
//		//Prerequisites, Schema Manager User + Data Source available + Schema available + Data source is added to the schema
//		@Test(priority = 10, description = "TC C60535_4 - Schema Manager Permissions")
//		@Description("Given I logged in with Schema manager user, and I've added a data source with schema, When I click load data from the schema. Then Data is loaded successfully.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void SchemaManager_Permissions_LoadData()
//		{	
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
//				testDataReader.getCellData("Password", "Data3"));
//
//			mainPage = new Skeleton(driver);
//			mainPage.Click_Element_Sidemenu("schemaItem");	
//			
//			schemasPage = new SchemaList(driver);
//			schemasPage.Click_schemaName(LoadDataSchema);
//			
//			schemasViewPage = new SchemaList_SchemaView(driver);
//			String initialLoadStatus = schemasViewPage.GetLastLoadStatus();
//			
//			mainPage.Click_load();
//			mainPage.Hover_overDropdownMenu("Load now");
//			mainPage.Select_fromDropdownMenu("Full");
//			
//			schemasViewPage.confirmLoadingData();
//			schemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
//			schemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);
//		}
//		//Prerequisites, Schema Manager User + Data Source Available + Schema Available
//		@Test(priority = 10, description = "TC C60535_5 - Schema Manager Permissions")
//		@Description("Given I log in with Schema manager user, and I've added a data source with schema, and click load data from the schema. Then Data is loaded normally.")
//		@Severity(SeverityLevel.CRITICAL)
//		public void SchemaManager_Permissions_ShareSchemaToEdit()
//		{	
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
//				testDataReader.getCellData("Password", "Data3"));
//
//			mainPage = new Skeleton(driver);
//			mainPage.Click_Element_Sidemenu("schemaItem");	
//			
//			schemasPage = new SchemaList(driver);
//			schemasPage.Click_schemaName(ShareSchema);
//			
//			mainPage.Click_Settings();
//
//			schemasViewPage = new SchemaList_SchemaView(driver);
//			schemasViewPage.Click_Sharing_Tab();
//			schemasViewPage.Click_AddButton_SharingTab();
//			schemasViewPage.Schema_Sharing_SearchAndSelectUsers(UserToShareWith);//User To Share With as a predefined
//			schemasViewPage.Schema_Sharing_ClickOnCanEdit();
//			schemasViewPage.Click_Save_Button();
//			schemasViewPage.Assertion_UserCanEdit(UserToShareWith, "Can Edit");
//		}	
//	
//		//Prerequisites, Analyzer user
//		@Test(priority = 11, description = "TC C60531_1 - Users permissions - Analyzer User")
//		@Description("When I log in with Analyzer User, and navigate to content tab, and click on create new folder. Then new folder will be created successfully.")
//		@Severity(SeverityLevel.NORMAL)
//		public void Analyzer_Permissions_CreateFolder() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
//					testDataReader.getCellData("Password", "Data1"));
//			
//			allContentPage = new AllContent(driver);
//			allContentPage.Assert_allContentTabIsSelected();
//			
//			mainPage = new Skeleton(driver);
//			mainPage.Click_add();
//			mainPage.Select_fromDropdownMenu("Create Folder");
//			
//			allContentPage.AddText_NewFolder(NewFolderName);
//			allContentPage.Click_Create_CreateFolder(NewFolderName);
//			allContentPage.Assert_folderIsDisplayed(NewFolderName);
//		}
		//Prerequisites, Analyzer user + Folder to be deleted
//		@Test(priority = 12, description = "TC C60531_2 - Users permissions - Analyzer User")
//		@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click delete. Then folder will be deleted successfully.")
//		@Severity(SeverityLevel.NORMAL)
//		public void Analyzer_Permissions_DeleteFolder() 
//		{
//			loginPage = new Login(driver);
//			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
//					testDataReader.getCellData("Password", "Data1"));
//					
//			allContentPage = new AllContent(driver);
//			allContentPage.Assert_allContentTabIsSelected();
//								
//			allContentPage.Click_FolderProperties(FolderNameToDelete);
//			allContentPage.Click_FolderProperties_ManageFolderButtons("deleteFolder");
//			allContentPage.Click_FolderProperties_ManageFolderButtons_ConfirmationButtonsForDelete("Delete");
//			allContentPage.Assert_folderIsNotDisplayed(FolderNameToDelete);
//		}
		//Prerequisites, Analyzer user + Folder to be shared + User to share with
		//*******************************Under Construction********************************
		@Test(priority = 13, description = "TC C60531_3 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click share and select any person to share with. Then folder will be shared successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_ShareFolder() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
					testDataReader.getCellData("Password", "Data1"));
					
			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();		
			allContentPage.Click_FolderProperties(FolderNameToShare);
			allContentPage.Click_FolderProperties_ManageFolderButtons("shareFolder");
			
			schemasViewPage = new SchemaList_SchemaView(driver);
			schemasViewPage.Schema_Sharing_SearchAndSelectUsers(UserToShareWith);
			schemasViewPage.Schema_Sharing_ClickOnCanEdit();
			schemasViewPage.Click_Save_Button();
			schemasViewPage.Assertion_UserCanEdit(UserToShareWith, "Can Edit");
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
