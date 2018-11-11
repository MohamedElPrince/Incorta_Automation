package tests.gui.security;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.Content;
import pageObjectModels.content.Content_Dashboard;
import pageObjectModels.content.Content_Dashboard_AnalyzeInsight;
import pageObjectModels.content.Content_Dashboard_ScheduleDashboard;
import pageObjectModels.content.Content_Dashboard_SendDashboard;
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.login.SignOut;
import pageObjectModels.main.Header;
import pageObjectModels.main.OldUI_SubHeader;
import pageObjectModels.scheduler.Dashboards;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;
import pageObjectModels.security.Groups;
import pageObjectModels.security.Groups_Group;
import pageObjectModels.security.Users;

@Epic("Security -> Roles")
public class RolesTest {

    // Declaring web-driver and excel reader instances
    private WebDriver driver;
    private ExcelFileManager testDataReader;

    // Declaring Page Objects that will be used in the tests
    private Login loginPage;
    private SignOut logoutPage;
    private OldUI_SubHeader subHeaderObject;
    private DataSources dataSourcesPage;
    private SchemaList schemasPage;
    private SchemaList_SchemaView schemasViewPage;
    private Users usersPage;
    private Content_Dashboard_AnalyzeInsight analyzeInsightPage;
    private Groups groupsPage;
    private Dashboards schedulerDashboardsPage;
    private Header newHeaderObject;
    private Content newContentPage;
    private Groups_Group groups_groupPage;
    private Content_Dashboard dashboardPage;
    private Content_Dashboard_SendDashboard sendDashboardPage;
    private Content_Dashboard_ScheduleDashboard scheduleDashboardPage;

    // Declaring public variables that will be shared between tests
    private String FolderNameToDelete = "Automation_Folder_FolderToBeDeleted";
    private String FolderNameToShare = "Automation_Folder_FolderToBeShared";
    private String UserToShareWithFolder = "Automation_User_UserToShareFolderWith";
    private String newDashboardName, newInsightName;
    private String NewSchemaName;
    private String ExistingSchemaNAME = "Automation_Schema_SchemaAddDataSource"; // Existing Schema to be used as
										 // predefined
    private String NewDataSourceTableName; // Table name to be used in assertion
    private String LoadDataSchema = "Automation_Schema_SchemaLoad";// Predefined Schema with data source to load data
								   // into it.
    private String ShareSchema = "Automaton_Schema_SchemaShare";
    private String UserToShareWith = "Automation_User_UserToShareSchemaWith"; // User To Share With as a predefined, he
									      // should
    // be super user or schema
    // manager to grant 'Can Edit' permission
    private String DataSourceName;
    private String initialLoadStatus;
    private String newGroupName;
    private String[] newUserData;
    private String FolderToBeRenamed = "Automation_Folder_FolderToBeRenamed";
    private String newFolderName;
    private String SchemaNameForInsight = "Automaton_Schema_SchemaShare";
    private String FolderNameToBeMoved = "Automation_Folder_FolderToBeMoved";
    private String FolderNameToMoveTo = "Automation_Folder_MovedFolder";
    private String DashboardNameToBeDeleted = "Automation_Dashboard_DashboardtoBeDeleted";
    private String DashboardToBeShared = "Automation_Dashboard_DashboardToBeSharedOrSend";
    private String ToMail = "automation_robot1@incorta.com";
    private String CcMail = "automation_robot2@incorta.com";
    private String BccMail = "automation_robot3@incorta.com";
    private String newScheduledSendDashboardJobName;
    private String DashboardNameToBeCopied = "Automation_Dashboard_ToBeCopied";
    private String DashboardNameToMove = "Automation_Dashboard_Move";
    private String DashboardNameToRename = "Automation_Dashboard_Rename";

