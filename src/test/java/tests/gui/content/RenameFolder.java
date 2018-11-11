package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.Content;
import pageObjectModels.content.Content_Dashboard;
import pageObjectModels.content.Content_Dashboard_AnalyzeInsight;
import pageObjectModels.content.Content_Dashboard_ScheduleDashboard;
import pageObjectModels.content.Content_Dashboard_SendDashboard;
import pageObjectModels.content.Content_Folder;
import pageObjectModels.data.DataSources;
import pageObjectModels.login.Login;
import pageObjectModels.main.Header;
import pageObjectModels.main.OldUI_SubHeader;
import pageObjectModels.scheduler.Dashboards;
import pageObjectModels.scheduler.SchemaLoads;
import pageObjectModels.schemas.SchemaList;
import pageObjectModels.schemas.SchemaList_SchemaView;
import pageObjectModels.security.Groups;
import pageObjectModels.security.Groups_Group;
import pageObjectModels.security.Users;

public class RenameFolder {

    // Declaring web-driver and excel reader instances
    WebDriver driver;
    ExcelFileManager testDataReader;

    // Declaring Page Objects that will be used throughout the test
    Login loginPage;
    OldUI_SubHeader subHeaderObject;
    DataSources dataSourcesPage;
    SchemaList schemasPage;
    SchemaList_SchemaView schemasViewPage;
    Users usersPage;

    Content_Dashboard_AnalyzeInsight analyzeInsightPage;
    Content_Dashboard newUI_dashboardPage;
    Content_Dashboard_SendDashboard sendDashboardPage;
    Content_Dashboard_ScheduleDashboard scheduleDashboardPage;
    Content_Folder newUI_folderPage;

    Groups groupsPage;
    SchemaLoads schedulerSchemaLoadsPage;
    Dashboards schedulerDashboardsPage;
    Header newHeaderObject;
    Content newContentPage;
    Groups_Group groups_groupPage;

    // Declaring public variables that will be shared between tests

