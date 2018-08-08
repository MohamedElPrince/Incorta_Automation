package pageObjectModels.main;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.validationsLibrary.Assertions;
import com.shaftEngine.validationsLibrary.Verifications;

public class NewUI_Header {
	//// Variables
	WebDriver driver;

	//// Elements
	// first menu
	By navigationWrapper_incortaLogo_image = By
			.xpath("//div[@class='nav-wrapper']//img[contains(@src,'incorta-white-logo')]");
	By navigationWrapper_sectionHeader_link; // li[@class='inc-header-item']/a[contains(@href,'incorta/new')][normalize-space(.)='Content']
	By navigationWrapper_userMenu_image = By.xpath("//div[@class='nav-wrapper']//div[@class='header-menu']//img");
	By navigationWrapper_userMenu_userName_label = By.className("header--user-details-name");
	By navigationWrapper_userMenu_userEmail_label = By.className("header--user-details-email");
	By navigationWrapper_userMenu_about_button = By
			.xpath("//div[@class='nav-wrapper']//span[text()='About']//ancestor::button");
	By navigationWrapper_userMenu_signOut_button = By
			.xpath("//div[@class='nav-wrapper']//span[text()='Sign Out']//ancestor::button");

	//// Functions
	public NewUI_Header(WebDriver driver) {
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
					.xpath("// li[@class='inc-header-item']/a[contains(@href,'incorta/new')][normalize-space(.)='"
							+ sectionName + "']");
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
				.xpath("// li[@class='inc-header-item']/a[contains(@href,'incorta/new')][normalize-space(.)='"
						+ sectionName + "']");
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
				.xpath("// li[@class='inc-header-item']/a[contains(@href,'incorta/new')][normalize-space(.)='"
						+ sectionName + "']");
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

	// public void assert_aboutPopupContent_isCorrect() {}

	/**
	 * Given that the userMenu is expanded, clicks the signOut button
	 */
	public void signOut() {
		ElementActions.click(driver, navigationWrapper_userMenu_signOut_button);
	}

}
