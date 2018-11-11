package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;
import com.shaft.validation.Assertions;

public class Content {
    //// Variables
    private WebDriver driver;
    private ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
    private String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_content");
    private int customElementIdentificationTimeout = 4;
    private int customNumberOfRetries = 1;

    //// Elements
    // first nested header
    private By body_add_button = By.xpath("//span[@class='inc-toolbar-button']//*[@data-icon='plus']");
    private By body_add_menuItem; // li[@class='ant-dropdown-menu-item'][contains(.,'Add Folder')]
    private By body_dashboardName_folderName;
    private By body_folderName_dashboardName_insideFolder;
    private By pageDetails_title_label = By.xpath("//span[@class='page-details-title']/h1");

    // second_nested_header
    private By searchWrapper_search_textBox = By.xpath("//div[@class='ant-select-search__field__wrap']//input");
    private By searchWrapper_searchResult_label = By.className("inc-search-option__item--left__name--highLighted"); // inc-search-option__item
    private By searchWrapper_searchCount_label = By.className("inc-search-count");
    private By searchWrapper_searchDropDown_button = By.xpath("//span[@class='inc-search-dropdown']//button");
    private By searchWrapper_searchDropDownOption_label; // span[@class='inc-search-dropdown']//li[@class='ant-dropdown-menu-item']/a[text()='']
    private By searchWrapper_catalogViewSwitchCard_button = By.xpath("//button[contains(@class,'inc-show-card')]//i");
    private By searchWrapper_catalogViewSwitchTable_button = By.xpath("//button[contains(@class,'inc-show-table')]//i");
    private By searchResult_search;

    private By searchResult_profilePicture = By
	    .xpath("//div[@class='inc-search-option__item']//img[contains(@class,'inc-catalog-search-user-icon')]");

    // body-cardView
    private By cardView_contentCard_label; // div[@class='inc-card-title'][text()='']
    private By cardView_contentCardFolder_link;
    private By cardView_contentCardDashboard_link;

    private By cardView_contentCardGenericFolder_link = By
	    .xpath("//div[contains(@class,'inc-folder-view--cards')]//span[@class='inc-card-title-text']");
    private By cardView_contentCardGenericDashboard_link = By
	    .xpath("//div[contains(@class,'inc-dashboard-view--cards')]//span[@class='inc-card-title-text']");

    // body-tableView
    private By tableView_contentTableEntry_link; // tbody[@class='ant-table-tbody']//a
    private By tableView_contentTableFolder_link;
    private By tableView_contentTableDashboard_link;
    private By tableView_folderDashboardProperties_Button;
    private By tableView_dashboardProperties_listOption;

    private By tableView_contentTableGenericFolder_link = By
	    .xpath("//div[@class='inc-folder-table']//tbody[@class='ant-table-tbody']//a");
    private By tableView_contentTableGenericDashboard_link = By
	    .xpath("//div[@class='inc-db-table']//tbody[@class='ant-table-tbody']//a");

    // popup_addDashboardOrFolder
    private By popup_addDashboardOrFolder_name_textBox = By.id("name");

    private By popup_renameFolder;
    private By popup_Rename_RenameButton = By.xpath("//button[contains(.,'Rename')]");
    private By popup_Rename_CancelButton = By.xpath("//button[contains(.,'Cancel')]");

    private By popup_manageDashboard_copy_folderToCopyTo;
    private By popup_manageDashboard_move_moveButton = By.xpath("//button//span[contains(text(),'Move')]");

    // popup menu
    private By popup_newDashboard_dashboardName_textBox = By
	    .xpath("//label/span[text()='Name']//following-sibling::input");
    private By popup_newDashboard_newFolder_add_buttons = By
	    .xpath("//button[@type='button']//span[contains(text(),'Add')]");
    private By popup_newFolder_folderName = By.xpath("//input[@id='name']");
    private By popup_folderDashboardProperties_delete_confirmationButtons;

    private By popup_share_searchField;
    private By popup_share_searchField_selectUser;
    private By popup_share_shareTypeOptionsMenu = By
	    .xpath("//span[@class='share-search']//i[@class='anticon anticon-eye-o']");
    private By popup_share_selectShareType;
    private By popup_share_clickOnShareButton;
    private By popup_shareAccessScreen_shareDropDownMenuForUser_button;
    private By popup_shareAccess_shareDropDown_shareType;

