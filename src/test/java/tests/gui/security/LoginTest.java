package tests.gui.security;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
import pageObjectModels.security.Users;

public class LoginTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;

	// Declaring Page Objects that will be used in the tests
	Login loginPage;
	Skeleton mainPage;
	DataSources dataSourcesPage;
	SchemaList schemasPage;
	SchemaList_SchemaView schemasViewPage;
	AllContent allContentPage;
	Users usersPage;

	// Declaring public variables that will be shared between tests
	String NewSchemaName;
	String ExistingSchemaNAME = "Abdelsalan_Automation_Schema"; // Existing Schema to be used as predefined
	String NewDataSourceTableName; // Table name to be used in assertion
	String LoadDataSchema = "LoadDataSchema1";// Predefined Schema with data source to load data into it.
	String ShareSchema = "ShareSchema";
	String UserToShareWith = "AbdelsalamSuper"; // User To Share With as a predefined, he should be super user or schema
												// manager to grant 'Can Edit' permission
	String DataSourceName;
	String initialLoadStatus;

	// Prerequisites, Manually created 'Analyzer User' [User name/Pass:
	// AbdelsalamAnalyzer/AbdelsalamAnalyzer1]
	@Test(priority = 1, description = "TC C60554_1 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, Then only scheduler and content tab will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithAnalyzer() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		mainPage = new Skeleton(driver);
		mainPage.AssertElementExist_Sidemenu("contentItem");
		mainPage.AssertElementExist_Sidemenu("schedulerItem");
	}

	// Prerequisites, Manually created 'Individual User' [User name/Pass:
	// AbdelsalamIndividual/AbdelsalamIndividual1]
	@Test(priority = 2, description = "TC C60554_2 - Users permissions - Individual User")
	@Description("When I log in with Individual User, Then only scheduler and content tab will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithIndividual() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data2"),
				testDataReader.getCellData("Username", "Data2"), testDataReader.getCellData("Password", "Data2"));

		mainPage = new Skeleton(driver);
		mainPage.AssertElementExist_Sidemenu("contentItem");
		mainPage.AssertElementExist_Sidemenu("schedulerItem");
	}

	// Prerequisites, Manually created 'Normal User' [User name/Pass:
	// AbdelsalamUser/AbdelsalamUser1]
	@Test(priority = 3, description = "TC C60554_3 - Users permissions - Normal User")
	@Description("When I log in with a normal User, Then only scheduler and content tab will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithNormalUser() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data4"),
				testDataReader.getCellData("Username", "Data4"), testDataReader.getCellData("Password", "Data4"));

		mainPage = new Skeleton(driver);
		mainPage.AssertElementExist_Sidemenu("contentItem");
		mainPage.AssertElementExist_Sidemenu("schedulerItem");
	}

	// Prerequisites, Manually created 'Schema Manager' [User name/Pass:
	// AbdelsalamSchemaManager/AbdelsalamSchemaManager1]
	@Test(priority = 4, description = "TC C60554_4 - Users permissions - Schema Manager User")
	@Description("When I log in with Schema Manager User. Then only scheduler, content, Data Sources, Business Schema and Schemas tabs will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithSchemaManager() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.AssertElementExist_Sidemenu("contentItem");
		mainPage.AssertElementExist_Sidemenu("schedulerItem");
		mainPage.AssertElementExist_Sidemenu("dataSourcesItem");
		mainPage.AssertElementExist_Sidemenu("businessSchemaItem");
		mainPage.AssertElementExist_Sidemenu("schemaItem");
	}

	// Prerequisites, Manually created 'User Manager' [User name/Pass:
	// AbdelsalamUserManager/AbdelsalamUserManager1]
	@Test(priority = 5, description = "TC C60554_5 - Users permissions - User Manager")
	@Description("When I log in with User Manager User. Then only scheduler, content and security tabs will be exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithUserManager() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data5"),
				testDataReader.getCellData("Username", "Data5"), testDataReader.getCellData("Password", "Data5"));

		mainPage = new Skeleton(driver);
		mainPage.AssertElementExist_Sidemenu("contentItem");
		mainPage.AssertElementExist_Sidemenu("schedulerItem");
		mainPage.AssertElementExist_Sidemenu("securityItem");
	}

	// Prerequisites, Manually created 'Super User' [User name/Pass:
	// AbdelsalamSuper/AbdelsalamSuper1]
	@Test(priority = 6, description = "TC C60554_6 - Users permissions - SUPER user")
	@Description("When I log in with Super User. Then all tabs will exist.")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginWithSuperUser() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data6"),
				testDataReader.getCellData("Username", "Data6"), testDataReader.getCellData("Password", "Data6"));

		mainPage = new Skeleton(driver);
		mainPage.AssertElementExist_Sidemenu("contentItem");
		mainPage.AssertElementExist_Sidemenu("schedulerItem");
		mainPage.AssertElementExist_Sidemenu("securityItem");
		mainPage.AssertElementExist_Sidemenu("dataSourcesItem");
		mainPage.AssertElementExist_Sidemenu("schemaItem");
		mainPage.AssertElementExist_Sidemenu("businessSchemaItem");
	}

	@Test(priority = 7, description = "C1275 - Successful Login")
	@Description("When I navigate to the login page, And I login using valid credentials Then login is done successfully and all content tab page is displayed")
	@Severity(SeverityLevel.CRITICAL)
	public void assertLoginWorksAsExpected() {
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data7"),
				testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));
		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
	}

	/*
	 * Login Using New Created User, Need to be logged in with Admin/User manager
	 * account, because user is created first then login with the new account
	 * created
	 */
	@Test(priority = 8, description = "C60950 - Login using new user created.")
	@Description("When I login with new created user, Then I will be redirected to change password page, And I will logged in successfully")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingNewlyCreatedUserAccount() {

		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data7"),
				testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));

		// Create New User

		usersPage = new Users(driver);
		usersPage.Navigate_toURL();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();

		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);

		// Navigate to login page, and login using the new created user.
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), newUserData[0], newUserData[1]);

		// Actions for first time login
		loginPage.FirstTimeLogin(newUserData[1], newUserData[0], newUserData[0]); // take user name as input for the new
																					// password
		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
	}

	// Prerequisites, Schema Manager user + Connection credentials to data source
	@Test(priority = 9, description = "TC C60535_1 - Schema Manager Permissions ")
	@Description("When I log in with Schema manager user and click add data source, then a new data source will be created.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_AddDataSource() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("dataSourcesItem");

		dataSourcesPage = new DataSources(driver);
		dataSourcesPage.Assert_dataSourcesTabIsSelected();

		mainPage.Click_add();

		DataSourceName = dataSourcesPage.AddDataSource("MySQL");
		dataSourcesPage.Assert_dataSourceCreationWasSuccessful(DataSourceName);
		dataSourcesPage.Assert_nameIsDisplayed(DataSourceName);
	}

	// Prerequisites, Schema Manager user + Test data defined for creating new
	// schema
	@Test(priority = 10, description = "TC C60535_2 - Schema Manager Permissions ")
	@Description("When I log in with Schema manager user and click add to create a new schema, then a new schema will be created")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_CreateSchema() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("schemaItem");
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Schema");

		schemasPage = new SchemaList(driver);
		NewSchemaName = schemasPage.createNewSchema();

		mainPage.Click_Element_Sidemenu("schemaItem");
		schemasPage.Assert_schemaNameIsDisplayed(NewSchemaName);
	}

	// Prerequisites, Schema Manager User + Data Source available + New schema
	// available [ExistingSchema]
	@Test(priority = 11, description = "TC C60535_3 - Schema Manager Permissions")
	@Description("Given I've a data source and schema, When I log in with Schema manager user, and add data source to schema using schema wizard, Then data source will be added to the schema.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_AddDataSourceToSchema() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		// Create new data source as a prerequisite.
		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("dataSourcesItem");

		dataSourcesPage = new DataSources(driver);
		dataSourcesPage.Assert_dataSourcesTabIsSelected();

		mainPage.Click_add();

		DataSourceName = dataSourcesPage.AddDataSource("MySQL");
		dataSourcesPage.Assert_dataSourceCreationWasSuccessful(DataSourceName);
		dataSourcesPage.Assert_nameIsDisplayed(DataSourceName);

		// Add Data Source to schema
		mainPage.Click_Element_Sidemenu("schemaItem");

		schemasPage = new SchemaList(driver);
		schemasPage.Click_schemaName(ExistingSchemaNAME);

		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Schema Wizard");

		schemasViewPage = new SchemaList_SchemaView(driver);
		schemasViewPage.Wizard_AddDataSourceTable(DataSourceName, true, "MySQL",
				testDataReader.getCellData("DatabaseTableName"));

		NewDataSourceTableName = schemasViewPage.GetNewestTableName();
		schemasViewPage.Assert_tableNameIsDisplayed(NewDataSourceTableName);
	}

	// Prerequisites, Schema Manager User + Data Source available + Schema available
	// + Data source is added to the schema
	@Test(priority = 12, description = "TC C60535_4 - Schema Manager Permissions")
	@Description("Given I logged in with Schema manager user, and I've added a data source with schema, When I click load data from the schema. Then Data is loaded successfully.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_LoadData() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("schemaItem");

		schemasPage = new SchemaList(driver);
		schemasPage.Click_schemaName(LoadDataSchema);

		schemasViewPage = new SchemaList_SchemaView(driver);
		initialLoadStatus = schemasViewPage.GetLastLoadStatus();

		mainPage.Click_load();
		mainPage.Hover_overDropdownMenu("Load now");
		mainPage.Select_fromDropdownMenu("Full");

		schemasViewPage.confirmLoadingData();
		schemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
		schemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);
	}

	// Prerequisites, Schema Manager User + Data Source Available + Schema Available
	@Test(priority = 13, description = "TC C60535_5 - Schema Manager Permissions")
	@Description("Given I log in with Schema manager user, and data source added to the schema, and I click on schema settings, and share schema with any user to [To Edit]. Then schema is shared successfully with Edit feature.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_ShareSchemaToEdit() {
		loginPage = new Login(driver);
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("schemaItem");

		schemasPage = new SchemaList(driver);
		schemasPage.Click_schemaName(ShareSchema);

		mainPage.Click_Settings();

		schemasViewPage = new SchemaList_SchemaView(driver);
		schemasViewPage.Click_Sharing_Tab();
		schemasViewPage.Click_AddButton_SharingTab();
		schemasViewPage.Schema_Sharing_SearchAndSelectUsers(UserToShareWith);// User To Share With as a predefined, he
																				// should be super user or schema
																				// manager to grant 'Can Edit'
																				// permission
		schemasViewPage.Schema_Sharing_ClickOnUserPermission("Can Edit");
		schemasViewPage.Click_Save_Button();
		schemasViewPage.Assertion_UserPermission(UserToShareWith, "Can Edit");
	}

	@BeforeMethod
	public void beforeMethod() {
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "security/TestData.xlsx");
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
