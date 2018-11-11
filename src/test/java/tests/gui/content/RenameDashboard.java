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
import pageObjectModels.content.Content;
import pageObjectModels.login.Login;

@Epic("Content -> Rename Dashboard")
public class RenameDashboard {

    // Declaring web-driver and excel reader instances
    private WebDriver driver;
    private ExcelFileManager testDataReader;

    // Declaring Page Objects that will be used throughout the test
    private Login loginPage;
    private Content newUI_allContentPage;

    @Test(priority = 1, description = "C83005 - Chrome: Dashboard : Rename a dashboard is working.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_Renamed"));
    }

    @Test(priority = 2, description = "C83007 - Chrome: Dashboard : Rename with special characters / numbers.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [Special Characters] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithSpecialCharacters_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_SpecialCharacters"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_SpecialCharacters_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage.Assert_DashboardExist(
		testDataReader.getCellData("Automation_Dashboard_Rename_SpecialCharacters_Renamed"));
    }

    @Test(priority = 3, description = "C83008 - Chrome: Dashboard : Rename with arabic name.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [Arabic] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithArabic_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_Arabic"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_Arabic_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage
		.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_Arabic_Renamed"));
    }

    @Test(priority = 4, description = "C83009 - Chrome: Dashboard : Rename with french letters.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [French] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithFrench_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_French"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_French_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage
		.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_French_Renamed"));
    }

    @Test(priority = 5, description = "C83010 - Chrome: Dashboard : Rename with Chinese letters.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [Chinese] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithChinese_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_Chinese"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_Chinese_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage
		.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_Chinese_Renamed"));
    }

    @Test(priority = 6, description = "C83016 - Chrome: Dashboard : Testing if no change done to the name.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename and I don't change the name. Then rename button will be dimmed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_NoRename_RenameButtonDimmed() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_NoRename"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renameButtonDisabled();
    }

    @Test(priority = 7, description = "C83018 - Chrome: Dashboard : if Rename bar is empty.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename and I don't change the name. Then rename button will be dimmed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WitnEmptyNameField_RenameButtonDimmed() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_NoRename"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(testDataReader.getCellData(""));
	newUI_allContentPage.assert_renameDashboard_renameButtonDisabled();
    }

    @Test(priority = 8, description = "C83057 - Chrome: Rename a dashboard window Layout.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename. Then I'll find that layout is correct.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_LayoutDisplayedCorrectly() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_NoRename"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_popupScreen_screenContentDisplayed();
    }

    @Test(priority = 9, description = "C83059 - Chrome: Rename a dashboard Cancel button.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename and I click cancel. Then dashboard will not be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_cancelRenaming() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_NoRename"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_Chinese_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Cancel");

	newUI_allContentPage.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_NoRename"));
    }

    @Test(priority = 10, description = "C83061 - Chrome: User can Rename a dashboard with a long Name.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [Long Name] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithLongName_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_LongName"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_LongName_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage
		.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_LongName_Renamed"));
    }

    @Test(priority = 11, description = "C83067 - Chrome: Verify that User can rename the dashboard with a Name contains Spaces.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [with spaces] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithSpaces_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_WithSpaces"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_WithSpaces_Renamed"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renameDashboard_renamed_confirmationMessage();
	newUI_allContentPage
		.Assert_DashboardExist(testDataReader.getCellData("Automation_Dashboard_Rename_WithSpaces_Renamed"));
    }

    @Test(priority = 12, description = "C83091 - Chrome: Verify that the User cannot Rename a dashboard with an existing Name.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [Duplicate] and I click on Rename. Then Dashboard will not be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_WithDuplicateName_ErrorMessageDisplayed() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_Duplicate1"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_typeNewDashboardName(
		testDataReader.getCellData("Automation_Dashboard_Rename_Duplicate2"));
	newUI_allContentPage.renameDashboard_popup_clickOnActionButtons("Rename");

	newUI_allContentPage.assert_renamedDashboard_duplicateName_errorMessageDisplayed();
    }

    @Test(priority = 13, description = "C83093 - Chrome: Verify that Close icon in 'Rename Dashboard' Window is working.")
    @Description("When I navigate to the content screen, and I click on dashboard properties -> Rename - write new dahsboard name [with spaces] and I click on Rename. Then Dashboard will be renamed.")
    @Severity(SeverityLevel.NORMAL)
    public void RenameDashboard_closeIcon_Working() {
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.navigate_toURL();

	newUI_allContentPage.click_dashboardFolder_properties_fromCardView(
		testDataReader.getCellData("Automation_Dashboard_Rename_CloseIcon"));
	newUI_allContentPage.Click_DashboardProperties_ManageDashboardButtons("Rename");

	newUI_allContentPage.renameDashboard_clickCloseIcon();
	newUI_allContentPage = new Content(driver);
	newUI_allContentPage.assert_renameDashboard_screenNotExist();
    }

    @BeforeClass
    public void beforeClass() {
	System.setProperty("testDataFilePath",
		System.getProperty("testDataFolderPath") + "renameDashboard_newUI/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	driver = BrowserFactory.getBrowser(testDataReader);

	loginPage = new Login(driver);
	loginPage.navigate_toURL();
	loginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
		testDataReader.getCellData("Password"));
    }
}
