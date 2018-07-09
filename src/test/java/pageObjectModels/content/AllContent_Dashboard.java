package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;
import com.shaftEngine.validationsLibrary.Verifications;

public class AllContent_Dashboard {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

	//// Elements
	By header_dashboardName_textBox = By.xpath("//div[@id='dashboardHeader']//input");
	By body_insightName_label = By.xpath("//header/span");

	By body_aggregatedTable_dataCell_text;

	By popup_sendDashboard_type_radioButton; // label[normalize-space(.)='HTML']/input[@type='radio']
	By popup_sendDashboard_plusReciever_button = By
			.xpath("//*[contains(@ng-click,\"!error && goToState('addUsers', 'to')\")]");
	By popup_sendDashboard_emailAddress_textBox = By.xpath("//input[@ng-model='$parent.entitySearchText']");
	By popup_sendDashboard_add_button = By.xpath("//button[@type='button'][normalize-space()='Add']");
	By popup_sendDashboard_reciever_label; // div[contains(@class,'folderUserRow')][contains(normalize-space(.),'mohab.mohie@incorta.com')]//h5[contains(@class,'UserData')]
	By popup_sendDashboard_send_button = By.xpath("//button[@type='button'][normalize-space()='Send']");
	By popup_sendDashboard_Labels;
	By popup_sendDashboard_label_hideNotificationText_checkbox_empty = By.xpath(
			"//label[contains(text(),'Hide Notification Text')]/following-sibling::input[contains(@class,'checkbox')]");
	By popup_sendDashboard_label_AppendTimestamp_checkbox_empty = By.xpath(
			"//label[contains(text(),'Append Timestamp')]/following-sibling::input[contains(@class,'checkbox')]");
	By popup_sendDashboard_EmailTypeOptions; // to replace previous elements
	By popup_sendDashboard_HideNotificationText_toolTip = By.xpath("//i[@class='fa fa-question-circle notification-info-icon']");
	By popup_sendDashboard_HideNotificationText_toolTip_text;
	By popup_sendDashboard_FileNameField = By.name("fileName");
	By popup_ScheduleSendDashboardScreens = By.id("send-dashboard-modal");
	By popup_sendDashboard_selectOutputFormat;
	By popup_dashboard_menu_share_button = By.xpath("//a[contains(@class,'shareFolder')]");
	By popup_sendDashboard_appenedTimestamp_checkbox = By.xpath("//input[@ng-model='appendTimestamp']");
	By popup_scheduleDashboard_emailAddress_textBox = By.xpath("//input[@ng-model='$ctrl.entitySearchText']");
	By popup_sendDashboard_fileName_text;
	By popup_scheduleScreen_recurrenceFrequency_radioButton;
	By popup_scheduleScreen_weeklyRecurrence_days;
	// Pagination Elements
	By body_insight_paginationFirst_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-left')]/following-sibling::i/parent::a");
	By body_insight_paginationPrevious_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-left')][not(following-sibling::i)][not(preceding-sibling::i)]/parent::a");
	By body_insight_paginationNumberOfRecords_text = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/span[contains(text(),'of')][contains(text(),'-')]");
	By body_insight_paginationNext_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-right')][not(following-sibling::i)][not(preceding-sibling::i)]/parent::a");
	By body_insight_paginationLast_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-right')]/following-sibling::i/parent::a");

	By popup_scheduleSendDashboard_jobName_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//input[@name='jobName']");
	By popup_scheduleSendDashboard_description_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//input[@name='description']");
	By popup_scheduleSendDashboard_startByDate_textBox = By.xpath(
			"//ng-form[@name='$ctrl.scheduleForm']//input[@ng-model='$ctrl.jobObject.startTime']/following-sibling::input[1]");
	By popup_scheduleSendDashboard_startByTime_textBox = By.xpath(
			"//ng-form[@name='$ctrl.scheduleForm']//input[@ng-model='$ctrl.jobObject.startTime2']/following-sibling::input[1]");
	By popup_scheduleSendDashboard_startByTimeZone_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//select[@ng-model='$ctrl.jobObject.timezone']");
	By popup_scheduleSendDashboard_recurrenceFrequency_radioButton;
	By popup_scheduleSendDashboard_schedule_button = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//button[@ng-click='$ctrl.scheduleJob()']");
	By popup_JobScreen_SaveChanges_Button = By.xpath("//button[@ng-click='$ctrl.updateJob()']");
	By popup_sendDashboard_subject_textBox = By.name("subject");
	By popup_scheduleDashboard_jobName_textBox = By.name("jobName");
	By popup_scheduleDashboard_description_textBox = By.xpath("//input[@name='description']");
	By popup_sendDashboard_body_textBox = By.xpath("//textarea[@name='body']");
	By popup_sendDashboard_EmailPlusButton;
	By popup_sendDashboard_toolTips;
	By popup_scheduleDashboard_appendTimestamp_helpIcon=By.xpath("//i[contains(@class,'timestamp-info-icon')]");
	By popup_scheduleDashboard_appendTimestamp_helpIcon_text;
	By popup_scheduleDashboard_CSV_OK_Button = By.xpath("//button[contains(text(),'Ok')]"); 
	
