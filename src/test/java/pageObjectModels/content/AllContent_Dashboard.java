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
	By popup_sendDashboard_label_hideNotificationText_checkbox_empty = By.xpath("//label[contains(text(),'Hide Notification Text')]/following-sibling::input[contains(@class,'checkbox')]");
//	By popup_sendDashboard_HTMLOption_Text = By.xpath("//span[contains(text(),'The dashboard will be sent in the same layout it appears now. Insights can be downloaded as images.')]");
//	By popup_sendDashboard_XLSXOption_Text = By.xpath("//span[contains(text(),'An XLSX file will be sent only for the tables and pivot tables in the dashboard.')]");
//	By popup_sendDashboard_CSVXOption_Text = By.xpath("//span[contains(text(),'A CSV file will be sent only for the first table in this dashboard.')]");
	By popup_sendDashboard_EmailTypeOptions; // to replace previous elements 
	
	By popup_sendDashboard_selectOutputFormat;
	By popup_dashboard_menu_share_button = By.xpath("//a[contains(@class,'shareFolder')]");
	By popup_sendDashboard_appenedTimestamp_checkbox = By.xpath("//input[@ng-model='appendTimestamp']");

	
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
	
	By popup_sendDashboard_subject_textBox = By.name("subject");
	By popup_sendDashboard_body_textBox = By.xpath("//textarea[@name='body']");
	By popup_sendDashboard_EmailPlusButton;	

	//Duplicate from  popup_sendDashboard_emailAddress_textBox  - Need to be re-factored.
	By popup_sendDashboard_EmailPlusButton_TypeEmail = By.xpath("//div[@class='shareSearch']/input");
	//Duplicate from  popup_sendDashboard_add_button  - Need to be re-factored.
	By popup_sendDashboard_EmailPlusButton_TypeEmail_AddButton = By.xpath("//button[contains(string(),'Add')]");
	
	By popup_FromDatePickerTable;

	//// Functions
	
	public AllContent_Dashboard(WebDriver driver) {
		this.driver = driver;
	}
	

	public void Assert_shared_button_Active() {

		Assertions.assertElementAttribute(driver, popup_dashboard_menu_share_button, "class",
				"shareFolder", true);

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
		// Check that if you are in the first page or not, if yes move to the last page by clicking on last button then click on first button.
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
	
	public void SendDashboard_FillColumns(String MailSubject, String BodyText)
	{
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, MailSubject);
		ElementActions.type(driver, popup_sendDashboard_body_textBox, BodyText);
		//ElementActions.click(driver, popup_sendDashboard_type_radioButton);
	}
	
	/**
	 * 
	 * @param MailRecipientsType
	 * To
	 * Cc
	 * Bcc
	 */
	public void SendDashboard_Click_AddMailRecipientsType(String MailRecipientsType)
	{
	popup_sendDashboard_EmailPlusButton = By.xpath("//label[contains(text(),'"+MailRecipientsType+"')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
	ElementActions.click(driver, popup_sendDashboard_EmailPlusButton);
	}
	
	//Create function for cancel for below
	public void TypeEmailAndClickAdd(String Email)
	{
		ElementActions.type(driver, popup_sendDashboard_EmailPlusButton_TypeEmail, Email);
		ElementActions.click(driver, popup_sendDashboard_EmailPlusButton_TypeEmail_AddButton);
	}
	
	public void Click_Send_Dashboard()
	{
		ElementActions.click(driver, popup_sendDashboard_send_button);
	}
	
	public String scheduleSendDashboard(String description, String startByDate,
			String startByTime, String startByTimeZone, String recurrence, String ToMail, String CcMail, String BccMail) {

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

	/**
	 * 
	 * @param LabelName
	 * "Subject"
	 * "Body"
	 * "Hide Notification Text
	 * "Type"
	 * "File Name"
	 * "Append Timestamp"
	 * "To"
	 * "Cc"
	 * "Bcc"
	 */
	public void sendDashboard_assert_labelsName_exist(String LabelName)
	{
		popup_sendDashboard_Labels = By.xpath("//label[contains(text(),'"+LabelName+"')]");
		Assertions.assertElementExists(driver, popup_sendDashboard_Labels, true);
	}

	public void sendDashboard_assert_subjectField_exist()
	{
		Assertions.assertElementExists(driver, popup_sendDashboard_subject_textBox, true);
	}
	
	public void sendDashboard_assert_bodyField_exist()
	{
		Assertions.assertElementExists(driver, popup_sendDashboard_body_textBox, true);
	}

	public void sendDashboard_assert_ideNotificationText_checkbox_Unchecked()
	{
		Assertions.assertElementAttribute(driver, popup_sendDashboard_label_hideNotificationText_checkbox_empty, "class", "checkbox-input ng-valid ng-dirty ng-valid-parse user-success ng-touched ng-empty", true);
	}

	public void sendDashboard_assert_toolTipIsDiplayed()
	{
		By popup_sendDashboard = By.xpath("//p[contains(text(),'Incorta email notifications are sent with auto-generated text that contains the                                             sender name, the dashboard name, and for CSV/XLSX formats, the filters applied to                                             the dashboard. Toggle this option on to hide this text.')]");
		Assertions.assertElementExists(driver, popup_sendDashboard, true);
	}
	
	/**
	 * 
	 * @param OutputFormat
	 * html
	 * xlsx
	 * csv
	 */
	public void sendDashboard_selectOutputFormat(String OutputFormat)
	{
		popup_sendDashboard_selectOutputFormat = By.xpath("//input[@value='"+OutputFormat+"']");
		ElementActions.click(driver, popup_sendDashboard_selectOutputFormat);
	}

	public void sendDashboard_assert_appenedTimestampOption_CheckedByDefault()
	{
		Assertions.assertElementAttribute(driver, popup_sendDashboard_appenedTimestamp_checkbox, "class", "ng-not-empty", true);
	}

//	public void sendDashboard_assert_XLSXOption_TextIsDisplayed()
//	{
//		Assertions.assertElementExists(driver, popup_sendDashboard_XLSXOption_Text, true);
//	}
//
//	public void sendDashboard_assert_CSVOption_TextIsDisplayed()
//	{
//		Assertions.assertElementExists(driver, popup_sendDashboard_XLSXOption_Text, true);
//	}
//	
//	public void sendDashboard_assert_HTMLOption_TextIsDisplayed()
//	{
//		Assertions.assertElementExists(driver, popup_sendDashboard_HTMLOption_Text, true);
//	}
	
	public void sendDashboard_assert_TypeOfEmailDescription(String type) // to be completed in its testcase to check element text
	{
		popup_sendDashboard_EmailTypeOptions = By.xpath("//span[contains(@ng-if,'"+type+"')]");
	}

	public void sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed()
	{
		Assertions.assertElementExists(driver, popup_sendDashboard_EmailPlusButton, true);
	}
	
	public void sendDashboard_addSubjectField(String text)
	{
		ElementActions.type(driver, popup_sendDashboard_subject_textBox, text);
	}


}
