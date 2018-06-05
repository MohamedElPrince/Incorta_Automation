package tests.gui.security;

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
import pageObjectModels.content.AllContent;
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.content.AllContent_Dashboard_AnalyzeInsight;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.security.Users;

@Epic("incorta > Security > Users")

public class UsersTest {

	//// Prerequisites
	/**
	 * - Manually Add user with name "Test_User" till automating test data - Add
	 * profile picture image at prepare test data phase
	 */

	//// Variables
	String newDashboardName, newInsightName;
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;
	String TempUser = "Test_User"; // to be replaced later with dynamic one created at prepare test data phase
	String Picture = "ProfilePicture.jpg"; // to be replaced later with dynamic one created at prepare test data phase

	//// Page Objects
	Login loginPage;
	Users usersPage;
	Skeleton mainPage;
	AllContent_Dashboard_AnalyzeInsight analyzeInsightPage;
    AllContent_Dashboard dashboardPage;
    AllContent allContentPage;

	//// Test Cases
//	@Test(priority = 1, description = "C478 - Create User")
//	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
//	@Severity(SeverityLevel.CRITICAL)
//	public void createNewUser() {
//		usersPage = new Users(driver);
//		usersPage.Navigate_toURL();
//
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
//		usersPage = new Users(driver);
//		usersPage.Navigate_toURL();
//		usersPage.Click_name(TempUser);
//		usersPage.UploadProfilePicture(Picture);
//		usersPage.Assert_imageIsDisplayed(TempUser);
//	}
//
//	@Test(priority = 3, description = "C471 - Delete Users")
//	@Description("Given I am logged in, When I navigate to the security.users page, And I select existing user, And I delete this selected user, Then user will not be displayed in the users list.")
//	@Severity(SeverityLevel.CRITICAL)
//	public void deleteUser() {
//		usersPage = new Users(driver);
//		usersPage.Navigate_toURL();
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
//		usersPage = new Users(driver);
//		usersPage.Navigate_toURL();
//		String impersonationUserName = testDataReader.getCellData("ImpersonationUserName");
//
//		usersPage.Assert_nameIsDisplayed(impersonationUserName);
//		usersPage.Click_name(impersonationUserName);
//		usersPage.Click_impersonation();
//
//		usersPage.Assert_impersonationUIElementsAreDisplayed();
//
//		mainPage = new Skeleton(driver);
//	}
	
	@Test(priority = 5, description = "C60533 - Individual Analyzer")
	@Description("Given I login With Individual Analyzer , When I  Create Dashboard, Then Dashboard is Created sucessfully, And I can't share,schedular or send it")
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
		 * -----------------------------------------------------------------------------
		 * ---
		 * -----------------------------------------------------------------------------
		 * test case steps: 1- logout from the previous user. 2- login with the
		 * pre-created user. 3- navigate to content section. 4- create dashboard, add
		 * table from the shared schema, select Aggregated table insight type. 5- Assert
		 * Export icon(this icon let user to share/send/schedule) not displayed in
		 * dashboard page. 6- Assert that dashboard and insight name are correct.
		 * 7-assert that share icon in dashboard settings is dimmed.
		 */

		// Navigate to login page to login with the Individual analyzer user
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
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

	//// Testng Annotations
	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "security/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant", "Data7"), testDataReader.getCellData("Username", "Data7"), 
				testDataReader.getCellData("Password", "Data7"));
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