    // Prerequisites, Schema Manager user + Connection credentials to data source
    @Test(priority = 14, description = "TC C60535_1 - Schema Manager Permissions")
    @Description("When I log in with Schema manager user and click add data source, then a new data source will be created.")
    @Severity(SeverityLevel.CRITICAL)
    public void SchemaManager_Permissions_AddDataSource() {

	logInWithUserAndAssertContentSection("Data3");

	dataSourcesPage = new DataSources(driver);
	dataSourcesPage.Navigate_toURL();
	dataSourcesPage.Assert_dataSourcesTabIsSelected();

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.click_add();

	DataSourceName = dataSourcesPage.AddDataSource("MySQL");
	dataSourcesPage.Assert_dataSourceCreationWasSuccessful(DataSourceName);
	dataSourcesPage.Assert_nameIsDisplayed(DataSourceName);
    }

    // Prerequisites, Schema Manager user + Test data defined for creating new
    // schema
    @Test(priority = 2, description = "TC C60535_2 - Schema Manager Permissions")
    @Description("When I log in with Schema manager user and click add to createa new schema,then a newschema will be created")
    @Severity(SeverityLevel.CRITICAL)
    public void SchemaManager_Permissions_CreateSchema() {

	logInWithUserAndAssertContentSection("Data3");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	schemasPage = new SchemaList(driver);
	schemasPage.Navigate_toURL();

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.click_add();
	subHeaderObject.Select_fromDropdownMenu("Create Schema");

	NewSchemaName = schemasPage.createNewSchema();

	schemasPage.Navigate_toURL();
	schemasPage.Assert_schemaNameIsDisplayed(NewSchemaName);
    }

    // Prerequisites, Schema Manager User + Data Source available + New schema
    // available [ExistingSchema]
    @Test(priority = 3, description = "TC C60535_3 - Schema Manager Permissions")
    @Description("Given I've a data source and schema, When I log in with Schema manager user, and add data source to schema using schema wizard, Then data source will be added to the schema.")
    @Severity(SeverityLevel.CRITICAL)
    public void SchemaManager_Permissions_AddDataSourceToSchema() {
	logInWithUserAndAssertContentSection("Data3");

	// Create new data source as a prerequisite.

	dataSourcesPage = new DataSources(driver);
	dataSourcesPage.Navigate_toURL();
	dataSourcesPage.Assert_dataSourcesTabIsSelected();

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.click_add();

	dataSourcesPage = new DataSources(driver);
	DataSourceName = dataSourcesPage.AddDataSource("MySQL");
	dataSourcesPage.Assert_dataSourceCreationWasSuccessful(DataSourceName);
	dataSourcesPage.Assert_nameIsDisplayed(DataSourceName);

	// Add Data Source to schema
	schemasPage = new SchemaList(driver);
	schemasPage.Navigate_toURL();
	schemasPage.click_schemaName(ExistingSchemaNAME);

	subHeaderObject.click_add();
	subHeaderObject.Select_fromDropdownMenu("Schema Wizard");

	schemasViewPage = new SchemaList_SchemaView(driver);
	schemasViewPage.Wizard_AddDataSourceTable(DataSourceName, true, "MySQL",
		testDataReader.getCellData("DatabaseTableName"));

	NewDataSourceTableName = schemasViewPage.GetNewestTableName();
	schemasViewPage.switchToDefaultContent();
	schemasViewPage.Assert_tableNameIsDisplayed(NewDataSourceTableName);
    }

