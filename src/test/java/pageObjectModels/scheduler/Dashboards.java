package pageObjectModels.scheduler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class Dashboards {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_scheduler_dashboards");

	//// Elements
	By header_dashboardsTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Dashboards']");
	By body_jobStatus_list = By.xpath("//select[@ng-change='jobStatusChanged()']");
	// By body_name_link;
	By body_dashboard_link;
	By body_Status_label;
	// By body_nextRun_label;
	By body_name_link;

	//// Functions
	public Dashboards(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_allDashboardsTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_dashboardsTabHeader_link, "class", "selectedTab", true);
	}

	public void ChangeJobStatus(String status) {
		ElementActions.select(driver, body_jobStatus_list, status);
	}

	public void Assert_nameIsDisplayed(String dashboard) {
		body_dashboard_link = By.xpath(
				"//div[contains(@class,'usersPanel')]//p[contains(@class,'job-status')][contains(normalize-space(.),'"
						+ dashboard + "')]");
		Assertions.assertElementExists(driver, body_dashboard_link, true);
	}
	
	public void Assert_jobNameIsDisplayed(String name) {
		body_name_link = By.xpath("//div[contains(@class,'usersPanel')]//p[@title='" + name + "']");
		Assertions.assertElementExists(driver, body_name_link, true);
	}

	public void Assert_jobStatusIsCorrect(String name, String expectedStatus) {
		body_Status_label = By.xpath(
				"//div[contains(@class,'usersPanel')]//div[contains(@class,'userName')][contains(normalize-space(.),'"
						+ name + "')]/following-sibling::div[contains(@class,'dataConnectionLink')]");
		Assertions.assertElementAttribute(driver, body_Status_label, "Text", expectedStatus, true);
	}
}
