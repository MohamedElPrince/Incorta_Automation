package tests.gui.content;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.scheduler.Dashboards;

public class SendDashboardTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	NewUI_Login loginPage;
	AllContent allContentPage;
	Skeleton mainPage;
	AllContent_Dashboard dashboardPage;
	Dashboards schedulerDashboardsPage;

	// Declaring public variables that will be shared between tests

	@Test(priority = 1, description = "C76805 - Firefox: Fresh Installation : Testing that the new 'Send Dashboard' Screen is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Send Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_Screen_IsOpened() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_screenIsOpened();
	}

	@Test(priority = 2, description = "C76806 - Firefox: Fresh Installation: Testing that 'Subject' field is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_Displayed_SendDashboard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_subjectField_exist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Subject");
	}

	@Test(priority = 3, description = "C76807 - Firefox: Fresh Installation: Testing that 'Body' field is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that body field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BodyField_Displayed_SendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_assert_bodyField_exist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Body");
	}

	@Test(priority = 4, description = "C76808 - Firefox: Fresh Installation: Testing that 'Hide Notification Text' Check box is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that 'Hide Notification Text' field exist along with a tooltip and a checkbox is Unchecked.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationTextField_Displayed_SendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_hideNotificationText_checkbox_unchecked();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Hide Notification Text");
		dashboardPage.scheduleSendDashboard_assert_hideNotificationText_toolTipIsDisplayed();
	}

	@Test(priority = 5, description = "C76809 - Firefox: Fresh Installation: Testing that when selecting HTML Option no File name can be added")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select HTML option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_HTML_Option_InSendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.scheduleSendDashboard_assert_fileNameFieldExist();
		dashboardPage.sendDashboard_assert_typeOfEmailDescription("html");
		dashboardPage.scheduleSendDashboard_assert_appendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
	}

	@Test(priority = 6, description = "C76810 - Firefox: Fresh Installation: Verify that when Selecting 'XLSX', the option of fixed file name is displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select XLSX option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_XLSX_Option_InSendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.scheduleSendDashboard_assert_fileNameFieldExist();
		dashboardPage.scheduleSendDashboard_assert_appendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
		dashboardPage.sendDashboard_assert_typeOfEmailDescription("xlsx");
	}

	@Test(priority = 7, description = "C76811 - Firefox: Fresh Installation: Verify that when selecting 'CSV', the option of fixed file name is displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select CSV option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_CSV_Option_InSendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.scheduleSendDashboard_assert_fileNameFieldExist();
		dashboardPage.sendDashboard_assert_typeOfEmailDescription("csv");
		dashboardPage.scheduleSendDashboard_assert_appendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
	}

	@Test(priority = 8, description = "C76812 - Firefox: Fresh Installation: Verify that 'To' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'To' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_To_MailReceipents_Displayed() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("To");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("To");
	}

	@Test(priority = 9, description = "C76813 - Firefox: Fresh Installation: Verify that 'Cc' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'Cc' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Cc_MailReceipents_Displayed() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Cc");
	}

	@Test(priority = 10, description = "C76814 - Firefox: Fresh Installation: Verify that 'Bcc' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'Bcc' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Bcc_MailReceipents_Displayed() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Bcc");
	}

	// Prerequisites: Admin user + dashboard created with name
	// "Automation_Dashboard_SendDashboard" +
	// Automation_Dashboard_SendDashboard is already sent before and completed job
	// exists with name
	// starts with System generated"
	@Test(priority = 11, description = "C76815 - Firefox: Fresh installation: Verify that Subject is not mandatory field")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email without subject, Then It should be sent successfully and subject displayed successfully with dashboard name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Subject_NotMandatory() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();
		navigate_to_sendDashboard();

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("DashboardName"));
		// Need to check that mail is sent successfully with DashboardName as subject
	}

	@Test(priority = 12, description = "C76816 - Firefox: Fresh Installation: Verify that the User can send a dashboard with subject")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with specific subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_subject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		String subject = dashboardPage.scheduleSendDashboard_addSubjectName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(subject);
		// Need to check that mail is sent successfully with correct subject name
	}

	@Test(priority = 13, description = "C76817 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains Arabic Letters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with Arabic subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_Arabic_subject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("Arabic"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("Arabic"));
		// Need to check that mail is sent successfully with Arabic subject name
	}

	@Test(priority = 14, description = "C76818 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains Special Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with special characters at subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_SpecialCharacters_subject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("SpecialCharacters"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_SpecialCharacters_Subject(testDataReader.getCellData("SpecialCharacters"));
		// Need to check that mail is sent successfully with Special Characters subject
		// name
	}

	@Test(priority = 15, description = "C76819 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains any non English Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with non English characters at subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_NonEnglish_subject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("NonEnglish"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("NonEnglish"));
		// Need to check that mail is sent successfully with non English Characters
		// subject name
	}

	@Test(priority = 16, description = "C76820 - Firefox: Fresh Installation: Verify that the user can send a dashboard with subject contains Numbers")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with numbers at subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_numbers_subject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("Numbers"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("Numbers"));
		// Need to check that mail is sent successfully with Numbers subject name
	}

	@Test(priority = 17, description = "C76821 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Body")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body, then It should be sent successfully and body displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		String body = dashboardPage.sendDashboard_addBodyName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(body);
		// Need to check that mail is sent successfully with correct body

	}

	@Test(priority = 18, description = "C76822 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Body contains Special Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body containing special characters, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_body_specialCharacters() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName(testDataReader.getCellData("SpecialCharacters"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_SpecialCharacters_Body(testDataReader.getCellData("SpecialCharacters"));
		// Need to check that mail is sent successfully with Special Characters in the
		// body
	}

	@Test(priority = 19, description = "C76823 - Firefox: Fresh Installation: Verify that the user can send a dashboard with body contains Arabic letters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with Arabic body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_Arabic_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName(testDataReader.getCellData("Arabic"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("Arabic"));
		// Need to check that mail is sent successfully with Arabic body
	}

	@Test(priority = 20, description = "C76824 - Firefox: Fresh Installation: Verify that the user can send a dashboard with body Contains Numbers")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with numbers in the body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_numbers_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName(testDataReader.getCellData("Numbers"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("Numbers"));
		// Need to check that mail is sent successfully with body containing numbers
	}

	@Test(priority = 21, description = "C76826 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Body contains non English characters (French , Chinese ..)")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with non-English characters in the body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_NonEnglish_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName(testDataReader.getCellData("NonEnglish"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("NonEnglish"));
		// Need to check that mail is sent successfully with body containing NonEnglish
		// characters
	}

	@Test(priority = 22, description = "C76825 - Firefox: Fresh Installation: Verify that the user can send a dashboard with \"Body\" contains Enter")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body contains Enter, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_bodyWithEnter() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName(testDataReader.getCellData("BodyWithEnter"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("BodyWithEnter"));
		// Need to check that mail is sent successfully with body contains Enter
	}

	@Test(priority = 23, description = "C76829 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Empty Body")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with empty body, then It should be sent successfully without body")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_without_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed("");
		// Need to check that mail is sent successfully with Empty body
	}

	@Test(priority = 24, description = "C76830 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Empty Body and Subject")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with empty body and empty subject, then It should be sent successfully without body and with dashboard name as email subject")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_without_bodyAndSubject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed("");
		// Need to check that mail is sent successfully with Empty body and subject
	}

	@Test(priority = 25, description = "C76834 - Firefox: Fresh Installation: Verify that the user can add a long Subject")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with long subject, Then It should be sent successfully and subject displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_long_subject() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addSubjectField(testDataReader.getCellData("LongSubject"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.Assert_Subject_Value(testDataReader.getCellData("LongSubject"));
		// Need to check that mail is sent successfully with long subject
	}

	@Test(priority = 26, description = "C76835 - Firefox: Fresh Installation: Verify that the user can add a long Body")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with long body, then It should be sent successfully and body is displayed successfully")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_long_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName(testDataReader.getCellData("LongBody"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("LongBody"));
		// Need to check that mail is sent successfully with long body
	}

	@Test(priority = 27, description = "C76827 - Firefox: Fresh Installation Verify that the user can send an email with Body and Hide notification TXT is checked")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body and Hide notification TXT is checked, then It should be sent successfully and body is displayed successfully without notification message")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_body_with_HideNotification() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		String body = dashboardPage.sendDashboard_addBodyName();
		dashboardPage.scheduleSendDashboard_click_hideNotificationText_checkbox();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(body);
		schedulerDashboardsPage.Assert_HideNotificationText_checkbox_checked();
		// Need to check that mail is sent successfully with body and without
		// notification
	}

	@Test(priority = 28, description = "C76828 - Firefox: Fresh Installation: Verify that he user can send an email with Body and Hide notification text is unchecked")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with body and Hide notification TXT is unchecked, then It should be sent successfully and body is displayed successfully with notification message")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_body_without_HideNotification() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		String body = dashboardPage.sendDashboard_addBodyName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(body);
		schedulerDashboardsPage.Assert_HideNotificationText_checkbox_unchecked();
		// Need to check that mail is sent successfully with body and with notification
		// message
	}

	@Test(priority = 29, description = "C76831 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Empty body and Hide notification text is checked")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with empty body and Hide notification TXT is checked, then It should be sent successfully and body is displayed successfully without notification message")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_without_body_without_HideNotification() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_click_hideNotificationText_checkbox();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed("");
		schedulerDashboardsPage.Assert_HideNotificationText_checkbox_checked();
		// Need to check that mail is sent successfully without body and without
		// notification message
	}

	@Test(priority = 30, description = "C76832 - Firefox: Fresh Installation: Verify that the user can send a dashboard with Empty body and Hide notification Text is unchecked")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with empty body and Hide notification TXT is unchecked, then It should be sent successfully and body is displayed successfully with notification message")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_without_body_with_HideNotification() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed("");
		schedulerDashboardsPage.Assert_HideNotificationText_checkbox_unchecked();
		// Need to check that mail is sent successfully without body and with
		// notification message
	}

	@Test(priority = 31, description = "C76833 - Firefox: Fresh Installation: Verify that the user can copy and paste text in the \"Body\" Area")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. And sending email with paseted text in the body, then It should be sent successfully and body is displayed successfully with copied text")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_copyAndPasteText_body() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();

		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_addBodyName();
		dashboardPage.copy_body_text();
		dashboardPage.paste_text_body();
		String body = dashboardPage.GetBodyText();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(body);
		// Need to check that mail is sent successfully with pasted text
	}

	@Test(priority = 32, description = "C76836 - Firefox: Fresh Installation: Verify that When Selecting XLSX the user can add \"File name\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with XLSX File type, then It should be sent successfully with file type XLSX and correct file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_XLSX_FileType() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.uncheck_appendTimestamp();
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked();

		// Need to check that mail is sent successfully with file type xlsx and correct
		// file name
	}

	@Test(priority = 33, description = "C76837 - Firefox: Fresh Installation: Verify that When Selecting XLSX the user can add \"File name\" With Arabic Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with XLSX File type, then It should be sent successfully with file type XLSX and Arabic file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_XLSX_ArabicFileName() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_addFileName(testDataReader.getCellData("Arabic"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("Arabic"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		// Need to check that mail is sent successfully with file type xlsx and arabic
		// file name with timestamp
	}

	@Test(priority = 34, description = "C76838 - Firefox: Fresh Installation: Verify that when selecting XLSX the user can amend timestamp to filename")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with XLSX File type, then It should be sent successfully with file type XLSX and timestamp appended to file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_XLSX_AppendTimestamp() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();
		// Need to check that mail is sent successfully with file type xlsx and correct
		// file name with timestamp appended to file name
	}

	@Test(priority = 35, description = "C76839 - Firefox: Fresh Installation: Verify that when selecting XLSX the dashboard name will be added by default in File Name field")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with XLSX File type, then It should be sent successfully with file type XLSX and default file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_XLSX_DefaultFileName() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.uncheck_appendTimestamp();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked();

		// Need to check that mail is sent successfully with file type xlsx and default
		// file name
	}

	@Test(priority = 36, description = "C76840 - Firefox: Fresh Installation: Verify that when selecting XLSX verify that File name has same validation done when saving excel locally")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and enter XLSX File name with special characters,then validation error message should be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_XLSX_InvalidFileName() {
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_addFileName(testDataReader.getCellData("FileNameWithSpecialCharacters"));
		dashboardPage.sendDashboard_assert_sendButton_disabled();
		dashboardPage.assert_invalidFileName_errorMessage();
	}

	@Test(priority = 37, description = "C76841 - Firefox: Fresh Installation: Verify that when selecting XLSX and amend timestamp option the dashboard name will be dashboardname_Timestamp")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with XLSX File type, then It should be sent successfully with file type XLSX and default file name with timestamp")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_XLSX_DefaultFileName_withTimestamp() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		// Need to check that mail is sent successfully with file type xlsx and default
		// file name with timestamp
	}

	@Test(priority = 38, description = "C76842 - Firefox: Fresh Installation: Verify that When Selecting CSV the user can add \"File name\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with CSV File type, then It should be sent successfully with file type CSV and correct file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_CSV_FileType() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.uncheck_appendTimestamp();
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked();

		// Need to check that mail is sent successfully with file type csv and correct
		// file name
	}

	@Test(priority = 39, description = "C76843 - Firefox: Fresh Installation: Verify that When Selecting CSV the user can add \"File name\" With Arabic Characters")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with CSV File type, then It should be sent successfully with file type XLSX and Arabic file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_CSV_ArabicFileName() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_addFileName(testDataReader.getCellData("Arabic"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("Arabic"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		// Need to check that mail is sent successfully with file type csv and arabic
		// file name with timestamp
	}

	@Test(priority = 40, description = "C76844 - Firefox: Fresh Installation: Verify that when selecting CSV the user can amend timestamp to filename")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with CSV File type, then It should be sent successfully with file type CSV and timestamp appended to file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_CSV_AppendTimestamp() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();
		// Need to check that mail is sent successfully with file type csv and correct
		// file name with timestamp appended to file name
	}

	@Test(priority = 41, description = "C76845 - Firefox: Fresh Installation: Verify that when selecting CSV the dashboard name will be added by default in File Name field")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with CSV File type, then It should be sent successfully with file type CSV and default file name")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_CSV_DefaultFileName() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.uncheck_appendTimestamp();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked();

		// Need to check that mail is sent successfully with file type csv and default
		// file name
	}

	@Test(priority = 42, description = "C76846 - Firefox: Fresh Installation: Verify that when selecting CSV verify that File name has same validation done when saving CSV file locally")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and enter CSV File name with special characters,then validation error message should be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_with_CSV_InvalidFileName() {
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_addFileName(testDataReader.getCellData("FileNameWithSpecialCharacters"));
		dashboardPage.sendDashboard_assert_sendButton_disabled();
		dashboardPage.assert_invalidFileName_errorMessage();
	}

	@Test(priority = 43, description = "C76847 - Firefox: Fresh Installation: Verify that when selecting CSV and amend timestamp option the dashboard name will be dashboard name_Timestamp")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and sending email with CSV File type, then It should be sent successfully with file type CSV and default file name with timestamp")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_email_CSV_DefaultFileName_withTimestamp() {

		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		// Need to check that mail is sent successfully with file type csv and default
		// file name with timestamp
	}

	@Test(priority = 44, description = "C76848 - Firefox: Fresh Installation:Verify that the user can send dashboard adding users in \"TO\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, then It \"TO\" field is displayed and user can add email to send dashboard to")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_ToField() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("To");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("To");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("EmailAddress"));

		// Need to check that mail is sent successfully to user email added to "To"
		// field and "Cc"\"Bcc" is empty
	}

	@Test(priority = 45, description = "C76849 - Firefox: Fresh Installation: Verify that the user can send dashboard adding users in \"CC\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, then It \"CC\" field is displayed and user can add email to send dashboard to")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_CcField() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Cc");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("EmailAddress"));

		// Need to check that mail is sent successfully to user email added to "Cc"
		// field and "To"\"Bcc" is empty
	}

	@Test(priority = 46, description = "C76850 - Firefox: Fresh Installation: Verify that the user can send dashboard adding users in \"Bcc\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, then It \"BCC\" field is displayed and user can add email to send dashboard to")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_BccField() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Bcc");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("EmailAddress"));

		// Need to check that mail is sent successfully to user email added to "Bcc"
		// field and "To"\"Cc" is empty
	}

	// Prerequisites:
	// - Admin user
	// - dashboard created with name "Automation_Dashboard_SendDashboard"
	// - Automation_Dashboard_SendDashboard is already sent before and completed job
	// exists with name
	// starts with System generated"
	// - group of certain users created and exists

	@Test(priority = 47, description = "C76851 - Firefox: Fresh Installation:Verify that the user can send dashboard adding Groups in \"TO\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, then It \"TO\" field is displayed and user can add email to send dashboard to certain group")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_ToField_GroupOfUsers() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("To");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("To");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("GroupToSendTo"));

		// Need to check that mail is sent successfully to the group of useres added to
		// "To" field and "Cc"\"Bcc" is empty
	}

	@Test(priority = 48, description = "C76852 - Firefox: Fresh Installation:Verify that the user can send dashboard adding Groups in \"CC\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, then It \"CC\" field is displayed and user can add email to send dashboard to to certain group")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_CcField_GroupOfUsers() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Cc");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("GroupToSendTo"));

		// Need to check that mail is sent successfully to the group of useres added to
		// "Cc" field and "To"\"Bcc" is empty
	}

	@Test(priority = 49, description = "C76853 - Firefox: Fresh Installation:Verify that the user can send dashboard adding Groups in \"Bcc\"")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, then It \"BCC\" field is displayed and user can add email to send dashboard to to certain group")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_BccField_GroupOfUsers() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Bcc");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("GroupToSendTo"));

		// Need to check that mail is sent successfully to the group of useres added to
		// "Bcc" field and "To"\"Cc" is empty
	}

	@Test(priority = 50, description = "C76854 - Firefox: Fresh Installation: Verify the tool tip of Amend time stamp is working in case of CSV")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and choose CSV as File type, and hover on help icon of Append Timestamp\n"
			+ ",then tool tip text should be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_HoverOnAppendTimeStamp_MessageDisplayed_SendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleSendDashboard_assert_appendTimeStamp_helpIsDisplayed();
	}

	@Test(priority = 51, description = "C76855 - Firefox: Fresh Installation: Verify the tool tip of Amend time stamp is working in case of XLSX")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and choose XLSX as File type, and hover on help icon of Append Timestamp\n"
			+ ",then tool tip text should be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectXLSX_HoverOnAppendTimeStamp_MessageDisplayed_SendDashBoard() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleSendDashboard_assert_appendTimeStamp_helpIsDisplayed();
	}

	@Test(priority = 52, description = "C76856 - Firefox: Fresh Installation: Verify that the user can add combination of Users and Groups in To / CC / Bcc")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and add users and groups in TO, CC and BCC, then mail should be sent correctly to correct users")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_Combination_GroupsAndUsers() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("GroupToSendTo"));

		// Need to check that mail is sent successfully to groups and useres added to
		// "Bcc", "To" and "Cc"
	}

	// Prerequisites:
	// - Admin user
	// - dashboard created with name "Automation_Dashboard_SendDashboard"
	// - Automation_Dashboard_SendDashboard is already sent before and completed job
	// exists with name
	// starts with System generated"
	// - group of certain users created and exists
	// - shared folder is configured through admin portal

	@Test(priority = 53, description = "C76857-1 - Firefox: Fresh Installation: User can send dashboard to shared folder as HTML")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select HTML as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_HTML_ToField() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_assert_placeholder_ToField();
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));

		// Need to check that mail is sent successfully to sharedFolder added to "To"
	}

	@Test(priority = 54, description = "C76857-2 - Firefox: Fresh Installation: User can send dashboard to shared folder as HTML")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select HTML as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_HTML_CcField() {
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.SendDashboard_assert_placeholder_CcBccField();
		dashboardPage.SendDashboard_TypeEmail(testDataReader.getCellData("SharedFolder"));
		dashboardPage.SendDashboard_assert_no_searchResult();
	}

	@Test(priority = 55, description = "C76857-3 - Firefox: Fresh Installation: User can send dashboard to shared folder as HTML")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select HTML as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_HTML_BccField() {
		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_assert_placeholder_CcBccField();
		dashboardPage.SendDashboard_TypeEmail(testDataReader.getCellData("SharedFolder"));
		dashboardPage.SendDashboard_assert_no_searchResult();
	}

	@Test(priority = 56, description = "C76858-1 - Firefox: Fresh Installation: User can send dashboard to shared folder as XLSX")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select XLSX as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_XLSX_ToField() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_assert_placeholder_ToField();
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");

		// Need to check that XLSX file is sent successfully to sharedFolder added to
		// "To"
	}

	@Test(priority = 57, description = "C76858-2 - Firefox: Fresh Installation: User can send dashboard to shared folder as XLSX")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select XLSX as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_XLSX_CcField() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.SendDashboard_assert_placeholder_CcBccField();
		dashboardPage.SendDashboard_TypeEmail(testDataReader.getCellData("SharedFolder"));
		dashboardPage.SendDashboard_assert_no_searchResult();
	}

	@Test(priority = 58, description = "C76858-3 - Firefox: Fresh Installation: User can send dashboard to shared folder as XLSX")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select XLSX as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_XLSX_BccField() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_assert_placeholder_CcBccField();
		dashboardPage.SendDashboard_TypeEmail(testDataReader.getCellData("SharedFolder"));
		dashboardPage.SendDashboard_assert_no_searchResult();
	}

	@Test(priority = 59, description = "C76862-1 - Firefox: Fresh Installation: User can send dashboard to shared folder as CSV")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select CSV as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_CSV_ToField() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_assert_placeholder_ToField();
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Cc", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");

		// Need to check that CSV file is sent successfully to sharedFolder added to
		// "To"
	}

	@Test(priority = 60, description = "C76862-2 - Firefox: Fresh Installation: User can send dashboard to shared folder as CSV")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select CSV as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_CSV_CcField() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.SendDashboard_assert_placeholder_CcBccField();
		dashboardPage.SendDashboard_TypeEmail(testDataReader.getCellData("SharedFolder"));
		dashboardPage.SendDashboard_assert_no_searchResult();
	}

	@Test(priority = 61, description = "C76862-3 - Firefox: Fresh Installation: User can send dashboard to shared folder as CSV")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select CSV as file type, and add Shared Folder in TO, CC and BCC,"
			+ "then mail should be sent correctly to shared folder in To & not appear in search result of CC & BCC")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_CSV_BccField() {
		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_assert_placeholder_CcBccField();
		dashboardPage.SendDashboard_TypeEmail(testDataReader.getCellData("SharedFolder"));
		dashboardPage.SendDashboard_assert_no_searchResult();
	}

	@Test(priority = 62, description = "C76859 - Firefox: Fresh Installation: User can send dashboard to shared folder as XLSX with fixed file name and no time stamp")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select XLSX as file type, and uncheck append timestamp and add Shared Folder in TO"
			+ "then mail should be sent correctly to shared folder in To with file name and no timestamp")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_XLSX_ToField_noTimestamp() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.uncheck_appendTimestamp();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked();

		// Need to check that XLSX file is sent successfully to sharedFolder added to
		// "To" field with automated file name and no timestamp
	}

	@Test(priority = 63, description = "C76860 - Firefox: Fresh Installation: User can send dashboard to shared folder as XLSX with fixed file name and with timestamp")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select XLSX as file type, and check append timestamp and add Shared Folder in TO"
			+ "then mail should be sent correctly to shared folder in To with file name and timestamp")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_XLSX_ToField_withTimestamp() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		// Need to check that XLSX file is sent successfully to sharedFolder added to
		// "To" field with automated file name and with timestamp
		// note: automated file name has timestamp, and timestamp is checked too, so
		// when manually test this, you are supposed to find two timestamps
	}

	@Test(priority = 64, description = "C76863 - Firefox: Fresh Installation: User can send dashboard to shared folder as CSV with fixed file name and no time stamp")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select CSV as file type, and uncheck append timestamp and add Shared Folder in TO"
			+ "then mail should be sent correctly to shared folder in To with file name and no timestamp")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_CSV_ToField_noTimestamp() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.uncheck_appendTimestamp();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked();

		// Need to check that CSV file is sent successfully to sharedFolder added to
		// "To" field with automated file name and no timestamp
	}

	@Test(priority = 65, description = "C76864 - Firefox: Fresh Installation: User can send dashboard to shared folder as CSV with fixed file name and with timestamp")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and select CSV as file type, and check append timestamp and add Shared Folder in TO"
			+ "then mail should be sent correctly to shared folder in To with file name and timestamp")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_SharedFolder_CSV_ToField_withTimestamp() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		String FileName = dashboardPage.sendDashboard_automated_addFileName();
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.Assert_FileName(FileName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
		schedulerDashboardsPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		// Need to check that CSV file is sent successfully to sharedFolder added to
		// "To" field with automated file name and with timestamp
		// note: automated file name has timestamp, and timestamp is checked too, so
		// when manually test this, you are supposed to find two timestamps
	}

	@Test(priority = 66, description = "C76866 - Firefox: Fresh Installation: User can add combination of Users , Groups , and Shared Folder")
	@Description("When I navigate to the target dashboard, and I click on send dashboard, and add users, groups and shared folder in TO, CC and BCC, then mail should be sent correctly to correct users")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_Combination_GroupsUsersSharedFolders() {
		String LastJobName = navigateToSchedulerPage_CompletedFilter_getLastJob();

		navigate_to_sendDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("SharedFolder"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.sendDashboard_typeEmailAndClickAdd(testDataReader.getCellData("EmailAddress"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.SendDashboard_TypeEmailAndSelectFirstSearchResult(testDataReader.getCellData("GroupToSendTo"));
		dashboardPage.sendDashboard_assert_sendButton_enabled();
		dashboardPage.click_send_dashboard();

		navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(LastJobName);

		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("SharedFolder"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("GroupToSendTo"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("EmailAddress"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("GroupToSendTo"));

		// Need to check that mail is sent successfully to groups and useres and shared
		// folder added to "Bcc", "To" and "Cc"
	}

	public void navigateToSchedulerPage_CompletedFilter_assertAndClickLastJob(String previousLastJob) {
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.Assert_lastJobName(previousLastJob);
		schedulerDashboardsPage.Click_On_LastJobName();
	}

	public String navigateToSchedulerPage_CompletedFilter_getLastJob() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		String JobName = schedulerDashboardsPage.GetLastJobName();
		return JobName;
	}

	public void navigate_to_sendDashboard() {
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("DashboardName"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("DashboardName"));

		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Send");
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "sendDashboard/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
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
