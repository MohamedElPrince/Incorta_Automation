package pageObjectModels.modules.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.elementActionLibrary.ElementActions;

public class Main_Skeleton {
	//// Variables
	WebDriver driver;

	//// Elements

	// header_incortaLogo_image
	// sideMenu_security_link
	// sideMenu_dataSourcesAndDataFiles_link
	// sideMenu_schemasAndSessionVariables_link
	// sideMenu_businessSchemas_link
	// sideMenu_scheduler_link
	// sideMenu_content_link

	// header_searchBar_textBox

	By header_load_button = By.id("load_types_btn");
	By header_loadMenuItem_link;
	// header_actions_button
	// header_actionsMenuItem_link
	By header_add_button = By.xpath("//div[contains(@class,'HeaderBtnWrap')]//img[contains(@src,'plus_icon')]");
	By header_addMenuItem_link;
	By header_user_button = By.className("dropdown-toggle");
	By header_userMenuItem_link;
	By header_done_link = By.id("saveButton_Charts");

	//// Functions
	public Main_Skeleton(WebDriver driver) {
		this.driver = driver;
	}
	// Assert_logoIsDisplayed
	// Navigate_toSideMenuItem

	// Search_query
	// Assert_searchResultIsDisplayed
	// Navigate_toSearchResult

	// Select_fromActionsMenu
	public void Click_load() {
		ElementActions.click(driver, header_load_button);
	}

	public void Click_add() {
		ElementActions.click(driver, header_add_button);
	}

	public void Click_done() {
		ElementActions.click(driver, header_done_link);
	}

	public void Select_fromDropdownMenu(String functionName) {
		header_addMenuItem_link = By
				.xpath("//ul[contains(@class,'dropdown-menu') and @role='menu']//a[contains(normalize-space(.),'"
						+ functionName + "')]");
		ElementActions.click(driver, header_addMenuItem_link);
	}

	public void Hover_overDropdownMenu(String functionName) {
		header_addMenuItem_link = By
				.xpath("//ul[contains(@class,'dropdown-menu') and @role='menu']//a[contains(normalize-space(.),'"
						+ functionName + "')]");
		ElementActions.hover(driver, header_addMenuItem_link);
	}

	public void Select_fromUserMenu(String functionName) {
		ElementActions.click(driver, header_user_button);
		header_userMenuItem_link = By
				.xpath("//ul[contains(@class,'UserDropdown')]//a[contains(text(),'" + functionName + "')]");
		ElementActions.click(driver, header_userMenuItem_link);
	}
}
