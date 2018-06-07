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
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;

public class LoginTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used in the tests
	Login loginPage;
	Skeleton mainPage;
	DataSources dataSourcesPage;
	SchemaList schemasPage;
	SchemaList_SchemaView schemasViewPage;

	// Declaring public variables that will be shared between tests
	String NewSchemaName;
	String ExistingSchemaNAME = "Abdelsalan_Automation_Schema"; // Existing Schema to be used as predefined
	String NewDataSourceTableName; // Table name to be used in assertion
	String LoadDataSchema = "LoadDataSchema1";// Predefined Schema with data source to load data into it.
	String ShareSchema = "ShareSchema";
	String UserToShareWith = "AbdelsalamSuper"; // User To Share With as a predefined, he should be super user or schema manager to grant 'Can Edit' permission
	String DataSourceName;
	String initialLoadStatus;

	// Prerequisites, Schema Manager user + Connection credentials to data source
	@Test(priority = 7, description = "TC C60535_1 - Schema Manager Permissions ")
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
	@Test(priority = 8, description = "TC C60535_2 - Schema Manager Permissions ")
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
	@Test(priority = 9, description = "TC C60535_3 - Schema Manager Permissions")
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
	@Test(priority = 10, description = "TC C60535_4 - Schema Manager Permissions")
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
	@Test(priority = 11, description = "TC C60535_5 - Schema Manager Permissions")
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
		schemasViewPage.Schema_Sharing_SearchAndSelectUsers(UserToShareWith);// User To Share With as a predefined, he should be super user or schema manager to grant 'Can Edit' permission
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
