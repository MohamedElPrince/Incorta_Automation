package pageObjectModels.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;

import com.google.common.base.Verify;
import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;
import com.shaftEngine.validationsLibrary.Verifications;

public class SchemaLoads {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_scheduler_schemas");

	//// Elements
	By header_schemasTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Schema Loads']");
	By body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']");
	By popup_schema_schedule_Save_Changes_button = By
			.xpath("//button[@type='button'][contains(text(),'Save Changes')]");
	By body_name_link;
	// By body_owner_link;
	By body_schema_link;
	By body_status_label;
	By body_Job_status_label;
	By body_schemajobName_link;
	By popup_schema_schedule_radio_button;
	By popup_schema_schedule_weekly_Day_check_box;
	By popup_schema_schedule_LoadType_dropdownMenu;
	By body_jobStatus_state;
	By popup_Confirm_Changing_Status_ok_button;

	// Confirm Delete popup view
	By popup_schema_schedule_Delete_button = By
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
	public SchemaLoads(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_schemasTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_schemasTabHeader_link, "class", "selectedTab", true);
	}

	public void ChangeJobStatus_Filter(String status) {
		ElementActions.select(driver, body_jobStatus_list, status);
	}
	
	public void Verify_jobStatusOptionIsDisplayed(String jobStatusOption) {
		
		body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']/option[contains(.,'" + jobStatusOption + "')]");
		//Verifications.verifyElementAttribute(driver, body_jobStatus_list, "text",jobStatusOption, true);
		Verifications.verifyElementExists(driver, body_jobStatus_list, true);
	}

	public void Assert_nameIsDisplayed(String name) {
		body_name_link = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name + "']");
		Assertions.assertElementExists(driver, body_name_link, true);
	}

	public void Assert_jobStatusIsCorrect(String name, String expectedStatus) {
		body_status_label = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name
				+ "']/parent::div[contains(@class,'userName')]/following-sibling::div[contains(@class,'dataConnectionLink')]");
		Assertions.assertElementAttribute(driver, body_status_label, "Text", expectedStatus, true);
	}

	public void Assert_SchemaJob_Created(String job_name, String schema_name) {
		body_Job_status_label = By.xpath("//p[@title='" + job_name
				+ "']/parent::div[contains(@class,'userName')]/following-sibling::div[contains(@class,'userName')]//p[contains(text(),'"
				+ schema_name + "')]");
		Assertions.assertElementAttribute(driver, body_Job_status_label, "Text", schema_name, true);
	}

	public void Assert_SchemaJob_Deleted(String job_name, String schema_name) {
		body_schemajobName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schema_name + "')]/p/parent::div/preceding-sibling::div");
		Assertions.assertElementExists(driver, body_schemajobName_link, false);
	}

	public void select_schemaName(String schemaName) {
		body_schemajobName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]");
		ElementActions.click(driver, body_schemajobName_link);

	}
	
	
	public void select_schemaNameCheckBox(String schemaJobName) {
		
		body_schemajobName_link = By.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"+ schemaJobName + "')]/p/parent::div/preceding-sibling::div");
		ElementActions.click(driver, body_schemajobName_link);
		
		
		
	}

	public void select_schemaName(String jobName, String schemaName) {

		body_schemajobName_link = By.xpath("//p[contains(text(),'" + schemaName
				+ "')]/ancestor::div[contains(@class,'userName')]/preceding-sibling::div[contains(@class,'userName')]//p[@title='"
				+ jobName + "']");
		ElementActions.click(driver, body_schemajobName_link);

	}

	
	
	/**
	 * 
	 * @param Radio_Button_Name
	 * 
	 * Daily
	 * Weekly
	 * Monthly
	 * No Recurrence
	 * 
	 */
	public void Select_schema_scheduler_radio_button(String Radio_button_name) {

		popup_schema_schedule_radio_button = By.xpath("//input[@type='radio'][@value= '" + Radio_button_name + "']");
		ElementActions.click(driver, popup_schema_schedule_radio_button);
	}

/**
 * 
 * @param Radio_Button_Name
 * 
 * Daily
 * Weekly
 * Monthly
 * No Recurrence
 * 
 */
	public void Assert_SchemaScheduler_Radio_Button_selected(String Radio_Button_Name) {

		popup_schema_schedule_radio_button = By.xpath("//input[@type='radio'][@value= '" + Radio_Button_Name + "']");
		Assertions.assertElementAttribute(driver, popup_schema_schedule_radio_button, "checked", "true", true);

	}

	public void Select_SchemaSchedule_Weekly_day_CheckBox(String Day) {

		popup_schema_schedule_weekly_Day_check_box = By
				.xpath("//div//input[@ng-model='w.selected'][@value='" + Day + "']");

		ElementActions.click(driver, popup_schema_schedule_weekly_Day_check_box);
	}

	public void Assert_SchemaSchedule_Weekly_day_CheckBox(String Day) {

		popup_schema_schedule_weekly_Day_check_box = By.xpath("//input[@ng-model='w.selected'][@value='" + Day + "']");

		Assertions.assertElementAttribute(driver, popup_schema_schedule_weekly_Day_check_box, "checked", "true", true);

	}

	public void Assert_SchemaSchedule_LoadType_dropdownMenu(String LoadType) {

		popup_schema_schedule_LoadType_dropdownMenu = By
				.xpath("//select[@name='loadType']//option[@value='" + LoadType + "']");
		Assertions.assertElementAttribute(driver, popup_schema_schedule_LoadType_dropdownMenu, "value", LoadType, true);

	}

	public void Select_SchemaSchedule_LoadType_dropdownMenu(String LoadType) {

		popup_schema_schedule_LoadType_dropdownMenu = By
				.xpath("//select[@name='loadType']//option[@value='" + LoadType + "']");
		ElementActions.click(driver, popup_schema_schedule_LoadType_dropdownMenu);

	}

	public void Schema_Schedule_Click_Save_Changes_button_Button() {

		ElementActions.click(driver, popup_schema_schedule_Save_Changes_button);

	}

	public void Select_Schema_Schedule_Click_Delete_Button() {

		ElementActions.click(driver, popup_schema_schedule_Delete_button);

	}

	public void Schedule_Schema_JobStatus_Check(String SchemaName, String JobName, String Status) {
		body_jobStatus_state = By.xpath("//p[contains(text(),'" + SchemaName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName
				+ "']/parent::div/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'"
				+ Status + "')]");
		Assertions.assertElementExists(driver, body_jobStatus_state, true);
	}

	public void Schedule_Schema_JobStatus_Change(String SchemaName, String JobName, String Status) {
		body_jobStatus_state = By.xpath("//p[contains(text(),'" + SchemaName
				+ "')]/parent::a/parent::div/preceding-sibling::div" + "/p[@title='" + JobName
				+ "']/parent::div/following-sibling::div[@class='dataConnectionLink left']" + "/a[contains(text(),'"
				+ Status + "')]");
		ElementActions.click(driver, body_jobStatus_state);
		popup_Confirm_Changing_Status_ok_button = By.xpath("//button[contains(text(),'Ok')]");
		ElementActions.click(driver, popup_Confirm_Changing_Status_ok_button);

	}

	public String scheduleSchemaLoad(String description, String schemaName, String loadType, String startByDate,
			String startByTime, String startByTimeZone, String recurrence) {

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
		return jobName;
	}
}
