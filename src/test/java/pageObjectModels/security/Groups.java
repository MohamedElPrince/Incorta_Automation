package pageObjectModels.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaft.browser.BrowserActions;
import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;

public class Groups {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_security_groups");

	//// Elements
	By header_groupsTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Groups']");
	// header_groups_label

	// body_group_checkbox
	By body_group_link;
	// body_description_label

	By popup_addNewGroup_name_textBox = By.xpath("//input[contains(@class,'groupInput')][@name='name']");
	By popup_addNewGroup_description_textBox = By.xpath("//input[contains(@class,'groupInput')][@name='description']");
	By popup_addNewGroup_addGroup_button = By.xpath("//button[normalize-space(.)='Add Group']");
	By popup_addNewGroup_cancel_button = By.xpath("//button[normalize-space(.)='Cancel']");
	
	//Button Delete selection from Actions in groups screen. Created by AbdelSalam
		By DeleteSelectionButton = By.xpath("//button[@class='btn btn-default userSaveBtn']");
		//By popup_Delete_button ;
		By group_Checkbox ;

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
		body_group_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		Assertions.assertElementExists(driver, body_group_link, true);
	}

	// Assert_detailsForGroupAreCorrect
	// Select_groupCheckbox
	public void Click_group(String name) {
		body_group_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		ElementActions.click(driver, body_group_link);
	}

	public String AddNewGroup() {
		return AddNewGroup("Automation" + "_Group_" + String.valueOf(System.currentTimeMillis()), "");
	}

	public String AddNewGroup(String name, String description) {
		ElementActions.type(driver, popup_addNewGroup_name_textBox, name);
		ElementActions.type(driver, popup_addNewGroup_description_textBox, description);
		ElementActions.click(driver, popup_addNewGroup_addGroup_button);
		return name;
	}
	
	
		public void ClickOnGroupCheckBox (String groupNameToBeSelected)
		{
			group_Checkbox =By.xpath("//input[contains(@name,'"+groupNameToBeSelected+"')]");
			ElementActions.click(driver, group_Checkbox);
		}
		
		public void Assert_groupIsNotDisplayed(String name) {
			body_group_link = By
					.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
							+ "')]/p");
			Assertions.assertElementExists(driver, body_group_link, false);
		}
		
		public void ClickOnDeleteButton()
		{
			ElementActions.click(driver, DeleteSelectionButton);
		}

}
