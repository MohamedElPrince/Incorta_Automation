package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class NewUI_Content {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content");
	int customElementIdentificationTimeout = 2;

	//// Elements
	// first nested header
	By pageDetails_title_label = By.xpath("//span[@class='page-details-title']/h1");

	// second_nested_header
	By searchWrapper_search_textBox = By.xpath("//div[@class='ant-select-search__field__wrap']//input");
	By searchWrapper_searchResult_label = By.className("inc-search-option__item");
	By searchWrapper_searchCount_label = By.className("inc-search-count");
	By searchWrapper_searchDropDown_button = By.xpath("//span[@class='inc-search-dropdown']//button");
	By searchWrapper_searchDropDownOption_label; // span[@class='inc-search-dropdown']//li[@class='ant-dropdown-menu-item']/a[text()='']
	By searchWrapper_catalogViewSwitchCard_button = By.xpath("//button[contains(@class,'inc-show-card')]");
	By searchWrapper_catalogViewSwitchTable_button = By.xpath("//button[contains(@class,'inc-show-table')]");

	// body-cardView
	By cardView_contentCard_label; // div[@class='inc-card-title'][text()='']
	By cardView_contentCardFolder_link;
	By cardView_contentCardDashboard_link;

	By cardView_contentCardGenericFolder_link = By
			.xpath("//div[contains(@class,'inc-folder-view--cards')]//span[@class='inc-card-title-text']");
	By cardView_contentCardGenericDashboard_link = By
			.xpath("//div[contains(@class,'inc-dashboard-view--cards')]//span[@class='inc-card-title-text']");

	// body-tableView
	By tableView_contentTableEntry_link; // tbody[@class='ant-table-tbody']//a
	By tableView_contentTableFolder_link;
	By tableView_contentTableDashboard_link;

	By tableView_contentTableGenericFolder_link = By
			.xpath("//div[@class='inc-folder-table']//tbody[@class='ant-table-tbody']//a");
	By tableView_contentTableGenericDashboard_link = By
			.xpath("//div[@class='inc-db-table']//tbody[@class='ant-table-tbody']//a");

	//// Functions
	public NewUI_Content(WebDriver driver) {
		this.driver = driver;
	}

	public void navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	/**
	 * Asserts that the mentioned pageTitle matches with the displayed pageTitle
	 * 
	 * @param pageTitle
	 */
	public void assert_pageTitle_isCorrect(String pageTitle) {
		Assertions.assertElementAttribute(driver, pageDetails_title_label, "text", pageTitle, true);
	}

	/**
	 * Changes the current catalogView to the desired one
	 * 
	 * @param catalogView
	 *            Card | Table
	 */
	public void changeCatalogView(String catalogView) {
		switch (catalogView.toLowerCase().trim()) {
		case "card":
			if (!ElementActions.getAttribute(driver, searchWrapper_catalogViewSwitchCard_button, "class")
					.contains("selected")) {
				ElementActions.click(driver, searchWrapper_catalogViewSwitchCard_button);
			}
			break;
		case "table":
			if (!ElementActions.getAttribute(driver, searchWrapper_catalogViewSwitchTable_button, "class")
					.contains("selected")) {
				ElementActions.click(driver, searchWrapper_catalogViewSwitchTable_button);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Asserts that the correct catalogView is selected
	 * 
	 * @param catalogView
	 *            Card | Table
	 */
	public void assert_correctCatalogView_isSelected(String catalogView) {

		switch (catalogView.toLowerCase().trim()) {
		case "card":
			Assertions.assertElementAttribute(driver, searchWrapper_catalogViewSwitchCard_button, "class", "*selected*",
					true);
			break;
		case "table":
			Assertions.assertElementAttribute(driver, searchWrapper_catalogViewSwitchTable_button, "class",
					"*selected*", true);
			break;
		default:
			break;
		}

	}

	/**
	 * Asserts that the correct searchCount is displayed
	 * 
	 * @param searchCount
	 *            sample text is [You're looking at 4 Folders and 8 Dashboards] but
	 *            you can also use regular expressions such as [.*4 Folders and 8
	 *            Dashboards]
	 */
	public void assert_correctSearchCount_isDisplayed(String searchCount) {
		Assertions.assertElementAttribute(driver, searchWrapper_searchCount_label, "text", searchCount, true);
	}

	/**
	 * Searches for the desired contentName in the search text box
	 * 
	 * @param contentName
	 */
	public void searchForContentUsingSearchBox(String contentName) {
		ElementActions.type(driver, searchWrapper_search_textBox, contentName);
	}

	/**
	 * Asserts that the desired contentName is the only search result displayed
	 * 
	 * @param contentName
	 */
	public void assert_contentSearchResult_isDisplayed(String contentName) {
		Assertions.assertElementAttribute(driver, searchWrapper_searchResult_label, "text", contentName, true);
	}

	/**
	 * Given that the desired contentName is the only search result displayed,
	 * navigates to open it
	 * 
	 * @param contentName
	 */
	public void navigate_toContentSearchResult(String contentName) {
		ElementActions.click(driver, searchWrapper_searchResult_label);
	}

	/**
	 * Navigates to a sub page of the "Content" section
	 * 
	 * @param subPageName
	 *            All Content | Shared | Owned
	 */
	public void navigate_toSubPage(String subPageName) {
		ElementActions.click(driver, searchWrapper_searchDropDown_button);
		searchWrapper_searchDropDownOption_label = By
				.xpath("//span[@class='inc-search-dropdown']//li[@class='ant-dropdown-menu-item']/a[text()='"
						+ subPageName + "']");
		ElementActions.click(driver, searchWrapper_searchDropDownOption_label);
	}

	/**
	 *********************************************************************
	 * The following is the cardView section
	 *********************************************************************
	 * 
	 */

	/**
	 * Given that cardView is selected, assert that the contentCard that contains
	 * the target contentName exists on the current page
	 * 
	 * @param contentName
	 */
	public void cardView_assert_contentCard_exists(String contentName) {
		cardView_contentCard_label = By.xpath("//div[@class='inc-card-title'][text()='" + contentName + "']");
		Assertions.assertElementExists(driver, cardView_contentCard_label, true);
	}

	/**
	 * Given that cardView is selected, navigates to the contentCard that contains
	 * the target contentName
	 * 
	 * @param contentName
	 */
	public void cardView_navigate_toContentCard(String contentName) {
		cardView_contentCard_label = By.xpath("//div[@class='inc-card-title'][text()='" + contentName + "']");
		ElementActions.click(driver, cardView_contentCard_label);
	}

	/**
	 * Given that cardView is selected, navigates to the cardDashboard that matches
	 * the target dashboardIndex
	 * 
	 * @param dashboardIndex
	 */
	public void cardView_navigate_toContentTableDashboard(int dashboardIndex) {
		cardView_contentCardDashboard_link = By
				.xpath("(//div[contains(@class,'inc-dashboard-view--cards')]//span[@class='inc-card-title-text'])["
						+ dashboardIndex + "]");
		ElementActions.click(driver, cardView_contentCardDashboard_link);

	}

	/**
	 * Given that cardView is selected, navigates to the cardFolder that matches the
	 * target folderIndex
	 * 
	 * @param folderIndex
	 */
	public void cardView_navigate_toContentTableFolder(int folderIndex) {
		cardView_contentCardFolder_link = By
				.xpath("(//div[contains(@class,'inc-folder-view--cards')]//span[@class='inc-card-title-text'])["
						+ folderIndex + "]");
		ElementActions.click(driver, cardView_contentCardFolder_link);

	}

	/**
	 * Given that cardView is selected, counts all cardDashboards
	 * 
	 * @return tableDashboards count
	 */
	public int cardView_countDashboards() {
		return ElementActions.getElementsCount(driver, cardView_contentCardGenericDashboard_link,
				customElementIdentificationTimeout);
	}

	/**
	 * Given that cardView is selected, counts all cardFolders
	 * 
	 * @return tableFolders count
	 */
	public int cardView_countFolders() {
		return ElementActions.getElementsCount(driver, cardView_contentCardGenericFolder_link,
				customElementIdentificationTimeout);
	}

	/**
	 *********************************************************************
	 * The following is the tableView section
	 *********************************************************************
	 */

	/**
	 * Given that tableView is selected, assert that the tableEntry that contains
	 * the target contentName exists on the current page
	 * 
	 * @param contentName
	 */
	public void tableView_assert_contentTableEntry_exists(String contentName) {
		tableView_contentTableEntry_link = By
				.xpath("//tbody[@class='ant-table-tbody']//a[text()='" + contentName + "']");
		Assertions.assertElementExists(driver, tableView_contentTableEntry_link, true);
	}

	/**
	 * Given that tableView is selected, navigates to the tableEntry that contains
	 * the target contentName
	 * 
	 * @param contentName
	 */
	public void tableView_navigate_toContentTableEntry(String contentName) {
		tableView_contentTableEntry_link = By
				.xpath("//tbody[@class='ant-table-tbody']//a[text()='" + contentName + "']");
		ElementActions.click(driver, tableView_contentTableEntry_link);
	}

	/**
	 * Given that tableView is selected, navigates to the tableDashboard that
	 * matches the target dashboardIndex
	 * 
	 * @param dashboardIndex
	 */
	public void tableView_navigate_toContentTableDashboard(int dashboardIndex) {
		tableView_contentTableDashboard_link = By
				.xpath("(//div[@class='inc-db-table']//tbody[@class='ant-table-tbody']//a)[" + dashboardIndex + "]");
		ElementActions.click(driver, tableView_contentTableDashboard_link);

	}

	/**
	 * Given that tableView is selected, navigates to the tableFolder that matches
	 * the target folderIndex
	 * 
	 * @param folderIndex
	 */
	public void tableView_navigate_toContentTableFolder(int folderIndex) {
		tableView_contentTableFolder_link = By
				.xpath("(//div[@class='inc-folder-table']//tbody[@class='ant-table-tbody']//a)[" + folderIndex + "]");
		ElementActions.click(driver, tableView_contentTableFolder_link);

	}

	/**
	 * Given that tableView is selected, counts all tableDashboards
	 * 
	 * @return tableDashboards count
	 */
	public int tableView_countDashboards() {
		return ElementActions.getElementsCount(driver, tableView_contentTableGenericDashboard_link,
				customElementIdentificationTimeout);
	}

	/**
	 * Given that tableView is selected, counts all tableFolders
	 * 
	 * @return tableFolders count
	 */
	public int tableView_countFolders() {
		return ElementActions.getElementsCount(driver, tableView_contentTableGenericFolder_link,
				customElementIdentificationTimeout);
	}

}
