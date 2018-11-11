package tests.gui.certification;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.Content;
import pageObjectModels.content.Content_Dashboard;
import pageObjectModels.content.Content_Dashboard_AnalyzeInsight;
import pageObjectModels.content.Content_Dashboard_SendDashboard;
import pageObjectModels.data.DataFiles;
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.login.SignOut;
import pageObjectModels.main.Header;
import pageObjectModels.main.OldUI_SubHeader;
import pageObjectModels.scheduler.Dashboards;
import pageObjectModels.scheduler.SchemaLoads;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;
import pageObjectModels.schemas.SchemaList_Table;
import pageObjectModels.security.Groups;
import pageObjectModels.security.Groups_Group;
import pageObjectModels.security.Users;

@Epic("incorta Certification Path - New UI.")
public class CertificationPath {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login newLoginPage;
	SignOut newSignOutPage;
	Header newHeaderObject;
	Content newContentPage;
	Content_Dashboard newDashboardPage;
	Content_Dashboard_AnalyzeInsight newAnalyzeInsightPage;
	OldUI_SubHeader newsubHeaderObject;
	Content_Dashboard_SendDashboard newSendDashboardPage;
	Users newUsersPage;
	Groups newGroupsPage;
	Groups_Group newGroupPage;
	DataSources newDataSourcesPage;
	Dashboards newSchedulerDashboardsPage;
	SchemaLoads newSchedulerSchemaLoadsPage;
	SchemaList newSchemasPage;
	SchemaList_SchemaView newSchemasViewPage;
	DataFiles newDataFilesPage;
	SchemaList_Table newSchemaTablePage;

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
	@Description("When I navigate to the login page, And I login using valid credentials Then all the section headers will be displayed, and the content tab is selected.")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingAdmin() {
		newLoginPage = new Login(driver);
		newLoginPage.navigate_toURL();
		newLoginPage.verify_correctVersionNumberIsDisplayed();
		newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));

		newHeaderObject = new Header(driver);
		newHeaderObject.verify_allSectionHeaders_areDisplayed();
		newHeaderObject.assert_sectionHeader_isSelected("Content");
	}

	@Test(priority = 2, description = "TC002 - Create User.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createUser() {
		newUsersPage = new Users(driver);
		newUsersPage.Navigate_toURL();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();

		newUserData = newUsersPage.AddNewUser();
		// usersPage.Navigate_toURL();
		newUsersPage.Assert_nameIsDisplayed(newUserData[2]);
	}

	@Test(priority = 3, description = "TC003 - Create Group.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("Given I am logged in, When I navigate to the security.groups page, And I create a new group, And I navigate to the security.groups page, Then the new group will be displayed in the groups list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createGroup() {
		newGroupsPage = new Groups(driver);
		newGroupsPage.Navigate_toURL();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();

		newGroupName = newGroupsPage.AddNewGroup();
		newGroupsPage.Navigate_toURL();
		newsubHeaderObject.SearchForContentAndAssertResultIsDisplayed_security(newGroupName);
	}

	@Test(priority = 4, description = "TC004 - Add Roles to Group.", dependsOnMethods = { "loginUsingAdmin",
			"createGroup" })
	@Description("Given I am logged in, And I have created a new group, When navigate to the groups page, An I click on the new group, And I click on add role, And I select all the roles I want to add, And I click on add, Then the new roles will be displayed in the roles list inside that group.")
	@Severity(SeverityLevel.CRITICAL)
	public void addRolesToGroup() {
		newGroupsPage = new Groups(driver);
		newGroupsPage.Navigate_toURL();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.SearchForContentAndOpenResult_security(newGroupName);

		newGroupPage = new Groups_Group(driver);
		String AddedRoles[] = new String[] { testDataReader.getCellData("GroupRoles", "Data1"),
				testDataReader.getCellData("GroupRoles", "Data2") };
		newGroupPage.AddRoles(AddedRoles);
		newGroupPage.Assert_rolesAreDisplayed(AddedRoles);
	}

	@Test(priority = 5, description = "TC005 - Add User to Group.", dependsOnMethods = { "loginUsingAdmin",
			"createUser", "createGroup" })
	@Description("Given I am logged in, And I have created a new group, When navigate to the groups page, An I click on the new group, And I click on add user, And I select all the users I want to add, And I click on add, Then the new users will be displayed in the roles list inside that group.")
	@Severity(SeverityLevel.CRITICAL)
	public void addUserToGroup() {
		newGroupsPage = new Groups(driver);
		newGroupsPage.Navigate_toURL();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.SearchForContentAndOpenResult_security(newGroupName);

		newGroupPage = new Groups_Group(driver);

		newGroupPage.AddUsers(new String[] { newUserData[2] });
		newGroupPage.Assert_usersAreDisplayed(new String[] { newUserData[2] });
	}

	@Test(priority = 6, description = "TC006 - SignOut from Admin Account.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("Given I am logged in, When I SigOut, Then SignOut success message is displayed.")
	@Severity(SeverityLevel.CRITICAL)
	public void logout() {
		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();

		newHeaderObject = new Header(driver);
		newHeaderObject.expandUserMenu();
		newHeaderObject.signOut();

		newSignOutPage = new SignOut(driver);
		newSignOutPage.assert_signOutMessageHeaderAndBodyAreCorrect();
	}

	@Test(priority = 7, description = "TC007 - Login using Newly Created User Account.", dependsOnMethods = {
			"createUser" })
	@Description("When I navigate to the login page, And I login using valid credentials, And I insert and confirm my new password, Then all content tab is selected.")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingNewlyCreatedUserAccount() {
		newLoginPage = new Login(driver);
		newLoginPage.navigate_toURL();
		newLoginPage.userLogin(testDataReader.getCellData("Tenant"), newUserData[0], newUserData[1]);
		// Actions for first time login
		String newPassword = "Automation@123";
		newLoginPage.firstTimeLogin(newUserData[1], newPassword, newPassword);
		// Storing the newly created password
		newUserData[1] = newPassword;

		newHeaderObject = new Header(driver);
		newHeaderObject.assert_sectionHeader_isSelected("Content");
	}

	@Test(priority = 8, description = "TC008 - Create DataSource.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I navigate to the DataSources page, And click add, And create a new dataSource, Then dataSource creation popup will be displayed, And dataSource name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createDatasource() {
		newDataSourcesPage = new DataSources(driver);
		newDataSourcesPage.Navigate_toURL();
		newDataSourcesPage.Assert_dataSourcesTabIsSelected();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();

		newDataSourceName = newDataSourcesPage.AddDataSource("MySQL");
		newDataSourcesPage.Assert_dataSourceCreationWasSuccessful(newDataSourceName);
		newDataSourcesPage.Assert_nameIsDisplayed(newDataSourceName);
	}

	@Test(priority = 9, description = "TC009 - Upload DataFile.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I navigate to the \"DataFiles\" page, And click add, And upload a new dataFile, Then dataFile upload success message will be displayed, And dataFile name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void uploadDataFile() {
		newDataFilesPage = new DataFiles(driver);
		newDataFilesPage.Navigate_toURL();
		newDataFilesPage.Assert_dataFilesTabIsSelected();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();

		newDataFileExtension = testDataReader.getCellData("DataFileExtension");
		newDataFileName = newDataFilesPage.AddDataFile(testDataReader.getCellData("DataFileName"),
				testDataReader.getCellData("DataFileExtension"));

		newDataFilesPage.Assert_dataFileUploadingWasSuccessful(newDataFileName);
		newDataFilesPage.Assert_nameIsDisplayed(newDataFileName);
	}

	@Test(priority = 10, description = "TC010 - Create Schema.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I navigate to the \"Schemas\" page, And click add, And create a new schema, And navigate back to the schemas page, Then the newly created schema name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createSchema() {
		newSchemasPage = new SchemaList(driver);
		newSchemasPage.Navigate_toURL();
		newSchemasPage.Assert_schemaListTabIsSelected();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();
		newsubHeaderObject.Select_fromDropdownMenu("Create Schema");

		newSchemaName = newSchemasPage.createNewSchema();

		newSchemasPage.Navigate_toURL();
		newSchemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
	}

	@Test(priority = 11, description = "TC011 - Add DataSource to Schema using Wizard.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createDatasource", "createSchema" })
	@Description("When I navigate to the \"Schemas\" page, And click on a schema, And use the schema wizard to add a new data source, Then the newly added data source table name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void addDatasourceToSchemaUsingWizard() {
		newSchemasPage = new SchemaList(driver);
		newSchemasPage.Navigate_toURL();
		newSchemasPage.click_schemaName(newSchemaName);

		newSchemasViewPage = new SchemaList_SchemaView(driver);
		newSchemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();
		newsubHeaderObject.Select_fromDropdownMenu("Schema Wizard");

		newSchemasViewPage.Wizard_AddDataSourceTable(newDataSourceName, true, "MySQL",
				testDataReader.getCellData("DatabaseTableName"));
		newDataSourceTableName = newSchemasViewPage.GetNewestTableName();
		newSchemasViewPage.Assert_tableNameIsDisplayed(newDataSourceTableName);
	}

	@Test(priority = 12, description = "TC012 - Add DataFile to Schema (with load filter).", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "uploadDataFile", "createSchema" })
	@Description("When I navigate to the \"Schemas\" page, And click on a schema, And add a new data file, Then the newly added data file table name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void addDatafileToSchemaWithLoadFilter() {
		newSchemasPage = new SchemaList(driver);
		newSchemasPage.Navigate_toURL();
		newSchemasPage.click_schemaName(newSchemaName);

		newSchemasViewPage = new SchemaList_SchemaView(driver);
		newSchemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();
		newsubHeaderObject.Hover_overDropdownMenu("Table");
		newsubHeaderObject.Select_fromDropdownMenu("File System");

		newSchemaTablePage = new SchemaList_Table(driver);
		newSchemaTablePage.Assert_AddDatasourcePopupIsDisplayed();
		newSchemaTablePage.Assert_correctDatasourceIsSelected("File System");
		newSchemaTablePage.AddDataFile("LocalFiles", "Text (csv, tsv, tab, txt)", false, newDataFileName,
				newDataFileExtension, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "UTF-8", "Comma");

		newDataFileTableName = newSchemaTablePage.SetTableName();
		newSchemaTablePage.SetLoadFilter();
		newsubHeaderObject.Click_done();

		newSchemasViewPage = new SchemaList_SchemaView(driver);
		newSchemasViewPage.Assert_tableNameIsDisplayed(newDataFileTableName);
	}

	@Test(priority = 13, description = "TC013 - Full load Schema.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createSchema" })
	@Description("When I navigate to the \"Schemas\" page, And click on a schema, And trigger a full data load, Then the data will be loaded, And the last load status will be updated.")
	@Severity(SeverityLevel.CRITICAL)
	public void fullLoadSchema() {
		newSchemasPage = new SchemaList(driver);
		newSchemasPage.Navigate_toURL();
		newSchemasPage.click_schemaName(newSchemaName);

		newSchemasViewPage = new SchemaList_SchemaView(driver);
		newSchemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		String initialLoadStatus = newSchemasViewPage.GetLastLoadStatus();
		// String initialLoadStatus = "Please load data";
		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.Click_load();
		newsubHeaderObject.Hover_overDropdownMenu("Load now");
		newsubHeaderObject.Select_fromDropdownMenu("Full");
		newSchemasViewPage.confirmLoadingData();

		newSchemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
		newSchemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);
	}

	@Test(priority = 15, description = "TC015 - Schedule Schema Incremental load.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createSchema" })
	@Description("When I navigate to the \"Schedule.SchemaLoad\" page, And create a new Incremental load job, Then the job will be displayed in the list, And the job's status will be active.")
	@Severity(SeverityLevel.CRITICAL)
	public void scheduleSchemaIncremnetalLoad() {
		newSchedulerSchemaLoadsPage = new SchemaLoads(driver);
		newSchedulerSchemaLoadsPage.Navigate_toURL();
		newSchedulerSchemaLoadsPage.Assert_schemasTabIsSelected();

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.click_add();

		newScheduledSchemaLoadJobName = newSchedulerSchemaLoadsPage.scheduleSchemaLoad(
				testDataReader.getCellData("SchemaLoadJobDescription"), newSchemaName,
				testDataReader.getCellData("SchemaLoadJobType"), testDataReader.getCellData("SchemaLoadJobDate"),
				testDataReader.getCellData("SchemaLoadJobTime"), testDataReader.getCellData("SchemaLoadJobTimeZone"),
				testDataReader.getCellData("SchemaLoadJobRecurrence"));

		newSchedulerSchemaLoadsPage.Assert_nameIsDisplayed(newScheduledSchemaLoadJobName);
		newSchedulerSchemaLoadsPage.Assert_jobStatusIsCorrect(newScheduledSchemaLoadJobName, "Active");
	}

	@Test(priority = 16, description = "TC016 - Create Dashboard and Insight (Aggregated Table).", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "fullLoadSchema" })
	@Description("When I navigate to the \"Content.AllContent\" page, And click on add, And create a dashboard, And add all the tables of the schema that was created previously to a new insight, Then the dashboard will be displayed in the list, And the insight will be displayed inside it.")
	@Severity(SeverityLevel.CRITICAL)
	public void createDashboardAndInsight() {
		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();
		newsubHeaderObject = new OldUI_SubHeader(driver);
		newHeaderObject = new Header(driver);

		newDashboardName = newContentPage.addNewCatalogItem("dashboard");
		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Added");
		newHeaderObject.assert_splashNotificationDescription_equalsExpected(
				"You've successfully added a dashboard named " + newDashboardName + ".");

		newDashboardPage = new Content_Dashboard(driver);
		newDashboardPage.addNewInsight();

		newAnalyzeInsightPage = new Content_Dashboard_AnalyzeInsight(driver);
		newAnalyzeInsightPage.selectVisualization("Aggregated");
		newAnalyzeInsightPage.addTableorSchemaToInsight(newSchemaName);
		newAnalyzeInsightPage.addColumnToInsight(newDataSourceTableName, "Quarter");
		newAnalyzeInsightPage.addColumnToInsight(newDataSourceTableName, "Units");

		newInsightName = newAnalyzeInsightPage.setInsightName();

		newsubHeaderObject.Click_done();

		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();
		newContentPage.searchForContentUsingSearchBox(newDashboardName);
		newContentPage.assert_contentSearchResult_isDisplayed(newDashboardName); // why did this fail?
		newContentPage.navigate_toContentSearchResult(newDashboardName);
		// newContentPage.changeCatalogView("Table");
		// newContentPage.tableView_assert_contentTableEntry_exists(newDashboardName);
		// newContentPage.tableView_navigate_toContentTableEntry(newDashboardName);

		newDashboardPage = new Content_Dashboard(driver);
		newDashboardPage.assert_dashboardName_isCorrect(newDashboardName);
		newDashboardPage.assert_insightName_isCorrect(newInsightName);
	}

	@Test(priority = 17, description = "TC017 - Validate Insight Data (Aggregated Table).", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createDashboardAndInsight" })
	@Description("When I navigate to the newly created insight, Then the insight will be displayed, And the data within it will be correct.")
	@Severity(SeverityLevel.CRITICAL)
	public void validateInsightData() {
		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();
		newContentPage.searchForContentUsingSearchBox(newDashboardName);
		newContentPage.assert_contentSearchResult_isDisplayed(newDashboardName);
		newContentPage.navigate_toContentSearchResult(newDashboardName);
		// newContentPage.changeCatalogView("Card");
		// newContentPage.cardView_navigate_toContentCard(newDashboardName);

		newDashboardPage = new Content_Dashboard(driver);

		newDashboardPage.aggregatedTable_assert_columnHeader_isCorrect(1, "Quarter");
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("type", 1,
				testDataReader.getCellData("InsightDataRows", "Data1"));
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("type", 2,
				testDataReader.getCellData("InsightDataRows", "Data2"));
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("type", 3,
				testDataReader.getCellData("InsightDataRows", "Data3"));
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("type", 4,
				testDataReader.getCellData("InsightDataRows", "Data4"));

		newDashboardPage.aggregatedTable_assert_columnHeader_isCorrect(2, "Units");
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("value", 1,
				testDataReader.getCellData("InsightDataMeasures", "Data1"));
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("value", 2,
				testDataReader.getCellData("InsightDataMeasures", "Data2"));
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("value", 3,
				testDataReader.getCellData("InsightDataMeasures", "Data3"));
		newDashboardPage.aggregatedTable_assert_cellValue_isCorrect("value", 4,
				testDataReader.getCellData("InsightDataMeasures", "Data4"));
	}

	@Test(priority = 18, description = "TC018 - Send Dashboard via email.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount", "createDashboardAndInsight" })
	@Description("When I navigate to the \"Content.AllContent\" page, And open a dashboard, And send it to an email address, Then a completed scheduled task will be displayed in the scheduled tasks list.")
	@Severity(SeverityLevel.CRITICAL)
	public void sendDashboardViaEmail() {
		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.searchForContentUsingSearchBox(newDashboardName);
		newContentPage.assert_contentSearchResult_isDisplayed(newDashboardName);
		newContentPage.navigate_toContentSearchResult(newDashboardName);

		newDashboardPage = new Content_Dashboard(driver);
		newDashboardPage.click_shareOptions_sendNow_button();

		newSendDashboardPage = new Content_Dashboard_SendDashboard(driver);
		newSendDashboardPage.sendDashboard_addFields("Subject", "Body", "HTML",
				testDataReader.getCellData("EmailAddress"), "", "");
		newSendDashboardPage.sendScheduleDashboard_click_send_schedule_buttons("Send");

		newHeaderObject = new Header(driver);

		newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Sent");
		newHeaderObject.assert_splashNotificationDescription_equalsExpected(
				"You've successfully sent " + newDashboardName + " via Email.");

		newSchedulerDashboardsPage = new Dashboards(driver);
		newSchedulerDashboardsPage.Navigate_toURL();
		newSchedulerDashboardsPage.ChangeJobStatus("All");

		newSchedulerDashboardsPage.Assert_nameIsDisplayed(newDashboardName);
		newSchedulerDashboardsPage.Assert_jobStatusIsCorrect(newDashboardName, "Completed");
	}

	@Test(priority = 19, description = "TC019 - Switch to admin Account.", dependsOnMethods = {
			"loginUsingNewlyCreatedUserAccount" })
	@Description("When I logout, And login as an administrator, Then I will be redirected to the All Content tab.")
	@Severity(SeverityLevel.CRITICAL)
	public void switchToAdminAccount() {
		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();

		logout();

		newLoginPage = new Login(driver);
		newLoginPage.navigate_toURL();
		newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
	}

	@Test(priority = 20, description = "TC020 - Delete User and Transfer Ownership to self.", dependsOnMethods = {
			"switchToAdminAccount" })
	@Description("Given I am logged in as an administrator, When I delete a user account that has content, And I transfer ownership to myself, Then the user will no longer be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUserAndTransferOwnershipToSelf() {
		newUsersPage = new Users(driver);
		newUsersPage.Navigate_toURL();
		newUsersPage.Select_nameCheckbox(newUserData[2]);

		newsubHeaderObject = new OldUI_SubHeader(driver);
		newsubHeaderObject.Click_actions();
		newsubHeaderObject.Select_fromDropdownMenu("Delete selection");

		newUsersPage.ConfirmUserDeletionAndTransferOwnershipToSelf();
		newUsersPage.Assert_nameIsNotDisplayed(newUserData[2]);
	}

	@Test(priority = 21, description = "TC021 - Search for Transfered Ownership Elements: Schema", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "createSchema" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsSchema() {
		/*
		 * 1 SCHEMA(s) 1 DASHBOARD(s) 1 DATASOURCE(s) 1 DATAFILE(s) 1of2 SCHEDULER(s)
		 */

		newSchemasPage = new SchemaList(driver);
		newSchemasPage.Navigate_toURL();
		newSchemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
	}

	@Test(priority = 22, description = "TC022 - Search for Transfered Ownership Elements: Dashboard", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "createDashboardAndInsight" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsDashboard() {
		newContentPage = new Content(driver);
		newContentPage.navigate_toURL();

		newContentPage.searchForContentUsingSearchBox(newDashboardName);
		newContentPage.assert_contentSearchResult_isDisplayed(newDashboardName);
	}

	@Test(priority = 23, description = "TC023 - Search for Transfered Ownership Elements: Datasource", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "createDatasource" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsDatasource() {
		newDataSourcesPage = new DataSources(driver);
		newDataSourcesPage.Navigate_toURL();
		newDataSourcesPage.Assert_nameIsDisplayed(newDataSourceName);
	}

	@Test(priority = 24, description = "TC024 - Search for Transfered Ownership Elements: DataFile", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "uploadDataFile" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsDataFile() {
		newDataFilesPage = new DataFiles(driver);
		newDataFilesPage.Navigate_toURL();
		newDataFilesPage.Assert_nameIsDisplayed(newDataFileName);
	}

	@Test(priority = 25, description = "TC025 - Search for Transfered Ownership Elements: Scheduler", dependsOnMethods = {
			"deleteUserAndTransferOwnershipToSelf", "sendDashboardViaEmail" })
	@Description("Given I am logged in as an administrator, And I deleted a user account that has content, And I transfered ownership to myself, When I search in the relevant lists, Then the transfered elements will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void searchForTransferedOwnershipElementsScheduler() {
		newSchedulerDashboardsPage = new Dashboards(driver);
		newSchedulerDashboardsPage.Navigate_toURL();
		newSchedulerDashboardsPage.ChangeJobStatus("All");
		newSchedulerDashboardsPage.Assert_nameIsDisplayed(newDashboardName);
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "certification_newUI/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		ReportManager.getTestLog();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		BrowserFactory.closeAllDrivers();
		ReportManager.getFullLog();
	}

}
