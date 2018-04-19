package tests.gui.certification;

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
import pageObjectModels.modules.content.Content_AllContent;
import pageObjectModels.modules.content.Content_AllContent_Dashboard;
import pageObjectModels.modules.content.Content_AllContent_Dashboard_Insight;
import pageObjectModels.modules.data.Data_DataFiles;
import pageObjectModels.modules.data.Data_DataSources;
import pageObjectModels.modules.login.Login_Login;
import pageObjectModels.modules.login.Login_Logout;
import pageObjectModels.modules.main.Main_Skeleton;
import pageObjectModels.modules.scheduler.Scheduler_Dashboards;
import pageObjectModels.modules.scheduler.Scheduler_SchemaLoads;
import pageObjectModels.modules.schemas.Schemas_SchemaList;
import pageObjectModels.modules.schemas.Schemas_SchemaList_SchemaView;
import pageObjectModels.modules.schemas.Schemas_SchemaList_Table;
import pageObjectModels.modules.security.Security_Groups;
import pageObjectModels.modules.security.Security_Groups_Group;
import pageObjectModels.modules.security.Security_Users;

@Epic("incorta Certification Path.")
public class CertificationPath {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login_Login loginPage;
	Content_AllContent allContentPage;
	Security_Users usersPage;
	Main_Skeleton mainPage;
	Security_Groups groupsPage;
	Security_Groups_Group groupPage;
	Login_Logout logoutPage;
	Data_DataSources dataSourcesPage;
	Data_DataFiles dataFilesPage;
	Schemas_SchemaList schemasPage;
	Schemas_SchemaList_SchemaView schemasViewPage;
	Schemas_SchemaList_Table schemaTablePage;
	Content_AllContent_Dashboard_Insight insightPage;
	Content_AllContent_Dashboard dashboardPage;
	Scheduler_Dashboards schedulerDashboardsPage;
	Scheduler_SchemaLoads schedulerSchemaLoadsPage;

	// Declaring public variables that will be shared between tests
	String[] newUserData; // username, password, displayname
	String newGroupName;
	String newDataSourceName;
	String newDataFileName, newDataFileExtension;
	String newSchemaName;
	String newScheduledSchemaLoadJobName;
	String newDataSourceTableName, newDataFileTableName;
	String newDashboardName, newInsightName;

