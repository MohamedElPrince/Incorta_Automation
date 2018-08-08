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

	//Send Dashboard pop up elements
	By popup_sendDashboard_type_radioButton; // label[normalize-space(.)='HTML']/input[@type='radio']
	By popup_sendDashboard_plusReciever_button = By
			.xpath("//*[contains(@ng-click,\"!error && goToState('addUsers', 'to')\")]");
	By popup_sendDashboard_emailAddress_textBox = By.xpath("//input[@ng-model='$parent.entitySearchText']");
	By popup_sendDashboard_add_button = By.xpath("//button[@type='button'][normalize-space()='Add']");
	By popup_sendDashboard_reciever_label; // div[contains(@class,'folderUserRow')][contains(normalize-space(.),'mohab.mohie@incorta.com')]//h5[contains(@class,'UserData')]
	By popup_sendDashboard_send_button = By.xpath("//button[@type='button'][normalize-space()='Send']");
	By popup_sendDashboard_Labels;
	By popup_sendScheduleDashboard_label_hideNotificationText_checkbox_empty = By.xpath(
			"//label[contains(text(),'Hide Notification Text')]/following-sibling::input[contains(@class,'checkbox')]");
	By popup_sendDashboard_label_AppendTimestamp_checkbox = By.xpath(
			"//label[contains(text(),'Append Timestamp')]/following-sibling::input[contains(@class,'checkbox')]");
	By popup_sendDashboard_EmailTypeOptions; // to replace previous elements
	By popup_sendDashboard_HideNotificationText_toolTip = By.xpath("//i[@class='fa fa-question-circle notification-info-icon']");
	By popup_sendDashboard_HideNotificationText_toolTip_text;
	By popup_sendDashboard_FileNameField = By.name("fileName");
	By popup_sendDashboard_selectOutputFormat;
	By popup_sendDashboard_fileName_text = By.xpath("//input[@name='fileName']");
	By popup_sendDashboard_subject_textBox = By.name("subject");
	By popup_sendDashboard_body_textBox = By.xpath("//textarea[@name='body']");
	By popup_sendDashboard_EmailPlus_Button;
	By popup_sendDashboard_toolTips;
	
	//Schedule Dashboard pop up elements
	By popup_scheduleDashboard_emailAddress_textBox = By.xpath("//input[@ng-model='$ctrl.entitySearchText']");
	By popup_scheduleDashboard_recurrenceFrequency_radioButton;
	By popup_scheduleDashboard_dailyRecurrence_Type;
	By popup_scheduleDashboard_dailyRecurrence_number = By.xpath("//div[@ng-switch-when='Daily']//input[@type='text']");
	By popup_scheduleDashboard_monthleyRecurrence_Type_radioButton;
	By popup_scheduleDashboard_monthleyRecurrence_Type_DayNumber = By.xpath("//select[@name='nth']");
	By popup_scheduleDashboard_monthleyRecurrence_Type_DayOfWeek = By.xpath("//select[contains(@ng-model,'dayOfWeek')]");
	By popup_scheduleDashboard_monthleyRecurrence_Type_DayOfMonth = By.xpath("(//input[@type='radio'][@value='Week']/parent::div/input)[last()]");
	By popup_scheduleDashboard_weeklyRecurrence_days;
	By popup_scheduleDashboard_endBy_radioButton = By.xpath("//div[@class='flex-box']/label[contains(string(),'End By')]/input");
	By popup_scheduleDashboard_endBy_date_field = By.xpath("//div[@class='flex-box']//input[contains(@class,'ws-date')]");
	By popup_scheduleDashboard_jobName_textBox = By.name("jobName");
	By popup_scheduleDashboard_description_textBox = By.xpath("//input[@name='description']");
	By popup_scheduleDashboard_appendTimestamp_helpIcon=By.xpath("//i[contains(@class,'timestamp-info-icon')]");
	By popup_scheduleDashboard_appendTimestamp_helpIcon_text;
	By popup_scheduleDashboard_csv_oK_button = By.xpath("//button[contains(text(),'Ok')]"); 
	By popup_scheduleDashboard_duplicateJobName_errorMessage;
	By popup_scheduleDashboard_body_textBox = By.xpath("//textarea[@name='description']");

	//Common Elements between [Schedule / Send] Dashboard pop ups
	By popup_ScheduleSendDashboardScreens = By.id("send-dashboard-modal");
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

	//Activity Monitor Elements [Folder Options]
	By popup_activityMonitor_folder_share_button = By.xpath("//a[contains(@class,'shareFolder')]");
	By popup_activityMonitor_share_selectUserFromSearchList;
	By popup_activityMonitor_sharedWith_List;
	By popup_activityMonitor_share_userSearch_textBox = By.xpath("//input[@ng-model='$parent.$parent.entitySearchText']");
	By popup_activityMonitor_share_saveButton = By.xpath("//button[@type='submit'][normalize-space()='Save']");
	By popup_activityMonitor_doneButton = By
			.xpath("//button[@class='btn btn-default userSaveBtn'][normalize-space()='Done']");
	By popup_dashboard_menu_SharedWithList;
	By popup_schedulerDashboard_DuplicateJobName_ErrorMessage;
	By popup_scheduleDashboard_body_textBox = By.xpath("//textarea[@name='description']");
	
	By popup_sendDashboard_invalidFileName = By.xpath("//div[@class='flex-box job-details-item']//span[@ng-if='fileInvalidCharAlert']");
	By popup_sendDashboard_firstemail_searchresult = By.xpath("//div[@class='folderDetails shareSearchResults']//a[@class='folderUserRow ng-scope'][position()=1]");
	//// Functions

	// Functions

	public AllContent_Dashboard(WebDriver driver) {
		this.driver = driver;
	}

	public void assert_shared_button_active() {
		Assertions.assertElementAttribute(driver, popup_activityMonitor_folder_share_button, "class", "shareFolder", true);
	}

	public void assert_shared_button_dimmed() {

		Assertions.assertElementAttribute(driver, popup_activityMonitor_folder_share_button, "class",
				"shareFolder dimmedAction", true);
	}

	public void assert_dashboardName(String name) {
		Assertions.assertElementAttribute(driver, header_dashboardName_textBox, "Text", name.trim(), true);
	}

	public void assert_insightName(String name) {
		Assertions.assertElementAttribute(driver, body_insightName_label, "Text", name.trim(), true);
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

	public void assertData_aggregatedTableContent(String RowOrMeasure, int RowNumber, String ExpectedData) {
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

	public void pagination_verify_uiElementsExist() {
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
	private int pagination_getFirstRecordInCurrentPage() {
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
	private int pagination_getLastRecordInCurrentPage() {
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
	private int pagination_getTotalNumberOfRecords() {
		// sample [1 - 100 of *620*]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");

		return Integer.parseInt(parts[1].trim());
	}

	public void pagination_assertThatNextButtonWorksAsExpected() {
		// Get last record in current page
		int lastRecordBeforeClickingNext = pagination_getLastRecordInCurrentPage();
		// Click the Next Button
		ElementActions.click(driver, body_insight_paginationNext_button);
		// Get first record in new current page
		int firstRecordAfterClickingNext = pagination_getFirstRecordInCurrentPage();

		Assertions.assertEquals(lastRecordBeforeClickingNext + 1, firstRecordAfterClickingNext, true);
	}

	public void pagination_assertThatLastButtonWorksAsExpected() {
		// Get total records from current page
		int TotalRecordFromCurruntPage = pagination_getTotalNumberOfRecords();
		// Click the Last Button
		ElementActions.click(driver, body_insight_paginationLast_button);
		// Get last record of the first record in new current page
		int SecondRecordAfterClickingLastPage = pagination_getLastRecordInCurrentPage();
		Assertions.assertEquals(TotalRecordFromCurruntPage, SecondRecordAfterClickingLastPage, true);

	}

	public void pagination_assertThatFirstButtontWorksAsExpected() {

		// Get First record in current page
		int initialRecord = pagination_getFirstRecordInCurrentPage();
		// Check that if you are in the first page or not, if yes move to the last page
		// by clicking on last button then click on first button.
		if (initialRecord == 1) {
			// I'm on the first page already
			// Click last Button
			ElementActions.click(driver, body_insight_paginationLast_button); // To be removed-->click on last button
			// Get First record in the current new page
			int firstRecordAfterClickingLastButton = pagination_getFirstRecordInCurrentPage();
			// Check that after click next button page changed
			Assertions.assertEquals(firstRecordAfterClickingLastButton, 1, false);
			// to confirm that I am no longer in the first page
		}
		// Click First Button
		ElementActions.click(driver, body_insight_paginationFirst_button);
		// Get First record in new current page after click first button
		int firstRecordAfterClickingFirstButton = pagination_getFirstRecordInCurrentPage();
		// Check that First Button works and navigated to first page
		Assertions.assertEquals(firstRecordAfterClickingFirstButton, 1, true);
	}

	public void pagination_assertThatPreviousButtonWorksAsExpected() {

		// Example 1-10 of 100
		// Navigate to the next page to confirm previous button isn't dimmed
		// Example 11-20 of 100
		// In next page split by of then split again by dash to get first number
		// Current page first row = 11
		// Navigate back to previous page (1-10 of 100)
		// Confirm current page last number (10) = (previous page first row -1)

		// Navigate to next page
		ElementActions.click(driver, body_insight_paginationNext_button);
		int firstRecord_BeforeClick_Previous = pagination_getFirstRecordInCurrentPage();
		// Navigate to previous page & get the last record
		ElementActions.click(driver, body_insight_paginationPrevious_button);
		int lasttRecord_AfterClick_Previous = pagination_getLastRecordInCurrentPage();
		Assertions.assertEquals(lasttRecord_AfterClick_Previous + 1, firstRecord_BeforeClick_Previous, true);

	}

	public String scheduleSendDashboard_addSubjectName() {
		String SubjectName = "Automation_" + "Subject_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, SubjectName);
		return SubjectName;
	}
	
	public void scheduleSendDashboard_addSubjectName(String SubjectName) {
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, SubjectName);
	}
	
	public String sendDashboard_addBodyName() {
		String BodyText = "Automation_" + "Body_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_sendDashboard_body_textBox, BodyText);
		return BodyText;
	}
	
	public String scheduleDashboard_addBodyName() {
		String BodyText = "Automation_" + "Body_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_scheduleDashboard_body_textBox, BodyText);
		return BodyText;
	}


	public void sendDashboard_addBodyName(String BodyText) {
		ElementActions.type(driver, popup_sendDashboard_body_textBox, BodyText);
	}

	public void scheduleDashboard_addBodyName(String BodyText) {
		ElementActions.type(driver, popup_scheduleDashboard_body_textBox, BodyText);
	}
	
	/**
	 * 
	 * @param MailRecipientsType
	 *            To Cc Bcc
	 */
	public void sendDashboard_click_addMailRecipientsType(String MailRecipientsType) {
		popup_sendDashboard_EmailPlus_Button = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
		ElementActions.click(driver, popup_sendDashboard_EmailPlus_Button);
	}

	// Create function for cancel for below
	public void sendDashboard_typeEmailAndClickAdd(String Email) {
		ElementActions.type(driver, popup_sendDashboard_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_sendDashboard_add_button);
	}
	
	public void SendDashboard_TypeEmailAndSelectFirstSearchResult(String Email) {
		ElementActions.type(driver, popup_sendDashboard_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_sendDashboard_firstemail_searchresult);
	}
	
	public void SendDashboard_TypeEmail(String Email) {
		ElementActions.type(driver, popup_sendDashboard_emailAddress_textBox, Email);
	}

	public void click_send_dashboard() {
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

		sendDashboard_click_addMailRecipientsType("To");
		scheduleDashboard_typeEmailAndClickAdd(ToMail);
		sendDashboard_click_addMailRecipientsType("Cc");
		scheduleDashboard_typeEmailAndClickAdd(CcMail);
		sendDashboard_click_addMailRecipientsType("Bcc");
		scheduleDashboard_typeEmailAndClickAdd(BccMail);

		ElementActions.click(driver, popup_scheduleSendDashboard_recurrenceFrequency_radioButton);
		ElementActions.click(driver, popup_scheduleSendDashboard_schedule_button);

		return jobName;
	}

	public void selectShareButton() {
		ElementActions.click(driver, popup_activityMonitor_folder_share_button);
	}

	public void selectUsertoShareFromList(String name) {

		popup_activityMonitor_share_selectUserFromSearchList = By
				.xpath("//h5[@class='UserData left ng-binding'][contains(text(),'" + name + "')]");
		popup_activityMonitor_sharedWith_List = By.xpath("//h5[@class='left ng-binding'][contains(text(),'" + name + "')]");
		ElementActions.type(driver, popup_activityMonitor_share_userSearch_textBox, name);
		ElementActions.click(driver, popup_activityMonitor_share_selectUserFromSearchList);
		ElementActions.click(driver, popup_activityMonitor_share_saveButton);
		Assertions.assertElementExists(driver, popup_activityMonitor_sharedWith_List, true);
		ElementActions.click(driver, popup_activityMonitor_doneButton);

	}

	public void assert_content_userPermission(String SharedWithUser, String Permission) {
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
	public void scheduleSendDashboard_assert_labelsName_exist(String LabelName) {
		popup_sendDashboard_Labels = By.xpath("//label[contains(text(),'" + LabelName + "')]");
		Assertions.assertElementExists(driver, popup_sendDashboard_Labels, true);
	}

	public void scheduleSendDashboard_assert_subjectField_exist() {
		Assertions.assertElementExists(driver, popup_sendDashboard_subject_textBox, true);
	}

	public void sendDashboard_assert_bodyField_exist() {
		Assertions.assertElementExists(driver, popup_sendDashboard_body_textBox, true);
	}

	public void scheduleSendDashboard_assert_hideNotificationText_checkbox_unchecked() {
		String Empty = "ng-empty";
		Assertions.assertElementAttribute(driver, popup_sendScheduleDashboard_label_hideNotificationText_checkbox_empty,
				"class", "([\\s\\S]*" + Empty + ".*[\\s\\S]*)", true);
	}

	public void scheduleSendDashboard_assert_click_hideNotificationText_checkbox_checked() {
		ElementActions.click(driver, popup_sendScheduleDashboard_label_hideNotificationText_checkbox_empty);
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_sendScheduleDashboard_label_hideNotificationText_checkbox_empty,
				"class", "([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}
	
	public void scheduleSendDashboard_click_hideNotificationText_checkbox() {
		ElementActions.click(driver, popup_sendScheduleDashboard_label_hideNotificationText_checkbox_empty);
	}

	public void scheduleSendDashboard_assert_hideNotificationText_toolTipIsDisplayed() {
		ElementActions.hover(driver, popup_sendDashboard_HideNotificationText_toolTip);
		popup_sendDashboard_HideNotificationText_toolTip_text = By.xpath("//div[@class='notification-info-tooltip']");
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
	 
	/**
	 * 
	 * @param type
	 *            html xlsx csv
	 */
	public void sendDashboard_assert_typeOfEmailDescription(String type) {
		popup_sendDashboard_EmailTypeOptions = By.xpath("//span[contains(@ng-if,'" + type + "')]");
		String Text = ElementActions.getText(driver, popup_sendDashboard_EmailTypeOptions);
		Assertions.assertEquals(testDataReader.getCellData(type), Text, true);
	}

	/**
	 * 
	 * @param MailRecipientsType
	 *            To Cc Bcc
	 */
	public void scheduleSendDashboard_assert_mailRecipientsType_plusSignIsDisplayed(String MailRecipientsType) {
		popup_sendDashboard_EmailPlus_Button = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
		Assertions.assertElementExists(driver, popup_sendDashboard_EmailPlus_Button, true);
	}

	public void sendDashboard_addSubjectField(String text) {
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, text);
	}
	
	public String sendDashboard_automated_addFileName() {
		String FileName = "Automation_" + "FileName_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_sendDashboard_FileNameField , FileName);
		return FileName;
	}
	
	public void sendDashboard_addFileName(String fileName) {
		ElementActions.type(driver, popup_sendDashboard_FileNameField , fileName);
	}

	public void scheduleSendDashboard_assert_fileNameFieldExist() {
		Assertions.assertElementExists(driver, popup_sendDashboard_FileNameField, true);
	}

	public void scheduleSendDashboard_assert_screenIsOpened() {
		Assertions.assertElementExists(driver, popup_ScheduleSendDashboardScreens, true);
	}

	public void scheduleDashboard_assert_jobNameField_exist() {
		Assertions.assertElementExists(driver, popup_scheduleDashboard_jobName_textBox, true);
	}

	public void scheduleDashboard_addJobName(String text) {
		ElementActions.type(driver, popup_scheduleDashboard_jobName_textBox, text);
	}
	
	public String scheduleDashboard_addJobName()
	{
		String jobName = "Automation_" + "ScheduleJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_scheduleDashboard_jobName_textBox, jobName);
		return jobName;
	}

	public void scheduleDashboard_click_schedule() {
		ElementActions.click(driver, popup_scheduleSendDashboard_schedule_button);
	}

	public void scheduleDashboard_assert_duplicateJobName_errorDisplayed() {
		popup_scheduleDashboard_duplicateJobName_errorMessage = By
				.xpath("//div[@ng-if='error']/div[@class='ng-binding']");
	//	Assertions.assertElementAttribute(driver, popup_schedulerDashboard_DuplicateJobName_ErrorMessage, "text", testDataReader.getCellData("DuplicateJobNameErrorMessage"), true);
		Assertions.assertElementAttribute(driver, popup_scheduleDashboard_duplicateJobName_errorMessage, "text", "INC_004010050:Another SCHEDULER with the same name \\[Automation_Scheduler_DuplicateJobName\\] already exists.", true);
	}

	public void scheduleDashboard_typeEmailAndClickAdd(String Email) {
		ElementActions.type(driver, popup_scheduleDashboard_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_sendDashboard_add_button);
	}

	public void scheduleDashboard_assert_scheduleButton_disabled() {
		Assertions.assertElementAttribute(driver, popup_scheduleSendDashboard_schedule_button, "disabled", "true",
				true);
	}
	
	public void scheduleDashboard_assert_scheduleButton_enabled() {
		Assertions.assertElementAttribute(driver, popup_scheduleSendDashboard_schedule_button, "disabled", "null",
				true);
	}

	public void scheduleDashboard_assert_DescriptionField_exist() {
		Assertions.assertElementExists(driver, popup_scheduleDashboard_description_textBox, true);
	}

	public void scheduleDashboard_assert_bodyField_exist() {
		Assertions.assertElementExists(driver, popup_scheduleDashboard_body_textBox, true);
	}

	public void scheduleDashboard_assert_fileNameField_filledWithDashboardNameByDefault(String ExpectedValue) {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_FileNameField, "text", ExpectedValue, true);
	}

	public void scheduleSendDashboard_assert_appendTimestamp_checkbox_checked() {
		String NotEmpty = "ng-not-empty";
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_AppendTimestamp_checkbox, "class",
				"([\\s\\S]*" + NotEmpty + ".*[\\s\\S]*)", true);
	}

	public void sendDashboard_assert_dashboardNameIsFileName(String ExpectedValue) {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_fileName_text, "text", ExpectedValue , true);
	}

	public void scheduleSendDashboard_assert_appendTimeStamp_helpIsDisplayed() {
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
	public void scheduleDashboard_addRecurrence(String recurrence)
	{
		popup_scheduleDashboard_recurrenceFrequency_radioButton = By
				.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
						+ "']/input[@type='radio']");
		ElementActions.click(driver, popup_scheduleDashboard_recurrenceFrequency_radioButton);
	}
	
	/**
	 * @param SelectType
	 * Week
	 * Day
	 * @param DayNumber
	 * 1st - 2nd - 3rd - 4th - 5th
	 * @param DayOfWeek
	 * Sun
	 * Mon
	 * Tue
	 * Wed
	 * Thu
	 * Fri
	 * Sat
	 */
	public void scheduleDashboard_monthlyRecurrence_selectType_2ndOption(String SelectType, String DayNumber, String DayOfWeek, String DayOfMonth)
	{
		popup_scheduleDashboard_monthleyRecurrence_Type_radioButton = By.xpath("//input[@value='"+SelectType+"']");
		ElementActions.click(driver, popup_scheduleDashboard_monthleyRecurrence_Type_radioButton);
		ElementActions.select(driver, popup_scheduleDashboard_monthleyRecurrence_Type_DayNumber, DayNumber);
		ElementActions.select(driver, popup_scheduleDashboard_monthleyRecurrence_Type_DayOfWeek, DayOfWeek);
		ElementActions.type(driver, popup_scheduleDashboard_monthleyRecurrence_Type_DayOfMonth, DayOfMonth);
	}
	
	/**
	 * 
	 * @param Type
	 * Minute
	 * Hour
	 * Day
	 * @param Number
	 * Min "min='1' - max='59'"
	 * Hour"min='1' - max='23'"
	 * Days"min='1' - max='31'"
	 */
	public void scheduleDashboard_dailyRecurrence_repeatType(String Type, String Number)
	{
		popup_scheduleDashboard_dailyRecurrence_Type = By.xpath("//div[@ng-switch-when='Daily']//option[@value='"+Type+"']");
		ElementActions.click(driver, popup_scheduleDashboard_dailyRecurrence_Type);
		ElementActions.type(driver, popup_scheduleDashboard_dailyRecurrence_number, Number);
	}	
	
	public void scheduleDashboard_select_jobTimeZone(String startByTimeZone)
	{
		ElementActions.select(driver, popup_scheduleSendDashboard_startByTimeZone_textBox, startByTimeZone);	
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
	public void scheduleDashboard_selectDays_weeklyRecurrence(String Day)
	{
		popup_scheduleDashboard_weeklyRecurrence_days = By.xpath("//input[@value ='"+Day+"']");
		ElementActions.click(driver, popup_scheduleDashboard_weeklyRecurrence_days);
	}

	public void scheduleDashboard_csv_xlsx_clickOK() {
		ElementActions.click(driver, popup_scheduleDashboard_csv_oK_button);
	}

	public void sendDashboard_assert_sendButton_enabled() {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_send_button, "disabled", "null", true);
	}
	
	public void sendDashboard_assert_sendButton_disabled() {
		Assertions.assertElementAttribute(driver, popup_sendDashboard_send_button, "disabled", "true", true);
	}
	
	public void copy_body_text(){
		ElementActions.clipboardActions(driver, popup_sendDashboard_body_textBox, "copy");
	}
	
	public void paste_text_body(){
		ElementActions.clipboardActions(driver, popup_sendDashboard_body_textBox, "paste");
	}
	
	public String GetBodyText() {
		String BodyText = ElementActions.getText(driver, popup_sendDashboard_body_textBox);
		return BodyText;
	}
	
	public void scheduleDashboard_typeFileNameField(String text) {
		ElementActions.type(driver, popup_sendDashboard_FileNameField, text);
	}
	
	public void scheduleDashboard_repeate_selectEndBy(String Date)
	{
		ElementActions.click(driver, popup_scheduleDashboard_endBy_radioButton);
		ElementActions.type(driver, popup_scheduleDashboard_endBy_date_field, Date);
	}
	
		public void uncheck_appendTimestamp() {
		ElementActions.click(driver, popup_sendDashboard_label_AppendTimestamp_checkbox);
	}
	
	public void assert_invalidFileName_errorMessage() {
		String errorMessage = testDataReader.getCellData("InvalidFileNameErrorMessage");
		String[] specialCharacters = {"\\","/","?","*",":","\"","<",">","|"};
		errorMessage = JavaActions.replaceRegex(specialCharacters, errorMessage);
		Assertions.assertElementAttribute(driver, popup_sendDashboard_invalidFileName, "text",errorMessage, true);
	}
	
	public void SendDashboard_assert_placeholder_ToField(){
		Assertions.assertElementAttribute(driver, popup_sendDashboard_emailAddress_textBox, "placeholder",testDataReader.getCellData("ToPlaceholder"), true);
	}
	
	public void SendDashboard_assert_placeholder_CcBccField(){
		Assertions.assertElementAttribute(driver, popup_sendDashboard_emailAddress_textBox, "placeholder",testDataReader.getCellData("CcBccPlaceholder"), true);
	}
	
	public void SendDashboard_assert_no_searchResult() {
		Assertions.assertElementExists(driver, popup_sendDashboard_firstemail_searchresult, false);
	}
}