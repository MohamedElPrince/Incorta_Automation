package pageObjectModels.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.validationsLibrary.Assertions;

public class Skeleton {
	//// Variables
	WebDriver driver;

	//// Elements

	// header_incortaLogo_image

	By SideMenu_GenericSideMenuItem_Link;

	By sideMenu_impersonation_switchBack_link = By.xpath("//img[contains(@src,'icon-switch-back')]");

	By header_search_textBox = By.id("inc-search-box-input");
	By header_searchResult_link;

	By header_load_button = By.id("load_types_btn");
	By header_loadMenuItem_link;
	By header_actions_button = By.xpath("//span[text()='Actions']");
	By header_export_button = By.xpath("//button[@id='dropdownMenu1']/img[contains(@src,'export')]");
	// header_settings_button
	By header_add_button = By.xpath("//div[contains(@class,'HeaderBtnWrap')]//img[contains(@src,'plus_icon')]");
	By header_genericMenuItem_link;
	By header_user_button = By
			.xpath("//img[contains(@ng-src,'getUserPicture')]//ancestor::a[@class='dropdown-toggle']");
	By header_chooseVisualization_button = By.id("charts-button");
	By header_userMenuItem_link;
	By header_done_link = By.id("saveButton_Charts");
	By header_exportStatus_button = By.xpath("//button[@id='dropdownMenu1']/img[contains(@src,'export')]/parent::button/parent::div");
	By header_settings_button = By.xpath("//a[@class='btn right ng-scope'][@ng-click='openSettings()']");

	
	

	//// Functions
	public Skeleton(WebDriver driver) {
		this.driver = driver;
	}
	
	public void assertExportIconIsNotDisplayed() {

			Assertions.assertElementAttribute(driver, header_exportStatus_button ,"class" ,"dropdown right ng-hide", true);

		}
	// Assert_logoIsDisplayed
	// Navigate_toSideMenuItem

	public void SearchForContentAndOpenResult(String query) {
		ElementActions.type(driver, header_search_textBox, query);
		header_searchResult_link = By.xpath("//*[@id='search-box-container']//h5[contains(normalize-space(),'" + query
				+ "')]/ancestor::a[contains(@id,'searchBoxItem')]");
		ElementActions.click(driver, header_searchResult_link);
	}

	public void SearchForContentAndAssertResultIsDisplayed(String query) {
		ElementActions.type(driver, header_search_textBox, query);
		header_searchResult_link = By.xpath("//*[@id='search-box-container']//h5[contains(normalize-space(),'" + query
				+ "')]/ancestor::a[contains(@id,'searchBoxItem')]");
		Assertions.assertElementExists(driver, header_searchResult_link, true);
	}

	// Assert_searchResultIsDisplayed
	// Navigate_toSearchResult

	// public void Click_securityTab() {
	// ElementActions.click(driver, sideMenu_security_link);
	// }

	public void Click_load() {
		ElementActions.click(driver, header_load_button);
	}

	public void Click_add() {
		ElementActions.click(driver, header_add_button);
	}

	public void Click_done() {
		ElementActions.click(driver, header_done_link);
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
		ElementActions.click(driver, header_settings_button);
	}

	public void Select_fromDropdownMenu(String functionName) {
		header_genericMenuItem_link = By
				.xpath("//ul[contains(@class,'dropdown-menu') and @role='menu']//*[contains(normalize-space(.),'"
						+ functionName + "')][@role='menuitem' or @class='importExport']");
		ElementActions.click(driver, header_genericMenuItem_link);
	}

	public void Hover_overDropdownMenu(String functionName) {
		header_genericMenuItem_link = By
				.xpath("//ul[contains(@class,'dropdown-menu') and @role='menu']//*[contains(normalize-space(.),'"
						+ functionName + "')][@role='menuitem' or @class='importExport']");
		ElementActions.hover(driver, header_genericMenuItem_link);
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
	 * @param tabName
	 *            --> Could have one of the following options: dataSourcesItem
	 *            schemaItem businessSchemaItem schedulerItem contentItem
	 *            securityItem
	 */
	public void AssertElementExist_Sidemenu(String tabName) {
		SideMenu_GenericSideMenuItem_Link = By.id(tabName);
		Assertions.assertElementExists(driver, SideMenu_GenericSideMenuItem_Link, true);
	}

	/**
	 * 
	 * @param tabName
	 *            --> Could have one of the following options: dataSourcesItem
	 *            schemaItem businessSchemaItem schedulerItem contentItem
	 *            securityItem
	 */
	public void Click_Element_Sidemenu(String tabName) {
		SideMenu_GenericSideMenuItem_Link = By.id(tabName);
		ElementActions.click(driver, SideMenu_GenericSideMenuItem_Link);
	}

}