	By popup_FromDatePickerTable;
	By popup_dashboard_menu_share_SearchList;
	By popup_dashboard_menu_User_List;
	By dashboards_menu_button;
	By popup_dashboard_menu_share_SearchtextBox = By.xpath("//input[@ng-model='$parent.$parent.entitySearchText']");
	By popup_dashboard_menu_share_SearchSaveButton = By.xpath("//button[@type='submit'][normalize-space()='Save']");
	By popup_dashboard_menu_share_DoneButton = By
			.xpath("//button[@class='btn btn-default userSaveBtn'][normalize-space()='Done']");
	By popup_dashboard_menu_SharedWithList;
	By popup_schedulerDashboard_DuplicateJobName_ErrorMessage;
	By popup_scheduleDashboard_body_textBox = By.xpath("//textarea[@name='description']");
	//// Functions

	public AllContent_Dashboard(WebDriver driver) {
		this.driver = driver;
	}

	public void Assert_shared_button_Active() {

		Assertions.assertElementAttribute(driver, popup_dashboard_menu_share_button, "class", "shareFolder", true);

	}

	public void Assert_shared_button_dimmed() {

		Assertions.assertElementAttribute(driver, popup_dashboard_menu_share_button, "class",
				"shareFolder dimmedAction", true);
	}

	public void Assert_dashboardName(String name) {
		Assertions.assertElementAttribute(driver, header_dashboardName_textBox, "Text", "(.*" + name + ".*)", true);
	}

	public void Assert_insightName(String name) {
		Assertions.assertElementAttribute(driver, body_insightName_label, "Text", "(.*" + name + ".*)", true);
	}

	public void selectEmailFormat(String format) {
		By popup_sendDashboard_type_radioButton = By
				.xpath("// label[normalize-space(.)='" + format + "']/input[@type='radio']");
		ElementActions.click(driver, popup_sendDashboard_type_radioButton);
	}

	public void addUserEmailToRecieversList(String email) {
		ElementActions.click(driver, popup_sendDashboard_plusReciever_button);
		ElementActions.type(driver, popup_sendDashboard_emailAddress_textBox, email);
		ElementActions.click(driver, popup_sendDashboard_add_button);
		popup_sendDashboard_reciever_label = By.xpath("//*[contains(@title,'" + email + "')]");
		Assertions.assertElementExists(driver, popup_sendDashboard_reciever_label, true);
	}

	public void scheduleEmailSending() {
		ElementActions.click(driver, popup_sendDashboard_send_button);
	}

	public void AssertData_AggregatedTableContent(String RowOrMeasure, int RowNumber, String ExpectedData) {
		switch (RowOrMeasure.trim().toLowerCase()) {
		case "row":
			body_aggregatedTable_dataCell_text = By
					.xpath("//tbody/tr[" + RowNumber + "]//a[contains(@onclick,', \"row\"')]");
			break;
		case "measure":
			body_aggregatedTable_dataCell_text = By
					.xpath("//tbody/tr[" + RowNumber + "]//a[contains(@onclick,', \"measure\"')]");
			break;
		default:
			break;
		}

		Assertions.assertElementAttribute(driver, body_aggregatedTable_dataCell_text, "text", ExpectedData, true);
	}

