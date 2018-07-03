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
import pageObjectModels.scheduler.Dashboards;

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
	Dashboards schedulerDashboardsPage;


	// Declaring public variables that will be shared between tests

	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 1, description = "C77025 - Firefox: Fresh Installation : Testing that the new Scheduler dashboard UI is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then schedule Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ScheduleDashboardScreenIsOpened()
	{		
		dashboardPage.scheduleDashboard_assert_DashboardScreenOpened();
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 2, description = "C77026 - Firefox: Fresh Installation: Testing that 'Job name' Area field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Job Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.scheduleDashboard_assert_JobNameField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Job Name");
	}
	
	//Prerequisite, Admin User + Dashboard Created + Job Already Created
	//In Progress - Need to check assertion issue
	@Test(priority = 3, description = "C77203 - Firefox: Fresh Installation: Testing that when enter an exists 'Job name', error message appears")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields with a duplicate job name and click send. Then an error message will be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_DoNotAcceptDuplicate() 
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
	public void Assert_JobNameField_DoNotAcceptNULL_ScheduleDashboard() 
	{		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_disabled();
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 5, description = "C77028 - Firefox: Fresh Installation: Testing that 'Description' field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Description' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JDescriptionField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.scheduleDashboard_assert_DescriptionField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Description");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 6, description = "C77029 - Firefox: Fresh Installation: Testing that 'Subject;=' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_subjectField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Subject");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 7, description = "C77030 - Firefox: Fresh Installation: Testing that 'Body Area' new field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that body field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BodyField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.scheduleDashboard_assert_bodyField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Body");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 8, description = "C77033 - Firefox: Fresh Installation: Testing that 'Hide Notification text' check box Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' is appeared beside the body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_Label_Displayed() 
	{		
		dashboardPage.sendDashboard_assert_labelsName_exist("Hide Notification Text");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 9, description = "C77034 - Firefox: Fresh Installation: Testing that 'Hide Notification Text' check box info is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' checkbox is appeared with enable/disable features.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_Checkbox_WorksCorrectly() 
	{		
		dashboardPage.sendDashboard_assert_HideNotificationText_checkbox_Unchecked();
		dashboardPage.sendDashboard_assert_Click_HideNotificationText_checkbox_checked();
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 10, description = "C77035 - Firefox: Fresh Installation: Testing that user can hover on 'Hide Notification Text' help")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' help [?] is appeared beside the body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_ToolTip_Displayed() 
	{		
		dashboardPage.sendDashboard_assert_HideNotificationText_toolTipIsDiplayed();
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 11, description = "C77037 - Firefox: Fresh Installation: Testing that 'To' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'To' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ToField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_labelsName_exist("To");
		dashboardPage.sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("To");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 12, description = "C77038 - Firefox: Fresh Installation: Testing that 'CC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Cc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_CcField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Cc");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 13, description = "C77039 - Firefox: Fresh Installation: Testing that 'BCC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Bcc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BccField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Bcc");
	}
	
	//Prerequisite, Admin User + Dashboard Created
	@Test(priority = 14, description = "C77042 - Firefox: Fresh Installation: Testing that 'Save changes' is correctly saving when removing 'emails'")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I add email in 'To' and schedule the dashboard and I go to the job and remove the email from 'To' and add it to the 'Cc' and save. and remove it from Cc and add it in bcc and click on save. Then save button is working normally and save previous changes.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobScreen_SaveChangesButton_WorksCorrectly() 
	{		
		schedulerDashboardsPage = new Dashboards(driver);
		dashboardPage.scheduleDashboard_addJobName(testDataReader.getCellData("JobName"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_allDashboardsTabIsSelected();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("JobName"));
		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("JobName"), testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("To", testDataReader.getCellData("Email"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Cc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();
		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("JobName"), testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("Cc", testDataReader.getCellData("Email"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Bcc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();
		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("JobName"), testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("Bcc", testDataReader.getCellData("Email"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Cc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();
		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("JobName"), testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("Email"));
	}

	@Test(priority = 15, description = "C77039 - Firefox: Fresh Installation: Testing that 'BCC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Bcc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_FileNameField_Displayed_ScheduleDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_FileNameFieldExist();
		dashboardPage.sendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(testDataReader.getCellData("DashboardName"));
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
