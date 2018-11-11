package tests.gui.security;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.Content;
import pageObjectModels.login.Login;
import pageObjectModels.main.Header;
import pageObjectModels.main.OldUI_SubHeader;
import pageObjectModels.security.Groups;
import pageObjectModels.security.Groups_Group;
import pageObjectModels.security.Users;

@Epic("Incorta -> Security -> Groups")
public class GroupsTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used in the tests
	Groups groupsPage;
	Login loginPage;
	OldUI_SubHeader subHeaderObject;
	Users usersPage;
	Groups_Group groupPage;
	Content newContentPage;
	Header newHeaderObject;

	// Declaring Variables that will be used in below tests
	String newGroupName;
	String AddedRoles[];

	// Prerequisite, Login using Admin + A predefined group, user, role
	// Prerequisites, To be created manually for now
	String groupNameToBeSelected = "Automation_Group_GroupToBeDeleted";
	String userNameAddedToTheGroup = "Automation_User_NormalUser";
	String groupNameForTheUser = "Automation_Group_AddUserToGroup";
	String clickOnGroupName = "Automation_Group_AddRoleToGroup";

	@Test(priority = 1, description = "TC_C474 - Create New Group.")
	@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and click on the "
			+ " and add Group name and description. Then, A new group will be added to group list.")
	@Severity(SeverityLevel.NORMAL)
	public void createNewGroup() {
		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();

		subHeaderObject = new OldUI_SubHeader(driver);
		subHeaderObject.click_add();
		newGroupName = groupsPage.AddNewGroup();
		groupsPage.Navigate_toURL();
		groupsPage.Assert_groupIsDisplayed(newGroupName);
	}

	@Test(priority = 2, description = "TC_C473 - Add user to group.")
	@Description("Given I've a created user. When I navigate to Security Tab -> Users and select user, And I click on Actions -> Add to group, Then user is added to the group successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void addUserToGroup() {

		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Select_nameCheckbox(userNameAddedToTheGroup);

		subHeaderObject = new OldUI_SubHeader(driver);
		subHeaderObject.Click_actions();
		subHeaderObject.Select_fromDropdownMenu("Add to group");

		usersPage.SelectGroupForUserFromUsersPage(groupNameForTheUser);
		usersPage.ClickAddToSelectGroupForUser();

		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.Click_group(groupNameForTheUser);
		groupPage = new Groups_Group(driver);
		groupPage.Assert_userIsDisplayed(userNameAddedToTheGroup);
	}

	// [groupNameToBeSelected] is needed to be created to be used when deleting
	// group.
	@Test(priority = 3, description = "TC_C467 - Delete Group.")
	@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and select any groups, Click on delete. Then groups is deleted.")
	@Severity(SeverityLevel.NORMAL)
	public void DeleteGroup() {
		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.ClickOnGroupCheckBox(groupNameToBeSelected);
		subHeaderObject = new OldUI_SubHeader(driver);
		subHeaderObject.Click_actions();
		subHeaderObject.Select_fromDropdownMenu("Delete selection");
		groupsPage.ClickOnDeleteButton();
		groupsPage.Assert_groupIsNotDisplayed(groupNameToBeSelected);
	}

	@Test(priority = 4, description = "TC_C469 - Add role to group.")
	@Description("Given I've a specific user and group, When I navigate to groups tab and select specific group, And I click on + button of Roles, And I select roles, And I click Add, Then Role is added to this group")
	@Severity(SeverityLevel.NORMAL)
	public void AddRoleToGroup() {
		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.Click_group(clickOnGroupName);

		groupPage = new Groups_Group(driver);
		AddedRoles = new String[] { testDataReader.getCellData("GroupRoles", "Data1") };
		groupPage.AddRoles(AddedRoles);
		groupPage.Assert_rolesAreDisplayed(AddedRoles);
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "security_newUI/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
		loginPage = new Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant", "Data7"),
				testDataReader.getCellData("Username", "Data7"), testDataReader.getCellData("Password", "Data7"));

		newHeaderObject = new Header(driver);
		newHeaderObject.assert_sectionHeader_isSelected("Content");

	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		ReportManager.getTestLog();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		BrowserFactory.closeAllDrivers();
		ReportManager.getFullLog();
	}

}