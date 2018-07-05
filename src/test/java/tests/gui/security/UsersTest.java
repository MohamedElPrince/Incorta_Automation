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
import io.qameta.allure.Epic;
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
import pageObjectModels.security.Groups_Group;
import pageObjectModels.security.Users;

@Epic("incorta -> Security -> Users")

public class UsersTest {

	//// Prerequisites
	/**
	 * - Manually Add user with name "Test_User" till automating test data - Add
	 * profile picture image at prepare test data phase
	 */

	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;
	String TempUser = "Test_User"; // to be replaced later with dynamic one created at prepare test data phase
	String Picture = "ProfilePicture.jpg"; // to be replaced later with dynamic one created at prepare test data phase
	String newPassword;
	String newDataSourceName;
	String newSchemaName;
	String newDataSourceTableName;
	String newFolderName;
	String newDashboardName;
	String newInsightName;

	//// Page Objects
	Login loginPage;
	Users usersPage;
	Skeleton mainPage;
	Groups groupsPage;
	Groups_Group groups_groupPage;
	AllContent allContentPage;
	DataSources dataSourcesPage;
	SchemaList schemasPage;
	SchemaList_SchemaView schemasViewPage;
	AllContent_Dashboard dashboardPage;
	AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;

//	//// Test Cases
//	@Test(priority = 1, description = "C478 - Create User")
//	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
//	@Severity(SeverityLevel.CRITICAL)
//	public void createNewUser() {
//		mainPage = new Skeleton(driver);
//		mainPage.Click_add();
//
//		newUserData = usersPage.AddNewUser();
//		usersPage.Assert_nameIsDisplayed(newUserData[2]);
//	}
//
//	@Test(priority = 2, description = "C16118 - User profile picture")
//	@Description("Given I am logged in, When I navigate to the security.users page, And I change profile picture of existing user, And I save changes, Then the new profile picture sill be displayed")
//	@Severity(SeverityLevel.CRITICAL)
//	public void changeProfilePicture() {
//		usersPage.Click_name(TempUser);
//		usersPage.UploadProfilePicture(Picture);
//		usersPage.Assert_imageIsDisplayed(TempUser);
//	}
//
//	@Test(priority = 3, description = "C471 - Delete Users")
//	@Description("Given I am logged in, When I navigate to the security.users page, And I select existing user, And I delete this selected user, Then user will not be displayed in the users list.")
//	@Severity(SeverityLevel.CRITICAL)
//	public void deleteUser() {
//		usersPage.Select_nameCheckbox(TempUser); // manually created user till be automated as prerequisites
//		mainPage = new Skeleton(driver);
//		mainPage.Click_actions();
//		mainPage.Select_fromDropdownMenu("Delete selection");
//		usersPage.ConfirmUserDeletion();
//		usersPage.Assert_nameIsNotDisplayed(TempUser);
//	}
//
//	@Test(priority = 4, description = "C53308 - Testing that during the impersonation session, the UI will be always showing a message indicating that this is an impersonated session")
//	@Description("Given I am logged in with an admin account, When I navigate to the security.users page, And I click on a user (not super user), And I click on Login As User, Then a message should be displayed to state that I'm impersonating the user, And a link should be present in the users dropdown menu to take me back, And a link should be displayed in the side menu to take me back.")
//	@Severity(SeverityLevel.NORMAL)
//	public void impersonationUI() {
//	
//		String impersonationUserName = testDataReader.getCellData("ImpersonationUserName");
//		usersPage.Assert_nameIsDisplayed(impersonationUserName);
//		usersPage.Click_name(impersonationUserName);
//		usersPage.Click_impersonation();
//		usersPage.Assert_impersonationUIElementsAreDisplayed();
//	}
	
