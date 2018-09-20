package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

import io.restassured.internal.assertion.Assertion;

public class NewUI_Content {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content");
	int customElementIdentificationTimeout = 4;

	//// Elements
	// first nested header
	By body_iframe = By.xpath("//iframe[@title='Legacy Web']");
	By body_dashboardName_folderName;
	By body_folderName_dashboardName_insideFolder;
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
	By tableView_folderDashboardProperties_Button;
	By tableView_dashboardProperties_listOption;

	By tableView_contentTableGenericFolder_link = By
			.xpath("//div[@class='inc-folder-table']//tbody[@class='ant-table-tbody']//a");
	By tableView_contentTableGenericDashboard_link = By
			.xpath("//div[@class='inc-db-table']//tbody[@class='ant-table-tbody']//a");
	
	By popup_renameFolder;
	By popup_Rename_RenameButton = By.xpath("//button//span[contains(text(),'Rename')]");
	By popup_manageDashboard_copy_folderToCopyTo;
	By popup_manageDashboard_move_moveButton = By.xpath("//button//span[contains(text(),'Move')]");

	// popup menu
	By popup_newDashboard_dashboardName_textBox = By.xpath("//label/span[text()='Name']//following-sibling::input");
	By popup_newDashboard_newFolder_add_buttons = By.xpath("//button[@type='button']//span[contains(text(),'Add')]");
	By popup_newFolder_folderName = By.xpath("//input[@id='name']");
	By popup_folderDashboardProperties_delete_confirmationButtons;
	
	By popup_share_searchField;
	By popup_share_searchField_selectUser;
	By popup_share_shareTypeOptionsMenu = By.xpath("//span[@class='share-search']//i[@class='anticon anticon-eye-o']");
	By popup_share_selectShareType;
	By popup_share_clickOnShareButton;
	By popup_shareAccessScreen_shareDropDownMenuForUser_button;
	By popup_shareAccess_shareDropDown_shareType;
	
	By popup_moveFolder_folderNameToMoveTo;
	By popup_moveFolder_select_folderNameToMoveTo;
	
	//Others
	By popup_dashboard_sentSuccessfully_message;
	By popup_dashboard_scheduledSuccessfully_message;
	
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
	
	public void Click_Folder_Dashboard_Properties(String FolderName)
	{
		tableView_folderDashboardProperties_Button = By.xpath("//div[@class='inc-card-title']//span[contains(text(), '"+FolderName+"')]//parent::div//following-sibling::div//button");
		ElementActions.click(driver, tableView_folderDashboardProperties_Button);
	}
	
	public void Click_DashboardProperties_ManageDashboardButtons(String Actions)
	{
		tableView_dashboardProperties_listOption = By.xpath("//div[contains(@class,'ant-dropdown ant-dropdown')]//li[@class='ant-dropdown-menu-item']//span[contains(text(),'"+Actions+"')]");
		ElementActions.click(driver, tableView_dashboardProperties_listOption);
	}
	
	public void assert_dashboardProperties_manageDashboardButtons_notExist(String Actions)
	{
		tableView_dashboardProperties_listOption = By.xpath("//div[contains(@class,'ant-dropdown ant-dropdown')]//li[@class='ant-dropdown-menu-item']//span[contains(text(),'"+Actions+"')]");
		Assertions.assertElementExists(driver, tableView_dashboardProperties_listOption, false);
	}
	
	public void dashboard_folder_properties_delete_confirmationButtons(String button)
	{
		popup_folderDashboardProperties_delete_confirmationButtons = By.xpath("//button[@type='button']//span[contains(text(),'"+button+"')]");
		ElementActions.click(driver, popup_folderDashboardProperties_delete_confirmationButtons);
	}
	
	public String FolderProperties_Rename() {
		String newFolderName = "Automation" + "_Dashboard_" + String.valueOf(System.currentTimeMillis());
		popup_renameFolder = By.id("inc-rename-catalog-item__control");
		ElementActions.type(driver, popup_renameFolder, newFolderName);
		return newFolderName;
	}
	
	public void Dashboard_Rename_ClickRenameButton()
	{
		ElementActions.click(driver, popup_Rename_RenameButton);
	}
	
	public void Assert_DashboardExist(String DashboardName)
	{
		By body_dashboardName = By.xpath("//div[@class='inc-card-title']//span[text()= '"+DashboardName+"']");
		Assertions.assertElementExists(driver, body_dashboardName, true);
	}
	
	public void dashboard_popup_clickOnFolder(String FolderName)
	{
		popup_manageDashboard_copy_folderToCopyTo = By.xpath("//span[@class='ant-tree-title']//span[text()='"+FolderName+"']");
		ElementActions.click(driver, popup_manageDashboard_copy_folderToCopyTo);	
	}

	public void dashboard_move_clickMoveButton()
		{
			ElementActions.click(driver, popup_manageDashboard_move_moveButton);
		}
	
	public void assert_dashboard_folder_notExist(String DashboardFolderName)
	{
		BrowserActions.refreshCurrentPage(driver);
		body_dashboardName_folderName = By.xpath("//span[text()='"+DashboardFolderName+"']");
		Assertions.assertElementExists(driver, body_dashboardName_folderName, false);
	}
	
