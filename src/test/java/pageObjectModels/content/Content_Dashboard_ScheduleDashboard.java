package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;

public class Content_Dashboard_ScheduleDashboard {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content");
	int customElementIdentificationTimeout = 4;

	//// Elements
	// Send Dashboard
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

	By body_sendDashboard_addRecipients_selectRecipientsType_To;
	By body_sendDashboard_addRecipients_selectRecipientsType_Cc;
	By body_sendDashboard_addRecipients_selectRecipientsType_Bcc;

	// Schedule Dashboard
	By body_scheduleDashboard_jobNameField = By.id("jobName");

	By body_scheduleDashboard_startingPicker_time = By
			.xpath("//div[contains(@class,'starting-picker')]//input[@placeholder='Select time']");
	By body_scheduleDashboard_startingPicker_timeZone_clickOnDropdownMenu = By
			.xpath("//div[contains(@class,'starting-picker')]//div[@role='combobox']");
	By body_scheduleDashboard_startingPicker_timeZone_selectTimeZone;
	By body_scheduleDashboard_startingAtTimeExternal_textBox = By.xpath("//input[@class='ant-time-picker-input']");
	By body_scheduleDashboard_startingAtTimeInternal_textBox = By
			.xpath("//input[contains(@class,'ant-time-picker-panel-input')]");

	By body_scheduleDashboard_startingPicker_dayOfTheMonth = By
			.xpath("//div[@class='input-group months-picker']//div[@role='combobox']");
	By body_scheduleDashboard_startingPicker_dayOfTheMonth_number = By
			.xpath("//div[@class='input-group months-picker']//input[contains(@class,'input-number')]");
	By body_scheduleDashboard_startingPicker_dayOfTheMonth_specificday = By.xpath(
			"//div[@class='input-group-inputs months-picker-inputs']//div[@class='ant-select-selection-selected-value'][contains(.,'1st')]");

	By body_scheduleDashboard_startingPicker_days;

	By body_scheduleDashboard_everyPicker_selectNoRecurrence = By
			.xpath("//label[contains(@class,'no-recurrence ')]//input[@type='checkbox']");
	By body_scheduleDashboard_everyPicker_recurrence = By
			.xpath("//div[contains(@class,'select-recurrence')]//div[contains(@class,'selected-value')]");
	By body_scheduleDashboard_everyPicker_number = By.xpath("//input[@class='ant-input-number-input']");
	By body_scheduleDashboard_everyPicker_recurrence_clickOnDropDown = By
			.xpath("//div[contains(@class,'select-recurrence')]//span");

	By body_scheduleDashboard_datePicker_calanderFrom = By
			.xpath("(//div[contains(@class,'date-pickers-inputs')]//input[@placeholder='Select date'])[1]");
	By body_scheduleDashboard_datePicker_calanderTo = By
			.xpath("(//div[contains(@class,'date-pickers-inputs')]//input[@placeholder='Select date'])[2]");
	By body_scheduleDashboard_datePicker_doesNotEnd_checkbox = By
			.xpath("//div[contains(@class,'date-pickers')]//span[@class='ant-checkbox-inner']");
	By selectDate = By.xpath("//input[@class='ant-calendar-input ']");

	By body_scheduleDashboard_startingPicker_time_hour = By.xpath("//div[@class='ant-time-picker-panel-select'][1]");
	By body_scheduleDashboard_startingPicker_time_minute = By.xpath("//div[@class='ant-time-picker-panel-select'][2]");
	By body_scheduleDashboard_startingPicker_time_AmPm = By.xpath("//div[@class='ant-time-picker-panel-select'][3]");

	//// Functions
	public Content_Dashboard_ScheduleDashboard(WebDriver driver) {
		this.driver = driver;
	}

