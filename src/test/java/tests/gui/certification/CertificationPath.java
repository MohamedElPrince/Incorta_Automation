package tests.gui.certification;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.io.ExcelReader;
import com.shaftEngine.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.modules.content.Content_AllContent;
import pageObjectModels.modules.data.Data_DataFiles;
import pageObjectModels.modules.data.Data_DataSources;
import pageObjectModels.modules.login.Login_Login;
import pageObjectModels.modules.login.Login_Logout;
import pageObjectModels.modules.main.Main_Skeleton;
import pageObjectModels.modules.schemas.Schemas_SchemaList;
import pageObjectModels.modules.schemas.Schemas_SchemaList_SchemaView;
import pageObjectModels.modules.security.Security_Groups;
import pageObjectModels.modules.security.Security_Groups_Group;
import pageObjectModels.modules.security.Security_Users;

@Epic("incorta Certification Path.")
public class CertificationPath {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelReader testDataReader;

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

	// Declaring public variables that will be shared between tests
	String[] newUserData; // username, password, displayname
	String newGroupName;
	String newDataSourceName;
	String uploadedDataFileName;
	String newSchemaName;

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
	public void logoutFromAdminAccount() {
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
	// @Test(priority = 8, description = "TC008 - Create DataSource.",
	// dependsOnMethods = { "loginUsingAdmin" })
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
	// @Test(priority = 9, description = "TC009 - Upload DataFile.",
	// dependsOnMethods = { "loginUsingAdmin" })
	@Description("When I navigate to the \"DataFiles\" page, And click add, And upload a new dataFile, Then dataFile upload success message will be displayed, And dataFile name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void uploadDataFile() {
		dataFilesPage = new Data_DataFiles(driver);
		dataFilesPage.Navigate_toURL();
		dataFilesPage.Assert_dataFilesTabIsSelected();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();

		uploadedDataFileName = dataFilesPage.AddDataFile(testDataReader.getCellData("DataFileName"),
				testDataReader.getCellData("DataFileExtension"));

		dataFilesPage.Assert_dataFileUploadingWasSuccessful(uploadedDataFileName);
		dataFilesPage.Assert_nameIsDisplayed(uploadedDataFileName);
	}

	// @Test(priority = 10, description = "TC010 - Create Schema.", dependsOnMethods
	// = { "loginUsingNewlyCreatedUserAccount" })
	@Test(priority = 10, description = "TC010 - Create Schema.", dependsOnMethods = { "loginUsingAdmin" })
	@Description("When I navigate to the \"Schemas\" page, And click add, And create a new schema, And navigate back to the schemas page, Then the newly created schema name will be displayed in the list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createSchema() {

		schemasPage = new Schemas_SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		mainPage = new Main_Skeleton(driver);
		mainPage.Click_add();
		mainPage.Select_fromAddMenu("Create Schema");

		newSchemaName = schemasPage.createNewSchema();

		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaNameIsDisplayed(newSchemaName);
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
		testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
	}

	@AfterClass
	public void afterClass() {
		BrowserFactory.closeAllDrivers();
		ReportManager.logTest();
		ReportManager.getLog();
	}

}
