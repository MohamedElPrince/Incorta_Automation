package pageObjectModels.modules.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class Schemas_SchemaList {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_schemas_schemaList");

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

	By popup_newSchema_schemaName_textBox = By.xpath("//input[@ng-model='$parent.schemaName']");
	By popup_newSchema_schemaDescription_textBox = By.xpath("//textarea[@ng-model='$parent.schemaDescription']");
	By popup_newSchema_create_button = By.xpath("//button[@type='submit'][contains(text(),'Create')]");
	By popup_newSchema_cancel_button = By.xpath("//button[@ng-click='modal.closeMe()']");;

	//// Functions
	public Schemas_SchemaList(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_schemaListTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_schemaListTabHeader_link, "class", "selectedTab", true);
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
}
