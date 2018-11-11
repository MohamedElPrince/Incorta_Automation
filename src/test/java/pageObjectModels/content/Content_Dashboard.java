package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

public class Content_Dashboard {
    //// Variables
    WebDriver driver;
    ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
    int customElementIdentificationTimeout = 1;
    int customNumberOfRetries = 1;

    //// Elements
    // first nested header
    By pageDetails_dashboardName_label = By.xpath("//div[@class='page-details--headerwrap']//h1");
    By pageDetails_back_button = By.xpath("//a[contains(@class,'page-details-back-button')]");
    By pageDetails_details_link = By.xpath("//button[contains(@class,'page-details--link')]");
    By pageDetails_setAsDefaultDashboard_button = By
	    .xpath("//i[contains(@class,'anticon-pushpin-o')]/ancestor::button");
    By pageDetails_dashboardActionMenu_button = By
	    .xpath("//div[@class='inc-dashboard__actions']//button[contains(@class,'dashboard-action-menu')]");
    By pageDetails_add_button = By
	    .xpath("//span[@class='inc-toolbar-button']//*[@data-icon='plus']/ancestor::a[contains(@href,'analyze')]");

    // second nested header
    By filterBar_bookmark_button = By.xpath("//div[@class='inc-filter-bar']//button[contains(@class,'bm-icon')]");
    By filterBar_filter_button = By.xpath("//div[@class='inc-filter-bar']//button[contains(@class,'filter-icon')]");
    By filterBar_search_textBox = By.xpath("//div[@class='inc-filter-bar']//input");
    By body_detailsButton = By.xpath("//button[contains(.,'Details')]");
    By body_detailsSection_description = By.xpath("//div[@class='page-details--description']");
    By body_detailsSection_ownedBy = By.xpath("//span[@class='detail-key'][contains(.,('Owned By'))]");
    By body_detailsSection_lastModified = By.xpath("//span[@class='detail-key'][contains(.,('Last Modified'))]");
    By body_detailsSection_modifiedBy = By.xpath("//span[@class='detail-key'][contains(.,('Modified by'))]");

    // spinner
    By loadingSpinner = By.xpath("//span[contains(@class,'ant-spin-dot')]");
    By genericInsight_div = By.xpath(
	    "//div[@class='insight__body']/ancestor::div[@class='draggable-insight-wrapper']|//div[@class='error-loading-wrapper__error']/ancestor::div[@class='draggable-insight-wrapper']");
    By insight_div;

    // body-insightHeader
    By insightHeader_insightName_label = By.className("insight__header-title");
    By insightHeader_insightActions_button = By.xpath("//span[@class='insight__header-actions']/button");

    // body-insightBody-aggregatedTable
    By aggregatedTable_columnHeader_link;
    By aggregatedTable_cellValue_link;

    // popup settings dashboard menu
    By popup_dashboard_folder_shareAccess_button = By.xpath("//a/span[contains(text(),'Share Access')]");
    By popup_dashboard_folder_sendNow_button = By.xpath("//a/span[contains(text(),'Send Now')]");
    By popup_dashboard_folder_scheduleDelivery_button = By.xpath("//a/span[contains(text(),'Schedule Delivery')]");

    // body Dashboard
    By body_shareButton = By.xpath("//i[@class='anticon anticon-share-alt']");
    // set_default Dashboard pin
    By body_unpushed_pin = By.xpath(
	    "//button[contains(@class, 'inc-clickable') and contains(@class ,'page-details-default')] | //svg[@data-icon='pushpin']");
    By body_pushed_pin = By.xpath(
	    "//button[contains(@class, 'inc-clickable') and contains(@class ,'isDefault')] | //svg[@data-icon='pushpin']");
    // set_default Dashboard pin
    By body_SetAsDefaultDashboardPin_Tooltip = By.xpath("//div[@class='ant-tooltip-inner']");
    By body_SetAsDefaultDashboard_Pin = By.xpath(
	    "//button[contains(@class, 'inc-clickable') and contains(@class ,'page-details-default')] | //svg[@data-icon='pushpin']");

    By body_bookmark = By.xpath("//i[@class='anticon anticon-book']");
    By popup_bookmarksName;

    By body_filter = By.xpath("//i[@class='anticon anticon-filter']");
    By popup_filtersName;
    By body_insightSettigns = By.xpath("//Button[@class='insight__header-menu ant-dropdown-trigger']");
    By body_insightSettigns_menueItemButtons;

    //// Functions
    public Content_Dashboard(WebDriver driver) {
	this.driver = driver;
	waitForDashboardToFullyLoad();
    }

