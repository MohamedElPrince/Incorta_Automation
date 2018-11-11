package pageObjectModels.schemas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;

public class SchemaList_SchemaView {
    //// Variables
    private WebDriver driver;
    private ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

    //// Elements
    private By header_schemaName_label = By.xpath("//div[@class='schema-header']//div[@class='schemaName']");
    private By header_lastLoadStatus_link = By.xpath("//*[contains(@ng-if,'schemaStatus.lastLoadState')]");

    private By body_iframe = By.xpath("//iframe[@title='Legacy Web']");

    private By body_tableName_label; // div[@id='sales']//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']
    private By body_tableAttribute_label;

    private By body_lastTableName_label = By.xpath(
	    "//section[@id='schemaWrapper']/div[contains(@class,'schemaContent')]/div[last()]//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']");

    private By popup_schemaWizard_dataSource_list = By
	    .xpath("//ng-include/div[contains(@class,'wizardFirstStep')]//select[@name='dataSourceId']");
    private By popup_schemaWizard_createImplicitJoins_checkbox = By
	    .xpath("//ng-include/div[contains(@class,'wizardFirstStep')]//input[@type='checkbox']"); // when selected
												     // the class
												     // contains
												     // [ng-not-empty]

    private By popup_schemaWizard_search_textBox = By.xpath("//input[@ng-model='searchText']");
    private By popup_schemaWizard_dataName_label; // span[contains(@class,'dataName')][text()='salesdb']

    private By popup_schemaWizard_welcomeText_label = By.xpath("//p[@class='wizardWelcomeTxt']");
    // p[@class='wizardWelcomeTxt'][contains(normalize-space(.),'Schema verification
    // finished! Click Finish to save your schema.')]

    private By popup_schemaWizard_next_button = By
	    .xpath("//button[@type='submit'][contains(normalize-space(),'Next')]");
    private By popup_schemaWizard_finish_button = By.xpath("//button[@type='submit'][@ng-click='doFinish()']");

    private By popup_dataLoading_load_button = By.xpath("//button[normalize-space(.)='Load']");

    private By popup_SchemaSettings_SharingTab = By
	    .xpath("//ul[@class='modalTabLinks']//a[contains(string(),'Sharing')]/parent::li");

    private By popup_SchemaSettings_SharingTab_Add_Button = By.xpath("//a[contains(@class,'usersHeadAdd right')]");
    private By popup_SchemaSettings_SharingTab_Permission;
    private By popup_SchemaSettings_SharingTab_ClickSearchField = By
	    .xpath("//div[@class='shareSearch ng-scope']/input[@type=\"text\"]");
    private By popup_SchemaSettings_SharingTab_SelectFromSearchField;
    private By popup_SchemaSettings_SharingTab_SaveButton = By.xpath("//button[contains(string(), 'Save')]");
    private By popup_SchemaSettings_SharingTab_UsersSharedWith;
    private By popup_SchemaSettings_SharingTab_UsersPermission;

    //// Functions
    public SchemaList_SchemaView(WebDriver driver) {
	this.driver = driver;
    }

    // Assert_AllPageElementsExist
    public void Assert_schemaNameIsDisplayed(String schemaName) {
	ElementActions.switchToIframe(driver, body_iframe);
	Assertions.assertElementAttribute(driver, header_schemaName_label, "text", schemaName, true);
	ElementActions.switchToDefaultContent(driver);
    }

    public String GetNewestTableName() {
	ElementActions.switchToIframe(driver, body_iframe);
	String newestTableName = ElementActions.getText(driver, body_lastTableName_label);
	ElementActions.switchToDefaultContent(driver);
	return newestTableName;
    }

