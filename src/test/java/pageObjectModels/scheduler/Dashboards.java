package pageObjectModels.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.supportActionLibrary.JavaActions;
import com.shaftEngine.validationsLibrary.Assertions;

public class Dashboards {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_scheduler_dashboards");

	//// Elements
	By header_dashboardsTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Dashboards']");
	By body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']");
	// By body_name_link;
	By body_dashboard_link;
	By body_Status_label;
	// By body_nextRun_label;
	By body_name_link;
	By body_checkBox_scheduleDashboard;
	By Body_StatusFilter = By.name("displayedJobStatus");
	By body_StatusFilter_Options;
	By body_Status;
	By body_CompletedStatus;
	By body_ActiveStatus;

	By body_JobName;
	By body_Last_JobName = By.xpath(
			"(//p[@title='Automation_Dashboard_SendDashboard']//ancestor::div[contains(@class,'usersTableRow')]//p[contains(@title,'System generated')])[last()]");

	By popup_JobScreen_RemoveEmail_Button;
	By popup_JobScreen_SaveChanges_Button = By.xpath("//button[contains(text(),'Save Changes')]");
	By popup_JobScreen_Email;
	By popup_JobScreen_emailAddress_textBox = By.xpath("//input[@ng-model='$ctrl.entitySearchText']");
	By popup_JobScreen_EmailAddress_add_button = By.xpath("//button[@type='button'][normalize-space()='Add']");
	By popup_JobScreen_SubjectField = By.name("subject");
	By popup_JobScreen_BodyField = By
			.xpath("//label[contains(text(),'Body')]/parent::div/following-sibling::textarea[@name='description']");
	By popup_JobScreen_EmailPlusButton;
	By popup_JobScreen_JobNameField = By.xpath("//ng-form[@name='$ctrl.scheduleForm']//input[@name='jobName']");
	By popup_JobScreen_description_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//input[@name='description']");
	By popup_JobScreen_startByDate_textBox = By.xpath(
			"//ng-form[@name='$ctrl.scheduleForm']//input[@ng-model='$ctrl.jobObject.startTime']/following-sibling::input[1]");
	By popup_JobScreen_startByTime_textBox = By.xpath(
			"//ng-form[@name='$ctrl.scheduleForm']//input[@ng-model='$ctrl.jobObject.startTime2']/following-sibling::input[1]");
	By popup_JobScreen_startByTimeZone_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//select[@ng-model='$ctrl.jobObject.timezone']");
	By popup_JobScreen_startByTimeZone_textBox_Value = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//select[@ng-model='$ctrl.jobObject.timezone']/option");
	By popup_JobScreen_recurrenceFrequency_radioButton;
	By popup_JobScreen_recurrenceFrequency_radioButton_Selected;
	By popup_jobScreen_selectOutputFormat;
	By popup_JobScreen_dailyRecurrence_number = By.xpath("//div[@ng-switch-when='Daily']//input[@type='text']");
	By popup_jobScreen_weeklyRecurrence_days;

	By popup_jobScreen_monthleyRecurrence_Type_radioButton;
	By popup_jobScreen_monthleyRecurrence_Type_DayNumber = By.xpath("//select[@name='nth']");
	By popup_jobScreen_monthleyRecurrence_Type_DayOfWeek = By.xpath("//select[contains(@ng-model,'dayOfWeek')]");
	By popup_jobScreen_monthleyRecurrence_Type_DayOfMonth = By
			.xpath("(//input[@type='radio'][@value='Week']/parent::div/input)[last()]");
	By popup_jobScreen_repeat_noEnd = By.xpath("//input[@value='0']");
	By popup_jobScreen_EndBy = By.xpath("//div[@class='flex-box']/label[contains(string(),'End By')]/input");
	By popup_jobScreen_EndBy_Date = By.xpath("//div[@class='flex-box']//input[contains(@class,'ws-date')]");

	By body_nextRun;

	By popup_confirmation_Delete_Cancel_ScheduleDashboard;
	By popup_confirmation_Suspend_ScheduleDashboard;
	By popup_sendDashboard_label_hideNotificationText_checkbox = By.xpath(
			"//label[contains(text(),'Hide Notification Text')]/following-sibling::input[contains(@class,'checkbox')]");

	By popup_sendDashboard_FileNameField = By.name("fileName");
	By popup_sendDashboard_label_AppendTimestamp_checkbox_empty = By.xpath(
			"//label[contains(text(),'Append Timestamp')]/following-sibling::input[contains(@class,'checkbox')]");

