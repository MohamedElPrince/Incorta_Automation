package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class NewUI_Content_Dashboard {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	int customElementIdentificationTimeout = 1;

	//// Elements
	// first nested header
	By pageDetails_dashboardName_label = By.xpath("//div[@class='page-details--headerwrap']//h1");
	By pageDetails_back_button = By.xpath("//a[contains(@class,'page-details-back-button')]");
	By pageDetails_details_link = By.xpath("//button[contains(@class,'page-details--link')]");
	By pageDetails_setAsDefaultDashboard_button = By
			.xpath("//i[contains(@class,'anticon-pushpin-o')]/ancestor::button");
	By pageDetails_dashboardActionMenu_button = By
			.xpath("//div[@class='inc-dashboard__actions']//button[contains(@class,'dashboard-action-menu')]");

	// second nested header
	By filterBar_bookmark_button = By.xpath("//div[@class='inc-filter-bar']//button[contains(@class,'bm-icon')]");
	By filterBar_filter_button = By.xpath("//div[@class='inc-filter-bar']//button[contains(@class,'filter-icon')]");
	By filterBar_search_textBox = By.xpath("//div[@class='inc-filter-bar']//input");

	// spinner
	By loadingSpinner = By.xpath("//span[contains(@class,'ant-spin-dot')]");
	By genericInsight_div = By.xpath(
			"//div[@class='insight__body']/ancestor::div[@class='draggable-insight-wrapper']|//div[@class='error-loading-wrapper__error']/ancestor::div[@class='draggable-insight-wrapper']");
	By insight_div;

	// body-insightHeader
	By insightHeader_insightName_label = By.className("insight__header-title");
	By insightHeader_insightActions_button = By.xpath("//span[@class='insight__header-actions']/button");

	// body-insightBody-aggregatedTable
	By aggregatedTable_columnHeader_link;
	By aggregatedTable_cellValue_link;

	//popup settings dashboard menu
	By popup_dashboard_folder_shareAccess_button = By.xpath("//a/span[contains(text(),'Share Access')]");
	By popup_dashboard_folder_sendNow_button = By.xpath("//a/span[contains(text(),'Send Now')]");
	By popup_dashboard_folder_scheduleDelivery_button = By.xpath("//a/span[contains(text(),'Schedule Delivery')]");
	
	//body Dashboard
	By body_shareButton = By.xpath("//i[@class='anticon anticon-share-alt']");
	
	//Send Dashboard
	By body_sendDashboard_bodyField = By.xpath("//div[@class='input-group body-box']//textarea");
	By body_sendDashboard_dataFormat;
	By body_sendDashboard_subjectField = By.xpath("//div[@class='inc-schedule-email']//input[@type='text']");
	By body_sendDashboard_addRecipients = By.xpath("//span[@class='recipients-search']//input");
	By body_sendDashboard_addRecipientsTo_recipientsTypeMenu;
	By body_sendDashboard_addRecipientsCc_recipientsTypeMenu;
	By body_sendDashboard_addRecipientsBcc_recipientsTypeMenu;
	By body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo;
	By body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc;
	By body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc;
	By body_sendDashboard_sendSchedule_button;
	
	//Schedule Dashboard
	By body_scheduleDashboard_jobNameField = By.id("jobName");

	By body_scheduleDashboard_startingPicker_time = By.xpath("//div[contains(@class,'starting-picker')]//input[@placeholder='Select time']");
	By body_scheduleDashboard_startingPicker_timeZone = By.xpath("//div[contains(@class,'starting-picker')]//div[@role='combobox']");
	
	By body_scheduleDashboard_startingPicker_dayOfTheMonth = By.xpath("//div[@class='input-group months-picker']//div[@role='combobox']");
	By body_scheduleDashboard_startingPicker_dayOfTheMonth_number = By.xpath("//div[@class='input-group months-picker']//input[contains(@class,'input-number')]");
	By body_scheduleDashboard_startingPicker_dayOfTheMonth_specificday = By.xpath("//div[@class='input-group-inputs months-picker-inputs']//div[@class='ant-select-selection-selected-value'][contains(.,'1st')]");
	
	By body_scheduleDashboard_startingPicker_days;
	
	By body_scheduleDashboard_everyPicker_selectNoRecurrence = By.xpath("//label[contains(@class,'no-recurrence ')]//input[@type='checkbox']");
	By body_scheduleDashboard_everyPicker_recurrence = By.xpath("//div[contains(@class,'select-recurrence')]//div[contains(@class,'selected-value')]");
	By body_scheduleDashboard_everyPicker_number = By.xpath("//input[@class='ant-input-number-input']");
	By body_scheduleDashboard_everyPicker_recurrence_clickOnDropDown = By.xpath("//div[contains(@class,'select-recurrence')]//span");

	By body_sendDashboard_addRecipients_selectRecipientsType_To;
	By body_sendDashboard_addRecipients_selectRecipientsType_Cc;
	By body_sendDashboard_addRecipients_selectRecipientsType_Bcc;
	
	By body_scheduleDashboard_datePicker_calanderFrom = By.xpath("(//div[contains(@class,'date-pickers-inputs')]//input[@placeholder='Select date'])[1]");
	By body_scheduleDashboard_datePicker_calanderTo = By.xpath("(//div[contains(@class,'date-pickers-inputs')]//input[@placeholder='Select date'])[2]");
	By body_scheduleDashboard_datePicker_doesNotEnd_checkbox = By.xpath("//div[contains(@class,'date-pickers')]//span[@class='ant-checkbox-inner']");
	By selectDate = By.xpath("//input[@class='ant-calendar-input ']");

	//// Functions
	public NewUI_Content_Dashboard(WebDriver driver) {
		this.driver = driver;
		waitForDashboardToFullyLoad();
	}

	public void reportcurrentDashboardURL() {
		ReportManager.log("Dashboard URL: [" + BrowserActions.getCurrentURL(driver) + "].");
	}

	public void waitForDashboardToFullyLoad() {
		int loadingSpinners = ElementActions.getElementsCount(driver, loadingSpinner,
				customElementIdentificationTimeout);
		while (loadingSpinners > 0) {
			loadingSpinners = ElementActions.getElementsCount(driver, loadingSpinner,
					customElementIdentificationTimeout);
		}
	}

	public int countInsights() {
		return ElementActions.getElementsCount(driver, genericInsight_div, customElementIdentificationTimeout);
	}

	public void assert_insightContent_isDisplayed(int insightIndex) {
		insight_div = By.xpath(
				"(//div[@class='insight__body']/ancestor::div[@class='draggable-insight-wrapper']|//div[@class='error-loading-wrapper__error']/ancestor::div[@class='draggable-insight-wrapper'])["
						+ insightIndex + "]");
		Assertions.assertElementExists(driver, insight_div, true);
	}

	/**
	 * Asserts that the target dashboardName is correctly displayed in the
	 * pageDetails_dashboardName_label
	 * 
	 * @param name
	 */
	public void assert_dashboardName_isCorrect(String dashboardName) {
		Assertions.assertElementAttribute(driver, pageDetails_dashboardName_label, "text", dashboardName, true);
	}

	/**
	 * Asserts that the target insightName is correctly displayed in the
	 * insightName_label
	 * 
	 * @param insightName
	 */
	public void assert_insightName_isCorrect(String insightName) {
		Assertions.assertElementAttribute(driver, insightHeader_insightName_label, "text", insightName, true);
	}

	/**
	 * Given that the insightType is aggregatedTable, Asserts that the actual
	 * columnHeaderValue matches with the expected one
	 * 
	 * @param columnNumber
	 *            the desired column number; 1 is the first column table header
	 * @param columnHeaderValue
	 *            the expected value of the target cell
	 */
	public void aggregatedTable_assert_columnHeader_isCorrect(int columnNumber, String columnHeaderValue) {
		aggregatedTable_columnHeader_link = By.xpath("(//thead//span[@class='colHeader']//a)[" + columnNumber + "]");
		Assertions.assertElementAttribute(driver, aggregatedTable_columnHeader_link, "text", columnHeaderValue, true);
	}

	/**
	 * Given that the insightType is aggregatedTable, Asserts that the actual
	 * cellValue matches with the expected one
	 * 
	 * @param typeOrValue
	 *            "type" refers to the first column, "value" refers to the second
	 *            column
	 * @param rowNumber
	 *            the desired row number; 1 is the first row after the table header
	 * @param cellValue
	 *            the expected value of the target cell
	 */
	public void aggregatedTable_assert_cellValue_isCorrect(String typeOrValue, int rowNumber, String cellValue) {
		switch (typeOrValue.trim().toLowerCase()) {
		case "type":
			aggregatedTable_cellValue_link = By
					.xpath("//tbody/tr[" + rowNumber + "]//a[contains(@class,'table__body-cell--aggregated')]");
			break;
		case "value":
			aggregatedTable_cellValue_link = By
					.xpath("//tbody/tr[" + rowNumber + "]//a[contains(@class,'table__body-cell--basic')]");
			break;
		default:
			break;
		}

		Assertions.assertElementAttribute(driver, aggregatedTable_cellValue_link, "text", cellValue, true);
	}

	public void assert_shared_button_active() {
		Assertions.assertElementExists(driver, popup_dashboard_folder_shareAccess_button, true);
	}
	
	public void assert_shared_button_disabled() {
		Assertions.assertElementExists(driver, popup_dashboard_folder_shareAccess_button, false);
	}
	
	public void click_shareOptions_sendNow_button()
	{
		ElementActions.click(driver, body_shareButton);
		ElementActions.click(driver, popup_dashboard_folder_sendNow_button);
	}
	
	public void sendDashboard_addFields(String subjectArea, String bodyArea, String dataFormat, String ToEmailAddress, String CcEmailAddress, String BccEmailAddress)
	{
		body_sendDashboard_addRecipientsTo_recipientsTypeMenu = By.xpath("//span[contains(.,'"+ToEmailAddress+"')]"
				+ "//following-sibling::span//span[@class='recipient-menu']//button//i");
		body_sendDashboard_addRecipientsCc_recipientsTypeMenu = By.xpath("//span[contains(.,'"+CcEmailAddress+"')]"
				+ "//following-sibling::span//span[@class='recipient-menu']//button//i");
		body_sendDashboard_addRecipientsBcc_recipientsTypeMenu = By.xpath("//span[contains(.,'"+BccEmailAddress+"')]"
				+ "//following-sibling::span//span[@class='recipient-menu']//button//i");
		body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo = By.xpath("//li[@type='emails'][contains(.,'"+ToEmailAddress+"')]");
		body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc = By.xpath("//li[@type='emails'][contains(.,'"+CcEmailAddress+"')]");
		body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc = By.xpath("//li[@type='emails'][contains(.,'"+BccEmailAddress+"')]");
		//Subject
		if(subjectArea!=null) {
		ElementActions.type(driver, body_sendDashboard_subjectField, subjectArea);
		}
		//Body
		if(bodyArea!=null) {
		ElementActions.type(driver, body_sendDashboard_bodyField, bodyArea);
		}
		//Data format
		body_sendDashboard_dataFormat = By.xpath("//div/label[contains(@class,'ant-radio-button-wrapper')][contains(.,'"+dataFormat+"')]");
		ElementActions.click(driver, body_sendDashboard_dataFormat);
		//Recipients 
		//To
		ElementActions.type(driver, body_sendDashboard_addRecipients, ToEmailAddress);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTo_recipientsTypeMenu);
		body_sendDashboard_addRecipients_selectRecipientsType_To = By.xpath("//div[@class='inc-search-option__item'][contains(.,'"+ToEmailAddress+"')]//li[@class='ant-dropdown-menu-item'][contains(.,'To')]");
		ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_To);
		//Cc
		ElementActions.type(driver, body_sendDashboard_addRecipients, CcEmailAddress);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc);
		ElementActions.click(driver, body_sendDashboard_addRecipientsCc_recipientsTypeMenu);	
		body_sendDashboard_addRecipients_selectRecipientsType_Cc = By.xpath("//div[@class='inc-search-option__item'][contains(.,'"+CcEmailAddress+"')]//li[@class='ant-dropdown-menu-item'][contains(.,'Cc')]");
		ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_Cc);
		//Bcc
		ElementActions.type(driver, body_sendDashboard_addRecipients, BccEmailAddress);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc);
		ElementActions.click(driver, body_sendDashboard_addRecipientsBcc_recipientsTypeMenu);
		body_sendDashboard_addRecipients_selectRecipientsType_Bcc = By.xpath("//div[@class='inc-search-option__item'][contains(.,'"+BccEmailAddress+"')]//li[@class='ant-dropdown-menu-item'][contains(.,'Bcc')]");
		ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_Bcc);
	}
	
	public void click_shareOptions_scheduleDelivery_button()
	{
		ElementActions.click(driver, body_shareButton);
		ElementActions.click(driver, popup_dashboard_folder_scheduleDelivery_button);
	}
	
