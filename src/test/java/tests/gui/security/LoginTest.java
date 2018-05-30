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
			String UserToShareWith = "automation_user_1526311329415";
			String NewFolderName = "AutomationFolder";
				
	 
		//Below Test cases is for Users login	
		//Prerequisites, Analyzer User to be defined + User name/Pass: AbdelsalamAnalyzer/AbdelsalamAnalyzer1.
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
		//Prerequisites, Analyzer User to be defined + User name/Pass: AbdelsalamIndividual/AbdelsalamIndividual1.
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
		//Prerequisites, Analyzer User to be defined + User name/Pass: AbdelsalamSchemaManager/AbdelsalamSchemaManager1.
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
		//Prerequisites, Analyzer User to be defined + User name/Pass: AbdelsalamUser/AbdelsalamUser1.
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
		//Prerequisites, Analyzer User to be defined + User name/Pass: AbdelsalamUserManager/AbdelsalamUserManager1.
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
		//Prerequisites, Analyzer User to be defined + User name/Pass: AbdelsalamSuper/AbdelsalamSuper1.
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

		//Below Test cases is for Users permissions | Schema Manager user
		//Prerequisites, Schema Manager user + Connection credentials to data source
		@Test(priority = 7, description = "TC C60535_1 - Schema Manager Permissions ")
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
			
			String MyDataSourceName = dataSourcesPage.AddDataSource("MySQL");
			dataSourcesPage.Assert_dataSourceCreationWasSuccessful(MyDataSourceName);
			dataSourcesPage.Assert_nameIsDisplayed(MyDataSourceName);
		}
		
		//Prerequisites, Schema Manager user + Test data defined for creating new schema 
		@Test(priority = 8, description = "TC C60535_2 - Schema Manager Permissions ")
		@Description("When I log in with Schema manager user, then i'll be able to create a new schema")
		@Severity(SeverityLevel.CRITICAL)
		public void SchemaManager_Permissions_CreateSchema()
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
				testDataReader.getCellData("Password", "Data3"));
			
			mainPage = new Skeleton(driver);
			mainPage.Click_Element_Sidemenu("schemaItem");
			mainPage.Click_add();
			mainPage.Select_fromDropdownMenu("Create Schema");
			
			schemasPage = new SchemaList(driver);
			schemasPage.createNewSchema(NewSchemaName, NewSchemaDescription);
			
			mainPage.Click_Element_Sidemenu("schemaItem");
			schemasPage.Assert_schemaNameIsDisplayed(NewSchemaName);
		}
		
		//Prerequisites, Schema Manager User + Data Source available + New schema available [ExistingSchema] 
		@Test(priority = 9, description = "TC C60535_3 - Schema Manager Permissions")
		@Description("When I log in with Schema manager user, Given I've a data source and schema, then I'll be able to go to schema wizard to add the data source to the schema.")
		@Severity(SeverityLevel.CRITICAL)
		public void SchemaManager_Permissions_AddDataSourceToSchema()
		{	
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
				testDataReader.getCellData("Password", "Data3"));

			//Create new data source as a prerequisite.
			mainPage = new Skeleton(driver);
			mainPage.Click_Element_Sidemenu("dataSourcesItem");			
			dataSourcesPage= new DataSources(driver);
			dataSourcesPage.Assert_dataSourcesTabIsSelected();
			mainPage.Click_add();
			String ExistingDataSourceName = dataSourcesPage.AddDataSource("MySQL");
			dataSourcesPage.Assert_dataSourceCreationWasSuccessful(ExistingDataSourceName);
			dataSourcesPage.Assert_nameIsDisplayed(ExistingDataSourceName);
			
			//Add Data Source to schema
			mainPage.Click_Element_Sidemenu("schemaItem");
			
			schemasPage = new SchemaList(driver);
			schemasPage.Click_schemaName(ExistingSchemaNAME);
			
			mainPage.Click_add();
			mainPage.Select_fromDropdownMenu("Schema Wizard");

			schemasViewPage = new SchemaList_SchemaView(driver);
			schemasViewPage.Wizard_AddDataSourceTable(ExistingDataSourceName, true, "MySQL",testDataReader.getCellData("DatabaseTableName"));
			
			NewDataSourceTableName = schemasViewPage.GetNewestTableName();
			schemasViewPage.Assert_tableNameIsDisplayed(NewDataSourceTableName);
		}
		
		//Prerequisites , Schema Manager User + Data Source available + Schema available + Data source is added to the schema
		//**************Under Construction***************
		@Test(priority = 10, description = "TC C60535_4 - Schema Manager Permissions")
		@Description("Given I log in with Schema manager user, and I've added a data source with schema, and click load data from the schema. Then Data is loaded normally.")
		@Severity(SeverityLevel.CRITICAL)
		public void SchemaManager_Permissions_LoadData()
		{	
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
				testDataReader.getCellData("Password", "Data3"));

			mainPage = new Skeleton(driver);
			mainPage.Click_Element_Sidemenu("schemaItem");	
			
			schemasPage = new SchemaList(driver);
			schemasPage.Click_schemaName(LoadDataSchema);
			
			schemasViewPage = new SchemaList_SchemaView(driver);
			String initialLoadStatus = schemasViewPage.GetLastLoadStatus();
			
			mainPage.Click_load();
			mainPage.Hover_overDropdownMenu("Load now");
			mainPage.Select_fromDropdownMenu("Full");
			
			schemasViewPage.confirmLoadingData();
			schemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
			schemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);
		}
		
		@Test(priority = 10, description = "TC C60535_5 - Schema Manager Permissions")
		@Description("Given I log in with Schema manager user, and I've added a data source with schema, and click load data from the schema. Then Data is loaded normally.")
		@Severity(SeverityLevel.CRITICAL)
		public void SchemaManager_Permissions_ShareSchemaToEdit()
		{	
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"), testDataReader.getCellData("Username", "Data3"), 
				testDataReader.getCellData("Password", "Data3"));

			mainPage = new Skeleton(driver);
			mainPage.Click_Element_Sidemenu("schemaItem");	
			
			schemasPage = new SchemaList(driver);
			schemasPage.Click_schemaName(ShareSchema);
			
			mainPage.Click_Settings();

			schemasViewPage = new SchemaList_SchemaView(driver);
			schemasViewPage.Click_Sharing_Tab();
			schemasViewPage.Click_AddButton_SharingTab();
			schemasViewPage.Schema_Sharing_SearchAndSelectUsers(UserToShareWith);//User To Share With as a predefined
			schemasViewPage.Schema_Sharing_ClickOnCanEdit();
			schemasViewPage.Click_Save_Button();
			schemasViewPage.Assertion_UserSharedWith(UserToShareWith);
			schemasViewPage.Assertion_UserCanEdit(UserToShareWith);
			
		}
		
		//Below test cases is for users permission | Analyzer user
		//******************************In Progress*****************************
		@Test(priority = 11, description = "TC C60531_1 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, only scheduler and content tab will be exist.")
		@Severity(SeverityLevel.CRITICAL)
		public void Analyzer_Permissions_blabla() 
		{
			loginPage = new Login(driver);
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
					testDataReader.getCellData("Password", "Data1"));
			
			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();
			
			mainPage = new Skeleton(driver);
			mainPage.Click_add();
			mainPage.Select_fromDropdownMenu("Create Folder");
			
			allContentPage.AddText_NewFolder(NewFolderName);
			allContentPage.Assert_folderIsDisplayed(NewFolderName);
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
