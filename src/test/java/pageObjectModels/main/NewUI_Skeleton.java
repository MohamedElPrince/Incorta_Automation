package pageObjectModels.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.validationsLibrary.Assertions;

public class NewUI_Skeleton {
	//// Variables
	WebDriver driver;

	//// Elements

	// header_incortaLogo_image
	By body_iframe = By.xpath("//iframe[@title='Legacy Web']");

	By SideMenu_GenericSideMenuItem_Link;

	By sideMenu_impersonation_switchBack_link = By.xpath("//img[contains(@src,'icon-switch-back')]");

	By header_search_textBox_security = By.xpath("//input[@placeholder='Search']");
	By header_search_textBox_content = By.xpath("//input[@placeholder='Search content']");

	By header_searchResult_link_security;
	By header_searchResult_link_content;

	By header_load_button = By.id("load_types_btn");
	By header_loadMenuItem_link;
	By header_actions_button = By.xpath("//span[text()='Actions']");
	By header_export_button = By.xpath("//button[@id='dropdownMenu1']/img[contains(@src,'export')]");
	// header_settings_button
	By header_add_content_button = By.xpath("//i[@class='anticon anticon-plus']//parent::span//parent::span//parent::button");
	By header_add_security_button = By.xpath("//a[@class='btn ']/img[contains(@src,'plus_icon')]");
	
	
	By header_genericMenuItem_link;
	By header_user_button = By
			.xpath("//img[contains(@ng-src,'getUserPicture')]//ancestor::a[@class='dropdown-toggle']");
	By header_chooseVisualization_button = By.id("charts-button");
	By header_userMenuItem_link;
	By header_done_link = By.id("saveButton_Charts");
	By header_exportStatus_button = By
			.xpath("//button[@id='dropdownMenu1']/img[contains(@src,'export')]/parent::button/parent::div");
	By header_settings_button = By.xpath("//a[@class='btn right ng-scope'][@ng-click='openSettings()']");
	By header_export_button_ExportOptions;
	By header_settings_list = By.id("settings-button");
	By header_allContentDashboard_Settings_Button = By.xpath("//button[@id='dropdownMenu1']//img[contains(@src,'settings')]");
	//// Functions
	public NewUI_Skeleton(WebDriver driver) {
		this.driver = driver;
	}

	public void assertExportIconIsNotDisplayed() {

		// Assertions.assertElementAttribute(driver, header_exportStatus_button, "class", "dropdown right ng-hide", true);
		Assertions.assertElementExists(driver, header_exportStatus_button, false);
		
	}

	public void assertExportIconIsDisplayed() {
		Assertions.assertElementExists(driver, header_export_button, true);
		Assertions.assertElementAttribute(driver, header_exportStatus_button, "class", "dropdown right ng-hide", false);
	}
	// Assert_logoIsDisplayed
	// Navigate_toSideMenuItem

	public void SearchForContentAndOpenResult_security(String query) {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.type(driver, header_search_textBox_security, query);
		header_searchResult_link_security = By.xpath("//*[@id='search-box-container']//h5[contains(normalize-space(),'" + query
				+ "')]/ancestor::a[contains(@id,'searchBoxItem')]");
		ElementActions.click(driver, header_searchResult_link_security);
		ElementActions.switchToDefaultContent(driver);
	}

	public void SearchForContentAndAssertResultIsDisplayed_security(String query) {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.type(driver, header_search_textBox_security, query);
		header_searchResult_link_security = By.xpath("//*[@id='search-box-container']//h5[contains(normalize-space(),'" + query
				+ "')]/ancestor::a[contains(@id,'searchBoxItem')]");
		Assertions.assertElementExists(driver, header_searchResult_link_security, true);
		ElementActions.switchToDefaultContent(driver);
	}
	
	public void SearchForContentAndOpenResult_content(String query) {
		ElementActions.type(driver, header_search_textBox_content, query);
		header_searchResult_link_content = By.xpath("//span[@class='inc-search-option__item--left']//mark[contains(text(),'"+query+"')]");
		ElementActions.click(driver, header_searchResult_link_content);
	}

	// Assert_searchResultIsDisplayed
	// Navigate_toSearchResult

	// public void Click_securityTab() {
	// ElementActions.click(driver, sideMenu_security_link);
	// }
	
