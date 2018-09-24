package pageObjectModels.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;
import com.shaftEngine.validationsLibrary.Verifications;

public class NewUI_SchemaLoads {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_scheduler_schemas");

	//// Elements
	By header_schemasTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Schema Loads']");
	By body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']");
	By body_name_link;
	// By body_owner_link;
	By body_schema_link;
	By body_status_label;
	By body_Job_status_label;
	By body_schemajobName_link;
	By body_jobStatus_state;
	By body_iframe = By.xpath("//iframe[@title='Legacy Web']");

	By popup_scheduleSchema_repeat_radioButton;
	By popup_scheduleSchema_weeklyDay_checkBox;
	By popup_scheduleSchema_loadType_dropDownMenu;
	By popup_scheduleSchema_confirmChangingStatusOk_button;
	By popup_scheduleSchema_saveChanges_button = By.xpath("//button[@type='button'][contains(text(),'Save Changes')]");
	By popup_scheduleSchema_dailyRecurrence_Type;
	By popup_scheduleSchema_dailyRecurrence_day_hour = By
			.xpath("//div[@ng-switch-when='Daily']//input[@type='number']/following-sibling::input");

	// Confirm Delete popup view
	By popup_scheduleSchema_delete_button = By
			.xpath("//button[@ng-click='confirmDeleteScheduler();'][contains(text(),'Delete')]");

	// By body_nextRun_label;

	By popup_scheduleSchemaLoad_jobName_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//input[@name='jobName']");
	By popup_scheduleSchemaLoad_description_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//input[@name='description']");
	By popup_scheduleSchemaLoad_schema_list = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//select[@name='schemaName']");
	By popup_scheduleSchemaLoad_loadType_list = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//select[@name='loadType']");
	By popup_scheduleSchemaLoad_startByDate_textBox = By.xpath(
			"//ng-form[@name='$ctrl.scheduleForm']//input[@ng-model='$ctrl.jobObject.startTime']/following-sibling::input[1]");
	By popup_scheduleSchemaLoad_startByTime_textBox = By.xpath(
			"//ng-form[@name='$ctrl.scheduleForm']//input[@ng-model='$ctrl.jobObject.startTime2']/following-sibling::input[1]");
	By popup_scheduleSchemaLoad_startByTimeZone_textBox = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//select[@ng-model='$ctrl.jobObject.timezone']");
	By popup_scheduleSchemaLoad_recurrenceFrequency_radioButton; // ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='Daily']/input[@type='radio']
	By popup_scheduleSchemaLoad_schedule_button = By
			.xpath("//ng-form[@name='$ctrl.scheduleForm']//button[@ng-click='$ctrl.scheduleJob()']");

