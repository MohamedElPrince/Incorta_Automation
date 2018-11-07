package pageObjectModels.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;

public class NewUI_SignOut {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String signedOutMessageHeader = testDataReader.getCellData("signedOutMessageHeader");
	String signedOutMessageBody = testDataReader.getCellData("signedOutMessageBody");

	//// Elements
	By body_signedOutMessageHeader_label = By
			.xpath("//div[@class='inc-login__forget-password-conf-container-reset-conf-text']/span");
	By body_signedOutMessageBody_label = By
			.xpath("//div[@class='inc-login__forget-password-conf-container-reset-conf-text-body']/span");
	By body_goToSignInPage_link = By.xpath("//a[@href='/incorta#/login']");

	//// Functions
	public NewUI_SignOut(WebDriver driver) {
		this.driver = driver;
	}

	public void assert_signOutMessageHeaderAndBodyAreCorrect() {
		Assertions.assertElementAttribute(driver, body_signedOutMessageHeader_label, "text", signedOutMessageHeader,
				true);
		Assertions.assertElementAttribute(driver, body_signedOutMessageBody_label, "text", signedOutMessageBody, true);
	}

	public void navigate_toLoginPage() {
		ElementActions.click(driver, body_goToSignInPage_link);
	}
}
