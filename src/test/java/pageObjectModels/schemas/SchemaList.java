package pageObjectModels.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.supportActionLibrary.JavaActions;
import com.shaftEngine.validationsLibrary.Assertions;

public class SchemaList {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_schemas_schemaList");
	String popup_schema_schedule_Error_Message =replace_regularExpression("INC_004010050:Another SCHEDULER with the same name [Schema Load] already exists.");
	//// Elements
	By header_schemaListTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Schemas']");

	// header_schemaList_label;
	// body_tableHeader_label;
	By body_schemaName_link;
	By body_schemaDetails_button;
	// body_status_label;
	// body_permission_label;
	// body_owner_label;
	// body_lastLoadStatus_label;
	// body_memoryUsed_label;
	By popup_schema_schedule_header = By.xpath("//span[contains(@class,'ng-scope')][text()='Schedule Schema Load']");
	By popup_schema_schedule_View = By.id("send-dashboard-modal");
	By popup_schema_schedule_label;
	By popup_schema_schedule_label_textBox;
	By popup_schema_schedule_JobName_textBox = By.xpath("//input[contains(@placeholder,'Job Name')]");
	By popup_schema_schedule_Description_textBox;
	By popup_schema_schedule_button =  By.xpath("//button[@type='button'][contains(text(),'Schedule')]");
	By popup_schema_schedule_button_disabled = By.xpath("//button[@type='button'][@disabled='disabled'][contains(text(),'Schedule')]");
	By popup_schema_schedule_Error_Message_Body_label = By.xpath("//div[contains(@class,'text-center')]/child::div[@class='ng-binding']");
	By popup_schema_schedule_radio_button;
	By popup_schema_schedule_weekly_Day_check_box;
	By popup_schema_schedule_LoadType_dropdownMenu;
	

	By popup_newSchema_schemaName_textBox = By.xpath("//input[@ng-model='$parent.schemaName']");
	By popup_newSchema_schemaDescription_textBox = By.xpath("//textarea[@ng-model='$parent.schemaDescription']");
	By popup_newSchema_create_button = By.xpath("//button[@type='submit'][contains(text(),'Create')]");
	By popup_newSchema_cancel_button = By.xpath("//button[@ng-click='modal.closeMe()']");
	By body_OwnerName_link;

