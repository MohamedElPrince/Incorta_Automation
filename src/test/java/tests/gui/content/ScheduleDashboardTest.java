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

	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 1, description = "C77025 - Firefox: Fresh Installation : Testing that the new Scheduler dashboard UI is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then schedule Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void AssertScheduleDashboardScreenIsOpened()
	{		
		dashboardPage.scheduleDashboard_assert_DashboardScreenOpened();
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 2, description = "C77026 - Firefox: Fresh Installation: Testing that 'Job name' Area field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Job Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void AssertThatJobNameFieldDisplayedInScheduleDashBoard() 
	{		
		dashboardPage.scheduleDashboard_assert_JobNameField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Job Name");
	}
	
	//Prerequisite, Admin User + Dashboard Created + Job Already Created
	//In Progress - Need to check assertion issue
	@Test(priority = 3, description = "C77203 - Firefox: Fresh Installation: Testing that when enter an exists 'Job name', error message appears")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields with a duplicate job name and click send. Then an error message will be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void AssertThatJobNameFieldDoNotAcceptDuplicate() 
	{		
		dashboardPage.scheduleDashboard_addJobName(testDataReader.getCellData("JobName"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_assert_duplicateJobName_errorDisplayed();
	}

	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 4, description = "C77027 - Firefox: Fresh Installation: Testing that user can't save changes when he leaves 'Job name' blank")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields except Job Name. Then schadule button will be disabled")
	@Severity(SeverityLevel.NORMAL)
	public void AssertThatJobNameFieldDoNotAcceptDuplicateInScheduleDashboard() 
	{		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_disabled();
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 5, description = "C77028 - Firefox: Fresh Installation: Testing that 'Description' field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Description' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void AssertThatJDescriptionFieldDisplayedInScheduleDashBoard() 
	{		
		dashboardPage.scheduleDashboard_assert_DescriptionField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Description");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 6, description = "C77029 - Firefox: Fresh Installation: Testing that \"Subject\" Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void AssertThatSubjectFieldDisplayedInScheduleDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_subjectField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Subject");
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
		mainPage.Select_fromDropdownMenu("Schedule");
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