    // Prerequisites, Schema Manager User + Data Source available + Schema available
    // + Data source is added to the schema
    @Test(priority = 4, description = "TC C60535_4 - Schema Manager Permissions")
    @Description("Given I logged in with Schema manager user, and I've added a data source with schema, When I click load data from the schema.Then Data is loaded successfully.")
    @Severity(SeverityLevel.CRITICAL)
    public void SchemaManager_Permissions_LoadData() {

	logInWithUserAndAssertContentSection("Data3");

	schemasPage = new SchemaList(driver);
	schemasPage.Navigate_toURL();
	schemasPage.click_schemaName(LoadDataSchema);

	schemasViewPage = new SchemaList_SchemaView(driver);
	initialLoadStatus = schemasViewPage.GetLastLoadStatus();

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.Click_load();
	subHeaderObject.Hover_overDropdownMenu("Load now");
	subHeaderObject.Select_fromDropdownMenu("Full");

	schemasViewPage.confirmLoadingData();
	schemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
	schemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);
    }

    // Prerequisites, Schema Manager User + Data Source Available + Schema Available
    @Test(priority = 5, description = "TC C60535_5 - Schema Manager Permissions")
    @Description("Given I log in with Schema manager user, and data source added to the schema, and I click on"
	    + "schema settings, and share schema with any user to[To Edit]. Then schema is shared successfully with Edit feature.")
    @Severity(SeverityLevel.CRITICAL)
    public void SchemaManager_Permissions_ShareSchemaToEdit() {

	logInWithUserAndAssertContentSection("Data3");

	schemasPage = new SchemaList(driver);
	schemasPage.Navigate_toURL();
	schemasPage.click_schemaName(ShareSchema);

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.Click_Settings();

	schemasViewPage = new SchemaList_SchemaView(driver);
	schemasViewPage.Click_Sharing_Tab();
	schemasViewPage.Click_AddButton_SharingTab();
	schemasViewPage.Schema_Sharing_SearchAndSelectUsers(UserToShareWith);// User To Share With as a predefined,
	// he should be super user or schema
	// manager to grant 'Can Edit' permission
	schemasViewPage.Schema_Sharing_ClickOnUserPermission("Can Edit");
	schemasViewPage.Click_Save_Button();
	schemasViewPage.Assertion_UserPermission(UserToShareWith, "Can Edit");
    }

    @Test(priority = 6, description = "C60533 - Individual Analyzer")
    @Description("Given I login With Individual Analyzer , When I CreateDashboard,Then Dashboards Created sucessfully,And I can't share, schedule or send it")
    @Severity(SeverityLevel.CRITICAL)
    public void IndividualAnalyzerRole() {

	/*
	 * prerequisite to this test case you need to create a user and add the user to
	 * Individual analyzer role to him by adding the user to the group.
	 *
	 * Steps: - user created with name: Individual Analyzer and password:
	 * IndividualAnalyzer --> user data added to excel sheet - user added to
	 * Individual Group that has an Individual Analyzer role assigned to that group
	 * Share Schema with that user in my case "HR"
	 *
	 * -----------------------------------------------------------------------------
	 * ---
	 *
	 * -----------------------------------------------------------------------------
	 * test case steps: 1- logout from the previous user. 2- login with the
	 * pre-created user. 3- navigate to content section. 4- create dashboard, add
	 * table from the shared schema, select Aggregated table insight type. 5- Assert
	 * Export icon(this icon let user to share/send/schedule) not displayed in
	 * dashboard page. 6- Assert that dashboard and insight name are correct.
	 * 7-assert that share icon in dashboard settings is dimmed.
	 */

	logInWithUserAndAssertContentSection("Data2");
	// Navigate to Content page and create dashboard
	newContentPage = new Content(driver);
	newContentPage.navigate_toURL();

	subHeaderObject = new OldUI_SubHeader(driver);

	newContentPage = new Content(driver);
	String NewDashBoradName = newContentPage.addNewCatalogItem("dashboard");
	newContentPage.navigate_toURL();
	subHeaderObject.SearchForContentAndOpenResult_content(NewDashBoradName);

	analyzeInsightPage = new Content_Dashboard_AnalyzeInsight(driver);
	analyzeInsightPage.clickOn_addInsight_button();
	analyzeInsightPage.selectVisualization("Aggregated");

	analyzeInsightPage.addTableorSchemaToInsight("HR");

	analyzeInsightPage.addColumnToInsight("DEPARTMENTS", "Mgr. First Name");
	analyzeInsightPage.addColumnToInsight("EMPLOYEES", "Employee Salary");

	newInsightName = analyzeInsightPage.setInsightName();
	subHeaderObject.Click_done();

	newContentPage.navigate_toURL();
	subHeaderObject.SearchForContentAndOpenResult_content(NewDashBoradName);

	// Assert that Export icon(this icon let user to share/send/schedule) is not
	// displayed in dashboard page
	subHeaderObject.assertExportIconIsNotDisplayed();

	// assert that dashboard and insight name are correct
	dashboardPage = new Content_Dashboard(driver);
	dashboardPage.assert_dashboardName_isCorrect(NewDashBoradName);
	dashboardPage.assert_insightName_isCorrect(newInsightName);

	newContentPage.navigate_toURL();
	// assert that share icon in dashboard settings is dimmed
	newContentPage.click_dashboardFolder_properties_fromCardView(NewDashBoradName);
	// Waiting Nouran to check if the button should be hidden or it will just send
	// an error that user not authorized to share.
	newContentPage.assert_dashboardProperties_manageDashboardButtons_notExist("Share Access");
    }

    @Test(priority = 7, description = "C60535 - User Manager")
    @Description("Given I login With User Manager, When I Create new User andGroup,Then userand groupcreated successfullyand ICan adduser androle to that group")
    @Severity(SeverityLevel.CRITICAL)
    public void userManagerRole() {

	/*
	 * prerequisite to this test case you need to create a user and add the user to
	 * User Manager role by adding the user to the group.
	 *
	 * Steps: - user created with name: User_Manager and password: UserManager -->
	 * user data added to excel sheet - user added to User Manager Group that has a
	 * User Manager role assigned to that group
	 *
	 * -----------------------------------------------------------------------------
	 *
	 *
	 * -----------------------------------------------------------------------------
	 * test case steps: 1- Login with the pre-created user "User Manager". 2- Create
	 * new user. 3- Create new group. 4- Assign user to the group.
	 *
	 */
	logInWithUserAndAssertContentSection("Data5");
	// Create New User
	usersPage = new Users(driver);
	usersPage.Navigate_toURL();

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.click_add();

	newUserData = usersPage.AddNewUser();
	usersPage.Assert_nameIsDisplayed(newUserData[2]);

	// Create New Group

	groupsPage = new Groups(driver);
	groupsPage.Navigate_toURL();

	subHeaderObject.click_add();

	newGroupName = groupsPage.AddNewGroup();
	groupsPage.Navigate_toURL();
	subHeaderObject.SearchForContentAndAssertResultIsDisplayed_security(newGroupName);

	// Add roles to group
	subHeaderObject.SearchForContentAndOpenResult_security(newGroupName);

	groups_groupPage = new Groups_Group(driver);
	String AddedRoles[] = new String[] { testDataReader.getCellData("GroupRoles", "Data1"),
		testDataReader.getCellData("GroupRoles", "Data2") };
	groups_groupPage.AddRoles(AddedRoles);
	groups_groupPage.Assert_rolesAreDisplayed(AddedRoles);

	// Add user to The new created Group

	groups_groupPage.AddUsers(new String[] { newUserData[2] });
	groups_groupPage.Assert_usersAreDisplayed(new String[] { newUserData[2] });
    }

    @Test(priority = 8, description = "C60530 - Super Role")
    @Description("Given I login With Super User, When I Create or manage (Users,Groups,Roles,Dashboards),Then I Can manage all of them sucessfully")
    @Severity(SeverityLevel.CRITICAL)
    public void superUserRole() {

	logInWithUserAndAssertContentSection("Data8");
	// Create New User
	usersPage = new Users(driver);
	usersPage.Navigate_toURL();

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.click_add();

	newUserData = usersPage.AddNewUser();
	usersPage.Assert_nameIsDisplayed(newUserData[2]);

	// Create New Group

	groupsPage = new Groups(driver);
	groupsPage.Navigate_toURL();

	subHeaderObject.click_add();

	newGroupName = groupsPage.AddNewGroup();
	groupsPage.Navigate_toURL();
	subHeaderObject.SearchForContentAndAssertResultIsDisplayed_security(newGroupName);

	// Add roles to group
	subHeaderObject.SearchForContentAndOpenResult_security(newGroupName);

	groups_groupPage = new Groups_Group(driver);
	String AddedRoles[] = new String[] { testDataReader.getCellData("GroupRoles", "Data1"),
		testDataReader.getCellData("GroupRoles", "Data2") };
	groups_groupPage.AddRoles(AddedRoles);
	groups_groupPage.Assert_rolesAreDisplayed(AddedRoles);

	// Add the new created user to The new created Group

	groups_groupPage.AddUsers(new String[] { newUserData[2] });
	groups_groupPage.Assert_usersAreDisplayed(new String[] { newUserData[2] });

	// Navigate to Content page and create dashboard
	newContentPage = new Content(driver);
	newContentPage.navigate_toURL();
	String NewDashBoradName = newContentPage.addNewCatalogItem("dashboard");
	newContentPage.navigate_toURL();
	subHeaderObject.SearchForContentAndOpenResult_content(NewDashBoradName);

	analyzeInsightPage = new Content_Dashboard_AnalyzeInsight(driver);
	analyzeInsightPage.clickOn_addInsight_button();
	analyzeInsightPage.selectVisualization("Aggregated");

	analyzeInsightPage.addTableorSchemaToInsight("HR");

	analyzeInsightPage.addColumnToInsight("DEPARTMENTS", "Mgr. First Name");
	analyzeInsightPage.addColumnToInsight("EMPLOYEES", "Employee Salary");

	newInsightName = analyzeInsightPage.setInsightName();
	subHeaderObject.Click_done();

	newContentPage.navigate_toURL();
	subHeaderObject.SearchForContentAndOpenResult_content(NewDashBoradName);

	// assert that dashboard and insight name are correct
	dashboardPage = new Content_Dashboard(driver);
	dashboardPage.assert_dashboardName_isCorrect(NewDashBoradName);
	dashboardPage.assert_insightName_isCorrect(newInsightName);

	newContentPage.navigate_toURL();

	// assert that share icon in dashboard settings is active
	newContentPage.click_dashboardFolder_properties_fromCardView(newDashboardName);

	dashboardPage.assert_shared_button_active();
    }

    // Prerequisites, Analyzer user
    @Test(priority = 9, description = "TC C60531_1 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on create new folder. Then new folder will be created successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_CreateFolder() {
	logInWithUserAndAssertContentSection("Data1");

	newContentPage = new Content(driver);
	String NewFolderName = newContentPage.addNewCatalogItem("folder");
	newContentPage.navigate_toURL();

	newContentPage.Assert_DashboardExist(NewFolderName);
    }

    // Prerequisites, Analyzer user + Folder to be deleted
    @Test(priority = 10, description = "TC C60531_2 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click delete. Then folder will be deleted successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_DeleteFolder() {

	logInWithUserAndAssertContentSection("Data1");

	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(FolderNameToDelete);
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");
	newContentPage.dashboard_folder_properties_delete_confirmationButtons("Delete");
	newContentPage.assert_dashboard_folder_notExist(FolderNameToDelete);
    }

    // Prerequisites, Analyzer user + Folder to be shared + User to share with
    @Test(priority = 11, description = "TC C60531_3 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click share and select any person to share with. Then folder will be shared successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_ShareFolder() {

	logInWithUserAndAssertContentSection("Data1");

	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(FolderNameToShare);
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Share Access");
	newContentPage.folderProperties_shareAccess_typeAndSelectInSearchField(UserToShareWithFolder, "Can Edit");
	newContentPage.assert_folder_dashboard_sharedSuccessfully(UserToShareWithFolder, "Can Edit");
    }

    // Prerequisites, Analyzer user + Folder name to update + New Folder NAME after
    // updating
    @Test(priority = 12, description = "TC C60531_4 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and update folder name. Then folder will be updated successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_UpdateFolder() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);

	newContentPage.click_dashboardFolder_properties_fromCardView(FolderToBeRenamed);

	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newFolderName = newContentPage.FolderProperties_Rename();

	newContentPage.Dashboard_Rename_ClickRenameButton();

	newContentPage.Assert_DashboardExist(newFolderName);

    }

    // Prerequisites, Analyzer user + Folder name to move + Folder name to move to
    @Test(priority = 13, description = "TC C60531_5 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options, move folder and select folder location. Then folder is moved successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_MoveFolder() {

	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(FolderNameToBeMoved);
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Move to...");

	newContentPage.Click_FolderProperties_MoveFolder_FolderNameToMoveTo(FolderNameToMoveTo);
	newContentPage.dashboard_move_clickMoveButton();

	newContentPage.assert_dashboard_folder_notExist(FolderNameToBeMoved);
	newContentPage.click_on_folder_dashboard(FolderNameToMoveTo);
	newContentPage.Assert_DashboardExist(FolderNameToBeMoved);

    }

    // Prerequisites, Analyzer user
    @Test(priority = 1, description = "TC C60531_6 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, navigate to content tab, click on create new dashboard and create a new insight. Then dashboard and insight are created successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_CreateDashboardAndInsight() {

	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	subHeaderObject = new OldUI_SubHeader(driver);

	newContentPage = new Content(driver);
	String NewDashBoradName = newContentPage.addNewCatalogItem("dashboard");
	newContentPage.navigate_toURL();
	subHeaderObject.SearchForContentAndOpenResult_content(NewDashBoradName);

	analyzeInsightPage = new Content_Dashboard_AnalyzeInsight(driver);
	analyzeInsightPage.clickOn_addInsight_button();
	analyzeInsightPage.selectVisualization("Aggregated");
	analyzeInsightPage.addTableorSchemaToInsight(SchemaNameForInsight);
	analyzeInsightPage.addColumnToInsight("sales", "Revenue");
	analyzeInsightPage.addColumnToInsight("sales", "Quarter");
	newInsightName = analyzeInsightPage.setInsightName();

	subHeaderObject.Click_done();

	dashboardPage = new Content_Dashboard(driver);
	dashboardPage.assert_dashboardName_isCorrect(NewDashBoradName);
	dashboardPage.assert_insightName_isCorrect(newInsightName);

	newContentPage.navigate_toURL();
	newContentPage.Assert_DashboardExist(NewDashBoradName);
    }

    @Test(priority = 15, description = "TC C60531_8 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and I click on any dashboard, and I click on export and I click on send. Then dashboard will be sent via mail successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_ShareDashboardViaMail_Direct() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.SearchForContentAndOpenResult_content(DashboardToBeShared);

	sendDashboardPage = new Content_Dashboard_SendDashboard(driver);
	dashboardPage = new Content_Dashboard(driver);
	dashboardPage.click_shareOptions_sendNow_button();
	sendDashboardPage.sendDashboard_addFields("", "This is body area", "HTML", ToMail, CcMail, BccMail);
	sendDashboardPage.sendScheduleDashboard_click_send_schedule_buttons("Send");

	newHeaderObject.assert_splashNotificationMessage_equalsExpected("Dashboard Sent");
	newHeaderObject.assert_splashNotificationDescription_equalsExpected(
		"You've successfully sent " + DashboardToBeShared + " via Email.");
	// Need to check that mail is sent successfully
    }

    // Under construction
    @Test(priority = 16, description = "TC C60531_9 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and I click on any dashboard, and I click on export and I click on schedule. Then dashboard will be shared via scheduled job successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_ShareDashboardViaMail_Scheduler() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.SearchForContentAndOpenResult_content(DashboardToBeShared);

	dashboardPage = new Content_Dashboard(driver);
	dashboardPage.click_shareOptions_scheduleDelivery_button();

	scheduleDashboardPage = new Content_Dashboard_ScheduleDashboard(driver);
	newScheduledSendDashboardJobName = scheduleDashboardPage.scheduleDashboard_addFields(DashboardToBeShared,
		"LOLO", "This is the body area", "HTML", ToMail, CcMail, BccMail, "Minute(s)", "10", "11", "11", "PM",
		"GMT+02:00", "3", "Saturday", "20", "3rd", "2018-12-12");

	scheduleDashboardPage.sendScheduleDashboard_click_send_schedule_buttons("Schedule");
	// GMT±00:00

	newHeaderObject.assert_splashNotificationMessage_equalsExpected("Delivery Scheduled");
	newHeaderObject.assert_splashNotificationDescription_equalsExpected("successfully scheduled");

	schedulerDashboardsPage = new Dashboards(driver);
	schedulerDashboardsPage.Navigate_toURL();

	schedulerDashboardsPage.Assert_jobNameIsDisplayed(DashboardToBeShared, newScheduledSendDashboardJobName);
	schedulerDashboardsPage.Assert_jobStatusIsCorrect(newScheduledSendDashboardJobName, "Active");
    }

    // Prerequisites, Analyzer user + Dashboard to be deleted
    @Test(priority = 17, description = "TC C60531_7 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click delete. Then dashboard will be deleted successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_DeleteDashboard() {

	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);

	newContentPage.click_dashboardFolder_properties_fromCardView(DashboardNameToBeDeleted);

	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");

	newContentPage.dashboard_folder_properties_delete_confirmationButtons("Delete");

	newContentPage.assert_dashboard_folder_notExist(DashboardNameToBeDeleted);
    }

    // Prerequisitrs, Analyzer user + Dashboard to share + User to share with
    @Test(priority = 18, description = "TC C60531_10 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click sharing and select any user to 'Can Edit' and click save. Then dashboard will be shared successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_ShareDashboard() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);

	newContentPage.click_dashboardFolder_properties_fromCardView(DashboardToBeShared);
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Share Access");

	newContentPage.folderProperties_shareAccess_typeAndSelectInSearchField(UserToShareWithFolder, "Can Edit");
	newContentPage.assert_folder_dashboard_sharedSuccessfully(UserToShareWithFolder, "Can Edit");

    }

    // Prerequisitrs, Analyzer user + Dashboard to copy + Folder to copy to
    @Test(priority = 19, description = "TC C60531_11 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click copy and select any to copy to and click on copy. Then dashboard will be copied successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_CopyDashboard() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);

	newContentPage.click_dashboardFolder_properties_fromCardView(DashboardNameToBeCopied);

	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Make a Copy");

	newContentPage.dashboard_popup_clickOnFolder(FolderNameToMoveTo);

	newContentPage.dashboardProperties_copyTo_copy_button();

	newContentPage.Assert_DashboardExist(DashboardNameToBeCopied);

	newContentPage.click_on_folder_dashboard(FolderNameToMoveTo);

	newContentPage.Assert_DashboardExist_Copied(DashboardNameToBeCopied);

    }

    // Prerequisitrs, Analyzer user + Dashboard to move + folder to move to
    @Test(priority = 20, description = "TC C60531_12 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click move and select any to move to and click on move. Then dashboard will be moved successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_MoveDashboard() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);

	newContentPage.click_dashboardFolder_properties_fromCardView(DashboardNameToMove);

	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Move to...");

	newContentPage.dashboard_popup_clickOnFolder(FolderNameToMoveTo);

	newContentPage.dashboard_move_clickMoveButton();

	newContentPage.assert_dashboard_folder_notExist(DashboardNameToMove);

	newContentPage.click_on_folder_dashboard(FolderNameToMoveTo);

	newContentPage.Assert_DashboardExist(DashboardNameToMove);
    }

    // Prerequisitrs, Analyzer user + Dashboard to rename
    @Test(priority = 21, description = "TC C60531_13 - Users permissions - Analyzer User")
    @Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click rename and type new name and click on rename. Then dashboard will be renamed successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void Analyzer_Permissions_RenameDashboard() {
	logInWithUserAndAssertContentSection("Data1");

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	newContentPage = new Content(driver);

	newContentPage.click_dashboardFolder_properties_fromCardView(DashboardNameToRename);

	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");// can't detect xpath of 'rename'
										  // option

	newDashboardName = newContentPage.FolderProperties_Rename();

	newContentPage.Dashboard_Rename_ClickRenameButton();

	newContentPage.Assert_DashboardExist(newDashboardName);
    }

    private void logInWithUserAndAssertContentSection(String UserData) {
	loginPage.userLogin(testDataReader.getCellData("Tenant", UserData),
		testDataReader.getCellData("Username", UserData), testDataReader.getCellData("Password", UserData));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

    }

    @BeforeMethod
    public void beforeMethod() {
	loginPage = new Login(driver);
	loginPage.navigate_toURL();
    }

    @BeforeClass
    public void beforeClass() {
	System.setProperty("testDataFilePath",
		System.getProperty("testDataFolderPath") + "security_newUI/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	driver = BrowserFactory.getBrowser(testDataReader);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
	newHeaderObject = new Header(driver);
	newHeaderObject.expandUserMenu();
	newHeaderObject.signOut();
	logoutPage = new SignOut(driver);
	logoutPage.goToSignInPage_button();
    }
}