	public void Pagination_Verify_UiElementsExist() {
		Verifications.verifyElementExists(driver, body_insight_paginationFirst_button, true);
		Verifications.verifyElementExists(driver, body_insight_paginationPrevious_button, true);
		Verifications.verifyElementExists(driver, body_insight_paginationNumberOfRecords_text, true);
		Verifications.verifyElementExists(driver, body_insight_paginationNext_button, true);
		Verifications.verifyElementExists(driver, body_insight_paginationLast_button, true);
	}

	/**
	 * 
	 * @return the first record [*1* - 100 of 620]
	 */
	private int Pagination_GetFirstRecordInCurrentPage() {
		// sample [*1* - 100 of 620]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");
		String[] firstHalf = parts[0].split(" - ");

		return Integer.parseInt(firstHalf[0].trim());
	}

	/**
	 * 
	 * @return the last record [1 - *100* of 620]
	 */
	private int Pagination_GetLastRecordInCurrentPage() {
		// sample [1 - *100* of 620]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");
		String[] firstHalf = parts[0].split(" - ");

		return Integer.parseInt(firstHalf[1].trim());
	}

	/**
	 * 
	 * @return the total number of records [1 - 100 of *620*]
	 */
	private int Pagination_GetTotalNumberOfRecords() {
		// sample [1 - 100 of *620*]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");

		return Integer.parseInt(parts[1].trim());
	}

	public void Pagination_AssertThatNextButtonWorksAsExpected() {
		// Get last record in current page
		int lastRecordBeforeClickingNext = Pagination_GetLastRecordInCurrentPage();
		// Click the Next Button
		ElementActions.click(driver, body_insight_paginationNext_button);
		// Get first record in new current page
		int firstRecordAfterClickingNext = Pagination_GetFirstRecordInCurrentPage();

		Assertions.assertEquals(lastRecordBeforeClickingNext + 1, firstRecordAfterClickingNext, true);
	}

	public void Pagination_AssertThatLastButtonWorksAsExpected() {
		// Get total records from current page
		int TotalRecordFromCurruntPage = Pagination_GetTotalNumberOfRecords();
		// Click the Last Button
		ElementActions.click(driver, body_insight_paginationLast_button);
		// Get last record of the first record in new current page
		int SecondRecordAfterClickingLastPage = Pagination_GetLastRecordInCurrentPage();
		Assertions.assertEquals(TotalRecordFromCurruntPage, SecondRecordAfterClickingLastPage, true);

	}

	public void Pagination_AssertThatFirstButtontWorksAsExpected() {

		// Get First record in current page
		int initialRecord = Pagination_GetFirstRecordInCurrentPage();
		// Check that if you are in the first page or not, if yes move to the last page
		// by clicking on last button then click on first button.
		if (initialRecord == 1) {
			// I'm on the first page already
			// Click last Button
			ElementActions.click(driver, body_insight_paginationLast_button); // To be removed-->click on last button
			// Get First record in the current new page
			int firstRecordAfterClickingLastButton = Pagination_GetFirstRecordInCurrentPage();
			// Check that after click next button page changed
			Assertions.assertEquals(firstRecordAfterClickingLastButton, 1, false);
			// to confirm that I am no longer in the first page
		}
		// Click First Button
		ElementActions.click(driver, body_insight_paginationFirst_button);
		// Get First record in new current page after click first button
		int firstRecordAfterClickingFirstButton = Pagination_GetFirstRecordInCurrentPage();
		// Check that First Button works and navigated to first page
		Assertions.assertEquals(firstRecordAfterClickingFirstButton, 1, true);
	}

	public void Pagination_AssertThatPreviousButtonWorksAsExpected() {

		// Example 1-10 of 100
		// Navigate to the next page to confirm previous button isn't dimmed
		// Example 11-20 of 100
		// In next page split by of then split again by dash to get first number
		// Current page first row = 11
		// Navigate back to previous page (1-10 of 100)
		// Confirm current page last number (10) = (previous page first row -1)

		// Navigate to next page
		ElementActions.click(driver, body_insight_paginationNext_button);
		int firstRecord_BeforeClick_Previous = Pagination_GetFirstRecordInCurrentPage();
		// Navigate to previous page & get the last record
		ElementActions.click(driver, body_insight_paginationPrevious_button);
		int lasttRecord_AfterClick_Previous = Pagination_GetLastRecordInCurrentPage();
		Assertions.assertEquals(lasttRecord_AfterClick_Previous + 1, firstRecord_BeforeClick_Previous, true);

	}

