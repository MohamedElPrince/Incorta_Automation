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
	WebDriver driver;
	ExcelFileManager testDataReader;
	String[] newUserData;
	String TempUser = "Test_User"; // to be replaced later with dynamic one created at prepare test data phase
	String Picture = "ProfilePicture.jpg"; // to be replaced later with dynamic one created at prepare test data phase

	//// Page Objects
	Login loginPage;
	Users usersPage;
	Skeleton mainPage;

	//// Test Cases
	@Test(priority = 1, description = "C478 - Create User")
	@Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createNewUser() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();

		mainPage = new Skeleton(driver);
		mainPage.Click_add();

		newUserData = usersPage.AddNewUser();
		usersPage.Assert_nameIsDisplayed(newUserData[2]);
	}
	
	@Test(priority = 2, description = "C16118 - User profile picture")
	@Description("Given I am logged in, When I navigate to the security.users page, And I change profile picture of existing user, And I save changes, Then the new profile picture sill be displayed")
	@Severity(SeverityLevel.CRITICAL)
	public void changeProfilePicture() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Click_name(TempUser);
		usersPage.UploadProfilePicture(Picture);
		usersPage.Assert_imageIsDisplayed(TempUser);
	}

	@Test(priority = 3, description = "C471 - Delete Users")
	@Description("Given I am logged in, When I navigate to the security.users page, And I select existing user, And I delete this selected user, Then user will not be displayed in the users list.")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUser() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Select_nameCheckbox(TempUser); // manually created user till be automated as prerequisites
		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");
		usersPage.ConfirmUserDeletion();
		usersPage.Assert_nameIsNotDisplayed(TempUser);
	}

	@Test(priority = 4, description = "C53308 - Testing that during the impersonation session, the UI will be always showing a message indicating that this is an impersonated session")
	@Description("Given I am logged in with an admin account, When I navigate to the security.users page, And I click on a user (not super user), And I click on Login As User, Then a message should be displayed to state that I'm impersonating the user, And a link should be present in the users dropdown menu to take me back, And a link should be displayed in the side menu to take me back.")
	@Severity(SeverityLevel.NORMAL)
	public void impersonationUI() {

		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
		String impersonationUserName = testDataReader.getCellData("ImpersonationUserName");

		usersPage.Assert_nameIsDisplayed(impersonationUserName);
		usersPage.Click_name(impersonationUserName);
		usersPage.Click_impersonation();

		usersPage.Assert_impersonationUIElementsAreDisplayed();

		mainPage = new Skeleton(driver);
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