    private By popup_moveFolder_folderNameToMoveTo;
    private By popup_moveFolder_select_folderNameToMoveTo;

    // Make a Copy
    private By popup_makeACopyScreen = By.xpath(
	    "//span[contains(.,'Make a Copy')]/parent::div[contains(@class,'title')]/parent::div[contains(@class,'header')]/parent::div[@class='ant-modal-content']");
    private By popup_makeACopyScreen_headerLabel = By
	    .xpath("//div[@class='ant-modal-header']//span[contains(.,'Make a Copy')]");
    private By popup_makeACopyScreen_newNameLabel = By
	    .xpath("//div[@class='ant-modal-body']//span[contains(.,'New Name')]");
    private By popup_makeACopyScreen_selectFolderLabel = By
	    .xpath("//div[@class='ant-modal-body']//span[contains(.,'Select Folder')]");
    private By popup_makeACopyScreen_foldersSection = By
	    .xpath("//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]");

    private By popup_makeACopyScreen_newFolderButton = By
	    .xpath("//div[@class='ant-modal-footer']//button[contains(.,'New Folder')]");
    private By popup_makeACopyScreen_cancelButton = By
	    .xpath("//div[@class='ant-modal-footer']//button[contains(.,'Cancel')]");
    private By popup_makeACopyScreen_copyAndOpenButton = By
	    .xpath("//div[@class='ant-modal-footer']//button[contains(.,'Copy  & Open')]");
    private By popup_makeACopyScreen_copyButton = By
	    .xpath("//div[@class='ant-modal-footer']//button[@class='ant-btn ant-btn-primary'][contains(.,'Copy')]");

    private By popup_makeACopyScreen_searchField = By.xpath("//input[@placeholder='Search Folders']");
    private By popup_makeACopyScreen_selectFolder;

    private By popup_makeACopyScreen_newNameField = By.xpath("//input[@placeholder='New Name']");

    private By popup_makeACopyScreen_foldersSection_folders;
    private By popup_makeACopy_foldersExapandIcon;
    private By body_searchContent;
    private By body_searchContent_resultFound;
    private By popup_makeACopy_errorDuplicateDashboardName;

    // Rename Folder
    private By popup_renameFolder_newNameField = By.xpath("//input[@placeholder='Name']");
    private By renameFolder_successfullyRenamedMessage;

    private By popup_renameFolder_headerLabel = By.xpath("//span[contains(.,'Rename Folder')]");
    private By popup_renameFolder_newNameLabel = By.xpath("//span[contains(.,'New Name')]");
    private By popup_renameFolder_screen = By.xpath("//div[@class='ant-modal']/div[@class='ant-modal-content']");
    private By popup_renameFolder_errorMessage_nameAlreadyExist = By
	    .xpath("//span[contains(@class,'error-message')][contains(text(),'Name already exists!')]");
    private By popup_renameFolder_closeIcon = By.xpath("//button[@class='ant-modal-close']");

    // Rename Dashboard
    private By popup_renameDashboard_field = By.id("inc-rename-catalog-item__control");
    private By popup_renameDashboard_renameCancel_buttons;
    private By popup_renameDashboard_confirmationMessage;
    private By popup_renameDashboard_rename_button;
    private By popup_renameDashboard_newNameField;
    private By popup_renameDashboard_newNameLabel;
    private By popup_renameDashboard_headerLabel;
    private By popup_renameDashboard_errorMessage = By
	    .xpath("//span[contains(@class,'error-message')][contains(.,'Name already exists!')]");
    private By popup_renameDashboard_closeIcon = By.xpath("//i[@class='anticon anticon-close ant-modal-close-icon']");
    private By popup_renameDashboard_screen = By.xpath("//div[@class='ant-modal']/div[@class='ant-modal-content']");

    // Delete Dashboard
    private By popup_deleteDashboard_questionCircle = By.xpath("//i[@class='anticon anticon-question-circle']");
    private By popup_deleteDashboard_confirmTitle = By
	    .xpath("//span[@class='ant-confirm-title'][contains(.,'Delete Dashboard?')]");
    private By popup_deleteDashboard_confirmContent = By.xpath("//div[@class='ant-confirm-content']/div");
    private By popup_deleteDashboard_neverMind_Button = By.xpath("//button[contains(.,'Never Mind')]");
    private By popup_deleteDashboard_delete_Button = By.xpath("//button[contains(.,'Delete')]");

    // Others
    private By body_folderCardDescription;

