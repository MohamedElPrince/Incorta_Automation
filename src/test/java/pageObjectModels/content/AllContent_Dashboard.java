package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;
import com.shaftEngine.validationsLibrary.Verifications;

public class AllContent_Dashboard {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

	//// Elements
	By header_dashboardName_textBox = By.xpath("//div[@id='dashboardHeader']//input");
	By body_insightName_label = By.xpath("//header/span");

	By body_aggregatedTable_dataCell_text;

	By popup_sendDashboard_type_radioButton; // label[normalize-space(.)='HTML']/input[@type='radio']
	By popup_sendDashboard_plusReciever_button = By
			.xpath("//*[contains(@ng-click,\"!error && goToState('addUsers', 'to')\")]");
	By popup_sendDashboard_emailAddress_textBox = By.xpath("//input[@ng-model='$parent.entitySearchText']");
	By popup_sendDashboard_add_button = By.xpath("//button[@type='button'][normalize-space()='Add']");
	By popup_sendDashboard_reciever_label; // div[contains(@class,'folderUserRow')][contains(normalize-space(.),'mohab.mohie@incorta.com')]//h5[contains(@class,'UserData')]
	By popup_sendDashboard_send_button = By.xpath("//button[@type='button'][normalize-space()='Send']");
	By popup_dashboard_menu_share_button = By.xpath("//a[contains(@class,'shareFolder')]");
	
	// Pagination Elements
	By body_insight_paginationFirst_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-left')]/following-sibling::i/parent::a");
	By body_insight_paginationPrevious_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-left')][not(following-sibling::i)][not(preceding-sibling::i)]/parent::a");
	By body_insight_paginationNumberOfRecords_text = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/span[contains(text(),'of')][contains(text(),'-')]");
	By body_insight_paginationNext_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-right')][not(following-sibling::i)][not(preceding-sibling::i)]/parent::a");
	By body_insight_paginationLast_button = By.xpath(
			"// div[contains(@class,'ht_master')]//div[@class='table-rows-limit-msg']/a/i[contains(@class,'angle-right')]/following-sibling::i/parent::a");

	//// Functions
	
	public AllContent_Dashboard(WebDriver driver) {
		this.driver = driver;
	}
	
	public void Assert_shared_button_dimmed() {

			Assertions.assertElementAttribute(driver, popup_dashboard_menu_share_button, "class",
					"shareFolder dimmedAction", true);
		}
	
	public void Assert_dashboardName(String name) {
		Assertions.assertElementAttribute(driver, header_dashboardName_textBox, "Text", "(.*" + name + ".*)", true);
	}

	public void Assert_insightName(String name) {
		Assertions.assertElementAttribute(driver, body_insightName_label, "Text", "(.*" + name + ".*)", true);
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
		popup_sendDashboard_reciever_label = By.xpath("//*[contains(@title,'" + email + "')]");
		Assertions.assertElementExists(driver, popup_sendDashboard_reciever_label, true);
	}

	public void scheduleEmailSending() {
		ElementActions.click(driver, popup_sendDashboard_send_button);
	}

	public void AssertData_AggregatedTableContent(String RowOrMeasure, int RowNumber, String ExpectedData) {
		switch (RowOrMeasure.trim().toLowerCase()) {
		case "row":
			body_aggregatedTable_dataCell_text = By
					.xpath("//tbody/tr[" + RowNumber + "]//a[contains(@onclick,', \"row\"')]");
			break;
		case "measure":
			body_aggregatedTable_dataCell_text = By
					.xpath("//tbody/tr[" + RowNumber + "]//a[contains(@onclick,', \"measure\"')]");
			break;
		default:
			break;
		}

		Assertions.assertElementAttribute(driver, body_aggregatedTable_dataCell_text, "text", ExpectedData, true);
	}

	public void Pagination_Verify_UiElementsExist() {
		Verifications.verifyElementExists(driver, body_insight_paginationFirst_button, true);
		Verifications.verifyElementExists(driver, body_insight_paginationPrevious_button, true);
		Verifications.verifyElementExists(driver, body_insight_paginationNumberOfRecords_text, true);
		Verifications.verifyElementExists(driver, body_insight_paginationNext_button, true);
		Verifications.verifyElementExists(driver, body_insight_paginationLast_button, true);
	}

	/**
	 * 
	 * @return the first record [*1* - 100 of 620]
	 */
	private int Pagination_GetFirstRecordInCurrentPage() {
		// sample [*1* - 100 of 620]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");
		String[] firstHalf = parts[0].split(" - ");

		return Integer.parseInt(firstHalf[0].trim());
	}

	/**
	 * 
	 * @return the last record [1 - *100* of 620]
	 */
	private int Pagination_GetLastRecordInCurrentPage() {
		// sample [1 - *100* of 620]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");
		String[] firstHalf = parts[0].split(" - ");

		return Integer.parseInt(firstHalf[1].trim());
	}

	/**
	 * 
	 * @return the total number of records [1 - 100 of *620*]
	 */
	private int Pagination_GetTotalNumberOfRecords() {
		// sample [1 - 100 of *620*]

		String record = ElementActions.getText(driver, body_insight_paginationNumberOfRecords_text);
		String[] parts = record.split(" of ");

		return Integer.parseInt(parts[1].trim());
	}

	public void Pagination_AssertThatNextButtonWorksAsExpected() {
		// Get last record in current page
		int lastRecordBeforeClickingNext = Pagination_GetLastRecordInCurrentPage();
		// Click the Next Button
		ElementActions.click(driver, body_insight_paginationNext_button);
		// Get first record in new current page
		int firstRecordAfterClickingNext = Pagination_GetFirstRecordInCurrentPage();

		Assertions.assertEquals(lastRecordBeforeClickingNext + 1, firstRecordAfterClickingNext, true);
	}
	
	public void Pagination_AssertThatLastButtonWorksAsExpected() {
		// Get total records from current page
		int TotalRecordFromCurruntPage = Pagination_GetTotalNumberOfRecords();
		// Click the Last Button
		ElementActions.click(driver, body_insight_paginationLast_button);
		// Get last record of the first record in new current page
		int SecondRecordAfterClickingLastPage = Pagination_GetLastRecordInCurrentPage();
		Assertions.assertEquals(TotalRecordFromCurruntPage, SecondRecordAfterClickingLastPage, true);
		
	}
	
	public void Pagination_AssertThatFirstButtontWorksAsExpected() {

		// Get First record in current page
		int initialRecord = Pagination_GetFirstRecordInCurrentPage();
		// Check that if you are in the first page or not, if yes move to the last page by clicking on last button then click on first button.
		if (initialRecord == 1) {
			// I'm on the first page already
			// Click last Button
			ElementActions.click(driver, body_insight_paginationLast_button); // To be removed-->click on last button
			// Get First record in the current new page
			int firstRecordAfterClickingLastButton = Pagination_GetFirstRecordInCurrentPage();
			// Check that after click next button page changed
			Assertions.assertEquals(firstRecordAfterClickingLastButton, 1, false);
			// to confirm that I am no longer in the first page
		}
		// Click First Button
		ElementActions.click(driver, body_insight_paginationFirst_button);
		// Get First record in new current page after click first button
		int firstRecordAfterClickingFirstButton = Pagination_GetFirstRecordInCurrentPage();
		// Check that First Button works and navigated to first page
		Assertions.assertEquals(firstRecordAfterClickingFirstButton, 1, true);
	}	
}
