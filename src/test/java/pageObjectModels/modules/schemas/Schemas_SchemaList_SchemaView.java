package pageObjectModels.modules.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;
import com.shaftEngine.io.ReportManager;

public class Schemas_SchemaList_SchemaView {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));

	//// Elements
	// By header_schemaIcon_image;
	By header_schemaName_label = By.xpath("//div[@class='schema-header']//div[@class='schemaName']");
	// By header_schemaDiscription_label;
	// By header_lastLoadStatusHeader_label;
	By header_lastLoadStatus_link = By
			.xpath("//div[contains(@class,'schemaInfo')]/div[contains(@class,'loadData')]/a/p");
	// By header_loadingTimeHeader_label;
	// By header_loadingTime_label;
	// By header_tablesHeader_label;
	// By header_tables_label;
	// By header_joinsHeader_label;
	// By header_joins_label;
	// By header_rowsHeader_label;
	// By header_rows_label;
	// By header_dataSizeHeader_label;
	// By header_dataSize_label;

	// By body_schemaWizard_button;
	By body_sqlDatabase_button;
	By body_fileSystem_button;
	// By body_kafka_button;
	// By body_netSuite_button;
	// By body_salesForce_button;
	// By body_serviceNow_button;
	// By body_zuora_button;
	// By body_materialized_button;
	// By body_alias_button;

	By body_tableType_image; // div[@id='sales']//img[contains(@class,'fileTypeImg')]
	By body_tableName_label; // div[@id='sales']//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']
	By body_tableAttribute_label;
	By body_dropDownToggle_link; // div[@id='sales']//a[@class='dropdown-toggle']
	By body_dropDownMenuItem_link; // div[@id='sales']//ul[contains(@class,'dropdown-menu')]//a[normalize-space(.)='Table
									// Details']
	By body_lastTableName_label = By.xpath(
			"//section[@id='schemaWrapper']/div[contains(@class,'schemaContent')]/div[last()]//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']");

	By popup_schemaWizard_header_label = By.xpath("//div[@class='btf-modal-title']/h1");
	By popup_schemaWizard_dataSource_list = By
			.xpath("//ng-include/div[contains(@class,'wizardFirstStep')]//select[@name='dataSourceId']");
	By popup_schemaWizard_createImplicitJoins_checkbox = By
			.xpath("//ng-include/div[contains(@class,'wizardFirstStep')]//input[@type='checkbox']"); // when selected
																										// the class
																										// contains
																										// [ng-not-empty]

	By popup_schemaWizard_search_textBox = By.xpath("//input[@ng-model='searchText']");
	By popup_schemaWizard_dataName_label; // span[contains(@class,'dataName')][text()='salesdb']
	By popup_schemaWizard_selectAll_link = By.xpath("//a[@ng-click='selectAllTables()']");

	By popup_schemaWizard_welcomeText_label = By.xpath("//p[@class='wizardWelcomeTxt']"); // p[@class='wizardWelcomeTxt'][contains(normalize-space(.),'Schema
																							// verification finished!
																							// Click Finish to save your
																							// schema.')]

	By popup_schemaWizard_next_button = By.xpath("//button[@type='submit'][contains(normalize-space(),'Next')]");
	By popup_schemaWizard_finish_button = By.xpath("//button[@type='submit'][@ng-click='doFinish()']");

	By popup_dataLoading_load_button = By.xpath("//button[normalize-space(.)='Load']");

	//// Functions
	public Schemas_SchemaList_SchemaView(WebDriver driver) {
		this.driver = driver;
	}

	// Assert_AllPageElementsExist
	public void Assert_schemaNameIsDisplayed(String schemaName) {
		CustomAssertions.cAssertElementAttribute(driver, header_schemaName_label, "text", schemaName, true);
	}

	public String GetNewestTableName() {
		return ElementActions.getText(driver, body_lastTableName_label);
	}

	public void Assert_tableNameIsDisplayed(String tableName) {
		body_tableName_label = By
				.xpath("//div[@id='" + tableName + "']//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']");
		CustomAssertions.cAssertElementExists(driver, body_tableName_label, true);
	}

	public void Assert_tableAttributeIsCorrect(String tableName, String tableAttribute, String expectedValue) {
		body_tableAttribute_label = By.xpath("//div[@id='" + tableName + "']//h5[normalize-space(.)='" + tableAttribute
				+ "']//following-sibling::p[@class='ng-binding']");
		CustomAssertions.cAssertEquals(expectedValue, ElementActions.getText(driver, body_tableAttribute_label), true);
	}

	public void Wizard_AddDataSourceTable(String dataSourceName, boolean createImplicitJoins, String dataSourceType,
			String tableName) {

		String columnName = testDataReader.getColumnNameUsingRowNameAndCellData("DataSourceType", dataSourceType);
		ReportManager.log("columnName: [" + columnName + "]");
		String databaseName = testDataReader.getCellData("DatabaseName", columnName);
		ReportManager.log("databaseName: [" + databaseName + "]");

		ElementActions.select(driver, popup_schemaWizard_dataSource_list, dataSourceName);
		if (createImplicitJoins
				& !ElementActions.getAttribute(driver, popup_schemaWizard_createImplicitJoins_checkbox, "class")
						.contains("ng-not-empty")) {
			ElementActions.click(driver, popup_schemaWizard_createImplicitJoins_checkbox);
		}
		ElementActions.click(driver, popup_schemaWizard_next_button);

		popup_schemaWizard_dataName_label = By
				.xpath("//span[contains(@class,'dataName')][text()='" + databaseName + "']");
		ElementActions.click(driver, popup_schemaWizard_dataName_label);
		ElementActions.type(driver, popup_schemaWizard_search_textBox, tableName);
		popup_schemaWizard_dataName_label = By.xpath("//span[contains(@class,'dataName')][text()='" + tableName + "']");
		ElementActions.click(driver, popup_schemaWizard_dataName_label);
		ElementActions.click(driver, popup_schemaWizard_next_button);

		Assert_wizardWelcomeTextIsDisplayed();
		ElementActions.click(driver, popup_schemaWizard_finish_button);
	}

	public void Assert_wizardWelcomeTextIsDisplayed() {
		CustomAssertions.cAssertElementExists(driver, popup_schemaWizard_welcomeText_label, true);
	}

	// public void Assert_WizardWelcomeTextIsCorrect() {}

	public String GetLastLoadStatus() {
		return ElementActions.getText(driver, header_lastLoadStatus_link);
	}

	public void confirmLoadingData() {
		ElementActions.click(driver, popup_dataLoading_load_button);
	}

	public void waitForDataToBeLoaded(String initialLoadStatus) {
		String currentLoadStatus;
		do {
			ElementActions.waitForTextToChange(driver, header_lastLoadStatus_link, initialLoadStatus);
			currentLoadStatus = ElementActions.getText(driver, header_lastLoadStatus_link);
		} while (currentLoadStatus.equals("(.*" + initialLoadStatus + ".*)")
				|| currentLoadStatus.equals("(.*Loading Data.*)")
				|| currentLoadStatus.equals("(.*Please load data.*)"));
	}

	public void Assert_lastLoadStatusIsUpdated(String initialLoadStatus) {
		CustomAssertions.cAssertEquals(initialLoadStatus, ElementActions.getText(driver, header_lastLoadStatus_link),
				false);
	}

}
