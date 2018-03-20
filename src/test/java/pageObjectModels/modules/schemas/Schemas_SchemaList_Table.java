package pageObjectModels.modules.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;

public class Schemas_SchemaList_Table {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));

	//// Elements
	By header_tableName_textBox = By.xpath("//input[@ng-model='table._name']");
	By body_loadFilter_textBox = By.xpath("//textArea[@placeholder='Load Filter']");

	By popup_addDatasource_header_label = By.xpath("//div[@class='btf-modal-title']/span[text()='Data Source']");
	By popup_addDatasource_datasourceImage_span;
	By popup_addDatasource_datasource_list = By.xpath("//select[@ng-model='tableDataSource._source']");
	By popup_addDatasource_fileType_list = By.xpath("//select[@name='fileType']");
	By popup_addDatasource_incremental_toggle = By.xpath("//span[@class='switch-text']");
	By popup_addDatasource_incrementalToggle_span = By.xpath("//span[@class='switch-text']/..");
	// By popup_addDatasource_fetchSize_textBox =
	// By.xpath("//input[@name='fetchsize']");
	// By popup_addDatasource_query_textBox =
	// By.xpath("//div[@class='ace_content']"); //
	// textarea[contains(@class,'ace_text-input')][contains(@class,'user-success')]
	By popup_addDatasource_selectFile_button = By.xpath("//button[@ng-click='openFileBrowser(property)']");
	By popup_addDatasource_dateFormat_list = By.xpath("//select[@name='dateformat']");
	By popup_addDatasource_timestampFormat_list = By.xpath("//select[@name='timestampFormat']");
	By popup_addDatasource_characterSet_list = By.xpath("//select[@name='charset']");
	By popup_addDatasource_separator_list = By.xpath("//select[@name='sep']");
	By popup_addDatasource_add_button = By.xpath("//a[contains(@class,'save')][contains(.,'Add')]");

	By popup_addFileFromLocalFiles_header_label = By
			.xpath("//div[@class='btf-modal-title']/span[text()='Add File from Local Files']");
	By popup_addFileFromLocalFiles_name_link;
	By popup_addFileFromLocalFiles_add_button = By.xpath("//button[@ng-click='addItem()']");

	//// Functions
	public Schemas_SchemaList_Table(WebDriver driver) {
		this.driver = driver;
	}

	public void Assert_AddDatasourcePopupIsDisplayed() {
		CustomAssertions.cAssertElementExists(driver, popup_addDatasource_header_label, true);
	}

	public void Assert_correctDatasourceIsSelected(String dataSourceType) { // span background is white for the selected
																			// datasource
		String imageSrc = "invalid";

		switch (dataSourceType) {
		case "SQL Database":
			imageSrc = "typeName=sql&subTypeName=drill";
			break;
		case "File System":
			imageSrc = "typeName=fs&subTypeName=box";
			break;
		case "Kafka":
			imageSrc = "typeName=kafka&subTypeName=kafka";
			break;
		case "NetSuite":
			imageSrc = "typeName=netsuite&subTypeName=netsuite";
			break;
		case "SalesForce":
			imageSrc = "typeName=salesforce&subTypeName=salesforce";
			break;
		case "ServiceNow":
			imageSrc = "typeName=servicenow&subTypeName=servicenow";
			break;
		case "Zuora":
			imageSrc = "typeName=zuora&subTypeName=zuora";
			break;
		case "Materialized View":
			imageSrc = "transformer.png";
			break;
		default:
			break;
		}

		popup_addDatasource_datasourceImage_span = By.xpath("//img[contains(@src,'" + imageSrc + "')]/../..");
		CustomAssertions.cAssertElementAttribute(driver, popup_addDatasource_datasourceImage_span, "style",
				".*(background: white;).*", true);
	}

	private void SelectDataFile(String dataFileName, String dataFileExtension) {
		ElementActions.click(driver, popup_addDatasource_selectFile_button);
		popup_addFileFromLocalFiles_name_link = By
				.xpath("//span[@title='" + dataFileName + "." + dataFileExtension + "']");
		ElementActions.click(driver, popup_addFileFromLocalFiles_name_link);
		ElementActions.click(driver, popup_addFileFromLocalFiles_add_button);
	}

	public void AddDataFile(String dataSource, String fileType, boolean incremental, String selectFile_name,
			String selectFile_extension, String dateFormat, String timestampFormat, String characterSet,
			String separator) {
		ElementActions.select(driver, popup_addDatasource_datasource_list, dataSource);
		ElementActions.select(driver, popup_addDatasource_fileType_list, fileType);

		String incrementalToggleStyle = ElementActions.getAttribute(driver, popup_addDatasource_incrementalToggle_span,
				"style");
		if (incremental && !incrementalToggleStyle.contains("checked")) {
			ElementActions.click(driver, popup_addDatasource_incremental_toggle);
		}
		SelectDataFile(selectFile_name, selectFile_extension);
		ElementActions.select(driver, popup_addDatasource_dateFormat_list, dateFormat);
		ElementActions.select(driver, popup_addDatasource_timestampFormat_list, timestampFormat);
		ElementActions.select(driver, popup_addDatasource_characterSet_list, characterSet);
		ElementActions.select(driver, popup_addDatasource_separator_list, separator);

		ElementActions.click(driver, popup_addDatasource_add_button);
	}

	public String SetTableName() {
		String newTableName = "Automation" + "_DataFileTable_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, header_tableName_textBox, newTableName);
		return newTableName;
	}

	public void SetLoadFilter() {
		ElementActions.type(driver, body_loadFilter_textBox, testDataReader.getCellData("DataFileLoadFilter"));
	}

}
