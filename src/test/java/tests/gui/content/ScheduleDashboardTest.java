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
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.scheduler.Dashboards;

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
		dashboardPage.scheduleSendDashboard_assert_screenIsOpened();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 2, description = "C77026 - Firefox: Fresh Installation: Testing that 'Job name' Area field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Job Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_assert_jobNameField_exist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Job Name");
	}

	// Prerequisite, Admin User + Dashboard Created + Job Already Created
	@Test(priority = 3, description = "C77203 - Firefox: Fresh Installation: Testing that when enter an exists 'Job name', error message appears")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields with a duplicate job name and click send. Then an error message will be displayed")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_DoesNotAcceptDuplicate() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_addJobName(testDataReader.getCellData("Automation_Scheduler_DuplicateJobName"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_assert_duplicateJobName_errorDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 4, description = "C77027 - Firefox: Fresh Installation: Testing that user can't save changes when he leaves 'Job name' blank")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. and add all mandatory fields except Job Name. Then schadule button will be disabled")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobNameField_DoNotAcceptNULL_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_disabled();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 5, description = "C77028 - Firefox: Fresh Installation: Testing that 'Description' field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Description' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_DescriptionField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_assert_DescriptionField_exist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Description");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 6, description = "C77029 - Firefox: Fresh Installation: Testing that 'Subject' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_subjectField_exist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Subject");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 7, description = "C77030 - Firefox: Fresh Installation: Testing that 'Body Area' new field functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that body field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BodyField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_assert_bodyField_exist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Body");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 8, description = "C77033 - Firefox: Fresh Installation: Testing that 'Hide Notification text' check box Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' is appeared beside the body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_Label_Displayed() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Hide Notification Text");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 9, description = "C77034 - Firefox: Fresh Installation: Testing that 'Hide Notification Text' check box info is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' checkbox is appeared with enable/disable features.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_Checkbox_WorksCorrectly() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_hideNotificationText_checkbox_unchecked();
		dashboardPage.scheduleSendDashboard_assert_click_hideNotificationText_checkbox_checked();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 10, description = "C77035 - Firefox: Fresh Installation: Testing that user can hover on 'Hide Notification Text' help")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Hide Notification Text' help [?] is appeared beside the body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationText_ToolTip_Displayed() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_hideNotificationText_toolTipIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 11, description = "C77037 - Firefox: Fresh Installation: Testing that 'To' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'To' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_ToField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("To");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("To");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 12, description = "C77038 - Firefox: Fresh Installation: Testing that 'CC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Cc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_CcField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Cc");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 13, description = "C77039 - Firefox: Fresh Installation: Testing that 'BCC' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'Bcc' field appeared.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BccField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed("Bcc");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 14, description = "C77042 - Firefox: Fresh Installation: Testing that 'Save changes' is correctly saving when removing 'emails'")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I add email in 'To' and schedule the dashboard and I go to the job and remove the email from 'To' and add it to the 'Cc' and save. and remove it from Cc and add it in bcc and click on save. Then save button is working normally and save previous changes.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_JobScreen_SaveChangesButton_WorksCorrectly() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleDashboard_addJobName(testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_allDashboardsTabIsSelected();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"));
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"), testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"));

		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"),
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Click_AddMailRecipientsType("Cc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"),
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Click_AddMailRecipientsType("Bcc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"),
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_RemoveEmail_Button("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Click_AddMailRecipientsType("Cc");
		schedulerDashboardsPage.JobScreen_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("Automation_Scheduler_SaveChnages_JobName"),
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("Bcc", testDataReader.getCellData("Email"));
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 15, description = "C77043 - Firefox: Fresh Installation: Testing that 'File name' Area new field Functionality is appeared")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'File Name' field appeared and It has Dashboard name by default.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_FileNameField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_fileNameFieldExist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 16, description = "C77046 - Firefox: Fresh Installation: Testing that 'File name' should be disappear when user select HTML type")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I click HTML. Then I'll find that 'File Name' field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_WhenSelectHTML_FileNameField_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		dashboardPage.scheduleSendDashboard_assert_fileNameFieldExist();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("File Name");
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 17, description = "C77047 - Firefox: Fresh Installation: Testing that 'Append Timestamp' new field validation is appeared.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard. Then I'll find that 'File Name' field appeared and It has Dashboard name by default.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_AppendTimestamp_Displayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_assert_labelsName_exist("Append Timestamp");
		dashboardPage.scheduleSendDashboard_assert_appendTimestamp_checkbox_checked();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 18, description = "C77048_1 - Firefox: Fresh Installation: Testing that user can hover on 'Append time stamp' help.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select HTML and hover on 'Append time stamp'. Then I'll find that a specific message is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectHTML_HoverOnAppendTimeStamp_MessageDisplayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		dashboardPage.scheduleSendDashboard_assert_appendTimeStamp_helpIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 19, description = "C77048_2 - Firefox: Fresh Installation: Testing that user can hover on 'Append time stamp' help.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select XLSX and hover on 'Append time stamp'. Then I'll find that a specific message is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectXLSX_HoverOnAppendTimeStamp_MessageDisplayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleSendDashboard_assert_appendTimeStamp_helpIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 20, description = "C77048_3 - Firefox: Fresh Installation: Testing that user can hover on 'Append time stamp' help.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I select CSV and hover on 'Append time stamp'. Then I'll find that a specific message is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_selectCSV_HoverOnAppendTimeStamp_MessageDisplayed_ScheduleDashBoard() {
		navigate_to_scheduleDashboard();
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleSendDashboard_assert_appendTimeStamp_helpIsDisplayed();
	}

	// Prerequisite, Admin User + Dashboard Created
	// Added JOB NAME to TC as it's mandatory field
	@Test(priority = 21, description = "C77052 - Firefox: Fresh Installation: Testing that the Dashboard Scheduler is Saved Successfully when adding the new fields.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I add fields 'subject,body,to and cc' and I click on save. Then I'll find that job is saved from the job screen.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_DashboardScheduler_SavedWhenAddingNewFields() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		String SubjectName = dashboardPage.scheduleSendDashboard_addSubjectName();
		String BodyName = dashboardPage.scheduleDashboard_addBodyName();
		
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_allDashboardsTabIsSelected();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
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
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),testDataReader.getCellData("Automation_Scheduler_UpdateJobName"));

		schedulerDashboardsPage.DashboardJob_ClickOnJob(testDataReader.getCellData("Automation_Scheduler_UpdateJobName"),
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		newScheduledJobName = schedulerDashboardsPage.JobScreen_UpdateFields(
				testDataReader.getCellData("ScheduleJobDescription"), testDataReader.getCellData("ScheduleJobDate"),
				testDataReader.getCellData("ScheduleJobTime"), testDataReader.getCellData("ScheduleJobPositiveTimeZone"),
				testDataReader.getCellData("ScheduleJobNoRecurrence"));

		schedulerDashboardsPage.DashboardJob_ClickOnJob(newScheduledJobName,
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));

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
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);
		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));

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
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobWeeklyRecurrence"));
		dashboardPage.scheduleDashboard_selectDays_weeklyRecurrence("Mon");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		schedulerDashboardsPage
				.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobWeeklyRecurrence"));
		schedulerDashboardsPage.JobScreen_SelectDays_WeeklyRecurrence("Mon");
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
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobMonthlyRecurrence"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));

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
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobNoRecurrence"));
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Completed");
		schedulerDashboardsPage.ScheduleDashboard_Assert_CompletedStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));

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
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_addSubjectName(testDataReader.getCellData("LongSubjectName"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		schedulerDashboardsPage.JobScreen_Assert_SubjectNameIsDisplayed(testDataReader.getCellData("LongSubjectName"));
	}

	// Prerequisite, Admin User + Scheduler job Dashboard Created
	@Test(priority = 28, description = "C77169 - Firefox: Fresh Installation: Testing that the user can delete a Created dashboard Job.")
	@Description("When I navigate to the target Job, and I select job and click on delete selection. Then schedule dashboard is deleted successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Delete_ScheduleDashboard() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("Automation_Schedule_DeleteJob"));
		schedulerDashboardsPage.ScheduleDashboard_Select_ScheduleJobs(testDataReader.getCellData("Automation_Scheduler_DashboardName"),
				testDataReader.getCellData("Automation_Schedule_DeleteJob"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");

		schedulerDashboardsPage.ScheduleDashboard_Click_ConfirmUserDeletion_Suspend("Delete");
		schedulerDashboardsPage.ScheduleDashboard_Assert_JobNotExist(testDataReader.getCellData("Automation_Scheduler_DashboardName"),
				testDataReader.getCellData("Automation_Schedule_DeleteJob"));
	}

	// Prerequisite, Admin User + Scheduler job Dashboard Created Active
	@Test(priority = 29, description = "C77170 - Firefox: Fresh Installation: Testing that the user can Suspend a dashboard job.")
	@Description("When I navigate to the target Job, and I click on Active beside the job and click on OK. Then schedule dashboard is suspended successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Suspend_ScheduleDashboard() {
		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(testDataReader.getCellData("Automation_Schedule_SuspendJob"));
		schedulerDashboardsPage.ScheduleDashboard_clickOnStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),
				testDataReader.getCellData("Automation_Schedule_SuspendJob"), "Active");
		schedulerDashboardsPage.ScheduleDashboard_Click_Suspend_Ok();
		schedulerDashboardsPage.ScheduleDashboard_StatusFilter_SelectFilter("Suspended");
		schedulerDashboardsPage.ScheduleDashboard_Assert_JobStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),
				testDataReader.getCellData("Automation_Schedule_SuspendJob"), "Suspended");
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
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		String SubjectName = dashboardPage.scheduleSendDashboard_addSubjectName();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
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
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		String BodyName = dashboardPage.scheduleDashboard_addBodyName();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
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
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_assert_click_hideNotificationText_checkbox_checked();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_assert_HideNotificationText_checkbox_checked();
		// Need to check that mail is sent successfully without hide notification text.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 34, description = "C77036_2 - Firefox: Fresh Installation: Testing that the mail is received successful with/witout 'Hide Notification Text'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and leave 'hide notification text' disabled and I click on schedule. Then Mail will be sent successfully with the default notification.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationtext_Disabled_SavedAndSent_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_assert_hideNotificationText_checkbox_unchecked();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked();
		// Need to check that mail is sent successfully with hide notification text.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 35, description = "C77040 - Firefox: Fresh Installation: Testing that 'Cc' on schedule dashboard does not give an error when 'To' is empty.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and CC field and leave To field empty and I click on schedule. Then Mail will be sent successfullyTo will not give an error.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_AddCCMail_ToMailIsEmpty_ToNotGivingAnError_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("Cc");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_enabled();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Cc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		// Need to check that mail is sent successfully with cc.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 36, description = "C77041 - Firefox: Fresh Installation: Testing that 'Bcc' on schedule dashboard does not give an error when 'To' is empty.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and Bcc field and leave To field empty and I click on schedule. Then Mail will be sent successfullyTo will not give an error.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_AddBccMail_ToMailIsEmpty_ToNotGivingAnError_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("Bcc");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_assert_scheduleButton_enabled();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("Bcc", testDataReader.getCellData("Email"));
		schedulerDashboardsPage.JobScreen_Assert_EmailIsNotExist("To", testDataReader.getCellData("Email"));
		// Need to check that mail is sent successfully with Bcc.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 37, description = "C77044_1 - Firefox: Fresh Installation: Testing that the mail is received successful with 'File name' for XLSX/ CSV files.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select CSV and I click on schedule. Then Mail will be sent successfully with file named with the file name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SelectCSV_MailReceived_NamedByFileName_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		// Need to check that mail is sent successfully with CSV name is same as file
		// name in schedule screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 38, description = "C77044_2 - Firefox: Fresh Installation: Testing that the mail is received successful with 'File name' for XLSX/ CSV files.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select CSV and I click on schedule. Then Mail will be sent successfully with file named with the file name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SelectXLSX_MailReceived_NamedByFileName_ScheduleDashboard() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(
				testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		// Need to check that mail is sent successfully with xlsx name is same as file
		// name in schedule screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 39, description = "C77045_1 - Firefox: Fresh Installation: Testing that the mail is received successful with default 'File name' when user leaves it blank.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select xlsx and empty field filename I click on schedule. Then Mail will be sent successfully with file named with the dashboard name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_fileNameIsDashboardName_WhenLeaveItBlankInScheduleDashboard_selectXLSX() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleDashboard_typeFileNameField("");

		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.jobScreen_Assert_fileNameField(testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		// Need to check that mail is sent successfully with xlsx name is same as file
		// name in Job screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 40, description = "C77045_2 - Firefox: Fresh Installation: Testing that the mail is received successful with default 'File name' when user leaves it blank.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select CSV and empty field filename and I click on schedule. Then Mail will be sent successfully with file named with the dashboard name.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_fileNameIsDashboardName_WhenLeaveItBlankInScheduleDashboard_selectCSV() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleDashboard_typeFileNameField("");

		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.jobScreen_Assert_fileNameField(testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		// Need to check that mail is sent successfully with xlsx name is same as file
		// name in Job screen.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 41, description = "C77049 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Time stamp' for XLSX.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select xlsx and select append time stamp and I click on schedule. Then Mail will be sent successfully with timestamp for xlsx.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_TimeStampForXLSX() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("xlsx");
		dashboardPage.scheduleSendDashboard_assert_appendTimestamp_checkbox_checked();

		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_assert_AppendTimestamp_checkbox_checked();
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("xlsx");

		//Need to check that mail is sent successfully with XLSX with time stamp.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 42, description = "	 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Time stamp' for CSV.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and select csv and select append time stamp and I click on schedule. Then Mail will be sent successfully with timestamp for xlsx.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_TimeStampForCSV() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_selectOutputFormat("csv");
		dashboardPage.scheduleSendDashboard_assert_appendTimestamp_checkbox_checked();

		dashboardPage.scheduleDashboard_click_schedule();
		dashboardPage.scheduleDashboard_csv_xlsx_clickOK();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_assert_AppendTimestamp_checkbox_checked();
		schedulerDashboardsPage.JobScreen_Assert_OutputFormat("csv");

		//Need to check that mail is sent successfully with csv with time stamp.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 43, description = "C77058 - Firefox: Fresh Installation: Verify that when sending the Scheduler Email 'Subject and Body' are displayed correctly.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and I click on schedule. Then Mail will be sent successfully with subject and body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_BodyAndSubjectExistAndCorrect() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		String BodyText = dashboardPage.scheduleDashboard_addBodyName();
		String SubjectText = dashboardPage.scheduleSendDashboard_addSubjectName();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(BodyText);
		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(SubjectText);
		
		//Need to check that mail is sent successfully withbody and subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 44, description = "C77059 - Firefox: Fresh Installation: Testing that the mail is received successful with 'Arabic Letters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add arabic letter in the subject and I click on schedule. Then Mail will be sent successfully with subject [Arabic Letters].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_ArabicLetterInSubject() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_addSubjectName(testDataReader.getCellData("ArabicSubject"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(testDataReader.getCellData("ArabicSubject"));
		
		//Need to check that mail is sent successfully with arabic letter in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 45, description = "C77060 - Firefox: Fresh Installation: Testing that the mail is received successful with Subject contains 'Special characters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add special chars in the subject and I click on schedule. Then Mail will be sent successfully with subject [special chars].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_SpecialCharactersInSubject() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_addSubjectName(testDataReader.getCellData("SpecialCharactersSubject"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		String replace = testDataReader.getCellData("SpecialCharactersSubject").replace("@", "\\@").replace("_", "\\_").replace("$", "\\$").replace("#", "\\#");

		schedulerDashboardsPage.JobScreen_Assert_SubjectTextIsDisplayed(replace);

		//Need to check that mail is sent successfully with special characters in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 46, description = "C77062 - Firefox: Fresh Installation: Testing that Body with very long message does not make any errors.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and very long body and I click on schedule. Then Mail will be sent successfully with the long body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_LongMessages_BodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addBodyName(testDataReader.getCellData("LongBody"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("LongBody"));

		//Need to check that mail is sent successfully with special characters in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 47, description = "C77063 - Firefox: Fresh Installation: Testing that Body with with Space and Enter does not make any errors.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and very long body and I click on schedule. Then Mail will be sent successfully with the long body.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_MessagesWithSpacesAndEnter_BodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addBodyName(testDataReader.getCellData("BodyWithSpacesAndEnter"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("BodyWithSpacesAndEnter"));

		//Need to check that mail is sent successfully with special characters in subject.
	}

	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 48, description = "C77064 - Firefox: Fresh Installation: Testing that the mail is received successful with Body contains 'Arabic letters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add arabic letter in the body and I click on schedule. Then Mail will be sent successfully with body [Arabic Letters].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_ArabicLetter_bodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addBodyName(testDataReader.getCellData("ArabicSubject"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(testDataReader.getCellData("ArabicSubject"));
		
		//Need to check that mail is sent successfully with arabic letter in subject.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 49, description = "C77065 - Firefox: Fresh Installation: Testing that the mail is received successful with Body contains 'Special Characters'.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add special chars in the body and I click on schedule. Then Mail will be sent successfully with body [special chars].")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_SpecialCharacters_bodyField() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addBodyName(testDataReader.getCellData("SpecialCharactersSubject"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		String replace = testDataReader.getCellData("SpecialCharactersSubject").replace("@", "\\@").replace("_", "\\_").replace("$", "\\$").replace("#", "\\#");

		schedulerDashboardsPage.JobScreen_Assert_BodyTextIsDisplayed(replace);

		//Need to check that mail is sent successfully with special characters in subject.
	}

	// Prerequisite, Admin User + Dashboard Created with prompts [Automation_Dashboard_Prompt_Filter]
	@Test(priority = 50, description = "C77201 - Firefox: Fresh Installation: Testing that the Scheduler is working with Prompt Filter.")
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
		dashboardPage.assert_dashboardName(testDataReader.getCellData("AutomationDashboardPrompts"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardPrompts"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}

	// Prerequisite, Admin User + Dashboard Created with prompts [Automation_Dashboard_Prompt_Filter]
	@Test(priority = 51, description = "C77225 - Firefox: Fresh Installation: Check that prompt filter applied not sent in the email in case hide notification check box applied.")
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
		dashboardPage.assert_dashboardName(testDataReader.getCellData("AutomationDashboardPrompts"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleSendDashboard_assert_click_hideNotificationText_checkbox_checked();

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardPrompts"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail without notification text.
	}

	// Prerequisite, Admin User + Dashboard Created with applied filter [Automation_Dashboard_Applied_Filter]
	@Test(priority = 52, description = "C77202 - Firefox: Fresh Installation: Testing that the Scheduler is working with Applied Filter.")
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
		dashboardPage.assert_dashboardName(testDataReader.getCellData("AutomationDashboardAppliedFilter"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addRecurrence("Daily");
	    dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardAppliedFilter"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}

	// Prerequisite, Admin User + Dashboard Created with presentation variables [AutomationDashboardPresentationVariables]
	@Test(priority = 53, description = "C77209 - Firefox: Fresh Installation: Testing that the Scheduler is working with Presentation Variables.")
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
		dashboardPage.assert_dashboardName(testDataReader.getCellData("AutomationDashboardPresentationVariables"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addRecurrence("Daily");
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardPresentationVariables"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}

	// Prerequisite, Admin User + Dashboard Created with presentation variables [AutomationDashboardPresentationVariables]
	@Test(priority = 54, description = "C77219 - Firefox: Fresh Installation: Testing that the Scheduler is working with Bookmark.")
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
		dashboardPage.assert_dashboardName(testDataReader.getCellData("AutomationDashboardBookmark"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addRecurrence("Daily");
		dashboardPage.scheduleSendDashboard_selectOutputFormat("html");
		
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("AutomationDashboardBookmark"),JobName);

		//Need to check that dashboard is sent successfully filtered in mail.
	}
	
	//******************************Old Schedule Dash board Test cases******************************
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 55, description = "C54221 - Fresh: Oracle: Java Sun: Dashboard Scheduler: Daily Every x min: Testing that: Schedule is working properly.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add time zone and repeate type min and number and I click on schedule. Then Mail will be sent successfully with time zone and repeate type Minute and number of minutes.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_DashboardSchedueled_DailyRecurrence_Minutes() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		dashboardPage.scheduleDashboard_dailyRecurrence_repeatType("Minute", "20");
		dashboardPage.scheduleDashboard_select_jobTimeZone(testDataReader.getCellData("ScheduleJobPositiveTimeZone"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);
		
		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\+03:00");
		schedulerDashboardsPage.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_dailyRecurrence_RepeatType_NumOfMin("Minute","20");
		schedulerDashboardsPage.JobScreen_Assert_Repeat_NoEnd_Checked();
		schedulerDashboardsPage.JobScreen_Assert_EmailExist("To", testDataReader.getCellData("Email"));
		//Need to check that mail is sent successfully with the right intervals in minutes.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 56, description = "C54232 - Fresh: Oracle: Java Sun: Dashboard Scheduler: Daily Every x hour: Testing that: Schedule is working properly.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and add time zone and repeate type hour and number and I click on schedule. Then Mail will be sent successfully with time zone and repeate type Hour and number of Hours.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_DashboardSchedueled_DailyRecurrence_Hours() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		dashboardPage.scheduleDashboard_dailyRecurrence_repeatType("Hour", "20");
		dashboardPage.scheduleDashboard_select_jobTimeZone(testDataReader.getCellData("ScheduleJobPositiveTimeZone"));
		dashboardPage.scheduleDashboard_repeate_selectEndBy(testDataReader.getCellData("ScheduleJobDate"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\+03:00");
		schedulerDashboardsPage.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobDailyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_dailyRecurrence_RepeatType_NumOfMin("Hour","20");
		schedulerDashboardsPage.scheduleDashboard_Assert_Repeate_EndByChecked_DateSelected(testDataReader.getCellData("ScheduleJobDate"));

		//Need to check that mail is sent successfully with the right intervals in Hours.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 57, description = "C54223 - Fresh: Oracle: Java Sun: Dashboard Scheduler: Monthly: Testing that: Schedule is working properly.")
	@Description("When I navigate to the scheduler dashboard screen, and I fill all fields and repeate type Monthley and start with specific day and I click on schedule. Then Mail will be sent successfully with time zone and repeate type Monthley.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_MailSent_DashboardSchedueled_monthlyRecurrence_Weeks() 
	{
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.scheduleDashboard_addRecurrence(testDataReader.getCellData("ScheduleJobMonthlyRecurrence"));
		dashboardPage.scheduleDashboard_monthlyRecurrence_selectType_2ndOption("Week", "2nd", "Sun", "11");
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));
		dashboardPage.scheduleDashboard_select_jobTimeZone(testDataReader.getCellData("ScheduleJobPositiveTimeZone"));
		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		schedulerDashboardsPage.JobScreen_Assert_JobRecurrence_Selected(testDataReader.getCellData("ScheduleJobMonthlyRecurrence"));
		schedulerDashboardsPage.JobScreen_Assert_JobNameIsDisplayed(JobName);
		schedulerDashboardsPage.JobScreen_Assert_monthlyRecurrence_selectType_2ndOption("Week", "2", "Sun", "11");
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\+03:00");
		//Need to check that mail is sent successfully with the right intervals in Hours.
	}
	
	// Prerequisite, Admin User + Dashboard Created
	@Test(priority = 58, description = "C77051 - Firefox: Fresh Installation: Testing that Scheduler is working with different Timezones.")
	@Description("When I navigate to the target dashboard, and I click on schedule dashboard and I updatet time zone and I click on save and from job screen update it again and click on save. Then next run [time zone]will be changed with every save.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_DashboardScheduler_workingWithDifferentTimeZones() {
		navigate_to_scheduleDashboard();
		String JobName = dashboardPage.scheduleDashboard_addJobName();
		dashboardPage.scheduleDashboard_select_jobTimeZone(testDataReader.getCellData("ScheduleJobPositiveTimeZone"));
		
		dashboardPage.sendDashboard_click_addMailRecipientsType("To");
		dashboardPage.scheduleDashboard_typeEmailAndClickAdd(testDataReader.getCellData("Email"));

		dashboardPage.scheduleDashboard_click_schedule();

		schedulerDashboardsPage = new Dashboards(driver);
		schedulerDashboardsPage.Navigate_toURL();
		schedulerDashboardsPage.Assert_allDashboardsTabIsSelected();
		schedulerDashboardsPage.Assert_jobNameIsDisplayed(JobName);
		schedulerDashboardsPage.ScheduleDashboard_Assert_ActiveStatus(testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);
		schedulerDashboardsPage.Assert_NextRunTimeZoneCorrect("GMT\\+03:00",testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\+03:00");
		schedulerDashboardsPage.JobScreen_Select_JobTimeZone(testDataReader.getCellData("ScheduleJobNegativeTimeZone"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();
		schedulerDashboardsPage.Assert_NextRunTimeZoneCorrect("GMT\\-03:00",testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\-03:00");
		schedulerDashboardsPage.JobScreen_Select_JobTimeZone(testDataReader.getCellData("ScheduleJobTimeZone"));
		schedulerDashboardsPage.JobScreen_SaveChanges_Button();
		schedulerDashboardsPage.Assert_NextRunTimeZoneCorrect("GMT\\±00:00",testDataReader.getCellData("Automation_Scheduler_DashboardName"),JobName);

		schedulerDashboardsPage.DashboardJob_ClickOnJob(JobName, testDataReader.getCellData("Automation_Scheduler_DashboardName"));
		schedulerDashboardsPage.JobScreen_Assert_JobTimeZone("GMT\\±00:00");
	}

	
//	// Prerequisite, Admin User + Dashboard Created
//	//************************On Hold - until Mona Update TCs - Invalid steps (4 TCs) [C54226-C54227-C54228-C54229]************************
//	@Test(priority = 59, description = "C54226 - Fresh: Oracle: Java Sun: Dashboard Scheduler: Testing that: Schedule is working properly, when it can be viewed by Tenant Owner.")
//	@Description("When I  and I click on schedule. Then Mail will be sent successfully with time zone and repeate type Monthley.")
//	@Severity(SeverityLevel.NORMAL)
//	public void Assert_MailSent_DashboardsSchedueledAndShared_Testtesttesttest() 
//	{
//		navigate_to_scheduleDashboard();
//		String JobName = dashboardPage.scheduleDashboard_addJobName();
//		dashboardPage.scheduleDashboard_AddRecurrence(testDataReader.getCellData("ScheduleJobMonthlyRecurrence"));
//		dashboardPage.scheduleDashboard_monthlyRecurrence_selectType_2ndOption("Week", "2nd", "Sun", "11");
//		dashboardPage.SendDashboard_Click_AddMailRecipientsType("To");
//		dashboardPage.ScheduleDashboard_TypeEmailAndClickAdd(testDataReader.getCellData("Email"));
//		dashboardPage.JobScreen_Select_JobTimeZone(testDataReader.getCellData("ScheduleJobTimeZone"));
//		dashboardPage.scheduleDashboard_Click_schedule();
//	}
	
	public void navigate_to_scheduleDashboard()
	{
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("Automation_Scheduler_DashboardName"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.assert_dashboardName(testDataReader.getCellData("Automation_Scheduler_DashboardName"));

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