    // Catalog Of Content Sorting
    private By body_firstDashboard_inTheList = By
	    .xpath("//span[@class='inc-card inc-dashboard-card inline medium'][1]//span[@class='inc-card-title-text']");
    private By body_secondDashboard_inTheList = By
	    .xpath("//span[@class='inc-card inc-dashboard-card inline medium'][2]//span[@class='inc-card-title-text']");
    private By body_thirdDashboard_inTheList = By
	    .xpath("//span[@class='inc-card inc-dashboard-card inline medium'][3]//span[@class='inc-card-title-text']");

    // Folder section
    private By body_sortButton = By.xpath("//i[@class='anticon anticon-down']");

    //// Functions
    public Content(WebDriver driver) {
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
     * Clicks on the add button, and adds a new item type
     * 
     * @param itemType
     *            accepts "dashboard" or "folder"
     * @return string value representing the name of the newly created catalog item
     */
    public String addNewCatalogItem(String itemType) {
	String newItemName = "";
	ElementActions.click(driver, body_add_button);
	switch (itemType.toLowerCase().trim()) {
	case "folder":
	    body_add_menuItem = By.xpath("//li[@class='ant-dropdown-menu-item'][contains(.,'Add Folder')]");
	    newItemName = "Automation" + "_Folder_" + String.valueOf(System.currentTimeMillis());
	    break;
	case "dashboard":
	    body_add_menuItem = By.xpath("//li[@class='ant-dropdown-menu-item'][contains(.,'Add Dashboard')]");
	    newItemName = "Automation" + "_Dashboard_" + String.valueOf(System.currentTimeMillis());
	    break;
	default:
	    break;
	}
	ElementActions.click(driver, body_add_menuItem);
	ElementActions.type(driver, popup_addDashboardOrFolder_name_textBox, newItemName);
	ElementActions.keyPress(driver, popup_addDashboardOrFolder_name_textBox, "ENTER");
	return newItemName;
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
	case "list":
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
	ReportManager.log("Navigating to [" + ElementActions.getText(driver, cardView_contentCardDashboard_link)
		+ "] Dashboard.");
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
		customElementIdentificationTimeout, customNumberOfRetries);
    }

