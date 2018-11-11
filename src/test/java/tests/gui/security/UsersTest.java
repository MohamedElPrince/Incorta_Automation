package tests.gui.security;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.Content;
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.login.SignOut;
import pageObjectModels.main.Header;
import pageObjectModels.main.OldUI_SubHeader;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;
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
    String TempUser = "Automation_user_user_toBeEditedAndDeleted"; // to be replaced later with dynamic one created at
    // prepare test data phase
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
    SignOut logoutpage;
    Users usersPage;
    OldUI_SubHeader subHeaderObject;
    Groups_Group groups_groupPage;
    Content allContentPage;
    DataSources dataSourcesPage;
    SchemaList schemasPage;
    SchemaList_SchemaView schemasViewPage;
    Header newHeaderObject;
    Content newContentPage;
    Header newsubHeaderObject;

    //// Test Cases
    @Test(priority = 1, description = "C478 - Create User")
    @Description("Given I am logged in, When I navigate to the security.users page, And I create a new user, And I navigate back to the security.users page, Then the new user will be displayed in the users list.")
    @Severity(SeverityLevel.CRITICAL)
    public void createNewUser() {

	logInWithUserAndNavigateToSecurity("Data7");
	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.click_add();

	newUserData = usersPage.AddNewUser();
	usersPage.Assert_nameIsDisplayed(newUserData[2]);
    }

    @Test(priority = 2, description = "C16118 - User profile picture")
    @Description("Given I am logged in, When I navigate to the security.users page, And I change profile picture of existing user, And I save changes, Then the new profile picture sill be displayed")
    @Severity(SeverityLevel.CRITICAL)
    public void changeProfilePicture() {
	logInWithUserAndNavigateToSecurity("Data7");

	usersPage.Click_name(TempUser);
	usersPage.UploadProfilePicture(Picture);
	usersPage.Assert_imageIsDisplayed(TempUser);
    }

    @Test(priority = 3, description = "C471 - Delete Users")
    @Description("Given I am logged in, When I navigate to the security.users page, And I select existing user, And I delete this selected user, Then user will not be displayed in the users list.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUser() {

	logInWithUserAndNavigateToSecurity("Data7");

	usersPage.Select_nameCheckbox(TempUser); // manually created user till be automated as prerequisites
	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.Click_actions();
	subHeaderObject.Select_fromDropdownMenu("Delete selection");
	usersPage.ConfirmUserDeletion();
	usersPage.Assert_nameIsNotDisplayed(TempUser);
    }

    @Test(priority = 4, description = "C53308 - Testing that during the impersonation session, the UI will be always showing a message indicating that this is an impersonated session")
    @Description("Given I am logged in with an admin account, When I navigate to the security.users page, And I click on a user (not super user), And I click on Login As User, Then a message should be displayed to state that I'm impersonating the user, And a link should be present in the users dropdown menu to take me back, And a link should be displayed in the side menu to take me back.")
    @Severity(SeverityLevel.NORMAL)
    public void impersonationUI() {

	logInWithUserAndNavigateToSecurity("Data7");

	String impersonationUserName = testDataReader.getCellData("ImpersonationUserName");
	usersPage.Assert_nameIsDisplayed(impersonationUserName);
	usersPage.Click_name(impersonationUserName);
	usersPage.Click_impersonation();

	newsubHeaderObject = new Header(driver);
	newsubHeaderObject.assert_splashNotificationMessage_equalsExpected("Signed in as");
	newsubHeaderObject.assert_splashNotificationDescription_equalsExpected("temporarily signed in");

	newHeaderObject.expandUserMenu();
	newHeaderObject.assert_userMenuItem("Switch Back");
	newHeaderObject.expandUserMenu();

    }

    @Test(priority = 5, description = "C647   - Testing Deleting user with option transfer ownership to another user ")
    @Description("Given I have two Super User Accounts, when I share content whith other user \"User1\", And I Delete \"User0\" and transferrer all his content to another user \"User2\", then all content ownership transfered to that user and shared content doesn`t get affected ")
    @Severity(SeverityLevel.CRITICAL)
    public void deletingUserWithOptionTransferOwnershipToAnotherUser() {

	logInWithUserAndNavigateToSecurity("Data7");

	// select user to be deleted and transfer ownership to another user
	usersPage.Select_nameCheckbox(testDataReader.getCellData("Username", "Data10"));
	// Data10 "Automation_User_SuperUser4"--> Owner

	subHeaderObject = new OldUI_SubHeader(driver);
	subHeaderObject.Click_actions();
	subHeaderObject.Select_fromDropdownMenu("Delete selection");

	// Transfer ownership to another user in data8 "Automation_User_SuperUser1"
	usersPage.ConfirmUserDeletionAndTransferOwnershipToAnother(testDataReader.getCellData("Username", "Data8"));
	usersPage.Assert_nameIsNotDisplayed(testDataReader.getCellData("Username", "Data10"));

	// Switch to another admin account user in data8 "Automation_User_SuperUser1"
	switchToAnotherUser("Data8");

	// check [schema, dashboard, folder].
	checkTransfaredContent();

	// Switch to another admin account user in data9 "Automation_User_SuperUser2"
	switchToAnotherUser("Data9");

	// check the shared content with the user[ schema, dashboard, folder].
	checkTransfaredContent();

    }

    private void switchToAnotherUser(String Usertestdata) {

	// Switch to another admin account user in data8 "Automation_User_SuperUser1"
	// and check the transfered content
	newHeaderObject = new Header(driver);
	newHeaderObject.expandUserMenu();
	newHeaderObject.signOut();

	// Assert in signout message.
	logoutpage = new SignOut(driver);
	logoutpage.assert_signOutMessageHeaderAndBodyAreCorrect();
	// Click loginback link on signoutpage

	loginPage = new Login(driver);
	loginPage.navigate_toURL();
	loginPage.userLogin(testDataReader.getCellData("Tenant", Usertestdata),
		testDataReader.getCellData("Username", Usertestdata),
		testDataReader.getCellData("Password", Usertestdata));

	// Assert Content tab is selected after login
	newHeaderObject.assert_sectionHeader_isSelected("Content");
    }

    private void checkTransfaredContent() {
	// check schema after transfer
	schemasPage = new SchemaList(driver);
	schemasPage.Navigate_toURL();
	schemasPage.Assert_schemaNameIsDisplayed(testDataReader.getCellData("newSchemaName"));

	// Check Dashboard/folder after transfer.
	newContentPage = new Content(driver);
	newContentPage.navigate_toURL();

	newContentPage.searchForContentUsingSearchBox(testDataReader.getCellData("newDashboardName"));
	newContentPage.assert_contentSearchResult_isDisplayed(testDataReader.getCellData("newDashboardName"));

	// Check Folder after transfer
	newContentPage.navigate_toURL();
	newContentPage.searchForContentUsingSearchBox(testDataReader.getCellData("newFolderName"));
	newContentPage.assert_contentSearchResult_isDisplayed(testDataReader.getCellData("newFolderName"));
    }

    private void logInWithUserAndNavigateToSecurity(String UserData) {

	loginPage = new Login(driver);
	loginPage.navigate_toURL();

	loginPage.userLogin(testDataReader.getCellData("Tenant", UserData),
		testDataReader.getCellData("Username", UserData), testDataReader.getCellData("Password", UserData));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	usersPage = new Users(driver);
	usersPage.Navigate_toURL();

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Security");

    }

    //// Testng Annotations

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

	// Assert in signout message.
	logoutpage = new SignOut(driver);
	logoutpage.assert_signOutMessageHeaderAndBodyAreCorrect();
    }
}
