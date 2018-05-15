package pageObjectModels.modules.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class Login_Login {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_login_login");
	String incortaVersion = System.getProperty("incortaVersion");

	//// Elements
	By header_incortaLogo_image = By.xpath("//img[contains(@src,'incorta')]");
	By body_tenant_textBox = By.xpath("//input[@ng-model='User.tenant']");
	By body_username_textBox = By.xpath("//input[@ng-model='User.user']");
	By body_password_textBox = By.xpath("//input[@ng-model='User.pass']");
	By body_forgotPassword_link = By.xpath("//input[@ng-click='forgetPassSwitch()']");
	By body_signIn_button = By.className("signInBtn");
	By footer_incortaCopyrights_label = By.className("loginFooter");

	// First Time Login
	By body_changePasswordMessageHeader_label;
	By body_changePasswordMessageBody_label;
	By body_oldPassword_textBox = By.xpath("//input[@ng-model='password.oldPassword']");
	By body_newPassword_textBox = By.xpath("//input[@ng-model='password.password']");
	By body_confirmNewPassword_textBox = By.xpath("//input[@ng-model='password.password_verify']");
	By body_updatePassword_button = By.className("signInBtn");

	//// Functions
	public Login_Login(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_logoIsDisplayed() {
		Assertions.assertElementExists(driver, header_incortaLogo_image, true);
	}

	public void Login(String tenant, String username, String password) {
		ElementActions.type(driver, body_tenant_textBox, tenant);
		ElementActions.type(driver, body_username_textBox, username);
		ElementActions.type(driver, body_password_textBox, password);
		ElementActions.click(driver, body_signIn_button);
	}

	public void Navigate_toForgotPassword() {
		ElementActions.click(driver, body_forgotPassword_link);
	}

	public void Assert_correctVersionNumberIsDisplayed() {
		Assertions.assertElementAttribute(driver, footer_incortaCopyrights_label, "Text",
				"([\\s\\S]*" + incortaVersion + ".*[\\s\\S]*)", true);

		// ([\s\S]*Rel3.2\s[\s\S]*) This regular expression matches all characters and
		// white spaces before the version number, then a whitespace right after the
		// version number, and any trailing white spaces or characters
	}

	public void FirstTimeLogin(String oldPassword, String newPassword, String confirmNewPassword) {
		ElementActions.type(driver, body_oldPassword_textBox, oldPassword);
		ElementActions.type(driver, body_newPassword_textBox, newPassword);
		ElementActions.type(driver, body_confirmNewPassword_textBox, confirmNewPassword);
		ElementActions.click(driver, body_updatePassword_button);
	}
}
