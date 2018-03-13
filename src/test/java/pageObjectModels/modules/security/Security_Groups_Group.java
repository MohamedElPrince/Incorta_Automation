package pageObjectModels.modules.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;

public class Security_Groups_Group {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));

	//// Elements
	// header_backToGroupsTab_link
	// header_nameBreadcrumb_label

	// body_name_textBox
	// body_descrption_textBox

	By body_users_label = By.xpath("//*[@id='contentScroll']//div[@class='usersHeader']/h3[contains(text(),'Users')]");
	// body_users_delete_link
	By body_users_add_link = By.xpath(
			"//*[@id='contentScroll']//div[@class='usersHeader']/h3[contains(text(),'Users')]//..//img[contains(@src,'plus')]");
	// body_users_name_checkbox
	By body_users_name_link;

	By popup_addUsersToGroup_search_textBox = By
			.xpath("//div[contains(@class,'userDetailsModal')]//input[@ng-model='usersSearchText']");
	By popup_addUsersToGroup_user_label;
	By popup_addUsersToGroup_add_button = By
			.xpath("//div[contains(@class,'userDetailsModal')]//button[@type='submit'][normalize-space(.)='Add']");
	By popup_addUsersToGroup_cancel_button = By
			.xpath("//div[contains(@class,'userDetailsModal')]//button[normalize-space(.)='Cancel']");

	By body_roles_label = By.xpath("//*[@id='contentScroll']//div[@class='usersHeader']/h3[contains(text(),'Roles')]");
	// body_roles_delete_link
	By body_roles_add_link = By.xpath(
			"//*[@id='contentScroll']//div[@class='usersHeader']/h3[contains(text(),'Roles')]//..//img[contains(@src,'plus')]");
	// body_roles_name_checkbox
	By body_roles_name_link;

	By popup_addRolesToGroup_role_label;
	By popup_addRolesToGroup_add_button = popup_addUsersToGroup_add_button;
	By popup_addRolesToGroup_cancel_button = popup_addUsersToGroup_cancel_button;

	//// Functions
	public Security_Groups_Group(WebDriver driver) {
		this.driver = driver;
	}

	// Navigate_backToGroupsTab
	// Assert_nameBreadcrumbIsDisplayed
	// Type_newName
	// Type_newDescription

	// Assert_usersLabelIsDisplayed
	// Assert_usersNumberIsCorrect
	// DeleteUsers
	public void AddUsers(String[] users) {
		if (users.length > 0) {
			ElementActions.click(driver, body_users_add_link);
			for (String user : users) {
				ElementActions.type(driver, popup_addUsersToGroup_search_textBox, user);
				popup_addUsersToGroup_user_label = By.xpath(
						"//div[contains(@class,'userDetailsModal')]//h5[contains(normalize-space(.),'" + user + "')]");
				ElementActions.click(driver, popup_addUsersToGroup_user_label);
			}
			ElementActions.click(driver, popup_addUsersToGroup_add_button);
		}
	}
	public void Assert_usersAreDisplayed(String[] users) {
		if (users.length > 0) {
			for (String user : users) {
				body_users_name_link = By.xpath(
						"//*[@id='contentScroll']//div[contains(@class,'usersPanel')]//div[contains(@ng-if,'group.users')]//p[text()='"
								+ user + "']");
				CustomAssertions.cAssertElementExists(driver, body_users_name_link, true);
			}
		}
	}

	// Assert_rolesLabelIsDisplayed
	// Assert_rolesNumberIsCorrect
	// DeleteRoles
	public void AddRoles(String[] roles) {
		if (roles.length > 0) {
			ElementActions.click(driver, body_roles_add_link);
			for (String role : roles) {
				popup_addRolesToGroup_role_label = By
						.xpath("//div[contains(@class,'userDetailsModal')]//h5[normalize-space(.)='" + role + "']");
				ElementActions.click(driver, popup_addRolesToGroup_role_label);
			}
			ElementActions.click(driver, popup_addRolesToGroup_add_button);
		}
	}

	public void Assert_rolesAreDisplayed(String[] roles) {
		if (roles.length > 0) {
			for (String role : roles) {
				body_roles_name_link = By.xpath(
						"//*[@id='contentScroll']//div[contains(@class,'usersPanel')]//div[contains(@ng-if,'group.roles')]//p[text()='"
								+ role + "']");
				CustomAssertions.cAssertElementExists(driver, body_roles_name_link, true);
			}
		}
	}

}