	public String ScheduleSendDashboard_AddSubjectNameAutomated() {
		String SubjectName = "Automation_" + "SchemaLoadJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, SubjectName);
		// ElementActions.click(driver, popup_sendDashboard_type_radioButton);
		return SubjectName;
	}
	
	public void ScheduleSendDashboard_AddSubjectName(String SubjectName) {
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, SubjectName);
	}
	
	public String ScheduleSendDashboard_AddBodyName() {
		String BodyText = "Automation_" + "SchemaLoadJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_sendDashboard_body_textBox, BodyText);
		// ElementActions.click(driver, popup_sendDashboard_type_radioButton);
		return BodyText;
	}

	/**
	 * 
	 * @param MailRecipientsType
	 *            To Cc Bcc
	 */
	public void SendDashboard_Click_AddMailRecipientsType(String MailRecipientsType) {
		popup_sendDashboard_EmailPlusButton = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
		ElementActions.click(driver, popup_sendDashboard_EmailPlusButton);
	}

	// Create function for cancel for below
	public void TypeEmailAndClickAdd(String Email) {
		ElementActions.type(driver, popup_sendDashboard_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_sendDashboard_add_button);
	}

	public void Click_Send_Dashboard() {
		ElementActions.click(driver, popup_sendDashboard_send_button);
	}

	public String scheduleSendDashboard(String description, String startByDate, String startByTime,
			String startByTimeZone, String recurrence, String ToMail, String CcMail, String BccMail) {

		String jobName = "Automation_" + "SchemaLoadJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_scheduleSendDashboard_jobName_textBox, jobName);

		ElementActions.type(driver, popup_scheduleSendDashboard_description_textBox, description);

		ElementActions.type(driver, popup_scheduleSendDashboard_startByDate_textBox, startByDate);
		ElementActions.type(driver, popup_scheduleSendDashboard_startByTime_textBox, startByTime);
		ElementActions.select(driver, popup_scheduleSendDashboard_startByTimeZone_textBox, startByTimeZone);

		popup_scheduleSendDashboard_recurrenceFrequency_radioButton = By
				.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
						+ "']/input[@type='radio']");

		SendDashboard_Click_AddMailRecipientsType("To");
		TypeEmailAndClickAdd(ToMail);
		SendDashboard_Click_AddMailRecipientsType("Cc");
		TypeEmailAndClickAdd(CcMail);
		SendDashboard_Click_AddMailRecipientsType("Bcc");
		TypeEmailAndClickAdd(BccMail);

		ElementActions.click(driver, popup_scheduleSendDashboard_recurrenceFrequency_radioButton);
		ElementActions.click(driver, popup_scheduleSendDashboard_schedule_button);

		return jobName;
	}

	public void selectShareButton() {
		ElementActions.click(driver, popup_dashboard_menu_share_button);
	}

	public void selectUsertoShareFromList(String name) {

		popup_dashboard_menu_share_SearchList = By
				.xpath("//h5[@class='UserData left ng-binding'][contains(text(),'" + name + "')]");
		popup_dashboard_menu_User_List = By.xpath("//h5[@class='left ng-binding'][contains(text(),'" + name + "')]");
		ElementActions.type(driver, popup_dashboard_menu_share_SearchtextBox, name);
		ElementActions.click(driver, popup_dashboard_menu_share_SearchList);
		ElementActions.click(driver, popup_dashboard_menu_share_SearchSaveButton);
		Assertions.assertElementExists(driver, popup_dashboard_menu_User_List, true);
		ElementActions.click(driver, popup_dashboard_menu_share_DoneButton);

	}

	public void Assert_Content_UserPermission(String SharedWithUser, String Permission) {
		By popup_menu_SharedWithList = By.xpath("//h5[@class='left ng-binding'][contains(string(),'" + SharedWithUser
				+ "')]//parent::div//following-sibling::div/a[contains(string(),'" + Permission + "')]/parent::div");
		Assertions.assertElementExists(driver, popup_menu_SharedWithList, true);

	}

	/**
	 * Could be used with Send / Schedule Dashboard
	 * 
	 * @param LabelName
	 *            "Subject" "Body" "Hide Notification Text "Type" "File Name"
	 *            "Append Timestamp" "To" "Cc" "Bcc" Job Name Description Body
	 */
	public void ScheduleSendDashboard_assert_labelsName_exist(String LabelName) {
		popup_sendDashboard_Labels = By.xpath("//label[contains(text(),'" + LabelName + "')]");
		Assertions.assertElementExists(driver, popup_sendDashboard_Labels, true);
	}

	public void ScheduleSendDashboard_assert_subjectField_exist() {
		Assertions.assertElementExists(driver, popup_sendDashboard_subject_textBox, true);
	}

	public void sendDashboard_assert_bodyField_exist() {
		Assertions.assertElementExists(driver, popup_sendDashboard_body_textBox, true);
	}

	public void ScheduleSendDashboard_assert_HideNotificationText_checkbox_Unchecked() {
		String Empty = "ng-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_hideNotificationText_checkbox_empty,
				"class", "([\\s\\S]*" + Empty + ".*[\\s\\S]*)", true);
	}

	public void ScheduleSendDashboard_assert_Click_HideNotificationText_checkbox_checked() {
		ElementActions.click(driver, popup_sendDashboard_label_hideNotificationText_checkbox_empty);
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_hideNotificationText_checkbox_empty,
				"class", "([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	public void ScheduleSendDashboard_assert_HideNotificationText_toolTipIsDisplayed() {
		ElementActions.hover(driver, popup_sendDashboard_HideNotificationText_toolTip);
		popup_sendDashboard_HideNotificationText_toolTip_text = By.xpath("//div[@class='notification-info-tooltip']");
		//Changed below function to assertElementAttribu to take a screenshot.
		//String ToolTipText = ElementActions.getText(driver, popup_sendDashboard_HideNotificationText_toolTip_text);
		//Assertions.assertEquals(testDataReader.getCellData("HideNotificationToolTipText"), ToolTipText, true);
		Assertions.assertElementAttribute(driver, popup_sendDashboard_HideNotificationText_toolTip_text, "text", testDataReader.getCellData("HideNotificationToolTipText"), true);
	}

	/**
	 * 
	 * @param OutputFormat
	 *            html xlsx csv
	 */
	public void scheduleSendDashboard_selectOutputFormat(String OutputFormat) {
		popup_sendDashboard_selectOutputFormat = By.xpath("//input[@value='" + OutputFormat + "']");
		ElementActions.click(driver, popup_sendDashboard_selectOutputFormat);
	}

	/* Duplicate function from sendDashboard_assert_AppendTimestamp_checkbox_checked
	public void sendDashboard_assert_appenedTimestampOption_CheckedByDefault() {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_appenedTimestamp_checkbox, "class",
				"'checkbox-input ng-valid ng-touched user-success ng-dirty ng-valid-parse ng-not-empty'", true);
	}
*/
	/**
	 * 
	 * @param type
	 *            html xlsx csv
	 */
	public void sendDashboard_assert_TypeOfEmailDescription(String type) {
		popup_sendDashboard_EmailTypeOptions = By.xpath("//span[contains(@ng-if,'" + type + "')]");
		String Text = ElementActions.getText(driver, popup_sendDashboard_EmailTypeOptions);
		Assertions.assertEquals(testDataReader.getCellData(type), Text, true);
	}

	/**
	 * 
	 * @param MailRecipientsType
	 *            To Cc Bcc
	 */
	public void ScheduleSendDashboard_assert_MailRecipientsType_plusSignIsDisplayed(String MailRecipientsType) {
		popup_sendDashboard_EmailPlusButton = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
		Assertions.assertElementExists(driver, popup_sendDashboard_EmailPlusButton, true);
	}

	public String GenerateJobName() {
		return "Automation" + "_ScheduleJob_" + String.valueOf(System.currentTimeMillis());
	}
	
	public String ScheduleSendDashboard_addJobName() {
		String JobName = GenerateJobName();
		ElementActions.type(driver, popup_scheduleDashboard_jobName_textBox, JobName);
		return JobName;
	}

	public void ScheduleSendDashboard_assert_FileNameFieldExist() {
		Assertions.assertElementExists(driver, popup_sendDashboard_FileNameField, true);
	}

	public void scheduleSendDashboard_assert_ScreenIsOpened() {
		Assertions.assertElementExists(driver, popup_ScheduleSendDashboardScreens, true);
	}

	public void scheduleDashboard_assert_JobNameField_exist() {
		Assertions.assertElementExists(driver, popup_scheduleDashboard_jobName_textBox, true);
	}

	public void scheduleDashboard_addJobName(String text) {
		ElementActions.type(driver, popup_scheduleDashboard_jobName_textBox, text);
	}
	
	public String scheduleDashboard_addJobNameAutomatic()
	{
		String jobName = "Automation_" + "ScheduleJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_scheduleDashboard_jobName_textBox, jobName);
		return jobName;
	}

	public void scheduleDashboard_Click_schedule() {
		ElementActions.click(driver, popup_scheduleSendDashboard_schedule_button);
	}

	public void scheduleDashboard_JobScreen_Click_SaveChanges() {
		ElementActions.click(driver, popup_JobScreen_SaveChanges_Button);
	}

	public void scheduleDashboard_assert_duplicateJobName_errorDisplayed() {
		popup_schedulerDashboard_DuplicateJobName_ErrorMessage = By
				.xpath("//div[@ng-if='error']/div[@class='ng-binding']");
		Assertions.assertElementAttribute(driver, popup_schedulerDashboard_DuplicateJobName_ErrorMessage, "text", testDataReader.getCellData("DuplicateJobNameErrorMessage"), true);
	}

	public void ScheduleDashboard_TypeEmailAndClickAdd(String Email) {
		ElementActions.type(driver, popup_scheduleDashboard_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_sendDashboard_add_button);
	}

	public void scheduleDashboard_assert_scheduleButton_disabled() {
		Assertions.assertElementAttribute(driver, popup_scheduleSendDashboard_schedule_button, "disabled", "true",
				true);
	}

	public void scheduleDashboard_assert_DescriptionField_exist() {
		Assertions.assertElementExists(driver, popup_scheduleDashboard_description_textBox, true);
	}

	public void scheduleDashboard_assert_bodyField_exist() {
		Assertions.assertElementExists(driver, popup_scheduleDashboard_body_textBox, true);
	}

	public void scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(String ExpectedValue) {

		String DefaultFileName = ElementActions.getText(driver, popup_sendDashboard_FileNameField);
		Assertions.assertEquals(ExpectedValue, DefaultFileName, true);
	}

	public void ScheduleSendDashboard_assert_AppendTimestamp_checkbox_checked() {
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_AppendTimestamp_checkbox_empty, "class",
				"([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	public String sendDashboardGetFileName() {
		popup_sendDashboard_fileName_text = By.xpath("//input[@name='fileName']");
		String Text = ElementActions.getText(driver, popup_sendDashboard_fileName_text);
		return Text;
	}

	public void sendDashboard_assert_dashboardNameIsFileName(String ActualValue) {
		Assertions.assertEquals(sendDashboardGetFileName(), ActualValue, true);
	}

	public void ScheduleSendDashboard_assert_AppendTimeStamp_HelpIsDisplayed() {
		ElementActions.hover(driver, popup_scheduleDashboard_appendTimestamp_helpIcon);
		popup_scheduleDashboard_appendTimestamp_helpIcon_text = By.xpath("//div[@class='timestamp-info-tooltip']/p");
		Assertions.assertElementAttribute(driver, popup_scheduleDashboard_appendTimestamp_helpIcon_text, "text", testDataReader.getCellData("AppendTimeStampHelpText"), true);
	}
	
	/**
	 * 
	 * @param recurrence
	 * No Recurrence
	 * Daily
	 */
	public void scheduleDashboard_AddRecurrence(String recurrence)
	{
		popup_scheduleScreen_recurrenceFrequency_radioButton = By
				.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
						+ "']/input[@type='radio']");
		ElementActions.click(driver, popup_scheduleScreen_recurrenceFrequency_radioButton);
	}
	
	/**
	 * 
	 * @param Day
	 * Sat
	 * Sun
	 * Mon
	 * Tue
	 * Wed
	 * Thu
	 * Fri
	 */
	public void scheduleDashboard_SelectDays_WeeklyRecurrence(String Day)
	{
		popup_scheduleScreen_weeklyRecurrence_days = By.xpath("//input[@value ='"+Day+"']");
		ElementActions.click(driver, popup_scheduleScreen_weeklyRecurrence_days);
	}
	
	public void scheduleDashboard_CSV_XLSX_ClickOK()
	{
		ElementActions.click(driver, popup_scheduleDashboard_CSV_OK_Button);
	}
	
}