    public void switchToDefaultContent() {
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assert_tableNameIsDisplayed(String tableName) {
	ElementActions.switchToIframe(driver, body_iframe);
	body_tableName_label = By
		.xpath("//div[@id='" + tableName + "']//div[contains(@class,'SchemaTable')]//p[@class='ng-binding']");
	Assertions.assertElementExists(driver, body_tableName_label, true);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assert_tableAttributeIsCorrect(String tableName, String tableAttribute, String expectedValue) {
	ElementActions.switchToIframe(driver, body_iframe);
	body_tableAttribute_label = By.xpath("//div[@id='" + tableName + "']//h5[normalize-space(.)='" + tableAttribute
		+ "']//following-sibling::p[@class='ng-binding']");
	Assertions.assertElementAttribute(driver, body_tableAttribute_label, "Text", expectedValue, true);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Wizard_AddDataSourceTable(String dataSourceName, boolean createImplicitJoins, String dataSourceType,
	    String tableName) {
	ElementActions.switchToIframe(driver, body_iframe);

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
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assert_wizardWelcomeTextIsDisplayed() {
	Assertions.assertElementExists(driver, popup_schemaWizard_welcomeText_label, true);
    }

    // public void Assert_WizardWelcomeTextIsCorrect() {}

    public String GetLastLoadStatus() {
	ElementActions.switchToIframe(driver, body_iframe);
	String status = ElementActions.getText(driver, header_lastLoadStatus_link);
	ElementActions.switchToDefaultContent(driver);
	return status;
    }

    public void confirmLoadingData() {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.click(driver, popup_dataLoading_load_button);
	ElementActions.switchToDefaultContent(driver);
    }

    public void waitForDataToBeLoaded(String initialLoadStatus) {
	ElementActions.switchToIframe(driver, body_iframe);
	String currentLoadStatus = initialLoadStatus;
	do {
	    ElementActions.waitForTextToChange(driver, header_lastLoadStatus_link, currentLoadStatus, 10);
	    currentLoadStatus = ElementActions.getText(driver, header_lastLoadStatus_link);
	} while (currentLoadStatus.equals(initialLoadStatus) || currentLoadStatus.matches("(.*Loading Data.*)")
		|| currentLoadStatus.matches("(.*Please load data.*)"));
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assert_lastLoadStatusIsUpdated(String initialLoadStatus) {
	ElementActions.switchToIframe(driver, body_iframe);
	Assertions.assertElementAttribute(driver, header_lastLoadStatus_link, "Text", initialLoadStatus, false);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Click_Sharing_Tab() {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.click(driver, popup_SchemaSettings_SharingTab);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Click_AddButton_SharingTab() {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.click(driver, popup_SchemaSettings_SharingTab_Add_Button);
	ElementActions.switchToDefaultContent(driver);
    }

    /**
     * 
     * @param UserPermission
     *            Can Edit Can View Can Share
     * 
     */
    public void Schema_Sharing_ClickOnUserPermission(String UserPermission) {
	ElementActions.switchToIframe(driver, body_iframe);
	popup_SchemaSettings_SharingTab_Permission = By.xpath("//h5[contains(string(), '" + UserPermission + "')]");
	ElementActions.click(driver, popup_SchemaSettings_SharingTab_Permission);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Schema_Sharing_SearchAndSelectUsers(String SearchText) {
	ElementActions.switchToIframe(driver, body_iframe);
	popup_SchemaSettings_SharingTab_SelectFromSearchField = By
		.xpath("//h5[@class='UserData left text-left ng-binding'][contains(text(),'" + SearchText + "')]");
	ElementActions.type(driver, popup_SchemaSettings_SharingTab_ClickSearchField, SearchText);
	ElementActions.click(driver, popup_SchemaSettings_SharingTab_SelectFromSearchField);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Click_Save_Button() {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.click(driver, popup_SchemaSettings_SharingTab_SaveButton);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assertion_UserSharedWith(String SearchText) {
	ElementActions.switchToIframe(driver, body_iframe);
	popup_SchemaSettings_SharingTab_UsersSharedWith = By
		.xpath("//div[@class='folderUserRow ng-scope']//h5[contains(string(), '" + SearchText + "')]");
	Assertions.assertElementExists(driver, popup_SchemaSettings_SharingTab_UsersSharedWith, true);
	ElementActions.switchToDefaultContent(driver);
    }

    /**
     * 
     * @param SharedWithUser
     * @param Permission
     *            Can Edit Can View Can Share
     */
    public void Assertion_UserPermission(String SharedWithUser, String Permission) {
	ElementActions.switchToIframe(driver, body_iframe);
	popup_SchemaSettings_SharingTab_UsersPermission = By.xpath("//h5[@class='left ng-binding'][contains(string(),'"
		+ SharedWithUser + "')]//parent::div//following-sibling::div/a[contains(string(),'" + Permission
		+ "')]/parent::div");
	Assertions.assertElementExists(driver, popup_SchemaSettings_SharingTab_UsersPermission, true);
	ElementActions.switchToDefaultContent(driver);
    }
}
