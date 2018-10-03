package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;
import com.shaft.validation.Assertions;

public class NewUI_Content_Dashboard {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	int customElementIdentificationTimeout = 1;

	//// Elements
	// first nested header
	By pageDetails_dashboardName_label = By.xpath("//div[@class='page-details--headerwrap']//h1");
	By pageDetails_back_button = By.xpath("//a[contains(@class,'page-details-back-button')]");
	By pageDetails_details_link = By.xpath("//button[contains(@class,'page-details--link')]");
	By pageDetails_setAsDefaultDashboard_button = By
			.xpath("//i[contains(@class,'anticon-pushpin-o')]/ancestor::button");
	By pageDetails_dashboardActionMenu_button = By
			.xpath("//div[@class='inc-dashboard__actions']//button[contains(@class,'dashboard-action-menu')]");

	// second nested header
	By filterBar_bookmark_button = By.xpath("//div[@class='inc-filter-bar']//button[contains(@class,'bm-icon')]");
	By filterBar_filter_button = By.xpath("//div[@class='inc-filter-bar']//button[contains(@class,'filter-icon')]");
	By filterBar_search_textBox = By.xpath("//div[@class='inc-filter-bar']//input");

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
	
	By body_bookmark = By.xpath("//i[@class='anticon anticon-book']");
	By popup_bookmarksName;
	
	By body_filter = By.xpath("//i[@class='anticon anticon-filter']");
	By popup_filtersName;
	
	//// Functions
	public NewUI_Content_Dashboard(WebDriver driver) {
		this.driver = driver;
		waitForDashboardToFullyLoad();
	}

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
	 * @param name
	 */
	public void assert_dashboardName_isCorrect(String dashboardName) {
		// replaceRegex
		Assertions.assertElementAttribute(driver, pageDetails_dashboardName_label, "text", dashboardName, 1, true);
//		Assertions.assertElementAttribute(driver, pageDetails_dashboardName_label, "text", dashboardName, true);
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
	
	public void click_bookmarkButton()
	{
		ElementActions.click(driver, body_bookmark);
	}
	
	public void assert_bookmarksExist(String BookmarkName)
	{
		popup_bookmarksName = By.xpath("//div[@class='inc-bookmarks-list-item'][contains(.,'"+BookmarkName+"')]");
		Assertions.assertElementExists(driver, popup_bookmarksName, true);
	}
	
	public void click_filterButton()
	{
		ElementActions.click(driver, body_filter);
	}

	public void assert_filterApplied(String FilterName)
	{
		popup_filtersName = By.xpath("//span[@class='inc-filter-master__menu-item'][contains(.,'"+FilterName+"')]/span");
		Assertions.assertElementAttribute(driver, popup_filtersName, "class", "([\\s\\S]*)applied", true);
	}

}