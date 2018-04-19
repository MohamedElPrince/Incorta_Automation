package pageObjectModels.modules.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class Login_Logout {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String loggedOutMessageHeader = testDataReader.getCellData("LoggedOutMessageHeader");
	String loggedOutMessageBody = testDataReader.getCellData("LoggedOutMessageBody");

	//// Elements
	By body_loggedOutMessageHeader_label = By.tagName("h1");
	By body_loggedOutMessageBody_label = By.tagName("p");
	By body_goToLoginPage_button = By.tagName("button");

	//// Functions
	public Login_Logout(WebDriver driver) {
		this.driver = driver;
	}

	public void Assert_logoutMessageHeaderAndBodyAreCorrect() {
		Assertions.assertElementAttribute(driver, body_loggedOutMessageHeader_label, "text", loggedOutMessageHeader,
				true);
		// CustomAssertions.cAssertElementAttribute(driver,
		// body_loggedOutMessageBody_label, "text", loggedOutMessageBody,true);
	}

	public void Navigate_toLoginPage() {
		ElementActions.click(driver, body_goToLoginPage_button);
	}
}
