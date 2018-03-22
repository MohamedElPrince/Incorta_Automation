package pageObjectModels.modules.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;

public class Scheduler_SchemaLoads {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_scheduler_schemas");

	//// Elements
	By header_schemasTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Schema Loads']");
	By body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']");
	By body_name_link;
	// By body_owner_link;
	By body_schema_link;
	By body_status_label;
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
	public Scheduler_SchemaLoads(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_schemasTabIsSelected() {
		CustomAssertions.cAssertElementAttribute(driver, header_schemasTabHeader_link, "class", "selectedTab", true);
	}

	public void ChangeJobStatus(String status) {
		ElementActions.select(driver, body_jobStatus_list, status);
	}

	public void Assert_nameIsDisplayed(String name) {
		body_name_link = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name + "']");
		CustomAssertions.cAssertElementExists(driver, body_name_link, true);
	}

	public void Assert_jobStatusIsCorrect(String name, String expectedStatus) {
		body_status_label = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name
				+ "']/parent::div[contains(@class,'userName')]/following-sibling::div[contains(@class,'dataConnectionLink')]");
		CustomAssertions.cAssertElementAttribute(driver, body_status_label, "Text", expectedStatus, true);
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
