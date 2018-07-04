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

	By body_JobName;
	By popup_JobScreen_RemoveEmail_Button;
	By popup_JobScreen_SaveChanges_Button= By.xpath("//button[contains(text(),'Save Changes')]");
	By popup_JobScreen_Email;
	By popup_JobScreen_emailAddress_textBox = By.xpath("//input[@ng-model='$ctrl.entitySearchText']");
	By popup_JobScreen_EmailAddress_add_button = By.xpath("//button[@type='button'][normalize-space()='Add']");
	By popup_sendDashboard_EmailPlusButton;
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

	public void DashboardJob_ClickOnJob(String JobName, String DashboardName)
	{
		body_JobName = By.xpath("//p[contains(text(),'"+DashboardName+"')]/parent::a/parent::div/preceding-sibling::div/p[@title='"+JobName+"']");
		ElementActions.click(driver, body_JobName);
	}
	
	public void JobScreen_RemoveEmail_Button(String MailRecipientsType, String Email)
	{
		popup_JobScreen_RemoveEmail_Button = By.xpath("//label[contains(text(),'"+MailRecipientsType+"')]/parent::div/following-sibling::div"
				+ "//span[@title ='"+Email+"']/parent::div/following-sibling::div//a[contains(@ng-click,'removeUser')]");
		ElementActions.click(driver, popup_JobScreen_RemoveEmail_Button);
	}
	
	public void JobScreen_SaveChanges_Button()
	{
		ElementActions.click(driver, popup_JobScreen_SaveChanges_Button);
	}
	
	public void JobScreen_Assert_EmailIsNotExist(String MailRecipientsType, String Email)
	{
		popup_JobScreen_Email = By.xpath("//label[contains(text(),'"+MailRecipientsType+"')]/"
				+ "parent::div/following-sibling::div//span[@title ='"+Email+"']");
		Assertions.assertElementExists(driver, popup_JobScreen_Email, false);
	}
	
	public void JobScreen_Assert_EmailExist(String MailRecipientsType, String Email)
	{
		popup_JobScreen_Email = By.xpath("//label[contains(text(),'"+MailRecipientsType+"')]/"
				+ "parent::div/following-sibling::div//span[@title ='"+Email+"']");
		Assertions.assertElementExists(driver, popup_JobScreen_Email, true);
	}
	
	public void JobScreen_TypeEmailAndClickAdd(String Email)
	{
		ElementActions.type(driver, popup_JobScreen_emailAddress_textBox, Email);
		ElementActions.click(driver, popup_JobScreen_EmailAddress_add_button);
	}
	
	 /** @param MailRecipientsType
	 *            To Cc Bcc
	 */
	public void ScheduleDashboard_Click_AddMailRecipientsType(String MailRecipientsType) {
		popup_sendDashboard_EmailPlusButton = By.xpath("//label[contains(text(),'" + MailRecipientsType
				+ "')]/parent::div//following-sibling::div[@class='items-list-title']//i[@class = 'fa fa-plus']");
		ElementActions.click(driver, popup_sendDashboard_EmailPlusButton);
	}
	
}
