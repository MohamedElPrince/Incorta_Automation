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
	String popup_schema_schedule_Error_Message = replace_regularExpression(
			"INC_004010050:Another SCHEDULER with the same name [Automation_ScheduleSchema_Load] already exists.");
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
	By body_OwnerName_link;

	By popup_scheduleSchema_header = By.xpath("//span[contains(@class,'ng-scope')][text()='Schedule Schema Load']");
	By popup_scheduleSchema_view = By.id("send-dashboard-modal");
	By popup_scheduleSchema_label;
	By popup_scheduleSchema_label_textBox;
	By popup_scheduleSchema_JobName_textBox = By.xpath("//input[contains(@placeholder,'Job Name')]");
	By popup_scheduleSchema_description_textBox;
	By popup_scheduleSchema_button = By.xpath("//button[@type='button'][contains(text(),'Schedule')]");
	By popup_scheduleSchema_button_disabled = By
			.xpath("//button[@type='button'][@disabled='disabled'][contains(text(),'Schedule')]");
	By popup_scheduleSchema_errorMessageBody_label = By
			.xpath("//div[contains(@class,'text-center')]/child::div[@class='ng-binding']");
	By popup_scheduleSchema_radioButton;
	By popup_scheduleSchema_weeklyDay_checkBox;
	By popup_scheduleSchema_loadType_dropDownMenu;
	By popup_scheduleSchema_dailyRecurrence_Type;
	By popup_scheduleSchema_dailyRecurrence_minute = By
			.xpath("//div[@ng-switch-when='Daily']//select[@ng-options='value as value for value in $ctrl.minutes']");
	By popup_scheduleSchema_dailyRecurrence_day_hour = By
			.xpath("//div[@ng-switch-when='Daily']//input[@type='number']");

	By popup_newSchema_schemaName_textBox = By.xpath("//input[@ng-model='$parent.schemaName']");
	By popup_newSchema_schemaDescription_textBox = By.xpath("//textarea[@ng-model='$parent.schemaDescription']");
	By popup_newSchema_create_button = By.xpath("//button[@type='submit'][contains(text(),'Create')]");
	By popup_newSchema_cancel_button = By.xpath("//button[@ng-click='modal.closeMe()']");

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
		Assertions.assertElementExists(driver, popup_scheduleSchema_view, true);
		Assertions.assertElementExists(driver, popup_scheduleSchema_header, true);
	}

	public void assert_schemaSchedule_labelName(String schedule_label_field) {

		popup_scheduleSchema_label = By.xpath("//label[contains(text(),'" + schedule_label_field + "')]");
		Assertions.assertElementExists(driver, popup_scheduleSchema_label, true);

		popup_scheduleSchema_label_textBox = By.xpath("//input[contains(@placeholder,'" + schedule_label_field + "')]");
		Assertions.assertElementExists(driver, popup_scheduleSchema_label_textBox, true);

	}

	public void Assert_scheduleSchema_radioButton_selected(String Radio_Button_Name) {

		popup_scheduleSchema_radioButton = By.xpath("//input[@type='radio'][@value= '" + Radio_Button_Name + "']");
		Assertions.assertElementAttribute(driver, popup_scheduleSchema_radioButton, "checked", "true", true);

	}

	public void Select_scheduleSchema_radioButton(String Radio_button_name) {

		popup_scheduleSchema_radioButton = By.xpath("//input[@type='radio'][@value= '" + Radio_button_name + "']");
		ElementActions.click(driver, popup_scheduleSchema_radioButton);
	}

	public void Select_scheduleSchema_weeklyDay_CheckBox(String Day) {

		popup_scheduleSchema_weeklyDay_checkBox = By
				.xpath("//div//input[@ng-model='w.selected'][@value='" + Day + "']");

		ElementActions.click(driver, popup_scheduleSchema_weeklyDay_checkBox);

	}

	public void select_scheduleSchema_loadType_dropDownMenu(String LoadType) {

		popup_scheduleSchema_loadType_dropDownMenu = By
				.xpath("//select[@name='loadType']//option[@value='" + LoadType + "']");
		// ElementActions.select(driver, popup_schema_schedule_LoadType_dropdownMenu,
		// LoadType);
		ElementActions.click(driver, popup_scheduleSchema_loadType_dropDownMenu);

	}

	public void Assert_scheduleSchema_textBox_empty(String label_name) {

		popup_scheduleSchema_label_textBox = By
				.xpath("//input[contains(@class,'ng-empty')][contains(@placeholder,'" + label_name + "')]");
		Assertions.assertElementExists(driver, popup_scheduleSchema_label_textBox, true);

	}

	public void Type_scheduleSchema_jobNameOrDescription(String schedule_label_field, String schedule_label_data) {

		popup_scheduleSchema_label_textBox = By.xpath("//input[contains(@placeholder,'" + schedule_label_field + "')]");

		ElementActions.type(driver, popup_scheduleSchema_label_textBox, schedule_label_data);

	}

	public String autoType_scheduleSchema_jobName() {
		String newScheduleSchema_jobName = "Automation" + "_Schedule_Schema_"
				+ String.valueOf(System.currentTimeMillis());
		ElementActions.click(driver, popup_scheduleSchema_JobName_textBox);
		ElementActions.type(driver, popup_scheduleSchema_JobName_textBox, newScheduleSchema_jobName);
		return newScheduleSchema_jobName;
	}

	public void click_scheduleSchema_scheduleButton() {

		ElementActions.click(driver, popup_scheduleSchema_button);

	}

	public void Assert_scheduleSchema_scheduleButton_disabled() {

		Assertions.assertElementExists(driver, popup_scheduleSchema_button_disabled, true);

	}

	public void Assert_scheduleSchema_jobNameDublicate_errorMessage() {

		Assertions.assertElementAttribute(driver, popup_scheduleSchema_errorMessageBody_label, "text",
				popup_schema_schedule_Error_Message, true);

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

	public void click_schemaName(String schemaName) {
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
		String arr[] = { "[", "]" };
		text = JavaActions.replaceRegex(arr, text);
		return text;
	}

	/**
	 * 
	 * @param Type
	 *            Minute Hour Day
	 * @param Number
	 *            Min "min='1' - max='59'" Hour"min='1' - max='23'" Days"min='1' -
	 *            max='31'"
	 */
	public void scheduleSchema_dailyRecurrence_repeatType(String Type, String Number) {
		popup_scheduleSchema_dailyRecurrence_Type = By
				.xpath("//div[@ng-switch-when='Daily']//select//option[@value='" + Type + "']");
		ElementActions.click(driver, popup_scheduleSchema_dailyRecurrence_Type);
		switch (Type) {
		case ("Minute"):
			ElementActions.select(driver, popup_scheduleSchema_dailyRecurrence_minute, Number);
			break;
		case ("Day"):
			ElementActions.type(driver, popup_scheduleSchema_dailyRecurrence_day_hour, Number);
			break;
		case ("Hour"):
			ElementActions.type(driver, popup_scheduleSchema_dailyRecurrence_day_hour, Number);
			break;
		}

	}
}