	By popup_JobScreen_label_hideNotificationText_checkbox_empty = By.xpath(
			"//label[contains(text(),'Hide Notification Text')]/following-sibling::input[contains(@class,'checkbox')]");

	By popup_JobScreen_label_AppendTimestamp_checkbox = By.xpath(
			"//label[contains(text(),'Append Timestamp')]/following-sibling::input[contains(@class,'checkbox')]");

	By popup_jobScreen_dailyRecurrence_Type;

	//// Functions
	public Dashboards(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_allDashboardsTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_dashboardsTabHeader_link, "class", "selectedTab", true);
	}

	public void ChangeJobStatus(String status) {
		ElementActions.select(driver, body_jobStatus_list, status);
	}

	public void Assert_nameIsDisplayed(String dashboard) {
		body_dashboard_link = By.xpath(
				"//div[contains(@class,'usersPanel')]//p[contains(@class,'job-status')][contains(normalize-space(.),'"
						+ dashboard + "')]");
		Assertions.assertElementExists(driver, body_dashboard_link, true);
	}

	public void Assert_jobNameIsDisplayed(String name) {
		body_name_link = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name + "']");
		Assertions.assertElementExists(driver, body_name_link, true);
	}

	public void Assert_jobStatusIsCorrect(String name, String expectedStatus) {
		body_Status_label = By.xpath(
				"//div[contains(@class,'usersPanel')]//div[contains(@class,'userName')][contains(normalize-space(.),'"
						+ name + "')]/following-sibling::div[contains(@class,'dataConnectionLink')]");
		Assertions.assertElementAttribute(driver, body_Status_label, "Text", expectedStatus, true);
	}

	public void DashboardJob_ClickOnJob(String JobName, String DashboardName) {
		body_JobName = By.xpath("//p[contains(text(),'" + DashboardName
				+ "')]/parent::a/parent::div/preceding-sibling::div/p[@title='" + JobName + "']");
		ElementActions.click(driver, body_JobName);
	}

	public void JobScreen_RemoveEmail_Button(String MailRecipientsType, String Email) {
		popup_JobScreen_RemoveEmail_Button = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div/following-sibling::div" + "//span[@title ='" + Email
				+ "']/parent::div/following-sibling::div//a[contains(@ng-click,'removeUser')]");
		ElementActions.click(driver, popup_JobScreen_RemoveEmail_Button);
	}

	public void JobScreen_SaveChanges_Button() {
		ElementActions.click(driver, popup_JobScreen_SaveChanges_Button);
	}

	public void JobScreen_Assert_EmailIsNotExist(String MailRecipientsType, String Email) {
		popup_JobScreen_Email = By.xpath("//label[contains(text(),'" + MailRecipientsType + "')]/"
				+ "parent::div/following-sibling::div//span[@title ='" + Email + "']");
		Assertions.assertElementExists(driver, popup_JobScreen_Email, false);
	}

	public void JobScreen_Assert_EmailExist(String MailRecipientsType, String Email) {
		popup_JobScreen_Email = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div/following-sibling::div//span[@title ='" + Email + "']");
		Assertions.assertElementExists(driver, popup_JobScreen_Email, true);
	}

	public void JobScreen_TypeEmailAndClickAdd(String Email) {
		ElementActions.type(driver, popup_JobScreen_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_JobScreen_EmailAddress_add_button);
	}

	/**
	 * @param MailRecipientsType
	 *            To Cc Bcc
	 */
	public void JobScreen_Click_AddMailRecipientsType(String MailRecipientsType) {
		popup_JobScreen_EmailPlusButton = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
		ElementActions.click(driver, popup_JobScreen_EmailPlusButton);
	}

	public void JobScreen_Assert_JobNameIsDisplayed(String JobName) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_JobNameField, "text", JobName, true);
	}

	public void JobScreen_Assert_DescriptionIsDisplayed(String Description) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_description_textBox, "text", Description, true);
	}

	public void JobScreen_Assert_SubjectNameIsDisplayed(String SubjectName) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_SubjectField, "text", SubjectName, true);
	}

	public void JobScreen_Assert_BodyTextIsDisplayed(String BodyText) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_BodyField, "text", BodyText, true);
	}

	public void JobScreen_Assert_SubjectTextIsDisplayed(String SubjectText) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_SubjectField, "text", SubjectText, true);
	}

	/**
	 * 
	 * @param recurrence
	 *            Monthly Weekly Daily No Recurrence
	 */
	public void JobScreen_Assert_JobRecurrence_Exist(String recurrence) {
		popup_JobScreen_recurrenceFrequency_radioButton = By
				.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
						+ "']/input[@type='radio']");
		Assertions.assertElementAttribute(driver, popup_JobScreen_recurrenceFrequency_radioButton, "value", recurrence,
				true);
	}

	public void JobScreen_Assert_JobDate(String StartDate) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_startByDate_textBox, "text", StartDate, true);
	}

	public void JobScreen_Assert_JobTime(String JobTime) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_startByTime_textBox, "text", JobTime, true);
	}

	public void JobScreen_Assert_JobTimeZone(String JobTimeZone) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_startByTimeZone_textBox, "value", JobTimeZone, true);
	}

	public void JobScreen_Select_JobTimeZone(String startByTimeZone) {
		ElementActions.select(driver, popup_JobScreen_startByTimeZone_textBox, startByTimeZone);
	}

	public String JobScreen_UpdateFields(String description, String startByDate, String startByTime,
			String startByTimeZone, String recurrence) {

		String jobName = "Automation_" + "ScheduleJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_JobScreen_JobNameField, jobName);

		ElementActions.type(driver, popup_JobScreen_description_textBox, description);

		ElementActions.type(driver, popup_JobScreen_startByDate_textBox, startByDate);
		ElementActions.type(driver, popup_JobScreen_startByTime_textBox, startByTime);
		ElementActions.select(driver, popup_JobScreen_startByTimeZone_textBox, startByTimeZone);

		popup_JobScreen_recurrenceFrequency_radioButton = By
				.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
						+ "']/input[@type='radio']");
		ElementActions.click(driver, popup_JobScreen_recurrenceFrequency_radioButton);

		ElementActions.click(driver, popup_JobScreen_SaveChanges_Button);
		return jobName;
	}

	public void JobScreen_Assert_OutputFormat(String OutputFormat) {
		popup_jobScreen_selectOutputFormat = By.xpath("//input[@value='" + OutputFormat + "']");
		Assertions.assertElementExists(driver, popup_jobScreen_selectOutputFormat, true);
	}

	/**
	 * 
	 * @param recurrence
	 *            "Daily" "Weekly" "Monthly" "No Recurrence"
	 */
	public void JobScreen_Assert_JobRecurrence_Selected(String recurrence) {
	By popup_scheduleDashboard_recurrenceFrequency_radioButton = By
	.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
	+ "']/input[@type='radio']");
	Assertions.assertElementAttribute(driver, popup_scheduleDashboard_recurrenceFrequency_radioButton, "checked", "true", true);
	}

	public void JobScreen_SelectDays_WeeklyRecurrence(String Day) {
		popup_jobScreen_weeklyRecurrence_days = By.xpath("//input[@value ='" + Day + "']");
		Assertions.assertElementAttribute(driver, popup_jobScreen_weeklyRecurrence_days, "text", Day, true);
	}

	public void ScheduleDashboard_Select_ScheduleJobs(String DashboardName, String JobName) {
		body_checkBox_scheduleDashboard = By
				.xpath("//p[contains(text(),'" + DashboardName + "')]/parent::a/parent::div/preceding-sibling::div"
						+ "/p[@title='" + JobName + "']/parent::div/preceding-sibling::div/input");
		ElementActions.click(driver, body_checkBox_scheduleDashboard);
	}

	/**
	 * 
	 * @param Button
	 *            Delete Cancel
	 */
	public void ScheduleDashboard_Click_ConfirmUserDeletion_Suspend(String Button) {
		popup_confirmation_Delete_Cancel_ScheduleDashboard = By.xpath("//button[contains(text(),'" + Button + "')]");
		ElementActions.click(driver, popup_confirmation_Delete_Cancel_ScheduleDashboard);
	}

	// Below function for test
	public void ScheduleDashboard_Click_Suspend_Ok() {
		popup_confirmation_Suspend_ScheduleDashboard = By.xpath("//button[contains(@class,'userSaveBtn')]");
		ElementActions.click(driver, popup_confirmation_Suspend_ScheduleDashboard);
	}

	public void ScheduleDashboard_Assert_JobNotExist(String DashboardName, String JobName) {
		body_JobName = By.xpath("//p[contains(text(),'" + DashboardName
				+ "')]/parent::a/parent::div/preceding-sibling::div/p[@title='" + JobName + "']");
		Assertions.assertElementExists(driver, body_JobName, false);
	}

	/**
	 * 
	 * @param DashboardName
	 * @param JobName
	 * @param Status
	 *            "Active" "Suspended"
	 */
	public void ScheduleDashboard_clickOnStatus(String DashboardName, String JobName, String Status) {
		body_Status = By.xpath("//p[contains(text(),'" + DashboardName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName + "']/parent::div"
				+ "/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'" + Status
				+ "')]");
		ElementActions.click(driver, body_Status);
	}

	/**
	 * 
	 * @param Status
	 *            "Active" "Completed" "Suspended" "Completed"
	 */
	public void ScheduleDashboard_StatusFilter_SelectFilter(String Status) {
		ElementActions.click(driver, Body_StatusFilter);
		body_StatusFilter_Options = By.xpath("//option[@value = '" + Status + "']");
		ElementActions.click(driver, body_StatusFilter_Options);
	}

	public void ScheduleDashboard_Assert_JobStatus(String DashboardName, String JobName, String Status) {
		body_Status = By.xpath("//p[contains(text(),'" + DashboardName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName + "']/parent::div"
				+ "/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'" + Status
				+ "')]");
		Assertions.assertElementExists(driver, body_Status, true);
	}

	public void ScheduleDashboard_Open_StatusFilter() {
		ElementActions.click(driver, Body_StatusFilter);
	}

	/**
	 * 
	 * @param Status
	 *            "Active" "Suspended" "Completed" "All"
	 */
	public void ScheduleDashboard_Assert_StatusFiltersExist(String Status) {
		body_StatusFilter_Options = By.xpath("//option[@value = '" + Status + "']");
		Assertions.assertElementExists(driver, body_StatusFilter_Options, true);
	}

	public String GetLastJobName() {
		if (ElementActions.getElementsCount(driver, body_Last_JobName) == 0) {
			return "";
		} else {
			return ElementActions.getText(driver, body_Last_JobName);
		}
	}

	public void Assert_lastJobName(String PreviousJobName) {
		Assertions.assertEquals(PreviousJobName, ElementActions.getText(driver, body_Last_JobName), false);
	}

	public void Click_On_LastJobName() {
		ElementActions.click(driver, body_Last_JobName);
	}

	public void Assert_SpecialCharacters_Subject(String SpecialCharactersSubject) {
		String specialCharacters[] = { "@", "#", "$", "^", "&", "*" };
		SpecialCharactersSubject = JavaActions.replaceRegex(specialCharacters, SpecialCharactersSubject);
		Assertions.assertElementAttribute(driver, popup_JobScreen_SubjectField, "text", SpecialCharactersSubject, true);
	}

	public void Assert_SpecialCharacters_Body(String SpecialCharactersBody) {
		String specialCharacters[] = { "@", "#", "$", "^", "&", "*" };
		SpecialCharactersBody = JavaActions.replaceRegex(specialCharacters, SpecialCharactersBody);
		Assertions.assertElementAttribute(driver, popup_JobScreen_BodyField, "text", SpecialCharactersBody, true);
	}

	public void Assert_Subject_Value(String DashboardName) {
		Assertions.assertElementAttribute(driver, popup_JobScreen_SubjectField, "text", DashboardName, true);
	}

	public void Assert_HideNotificationText_checkbox_checked() {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_hideNotificationText_checkbox, "checked",
				"true", true);
	}

	public void Assert_HideNotificationText_checkbox_unchecked() {
		String Empty = "ng-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_hideNotificationText_checkbox, "class",
				"([\\s\\S]*" + Empty + ".*[\\s\\S]*)", true);
	}

	public void Assert_FileName(String Expected_FileName) {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_FileNameField, "text", Expected_FileName, true);
	}

	public void ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked() {
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_AppendTimestamp_checkbox_empty, "class",
				"([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	public void ScheduleSendDashboard_assert_AppendTimestamp_checkbox_unchecked() {
		String NotEmpty = "ng-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_AppendTimestamp_checkbox_empty, "class",
				"([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	public void JobScreen_assert_HideNotificationText_checkbox_checked() {
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_JobScreen_label_hideNotificationText_checkbox_empty, "class",
				"([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	public void ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked() {
		String Empty = "ng-empty";
		Assertions.assertElementAttribute(driver, popup_JobScreen_label_hideNotificationText_checkbox_empty, "class",
				"([\\s\\S]*" + Empty + ".*[\\s\\S]*)", true);
	}

	public void jobScreen_Assert_fileNameField(String expectedValue) {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_FileNameField, "text", expectedValue, true);
	}

	public void JobScreen_assert_AppendTimestamp_checkbox_checked() {
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_JobScreen_label_AppendTimestamp_checkbox, "class",
				"([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	/**
	 * 
	 * @param DashboardName
	 * @param JobName
	 */
	public void ScheduleDashboard_Assert_ActiveStatus(String DashboardName, String JobName) {
		body_ActiveStatus = By.xpath("//p[contains(text(),'" + DashboardName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName + "']/parent::div"
				+ "/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'Active')]");
		Assertions.assertElementExists(driver, body_ActiveStatus, true);
	}

	public void ScheduleDashboard_Assert_CompletedStatus(String DashboardName, String JobName) {
		body_CompletedStatus = By.xpath("//p[contains(text(),'" + DashboardName
				+ "')]/parent::a/parent::div/preceding-sibling::div/p[@title='" + JobName + "']"
				+ "/parent::div/following-sibling::div[@class='dataConnectionLink left']/p[contains(text(),'Completed')]");
		Assertions.assertElementExists(driver, body_CompletedStatus, true);
	}

	/**
	 * 
	 * @param Type
	 *            Minute Hour Day
	 * @param Number
	 *            Min "min='1' - max='59'" Hour"min='1' - max='23'" Days"min='1' -
	 *            max='31'"
	 */
	public void JobScreen_Assert_dailyRecurrence_RepeatType_NumOfMin(String Type, String Number) {
		popup_jobScreen_dailyRecurrence_Type = By
				.xpath("//div[@ng-switch-when='Daily']//option[@value='" + Type + "']");
		Assertions.assertElementAttribute(driver, popup_jobScreen_dailyRecurrence_Type, "ng-selected", "true", true);
		Assertions.assertElementAttribute(driver, popup_JobScreen_dailyRecurrence_number, "text", Number, true);
	}

	/**
	 * 
	 * @param SelectType
	 *            Day Week
	 * @param DayNumber
	 *            1st =1 2nd =2 3rd =3 4th -4 5th =5
	 * @param DayOfWeek
	 *            Sat Sun ...
	 * @param DayOfMonth
	 *            1-12
	 */
	public void JobScreen_Assert_monthlyRecurrence_selectType_2ndOption(String SelectType, String DayNumber,
			String DayOfWeek, String DayOfMonth) {
		popup_jobScreen_monthleyRecurrence_Type_radioButton = By.xpath("//input[@value='" + SelectType + "']");
		Assertions.assertElementAttribute(driver, popup_jobScreen_monthleyRecurrence_Type_radioButton, "checked",
				"true", true);
		Assertions.assertElementAttribute(driver, popup_jobScreen_monthleyRecurrence_Type_DayNumber, "value", DayNumber,
				true);
		Assertions.assertElementAttribute(driver, popup_jobScreen_monthleyRecurrence_Type_DayOfWeek, "value", DayOfWeek,
				true);
		Assertions.assertElementAttribute(driver, popup_jobScreen_monthleyRecurrence_Type_DayOfMonth, "text",
				DayOfMonth, true);
		Assertions.assertElementAttribute(driver, popup_jobScreen_repeat_noEnd, "checked", "true", true);
	}

	public void JobScreen_Assert_Repeat_NoEnd_Checked() {
		Assertions.assertElementAttribute(driver, popup_jobScreen_repeat_noEnd, "checked", "true", true);
	}

	public void scheduleDashboard_Assert_Repeate_EndByChecked_DateSelected(String Date) {
		Assertions.assertElementAttribute(driver, popup_jobScreen_EndBy, "Checked", "true", true);
		Assertions.assertElementAttribute(driver, popup_jobScreen_EndBy_Date, "text", Date, true);
	}

	public void Assert_NextRunTimeZoneCorrect(String TimeZone, String DashboardName, String JobName) {
		body_nextRun = By.xpath(
				"//p[contains(text(),'" + DashboardName + "')]/parent::a/parent::div/preceding-sibling::div/p[@title='"
						+ JobName + "']/parent::div/following-sibling::div[@class='dataTypeName right']/p");
		Assertions.assertElementAttribute(driver, body_nextRun, "text", "([\\s\\S]*" + TimeZone + ".*[\\s\\S]*)", true);
	}

}
