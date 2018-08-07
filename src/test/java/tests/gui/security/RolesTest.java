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
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.content.AllContent_Dashboard_AnalyzeInsight;
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.scheduler.Dashboards;
import pageObjectModels.scheduler.SchemaLoads;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;
import pageObjectModels.security.Groups;
import pageObjectModels.security.Groups_Group;
import pageObjectModels.security.Users;

public class RolesTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used in the tests
	Login loginPage;
	Skeleton mainPage;
	DataSources dataSourcesPage;
	SchemaList schemasPage;
	SchemaList_SchemaView schemasViewPage;
	AllContent allContentPage;
	Users usersPage;
	AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;
	AllContent_Dashboard dashboardPage;
	Groups groupsPage;
	SchemaLoads schedulerSchemaLoadsPage;
	Dashboards schedulerDashboardsPage;

	// Declaring public variables that will be shared between tests
	String NewFolderName;
	String FolderNameToDelete = "ahmed";
	String FolderNameToShare = "Folder";
	String UserToShareWithFolder = "Abdelsalam_User";
	String newDashboardName, newInsightName;
	String NewSchemaName;
	String ExistingSchemaNAME = "Abdelsalan_Automation_Schema"; // Existing Schema to be used as predefined
	String NewDataSourceTableName; // Table name to be used in assertion
	String LoadDataSchema = "LoadDataSchema1";// Predefined Schema with data source to load data into it.
	String ShareSchema = "ShareSchema";
	String UserToShareWith = "AbdelsalamSuper"; // User To Share With as a predefined, he should be super user or schema
												// manager to grant 'Can Edit' permission
	String DataSourceName;
	String initialLoadStatus;
	String newGroupName;
	String[] newUserData;
	String NewSchemaDescription = "Created by a script in class LoginTest.java";
	String FolderToBeRenamed = "New Folder";
	String newFolderName;
	String NewDashboardName = "New Dashboard";
	String SchemaNameForInsight = "Automation_Schema_1525933948339";
	String FolderNameToBeMoved = "CopyFolder";
	String FolderNameToMoveTo = "Mohamed";
	String DashboardNameToBeDeleted = "H_A_L";
	String DashboardToBeShared = "SendDashboard";
	String ToMail = "mona.amr@incorta.com";
	String CcMail = "Ahmed.Abdelsalam@incorta.com";
	String BccMail = "Menna.maged@incorta.com";
	String newScheduledSendDashboardJobName;
	String FolderNameToBeMovedTo = "Folder";
	String DashboardNameToBeCopied = "Copy Dashboard";
	String DashboardFolderNameToBeMovedTo = "Dashboard Folder Moved";
	String DashboardNameToMove = "Move Dashboard";
	String DashboardNameToRename = "Rename Dashboard";

	// Prerequisites, Schema Manager user + Connection credentials to data source
	@Test(priority = 1, description = "TC C60535_1 - Schema Manager Permissions")
	@Description("When I log in with Schema manager user and click add data source, then a new data source will be created.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_AddDataSource() {
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
	@Test(priority = 2, description = "TC C60535_2 - Schema Manager Permissions")
	@Description("When I log in with Schema manager user and click add to createa new schema,then a newschema will be created")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_CreateSchema() {
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
	@Test(priority = 3, description = "TC C60535_3 - Schema Manager Permissions")
	@Description("Given I've a data source and schema, When I log in with Schema manager user, and add data source to schema using schema wizard, Then data source will be added to the schema.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_AddDataSourceToSchema() {
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
		schemasPage.click_schemaName(ExistingSchemaNAME);

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
	@Test(priority = 4, description = "TC C60535_4 - Schema Manager Permissions")
	@Description("Given I logged in with Schema manager user, and I've added a data source with schema, When I click load data from the schema.Then Data is loaded successfully.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_LoadData() {
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("schemaItem");

		schemasPage = new SchemaList(driver);
		schemasPage.click_schemaName(LoadDataSchema);

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
	@Test(priority = 5, description = "TC C60535_5 - Schema Manager Permissions")
	@Description("Given I log in with Schema manager user, and data source added to the schema, and I click on"
			+ "schema settings, and share schema with any user to[To Edit]. Then schema is shared successfully with Edit feature.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchemaManager_Permissions_ShareSchemaToEdit() {
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data3"),
				testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("Password", "Data3"));

		mainPage = new Skeleton(driver);
		mainPage.Click_Element_Sidemenu("schemaItem");

		schemasPage = new SchemaList(driver);
		schemasPage.click_schemaName(ShareSchema);

		mainPage.Click_Settings();

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
	@Description("Given I login With Individual Analyzer , When I CreateDashboard,Then Dashboardis Created sucessfully,And I can'tshare,schedular or send it")

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

		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data2"),
				testDataReader.getCellData("Username", "Data2"), testDataReader.getCellData("Password", "Data2"));

		// Navigate to Content page and create dashboard
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Dashboard");

		newDashboardName = allContentPage.setNewDashboardName();

		analyzeInsightPage = new AllContent_Dashboard_AnalyzeInsight(driver);
		analyzeInsightPage.addTableorSchemaToInsight("HR");

		analyzeInsightPage.addColumnToInsight("DEPARTMENTS", "Mgr. First Name");
		analyzeInsightPage.addColumnToInsight("EMPLOYEES", "Employee Salary");

		mainPage.Click_ChooseVisualization();
		analyzeInsightPage.selectVisualization("Aggregated");

		newInsightName = analyzeInsightPage.setInsightName();
		mainPage.Click_done();

		allContentPage.Navigate_toURL();
		mainPage.SearchForContentAndOpenResult(newDashboardName);

		dashboardPage = new AllContent_Dashboard(driver);
		// Assert that Export icon(this icon let user to share/send/schedule) is not
		// displayed in dashboard page
		mainPage.assertExportIconIsNotDisplayed(); //

		// assert that dashboard and insight name are correct
		dashboardPage.Assert_dashboardName(newDashboardName);
		dashboardPage.Assert_insightName(newInsightName);

		allContentPage.Navigate_toURL();
		// assert that share icon in dashboard settings is dimmed
		allContentPage.selectDashboardMenuButton(newDashboardName);
		dashboardPage.Assert_shared_button_dimmed();
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
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data5"),
				testDataReader.getCellData("Username", "Data5"), testDataReader.getCellData("Password", "Data5"));

		// Create New User
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();

		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);

		// Create New Group

		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();

		mainPage.Click_add();

		newGroupName = groupsPage.AddNewGroup();
		groupsPage.Navigate_toURL();
		mainPage.SearchForContentAndAssertResultIsDisplayed(newGroupName);

		// Add roles to group
		mainPage.SearchForContentAndOpenResult(newGroupName);

		Groups_Group groups_groupPage = new Groups_Group(driver);
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

		/*
		 * prerequisite to this test case you need to create a user and assign Super
		 * role to him by adding the user to the group.
		 * 
		 * Steps: - user created with name: superuser and password: superuser --> user
		 * data added to excel sheet - user added to Super Group that has a SuperRole
		 * role assigned to that group
		 * -----------------------------------------------------------------------------
		 * -----------------------------------------------------------------------------
		 * 
		 * /* TCs order.
		 */
		/* 1- login with the pre-created user "Superuser". */
		/* 2- create new user. 3- create new group. */
		/* 4- assign role to group */
		/* 5- add user to the group. */
		/* 6- navigate to content section. */
		/*
		 * 7- create dashboard, add table from the shared schema, select Aggregated
		 * table insight type. 8- Assert Export icon(this icon let user to
		 * share/send/schedule) is displayed in dashboard page.
		 */
		/* 9- Assert that dashboard and insight name are correct . */
		/* 10- assert that share icon in dashboard setting is active. */

		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data8"),
				testDataReader.getCellData("Username", "Data8"), testDataReader.getCellData("Password", "Data8"));

		// Create New User
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();

		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);

		// Create New Group

		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();

		mainPage.Click_add();

		newGroupName = groupsPage.AddNewGroup();
		groupsPage.Navigate_toURL();
		mainPage.SearchForContentAndAssertResultIsDisplayed(newGroupName);

		// Add roles to group
		mainPage.SearchForContentAndOpenResult(newGroupName);

		Groups_Group groups_groupPage = new Groups_Group(driver);
		String AddedRoles[] = new String[] { testDataReader.getCellData("GroupRoles", "Data1"),
				testDataReader.getCellData("GroupRoles", "Data2") };
		groups_groupPage.AddRoles(AddedRoles);
		groups_groupPage.Assert_rolesAreDisplayed(AddedRoles);

		// Add the new created user to The new created Group

		groups_groupPage.AddUsers(new String[] { newUserData[2] });
		groups_groupPage.Assert_usersAreDisplayed(new String[] { newUserData[2] });

		// Navigate to Content page and create dashboard

		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();

		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Dashboard");

		newDashboardName = allContentPage.setNewDashboardName();

		analyzeInsightPage = new AllContent_Dashboard_AnalyzeInsight(driver);
		analyzeInsightPage.addTableorSchemaToInsight("HR");

		analyzeInsightPage.addColumnToInsight("DEPARTMENTS", "Mgr. First Name");
		analyzeInsightPage.addColumnToInsight("EMPLOYEES", "Employee Salary");

		mainPage.Click_ChooseVisualization();
		analyzeInsightPage.selectVisualization("Aggregated");

		newInsightName = analyzeInsightPage.setInsightName();
		mainPage.Click_done();

		allContentPage.Navigate_toURL();
		mainPage.SearchForContentAndOpenResult(newDashboardName);

		mainPage.assertExportIconIsDisplayed();

		dashboardPage = new AllContent_Dashboard(driver);

		// assert that dashboard and insight name are correct
		dashboardPage.Assert_dashboardName(newDashboardName);
		dashboardPage.Assert_insightName(newInsightName);

		allContentPage.Navigate_toURL();
		// assert that share icon in dashboard settings is active
		allContentPage.selectDashboardMenuButton(newDashboardName);
		dashboardPage.Assert_shared_button_Active();
	}

	// Prerequisites, Analyzer user
	@Test(priority = 9, description = "TC C60531_1 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and navigate to content tab, and click on create new folder. Then new folder will be created successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_CreateFolder() {
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Folder");
		NewFolderName = allContentPage.SetNewFolderName();
		allContentPage.Assert_folder_Dashboard_IsDisplayed(NewFolderName);
	}

	// Prerequisites, Analyzer user + Folder to be deleted
	@Test(priority = 10, description = "TC C60531_2 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click delete. Then folder will be deleted successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_DeleteFolder() {
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();

		allContentPage.Click_Folder_Dashboard_Properties(FolderNameToDelete);
		allContentPage.Click_FolderProperties_ManageFolderButtons("deleteFolder");
		allContentPage.Click_Folder_Dashboard_Properties_ManageFolderButtons_ConfirmationButtonsForDelete("Delete");
		allContentPage.Assert_folder_Dashboard_IsNotDisplayed(FolderNameToDelete);
	}

	// Prerequisites, Analyzer user + Folder to be shared + User to share with
	@Test(priority = 11, description = "TC C60531_3 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and click share and select any person to share with. Then folder will be shared successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_ShareFolder() {
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();

		allContentPage.Click_Folder_Dashboard_Properties(FolderNameToShare);
		allContentPage.Click_FolderProperties_ManageFolderButtons("shareFolder");

		allContentPage.Folder_Sharing_SearchAndSelectUsers(UserToShareWithFolder);

		schemasViewPage = new SchemaList_SchemaView(driver);
		schemasViewPage.Schema_Sharing_ClickOnUserPermission("Can Edit");
		schemasViewPage.Click_Save_Button();
		schemasViewPage.Assertion_UserPermission(UserToShareWithFolder, "Can Edit");
	}

	// Prerequisites, Analyzer user + Folder name to update + New Folder NAME after
	// updating
	@Test(priority = 12, description = "TC C60531_4 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options and update folder name. Then folder will be updated successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_UpdateFolder() {
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
		allContentPage.Click_Folder_Dashboard_Properties(FolderToBeRenamed);
		allContentPage.Click_FolderProperties_ManageFolderButtons("renameFolder");
		newFolderName = allContentPage.FolderProperties_RenameFolder();
		allContentPage.FolderProperties_Rename_MoveFolder_Buttons("Rename");
		allContentPage.FolderProperties_Rename_MoveFolder_Buttons("Done");
		allContentPage.Assert_folder_Dashboard_IsDisplayed(newFolderName);
	}

	// Prerequisites, Analyzer user + Folder name to move + Folder name to move to
	@Test(priority = 13, description = "TC C60531_5 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and navigate to content tab, and click on folder options, move folder and select folder location. Then folder is moved successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_MoveFolder() {
	
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
		allContentPage.Click_Folder_Dashboard_Properties(FolderNameToBeMoved);
		allContentPage.Click_FolderProperties_ManageFolderButtons("moveFolder");
		allContentPage.Click_FolderProperties_MoveFolder_FolderNameToMoveTo(FolderNameToMoveTo);
		allContentPage.FolderProperties_Rename_MoveFolder_Buttons("Move");
		allContentPage.Assert_folder_Dashboard_IsNotDisplayed(FolderNameToBeMoved);
		allContentPage.Click_FolderName(FolderNameToMoveTo);
		allContentPage.Assert_FolderExist_InsideFolder(FolderNameToBeMoved);
	}

	// Prerequisites, Analyzer user
	@Test(priority = 14, description = "TC C60531_6 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, navigate to content tab, click on create new dashboard and create a new insight. Then dashboard and insight are created successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_CreateDashboardAndInsight() {

		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
				testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Dashboard");

		String NewDashBoradName = allContentPage.setNewDashboardName();

		analyzeInsightPage = new AllContent_Dashboard_AnalyzeInsight(driver);
		analyzeInsightPage.addTableorSchemaToInsight(SchemaNameForInsight);
		analyzeInsightPage.addColumnToInsight("sales", "Revenue");
		analyzeInsightPage.addColumnToInsight("sales", "Quarter");

		mainPage.Click_ChooseVisualization();

		analyzeInsightPage.selectVisualization("Aggregated");

		mainPage.Click_done();
		mainPage.Click_Element_Sidemenu("contentItem");

		allContentPage.Assert_DashboardExist(NewDashBoradName);

		mainPage.SearchForContentAndOpenResult(NewDashBoradName);

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(NewDashBoradName);
	}
	
	@Test(priority = 15, description = "TC C60531_8 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and I click on any dashboard, and I click on export and I click on send. Then dashboard will be sent via mail successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_ShareDashboardViaMail_Direct() 
	{
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
				testDataReader.getCellData("Password", "Data1"));
				
		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
		allContentPage.Click_Dashboard(DashboardToBeShared);		
		
		mainPage = new Skeleton(driver);
		mainPage.Click_export();
		mainPage.Click_FromExportMenu("Send");
		
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.ScheduleSendDashboard_AddSubjectNameAutomated();
		dashboardPage.ScheduleSendDashboard_AddBodyNameAutomated();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(ToMail);
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Cc");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(CcMail);
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(BccMail);
		dashboardPage.Click_Send_Dashboard();
		dashboardPage.Assert_dashboardName(DashboardToBeShared);
	}

	@Test(priority = 16, description = "TC C60531_9 - Users permissions - Analyzer User")
	@Description("When I log in with Analyzer User, and I click on any dashboard, and I click on export and I click on schedule. Then dashboard will be shared via scheduled job successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Analyzer_Permissions_ShareDashboardViaMail_Scheduler() 
	{
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"), testDataReader.getCellData("Username", "Data1"), 
			testDataReader.getCellData("Password", "Data1"));
					
		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
		allContentPage.Click_Dashboard(DashboardToBeShared);		
			
		mainPage = new Skeleton(driver);
		mainPage.Click_export();
		mainPage.Click_FromExportMenu("Schedule");
			
		dashboardPage = new AllContent_Dashboard(driver);
		newScheduledSendDashboardJobName = dashboardPage.scheduleSendDashboard(testDataReader.getCellData("SchemaLoadJobDescription"),
				testDataReader.getCellData("SchemaLoadJobDate"),
				testDataReader.getCellData("SchemaLoadJobTime"), testDataReader.getCellData("SchemaLoadJobTimeZone"),
				testDataReader.getCellData("SchemaLoadJobRecurrence"), ToMail, CcMail, BccMail);
		schedulerDashboardsPage = new Dashboards(driver); 
		schedulerDashboardsPage.Navigate_toURL();
		
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(newScheduledSendDashboardJobName);
		schedulerDashboardsPage.Assert_jobStatusIsCorrect(newScheduledSendDashboardJobName, "Active");
	}

	// Prerequisites, Analyzer user + Dashboard to be deleted
		@Test(priority = 17 , description = "TC C60531_7 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click delete. Then dashboard will be deleted successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_DeleteDashboard() {
			
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
					testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();

			allContentPage.Click_Folder_Dashboard_Properties(DashboardNameToBeDeleted);

			allContentPage.Click_DashboardProperties_ManageDashboardButtons("Delete");

			allContentPage.Click_Folder_Dashboard_Properties_ManageFolderButtons_ConfirmationButtonsForDelete("Delete");

			allContentPage.Assert_folder_Dashboard_IsNotDisplayed(DashboardNameToBeDeleted);
		}
		
		//Prerequisitrs, Analyzer user + Dashboard to share + User to share with
		@Test(priority = 18 , description = "TC C60531_10 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click sharing and select any user to 'Can Edit' and click save. Then dashboard will be shared successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_ShareDashboard() {
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
					testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();

			allContentPage.Click_Folder_Dashboard_Properties(DashboardToBeShared);

			allContentPage.Click_DashboardProperties_ManageDashboardButtons("Share");

			allContentPage.Folder_Sharing_SearchAndSelectUsers(UserToShareWithFolder);

			allContentPage.Dashboard_Sharing_ClickOnUserPermission("Can Share");
			
			allContentPage.Dashboard_Sharing_Click_Save_Button();
			
			allContentPage.Assertion_Sharing_UserPermission(UserToShareWithFolder, "Can Share");		
		}

		//Prerequisitrs, Analyzer user + Dashboard to copy + Folder to copy to
		@Test(priority = 19 , description = "TC C60531_11 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click copy and select any to copy to and click on copy. Then dashboard will be copied successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_CopyDashboard() {
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
					testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();

			allContentPage.Click_Folder_Dashboard_Properties(DashboardNameToBeCopied);

			allContentPage.Click_DashboardProperties_ManageDashboardButtons("Copy");

			allContentPage.Dashboard_popup_ClickOnFolder(FolderNameToBeMovedTo);
			
			allContentPage.Dashboard_Copy_ClickCopyButton();
			
			allContentPage.FolderProperties_Rename_Move_Done_Buttons("Done");
			
			allContentPage.Assert_DashboardExist(DashboardNameToBeCopied);
			
			allContentPage.Click_FolderName(FolderNameToBeMovedTo);
			
			allContentPage.Assert_DashboardExist_Copied(DashboardNameToBeCopied);
			
		}
		
		//Prerequisitrs, Analyzer user + Dashboard to move + folder to move to
		@Test(priority = 20 , description = "TC C60531_12 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click move and select any to move to and click on move. Then dashboard will be moved successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_MoveDashboard() {
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
					testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();

			allContentPage.Click_Folder_Dashboard_Properties(DashboardNameToMove);

			allContentPage.Click_DashboardProperties_ManageDashboardButtons("Move");
			
			allContentPage.Dashboard_popup_ClickOnFolder(DashboardFolderNameToBeMovedTo);
					
			allContentPage.Dashboard_Move_ClickMoveButton();
					
			allContentPage.Assert_DashboardNotExist(DashboardNameToMove);
			
			allContentPage.Click_FolderName(DashboardFolderNameToBeMovedTo);
			
			allContentPage.Assert_DashboardExist(DashboardNameToMove);
		}
		
		//Prerequisitrs, Analyzer user + Dashboard to rename
		@Test(priority = 21 , description = "TC C60531_13 - Users permissions - Analyzer User")
		@Description("When I log in with Analyzer User, and navigate to content tab, and click on dashboard options and click rename and type new name and click on rename. Then dashboard will be renamed successfully.")
		@Severity(SeverityLevel.NORMAL)
		public void Analyzer_Permissions_RenameDashboard() {
			loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data1"),
					testDataReader.getCellData("Username", "Data1"), testDataReader.getCellData("Password", "Data1"));

			allContentPage = new AllContent(driver);
			allContentPage.Assert_allContentTabIsSelected();

			allContentPage.Click_Folder_Dashboard_Properties(DashboardNameToRename);

			allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
			
			newDashboardName = allContentPage.FolderProperties_Rename();
					
			allContentPage.Dashboard_Rename_ClickRenameButton();
			
			allContentPage.FolderProperties_Rename_Move_Done_Buttons("Done");
			
			allContentPage.Assert_DashboardExist(newDashboardName);
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