	public void sendDashboard_addFields(String subjectArea, String bodyArea, String dataFormat, String ToEmailAddress,
			String CcEmailAddress, String BccEmailAddress) {
		body_sendDashboard_addRecipientsTo_recipientsTypeMenu = By.xpath("//span[contains(.,'" + ToEmailAddress + "')]"
				+ "//following-sibling::span//span[@class='recipient-menu']//button//i");
		body_sendDashboard_addRecipientsCc_recipientsTypeMenu = By.xpath("//span[contains(.,'" + CcEmailAddress + "')]"
				+ "//following-sibling::span//span[@class='recipient-menu']//button//i");
		body_sendDashboard_addRecipientsBcc_recipientsTypeMenu = By.xpath("//span[contains(.,'" + BccEmailAddress
				+ "')]" + "//following-sibling::span//span[@class='recipient-menu']//button//i");
		body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo = By
				.xpath("//li[@type='emails'][contains(.,'" + ToEmailAddress + "')]");
		body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc = By
				.xpath("//li[@type='emails'][contains(.,'" + CcEmailAddress + "')]");
		body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc = By
				.xpath("//li[@type='emails'][contains(.,'" + BccEmailAddress + "')]");
		// Subject
		if (subjectArea != null) {
			ElementActions.type(driver, body_sendDashboard_subjectField, subjectArea);
		}
		// Body
		if (bodyArea != null) {
			ElementActions.type(driver, body_sendDashboard_bodyField, bodyArea);
		}
		// Data format
		body_sendDashboard_dataFormat = By
				.xpath("//div/label[contains(@class,'ant-radio-button-wrapper')][contains(.,'" + dataFormat + "')]");
		ElementActions.click(driver, body_sendDashboard_dataFormat);
		// Recipients
		// To
		ElementActions.type(driver, body_sendDashboard_addRecipients, ToEmailAddress);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTo_recipientsTypeMenu);
		body_sendDashboard_addRecipients_selectRecipientsType_To = By
				.xpath("//div[@class='inc-search-option__item'][contains(.,'" + ToEmailAddress
						+ "')]//li[@class='ant-dropdown-menu-item'][contains(.,'To')]");
		ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_To);
		// Cc
		ElementActions.type(driver, body_sendDashboard_addRecipients, CcEmailAddress);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc);
		ElementActions.click(driver, body_sendDashboard_addRecipientsCc_recipientsTypeMenu);
		body_sendDashboard_addRecipients_selectRecipientsType_Cc = By
				.xpath("//div[@class='inc-search-option__item'][contains(.,'" + CcEmailAddress
						+ "')]//li[@class='ant-dropdown-menu-item'][contains(.,'Cc')]");
		ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_Cc);
		// Bcc
		ElementActions.type(driver, body_sendDashboard_addRecipients, BccEmailAddress);
		ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc);
		ElementActions.click(driver, body_sendDashboard_addRecipientsBcc_recipientsTypeMenu);
		body_sendDashboard_addRecipients_selectRecipientsType_Bcc = By
				.xpath("//div[@class='inc-search-option__item'][contains(.,'" + BccEmailAddress
						+ "')]//li[@class='ant-dropdown-menu-item'][contains(.,'Bcc')]");
		ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_Bcc);
	}

	/**
	 * @param JobName
	 * @param subjectArea
	 * @param bodyArea
	 * @param dataFormat       HTML - CSV - XLSX
	 * @param ToEmailAddress
	 * @param CcEmailAddress
	 * @param BccEmailAddress
	 * @param Recurrence       Minute(s) Hour(s) Day(s) Week(s) Month(s)
	 * @param RecurrenceNumber Minimum 1
	 * @param Time
	 * @param TimeZone
	 * @param dayOfTheWeek
	 * @param day
	 * @param MonthlyDay
	 * @param SpecificDay
	 * @param CalanderFrom     Format "2018-09-16"
	 * @param CalanderTo       Format "2018-09-16"
	 * @return
	 */
	public String scheduleDashboard_addFields(String JobName, String subjectArea, String bodyArea, String dataFormat,
			String ToEmailAddress, String CcEmailAddress, String BccEmailAddress, String Recurrence,
			String RecurrenceNumber, String Hour, String Minute, String AmPm, String TimeZone, String dayOfTheWeek,
			String day, String MonthlyDay, String SpecificDay, String CalanderFrom) {
		// Add Job Name
		ElementActions.type(driver, body_scheduleDashboard_jobNameField, JobName);
		// Delivery Section
		// Every
		By body_scheduleDashboard_everyPicker_recurrence = By.xpath("//li[contains(.,'" + Recurrence + "')]");
		if (Recurrence == null || RecurrenceNumber == null) {
			ElementActions.click(driver, body_scheduleDashboard_everyPicker_selectNoRecurrence);
		} else {
			ElementActions.click(driver, body_scheduleDashboard_everyPicker_recurrence_clickOnDropDown);
			ElementActions.click(driver, body_scheduleDashboard_everyPicker_recurrence);
			ElementActions.type(driver, body_scheduleDashboard_everyPicker_number, RecurrenceNumber);
		}
		// Starting at
		if (Recurrence == "Minute(s)" || Recurrence == "Hour(s)" || Recurrence == "Day(s)") {
			scheduleDashboard_addFields_delivery_dailyHourMinRecurrence_startingAtSection(Hour, Minute, AmPm, TimeZone);

		} else if (Recurrence == "Week(s)") {
			scheduleDashboard_addFields_delivery_weeklyRecurrence_startingAtSection(dayOfTheWeek);
		} else if (Recurrence == "Month(s)") {
			scheduleDashboard_addFields_delivery_monthlyRecurrence_startingAtSection_DaySelection(day, MonthlyDay,
					SpecificDay);
		}
		// From
		if (CalanderFrom != null) {
			ElementActions.click(driver, body_scheduleDashboard_datePicker_calanderFrom);
			ElementActions.type(driver, selectDate, CalanderFrom);
		}
		sendDashboard_addFields(subjectArea, bodyArea, dataFormat, ToEmailAddress, CcEmailAddress, BccEmailAddress);
		return JobName;
	}

	/**
	 * used with Minute / Hours / Days
	 * 
	 * @param Time Should have the format of "12:00 PM" Or "12:00 AM"
	 * 
	 */

	private void scheduleDashboard_addFields_delivery_setSchedulerTime(String timeValue) {		
		ElementActions.click(driver, body_scheduleDashboard_startingAtTimeExternal_textBox);
		ElementActions.type(driver, body_scheduleDashboard_startingAtTimeInternal_textBox, "");
		
		// substring, remove the last letter M
		
		ElementActions.typeAppend(driver, body_scheduleDashboard_startingAtTimeInternal_textBox, timeValue.substring(0, timeValue.indexOf('M')));

	}

	/**
	 * used with Minute / Hours / Days
	 * 
	 * @param Time     Should have the format of "12:00 PM"
	 * @param TimeZone Should have the format of "GMT-04:00" OR "GMT+02:00"
	 */
	public void scheduleDashboard_addFields_delivery_dailyHourMinRecurrence_startingAtSection(String Hour,
			String Minute, String AmPm, String TimeZone) {

		body_scheduleDashboard_startingPicker_timeZone_selectTimeZone = By
				.xpath("//ul/li[contains(.,'" + TimeZone + "')]");
//		body_scheduleDashboard_startingPicker_time_hour = By
//				.xpath("//div[@class='ant-time-picker-panel-select'][1]//li[contains(string(),'" + Hour + "')]");
//		body_scheduleDashboard_startingPicker_time_minute = By
//				.xpath("//div[@class='ant-time-picker-panel-select'][2]//li[contains(string(),'" + Minute + "')]");
//		body_scheduleDashboard_startingPicker_time_AmPm = By
//				.xpath("//div[@class='ant-time-picker-panel-select'][3]//li[contains(string(),'" + AmPm + "')]");

//		ElementActions.click(driver, body_scheduleDashboard_startingPicker_time);

//		ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_hour);
//		ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_minute);

//		try {
//			ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_hour);
//		} catch (AssertionError e) {
//			ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_hour);
//		}
//		try {
//			ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_minute);
//		} catch (AssertionError e) {
//			ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_minute);
//		}
//		try {
//			ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_AmPm);
//		} catch (AssertionError e) {
//			ElementActions.click(driver, body_scheduleDashboard_startingPicker_time_AmPm);
//		}
		scheduleDashboard_addFields_delivery_setSchedulerTime(Hour + ":" + Minute + " " + AmPm);

		ElementActions.click(driver, body_scheduleDashboard_startingPicker_timeZone_clickOnDropdownMenu);
		ElementActions.click(driver, body_scheduleDashboard_startingPicker_timeZone_selectTimeZone);
	}

	/**
	 * 
	 * @param dayOfTheWeek 1 for Sun 2 for Mon 3 for Tue 4 for Wed 5 for Thur 6 for
	 *                     Fri 7 for Sat
	 */
	public void scheduleDashboard_addFields_delivery_weeklyRecurrence_startingAtSection(String dayOfTheWeek) {
		body_scheduleDashboard_startingPicker_days = By.xpath(
				"//div[@class='input-group weeks-picker']//label[@class='ant-checkbox-wrapper'][" + dayOfTheWeek + "]");
		ElementActions.click(driver, body_scheduleDashboard_startingPicker_days);
	}

	/**
	 * 
	 * @param day         Day Saturday Sunday Monday Tuesday Wednesday Thursday
	 *                    Friday
	 * @param MonthlyDay  It will be used only with @param day = day Min value = 1
	 *                    -- Max value = 31
	 * @param specificDay It will be used with all other options except with @param
	 *                    day = day 1st 2nd 3rd 4th 5th
	 */
	public void scheduleDashboard_addFields_delivery_monthlyRecurrence_startingAtSection_DaySelection(String day,
			String MonthlyDay, String SpecificDay) {
		ElementActions.type(driver, body_scheduleDashboard_startingPicker_dayOfTheMonth, day);
		if (day == "Day") {
			ElementActions.type(driver, body_scheduleDashboard_startingPicker_dayOfTheMonth_number, MonthlyDay);
		}
		if (day == "Saturday" || day == "Sunday" || day == "Monday" || day == "Tuesday" || day == "Friday"
				|| day == "Wednesday" || day == "Thursday") {
			ElementActions.type(driver, body_scheduleDashboard_startingPicker_dayOfTheMonth_specificday, SpecificDay);
		}
	}

	/**
	 * Send - Schedule - Cancel
	 */
	public void sendScheduleDashboard_click_send_schedule_buttons(String Actions) {
		By body_sendDashboard_sendSchedule_button = By.xpath("//button[.='" + Actions + "']");
		ElementActions.click(driver, body_sendDashboard_sendSchedule_button);
	}

}