	@Test(priority = 5, description = "C647   - Testing Deleting user with option transfer ownership to another user ")
	@Description("Given I have two Super User Accounts, when I share content whith other user \"User1\", And I Delete \"User0\" and transferrer all his content to another user \"User2\", then all content ownership transfered to that user and shared content doesn`t get affected ")
	@Severity(SeverityLevel.CRITICAL)
	public void deletingUserWithOptionTransferOwnershipToAnotherUser() {

		/*
		 * prerequisite to this test case you need to create a Two users and assign
		 * Super role to them by adding the users to the group that have super user
		 * role.
		 * 
		 * Steps: - user created with name: farid and password: farid, another user
		 * name: ahmed and password: ahmed --> users data added to excel sheet - users
		 * added to Super Group that has a SuperRole role assigned to that group
		 * -----------------------------------------------------------------------------
		 * -----------------------------------------------------------------------------
		 * 1- create new user 2- Add the new created user to Super Group 3- logout and
		 * login using the new account created user 4- Create new data source 5- Create
		 * Schema new schema using data source 6- Load the created schema "Full load" 7-
		 * Create Folder 8- Create Dashboard and add insight 9- assert that share icon
		 * in dashboard settings is active 10-Share Folder with another user 11- share
		 * dashboard with another user 12- Switch to another admin account 13- Delete
		 * User and Transfer Ownership to another super user 14- Switch to another admin
		 * account user in data5 and check the transfered content. 15- Check data source
		 * owner after transfer 16- Check schema owner after transfer 17- Check
		 * Dashboard owner after transfer 18- Check Folder owner after transfer
		 */

		// Create New User
		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);

		// Add the new created user to Super Group
		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();

		mainPage.SearchForContentAndOpenResult("Supers");
		groups_groupPage = new Groups_Group(driver);
		groups_groupPage.AddUsers(new String[] { newUserData[2] });
		groups_groupPage.Assert_usersAreDisplayed(new String[] { newUserData[2] });

