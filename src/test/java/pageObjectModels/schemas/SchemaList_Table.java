package pageObjectModels.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;

public class SchemaList_Table {
    //// Variables
    private WebDriver driver;
    private ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

    //// Elements
    private By header_tableName_textBox = By.xpath("//input[@ng-model='table._name']");
    private By body_iframe = By.xpath("//iframe[@title='Legacy Web']");
    private By body_loadFilter_textBox = By.xpath("//textArea[@placeholder='Load Filter']");

    private By popup_addDatasource_header_label = By
	    .xpath("//div[@class='btf-modal-title']/span[text()='Data Source']");
    private By popup_addDatasource_datasourceImage_span;
    private By popup_addDatasource_datasource_list = By.xpath("//select[@ng-model='tableDataSource._source']");
    private By popup_addDatasource_fileType_list = By.xpath("//select[@name='fileType']");
    private By popup_addDatasource_incremental_toggle = By.xpath("//span[@class='switch-text']");
    private By popup_addDatasource_incrementalToggle_span = By.xpath("//span[@class='switch-text']/..");
    private By popup_addDatasource_selectFile_button = By.xpath("//button[@ng-click='openFileBrowser(property)']");
    private By popup_addDatasource_dateFormat_list = By.xpath("//select[@name='dateformat']");
    private By popup_addDatasource_timestampFormat_list = By.xpath("//select[@name='timestampFormat']");
    private By popup_addDatasource_characterSet_list = By.xpath("//select[@name='charset']");
    private By popup_addDatasource_separator_list = By.xpath("//select[@name='sep']");
    private By popup_addDatasource_add_button = By.xpath("//a[contains(@class,'save')][contains(.,'Add')]");

    private By popup_addFileFromLocalFiles_name_link;
    private By popup_addFileFromLocalFiles_add_button = By.xpath("//button[@ng-click='addItem()']");

    //// Functions
    public SchemaList_Table(WebDriver driver) {
	this.driver = driver;
    }

    public void Assert_AddDatasourcePopupIsDisplayed() {
	ElementActions.switchToIframe(driver, body_iframe);
	Assertions.assertElementExists(driver, popup_addDatasource_header_label, true);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assert_correctDatasourceIsSelected(String dataSourceType) { // span background is white for the selected
									    // datasource
	ElementActions.switchToIframe(driver, body_iframe);
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

	popup_addDatasource_datasourceImage_span = By
		.xpath("//div[contains(@class,'flex-box-wrap')]//img[contains(@src,'" + imageSrc + "')]/../..");
	Assertions.assertElementAttribute(driver, popup_addDatasource_datasourceImage_span, "style",
		".*(background:.*white).*", true);
	ElementActions.switchToDefaultContent(driver);
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
	ElementActions.switchToIframe(driver, body_iframe);
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
	ElementActions.switchToDefaultContent(driver);
    }

    public String SetTableName() {
	ElementActions.switchToIframe(driver, body_iframe);
	String newTableName = "Automation" + "_DataFileTable_" + String.valueOf(System.currentTimeMillis());
	ElementActions.type(driver, header_tableName_textBox, newTableName);
	ElementActions.switchToDefaultContent(driver);
	return newTableName;
    }

    public void SetLoadFilter() {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.type(driver, body_loadFilter_textBox, testDataReader.getCellData("DataFileLoadFilter"));
	ElementActions.switchToDefaultContent(driver);
    }

}
