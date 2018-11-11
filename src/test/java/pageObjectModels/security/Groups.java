package pageObjectModels.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;

public class Groups {
    //// Variables
    private WebDriver driver;
    private ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
    private String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_security_groups");

    //// Elements
    private By body_group_link;
    private By body_iframe = By.xpath("//iframe[@title='Legacy Web']");

    private By popup_addNewGroup_name_textBox = By.xpath("//input[contains(@class,'groupInput')][@name='name']");
    private By popup_addNewGroup_description_textBox = By
	    .xpath("//input[contains(@class,'groupInput')][@name='description']");
    private By popup_addNewGroup_addGroup_button = By.xpath("//button[normalize-space(.)='Add Group']");

    // Button Delete selection from Actions in groups screen. Created by AbdelSalam
    private By DeleteSelectionButton = By.xpath("//button[@class='btn btn-default userSaveBtn']");
    private By group_Checkbox;

    //// Functions
    public Groups(WebDriver driver) {
	this.driver = driver;
    }

    public void Navigate_toURL() {
	BrowserActions.navigateToURL(driver, url);
    }
    // Assert_groupsTabIsSelected
    // Assert_groupsLabelIsDisplayed
    // Assert_groupsNumberIsCorrect

    public void Assert_groupIsDisplayed(String name) {
	ElementActions.switchToIframe(driver, body_iframe);
	body_group_link = By
		.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
			+ "')]/p");
	Assertions.assertElementExists(driver, body_group_link, true);
	ElementActions.switchToDefaultContent(driver);
    }

    // Assert_detailsForGroupAreCorrect
    // Select_groupCheckbox
    public void Click_group(String name) {
	ElementActions.switchToIframe(driver, body_iframe);
	body_group_link = By
		.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
			+ "')]/p");
	ElementActions.click(driver, body_group_link);
	ElementActions.switchToDefaultContent(driver);
    }

    public String AddNewGroup() {
	return AddNewGroup("Automation" + "_Group_" + String.valueOf(System.currentTimeMillis()), "");
    }

    public String AddNewGroup(String name, String description) {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.type(driver, popup_addNewGroup_name_textBox, name);
	ElementActions.type(driver, popup_addNewGroup_description_textBox, description);
	ElementActions.click(driver, popup_addNewGroup_addGroup_button);
	ElementActions.switchToDefaultContent(driver);
	return name;
    }

    public void ClickOnGroupCheckBox(String groupNameToBeSelected) {
	ElementActions.switchToIframe(driver, body_iframe);
	group_Checkbox = By.xpath("//input[contains(@name,'" + groupNameToBeSelected + "')]");
	ElementActions.click(driver, group_Checkbox);
	ElementActions.switchToDefaultContent(driver);
    }

    public void Assert_groupIsNotDisplayed(String name) {
	ElementActions.switchToIframe(driver, body_iframe);
	body_group_link = By
		.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
			+ "')]/p");
	Assertions.assertElementExists(driver, body_group_link, false);
	ElementActions.switchToDefaultContent(driver);
    }

    public void ClickOnDeleteButton() {
	ElementActions.switchToIframe(driver, body_iframe);
	ElementActions.click(driver, DeleteSelectionButton);
	ElementActions.switchToDefaultContent(driver);
    }

}
