package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;

public class NewUI_Content_Dashboard_SendDashboard {
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
	public NewUI_Content_Dashboard_SendDashboard(WebDriver driver) {
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

	public void sendScheduleDashboard_click_send_schedule_buttons(String Actions) {
		By body_sendDashboard_sendSchedule_button = By.xpath("//button[.='" + Actions + "']");
		ElementActions.click(driver, body_sendDashboard_sendSchedule_button);
	}

}