    /**
     * Verifies that the displayed dashboardName matches the desired one
     * 
     * @param dashboardName
     */
    public void verify_dashboardName_matches(String dashboardName) {
	Verifications.verifyElementAttribute(driver, pageDetails_dashboardName_label, "text", dashboardName, true);
    }

    /**
     * Asserts that the target insightName is correctly displayed in the
     * insightName_label
     * 
     * @param insightName
     */

    public void reportcurrentDashboardURL() {
	ReportManager.log("Dashboard URL: [" + BrowserActions.getCurrentURL(driver) + "].");
    }

    public void waitForDashboardToFullyLoad() {
	int loadingSpinners = ElementActions.getElementsCount(driver, loadingSpinner,
		customElementIdentificationTimeout);
	while (loadingSpinners > 0) {
	    loadingSpinners = ElementActions.getElementsCount(driver, loadingSpinner,
		    customElementIdentificationTimeout);
	}
    }

    public int countInsights() {
	return ElementActions.getElementsCount(driver, genericInsight_div, customElementIdentificationTimeout);
    }

    public void assert_insightContent_isDisplayed(int insightIndex) {
	insight_div = By.xpath(
		"(//div[@class='insight__body']/ancestor::div[@class='draggable-insight-wrapper']|//div[@class='error-loading-wrapper__error']/ancestor::div[@class='draggable-insight-wrapper'])["
			+ insightIndex + "]");
	Assertions.assertElementExists(driver, insight_div, true);
    }

    /**
     * Asserts that the target dashboardName is correctly displayed in the
     * pageDetails_dashboardName_label
     * 
     * @param dashboardName
     */
    public void assert_dashboardName_isCorrect(String dashboardName) {
	// replaceRegex
	Assertions.assertElementAttribute(driver, pageDetails_dashboardName_label, "text", dashboardName, 1, true);
	// Assertions.assertElementAttribute(driver, pageDetails_dashboardName_label,
	// "text", dashboardName, true);
    }

    /**
     * Asserts that the displayed dashboardName matches the desired one
     * 
     * @param dashboardName
     */
    public void assert_dashboardName_matches(String dashboardName) {
	Assertions.assertElementAttribute(driver, pageDetails_dashboardName_label, "text", dashboardName, true);
    }

    /**
     * Asserts that the target insightName is correctly displayed in the
     * insightName_label
     * 
     * @param insightName
     */
    public void assert_insightName_isCorrect(String insightName) {
	Assertions.assertElementAttribute(driver, insightHeader_insightName_label, "text", insightName, true);
    }

    /**
     * Given that the insightType is aggregatedTable, Asserts that the actual
     * columnHeaderValue matches with the expected one
     * 
     * @param columnNumber
     *            the desired column number; 1 is the first column table header
     * @param columnHeaderValue
     *            the expected value of the target cell
     */
    public void aggregatedTable_assert_columnHeader_isCorrect(int columnNumber, String columnHeaderValue) {
	aggregatedTable_columnHeader_link = By.xpath("(//thead//span[@class='colHeader']//a)[" + columnNumber + "]");
	Assertions.assertElementAttribute(driver, aggregatedTable_columnHeader_link, "text", columnHeaderValue, true);
    }

    /**
     * Given that the insightType is aggregatedTable, Asserts that the actual
     * cellValue matches with the expected one
     * 
     * @param typeOrValue
     *            "type" refers to the first column, "value" refers to the second
     *            column
     * @param rowNumber
     *            the desired row number; 1 is the first row after the table header
     * @param cellValue
     *            the expected value of the target cell
     */
    public void aggregatedTable_assert_cellValue_isCorrect(String typeOrValue, int rowNumber, String cellValue) {
	switch (typeOrValue.trim().toLowerCase()) {
	case "type":
	    aggregatedTable_cellValue_link = By
		    .xpath("//tbody/tr[" + rowNumber + "]//a[contains(@class,'table__body-cell--aggregated')]");
	    break;
	case "value":
	    aggregatedTable_cellValue_link = By
		    .xpath("//tbody/tr[" + rowNumber + "]//a[contains(@class,'table__body-cell--basic')]");
	    break;
	default:
	    break;
	}

	Assertions.assertElementAttribute(driver, aggregatedTable_cellValue_link, "text", cellValue, true);
    }

    public void assert_shared_button_active() {
	Assertions.assertElementExists(driver, popup_dashboard_folder_shareAccess_button, true);
    }

    public void assert_shared_button_disabled() {
	Assertions.assertElementExists(driver, popup_dashboard_folder_shareAccess_button, false);
    }

    public void click_shareOptions_sendNow_button() {
	ElementActions.click(driver, body_shareButton);
	ElementActions.click(driver, popup_dashboard_folder_sendNow_button);
    }

