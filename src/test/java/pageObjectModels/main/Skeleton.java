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
	// sideMenu_dataSourcesAndDataFiles_link
	By sideMenu_dataSrourcesAndDataFiles_link = By.xpath("//a[@id='dataSourcesItem']");
	// sideMenu_schemasAndSessionVariables_link
	By sideMenu_schemasAndSessionVariables_link = By.xpath("//a[@id='schemaItem']");
	// sideMenu_businessSchemas_link
	By sideMenu_businessSchemas_link = By.xpath("//a[@id='businessSchemaItem']");
	// sideMenu_scheduler_link
	By sideMenu_scheduler_link = By.xpath("//a[@id='schedulerItem']");
	// sideMenu_content_link
	By sideMenu_content_link = By.xpath("//a[@id='contentItem']");
	// sideMenu_security_link
	By sideMenu_security_link = By.xpath("//a[@id='securityItem']");
	

	
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
	
	//// Functions
	public Skeleton(WebDriver driver) {
		this.driver = driver;
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
	
	//	public void Click_securityTab() {
	//		ElementActions.click(driver, sideMenu_security_link);
	//	}

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
	public void AssertExist_SideMenu_Security()

	{

	Assertions.assertElementExists(driver, sideMenu_security_link, true);

	}


	public void AssertExist_SideMenu_Content()

	{

	Assertions.assertElementExists(driver, sideMenu_content_link, true);

	}


	public void AssertExist_SideMenu_Scheduler()

	{

	Assertions.assertElementExists(driver, sideMenu_scheduler_link, true);

	}


	public void AssertExist_SideMenu_businessSchemas()

	{

	Assertions.assertElementExists(driver, sideMenu_businessSchemas_link, true);

	}


	public void AssertExist_SideMenu_schemasAndSessionVariables()

	{

	Assertions.assertElementExists(driver, sideMenu_schemasAndSessionVariables_link, true);

	}


	public void AssertExist_SideMenu_dataSrourcesAndDataFiles()

	{

	Assertions.assertElementExists(driver, sideMenu_dataSrourcesAndDataFiles_link, true);

	}
}
