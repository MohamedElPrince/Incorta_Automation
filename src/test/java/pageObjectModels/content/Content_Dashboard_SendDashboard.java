package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;

public class Content_Dashboard_SendDashboard {
    //// Variables
    private WebDriver driver;

    //// Elements
    // Send Dashboard
    private By body_sendDashboard_bodyField = By.xpath("//div[@class='input-group body-box']//textarea");
    private By body_sendDashboard_dataFormat;
    private By body_sendDashboard_subjectField = By.xpath("//div[@class='inc-schedule-email']//input[@type='text']");
    private By body_sendDashboard_addRecipients = By.xpath("//span[@class='recipients-search']//input");
    private By body_sendDashboard_addRecipientsTo_recipientsTypeMenu;
    private By body_sendDashboard_addRecipientsCc_recipientsTypeMenu;
    private By body_sendDashboard_addRecipientsBcc_recipientsTypeMenu;
    private By body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo;
    private By body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc;
    private By body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc;

    private By body_sendDashboard_addRecipients_selectRecipientsType_To;
    private By body_sendDashboard_addRecipients_selectRecipientsType_Cc;
    private By body_sendDashboard_addRecipients_selectRecipientsType_Bcc;

    //// Functions
    public Content_Dashboard_SendDashboard(WebDriver driver) {
	this.driver = driver;
    }

    public void sendDashboard_addFields(String subjectArea, String bodyArea, String dataFormat, String ToEmailAddress,
	    String CcEmailAddress, String BccEmailAddress) {
	// Subject
	if (!subjectArea.trim().equals("")) {
	    ElementActions.type(driver, body_sendDashboard_subjectField, subjectArea);
	}
	// Body
	if (!bodyArea.trim().equals("")) {
	    ElementActions.type(driver, body_sendDashboard_bodyField, bodyArea);
	}
	// Data format
	body_sendDashboard_dataFormat = By
		.xpath("//div/label[contains(@class,'ant-radio-button-wrapper')][contains(.,'" + dataFormat + "')]");
	ElementActions.click(driver, body_sendDashboard_dataFormat);
	// Recipients
	// To
	if (!ToEmailAddress.trim().equals("")) {
	    body_sendDashboard_addRecipientsTo_recipientsTypeMenu = By.xpath("//span[contains(.,'" + ToEmailAddress
		    + "')]" + "//following-sibling::span//span[@class='recipient-menu']//button//i");
	    body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo = By
		    .xpath("//li[@type='emails'][contains(.,'" + ToEmailAddress + "')]");

	    ElementActions.type(driver, body_sendDashboard_addRecipients, ToEmailAddress);
	    ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientTo);
	    ElementActions.click(driver, body_sendDashboard_addRecipientsTo_recipientsTypeMenu);
	    body_sendDashboard_addRecipients_selectRecipientsType_To = By
		    .xpath("//div[@class='inc-search-option__item'][contains(.,'" + ToEmailAddress
			    + "')]//li[@class='ant-dropdown-menu-item'][contains(.,'To')]");
	    ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_To);
	}
	// Cc
	if (!CcEmailAddress.trim().equals("")) {
	    body_sendDashboard_addRecipientsCc_recipientsTypeMenu = By.xpath("//span[contains(.,'" + CcEmailAddress
		    + "')]" + "//following-sibling::span//span[@class='recipient-menu']//button//i");
	    body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc = By
		    .xpath("//li[@type='emails'][contains(.,'" + CcEmailAddress + "')]");

	    ElementActions.type(driver, body_sendDashboard_addRecipients, CcEmailAddress);
	    ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientCc);
	    ElementActions.click(driver, body_sendDashboard_addRecipientsCc_recipientsTypeMenu);
	    body_sendDashboard_addRecipients_selectRecipientsType_Cc = By
		    .xpath("//div[@class='inc-search-option__item'][contains(.,'" + CcEmailAddress
			    + "')]//li[@class='ant-dropdown-menu-item'][contains(.,'Cc')]");
	    ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_Cc);
	}
	// Bcc
	if (!BccEmailAddress.trim().equals("")) {
	    body_sendDashboard_addRecipientsBcc_recipientsTypeMenu = By.xpath("//span[contains(.,'" + BccEmailAddress
		    + "')]" + "//following-sibling::span//span[@class='recipient-menu']//button//i");
	    body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc = By
		    .xpath("//li[@type='emails'][contains(.,'" + BccEmailAddress + "')]");

	    ElementActions.type(driver, body_sendDashboard_addRecipients, BccEmailAddress);
	    ElementActions.click(driver, body_sendDashboard_addRecipientsTypeMenu_selectRecipientBcc);
	    ElementActions.click(driver, body_sendDashboard_addRecipientsBcc_recipientsTypeMenu);
	    body_sendDashboard_addRecipients_selectRecipientsType_Bcc = By
		    .xpath("//div[@class='inc-search-option__item'][contains(.,'" + BccEmailAddress
			    + "')]//li[@class='ant-dropdown-menu-item'][contains(.,'Bcc')]");
	    ElementActions.click(driver, body_sendDashboard_addRecipients_selectRecipientsType_Bcc);
	}
    }

    public void sendScheduleDashboard_click_send_schedule_buttons(String Actions) {
	By body_sendDashboard_sendSchedule_button = By.xpath("//button[.='" + Actions + "']");
	ElementActions.click(driver, body_sendDashboard_sendSchedule_button);
    }

}