	@Test(priority = 1, description = "TC001 - Login using Admin Account.")
	@Description("When I navigate to the login page, And I login using valid credentials Then all content tab is selected.")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingAdmin() {
		loginPage = new Login_Login(driver);
		loginPage.Navigate_toURL();
		loginPage.Assert_correctVersionNumberIsDisplayed();
		loginPage.Login(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
		allContentPage = new Content_AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
	}

	@Test(priority = 2, description = "TC002 - Create User.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createUser() {
		usersPage = new Security_Users(driver);
		usersPage.Navigate_toURL();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();

		newUserData = usersPage.AddNewUser();
		// usersPage.Navigate_toURL();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);
	}

	@Test(priority = 3, description = "TC003 - Create Group.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("Given I am logged in, When I navigate to the security.groups page, And I create a new group, And I navigate to the security.groups page, Then the new group will be displayed in the groups list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createGroup() {
		groupsPage = new Security_Groups(driver);
		groupsPage.Navigate_toURL();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();

		newGroupName = groupsPage.AddNewGroup();
		groupsPage.Navigate_toURL();
		groupsPage.Assert_groupIsDisplayed(newGroupName);
	}

	@Test(priority = 4, description = "TC004 - Add Roles to Group.", dependsOnMethods = { "loginUsingAdmin",
			"createGroup" })
	@Description("Given I am logged in, And I have created a new group, When navigate to the groups page, An I click on the new group, And I click on add role, And I select all the roles I want to add, And I click on add, Then the new roles will be displayed in the roles list inside that group.")
	@Severity(SeverityLevel.CRITICAL)
	public void addRolesToGroup() {
		groupsPage = new Security_Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.Click_group(newGroupName);

		groupPage = new Security_Groups_Group(driver);
		String AddedRoles[] = new String[] { testDataReader.getCellData("GroupRoles", "Data1"),
				testDataReader.getCellData("GroupRoles", "Data2") };
		groupPage.AddRoles(AddedRoles);
		groupPage.Assert_rolesAreDisplayed(AddedRoles);
	}

	@Test(priority = 5, description = "TC005 - Add User to Group.", dependsOnMethods = { "loginUsingAdmin",
			"createUser", "createGroup" })
	@Description("Given I am logged in, And I have created a new group, When navigate to the groups page, An I click on the new group, And I click on add user, And I select all the users I want to add, And I click on add, Then the new users will be displayed in the roles list inside that group.")
	@Severity(SeverityLevel.CRITICAL)
	public void addUserToGroup() {
		groupsPage = new Security_Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.Click_group(newGroupName);

		groupPage = new Security_Groups_Group(driver);

		groupPage.AddUsers(new String[] { newUserData[2] });
		groupPage.Assert_usersAreDisplayed(new String[] { newUserData[2] });
	}

	@Test(priority = 6, description = "TC006 - Logout from Admin Account.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("Given I am logged in, When I logout, Then logout success message is displayed.")
	@Severity(SeverityLevel.CRITICAL)
	public void logout() {
		mainPage = new Main_Skeleton(driver);
		mainPage.Select_fromUserMenu("Logout");

		logoutPage = new Login_Logout(driver);
		logoutPage.Assert_logoutMessageHeaderAndBodyAreCorrect();
	}

	@Test(priority = 7, description = "TC007 - Login using Newly Created User Account.", dependsOnMethods = {
			"createUser" })
	@Description("When I navigate to the login page, And I login using valid credentials, And I insert and confirm my new password, Then all content tab is selected.")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingNewlyCreatedUserAccount() {
		loginPage = new Login_Login(driver);
		loginPage.Navigate_toURL();
		loginPage.Login(testDataReader.getCellData("Tenant"), newUserData[0], newUserData[1]);
		// Actions for first time login
		String newPassword = "Automation";
		loginPage.FirstTimeLogin(newUserData[1], newPassword, newPassword);
		// Storing the newly created password
		newUserData[1] = newPassword;
		allContentPage = new Content_AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
	}

	@Test(priority = 8, description = "TC008 - Create DataSource.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I navigate to the DataSources page, And click add, And create a new dataSource, Then dataSource creation popup will be displayed, And dataSource name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createDatasource() {
		dataSourcesPage = new Data_DataSources(driver);
		dataSourcesPage.Navigate_toURL();
		dataSourcesPage.Assert_dataSourcesTabIsSelected();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();

		newDataSourceName = dataSourcesPage.AddDataSource("MySQL");
		dataSourcesPage.Assert_dataSourceCreationWasSuccessful(newDataSourceName);
		dataSourcesPage.Assert_nameIsDisplayed(newDataSourceName);
	}

	@Test(priority = 9, description = "TC009 - Upload DataFile.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I navigate to the \"DataFiles\" page, And click add, And upload a new dataFile, Then dataFile upload success message will be displayed, And dataFile name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void uploadDataFile() {
		dataFilesPage = new Data_DataFiles(driver);
		dataFilesPage.Navigate_toURL();
		dataFilesPage.Assert_dataFilesTabIsSelected();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();

		newDataFileExtension = testDataReader.getCellData("DataFileExtension");
		newDataFileName = dataFilesPage.AddDataFile(testDataReader.getCellData("DataFileName"),
				testDataReader.getCellData("DataFileExtension"));

		dataFilesPage.Assert_dataFileUploadingWasSuccessful(newDataFileName);
		dataFilesPage.Assert_nameIsDisplayed(newDataFileName);
	}

	@Test(priority = 10, description = "TC010 - Create Schema.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I navigate to the \"Schemas\" page, And click add, And create a new schema, And navigate back to the schemas page, Then the newly created schema name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createSchema() {
		schemasPage = new Schemas_SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Schema");

		newSchemaName = schemasPage.createNewSchema();

		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
	}

	@Test(priority = 11, description = "TC011 - Add DataSource to Schema using Wizard.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createDatasource", "createSchema" })
	@Description("When I navigate to the \"Schemas\" page, And click on a schema, And use the schema wizard to add a new data source, Then the newly added data source table name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void addDatasourceToSchemaUsingWizard() {
		schemasPage = new Schemas_SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Click_schemaName(newSchemaName);

		schemasViewPage = new Schemas_SchemaList_SchemaView(driver);
		schemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Schema Wizard");

		schemasViewPage.Wizard_AddDataSourceTable(newDataSourceName, true, "MySQL",
				testDataReader.getCellData("DatabaseTableName"));
		newDataSourceTableName = schemasViewPage.GetNewestTableName();
		schemasViewPage.Assert_tableNameIsDisplayed(newDataSourceTableName);
	}

	@Test(priority = 12, description = "TC012 - Add DataFile to Schema (with load filter).", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "uploadDataFile", "createSchema" })
	@Description("When I navigate to the \"Schemas\" page, And click on a schema, And add a new data file, Then the newly added data file table name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void addDatafileToSchemaWithLoadFilter() {
		schemasPage = new Schemas_SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Click_schemaName(newSchemaName);

		schemasViewPage = new Schemas_SchemaList_SchemaView(driver);
		schemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();
		mainPage.Hover_overDropdownMenu("Table");
		mainPage.Select_fromDropdownMenu("File System");

		schemaTablePage = new Schemas_SchemaList_Table(driver);
		schemaTablePage.Assert_AddDatasourcePopupIsDisplayed();
		schemaTablePage.Assert_correctDatasourceIsSelected("File System");
		schemaTablePage.AddDataFile("LocalFiles", "Text (csv, tsv, tab, txt)", false, newDataFileName,
				newDataFileExtension, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "UTF-8", "Comma");

		newDataFileTableName = schemaTablePage.SetTableName();
		schemaTablePage.SetLoadFilter();
		mainPage.Click_done();
		schemasViewPage.Assert_tableNameIsDisplayed(newDataFileTableName);
	}

	@Test(priority = 13, description = "TC013 - Full load Schema.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createSchema" })
	@Description("When I navigate to the \"Schemas\" page, And click on a schema, And trigger a full data load, Then the data will be loaded, And the last load status will be updated.")
	@Severity(SeverityLevel.CRITICAL)
	public void fullLoadSchema() {
		schemasPage = new Schemas_SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Click_schemaName(newSchemaName);

		schemasViewPage = new Schemas_SchemaList_SchemaView(driver);
		schemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		String initialLoadStatus = schemasViewPage.GetLastLoadStatus();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_load();
		mainPage.Hover_overDropdownMenu("Load now");
		mainPage.Select_fromDropdownMenu("Full");
		schemasViewPage.confirmLoadingData();

		schemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
		schemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);
	}

	@Test(priority = 15, description = "TC015 - Schedule Schema Incremental load.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createSchema" })
	@Description("When I navigate to the \"Schedule.SchemaLoad\" page, And create a new Incremental load job, Then the job will be displayed in the list, And the job's status will be active.")
	@Severity(SeverityLevel.CRITICAL)
	public void scheduleSchemaIncremnetalLoad() {
		schedulerSchemaLoadsPage = new Scheduler_SchemaLoads(driver);
		schedulerSchemaLoadsPage.Navigate_toURL();
		schedulerSchemaLoadsPage.Assert_schemasTabIsSelected();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();

		newScheduledSchemaLoadJobName = schedulerSchemaLoadsPage.scheduleSchemaLoad(
				testDataReader.getCellData("SchemaLoadJobDescription"), newSchemaName,
				testDataReader.getCellData("SchemaLoadJobType"), testDataReader.getCellData("SchemaLoadJobDate"),
				testDataReader.getCellData("SchemaLoadJobTime"), testDataReader.getCellData("SchemaLoadJobTimeZone"),
				testDataReader.getCellData("SchemaLoadJobRecurrence"));

		schedulerSchemaLoadsPage.Assert_nameIsDisplayed(newScheduledSchemaLoadJobName);
		schedulerSchemaLoadsPage.Assert_jobStatusIsCorrect(newScheduledSchemaLoadJobName, "Active");
	}

	@Test(priority = 16, description = "TC016 - Create Dashboard and Insight.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "fullLoadSchema" })
	@Description("When I navigate to the \"Content.AllContent\" page, And click on add, And create a dashboard, And add all the tables of the schema that was created previously to a new insight, Then the dashboard will be displayed in the list, And the insight will be displayed inside it.")
	@Severity(SeverityLevel.CRITICAL)
	public void createDashboardAndInsight() {
		allContentPage = new Content_AllContent(driver);
		allContentPage.Navigate_toURL();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Dashboard");

		newDashboardName = allContentPage.setNewDashboardName();

		insightPage = new Content_AllContent_Dashboard_Insight(driver);
		insightPage.addTableorSchemaToInsight(newSchemaName);
		insightPage.addColumnToInsight(newDataSourceTableName, "Quarter");
		insightPage.addColumnToInsight(newDataSourceTableName, "Units");

		newInsightName = insightPage.setInsightName();
		mainPage.Click_done();

		allContentPage.Navigate_toURL();
		mainPage.SearchForContentAndOpenResult(newDashboardName);

		dashboardPage = new Content_AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(newDashboardName);
		dashboardPage.Assert_insightName(newInsightName);
	}

	@Test(priority = 17, description = "TC017 - Send Dashboard via email.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createDashboardAndInsight" })
	@Description("When I navigate to the \"Content.AllContent\" page, And open a dashboard, And send it to an email address, Then a completed scheduled task will be displayed in the scheduled tasks list.")
	@Severity(SeverityLevel.CRITICAL)
	public void sendDashboardViaEmail() {
		allContentPage = new Content_AllContent(driver);
		allContentPage.Navigate_toURL();

		mainPage = new Main_Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(newDashboardName);
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Send");

		dashboardPage = new Content_AllContent_Dashboard(driver);
		dashboardPage.selectEmailFormat(testDataReader.getCellData("EmailFormat"));
		dashboardPage.addUserEmailToRecieversList(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleEmailSending();

		schedulerDashboardsPage = new Scheduler_Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ChangeJobStatus("All");

		schedulerDashboardsPage.Assert_nameIsDisplayed(newDashboardName);
		schedulerDashboardsPage.Assert_jobStatusIsCorrect(newDashboardName, "Completed");
	}

	@Test(priority = 18, description = "TC018 - Switch to admin Account.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I logout, And login as an administrator, Then I will be redirected to the All Content tab.")
	@Severity(SeverityLevel.CRITICAL)
	public void switchToAdminAccount() {
		allContentPage = new Content_AllContent(driver);
		allContentPage.Navigate_toURL();

		logout();
		loginUsingAdmin();
	}

	@Test(priority = 19, description = "TC019 - Delete User and Transfer Ownership to self.", dependsOnMethods = {
			"switchToAdminAccount" })
	@Description("Given I am logged in as an administrator, When I delete a user account that has content, And I transfer ownership to myself, Then the user will no longer be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUserAndTransferOwnershipToSelf() {
		usersPage = new Security_Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Select_nameCheckbox(newUserData[2]);

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");

		usersPage.ConfirmUserDeletionAndTransferOwnershipToSelf();
		usersPage.Assert_nameIsNotDisplayed(newUserData[2]);
	}

	@Test(priority = 20, description = "TC020 - Search for Transfered Ownership Elements: Schema", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "createSchema" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsSchema() {
		/*
		 * 1 SCHEMA(s) 1 DASHBOARD(s) 1 DATASOURCE(s) 1 DATAFILE(s) 1of2 SCHEDULER(s)
		 */

		schemasPage = new Schemas_SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
	}

	@Test(priority = 21, description = "TC021 - Search for Transfered Ownership Elements: Dashboard", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "createDashboardAndInsight" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsDashboard() {
		allContentPage = new Content_AllContent(driver);
		allContentPage.Navigate_toURL();
		// allContentPage.Assert_dashboardIsDisplayed(newDashboardName);
		mainPage = new Main_Skeleton(driver);
		mainPage.SearchForContentAndAssertResultIsDisplayed(newDashboardName);
	}

	@Test(priority = 22, description = "TC022 - Search for Transfered Ownership Elements: Datasource", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "createDatasource" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsDatasource() {
		dataSourcesPage = new Data_DataSources(driver);
		dataSourcesPage.Navigate_toURL();
		dataSourcesPage.Assert_nameIsDisplayed(newDataSourceName);
	}

	@Test(priority = 23, description = "TC023 - Search for Transfered Ownership Elements: DataFile", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "uploadDataFile" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsDataFile() {
		dataFilesPage = new Data_DataFiles(driver);
		dataFilesPage.Navigate_toURL();
		dataFilesPage.Assert_nameIsDisplayed(newDataFileName);
	}

	@Test(priority = 24, description = "TC024 - Search for Transfered Ownership Elements: Scheduler", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsScheduler() {
		schedulerDashboardsPage = new Scheduler_Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ChangeJobStatus("All");
		schedulerDashboardsPage.Assert_nameIsDisplayed(newDashboardName);
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
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
