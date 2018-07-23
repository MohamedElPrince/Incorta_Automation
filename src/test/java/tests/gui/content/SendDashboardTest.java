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

public class SendDashboardTest {
	
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

	@Test(priority = 1, description = "C76805 - Firefox: Fresh Installation : Testing that the new 'Send Dashboard' Screen is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Send Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_Screen_IsOpened()
	{		
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_ScreenIsOpened();
	}
	
	@Test(priority = 2, description = "C76806 - Firefox: Fresh Installation: Testing that 'Subject' field is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_Displayed_SendDashboard() 
	{		
		navigate_to_sendDashboard();
		dashboardPage.ScheduleSendDashboard_assert_subjectField_exist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Subject");
	}
	
	@Test(priority = 3, description = "C76807 - Firefox: Fresh Installation: Testing that 'Body' field is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that body field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BodyField_Displayed_SendDashBoard() 
	{		
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_assert_bodyField_exist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Body");
	}
  	
	@Test(priority = 4, description = "C76808 - Firefox: Fresh Installation: Testing that 'Hide Notification Text' Check box is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that 'Hide Notification Text' field exist along with a tooltip and a checkbox is Unchecked.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationTextField_Displayed_SendDashBoard() 
	{	
		navigate_to_sendDashboard();
		dashboardPage.ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Hide Notification Text");
		dashboardPage.ScheduleSendDashboard_assert_HideNotificationText_toolTipIsDisplayed();
	}
	
	@Test(priority = 5, description = "C76809 - Firefox: Fresh Installation: Testing that when selecting HTML Option no File name can be added")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select HTML option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_HTML_Option_InSendDashBoard() 
	{
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.ScheduleSendDashboard_assert_FileNameFieldExist();
		dashboardPage.sendDashboard_assert_TypeOfEmailDescription("html");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
	}
	
	@Test(priority = 6, description = "C76810 - Firefox: Fresh Installation: Verify that when Selecting 'XLSX', the option of fixed file name is displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select XLSX option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_XLSX_Option_InSendDashBoard() 
	{		
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.ScheduleSendDashboard_assert_FileNameFieldExist();
		dashboardPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
		dashboardPage.sendDashboard_assert_TypeOfEmailDescription("xlsx");
	}

	@Test(priority = 7, description = "C76811 - Firefox: Fresh Installation: Verify that when selecting 'CSV', the option of fixed file name is displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select CSV option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_CSV_Option_InSendDashBoard()
	{	
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.ScheduleSendDashboard_assert_FileNameFieldExist();
		dashboardPage.sendDashboard_assert_TypeOfEmailDescription("csv");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
	}
	
	@Test(priority = 8, description = "C76812 - Firefox: Fresh Installation: Verify that 'To' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'To' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_To_MailReceipents_Displayed()
	{		
		navigate_to_sendDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("To");
		dashboardPage.ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("To");
	}
	
	@Test(priority = 9, description = "C76813 - Firefox: Fresh Installation: Verify that 'Cc' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'Cc' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Cc_MailReceipents_Displayed()
	{		
		navigate_to_sendDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Cc");
	}
	
	@Test(priority = 10, description = "C76814 - Firefox: Fresh Installation: Verify that 'Bcc' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'Bcc' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Bcc_MailReceipents_Displayed() 
	{	
		navigate_to_sendDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Bcc");
	}
	
	//Prerequisites: Admin user + dashboard created
	@Test(priority = 11, description = "C76815 - Firefox: Fresh installation: Verify that Subject is not mandatory field")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email without subject, Then It should be sent successfully and subject displayed successfully with dashboard name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Subject_NotMandatory()
	{
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("DashboardName"));
		//Need to check that mail is sent successfully with DashboardName as subject
	}
	
	@Test(priority = 12, description = "C76816 - Firefox: Fresh Installation: Verify that the User can send a dashboard with subject")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with specific subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_subject() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		String subject = dashboardPage.ScheduleSendDashboard_AddSubjectNameAutomated();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_Subject_Value(subject);
		//Need to check that mail is sent successfully with correct subject name
	}
	
	@Test(priority = 13, description = "C76817 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains Arabic Letters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with Arabic subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_Arabic_subject() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("Arabic"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("Arabic"));
		//Need to check that mail is sent successfully with Arabic subject name
	}
	
	@Test(priority = 14, description = "C76818 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains Special Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with special characters at subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_SpecialCharacters_subject() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("SpecialCharacters"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_SpecialCharacters_Subject(testDataReader.getCellData("SpecialCharacters"));
		//Need to check that mail is sent successfully with Special Characters subject name
	}
	
	@Test(priority = 15, description = "C76819 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains any non English Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with non English characters at subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_NonEnglish_subject() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("NonEnglish"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("NonEnglish"));
		//Need to check that mail is sent successfully with non English Characters subject name
	}
	
	@Test(priority = 16, description = "C76820 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains Numbers")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with numbers at subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_numbers_subject() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("Numbers"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("Numbers"));
		//Need to check that mail is sent successfully with Numbers subject name
	}
	
	@Test(priority = 17, description = "C76821 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Body")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body, then It should be sent successfully and body displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_body() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		String body = dashboardPage.ScheduleSendDashboard_Add_Body_Automated();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(body);
		//Need to check that mail is sent successfully with correct body
		
	}
	
	@Test(priority = 18, description = "C76822 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Body contains Special Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body containing special characters, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_body_specialCharacters() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.ScheduleSendDashboard_AddBody(testDataReader.getCellData("SpecialCharacters"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.Assert_SpecialCharacters_Body(testDataReader.getCellData("SpecialCharacters"));
		//Need to check that mail is sent successfully with Special Characters in the body
	}
	
	@Test(priority = 19, description = "C76823 - Firefox: Fresh Installation: Verify that the user can send a dashboard with body contains Arabic letters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with Arabic body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_Arabic_body() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.ScheduleSendDashboard_AddBody(testDataReader.getCellData("Arabic"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("Arabic"));
		//Need to check that mail is sent successfully with Arabic body
	}
	
	@Test(priority = 20, description = "C76824 - Firefox: Fresh Installation: Verify that the user can send a dashboard with body Contains Numbers")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with numbers in the body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_numbers_body() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.ScheduleSendDashboard_AddBody(testDataReader.getCellData("Numbers"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("Numbers"));
		//Need to check that mail is sent successfully with body containing numbers
	}
	
	@Test(priority = 21, description = "C76826 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Body contains non English characters (French , Chinese ..)")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with non-English characters in the body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_NonEnglish_body() {
		
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		
		navigate_to_sendDashboard();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.ScheduleSendDashboard_AddBody(testDataReader.getCellData("NonEnglish"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.Click_Send_Dashboard();
		
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(JobName);
		schedulerDashboardsPage.Click_On_LastJobName();
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("NonEnglish"));
		//Need to check that mail is sent successfully with body containing NonEnglish characters
	}
	
	public void navigate_to_sendDashboard() {
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
