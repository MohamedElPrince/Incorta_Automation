package pageObjectModels.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

public class Login {
    //// Variables
    private WebDriver driver;
    private ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
    private String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_login_login");
    private String incortaVersion = System.getProperty("incortaVersion");

    //// Elements
    private By header_incortaLogo_image = By.xpath("//img[contains(@src,'incorta')]");
    private By header_incorta_website_logo = By.xpath("//span[text()='Website']/parent::a");
    private By header_incorta_twitter_logo = By.xpath("//*[@data-icon='twitter']/ancestor::a");
    private By header_incorta_linkedin_logo = By.xpath("//i[contains(@class,'linkedin')]/parent::a");
    private By body_WarningMessage = By.xpath("//span[@class='error-message']");
    private By body_incorta_support_logo = By.xpath("//span[contains(.,'Support')]/parent::a");
    private By body_tenant_textBox = By.id("tenant");
    private By body_username_textBox = By.id("username");
    private By body_password_textBox = By.id("password");
    private By body_forgotPassword_link = By.xpath("//button[@type='button'][contains(.,'Forgot Password?')]");
    private By body_signIn_button = By.xpath("//button[@type='submit'][contains(.,'Sign In')]");
    private By footer_incortaCopyrights_label = By.xpath("//div[contains(@class,'footer')]");

    // First Time Login
    private By body_oldPassword_textBox = By.id("oldPassword");
    private By body_newPassword_textBox = By.id("newPassword");
    private By body_confirmNewPassword_textBox = By.id("confirmNewPassword");
    private By body_updatePassword_button = By.xpath("//button[@type='submit'][contains(.,'Create & Go')]");

    //// Functions
    public Login(WebDriver driver) {
	this.driver = driver;
    }

    public void navigate_toURL() {
	BrowserActions.navigateToURL(driver, url);
    }

    public void assert_logoIsDisplayed() {
	Assertions.assertElementExists(driver, header_incortaLogo_image, true);
    }

    public void userLogin(String tenant, String username, String password) {
	ElementActions.type(driver, body_tenant_textBox, tenant);
	ElementActions.type(driver, body_username_textBox, username);
	ElementActions.type(driver, body_password_textBox, password);
	ElementActions.click(driver, body_signIn_button);
    }

    public void navigate_toForgotPassword() {
	ElementActions.click(driver, body_forgotPassword_link);
    }

    public void verify_correctVersionNumberIsDisplayed() {
	Verifications.verifyElementAttribute(driver, footer_incortaCopyrights_label, "text",
		"([\\s\\S]*" + incortaVersion + "[\\s\\S]*)", true);

	// ([\s\S]*Rel3.2\s[\s\S]*) This regular expression matches all characters and
	// white spaces before the version number, then a whitespace right after the
	// version number, and any trailing white spaces or characters
    }

    public void firstTimeLogin(String oldPassword, String newPassword, String confirmNewPassword) {
	ElementActions.type(driver, body_oldPassword_textBox, oldPassword);
	ElementActions.type(driver, body_newPassword_textBox, newPassword);
	ElementActions.type(driver, body_confirmNewPassword_textBox, confirmNewPassword);
	ElementActions.click(driver, body_updatePassword_button);
    }

    public void verifyThat_All_logosAreDisplayed() {

	Verifications.verifyElementExists(driver, header_incortaLogo_image, true);
	Verifications.verifyElementExists(driver, header_incorta_website_logo, true);
	Verifications.verifyElementExists(driver, header_incorta_twitter_logo, true);
	Verifications.verifyElementExists(driver, header_incorta_linkedin_logo, true);
	Verifications.verifyElementExists(driver, body_incorta_support_logo, true);
	Verifications.verifyElementExists(driver, body_forgotPassword_link, true);

    }

    /**
     * 
     * @param logoURlCheck
     *            --> Could have one of the following options: "website" "twitter"
     *            "linkedin" "support"
     */

    public void assert_PageIconURLsAreCorrect(String logoURlCheck) {

	switch (logoURlCheck) {
	case "website":
	    Assertions.assertElementAttribute(driver, header_incorta_website_logo, "href",
		    testDataReader.getCellData("website"), 1, true);
	    break;
	case "twitter":
	    Assertions.assertElementAttribute(driver, header_incorta_twitter_logo, "href",
		    testDataReader.getCellData("twitter"), 1, true);
	    break;
	case "linkedin":
	    Assertions.assertElementAttribute(driver, header_incorta_linkedin_logo, "href",
		    testDataReader.getCellData("linkedin"), 1, true);
	    break;
	case "support":
	    Assertions.assertElementAttribute(driver, body_incorta_support_logo, "href",
		    testDataReader.getCellData("support"), 1, true);
	    break;
	}
    }

    /**
     * 
     * @param ErrorCheck
     *            --> Could have one of the following options: "wrongTenant"
     *            "wronguser" "wrongpassword"
     */
    public void assert_errorMessage(String ErrorCheck) {

	if (ErrorCheck == "wrongTenant") {

	    Assertions.assertElementAttribute(driver, body_WarningMessage, "text",
		    testDataReader.getCellData("WrongTenantNameError"), 1, true);
	}

	else {

	    Assertions.assertElementAttribute(driver, body_WarningMessage, "text",
		    testDataReader.getCellData("WrongUser_passwordError"), 1, true);

	}

    }

    /**
     * 
     * @param ButtonCheck
     *            --> Could have one of the following options: "true": check that
     *            the button enabled "false": check that button is disabled
     */
    public void assert_SignIn_Button(boolean ButtonCheck) {

	if (ButtonCheck) {
	    Assertions.assertElementAttribute(driver, body_signIn_button, "disabled", "true", 3, false);
	} else {
	    Assertions.assertElementAttribute(driver, body_signIn_button, "disabled", "true", 3, true);
	}

    }

}
