package java.pageObjectModels.modules.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.io.ExcelReader;

public class Schemas_SchemaList_SchemaView {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));

	//// Elements
	// By header_schemaIcon_image;
	By header_schemaName_label = By.xpath("//div[@class='schema-header']//div[@class='schemaName']");
	// By header_schemaDiscription_label;
	// By header_lastLoadStatus_label;
	// By header_pleaseLoadData_link; // rename
	// By header_loadingTime_label;
	// By header_loadingTimeValue_label;
	// By header_tables_label;
	// By header_tabelsValue_label;
	// By header_joins_label;
	// By header_joinsValue_label;
	// By header_rows_label;
	// By header_rowsValue_label;
	// By header_dataSize_label;
	// By header_dataSizeValue_label;

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

	//// Functions
	public Schemas_SchemaList_SchemaView(WebDriver driver) {
		this.driver = driver;
	}

	// Assert_AllPageElementsExist
	public void Assert_schemaNameIsDisplayed(String schemaName) {
		CustomAssertions.cAssertElementAttribute(driver, header_schemaName_label, "text", schemaName, true);
	}

}
