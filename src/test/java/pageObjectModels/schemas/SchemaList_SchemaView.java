package pageObjectModels.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class SchemaList_SchemaView {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

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

	//By popup_SchemaSettings_SharingTab = By.xpath("//ul[@class='modalTabLinks']//a[@ng-click='tabState = 'sharing'']");
	By popup_SchemaSettings_SharingTab = By.xpath("//ul[@class='modalTabLinks']//a[contains(string(),'Sharing')]/parent::li");
	
	By popup_SchemaSettings_SharingTab_Add_Button = By.xpath("//a[contains(@class,'usersHeadAdd right')]");
	By popup_SchemaSettings_SharingTab_CanEdit = By.xpath("//h5[contains(string(), 'Can Edit')]");
	By popup_SchemaSettings_SharingTab_ClickSearchField = By.xpath("//div[@class='shareSearch ng-scope']/input[@type=\"text\"]");
	By popup_SchemaSettings_SharingTab_SelectFromSearchField;
	By popup_SchemaSettings_SharingTab_SaveButton = By.xpath("//button[contains(string(), 'Save')]");
	By popup_SchemaSettings_SharingTab_UsersSharedWith;
	By popup_SchemaSettings_SharingTab_UsersCanEdit;
	
	//// Functions
	public SchemaList_SchemaView(WebDriver driver) {
		this.driver = driver;
	}

	// Assert_AllPageElementsExist
	public void Assert_schemaNameIsDisplayed(String schemaName) {
		Assertions.assertElementAttribute(driver, header_schemaName_label, "text", schemaName, true);
	}

	public String GetNewestTableName() {
		return ElementActions.getText(driver, body_lastTableName_label);
	}

	public void Assert_tableNameIsDisplayed(String tableName) {
		body_tableName_label = By
				.xpath("//div[@id='" + tableName + "']//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']");
		Assertions.assertElementExists(driver, body_tableName_label, true);
	}

	public void Assert_tableAttributeIsCorrect(String tableName, String tableAttribute, String expectedValue) {
		body_tableAttribute_label = By.xpath("//div[@id='" + tableName + "']//h5[normalize-space(.)='" + tableAttribute
				+ "']//following-sibling::p[@class='ng-binding']");
		Assertions.assertElementAttribute(driver, body_tableAttribute_label, "Text", expectedValue, true);
	}

	public void Wizard_AddDataSourceTable(String dataSourceName, boolean createImplicitJoins, String dataSourceType,
			String tableName) {

		String columnName = testDataReader.getColumnNameUsingRowNameAndCellData("DataSourceType", dataSourceType);
		// ReportManager.log("columnName: [" + columnName + "]");
		String databaseName = testDataReader.getCellData("DatabaseName", columnName);
		// ReportManager.log("databaseName: [" + databaseName + "]");

		ElementActions.select(driver, popup_schemaWizard_dataSource_list, dataSourceName);
		ElementActions.click(driver, popup_schemaWizard_next_button);

		popup_schemaWizard_dataName_label = By
				.xpath("//span[contains(@class,'dataName')][text()='" + databaseName + "']");
		ElementActions.click(driver, popup_schemaWizard_dataName_label);
		ElementActions.type(driver, popup_schemaWizard_search_textBox, tableName);
		popup_schemaWizard_dataName_label = By.xpath("//span[contains(@class,'dataName')][text()='" + tableName + "']");
		ElementActions.click(driver, popup_schemaWizard_dataName_label);
		ElementActions.click(driver, popup_schemaWizard_next_button);

		Assert_wizardWelcomeTextIsDisplayed();
		if (createImplicitJoins
				& !ElementActions.getAttribute(driver, popup_schemaWizard_createImplicitJoins_checkbox, "class")
						.contains("ng-not-empty")) {
			ElementActions.click(driver, popup_schemaWizard_createImplicitJoins_checkbox);
		}
		ElementActions.click(driver, popup_schemaWizard_finish_button);
	}

	public void Assert_wizardWelcomeTextIsDisplayed() {
		Assertions.assertElementExists(driver, popup_schemaWizard_welcomeText_label, true);
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
			ElementActions.waitForTextToChange(driver, header_lastLoadStatus_link, initialLoadStatus, 1);
			currentLoadStatus = ElementActions.getText(driver, header_lastLoadStatus_link);
		} while (currentLoadStatus.equals("(.*" + initialLoadStatus + ".*)")
				|| currentLoadStatus.equals("(.*Loading Data.*)")
				|| currentLoadStatus.equals("(.*Please load data.*)"));
	}

	public void Assert_lastLoadStatusIsUpdated(String initialLoadStatus) {
		Assertions.assertElementAttribute(driver, header_lastLoadStatus_link, "Text", initialLoadStatus, false);
	}

	public void Click_Sharing_Tab()
	{
		ElementActions.click(driver, popup_SchemaSettings_SharingTab);
	}

	public void Click_AddButton_SharingTab()
	{
		ElementActions.click(driver, popup_SchemaSettings_SharingTab_Add_Button);
	}

	public void Schema_Sharing_ClickOnCanEdit()
	{
		ElementActions.click(driver, popup_SchemaSettings_SharingTab_CanEdit);
	}
	
	public void Schema_Sharing_SearchAndSelectUsers(String SearchText)
	{
		popup_SchemaSettings_SharingTab_SelectFromSearchField = By.xpath("//h5[@class='UserData left text-left ng-binding'][contains(text(),'"+SearchText+"')]");
		ElementActions.type(driver, popup_SchemaSettings_SharingTab_ClickSearchField, SearchText);
		ElementActions.click(driver, popup_SchemaSettings_SharingTab_SelectFromSearchField);
	}
	
	public void Click_Save_Button()
	{
		ElementActions.click(driver, popup_SchemaSettings_SharingTab_SaveButton);
	}
	
	public void Assertion_UserSharedWith(String SearchText)
	{
		popup_SchemaSettings_SharingTab_UsersSharedWith = By.xpath("//div[@class='folderUserRow ng-scope']//h5[contains(string(), '"+SearchText+"')]");
		Assertions.assertElementExists(driver, popup_SchemaSettings_SharingTab_UsersSharedWith, true);
	}
	/**
	 * 
	 * @param SharedWithUser
	 * @param Permission 
	 * Can Edit
	 * Can View
	 * Can Share
	 */
	public void Assertion_UserCanEdit(String SharedWithUser, String Permission)
	{
		popup_SchemaSettings_SharingTab_UsersCanEdit = 
				By.xpath("//h5[@class='left ng-binding'][contains(string(),'"+SharedWithUser+"')]//parent::div//following-sibling::div/a[contains(string(),'"+Permission+"')]/parent::div");
		Assertions.assertElementExists(driver, popup_SchemaSettings_SharingTab_UsersCanEdit, true);
	}
}
