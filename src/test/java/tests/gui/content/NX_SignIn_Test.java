package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.login.Login;
import pageObjectModels.login.SignOut;
import pageObjectModels.main.Header;

@Epic("Content -> NX SignIn")
public class NX_SignIn_Test {

    // Declaring web-driver and excel reader instances
    private WebDriver driver;
    private ExcelFileManager testDataReader;

    // Declaring Page Objects that will be used throughout the test
    private Login newLoginPage;
    private SignOut newSignOutPage;
    private Header newHeaderObject;

    @Test(priority = 1, description = "C82103 - Chrome: Testing that sign in page icons are displayed")
    @Description("When I navigate to the login page,Then all the icons will be displayed")
    @Severity(SeverityLevel.CRITICAL)
    public void signinPageIconsAreDisplayed() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();

	newLoginPage.verifyThat_All_logosAreDisplayed();
    }

    @Test(priority = 2, description = "C82103_01 - Chrome: Testing that sign in page icons are displayed")
    @Description("When I navigate to the login page,and check website link,then i found that it match with the expected one")
    @Severity(SeverityLevel.CRITICAL)
    public void signinPageIconURLsAreCorrect_incortaWebsite() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();

	newLoginPage.assert_PageIconURLsAreCorrect("website");

    }

    @Test(priority = 3, description = "C82103_02 - Chrome: Testing that sign in page icons are displayed")
    @Description("When I navigate to the login page,and check twitter link,then i found that it match with the expected one")
    @Severity(SeverityLevel.CRITICAL)
    public void signinPageIconURLsAreCorrect_twitter() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();

	newLoginPage.assert_PageIconURLsAreCorrect("twitter");
    }

    @Test(priority = 4, description = "C82103_03 - Chrome: Testing that sign in page icons are displayed")
    @Description("When I navigate to the login page,and check linkedin link,then i found that it match with the expected one")
    @Severity(SeverityLevel.CRITICAL)
    public void signinPageIconURLsAreCorrect_linkedin() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();

	newLoginPage.assert_PageIconURLsAreCorrect("linkedin");
    }

    @Test(priority = 5, description = "C82103_4 - Chrome: Testing that sign in page icons are displayed")
    @Description("When I navigate to the login page,and check support link,then i found that it match with the expected one")
    @Severity(SeverityLevel.CRITICAL)
    public void signinPageIconURLsAreCorrect_support() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();

	newLoginPage.assert_PageIconURLsAreCorrect("support");
    }

    @Test(priority = 6, description = "C82104 - Chrome: Testing that the sign in works properly for a user who previously signed in")
    @Description("When I navigate to the login page,and enter user credentials for login and click signin,user signin successful ")
    @Severity(SeverityLevel.CRITICAL)
    public void signinworksCorrect_WithPreCreatecUser_signedinBefore() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant", "Data2"),
		testDataReader.getCellData("Username", "Data2"), testDataReader.getCellData("Password", "Data2"));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	signOutFromCurrentUser();

    }

    @Test(priority = 7, description = "C82116 - Chrome: Testing that the sign in works properly for a user who is signing in for the first time")
    @Description("When I navigate to the login page,and enter New created user credentials and click signin,user redirected to change password page,then password changed and signin successful ")
    @Severity(SeverityLevel.CRITICAL)
    public void signinworksCorrect_WithNewCreatecUser() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant", "Data3"),
		testDataReader.getCellData("Username", "Data3"), testDataReader.getCellData("FirstPassword", "Data3"));

	newLoginPage.firstTimeLogin(testDataReader.getCellData("FirstPassword", "Data3"),
		testDataReader.getCellData("Password", "Data3"), testDataReader.getCellData("Password", "Data3"));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	signOutFromCurrentUser();

    }

    @Test(priority = 8, description = "C82105 - Chrome: Entering incorrect Tenant")
    @Description("When I navigate to the login page,and user credentials but with incorrect tenant name,warning message appeare")
    @Severity(SeverityLevel.CRITICAL)
    public void signinWithIncortectTenantName() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("WrongTenantName"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("Password"));
	newLoginPage.assert_errorMessage("wrongTenant");

    }

    @Test(priority = 9, description = "C82106 - Chrome: Entering incorrect Username")
    @Description("When I navigate to the login page,and enter user credentials but with incorrect user name,warning message appeare")
    @Severity(SeverityLevel.CRITICAL)
    public void signinWithIncortectUserName() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("WrongUserName"),
		testDataReader.getCellData("Password"));
	newLoginPage.assert_errorMessage("wronguser");

    }

    @Test(priority = 10, description = "C82107 - Chrome: Entering incorrect Password")
    @Description("When I navigate to the login page,and enter user credentials but with incorrect password,warning message appeare")
    @Severity(SeverityLevel.CRITICAL)
    public void signinWithIncortectPassword() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("WrongPassword"));
	newLoginPage.assert_errorMessage("wrongpassword");

    }

    @Test(priority = 11, description = "C82109 - Chrome: Testing if some data is missing from the credentials")
    @Description("When I navigate to the login page,and miss one of user credentials,then signin button dimmed")
    @Severity(SeverityLevel.CRITICAL)
    public void signinWithMissingCredentials_Password() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"), "");
	newLoginPage.assert_SignIn_Button(false);

    }

    @Test(priority = 12, description = "C82109-01 - Chrome: Testing if some data is missing from the credentials")
    @Description("When I navigate to the login page,and miss one of user credentials,then signin button dimmed")
    @Severity(SeverityLevel.CRITICAL)
    public void signinWithMissingCredentials_Tenant() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin("", testDataReader.getCellData("Username"), testDataReader.getCellData("Password"));
	newLoginPage.assert_SignIn_Button(false);

    }

    @Test(priority = 13, description = "C82109-02 - Chrome: Testing if some data is missing from the credentials")
    @Description("When I navigate to the login page,and miss one of user credentials,then signin button dimmed")
    @Severity(SeverityLevel.CRITICAL)
    public void signinWithMissingCredentials_username() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), "", testDataReader.getCellData("Password"));
	newLoginPage.assert_SignIn_Button(false);

    }

    @Test(priority = 14, description = "C82117 - Chrome: Testing that the sign out works properly")
    @Description("When I login,and I click signOut, then I signout successfuly and redirect to signout page")
    @Severity(SeverityLevel.CRITICAL)
    public void signOutWorksProperly() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("Password"));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	signOutFromCurrentUser();

    }

    @Test(priority = 15, description = "C82118 - Chrome: Testing the link to sign back in works properly")
    @Description("When I login,and I click signOut, then I signout successfuly and redirect to signout page then i check that sign back in works properly")
    @Severity(SeverityLevel.CRITICAL)
    public void signbackLink_WorksProperly() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("Password"));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	signOutFromCurrentUser();

	newSignOutPage = new SignOut(driver);

	newSignOutPage.navigate_toLoginPage();

	newLoginPage.assert_logoIsDisplayed();
    }

    @Test(priority = 16, description = "C82123 - Chrome: Testing that the Icons in the sign out page are displayed Correctly")
    @Description("When I login,and I click signOut, then I signout successfuly and redirect to signout page then i that all icons are displayed")
    @Severity(SeverityLevel.CRITICAL)
    public void signOutPage_iconsdisplayed() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("Password"));

	newHeaderObject = new Header(driver);
	newHeaderObject.assert_sectionHeader_isSelected("Content");

	signOutFromCurrentUser();

	newSignOutPage = new SignOut(driver);
	newSignOutPage.verifyThat_All_logosAreDisplayed();
    }

    @Test(priority = 17, description = "C83459 - Chrome: Testing that the Build version and Date are correctly displayed and reflected in the sign in and sign out pages")
    @Description("When I login,and I check the build version section, then I find there the build version and build date reflected on the login page")
    @Severity(SeverityLevel.CRITICAL)
    public void logInPage_BuildInformation() {
	newLoginPage = new Login(driver);
	newLoginPage.navigate_toURL();
	newLoginPage.verify_correctVersionNumberIsDisplayed();
    }

    private void signOutFromCurrentUser() {
	newHeaderObject = new Header(driver);
	newHeaderObject.expandUserMenu();
	newHeaderObject.signOut();

	newSignOutPage = new SignOut(driver);
	newSignOutPage.assert_signOutMessageHeaderAndBodyAreCorrect();
    }

    @BeforeClass
    public void beforeClass() {
	System.setProperty("testDataFilePath",
		System.getProperty("testDataFolderPath") + "NewUI_NX_SignIn/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	driver = BrowserFactory.getBrowser();
    }
}