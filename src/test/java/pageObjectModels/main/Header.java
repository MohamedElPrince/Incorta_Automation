package pageObjectModels.main;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.element.ElementActions;
import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

public class Header {
    //// Variables
    private WebDriver driver;

    //// Elements
    // first menu
    private By navigationWrapper_incortaLogo_image = By
	    .xpath("//div[@class='nav-wrapper']//img[contains(@src,'incorta-white-logo')]");
    private By navigationWrapper_sectionHeader_link; // li[@class='inc-header-item']/a[contains(@href,'incorta/new')][normalize-space(.)='Content']
    private By navigationWrapper_userMenu_image = By
	    .xpath("//div[@class='nav-wrapper']//div[@class='header-menu']//img");
    private By navigationWrapper_userMenu_userName_label = By.className("header--user-details-name");
    private By navigationWrapper_userMenu_userEmail_label = By.className("header--user-details-email");
    private By navigationWrapper_userMenu_generic_button; // div[@class='nav-wrapper']//span[text()='Switch
    // Back']//ancestor::button

    // Splash notification
    private By splash_notificationMessage_text = By.xpath("//div[contains(@class,'ant-notification-notice-message')]");
    private By splash_notificationDescription_text = By
	    .xpath("//div[contains(@class,'ant-notification-notice-description')]");

    //// Functions
    public Header(WebDriver driver) {
	this.driver = driver;
    }

    public void assert_incortaLogo_isDisplayed() {
	Assertions.assertElementExists(driver, navigationWrapper_incortaLogo_image, true);
    }

    public void verify_allSectionHeaders_areDisplayed() {

	List<String> sectionNames = Arrays.asList("Content", "Scheduler", "Business Schema", "Schema", "Data",
		"Security");

	sectionNames.forEach((sectionName) -> {
	    navigationWrapper_sectionHeader_link = By
		    .xpath("//li[@class='inc-header-item']/a[normalize-space(.)='" + sectionName + "']");
	    Verifications.verifyElementExists(driver, navigationWrapper_sectionHeader_link, true);
	});
    }

    /**
     * Asserts that the mentioned sectionName contains the value "selected" in its
     * "class" attribute
     * 
     * @param sectionName
     *            "Content", "Scheduler", "Business Schema", "Schema", "Data",
     *            "Security"
     */
    public void assert_sectionHeader_isSelected(String sectionName) {
	navigationWrapper_sectionHeader_link = By
		.xpath("//li[@class='inc-header-item']/a[normalize-space(.)='" + sectionName + "']");
	Assertions.assertElementAttribute(driver, navigationWrapper_sectionHeader_link, "class", ".*selected.*", true);
    }

    /**
     * Clicks on the desired sectionName header link
     * 
     * @param sectionName
     *            "Content", "Scheduler", "Business Schema", "Schema", "Data",
     *            "Security"
     */
    public void navigate_toSection(String sectionName) {
	navigationWrapper_sectionHeader_link = By
		.xpath("//li[@class='inc-header-item']/a[normalize-space(.)='" + sectionName + "']");
	ElementActions.click(driver, navigationWrapper_sectionHeader_link);
    }

    public void expandUserMenu() {
	ElementActions.click(driver, navigationWrapper_userMenu_image);
    }

    /**
     * Given that the userMenu is expanded, asserts that the correct userName is
     * displayed
     * 
     * @param userName
     */
    public void assert_userName(String userName) {
	Assertions.assertElementAttribute(driver, navigationWrapper_userMenu_userName_label, "text", userName, true);
    }

    /**
     * Given that the userMenu is expanded, asserts that the correct userEmail is
     * displayed
     * 
     * @param userEmail
     */
    public void assert_userEmail(String userEmail) {
	Assertions.assertElementAttribute(driver, navigationWrapper_userMenu_userEmail_label, "text", userEmail, true);

    }

    /**
     * Given that the userMenu is expanded, asserts that the correct userName and
     * userEmail are displayed
     * 
     * @param userName
     * @param userEmail
     */
    public void assert_userData(String userName, String userEmail) {
	assert_userName(userName);
	assert_userEmail(userEmail);
    }

    /**
     * Given that the userMenu is expanded, asserts that the desired menuItem exists
     * 
     * @param menuItem
     *            anything other than userName and userEmail, for which you can use
     *            the assert_userData method
     */
    public void assert_userMenuItem(String menuItem) {
	navigationWrapper_userMenu_generic_button = By
		.xpath("//div[@class='nav-wrapper']//span[text()='" + menuItem + "']//ancestor::button");
	Assertions.assertElementExists(driver, navigationWrapper_userMenu_generic_button, true);

    }

    // public void assert_aboutPopupContent_isCorrect() {}

    /**
     * Given that the userMenu is expanded, clicks the signOut button
     */
    public void signOut() {
	navigationWrapper_userMenu_generic_button = By
		.xpath("//div[@class='nav-wrapper']//span[text()='Sign Out']//ancestor::button");
	ElementActions.click(driver, navigationWrapper_userMenu_generic_button);
    }

    /**
     * 
     * @param sectionName
     *            --> Could have one of the following options: "Content" "Scheduler"
     *            "Business Schema" "Schema" "Data" "Security"
     */
    public void assert_sectionHeader_isDisplayed(String sectionName) {
	navigationWrapper_sectionHeader_link = By
		.xpath("//li[@class='inc-header-item']/a[normalize-space(.)='" + sectionName + "']");
	Assertions.assertElementExists(driver, navigationWrapper_sectionHeader_link, true);
    }

    /**
     * Asserts that the splash notification message contains the provided
     * expectedMessage
     * 
     * @param expectedMessage
     *            a subset of the message that is expected to show up in the splash
     *            notification
     */
    public void assert_splashNotificationMessage_equalsExpected(String expectedMessage) {
	Assertions.assertElementAttribute(driver, splash_notificationMessage_text, "text", expectedMessage, 3, true);
    }

    /**
     * Asserts that the splash notification description contains the provided
     * expectedDescription
     * 
     * @param expectedDescription
     *            a subset of the description that is expected to show up in the
     *            splash notification
     */
    public void assert_splashNotificationDescription_equalsExpected(String expectedDescription) {
	Assertions.assertElementAttribute(driver, splash_notificationDescription_text, "text", expectedDescription, 3,
		true);
    }

}
