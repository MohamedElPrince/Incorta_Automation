package pageObjectModels.modules.security;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;

public class Security_Users {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_security_users");
	String imagesFolderPath = System.getProperty("testDataFolderPath") + "images/";

	//// Elements

	By header_usersTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Users']");
	// header_users_label

	// body_name_checkbox
	By body_name_link;
	// body_lastSignedIn_label

	By popup_addNewUser_loginName_textBox = By.xpath("//input[@ng-model='user.loginName']");
	By popup_addNewUser_password_textBox = By.xpath("//input[@ng-model='user.password']");
	By popup_addNewUser_confirmPassword_textBox = By.xpath("//input[@ng-model='user.password_verify']");
	By popup_addNewUser_displayName_textBox = By.xpath("//input[@ng-model='user.name']");
	By popup_addNewUser_email_textBox = By.xpath("//input[@ng-model='user.email']");
	By popup_addNewUser_language_list = By.xpath("//select[@ng-model='user.language']");
	By popup_addNewUser_regionFormat_list = By.xpath("//select[@ng-model='user.country']");
	By popup_addNewUser_timeZone_list = By.xpath("//select[@ng-model='user.timezone']");
	By popup_addNewUser_calendar_list = By.xpath("//select[@ng-model='user.calendar']");
	By popup_addNewUser_uploadImage_textBox = By
			.xpath("//div[contains(@class,'userDetailsModal')]//input[@type='file']");
	By popup_addNewUser_addUser_button = By.xpath("//button[@ng-click='addNewUser(user)']");
	By popup_addNewUser_cancel_button = By.xpath("//button[@ng-click='modal.closeMe()']");

	//// Functions
	public Security_Users(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}
	// Assert_usersTabIsSelected
	// Assert_usersLabelIsDisplayed
	// Assert_usersNumberIsCorrect

	public void Assert_nameIsDisplayed(String name) {
		body_name_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		CustomAssertions.cAssertElementExists(driver, body_name_link, true);
	}

	// Assert_lastSignedInForUserNameIsCorrect
	// Select_nameCheckbox
	public void Click_name(String name) {
		body_name_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		ElementActions.click(driver, body_name_link);
	}

	public String[] AddNewUser() {
		// can be used to read data from data sheet after adding a parameter to map with
		// the column name
		String[] userData = new String[10];
		userData[0] = "Automation"; // Login Name
		userData[1] = "a"; // Password
		userData[2] = "a"; // Confirm Password
		userData[3] = "automation"; // Display Name
		userData[4] = "a@a.a"; // Email
		userData[5] = "Arabic"; // Language
		userData[6] = "Egypt"; // Region Format
		userData[7] = "-- Time Zone --"; // Time Zone
		userData[8] = "-- Calendar --"; // Calendar
		userData[9] = "user.jpg"; // User Picture File Name
		return AddNewUser(userData); // return login credentials
	}

	public String[] AddNewUser(String[] userData) {
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String loginName = userData[0] + "_" + timeStamp;
		ElementActions.type(driver, popup_addNewUser_loginName_textBox, loginName);
		ElementActions.type(driver, popup_addNewUser_password_textBox, userData[1]);
		ElementActions.type(driver, popup_addNewUser_confirmPassword_textBox, userData[2]);

		String displayName = userData[3] + "_" + timeStamp;
		ElementActions.type(driver, popup_addNewUser_displayName_textBox, displayName);
		ElementActions.type(driver, popup_addNewUser_email_textBox, timeStamp + "_" + userData[4]);
		ElementActions.select(driver, popup_addNewUser_language_list, userData[5]);
		ElementActions.select(driver, popup_addNewUser_regionFormat_list, userData[6]);
		ElementActions.select(driver, popup_addNewUser_timeZone_list, userData[7]);
		ElementActions.select(driver, popup_addNewUser_calendar_list, userData[8]);

		String UserImagePath = imagesFolderPath + userData[9];
		UserImagePath = (new File(UserImagePath)).getAbsolutePath();

		ElementActions.typeFileLocationForUpload(driver, popup_addNewUser_uploadImage_textBox, UserImagePath);

		ElementActions.click(driver, popup_addNewUser_addUser_button);
		return new String[] { loginName, userData[1], displayName }; // return username,password,displayname
	}

}
