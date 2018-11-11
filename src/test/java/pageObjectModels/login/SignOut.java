package pageObjectModels.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

public class SignOut {

    //// Variables
    private WebDriver driver;
    private ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
    private String signedOutMessageHeader = testDataReader.getCellData("signedOutMessageHeader");
    private String signedOutMessageBody = testDataReader.getCellData("signedOutMessageBody");

    //// Elements
    private By header_incortaLogo_image = By.xpath("//img[contains(@src,'incorta')]");
    private By header_incorta_website_logo = By.xpath("//span[text()='Website']/parent::a");
    private By header_incorta_twitter_logo = By.xpath("//img[contains(@src,'twitter')]/parent::a");
    private By header_incorta_linkedin_logo = By.xpath("//img[contains(@src,'linkedin')]/parent::a");
    private By body_incorta_signOut_logo = By.xpath("//span[contains(@class,'container')]");

    private By body_signedOutMessageHeader_label = By
	    .xpath("//div[@class='inc-login__forget-password-conf-container-reset-conf-text']/span");
    private By body_signedOutMessageBody_label = By
	    .xpath("//div[@class='inc-login__forget-password-conf-container-reset-conf-text-body']/span");
    private By body_goToSignInPage = By.xpath("//span[contains(.,'Go to the Sign-in Page')]");
    private By body_goToSignInPage_link = By.xpath("//a[@href='/incorta#/login']");

    //// Functions
    public SignOut(WebDriver driver) {
	this.driver = driver;
    }

    public void assert_signOutMessageHeaderAndBodyAreCorrect() {
	Assertions.assertElementAttribute(driver, body_signedOutMessageHeader_label, "text", signedOutMessageHeader,
		true);
	Assertions.assertElementAttribute(driver, body_signedOutMessageBody_label, "text", signedOutMessageBody, true);
    }

    public void goToSignInPage_button() {
	ElementActions.click(driver, body_goToSignInPage);
    }

    public void navigate_toLoginPage() {
	ElementActions.click(driver, body_goToSignInPage_link);
    }

    public void verifyThat_All_logosAreDisplayed() {

	Verifications.verifyElementExists(driver, header_incortaLogo_image, true);
	Verifications.verifyElementExists(driver, header_incorta_website_logo, true);
	Verifications.verifyElementExists(driver, header_incorta_twitter_logo, true);
	Verifications.verifyElementExists(driver, header_incorta_linkedin_logo, true);
	Verifications.verifyElementExists(driver, body_incorta_signOut_logo, true);
    }

}