	public void Click_load() {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.click(driver, header_load_button);		
		ElementActions.switchToDefaultContent(driver);
	}

	public void Click_add_content() {
		ElementActions.click(driver, header_add_content_button);
	}
	
	public void Click_add_security() {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.click(driver, header_add_security_button);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Click_done() {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.click(driver, header_done_link);
		ElementActions.switchToDefaultContent(driver);
	}
	
	public void Click_doneAndRefresh() {
		ElementActions.click(driver, header_done_link);
		BrowserActions.refreshCurrentPage(driver);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Click_export() {
		ElementActions.click(driver, header_export_button);
	}

	public void Click_actions() {
		ElementActions.click(driver, header_actions_button);
	}
	
	public void Click_ChooseVisualization() {
		ElementActions.click(driver, header_chooseVisualization_button);
	}

	public void Click_Settings()
	{
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.click(driver, header_settings_button);
		ElementActions.switchToDefaultContent(driver);
	}
		
	public void click_settings_allContent_dashboard()
	{
		ElementActions.click(driver, header_allContentDashboard_Settings_Button);
	}
	
	public void Open_SettingsList()
	{
		ElementActions.click(driver, header_settings_list);
	}
	
	public void Select_fromDropdownMenu(String functionName) {
		header_genericMenuItem_link = By
				.xpath("//ul[contains(@class,'dropdown-menu') and @role='menu']//*[contains(normalize-space(.),'"
						+ functionName + "')][@role='menuitem' or @class='importExport']");
		ElementActions.click(driver, header_genericMenuItem_link);
	}

	public void Hover_overDropdownMenu(String functionName) {
		ElementActions.switchToIframe(driver, body_iframe);
		header_genericMenuItem_link = By
				.xpath("//ul[contains(@class,'dropdown-menu') and @role='menu']//*[contains(normalize-space(.),'"
						+ functionName + "')][@role='menuitem' or @class='importExport']");
		ElementActions.hover(driver, header_genericMenuItem_link);
		ElementActions.switchToDefaultContent(driver);
	}

	public void Select_fromUserMenu(String functionName) {
		ElementActions.click(driver, header_user_button);
		header_userMenuItem_link = By.xpath(
				"//ul[contains(@class,'UserDropdown')]//li[contains(normalize-space(.),'" + functionName + "')]");
		ElementActions.click(driver, header_userMenuItem_link);
	}

	public void Assert_impersonation_switchBack_link_IsDisplayed() {
		Assertions.assertElementExists(driver, sideMenu_impersonation_switchBack_link, true);
	}

	public void Click_impersonation_switchBack_link() {
		ElementActions.click(driver, sideMenu_impersonation_switchBack_link);
	}

	public void Assert_fromUserMenu(String functionName) {
		ElementActions.click(driver, header_user_button);
		header_userMenuItem_link = By.xpath(
				"//ul[contains(@class,'UserDropdown')]//li[contains(normalize-space(.),'" + functionName + "')]");
		Assertions.assertElementExists(driver, header_userMenuItem_link, true);
	}

	/**
	 * 
	 * @param tabName --> Could have one of the following options:
	 * dataSourcesItem
	 * schemaItem
	 * businessSchemaItem
	 * schedulerItem
	 * contentItem
	 * securityItem
	 */
	public void AssertElementExist_Sidemenu(String tabName)
	{
		SideMenu_GenericSideMenuItem_Link = By.id(tabName);
		Assertions.assertElementExists(driver, SideMenu_GenericSideMenuItem_Link, true);
	}
	
	/**
	 * 
	 * @param tabName --> Could have one of the following options:
	 * dataSourcesItem
	 * schemaItem
	 * businessSchemaItem
	 * schedulerItem
	 * contentItem
	 * securityItem
	 */
	public void Click_Element_Sidemenu(String tabName)
	{
		SideMenu_GenericSideMenuItem_Link = By.id(tabName);
		ElementActions.click(driver, SideMenu_GenericSideMenuItem_Link);
	}
	
	/**
	 * 
	 * @param ExportOptions
	 * Share
	 * Send
	 * Schedule
	 */
	public void Click_FromExportMenu(String ExportOptions)
	{
		header_export_button_ExportOptions = By.xpath("//li[@class = \"importExport\"][contains(string(),'"+ExportOptions+"')]");
		ElementActions.click(driver, header_export_button_ExportOptions);
	}
}
