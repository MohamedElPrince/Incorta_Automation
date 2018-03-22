package pageObjectModels.modules.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;

public class Content_AllContent_Dashboard {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));

	//// Elements
	By header_dashboardName_textBox = By.xpath("//div[@id='dashboardHeader']//input");
	By body_insightName_label = By.xpath("//header/span");

	By popup_sendDashboard_type_radioButton; // label[normalize-space(.)='HTML']/input[@type='radio']
	By popup_sendDashboard_plusReciever_button = By
			.xpath("//a[contains(@class,'usersHeadAdd')]/img[contains(@src,'plus')]");
	By popup_sendDashboard_emailAddress_textBox = By.xpath("//input[@ng-model='$parent.entitySearchText']");
	By popup_sendDashboard_add_button = By.xpath("//button[@type='button'][normalize-space()='Add']");
	By popup_sendDashboard_reciever_label; // div[contains(@class,'folderUserRow')][contains(normalize-space(.),'mohab.mohie@incorta.com')]//h5[contains(@class,'UserData')]
	By popup_sendDashboard_send_button = By.xpath("//button[@type='button'][normalize-space()='Send']");

	//// Functions
	public Content_AllContent_Dashboard(WebDriver driver) {
		this.driver = driver;
	}

	public void Assert_dashboardName(String name) {
		CustomAssertions.cAssertElementAttribute(driver, header_dashboardName_textBox, "Text", "(.*" + name + ".*)",
				true);
	}

	public void Assert_insightName(String name) {
		CustomAssertions.cAssertElementAttribute(driver, body_insightName_label, "Text", "(.*" + name + ".*)", true);
	}

	public void selectEmailFormat(String format) {
		By popup_sendDashboard_type_radioButton = By
				.xpath("// label[normalize-space(.)='" + format + "']/input[@type='radio']");
		ElementActions.click(driver, popup_sendDashboard_type_radioButton);
	}

	public void addUserEmailToRecieversList(String email) {
		ElementActions.click(driver, popup_sendDashboard_plusReciever_button);
		ElementActions.type(driver, popup_sendDashboard_emailAddress_textBox, email);
		ElementActions.click(driver, popup_sendDashboard_add_button);
		popup_sendDashboard_reciever_label = By
				.xpath("// div[contains(@class,'folderUserRow')][contains(normalize-space(.),'" + email
						+ "')]//h5[contains(@class,'UserData')]");
		CustomAssertions.cAssertElementExists(driver, popup_sendDashboard_reciever_label, true);
	}

	public void scheduleEmailSending() {
		ElementActions.click(driver, popup_sendDashboard_send_button);
	}
}
