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
	Skeleton mainPage;
	Users usersPage;
	Groups_Group groupPage;

	// Declaring Variables that will be used in below tests
	String newGroupName;
	String groupNameToBeSelected = "Abdelsalam_groupCheckBox";// When creating group --> It's name will be
																// "Abdelsalam_group" without "CheckBox".
	// userName Variable created temporarily until we check the prerequisites
	// needed.
	String userNameAddedToTheGroup = "Abdelsalam_User";
	// As a workaround I'll create another userName to make it array just for method
	// Assert_usersAreDisplayed.
	String addUserToGroupButton = "Add to group";
	String groupNameForTheUser = "Test_group";
	String clickOnGroupName = "Abdelsalam_group_automation";
	String rolesName = "Schema Manager";

	@Test(priority = 1, description = "TC_C474 - Create New Group.")
	@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and click on the "
			+ " and add Group name and description, Click 'Add User' Button. Then, A new group will be added to group list.")
	@Severity(SeverityLevel.CRITICAL)
	public void createNewGroup() {
		groupsPage = new Groups(driver);
		mainPage = new Skeleton(driver);
		groupsPage.Navigate_toURL();
		mainPage.Click_add();
		newGroupName = groupsPage.AddNewGroup();
		groupsPage.Navigate_toURL();
		groupsPage.Assert_groupIsDisplayed(newGroupName);
	}

	@Test(priority = 2, description = "TC_C473 - Add user to group.")
	@Description("Given I've a created user. When I navigate to Security Tab -> Users and select my user, then click on Actions -> Add to group, Then select my group and click on 'Add'. Then my user is added to the group successfully.")
	@Severity(SeverityLevel.CRITICAL)
	public void assignUserToGroup() {
		usersPage = new Users(driver);
		usersPage.Navigate_toURL();
		usersPage.Select_nameCheckbox(userNameAddedToTheGroup);
		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu(addUserToGroupButton);
		// New Function
		usersPage.SelectGroupForUserFromUsersPage(groupNameForTheUser);
		// New Function
		usersPage.ClickAddToSelectGroupForUser();

		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.Click_group(groupNameForTheUser);

		groupPage = new Groups_Group(driver);
		// New Function
		groupPage.Assert_userAreDisplayed(userNameAddedToTheGroup);
	}
	
	
	// Prerequisite --> Login using Admin || A predefined group
	// [groupNameToBeSelected] is needed to be created to be used when deleting
	// group.
	@Test(priority = 3, description = "TC_C467 - Delete Group.")
	@Description("Given I've logged in. When I navigate to Security Tab, And go to Groups and select any groups, Click on delete. Then groups is deleted.")
	@Severity(SeverityLevel.CRITICAL)
	public void DeleteGroup() {
		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.ClickOnGroupCheckBox(groupNameToBeSelected);
		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");
		groupsPage.ClickOnDeleteButton();
		groupsPage.Assert_groupIsNotDisplayed(groupNameToBeSelected);
	}
	
	
	@Test(priority = 4, description = "TC_C469 - Add role to group.")
	@Description("Given I've a specific user and group, When I navigate to groups tab and select my group, Then click on + for Add roles. ")
	@Severity(SeverityLevel.CRITICAL)
	public void AddRoleToGroup() {
		groupsPage = new Groups(driver);
		groupsPage.Navigate_toURL();
		groupsPage.Click_group(clickOnGroupName);
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		groupPage = new Groups_Group(driver);
		String AddedRoles[] = new String[] { testDataReader.getCellData("GroupRoles", "Data1") };
		groupPage.AddRoles(AddedRoles);
		groupPage.Assert_rolesAreDisplayed(AddedRoles);
		groupPage.Assert_rolesAreDisplayed(AddedRoles);
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "certification/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
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