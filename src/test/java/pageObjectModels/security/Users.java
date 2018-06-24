package pageObjectModels.security;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

import pageObjectModels.main.Skeleton;

public class Users {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_security_users");
	String imagesFolderPath = System.getProperty("testDataFolderPath") + "images/";

	//// Elements

	By header_usersTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Users']");
	// header_users_label

	By body_name_checkbox;
	By body_name_link;
	By body_image_icon;
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
	By popup_existingUser_saveChanges_button = By.xpath("//button[@ng-click='updateUser(user)']");

	By popup_confirmDelete_transferOwnership_button = By
			.xpath("//div[contains(@class,'confirmDeleteModal')]//button[normalize-space()='Transfer ownership']");
	By popup_confirmDelete_deleteAnyway_button = By
			.xpath("//div[contains(@class,'confirmDeleteModal')]//button[normalize-space()='Delete anyway']");

	By popup_confirmDelete_delete_button = By
			.xpath("//div[contains(@class,'confirmDeleteModal')]//button[normalize-space()='Delete']");

	By popup_transferOwnership_targetuser_radioButton; // p[contains(normalize-space(),'Transfer ownership to the
														// current user')]/preceding-sibling::input[@type='radio']
	By popup_transferOwnership_transferSharingPermissions_checkBox = By
			.xpath("//input[@type='checkbox'][@ng-model='transferSharingPermissions']");
	By popup_transferOwnership_transferOwnership_button = By.xpath("//button[@ng-click='transferOwnership()']");

	By popup_userInformation_userDetails_LoginAsUser_button = By.xpath("//a[@ng-click='impersonate(user)']");

	By popup_impersonationMessage = By.xpath("//span[contains(@class,'impersonate-message')]");

	By popup_UsersToBeAdded_checkbox;
	By popup_addUsersToGroup_UsersPage_add_button = By
			.xpath("//div[contains(@class,'userDetailsModal')]//button[@type='submit'][normalize-space(.)='Add']");

	//// Functions
	public Users(WebDriver driver) {
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
		Assertions.assertElementExists(driver, body_name_link, true);
	}

	public void Assert_nameIsNotDisplayed(String name) {
		body_name_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		Assertions.assertElementExists(driver, body_name_link, false);
	}

	// Assert_lastSignedInForUserNameIsCorrect
	public void Select_nameCheckbox(String name) {
		body_name_checkbox = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/preceding-sibling::div[contains(@class,'userSelection')]/input");
		ElementActions.click(driver, body_name_checkbox);
	}

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
		userData[0] = "Automation_User"; // Login Name
		userData[1] = "a"; // Password
		userData[2] = "a"; // Confirm Password
		userData[3] = "automation_user"; // Display Name
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

	public void ConfirmUserDeletionAndTransferOwnershipToSelf() {
		ElementActions.click(driver, popup_confirmDelete_transferOwnership_button);
		popup_transferOwnership_targetuser_radioButton = By.xpath(
				"//p[contains(normalize-space(),'Transfer ownership to the current user')]/preceding-sibling::input[@type='radio']");
		ElementActions.click(driver, popup_transferOwnership_targetuser_radioButton);
		ElementActions.click(driver, popup_transferOwnership_transferSharingPermissions_checkBox);
		ElementActions.click(driver, popup_transferOwnership_transferOwnership_button);
		ElementActions.click(driver, popup_confirmDelete_delete_button);
	}

	public void ConfirmUserDeletionAnyway() {
		ElementActions.click(driver, popup_confirmDelete_deleteAnyway_button);
	}

	public void UploadProfilePicture(String pictureName) {
		String UserImagePath = imagesFolderPath + pictureName;
		UserImagePath = (new File(UserImagePath)).getAbsolutePath();
		ElementActions.typeFileLocationForUpload(driver, popup_addNewUser_uploadImage_textBox, UserImagePath);
		ElementActions.click(driver, popup_existingUser_saveChanges_button);
	}

	public void Assert_imageIsDisplayed(String userName) {
		body_image_icon = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ userName + "')]//preceding-sibling::div[contains(@class,'userImage')]/img");
		Assertions.assertElementAttribute(driver, body_image_icon, "src", "./content/images/defaultUser.png", false);
	}

	public void ConfirmUserDeletion() {
		ElementActions.click(driver, popup_confirmDelete_delete_button);
	}

	public void Click_impersonation() {
		ElementActions.click(driver, popup_userInformation_userDetails_LoginAsUser_button);
	}

	public void Assert_impersonationUIElementsAreDisplayed() {
		Assertions.assertElementExists(driver, popup_impersonationMessage, true);
		Assertions.assertElementAttribute(driver, popup_impersonationMessage, "text",
				testDataReader.getCellData("ImpersonationMessage"), true);

		Skeleton mainPage;
		mainPage = new Skeleton(driver);
		mainPage.Assert_impersonation_switchBack_link_IsDisplayed();
		mainPage.Assert_fromUserMenu("Switch Back");
	}
	
		public void SelectGroupForUserFromUsersPage(String GroupName)
		{
			popup_UsersToBeAdded_checkbox = By.name(GroupName);
			ElementActions.click(driver, popup_UsersToBeAdded_checkbox);
		}
		
		public void ClickAddToSelectGroupForUser ()
		{
			ElementActions.click(driver, popup_addUsersToGroup_UsersPage_add_button);
		}
		
		public void ConfirmUserDeletionAndTransferOwnershipToAnother(String name) {
			ElementActions.click(driver, popup_confirmDelete_transferOwnership_button);
			popup_transferOwnership_targetuser_radioButton = By.xpath(
					"//p[contains(normalize-space(),'Transfer ownership to another user')]/preceding-sibling::input[@type='radio']");
			ElementActions.click(driver, popup_transferOwnership_targetuser_radioButton);
			
			////////////////////////
			By popup_Transfer_Ownership_To_AnotherUser_Searchbox = By.xpath("//input[@ng-model='entitySearchText']");
			By popup_Transfer_Ownership_To_AnotherUser_SearchList = By.xpath("//h5[@class='UserData left text-left ng-binding'][contains(text(),'"+ name +"')]");
			ElementActions.click(driver, popup_Transfer_Ownership_To_AnotherUser_Searchbox);
			ElementActions.type(driver, popup_Transfer_Ownership_To_AnotherUser_Searchbox, name);

			ElementActions.click(driver, popup_Transfer_Ownership_To_AnotherUser_SearchList);
			
			Assertions.assertElementExists(driver, popup_Transfer_Ownership_To_AnotherUser_SearchList, true);

			////////////////////////

			ElementActions.click(driver, popup_transferOwnership_transferSharingPermissions_checkBox);
			ElementActions.click(driver, popup_transferOwnership_transferOwnership_button);
			ElementActions.click(driver, popup_confirmDelete_delete_button);
		}
}
