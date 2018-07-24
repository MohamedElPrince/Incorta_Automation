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
	String newScheduledJobName;

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 1, description = "C77025 - Firefox: Fresh Installation : Testing that the new Scheduler dashboard UI is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then schedule Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ScheduleDashboardScreenIsOpened() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_ScreenIsOpened();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 2, description = "C77026 - Firefox: Fresh Installation: Testing that 'Job name' Area field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Job Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_assert_JobNameField_exist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Job Name");
	}

	// Prerequisite, Admin User + Dashboard Created + Job Already Created
	@Test(priority = 3, description = "C77203 - Firefox: Fresh Installation: Testing that when enter an exists 'Job name', error message appears")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields with a duplicate job name and click send. Then an error message will be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_DoesNotAcceptDuplicate() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_addJobName(testDataReader.getCellData("JobName"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_assert_duplicateJobName_errorDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 4, description = "C77027 - Firefox: Fresh Installation: Testing that user can't save changes when he leaves 'Job name' blank")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields except Job Name. Then schadule button will be disabled")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_DoNotAcceptNULL_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_disabled();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 5, description = "C77028 - Firefox: Fresh Installation: Testing that 'Description' field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Description' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_DescriptionField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_assert_DescriptionField_exist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Description");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 6, description = "C77029 - Firefox: Fresh Installation: Testing that 'Subject' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_subjectField_exist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Subject");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 7, description = "C77030 - Firefox: Fresh Installation: Testing that 'Body Area' new field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that body field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BodyField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_assert_bodyField_exist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Body");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 8, description = "C77033 - Firefox: Fresh Installation: Testing that 'Hide Notification text' check box Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' is appeared beside the body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_Label_Displayed() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Hide Notification Text");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 9, description = "C77034 - Firefox: Fresh Installation: Testing that 'Hide Notification Text' check box info is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' checkbox is appeared with enable/disable features.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_Checkbox_WorksCorrectly() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked();
		dashboardPage.ScheduleSendDashboard_assert_Click_HideNotificationText_checkbox_checked();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 10, description = "C77035 - Firefox: Fresh Installation: Testing that user can hover on 'Hide Notification Text' help")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' help [?] is appeared beside the body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_ToolTip_Displayed() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_HideNotificationText_toolTipIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 11, description = "C77037 - Firefox: Fresh Installation: Testing that 'To' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'To' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ToField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("To");
		dashboardPage.ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("To");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 12, description = "C77038 - Firefox: Fresh Installation: Testing that 'CC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Cc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_CcField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Cc");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 13, description = "C77039 - Firefox: Fresh Installation: Testing that 'BCC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Bcc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BccField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Bcc");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 14, description = "C77042 - Firefox: Fresh Installation: Testing that 'Save changes' is correctly saving when removing 'emails'")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I add email in 'To' and schedule the dashboard and I go to the job and remove the email from 'To' and add it to the 'Cc' and save. and remove it from Cc and add it in bcc and click on save. Then save button is working normally and save previous changes.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobScreen_SaveChangesButton_WorksCorrectly() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_addJobName(testDataReader.getCellData("C77042JobName"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_allDashboardsTabIsSelected();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("C77042JobName"));
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"), testDataReader.getCellData("C77042JobName"));

		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("C77042JobName"),
				testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Click_AddMailRecipientsType("Cc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("C77042JobName"),
				testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Click_AddMailRecipientsType("Bcc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("C77042JobName"),
				testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Click_AddMailRecipientsType("Cc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("C77042JobName"),
				testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("Email"));
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 15, description = "C77043 - Firefox: Fresh Installation: Testing that 'File name' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'File Name' field appeared and It has Dashboard name by default.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_FileNameField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_FileNameFieldExist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(
				testDataReader.getCellData("DashboardName"));
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 16, description = "C77046 - Firefox: Fresh Installation: Testing that 'File name' should be disappear when user select HTML type")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I click HTML. Then I'll find that 'File Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_WhenSelectHTML_FileNameField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		dashboardPage.ScheduleSendDashboard_assert_FileNameFieldExist();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("File Name");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 17, description = "C77047 - Firefox: Fresh Installation: Testing that 'Append Timestamp' new field validation is appeared.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'File Name' field appeared and It has Dashboard name by default.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_AppendTimestamp_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.ScheduleSendDashboard_assert_labelsName_exist("Append Timestamp");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 18, description = "C77048_1 - Firefox: Fresh Installation: Testing that user can hover on 'Append time stamp' help.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select HTML and hover on 'Append time stamp'. Then I'll find that a specific message is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectHTML_HoverOnAppendTimeStamp_MessageDisplayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimeStamp_HelpIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 19, description = "C77048_2 - Firefox: Fresh Installation: Testing that user can hover on 'Append time stamp' help.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select XLSX and hover on 'Append time stamp'. Then I'll find that a specific message is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectXLSX_HoverOnAppendTimeStamp_MessageDisplayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimeStamp_HelpIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 20, description = "C77048_3 - Firefox: Fresh Installation: Testing that user can hover on 'Append time stamp' help.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select CSV and hover on 'Append time stamp'. Then I'll find that a specific message is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_HoverOnAppendTimeStamp_MessageDisplayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimeStamp_HelpIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	// Added JOB NAME to TC as it's mandatory field
	@Test(priority = 21, description = "C77052 - Firefox: Fresh Installation: Testing that the Dashboard Scheduler is Saved Successfully when adding the new fields.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I add fields 'subject,body,to and cc' and I click on save. Then I'll find that job is saved from the job screen.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_DashboardScheduler_SavedWhenAddingNewFields() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		String SubjectName = dashboardPage.ScheduleSendDashboard_AddSubjectName();
		String BodyName = dashboardPage.ScheduleSendDashboard_AddBodyName();
		
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Cc");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_allDashboardsTabIsSelected();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(JobName);
		schedulerDashboardsPage.JobScreen_Assert_SubjectNameIsDisplayed(SubjectName);
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(BodyName);
	}

	// Prerequisite, Admin User + Dashboard to create job + Job Created with [Name -
	// Description and Time]
	@Test(priority = 22, description = "C77053 - Firefox: Fresh Installation: Testing that user can edit Scheduler Dashboard.")
	@Description("When I navigate to the target job, and I click on it and I update any field and save changes. Then I'll find that fields is updated successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobScreen_FieldsUpdatedSuccessfully() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),testDataReader.getCellData("ScheduleJobName"));

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("ScheduleJobName"),
				testDataReader.getCellData("DashboardName"));

		newScheduledJobName = schedulerDashboardsPage.JobScreen_UpdateFields(
				testDataReader.getCellData("ScheduleJobDescription"), testDataReader.getCellData("ScheduleJobDate"),
				testDataReader.getCellData("ScheduleJobTime"), testDataReader.getCellData("ScheduleJobTimeZone"),
				testDataReader.getCellData("ScheduleJobNoRecurrence"));

		schedulerDashboardsPage.DashboardJob_ClickOnJob(newScheduledJobName,
				testDataReader.getCellData("DashboardName"));

		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(newScheduledJobName);
		schedulerDashboardsPage
				.JobScreen_Assert_DescriptionIsDisplayed(testDataReader.getCellData("ScheduleJobDescription"));
		schedulerDashboardsPage.JobScreen_Assert_JobDate(testDataReader.getCellData("ScheduleJobDate"));
		schedulerDashboardsPage.JobScreen_Assert_JobTime(testDataReader.getCellData("ScheduleJobTime"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\+03:00");
		schedulerDashboardsPage
				.JobScreen_Assert_JobRecurrence_Exist(testDataReader.getCellData("ScheduleJobNoRecurrence"));
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 23, description = "C77054 - Firefox: Fresh Installation: Testing that the Scheduler is working with Daily recurrence - HTML / CSV / XSLS.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select CSV and Daily recurrence option and I click on save changes. Then I'll find that job saved successfully with this options..")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_selectDailyRecurrence_SavedNormally() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.scheduleDashboard_AddRecurrence(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);
		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));

		schedulerDashboardsPage
				.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(JobName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 24, description = "C77055 - Firefox: Fresh Installation: Testing that the Scheduler is working with Weekly recurrence - HTML / CSV / XSLS.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select CSV and weekly recurrence option and I click on save changes. Then I'll find that job saved successfully with this options..")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_selectWeeklyRecurrence_SavedNormally() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.scheduleDashboard_AddRecurrence(testDataReader.getCellData("ScheduleJobWeeklyRecurrence"));
		dashboardPage.scheduleDashboard_SelectDays_WeeklyRecurrence("Mon");
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));

		schedulerDashboardsPage
				.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobWeeklyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(JobName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 25, description = "C77056 - Firefox: Fresh Installation: Testing that the Scheduler is working with Monthly recurrence - HTML / CSV / XSLS.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select CSV and Monthly recurrence option and I click on save changes. Then I'll find that job saved successfully with this options..")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_selectMonthlyRecurrence_SavedNormally() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.scheduleDashboard_AddRecurrence(testDataReader.getCellData("ScheduleJobMonthlyRecurrence"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));

		schedulerDashboardsPage
				.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobMonthlyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(JobName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 26, description = "C77057 - Firefox: Fresh Installation: Testing that the user can Schedule a dashboard 'No Recurrence'.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select xlsx and No recurrence option and I click on save changes. Then I'll find that job saved successfully with this options..")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_selectNoRecurrence_SavedNormally() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.scheduleDashboard_AddRecurrence(testDataReader.getCellData("ScheduleJobNoRecurrence"));
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.ScheduleDashboard_Assert_CompletedStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));

		schedulerDashboardsPage
				.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobNoRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(JobName);
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 27, description = "C77061 - Firefox: Fresh Installation: Testing that Subject with Long name does not make any errors.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I add long subject. Then I'll find that a job saved successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_LongSubject_SavedNormally_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddSubjectName(testDataReader.getCellData("LongSubjectName"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));

		schedulerDashboardsPage.JobScreen_Assert_SubjectNameIsDisplayed(testDataReader.getCellData("LongSubjectName"));
	}

	// Prerequisite, Admin User + Scheduler job Dashboard Created
	@Test(priority = 28, description = "C77169 - Firefox: Fresh Installation: Testing that the user can delete a Created dashboard Job.")
	@Description("When I navigate to the target Job, and I select job and click on delete selection. Then schedule dashboard is deleted successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Delete_ScheduleDashboard() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("DeleteScheduleDashboard"));
		schedulerDashboardsPage.ScheduleDashboard_Select_ScheduleJobs(testDataReader.getCellData("DashboardName"),
				testDataReader.getCellData("DeleteScheduleDashboard"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");

		schedulerDashboardsPage.ScheduleDashboard_Click_ConfirmUserDeletion_Suspend("Delete");
		schedulerDashboardsPage.ScheduleDashboard_Assert_JobNotExist(testDataReader.getCellData("DashboardName"),
				testDataReader.getCellData("DeleteScheduleDashboard"));
	}

	// Prerequisite, Admin User + Scheduler job Dashboard Created Active
	@Test(priority = 29, description = "C77170 - Firefox: Fresh Installation: Testing that the user can Suspend a dashboard job.")
	@Description("When I navigate to the target Job, and I click on Active beside the job and click on OK. Then schedule dashboard is suspended successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Suspend_ScheduleDashboard() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("SuspendScheduleDashboard"));
		schedulerDashboardsPage.ScheduleDashboard_clickOnStatus(testDataReader.getCellData("DashboardName"),
				testDataReader.getCellData("SuspendScheduleDashboard"), "Active");
		schedulerDashboardsPage.ScheduleDashboard_Click_Suspend_Ok();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Suspended");
		schedulerDashboardsPage.ScheduleDashboard_Assert_JobStatus(testDataReader.getCellData("DashboardName"),
				testDataReader.getCellData("SuspendScheduleDashboard"), "Suspended");
	}

	// Prerequisite, Admin User
	@Test(priority = 30, description = "C77171 - Firefox: Fresh Installation: Check dashboard Jobs menu.")
	@Description("When I navigate to the scheduler dashboard screen, and I click on the status. Then 4 options [All,Suspended,Active,Completed] will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobStatuses_Exist_ScheduleDashboard() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_StatusFiltersExist("Active");
		schedulerDashboardsPage.ScheduleDashboard_Assert_StatusFiltersExist("All");
		schedulerDashboardsPage.ScheduleDashboard_Assert_StatusFiltersExist("Suspended");
		schedulerDashboardsPage.ScheduleDashboard_Assert_StatusFiltersExist("Completed");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 31, description = "C77031 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Subject' name.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields including subject field and I click on schedule. Then Mail will be sent successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_SavedAndSent_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		String SubjectName = dashboardPage.ScheduleSendDashboard_AddSubjectName();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(SubjectName);
		// Need to check that mail is sent successfully with subject.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 32, description = "C77032 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Body Area'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields including body field and I click on schedule. Then Mail will be sent successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_bodyField_SavedAndSent_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		String BodyName = dashboardPage.ScheduleSendDashboard_AddBodyName();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(BodyName);
		// Need to check that mail is sent successfully with body.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 33, description = "C77036_1 - Firefox: Fresh Installation: Testing that the mail is received successful with/witout 'Hide Notification Text'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and leave 'hide notification text' enabled and I click on schedule. Then Mail will be sent successfully without the default notification.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationtext_Enabled_SavedAndSent_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_assert_Click_HideNotificationText_checkbox_checked();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_assert_HideNotificationText_checkbox_checked();
		// Need to check that mail is sent successfully without hide notification text.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 35, description = "C77036_2 - Firefox: Fresh Installation: Testing that the mail is received successful with/witout 'Hide Notification Text'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and leave 'hide notification text' disabled and I click on schedule. Then Mail will be sent successfully with the default notification.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationtext_Disabled_SavedAndSent_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked();
		// Need to check that mail is sent successfully with hide notification text.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 36, description = "C77040 - Firefox: Fresh Installation: Testing that 'Cc' on schedule dashboard does not give an error when 'To' is empty.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and CC field and leave To field empty and I click on schedule. Then Mail will be sent successfullyTo will not give an error.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_AddCCMail_ToMailIsEmpty_ToNotGivingAnError_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Cc");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_enabled();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		// Need to check that mail is sent successfully with cc.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 37, description = "C77041 - Firefox: Fresh Installation: Testing that 'Bcc' on schedule dashboard does not give an error when 'To' is empty.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and Bcc field and leave To field empty and I click on schedule. Then Mail will be sent successfullyTo will not give an error.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_AddBccMail_ToMailIsEmpty_ToNotGivingAnError_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("Bcc");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_enabled();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		// Need to check that mail is sent successfully with Bcc.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 38, description = "C77044_1 - Firefox: Fresh Installation: Testing that the mail is received successful with 'File name' for XLSX/ CSV files.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select CSV and I click on schedule. Then Mail will be sent successfully with file named with the file name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SelectCSV_MailReceived_NamedByFileName_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(
				testDataReader.getCellData("DashboardName"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		// Need to check that mail is sent successfully with CSV name is same as file
		// name in schedule screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 39, description = "C77044_2 - Firefox: Fresh Installation: Testing that the mail is received successful with 'File name' for XLSX/ CSV files.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select CSV and I click on schedule. Then Mail will be sent successfully with file named with the file name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SelectXLSX_MailReceived_NamedByFileName_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(
				testDataReader.getCellData("DashboardName"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		// Need to check that mail is sent successfully with xlsx name is same as file
		// name in schedule screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 40, description = "C77045_1 - Firefox: Fresh Installation: Testing that the mail is received successful with default 'File name' when user leaves it blank.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select xlsx and empty field filename I click on schedule. Then Mail will be sent successfully with file named with the dashboard name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_fileNameIsDashboardName_WhenLeaveItBlankInScheduleDashboard_selectXLSX() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleDashboard_TypeFileNameField("");

		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.jobScreen_Assert_fileNameField(testDataReader.getCellData("DashboardName"));

		// Need to check that mail is sent successfully with xlsx name is same as file
		// name in Job screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 41, description = "C77045_2 - Firefox: Fresh Installation: Testing that the mail is received successful with default 'File name' when user leaves it blank.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select CSV and empty field filename and I click on schedule. Then Mail will be sent successfully with file named with the dashboard name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_fileNameIsDashboardName_WhenLeaveItBlankInScheduleDashboard_selectCSV() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleDashboard_TypeFileNameField("");

		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.jobScreen_Assert_fileNameField(testDataReader.getCellData("DashboardName"));

		// Need to check that mail is sent successfully with xlsx name is same as file
		// name in Job screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 42, description = "C77049 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Time stamp' for XLSX.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select xlsx and select append time stamp and I click on schedule. Then Mail will be sent successfully with timestamp for xlsx.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_TimeStampForXLSX() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_assert_AppendTimestamp_checkbox_checked();
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");

		//Need to check that mail is sent successfully with XLSX with time stamp.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 43, description = "C77050 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Time stamp' for CSV.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select csv and select append time stamp and I click on schedule. Then Mail will be sent successfully with timestamp for xlsx.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_TimeStampForCSV() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked();

		dashboardPage.scheduleDashboard_Click_schedule();
		dashboardPage.scheduleDashboard_CSV_XLSX_ClickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_assert_AppendTimestamp_checkbox_checked();
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");

		//Need to check that mail is sent successfully with csv with time stamp.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 44, description = "C77058 - Firefox: Fresh Installation: Verify that when sending the Scheduler Email 'Subject and Body' are displayed correctly.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and I click on schedule. Then Mail will be sent successfully with subject and body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_BodyAndSubjectExistAndCorrect() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		String BodyText = dashboardPage.ScheduleSendDashboard_AddBodyName();
		String SubjectText = dashboardPage.ScheduleSendDashboard_AddSubjectName();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(BodyText);
		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(SubjectText);
		
		//Need to check that mail is sent successfully withbody and subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 45, description = "C77059 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Arabic Letters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add arabic letter in the subject and I click on schedule. Then Mail will be sent successfully with subject [Arabic Letters].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_ArabicLetterInSubject() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddSubjectName(testDataReader.getCellData("ArabicSubject"));

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(testDataReader.getCellData("ArabicSubject"));
		
		//Need to check that mail is sent successfully with arabic letter in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 46, description = "C77060 - Firefox: Fresh Installation: Testing that the mail is received successful with Subject contains 'Special characters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add special chars in the subject and I click on schedule. Then Mail will be sent successfully with subject [special chars].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_SpecialCharactersInSubject() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddSubjectName(testDataReader.getCellData("SpecialCharactersSubject"));

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		String replace = testDataReader.getCellData("SpecialCharactersSubject").replace("@", "\\@").replace("_", "\\_").replace("$", "\\$").replace("#", "\\#");

		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(replace);

		//Need to check that mail is sent successfully with special characters in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 47, description = "C77062 - Firefox: Fresh Installation: Testing that Body with very long message does not make any errors.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and very long body and I click on schedule. Then Mail will be sent successfully with the long body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_LongMessages_BodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddBodyName(testDataReader.getCellData("LongBody"));

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("LongBody"));

		//Need to check that mail is sent successfully with special characters in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 48, description = "C77063 - Firefox: Fresh Installation: Testing that Body with with Space and Enter does not make any errors.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and very long body and I click on schedule. Then Mail will be sent successfully with the long body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_MessagesWithSpacesAndEnter_BodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddBodyName(testDataReader.getCellData("BodyWithSpacesAndEnter"));

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("BodyWithSpacesAndEnter"));

		//Need to check that mail is sent successfully with special characters in subject.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 49, description = "C77064 - Firefox: Fresh Installation: Testing that the mail is received successful with Body contains 'Arabic letters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add arabic letter in the body and I click on schedule. Then Mail will be sent successfully with body [Arabic Letters].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_ArabicLetter_bodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddBodyName(testDataReader.getCellData("ArabicSubject"));

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("ArabicSubject"));
		
		//Need to check that mail is sent successfully with arabic letter in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 50, description = "C77065 - Firefox: Fresh Installation: Testing that the mail is received successful with Body contains 'Special Characters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add special chars in the body and I click on schedule. Then Mail will be sent successfully with body [special chars].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_SpecialCharacters_bodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_AddBodyName(testDataReader.getCellData("SpecialCharactersSubject"));

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		String replace = testDataReader.getCellData("SpecialCharactersSubject").replace("@", "\\@").replace("_", "\\_").replace("$", "\\$").replace("#", "\\#");

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(replace);

		//Need to check that mail is sent successfully with special characters in subject.
	}

	// Prerequisite, Admin User + Dashboard Created with prompts [Automation_Dashboard_Prompt_Filter]
	@Test(priority = 51, description = "C77201 - Firefox: Fresh Installation: Testing that the Scheduler is working with Prompt Filter.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields along with making prompt filter mandatory and I click on schedule. Then scheduler will work with prompt successfully and Mail will be sent successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_PromptMandatory_schedulerDashboard() 
	{
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("AutomationDashboardPrompts"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("AutomationDashboardPrompts"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardPrompts"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}

	// Prerequisite, Admin User + Dashboard Created with prompts [Automation_Dashboard_Prompt_Filter]
	@Test(priority = 52, description = "C77225 - Firefox: Fresh Installation: Check that prompt filter applied not sent in the email in case hide notification check box applied.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and check hide notification text along with making prompt filter mandatory and I click on schedule. Then scheduler will work with prompt successfully and Mail will be sent successfully without notification.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_PromptMandatory_withoutNotificationText_schedulerDashboard() 
	{
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("AutomationDashboardPrompts"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("AutomationDashboardPrompts"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.ScheduleSendDashboard_assert_Click_HideNotificationText_checkbox_checked();

		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardPrompts"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail without notification text.
	}

	// Prerequisite, Admin User + Dashboard Created with applied filter [Automation_Dashboard_Applied_Filter]
	@Test(priority = 53, description = "C77202 - Firefox: Fresh Installation: Testing that the Scheduler is working with Applied Filter.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields along with making applied filter mandatory and I click on schedule. Then scheduler will work with prompt successfully and Mail will be sent successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_AppliedFilter_schedulerDashboard() 
	{
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("AutomationDashboardAppliedFilter"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("AutomationDashboardAppliedFilter"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_AddRecurrence("Daily");
	    dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardAppliedFilter"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}

	// Prerequisite, Admin User + Dashboard Created with presentation variables [AutomationDashboardPresentationVariables]
	@Test(priority = 54, description = "C77209 - Firefox: Fresh Installation: Testing that the Scheduler is working with Presentation Variables.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields along with making presentation variables and I click on schedule. Then scheduler will work with prompt successfully and Mail will be sent successfully with filter.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_PresentationVariables_schedulerDashboard() 
	{
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("AutomationDashboardPresentationVariables"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("AutomationDashboardPresentationVariables"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_AddRecurrence("Daily");
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardPresentationVariables"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}

	// Prerequisite, Admin User + Dashboard Created with presentation variables [AutomationDashboardPresentationVariables]
	@Test(priority = 55, description = "C77219 - Firefox: Fresh Installation: Testing that the Scheduler is working with Bookmark.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields along with filter and bookmark and I click on schedule. Then scheduler will work with filter successfully and Mail will be sent successfully with filter.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_Bookmark_schedulerDashboard() 
	{
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("AutomationDashboardBookmark"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("AutomationDashboardBookmark"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_AddRecurrence("Daily");
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardBookmark"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}
	
	
	//******************************Old Schedule Dash board Test cases******************************
	//************************in progress************************************************
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 60, description = "C54221 - Firefox: Fresh Installation: Testing that the mail is received successful with Body contains 'Special Characters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add time zone and repeate type and number and I click on schedule. Then Mail will be sent successfully with time zone and repeate type and number.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_DashboardSchedueled_DailyRecurrence_Minutes() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_AddRecurrence(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		dashboardPage.scheduleDashboard_dailyRecurrence_RepeatType("Minute", "20");
		dashboardPage.JobScreen_Select_JobTimeZone(testDataReader.getCellData("ScheduleJobTimeZone"));
		dashboardPage.scheduleDashboard_Click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone(testDataReader.getCellData("ScheduleJobTimeZone"));
		schedulerDashboardsPage.JobScreen_Assert_dailyRecurrence_RepeatType("Minute");
		
		//Need to check that mail is sent successfully with special characters in subject.
	}
	
	public void navigate_to_scheduleDashboard()
	{
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
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "sendDashboard/TestData.xlsx");
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