		// logout and login using the new account created
		mainPage.Select_fromUserMenu("Logout");
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), newUserData[0], newUserData[1]);

		// Actions for first time login
		newPassword = "Automation";
		loginPage.FirstTimeLogin(newUserData[1], newPassword, newPassword);

		allContentPage = new AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();

		// Create new data source
		dataSourcesPage = new DataSources(driver);
		dataSourcesPage.Navigate_toURL();
		dataSourcesPage.Assert_dataSourcesTabIsSelected();

		mainPage.Click_add();

		newDataSourceName = dataSourcesPage.AddDataSource("MySQL");
		dataSourcesPage.Assert_dataSourceCreationWasSuccessful(newDataSourceName);
		dataSourcesPage.Assert_nameIsDisplayed(newDataSourceName);

		// Create Schema

	    schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Schema");

		newSchemaName = schemasPage.createNewSchema();

		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
		schemasPage.Click_schemaName(newSchemaName);

		schemasViewPage = new SchemaList_SchemaView(driver);
		schemasViewPage.Assert_schemaNameIsDisplayed(newSchemaName);

		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Schema Wizard");

		schemasViewPage.Wizard_AddDataSourceTable(newDataSourceName, true, "MySQL",
				testDataReader.getCellData("DatabaseTableName")); //need to check if condition after this function call
																  // "Assert_wizardWelcomeTextIsDisplayed()"
		newDataSourceTableName = schemasViewPage.GetNewestTableName();
		schemasViewPage.Assert_tableNameIsDisplayed(newDataSourceTableName);

		// Full Load the created schema

		String initialLoadStatus = schemasViewPage.GetLastLoadStatus();

		mainPage.Click_load();
		mainPage.Hover_overDropdownMenu("Load now");
		mainPage.Select_fromDropdownMenu("Full");
		schemasViewPage.confirmLoadingData();

		schemasViewPage.waitForDataToBeLoaded(initialLoadStatus);
		schemasViewPage.Assert_lastLoadStatusIsUpdated(initialLoadStatus);

		// Create Folder
		dashboardPage = new AllContent_Dashboard(driver);
		allContentPage.Navigate_toURL();
		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Folder");
		newFolderName = allContentPage.SetNewFolderName();

		allContentPage.selectContentOptionButton(newFolderName);
		dashboardPage.Assert_shared_button_Active();
		// share Folder with another user with Can View
		dashboardPage.selectShareButton();
		dashboardPage.selectUsertoShareFromList(testDataReader.getCellData("Username", "Data9"));

		// Create Dashboard and add insight
		allContentPage.Navigate_toURL();

		mainPage.Click_add();
		mainPage.Select_fromDropdownMenu("Create Dashboard");

		newDashboardName = allContentPage.setNewDashboardName();

		analyzeInsightPage = new AllContent_Dashboard_AnalyzeInsight(driver);
		analyzeInsightPage.addTableorSchemaToInsight(newSchemaName);
		analyzeInsightPage.addColumnToInsight(newDataSourceTableName, "Quarter");
		analyzeInsightPage.addColumnToInsight(newDataSourceTableName, "Units");

		mainPage.Click_ChooseVisualization();
		analyzeInsightPage.selectVisualization("Aggregated");

		newInsightName = analyzeInsightPage.setInsightName();
		mainPage.Click_done();

		allContentPage.Navigate_toURL();
		mainPage.SearchForContentAndOpenResult(newDashboardName);

		dashboardPage.Assert_dashboardName(newDashboardName);
		dashboardPage.Assert_insightName(newInsightName);

		allContentPage.Navigate_toURL();

		// assert that share icon in dashboard settings is active
		allContentPage.selectContentOptionButton(newDashboardName);
		dashboardPage.Assert_shared_button_Active();

		// share dashboard with another user with Can View
		dashboardPage.selectShareButton();
		dashboardPage.selectUsertoShareFromList(testDataReader.getCellData("Username", "Data9"));

		// Switch to another admin account
		mainPage.Select_fromUserMenu("Logout");
		loginPage.Navigate_toURL();

		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data6"),
				testDataReader.getCellData("Username", "Data6"), testDataReader.getCellData("Password", "Data6"));

		// Delete User and Transfer Ownership to another user
		usersPage.Navigate_toURL();
		usersPage.Select_nameCheckbox(newUserData[2]);

		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");
		// Transfer ownership to user in data5
		usersPage.ConfirmUserDeletionAndTransferOwnershipToAnother(testDataReader.getCellData("Username", "Data8"));
		usersPage.Assert_nameIsNotDisplayed(newUserData[2]);

		// Switch to another admin account user in data5 and check the transfered
		// content.
		mainPage.Select_fromUserMenu("Logout");
		loginPage.Navigate_toURL();

		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data8"),
				testDataReader.getCellData("Username", "Data8"), testDataReader.getCellData("Password", "Data8"));

		String Owner = testDataReader.getCellData("Username", "Data8");

		// check data source owner after transfer
		dataSourcesPage.Navigate_toURL();
		dataSourcesPage.Assert_nameIsDisplayed(newDataSourceName);
		dataSourcesPage.Assert_DSnameAndOwnerIsDisplayed(newDataSourceName, Owner);

		// check schema owner after transfer
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
		schemasPage.Assert_schemaNameIsDisplayedAndItsOwnerName(newSchemaName, Owner);

		// Check Dashboard owner after transfer
		allContentPage.Navigate_toURL();
		mainPage.SearchForContentAndAssertResultIsDisplayed(newDashboardName);
		allContentPage.selectContentOptionButton(newDashboardName);
		dashboardPage.Assert_Content_UserPermission(Owner, "Owner");
		dashboardPage.Assert_Content_UserPermission(testDataReader.getCellData("Username", "Data9"), "Can View"); 
		// do we need to add click on Done after assert the permission for the user,will result an issue if i use this method to check more than one user

		// Check Folder owner after transfer
		allContentPage.Navigate_toURL();
		allContentPage.selectContentOptionButton(newFolderName);
		dashboardPage.Assert_Content_UserPermission(Owner, "Owner");
		dashboardPage.Assert_Content_UserPermission(testDataReader.getCellData("Username", "Data9"), "Can View");
	}

	//// Testng Annotations
	
	@BeforeMethod
	public void beforeMethod() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
	}
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "security/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data7"),
				testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));
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