    @Test(priority = 1, description = "C83174 - Chrome: Folder : Rename a folder is working.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_Working() {
	newContentPage = new Content(driver);
	newContentPage
		.click_dashboardFolder_properties_fromCardView(testDataReader.getCellData("Automation_Folder_Rename"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage
		.renameFolder_popup_typeNewFolderName(testDataReader.getCellData("Automation_Folder_Rename_Renamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage
		.assert_renameFolder_successMessage(testDataReader.getCellData("Automation_Folder_Rename_Renamed"));
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_Renamed"));
    }

    @Test(priority = 2, description = "C83175 - Chrome: Folder : Rename with special characters / numbers.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name [With Special characters] and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_SpecialCharacters_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_SpecialCharacters"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_SpecialCharacters_Renamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_successMessage(
		testDataReader.getCellData("Automation_Folder_Rename_SpecialCharacters_Renamed"));
	newContentPage.Assert_DashboardExist(
		testDataReader.getCellData("Automation_Folder_Rename_SpecialCharacters_Renamed"));
    }

    @Test(priority = 3, description = "C83176 - Chrome: Folder : Rename with arabic name.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name [Arabic] and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_Arabic_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_Arabic"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_Arabic_Renamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_successMessage(
		testDataReader.getCellData("Automation_Folder_Rename_Arabic_Renamed"));
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_Arabic_Renamed"));
    }

    @Test(priority = 4, description = "C83177 - Chrome: Folder : Rename with french name.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name [French] and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_French_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_French"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_French_Renamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_successMessage(
		testDataReader.getCellData("Automation_Folder_Rename_French_Renamed"));
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_French_Renamed"));
    }

    @Test(priority = 5, description = "C83178 - Chrome: Folder : Rename with chinese name.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name [Chinese] and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_Chinese_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_Chinese"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_Chinese_Renamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_successMessage(
		testDataReader.getCellData("Automation_Folder_Rename_Chinese_Renamed"));
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_Chinese_Renamed"));
    }

    @Test(priority = 6, description = "C83180 - Chrome: Folder : Testing if no change done to the name.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename. Then rename button will be dimmed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_NoChangeInFolderName_RenameButtonDimmed() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_NoRename"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newContentPage.assert_renameFolder_popup_renameButtonDimmed();
    }

    @Test(priority = 7, description = "C83182 - Chrome: Folder: if Rename bar is empty.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename and delete folder name. Then rename button will be dimmed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_RenameBarIsEmpty_RenameButtonDimmed() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_NoRename"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newContentPage.renameFolder_popup_typeNewFolderName("");
	newContentPage.assert_renameFolder_popup_renameButtonDimmed();
    }

    @Test(priority = 8, description = "C83184 - Chrome: Rename folder window layout.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename. Then rename screen will be displayed correctly.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_RenameScreen_DisplayedCorrectly() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_NoRename"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newContentPage.assert_renameFolder_popupScreen_screenContentDisplayed();
    }

    @Test(priority = 9, description = "C83186 - Chrome: Rename a folder Cancel button.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename -> Add new name and cancel. Then name will not be changed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_CancelButton_FolderNotRenamed() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_NoRename"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_Chinese_Renamed"));
	newContentPage.renameFolder_popup_clickCancel();
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_NoRename"));
    }

    @Test(priority = 10, description = "C83188 - Chrome: User can Rename a folder with a long name.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name [Long Name] and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_LongName_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_LongName"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_LongNameRenamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_successMessage(
		testDataReader.getCellData("Automation_Folder_Rename_LongNameRenamed"));
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_LongNameRenamed"));
    }

    @Test(priority = 11, description = "C83192 - Chrome: Verify that User can rename the Folder with a Name contains Spaces.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write new folder name [With Spaces] and I click on Rename. Then Folder will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_WithSpaces_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_WithSpaces"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(
		testDataReader.getCellData("Automation_Folder_Rename_WithSpaces_Renamed"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_successMessage(
		testDataReader.getCellData("Automation_Folder_Rename_WithSpaces_Renamed"));
	newContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Folder_Rename_WithSpaces_Renamed"));
    }

    @Test(priority = 12, description = "C83194 - Chrome: Verify that the user cannot Rename a Folder with an existing Name.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - write existing folder name and I click on Rename. Then an error will be displayed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_WithExistingFolderName_ErrorMessageDisplayed() {
	newContentPage = new Content(driver);
	newContentPage
		.click_dashboardFolder_properties_fromCardView(testDataReader.getCellData("Automation_Folder_Rename1"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");
	newContentPage.renameFolder_popup_typeNewFolderName(testDataReader.getCellData("Automation_Folder_Rename2"));
	newContentPage.renameFolder_popup_clickRename();
	newContentPage.assert_renameFolder_errorMessageDisplayed();
    }

    @Test(priority = 13, description = "C83196 - Chrome: Verify that Close Icon in 'Rename Folder' window is working.")
    @Description("When I navigate to the content screen, and I click on Folder properties -> Rename - wand I click on close Icon. Then screen will be closed successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameFolder_CloseIcon_Working() {
	newContentPage = new Content(driver);
	newContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Folder_Rename_NoRename"));
	newContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newContentPage.renameFolder_clickCloseIcon();
	newContentPage.assert_renameFolder_screen_notExist();
    }

    @BeforeMethod
    public void beforeMethod() {
	newContentPage = new Content(driver);
	newContentPage.navigate_toURL();
    }

    @BeforeClass
    public void beforeClass() {
	System.setProperty("testDataFilePath",
		System.getProperty("testDataFolderPath") + "renameFolder_newUI/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	driver = BrowserFactory.getBrowser(testDataReader);

	loginPage = new Login(driver);
	loginPage.navigate_toURL();
	loginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("Password"));
    }
}