	//// Functions
	public NewUI_SchemaLoads(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_schemasTabIsSelected() {
		ElementActions.switchToIframe(driver, body_iframe);
		Assertions.assertElementAttribute(driver, header_schemasTabHeader_link, "class", "selectedTab", true);
		ElementActions.switchToDefaultContent(driver);
	}

	public void changeJobStatusFilter(String status) {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.select(driver, body_jobStatus_list, status);
		ElementActions.switchToDefaultContent(driver);
	}

	public void verify_jobStatusOptionIsDisplayed(String jobStatusOption) {
		ElementActions.switchToIframe(driver, body_iframe);

		body_jobStatus_list = By
				.xpath("//select[@ng-change='jobStatusChanged()']/option[contains(.,'" + jobStatusOption + "')]");
		// Verifications.verifyElementAttribute(driver, body_jobStatus_list,
		// "text",jobStatusOption, true);
		Verifications.verifyElementExists(driver, body_jobStatus_list, true);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Assert_nameIsDisplayed(String name) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_name_link = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name + "']");
		Assertions.assertElementExists(driver, body_name_link, true);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Assert_jobStatusIsCorrect(String name, String expectedStatus) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_status_label = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name
				+ "']/parent::div[contains(@class,'userName')]/following-sibling::div[contains(@class,'dataConnectionLink')]");
		Assertions.assertElementAttribute(driver, body_status_label, "Text", expectedStatus, true);
		ElementActions.switchToDefaultContent(driver);
	}

	public void assert_schemaJob_created(String job_name, String schema_name) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_Job_status_label = By.xpath("//p[@title='" + job_name
				+ "']/parent::div[contains(@class,'userName')]/following-sibling::div[contains(@class,'userName')]//p[contains(text(),'"
				+ schema_name + "')]");
		Assertions.assertElementAttribute(driver, body_Job_status_label, "Text", schema_name, true);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Assert_schemaJob_deleted(String job_name, String schema_name) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_schemajobName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schema_name + "')]/p/parent::div/preceding-sibling::div");
		Assertions.assertElementExists(driver, body_schemajobName_link, false);
		ElementActions.switchToDefaultContent(driver);
	}

	public void select_schemaName(String schemaName) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_schemajobName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]");
		ElementActions.click(driver, body_schemajobName_link);

		ElementActions.switchToDefaultContent(driver);
	}

	public void select_schemaNameCheckBox(String schemaJobName) {
		ElementActions.switchToIframe(driver, body_iframe);

		body_schemajobName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaJobName + "')]/p/parent::div/preceding-sibling::div");
		ElementActions.click(driver, body_schemajobName_link);

		ElementActions.switchToDefaultContent(driver);
	}

	public void select_schemaName(String jobName, String schemaName) {
		ElementActions.switchToIframe(driver, body_iframe);

		body_schemajobName_link = By.xpath("//p[contains(text(),'" + schemaName
				+ "')]/ancestor::div[contains(@class,'userName')]/preceding-sibling::div[contains(@class,'userName')]//p[@title='"
				+ jobName + "']");
		ElementActions.click(driver, body_schemajobName_link);

		ElementActions.switchToDefaultContent(driver);
	}

	/**
	 * This function to select repeat radio button choice
	 * 
	 * @param Radio_Button_Name
	 * 
	 *            Daily, Weekly, Monthly,No End, No Recurrence
	 * 
	 */
	public void Select_schedulerSchema_radioButton(String Radio_button_name) {
		ElementActions.switchToIframe(driver, body_iframe);

		popup_scheduleSchema_repeat_radioButton = By
				.xpath("//input[@type='radio'][@value= '" + Radio_button_name + "']");
		ElementActions.click(driver, popup_scheduleSchema_repeat_radioButton);
		ElementActions.switchToDefaultContent(driver);
	}

	/**
	 * This function to assert the selected radio button
	 * 
	 * @param Radio_Button_Name
	 * 
	 *            Daily, Weekly, Monthly,No End, No Recurrence
	 * 
	 */
	public void assert_schedulerSchema_radioButton_selected(String Radio_Button_Name) {
		ElementActions.switchToIframe(driver, body_iframe);

		popup_scheduleSchema_repeat_radioButton = By
				.xpath("//input[@type='radio'][@value= '" + Radio_Button_Name + "']");
		Assertions.assertElementAttribute(driver, popup_scheduleSchema_repeat_radioButton, "checked", "true", true);

		ElementActions.switchToDefaultContent(driver);
	}

	/**
	 * This function to select one day from the weekly days section
	 * 
	 * @param weekday_checkbox
	 * 
	 *            Sun, Mon, Tue, Wed, Thu, Fri, Sat
	 * 
	 */
	public void select_scheduleSchema_weeklyDay_CheckBox(String Day) {
		ElementActions.switchToIframe(driver, body_iframe);

		popup_scheduleSchema_weeklyDay_checkBox = By
				.xpath("//div//input[@ng-model='w.selected'][@value='" + Day + "']");

		ElementActions.click(driver, popup_scheduleSchema_weeklyDay_checkBox);
		ElementActions.switchToDefaultContent(driver);
	}

	/**
	 * This function to assert the selected day from the weekly days section
	 * 
	 * @param weekday_checkbox
	 * 
	 *            Sun, Mon, Tue, Wed, Thu, Fri, Sat
	 * 
	 */
	public void Assert_scheduleSchema_weeklyDay_CheckBox(String Day) {
		ElementActions.switchToIframe(driver, body_iframe);

		popup_scheduleSchema_weeklyDay_checkBox = By.xpath("//input[@ng-model='w.selected'][@value='" + Day + "']");

		Assertions.assertElementAttribute(driver, popup_scheduleSchema_weeklyDay_checkBox, "checked", "true", true);

		ElementActions.switchToDefaultContent(driver);
	}

	/**
	 * This function to Assert the selected loadtype from the dropdown list
	 * 
	 * @param LoadType
	 *            DropDown
	 * 
	 *            Incremental, Full, Staging, Snapshot
	 * 
	 */
	public void Assert_scheduleSchema_loadType_dropDownMenu(String LoadType) {
		ElementActions.switchToIframe(driver, body_iframe);

		popup_scheduleSchema_loadType_dropDownMenu = By
				.xpath("//select[@name='loadType']//option[@value='" + LoadType + "']");
		Assertions.assertElementAttribute(driver, popup_scheduleSchema_loadType_dropDownMenu, "value", LoadType, true);

		ElementActions.switchToDefaultContent(driver);
	}

	/**
	 * This function to select loadtype from the dropdown list
	 * 
	 * @param LoadType
	 *            DropDown
	 * 
	 *            Incremental, Full, Staging, Snapshot
	 * 
	 */

	public void Select_scheduleSchema_loadType_dropDownMenu(String LoadType) {
		ElementActions.switchToIframe(driver, body_iframe);

		popup_scheduleSchema_loadType_dropDownMenu = By
				.xpath("//select[@name='loadType']//option[@value='" + LoadType + "']");
		ElementActions.click(driver, popup_scheduleSchema_loadType_dropDownMenu);

		ElementActions.switchToDefaultContent(driver);
	}

	public void Click_scheduleSchema_saveChanges_button() {
		ElementActions.switchToIframe(driver, body_iframe);

		ElementActions.click(driver, popup_scheduleSchema_saveChanges_button);

		ElementActions.switchToDefaultContent(driver);
	}

	public void Click_scheduleSchema_deleteButton() {
		ElementActions.switchToIframe(driver, body_iframe);

		ElementActions.click(driver, popup_scheduleSchema_delete_button);

		ElementActions.switchToDefaultContent(driver);
	}

	public void Assert_scheduleSchema_jobStatus(String SchemaName, String JobName, String Status) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_jobStatus_state = By.xpath("//p[contains(text(),'" + SchemaName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName
				+ "']/parent::div/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'"
				+ Status + "')]");
		Assertions.assertElementExists(driver, body_jobStatus_state, true);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Change_scheduleSchema_JobStatus_(String SchemaName, String JobName, String Status) {
		ElementActions.switchToIframe(driver, body_iframe);
		body_jobStatus_state = By.xpath("//p[contains(text(),'" + SchemaName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName
				+ "']/parent::div/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'"
				+ Status + "')]");
		ElementActions.click(driver, body_jobStatus_state);
		popup_scheduleSchema_confirmChangingStatusOk_button = By.xpath("//button[contains(text(),'Ok')]");
		ElementActions.click(driver, popup_scheduleSchema_confirmChangingStatusOk_button);

		ElementActions.switchToDefaultContent(driver);
	}

	public String scheduleSchemaLoad(String description, String schemaName, String loadType, String startByDate,
			String startByTime, String startByTimeZone, String recurrence) {
		ElementActions.switchToIframe(driver, body_iframe);

		String jobName = "Automation_" + "SchemaLoadJob_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_scheduleSchemaLoad_jobName_textBox, jobName);

		ElementActions.type(driver, popup_scheduleSchemaLoad_description_textBox, description);
		ElementActions.select(driver, popup_scheduleSchemaLoad_schema_list, schemaName);
		ElementActions.select(driver, popup_scheduleSchemaLoad_loadType_list, loadType);

		ElementActions.type(driver, popup_scheduleSchemaLoad_startByDate_textBox, startByDate);
		ElementActions.type(driver, popup_scheduleSchemaLoad_startByTime_textBox, startByTime);
		ElementActions.select(driver, popup_scheduleSchemaLoad_startByTimeZone_textBox, startByTimeZone);

		popup_scheduleSchemaLoad_recurrenceFrequency_radioButton = By
				.xpath("//ng-form[@name='$ctrl.scheduleForm']//parent::label[normalize-space()='" + recurrence
						+ "']/input[@type='radio']");
		ElementActions.click(driver, popup_scheduleSchemaLoad_recurrenceFrequency_radioButton);

		ElementActions.click(driver, popup_scheduleSchemaLoad_schedule_button);
		ElementActions.switchToDefaultContent(driver);
		return jobName;
	}

	/**
	 * 
	 * @param Type
	 *            Minute Hour Day
	 * @param Number
	 *            Min "min='1' - max='59'" Hour"min='1' - max='23'" Days"min='1' -
	 *            max='31'"
	 */
	public void JobScreen_Assert_dailyRecurrence_RepeatType(String Type, String Number) {
		ElementActions.switchToIframe(driver, body_iframe);
		popup_scheduleSchema_dailyRecurrence_Type = By
				.xpath("//div[@ng-switch-when='Daily']//option[@value='" + Type + "']");
		By popup_scheduleSchema_dailyRecurrence_number = By.xpath(
				"//div[@ng-switch-when='Daily']//select[@ng-options='value as value for value in $ctrl.minutes']//option[contains(.,'"
						+ Number + "')]");
		Assertions.assertElementAttribute(driver, popup_scheduleSchema_dailyRecurrence_Type, "ng-selected", "true",
				true);
		switch (Type) {
		case ("Minute"):
			Assertions.assertElementAttribute(driver, popup_scheduleSchema_dailyRecurrence_number, "text", Number,
					true);
			break;
		case ("Day"):
			String day = ElementActions.getText(driver, popup_scheduleSchema_dailyRecurrence_day_hour);
			Assertions.assertEquals(Number, day, true);
			break;
		case ("Hour"):
			String hour = ElementActions.getText(driver, popup_scheduleSchema_dailyRecurrence_day_hour);
			Assertions.assertEquals(Number, hour, true);
			break;
		}
	}
}