	public void dashboardProperties_copyTo_copy_button()
	{
		By popup_dashboardProperties_copyTo_copy_button = By.xpath("//span[text()='Copy']");
		ElementActions.click(driver, popup_dashboardProperties_copyTo_copy_button);
	}
	
	public void click_on_folder_dashboard(String DashboardFolderName)
		{
			BrowserActions.refreshCurrentPage(driver);
			body_folderName_dashboardName_insideFolder = By.xpath("//span[text()='"+DashboardFolderName+"']");
			ElementActions.click(driver, body_folderName_dashboardName_insideFolder);
		}
	
	
	public void Assert_DashboardExist_Copied(String DashboardName)
	{
		DashboardName = DashboardName + " Copy";
		By body_folderName_dashboardName_insideFolder = By.xpath("//span[text()='"+DashboardName+"']");
		Assertions.assertElementExists(driver, body_folderName_dashboardName_insideFolder, true);
	}

	public String setNewDashboardName() {
		String newDashboardName = "Automation" + "_Dashboard_" + String.valueOf(System.currentTimeMillis());
		ElementActions.type(driver, popup_newDashboard_dashboardName_textBox, newDashboardName);
		ElementActions.click(driver, popup_newDashboard_newFolder_add_buttons);
		return newDashboardName;
	}

	public String SetNewFolderName() {
        String newFolderName = "Automation" + "_Folder_" + String.valueOf(System.currentTimeMillis());
        ElementActions.type(driver, popup_newFolder_folderName, newFolderName);
        ElementActions.click(driver, popup_newDashboard_newFolder_add_buttons);
        return newFolderName;
    }
	
	/**
	 * 
	 * @param Email
	 * Can Share
	 * Can Edit
	 * Can View
	 */
	public void folderProperties_shareAccess_typeAndSelectInSearchField(String Email, String shareType)
	{
		popup_share_searchField = By.xpath("//input[@placeholder='Search names, emails, and groups']");
		ElementActions.type(driver, popup_share_searchField, Email);
		popup_share_searchField_selectUser = By.xpath("//div[@class='inc-search-option']//span/mark[text()='"+Email+"']");
		ElementActions.click(driver, popup_share_searchField_selectUser);
		ElementActions.click(driver, popup_share_shareTypeOptionsMenu);
		popup_share_selectShareType = By.xpath("//button[@class='inc-clickable share-dropdown ant-dropdown-trigger']"
				+ "//following-sibling::div//li[@class='ant-dropdown-menu-item']//span[text()='"+shareType+"']");
		ElementActions.click(driver, popup_share_selectShareType);
		popup_share_clickOnShareButton = By.xpath("//a[@class='share-button']//span");
		ElementActions.click(driver, popup_share_clickOnShareButton);
	}
	
	/**
	 * 
	 * @param Email
	 * @param shareType
	 * Can Edit
	 * Can Share
	 * Can View
	 */
	public void assert_folder_dashboard_sharedSuccessfully(String Email, String shareType)
	{
		popup_shareAccessScreen_shareDropDownMenuForUser_button = By.xpath("//div[@class='inc-search-option__item'][contains(.,'"+Email+"')]//button[contains(@class,'share-dropdown')]");
		ElementActions.click(driver, popup_shareAccessScreen_shareDropDownMenuForUser_button);
		popup_shareAccess_shareDropDown_shareType = By.xpath("//div[@class='inc-search-option__item'][contains(.,'"+Email+"')]//li[.='"+shareType+"']");
		Assertions.assertElementAttribute(driver, popup_shareAccess_shareDropDown_shareType, "aria-disabled", "true", true);
	}
	
	public void Click_FolderProperties_MoveFolder_FolderNameToMoveTo(String FolderName)
	{
		popup_moveFolder_folderNameToMoveTo = By.xpath("//input[@placeholder='Search Folders']");
		ElementActions.type(driver, popup_moveFolder_folderNameToMoveTo, FolderName);
		
		popup_moveFolder_select_folderNameToMoveTo = By.xpath("//li[@role='treeitem']//span[contains(text(),'"+FolderName+"')]");
		ElementActions.click(driver, popup_moveFolder_select_folderNameToMoveTo);
	}
	
	public void assert_dashboardSentSuccessfullyMessage(String DashboardName)
	{
		popup_dashboard_sentSuccessfully_message = By.xpath("//div[contains(text(),'Send')]//following-sibling::div[contains(text(),'Successfully sent "+DashboardName+".')]");
		Assertions.assertElementExists(driver, popup_dashboard_sentSuccessfully_message, true);
	}
	
	public void assert_dashboardScheduledSuccessfullyMessage(String DashboardName)
	{
		popup_dashboard_scheduledSuccessfully_message = By.xpath("//div[@class='ant-notification-notice-description'][contains(text(),'Successfully created schedule for "+DashboardName+".')]");
		Assertions.assertElementExists(driver, popup_dashboard_scheduledSuccessfully_message, true);
	}
	
}