/**
	 * @param JobName
	 * @param subjectArea
	 * @param bodyArea
	 * @param dataFormat
	 * HTML - CSV - XLSX
	 * @param ToEmailAddress
	 * @param CcEmailAddress
	 * @param BccEmailAddress
	 * @param Recurrence
	 * Minute(s)
	 * Hour(s)
	 * Day(s)
	 * Week(s)
	 * Month(s)
	 * @param RecurrenceNumber
	 * Minimum 1
	 * @param Time
	 * @param TimeZone
	 * @param dayOfTheWeek
	 * @param day
	 * @param MonthlyDay
	 * @param SpecificDay
	 * @param CalanderFrom
	 * Format "2018-09-16"
	 * @param CalanderTo
	 * Format "2018-09-16"
	 *@return
 */
	public String scheduleDashboard_addFields(String JobName, String subjectArea, String bodyArea, 
			String dataFormat, String ToEmailAddress, String CcEmailAddress, String BccEmailAddress, 
			String Recurrence, String RecurrenceNumber,
			String Time, String TimeZone, String dayOfTheWeek,
			String day, String MonthlyDay, String SpecificDay,
			String CalanderFrom)
	{

		//Add Job Name
		ElementActions.typeAppend(driver, body_scheduleDashboard_jobNameField, JobName);
		
		//Delivery Section
		//Every
		By body_scheduleDashboard_everyPicker_recurrence = By.xpath("//li[contains(.,'"+Recurrence+"')]");

		if(Recurrence == null || RecurrenceNumber == null)
		{
			ElementActions.click(driver, body_scheduleDashboard_everyPicker_selectNoRecurrence);
		}else {
			ElementActions.click(driver, body_scheduleDashboard_everyPicker_recurrence_clickOnDropDown);
			ElementActions.click(driver, body_scheduleDashboard_everyPicker_recurrence);
			ElementActions.typeAppend(driver, body_scheduleDashboard_everyPicker_number, RecurrenceNumber);
		}
		//Starting at
		if (Recurrence == "Minute(s)" || Recurrence == "Hour(s)" || Recurrence == "Day(s)" )
		{
			scheduleDashboard_addFields_delivery_dailyHourMinRecurrence_startingAtSection(Time, TimeZone);
		}
		else if (Recurrence == "Week(s)") 
		{
			scheduleDashboard_addFields_delivery_weeklyRecurrence_startingAtSection(dayOfTheWeek);
		}
		else if (Recurrence == "Month(s)")
		{
			scheduleDashboard_addFields_delivery_monthlyRecurrence_startingAtSection_DaySelection(day, MonthlyDay, SpecificDay);
		}

		//From
		if(CalanderFrom != null)
		{
			ElementActions.click(driver, body_scheduleDashboard_datePicker_calanderFrom);
			ElementActions.type(driver, selectDate, CalanderFrom);
		}

		sendDashboard_addFields(subjectArea, bodyArea, dataFormat, ToEmailAddress, CcEmailAddress, BccEmailAddress);
		return JobName;
	}
	
	/**
	 * used with Minute / Hours / Days
	 * @param Time
	 * Should have the format of "12:00 PM"
	 * @param TimeZone
	 * Should have the format of "GMT-04:00" OR "GMT+02:00"
	 */
	public void scheduleDashboard_addFields_delivery_dailyHourMinRecurrence_startingAtSection(String Time, String TimeZone) 
	{
		ElementActions.typeAppend(driver, body_scheduleDashboard_startingPicker_time, Time);
		ElementActions.typeAppend(driver, body_scheduleDashboard_startingPicker_timeZone, TimeZone);
	}

	/**
	 * 
	 * @param dayOfTheWeek
	 * 1 for Sun
	 * 2 for Mon
	 * 3 for Tue
	 * 4 for Wed
	 * 5 for Thur
	 * 6 for Fri
	 * 7 for Sat
	 */
	public void scheduleDashboard_addFields_delivery_weeklyRecurrence_startingAtSection(String dayOfTheWeek) 
	{
		body_scheduleDashboard_startingPicker_days = By.xpath("//div[@class='input-group weeks-picker']//label[@class='ant-checkbox-wrapper']["+dayOfTheWeek+"]");
		ElementActions.click(driver, body_scheduleDashboard_startingPicker_days);
	}
	
	/**
	 * 
	 * @param day
	 * Day
	 * Saturday
	 * Sunday
	 * Monday
	 * Tuesday
	 * Wednesday
	 * Thursday
	 * Friday
	 * @param MonthlyDay
	 * It will be used only with @param day = day
	 * Min value = 1 -- Max value = 31
	 * @param specificDay
	 * It will be used with all other options except with @param day = day
	 * 1st
	 * 2nd
	 * 3rd
	 * 4th
	 * 5th
	 */
	public void scheduleDashboard_addFields_delivery_monthlyRecurrence_startingAtSection_DaySelection(String day,String MonthlyDay, String SpecificDay ) 
	{

		ElementActions.typeAppend(driver, body_scheduleDashboard_startingPicker_dayOfTheMonth, day);
		
		if (day == "Day") 
		{
			ElementActions.typeAppend(driver, body_scheduleDashboard_startingPicker_dayOfTheMonth_number, MonthlyDay);
		}
		if (day == "Saturday" || day == "Sunday" || day == "Monday" || day == "Tuesday" || day == "Friday" || day == "Wednesday" || day == "Thursday")
		{
			ElementActions.typeAppend(driver, body_scheduleDashboard_startingPicker_dayOfTheMonth_specificday, SpecificDay);
		}
	}
	
	/**
	 * Send - Schedule - Cancel
	 */
	public void sendScheduleDashboard_click_send_schedule_buttons(String Actions)
	{
		By body_sendDashboard_sendSchedule_button = By.xpath("//button[.='"+Actions+"']");
		ElementActions.click(driver, body_sendDashboard_sendSchedule_button);
	}
	
}