    public void click_shareOptions_scheduleDelivery_button() {
	ElementActions.click(driver, body_shareButton);
	ElementActions.click(driver, popup_dashboard_folder_scheduleDelivery_button);
    }

    // Set default dashboard pin

    public void assert_SetDefault_DashboardPin_Displayed() {

	Assertions.assertElementExists(driver, body_unpushed_pin, true);

    }

    public void click_SetDefault_Dashboardpin() {

	ElementActions.click(driver, body_unpushed_pin);
	Assertions.assertElementExists(driver, body_pushed_pin, true);

    }

    public void click_bookmarkButton() {
	ElementActions.click(driver, body_bookmark);
    }

    public void assert_bookmarksExist(String BookmarkName) {
	popup_bookmarksName = By.xpath("//div[@class='inc-bookmarks-list-item'][contains(.,'" + BookmarkName + "')]");
	Assertions.assertElementExists(driver, popup_bookmarksName, true);
    }

    public void click_filterButton() {
	ElementActions.click(driver, body_filter);
    }

    public void assert_filterApplied(String FilterName) {
	popup_filtersName = By
		.xpath("//span[@class='inc-filter-master__menu-item'][contains(.,'" + FilterName + "')]/span");
	Assertions.assertElementAttribute(driver, popup_filtersName, "class", "([\\s\\S]*)applied", true);
    }

    public void click_detailsButton() {
	ElementActions.click(driver, body_detailsButton);
    }

    public void assert_detailsSectionContentDisplayed(String Description) {
	Assertions.assertElementAttribute(driver, body_detailsSection_description, "text", Description, true);
	Assertions.assertElementExists(driver, body_detailsSection_ownedBy, true);
	Assertions.assertElementExists(driver, body_detailsSection_lastModified, true);
	Assertions.assertElementExists(driver, body_detailsSection_modifiedBy, true);
    }

    public void assert_detailsSection_ownedByDisplayed(String DashboardCreator) {
	Assertions.assertElementAttribute(driver, body_detailsSection_ownedBy, "text", "Owned By " + DashboardCreator,
		true);
    }

    public void assert_detailsSection_modifiedByDisplayed(String DashboardUpdater) {
	Assertions.assertElementAttribute(driver, body_detailsSection_modifiedBy, "text",
		"Modified by " + DashboardUpdater, true);
    }

    public void assert_detailsSection_lastModifiedDisplayed() {
	Assertions.assertElementExists(driver, body_detailsSection_lastModified, true);
    }

    // "([\\s\\S]*" + Empty + ".*[\\s\\S]*)"
    public void addNewInsight() {
	ElementActions.click(driver, pageDetails_add_button);
    }

    public void insightSettings_clickOnMenuItem(String MenuItem) {
	ElementActions.click(driver, body_insightSettigns);
	body_insightSettigns_menueItemButtons = By.xpath("//li[@role='menuitem'][contains(.,'" + MenuItem + "')]");
	ElementActions.click(driver, body_insightSettigns_menueItemButtons);
    }

    public void assert_SetAsDefault_DashboardPin_Displayed() {
	Assertions.assertElementExists(driver, body_SetAsDefaultDashboard_Pin, true);
    }

    public void assert_SetAsDefaultDashboardPin_tooltip_message(Boolean isSelected) {
	ElementActions.hover(driver, body_SetAsDefaultDashboard_Pin);

	if (isSelected) {
	    Assertions.assertElementAttribute(driver, body_SetAsDefaultDashboardPin_Tooltip, "text", "Remove", 3, true);
	} else {
	    Assertions.assertElementAttribute(driver, body_SetAsDefaultDashboardPin_Tooltip, "text", "Set", 3, true);
	}
    }

    /**
     * Take action click on set as default dasboard pin button
     * 
     * 
     * @param actionType
     * 
     *            True: set default dashboard False: unset default dashboard
     */

    public void click_SetAsDefaultPinIcon(Boolean actionType) {

	if ((actionType != ElementActions.getAttribute(driver, body_SetAsDefaultDashboard_Pin, "class")
		.contains("isDefault"))) {
	    ElementActions.click(driver, body_SetAsDefaultDashboard_Pin);
	}
    }

    /**
     * Asserts that the target Set As Default dashboard pin button is set or not
     * 
     * 
     * @param stateType
     * 
     *            True: check that the pin button is selected False: check that the
     *            pin button
     */
    public void assert_setAsDefaultPinIcon_state(Boolean stateType) {
	Assertions.assertElementAttribute(driver, body_SetAsDefaultDashboard_Pin, "class", "isDefault", 3, stateType);
    }
}