    /**
     * Given that cardView is selected, counts all cardFolders
     * 
     * @return tableFolders count
     */
    public int cardView_countFolders() {
	return ElementActions.getElementsCount(driver, cardView_contentCardGenericFolder_link,
		customElementIdentificationTimeout, customNumberOfRetries);
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
	ReportManager.log("Navigating to [" + ElementActions.getText(driver, cardView_contentCardDashboard_link)
		+ "] Dashboard.");
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
		customElementIdentificationTimeout, customNumberOfRetries);
    }

    /**
     * Given that tableView is selected, counts all tableFolders
     * 
     * @return tableFolders count
     */
    public int tableView_countFolders() {
	return ElementActions.getElementsCount(driver, tableView_contentTableGenericFolder_link,
		customElementIdentificationTimeout, customNumberOfRetries);
    }

    public void click_dashboardFolder_properties_fromCardView(String FolderName) {
	tableView_folderDashboardProperties_Button = By.xpath("//div[@class='inc-card-title']//span[text()= '"
		+ FolderName + "']//parent::div//following-sibling::div//button");
	ElementActions.click(driver, tableView_folderDashboardProperties_Button);
    }

    public void click_dashboardFolder_properties_fromListView(String FolderName) {
	tableView_folderDashboardProperties_Button = By.xpath("//tr[contains(.,'" + FolderName + "')]//button");
	ElementActions.click(driver, tableView_folderDashboardProperties_Button);
    }

    public void Click_DashboardProperties_ManageDashboardButtons(String Actions) {
	tableView_dashboardProperties_listOption = By.xpath(
		"//div[contains(@class,'ant-dropdown')][not(contains(@class,'ant-dropdown-hidden'))]//span[text()='"
			+ Actions + "']");
	ElementActions.click(driver, tableView_dashboardProperties_listOption);
    }

    public void assert_dashboardProperties_manageDashboardButtons_notExist(String Actions) {
	tableView_dashboardProperties_listOption = By.xpath(
		"//div[contains(@class,'ant-dropdown  ant-dropdown')]//span[text()='" + Actions + "']/parent::a");
	Assertions.assertElementExists(driver, tableView_dashboardProperties_listOption, false);
    }

    public void assert_dashboardProperties_manageDashboardButtons_displayed(String Actions) {
	tableView_dashboardProperties_listOption = By
		.xpath("//div[contains(@class,'ant-dropdown ant-dropdown')]//span[text()='" + Actions + "']/parent::a");
	Assertions.assertElementExists(driver, tableView_dashboardProperties_listOption, true);
    }

    public void dashboard_folder_properties_delete_confirmationButtons(String button) {
	popup_folderDashboardProperties_delete_confirmationButtons = By
		.xpath("//button[@type='button']//span[contains(text(),'" + button + "')]");
	ElementActions.click(driver, popup_folderDashboardProperties_delete_confirmationButtons);
    }

    public String FolderProperties_Rename() {
	String newFolderName = "Automation" + "_Dashboard_" + String.valueOf(System.currentTimeMillis());
	popup_renameFolder = By.id("inc-rename-catalog-item__control");
	ElementActions.type(driver, popup_renameFolder, newFolderName);
	return newFolderName;
    }

    public void Dashboard_Rename_ClickRenameButton() {
	ElementActions.click(driver, popup_Rename_RenameButton);
    }

    public void Assert_DashboardExist(String DashboardName) {
	By body_dashboardName = By.xpath("//div[@class='inc-card-title']//span[text()= '" + DashboardName + "']");
	Assertions.assertElementExists(driver, body_dashboardName, true);
    }

    public void dashboard_popup_clickOnFolder(String FolderName) {
	popup_manageDashboard_copy_folderToCopyTo = By
		.xpath("//span[@class='ant-tree-title']//span[text()='" + FolderName + "']");
	ElementActions.click(driver, popup_manageDashboard_copy_folderToCopyTo);
    }

    public void dashboard_move_clickMoveButton() {
	ElementActions.click(driver, popup_manageDashboard_move_moveButton);
    }

    public void assert_dashboard_folder_notExist(String DashboardFolderName) {
	BrowserActions.refreshCurrentPage(driver);
	body_dashboardName_folderName = By.xpath("//span[text()='" + DashboardFolderName + "']");
	Assertions.assertElementExists(driver, body_dashboardName_folderName, false);
    }

    public void dashboardProperties_copyTo_copy_button() {
	By popup_dashboardProperties_copyTo_copy_button = By.xpath("//span[text()='Copy']");
	ElementActions.click(driver, popup_dashboardProperties_copyTo_copy_button);
    }

    public void click_on_folder_dashboard(String DashboardFolderName) {
	// BrowserActions.refreshCurrentPage(driver);
	body_folderName_dashboardName_insideFolder = By.xpath("//span[text()='" + DashboardFolderName + "']");
	ElementActions.click(driver, body_folderName_dashboardName_insideFolder);
    }

    public void Assert_DashboardExist_Copied(String DashboardName) {
	DashboardName = DashboardName + " Copy";
	By body_folderName_dashboardName_insideFolder = By.xpath("//span[text()='" + DashboardName + "']");
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
     *            Can Share Can Edit Can View
     */
    public void folderProperties_shareAccess_typeAndSelectInSearchField(String Email, String shareType) {
	popup_share_searchField = By.xpath("//input[@placeholder='Search names, emails, and groups']");
	ElementActions.type(driver, popup_share_searchField, Email);
	popup_share_searchField_selectUser = By
		.xpath("//div[@class='inc-search-option']//span/mark[text()='" + Email + "']");
	ElementActions.click(driver, popup_share_searchField_selectUser);
	ElementActions.click(driver, popup_share_shareTypeOptionsMenu);
	popup_share_selectShareType = By.xpath("//button[@class='inc-clickable share-dropdown ant-dropdown-trigger']"
		+ "//following-sibling::div//li[@class='ant-dropdown-menu-item']//span[text()='" + shareType + "']");
	ElementActions.click(driver, popup_share_selectShareType);
	popup_share_clickOnShareButton = By.xpath("//a[@class='share-button']//span");
	ElementActions.click(driver, popup_share_clickOnShareButton);
    }

    /**
     * 
     * @param Email
     * @param shareType
     *            Can Edit Can Share Can View
     */
    public void assert_folder_dashboard_sharedSuccessfully(String Email, String shareType) {
	popup_shareAccessScreen_shareDropDownMenuForUser_button = By
		.xpath("//div[@class='inc-search-option__item'][contains(.,'" + Email
			+ "')]//button[contains(@class,'share-dropdown')]");
	ElementActions.click(driver, popup_shareAccessScreen_shareDropDownMenuForUser_button);
	popup_shareAccess_shareDropDown_shareType = By.xpath(
		"//div[@class='inc-search-option__item'][contains(.,'" + Email + "')]//li[.='" + shareType + "']");
	Assertions.assertElementAttribute(driver, popup_shareAccess_shareDropDown_shareType, "aria-disabled", "true",
		true);
    }

    public void Click_FolderProperties_MoveFolder_FolderNameToMoveTo(String FolderName) {
	popup_moveFolder_folderNameToMoveTo = By.xpath("//input[@placeholder='Search Folders']");
	ElementActions.type(driver, popup_moveFolder_folderNameToMoveTo, FolderName);

	popup_moveFolder_select_folderNameToMoveTo = By
		.xpath("//li[@role='treeitem']//span[contains(text(),'" + FolderName + "')]");
	ElementActions.click(driver, popup_moveFolder_select_folderNameToMoveTo);
    }

    // Make a Copy
    public void assert_makeACopy_popup_displayed() {
	Assertions.assertElementExists(driver, popup_makeACopyScreen, true);
    }

    public void assert_makeACopy_popup_notDisplayed() {
	Assertions.assertElementExists(driver, popup_makeACopyScreen, false);
    }

    public void assert_makeACopy_popupScreen_screenContentDisplayed() {
	Assertions.assertElementExists(driver, popup_makeACopyScreen_headerLabel, true);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_newNameLabel, true);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_selectFolderLabel, true);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_foldersSection, true);

	Assertions.assertElementExists(driver, popup_makeACopyScreen_newFolderButton, true);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_cancelButton, true);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_copyAndOpenButton, true);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_copyButton, true);
    }

    public void makeACopy_searchAndSelectFolder(String FolderName) {
	popup_makeACopyScreen_selectFolder = By.xpath(
		"//div[@class='ant-modal-body']//li[@class='ant-tree-treenode-switcher-open']//li[@role='treeitem']//span[@class='inc-overflow-tooltip inc-tree-node-title']//span[text()='"
			+ FolderName + "']");

	ElementActions.type(driver, popup_makeACopyScreen_searchField, FolderName);
	ElementActions.click(driver, popup_makeACopyScreen_selectFolder);
    }

    public void makeACopy_searchAndSelectAndAssertFolderDisplayed(String FolderName) {
	popup_makeACopyScreen_selectFolder = By.xpath(
		"//div[@class='ant-modal-body']//li[@class='ant-tree-treenode-switcher-open']//li[@role='treeitem']//span[@class='inc-overflow-tooltip inc-tree-node-title']//span[text()='"
			+ FolderName + "']");

	ElementActions.type(driver, popup_makeACopyScreen_searchField, FolderName);
	Assertions.assertElementExists(driver, popup_makeACopyScreen_searchField, true);
    }

    public String makeACopy_getDashboard_newName() {
	String NewDashboardName = ElementActions.getText(driver, popup_makeACopyScreen_newNameField);
	return NewDashboardName;
    }

    public void makeACopy_addDashboard_newName(String NewDashboardName) {
	ElementActions.type(driver, popup_makeACopyScreen_newNameField, NewDashboardName);
    }

    public void makeACopy_clickCopyButton() {
	ElementActions.click(driver, popup_makeACopyScreen_copyButton);
    }

    public void makeACopy_clickCopyAndOpenButton() {
	ElementActions.click(driver, popup_makeACopyScreen_copyAndOpenButton);
    }

    public void makeACopy_clickCancelButton() {
	ElementActions.click(driver, popup_makeACopyScreen_cancelButton);
    }

    public void assert_makeACopy_foldersExist(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//li[contains(.,'"
			+ FolderName + "')]");
	Assertions.assertElementExists(driver, popup_makeACopyScreen_foldersSection_folders, true);
    }

    public void assert_makeACopy_foldersNotExist(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//li[contains(.,'"
			+ FolderName + "')]");
	Assertions.assertElementExists(driver, popup_makeACopyScreen_foldersSection_folders, false);
    }

    public void assert_makeACopy_foldersInsideFolderExist(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//li//ul[contains(.,'"
			+ FolderName + "')]");
	Assertions.assertElementExists(driver, popup_makeACopyScreen_foldersSection_folders, true);
    }

    public void assert_makeACopy_foldersInsideFolderNotExist(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//li//ul[contains(.,'"
			+ FolderName + "')]");
	Assertions.assertElementExists(driver, popup_makeACopyScreen_foldersSection_folders, false);
    }

    public void assert_makeACopy_folderButtonEnabled(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//li[contains(.,'"
			+ FolderName + "')]");
	Assertions.assertElementAttribute(driver, popup_makeACopyScreen_foldersSection_folders, "class",
		"ant-tree-treenode-disabled", false);
    }

    public void assert_makeACopy_folderButtonDisabled(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//li[contains(.,'"
			+ FolderName + "')]");

	Assertions.assertElementAttribute(driver, popup_makeACopyScreen_foldersSection_folders, "class",
		"ant-tree-treenode-disabled", true);
    }

    public void assert_makeACopy_defaultSelectedFolder(String FolderName) {
	popup_makeACopyScreen_foldersSection_folders = By.xpath(
		"//div[@class='ant-modal-body']//li[contains(@class,'ant-tree-treenode-switcher-open')]//span[contains(@class,'ant-tree-node-content-wrapper')][contains(.,'"
			+ FolderName + "')]");
	Assertions.assertElementAttribute(driver, popup_makeACopyScreen_foldersSection_folders, "class",
		"([\\s\\S]*)selected", true);
    }

    /**
     * 
     * @param FolderName
     *            Folder To expand OR Collapse
     * @param ExpandOrCollapse
     *            minus for collapse OR plus for expand
     */
    public void makeACopy_expandIconFolders(String FolderName, String ExpandOrCollapse) {
	popup_makeACopy_foldersExapandIcon = By.xpath("//ul[@role='group']//li[contains(.,'" + FolderName
		+ "')]//i[contains(@class,'" + ExpandOrCollapse + "')]");
	ElementActions.click(driver, popup_makeACopy_foldersExapandIcon);
    }

    public void makeACopy_searchContentPageAndAssertAndClick(String SearchText) {
	body_searchContent = By.xpath("//input[@placeholder='Search content']");
	ElementActions.type(driver, body_searchContent, SearchText);
	body_searchContent_resultFound = By
		.xpath("//span[@class='inc-search-option__item--left'][contains(.,'" + SearchText + "')]");
	Assertions.assertElementExists(driver, body_searchContent_resultFound, true);
	ElementActions.click(driver, body_searchContent_resultFound);
    }

    public void assert_makeACopy_errorIsDisplayed_DuplicateDashboardName() {
	popup_makeACopy_errorDuplicateDashboardName = By
		.xpath("//label[contains(@class,'error')][contains(.,'Name already exists!')]");
	Assertions.assertElementExists(driver, popup_makeACopy_errorDuplicateDashboardName, true);
    }

    public void assert_makeACopy_dashboardNewName_dashboardNamePlusCopyWord(String DashboardName) {
	String DashboardNewName = DashboardName + " " + "Copy";
	Assertions.assertElementAttribute(driver, popup_makeACopyScreen_newNameField, "text", DashboardNewName, true);
    }

    // Rename Folder
    public void renameFolder_popup_typeNewFolderName(String NewFolderName) {
	ElementActions.type(driver, popup_renameFolder_newNameField, NewFolderName);
    }

    public void renameFolder_popup_clickRename() {
	ElementActions.click(driver, popup_Rename_RenameButton);
    }

    public void renameFolder_popup_clickCancel() {
	ElementActions.click(driver, popup_Rename_CancelButton);
    }

    public void assert_renameFolder_popup_renameButtonDimmed() {
	Assertions.assertElementAttribute(driver, popup_Rename_RenameButton, "disabled", "true", true);
    }

    public void assert_renameFolder_successMessage(String NewFolderName) {
	renameFolder_successfullyRenamedMessage = By.xpath(
		"//div[@class='ant-notification-notice-description']/span[contains(.,\"You've successfully renamed the Folder to\")]/a[contains(.,'"
			+ NewFolderName + "')]");
	Assertions.assertElementExists(driver, renameFolder_successfullyRenamedMessage, true);
    }

    public void assert_renameFolder_popupScreen_screenContentDisplayed() {
	Assertions.assertElementExists(driver, popup_renameFolder_screen, true);
	Assertions.assertElementExists(driver, popup_renameFolder_headerLabel, true);
	Assertions.assertElementExists(driver, popup_renameFolder_newNameLabel, true);
	Assertions.assertElementExists(driver, popup_renameFolder_newNameField, true);
	Assertions.assertElementExists(driver, popup_Rename_RenameButton, true);
	Assertions.assertElementExists(driver, popup_Rename_CancelButton, true);
    }

    public void assert_renameFolder_errorMessageDisplayed() {
	Assertions.assertElementExists(driver, popup_renameFolder_errorMessage_nameAlreadyExist, true);
    }

    public void renameFolder_clickCloseIcon() {
	ElementActions.click(driver, popup_renameFolder_closeIcon);
    }

    public void assert_renameFolder_screen_notExist() {
	Assertions.assertElementExists(driver, popup_renameFolder_screen, false);
    }

    // Rename Dashboard
    /**
     * 
     * @param NewDashboardName
     * @param Button
     *            Rename OR Cancel
     */
    public void renameDashboard_typeNewDashboardName(String NewDashboardName) {
	ElementActions.type(driver, popup_renameDashboard_field, NewDashboardName);

	popup_renameDashboard_confirmationMessage = By
		.xpath("//div[@class='ant-notification ant-notification-topRight']/span[contains(.,'" + NewDashboardName
			+ "')]/div");
    }

    public void renameDashboard_popup_clickOnActionButtons(String Action) {
	popup_renameDashboard_renameCancel_buttons = By.xpath("//button[contains(.,'" + Action + "')]");
	ElementActions.click(driver, popup_renameDashboard_renameCancel_buttons);
    }

    public void assert_renameDashboard_renamed_confirmationMessage() {
	Assertions.assertElementExists(driver, popup_renameDashboard_confirmationMessage, true);
    }

    public void assert_renameDashboard_renameButtonDisabled() {
	popup_renameDashboard_rename_button = By.xpath("//button[contains(.,'Rename')]");
	Assertions.assertElementAttribute(driver, popup_renameDashboard_rename_button, "disabled", "true", true);
    }

    public void assert_renameDashboard_popupScreen_screenContentDisplayed() {
	popup_renameDashboard_headerLabel = By.xpath("//span[contains(text(),'Rename Dashboard')]");
	Assertions.assertElementExists(driver, popup_renameDashboard_headerLabel, true);

	popup_renameDashboard_newNameLabel = By.xpath("//span[contains(text(),'New Name')]");
	Assertions.assertElementExists(driver, popup_renameDashboard_newNameLabel, true);

	popup_renameDashboard_newNameField = By.id("inc-rename-catalog-item__control");
	Assertions.assertElementExists(driver, popup_renameDashboard_newNameField, true);

	popup_renameDashboard_renameCancel_buttons = By.xpath("//button[contains(.,'Rename')]");
	Assertions.assertElementExists(driver, popup_renameDashboard_renameCancel_buttons, true);

	popup_renameDashboard_renameCancel_buttons = By.xpath("//button[contains(.,'Cancel')]");
	Assertions.assertElementExists(driver, popup_renameDashboard_renameCancel_buttons, true);
    }

    public void assert_renamedDashboard_duplicateName_errorMessageDisplayed() {
	Assertions.assertElementExists(driver, popup_renameDashboard_errorMessage, true);
    }

    public void renameDashboard_clickCloseIcon() {
	ElementActions.click(driver, popup_renameDashboard_closeIcon);
    }

    public void assert_renameDashboard_screenNotExist() {
	Assertions.assertElementExists(driver, popup_renameDashboard_screen, false);
    }

    // Catalog of Content
    public void catalog_searchAssertAndOpenResults_contentSearchBox(String contentName) {
	searchResult_search = By.xpath("//li[@text='" + contentName + "']");
	ElementActions.type(driver, searchWrapper_search_textBox, contentName);
	Assertions.assertElementExists(driver, searchResult_search, true);
	ElementActions.click(driver, searchResult_search);
    }

    public void catalog_searchAndAssertResultNotExist_contentSearchBox(String contentName) {
	searchResult_search = By.xpath("//li[@text='" + contentName + "']");
	ElementActions.type(driver, searchWrapper_search_textBox, contentName);
	Assertions.assertElementExists(driver, searchResult_search, false);
    }

    public void catalog_searchAndAssertResultsDisplayed_contentSearchBox(String contentName) {
	searchResult_search = By.xpath("//li[@text='" + contentName + "']");
	ElementActions.type(driver, searchWrapper_search_textBox, contentName);
	Assertions.assertElementExists(driver, searchResult_search, true);
    }

    /**
     * Below function only used when adding profile picture for the user.
     * 
     * @param contentName
     */
    public void catalog_searchAndAssert_resultsDisplayProfilePicture_contentSearchBox(String contentName) {
	searchResult_search = By.xpath("//li[@text='" + contentName + "']");
	ElementActions.type(driver, searchWrapper_search_textBox, contentName);
	Assertions.assertElementExists(driver, searchResult_search, true);
	Assertions.assertElementExists(driver, searchResult_profilePicture, true);
    }

    // Delete Dashbaord
    public void assert_deleteDashboard_popup_screenContentDisplayed(String DashboardName) {
	Assertions.assertElementExists(driver, popup_deleteDashboard_questionCircle, true);
	Assertions.assertElementExists(driver, popup_deleteDashboard_confirmTitle, true);
	Assertions.assertElementAttribute(driver, popup_deleteDashboard_confirmContent, "text",
		"Heads up! Deleting " + DashboardName + " can't be undone.", true);
	Assertions.assertElementExists(driver, popup_deleteDashboard_neverMind_Button, true);
	Assertions.assertElementExists(driver, popup_deleteDashboard_delete_Button, true);
    }

    public void deleteDashboard_popup_click_confirmationButton_delete() {
	ElementActions.click(driver, popup_deleteDashboard_delete_Button);
    }

    public void deleteDashboard_popup_click_confirmationButton_neverMind() {
	ElementActions.click(driver, popup_deleteDashboard_neverMind_Button);
    }

    public void assert_deleteDashboard_popup_dashboardNameIsCorrect(String DashboardName) {
	Assertions.assertElementAttribute(driver, popup_deleteDashboard_confirmContent, "text",
		"Heads up! Deleting " + DashboardName + " can't be undone.", true);
    }

    public void assert_deleteDashboard_popup_dashboardName_specialCharachters_IsCorrect(String DashboardName) {
	Assertions.assertElementAttribute(driver, popup_deleteDashboard_confirmContent, "text",
		"Heads up! Deleting " + DashboardName + " can't be undone.", 1, true);
    }

    // Catalog of content

    /**
     * Function only check for specific scenario which folder has 1 folder and 1
     * dashboard inside it.
     * 
     * @param FolderName
     */
    public void assert_folderCardDescription_grammerAndCountCorrect(String FolderName, String NoOfFolderAndDashboards) {
	body_folderCardDescription = By.xpath("//span[text()='" + FolderName
		+ "']/parent::div/following-sibling::div/div[@class='inc-card-description']");
	Assertions.assertElementAttribute(driver, body_folderCardDescription, "text", NoOfFolderAndDashboards, true);
    }

    public void assert_sortingIsCorrect(String SortingType, String firstDashboard, String secondDashboard,
	    String thirdDashboard) {
	switch (SortingType) {
	case "Owner":
	    assert_sorting(firstDashboard, secondDashboard, thirdDashboard);
	    break;

	case "Modified By":
	    assert_sorting(firstDashboard, secondDashboard, thirdDashboard);
	    break;

	case "Name":
	    assert_sorting(firstDashboard, secondDashboard, thirdDashboard);
	    break;

	default:
	    break;
	}
    }

    /**
     * Function will get the dashboards name and compare them with the expected
     * result retrieved from Excel sheet
     * 
     * @param firstDashboard
     *            Will be retrieved from excel sheet
     * @param secondDashboard
     *            Will be retrieved from excel sheet
     * @param thirdDashboard
     *            Will be retrieved from excel sheet
     */
    public void assert_sorting(String firstDashboard, String secondDashboard, String thirdDashboard) {
	Assertions.assertElementAttribute(driver, body_firstDashboard_inTheList, "text", firstDashboard, true);
	Assertions.assertElementAttribute(driver, body_secondDashboard_inTheList, "text", secondDashboard, true);
	Assertions.assertElementAttribute(driver, body_thirdDashboard_inTheList, "text", thirdDashboard, true);
    }

    /**
     * "Name" "Last Modified" "Owner"
     * 
     * @param SortType
     */
    public void click_onSorting(String SortType) {
	ElementActions.click(driver, body_sortButton);
	By body_sortButton_types = By.xpath("//li[@class='ant-dropdown-menu-item'][contains(.,'" + SortType + "')]");
	ElementActions.click(driver, body_sortButton_types);
    }

}
