package pageObjectModels.modules.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.customValidations.CustomAssertions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.io.ExcelReader;

public class Scheduler_Dashboards {
	//// Variables
	WebDriver driver;
	ExcelReader testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_scheduler_dashboards");

	//// Elements
	By header_dashboardsTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Dashboards']");
	By body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']");
	// By body_name_link;
	By body_dashboard_link;
	By body_Status_label;
	// By body_nextRun_label;

	//// Functions
	public Scheduler_Dashboards(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_allDashboardsTabIsSelected() {
		CustomAssertions.cAssertElementAttribute(driver, header_dashboardsTabHeader_link, "class", "selectedTab", true);
	}

	public void ChangeJobStatus(String status) {
		ElementActions.select(driver, body_jobStatus_list, status);
	}

	public void Assert_nameIsDisplayed(String name) {
		body_dashboard_link = By.xpath(
				"//div[contains(@class,'usersPanel')]//p[contains(@class,'job-status')][contains(normalize-space(.),'"
						+ name + "')]");
		CustomAssertions.cAssertElementExists(driver, body_dashboard_link, true);
	}

	public void Assert_jobStatusIsCorrect(String name, String expectedStatus) {
		body_Status_label = By.xpath(
				"//div[contains(@class,'usersPanel')]//div[contains(@class,'dataTypeName')][contains(normalize-space(.),'"
						+ name + "')]/following-sibling::div[contains(@class,'dataConnectionLink')]");
		CustomAssertions.cAssertElementAttribute(driver, body_Status_label, "Text", expectedStatus, true);
	}
}