	//// Functions
	public SchemaList(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_schemaListTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_schemaListTabHeader_link, "class", "selectedTab", true);
	}

	public void Assert_schemaSchedule_popup_is_displayed() {
		Assertions.assertElementExists(driver, popup_schema_schedule_View, true);
		Assertions.assertElementExists(driver, popup_schema_schedule_header, true);
	}

	public void Assert_schemaSchedule_label_Name(String schedule_label_field) {

		popup_schema_schedule_label = By.xpath("//label[contains(text(),'" + schedule_label_field + "')]");
		Assertions.assertElementExists(driver, popup_schema_schedule_label, true);
		
		popup_schema_schedule_label_textBox = By.xpath("//input[contains(@placeholder,'" + schedule_label_field + "')]");
		Assertions.assertElementExists(driver, popup_schema_schedule_label_textBox, true);

	}
	
	
	public void Assert_SchemaScheduler_Radio_Button_selected(String Radio_Button_Name) {
		
		popup_schema_schedule_radio_button = By.xpath("//input[@type='radio'][@value= '" + Radio_Button_Name + "']");		
		Assertions.assertElementAttribute(driver, popup_schema_schedule_radio_button, "checked", "true", true);
		
		
	}
	
	public void Select_schema_scheduler_radio_button(String Radio_button_name) {
		
		popup_schema_schedule_radio_button = By.xpath("//input[@type='radio'][@value= '" + Radio_button_name + "']");
		ElementActions.click(driver, popup_schema_schedule_radio_button);
	}
	
	
	public void Select_SchemaSchedule_Weekly_day_CheckBox(String Day) {
		
		
		popup_schema_schedule_weekly_Day_check_box = By.xpath("//div//input[@ng-model='w.selected'][@value='"+ Day +"']");
		
		ElementActions.click(driver, popup_schema_schedule_weekly_Day_check_box);
		
		
	}
	
	public void Select_SchemaSchedule_LoadType_dropdownMenu(String LoadType){
		
		popup_schema_schedule_LoadType_dropdownMenu = By.xpath("//select[@name='loadType']//option[@value='"+ LoadType+"']");
		//ElementActions.select(driver, popup_schema_schedule_LoadType_dropdownMenu, LoadType);
		ElementActions.click(driver, popup_schema_schedule_LoadType_dropdownMenu);
		
	}
	
	
	
	public void Assert_schemaSchedule_lable_textbox_empty(String label_name) {
		
		
		popup_schema_schedule_label_textBox =By.xpath("//input[contains(@class,'ng-empty')][contains(@placeholder,'" + label_name + "')]");
		Assertions.assertElementExists(driver, popup_schema_schedule_label_textBox, true);
		
		
	}
	
	
	
	public void Type_Schema_Schedule_jobNameOrDescription(String schedule_label_field, String schedule_label_data) {
		
		popup_schema_schedule_label_textBox = By.xpath("//input[contains(@placeholder,'" + schedule_label_field + "')]");
	
		ElementActions.type(driver, popup_schema_schedule_label_textBox, schedule_label_data);
		
	}
	
	
	public String Auto_Type_Schedule_Schema_JobName() {
		String newSchemaSchdule_jobName = "Automation" + "_Schedule_Schema_" + String.valueOf(System.currentTimeMillis());
		ElementActions.click(driver, popup_schema_schedule_JobName_textBox);
		ElementActions.type(driver, popup_schema_schedule_JobName_textBox, newSchemaSchdule_jobName);
		return newSchemaSchdule_jobName;
	}

	
	public void Schema_Schedule_Click_Schedule_Button () {
		
		ElementActions.click(driver, popup_schema_schedule_button);

	}
	
	public void Assert_Schedule_button_disabled() {
		
		Assertions.assertElementExists(driver, popup_schema_schedule_button_disabled, true);
		
	}
	
	public void Assert_dublicate_Schema_Schedule_job_Name_Error_Message() {
		
		
		Assertions.assertElementAttribute(driver, popup_schema_schedule_Error_Message_Body_label, "text", popup_schema_schedule_Error_Message,
				true);
		
		
		
	}
	
	public void Assert_schemaNameIsDisplayed(String schemaName) {
		body_schemaName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]/p");
		Assertions.assertElementExists(driver, body_schemaName_link, true);
	}
	// Assert_statusIsDisplayed
	// Assert_permissionIsDisplayed
	// Assert_ownerIsDisplayed
	// Assert_lastLoadStatusIsDisplayed
	// Assert_memoryUsedIsDisplayed

	public void Click_schemaName(String schemaName) {
		body_schemaName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]/p");
		ElementActions.click(driver, body_schemaName_link);
	}

	public void select_schemaName(String schemaName) {
		body_schemaName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]/p/parent::div/preceding-sibling::div");
		ElementActions.click(driver, body_schemaName_link);
	}

	public void hoverOnSchemaAndClickDetails(String schemaName) {
		body_schemaName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]/p");
		ElementActions.hover(driver, body_schemaName_link);

		body_schemaDetails_button = By
				.xpath("//div[contains(@class,'usersPanel')]//button[contains(text(),'Details') and contains(@href,'/"
						+ schemaName + "/')]");
		ElementActions.click(driver, body_schemaDetails_button);
	}

	public String createNewSchema() {
		return createNewSchema("Automation" + "_Schema_" + String.valueOf(System.currentTimeMillis()), "");
	}

	public String createNewSchema(String schemaName, String schemaDesription) {
		ElementActions.type(driver, popup_newSchema_schemaName_textBox, schemaName);
		ElementActions.type(driver, popup_newSchema_schemaDescription_textBox, schemaDesription);
		ElementActions.click(driver, popup_newSchema_create_button);
		return schemaName;
	}

	public void Assert_schemaNameIsDisplayedAndItsOwnerName(String schemaName, String name) {
		body_schemaName_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'"
						+ schemaName + "')]/p");
		Assertions.assertElementExists(driver, body_schemaName_link, true);

		body_OwnerName_link = By.xpath("//div[contains(@class,'userName') and contains(.,'" + schemaName
				+ "')]/following-sibling::div/p[@class=\"ng-binding\"][contains(.,'" + name + "')]");
		Assertions.assertElementExists(driver, body_OwnerName_link, true);
	}
	
	public String replace_regularExpression(String text) {
		String arr[] = {"[","]"};
		text = JavaActions.replaceRegex(arr, text);
		return text;
	}

}
