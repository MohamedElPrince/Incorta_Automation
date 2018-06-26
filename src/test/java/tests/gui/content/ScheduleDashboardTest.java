package tests.gui.content;

import org.testng.annotations.Test;
import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

public class ScheduleDashboardTest {
	
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login loginPage;
	AllContent allContentPage;
	Skeleton mainPage;
	AllContent_Dashboard dashboardPage;

	// Declaring public variables that will be shared between tests

	@Test(priority = 1, description = "C77025 - Firefox: Fresh Installation : Testing that the new Scheduler dashboard UI is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then schedule Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void AssertScheduleDashboardScreenIsOpened()
	{		
		dashboardPage.scheduleDashboard_assert_DashboardScreenOpened();
	}
	
	@Test(priority = 2, description = "C77026 - Firefox: Fresh Installation: Testing that 'Job name' Area field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Job Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void AssertThatSubjectFieldDisplayedInSendDashBoard() 
	{		
		dashboardPage.scheduleDashboard_assert_JobNameField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Job Name");
	}
	
	
	@BeforeMethod
	public void beforeMethod(){
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("DashboardName"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("DashboardName"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Send");
	}
	
	@BeforeClass
  	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "sendDashboard/TestData.xlsx");
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
