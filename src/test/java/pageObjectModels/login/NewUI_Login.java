package pageObjectModels.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

public class NewUI_Login {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_login_login");
	String incortaVersion = System.getProperty("incortaVersion");

	//// Elements
	private By header_incortaLogo_image = By.xpath("//img[contains(@src,'incorta')]");
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
	public NewUI_Login(WebDriver driver) {
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

}
