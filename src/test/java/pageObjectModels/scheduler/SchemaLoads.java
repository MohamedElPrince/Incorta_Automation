package pageObjectModels.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class SchemaLoads {
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
	By body_schemaName_link;
	By popup_schema_schedule_radio_button;
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

	public void ChangeJobStatus(String status) {
		ElementActions.select(driver, body_jobStatus_list, status);
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
		body_Job_status_label = By.xpath("//p[@title='"+ job_name +"']/parent::div[contains(@class,'userName')]/following-sibling::div[contains(@class,'userName')]//p[contains(text(),'" + schema_name + "')]");
		Assertions.assertElementAttribute(driver, body_Job_status_label, "Text", schema_name, true);
	}

	
	
	

	public void select_schemaName(String schemaName) {
		body_schemaName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]/p");
		ElementActions.click(driver, body_schemaName_link);
		
		
	}
	
public void Assert_SchemaScheduler_Radio_Button_selected(String Radio_Button_Name) {
		
		popup_schema_schedule_radio_button = By.xpath("//input[@type='radio'][@value= '" + Radio_Button_Name + "']");		
		Assertions.assertElementAttribute(driver, popup_schema_schedule_radio_button, "checked", "true", true);
		
		
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
