package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;

public class NewUI_AllContent_Dashboard_AnalyzeInsight {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

	//// Elements
	By menuHeader_search_textBox = By
			.xpath("//tables-panel//input[@ng-model='$ctrl.searchKey'][not(ancestor::tables-selection-panel)]");
	By menuHeader_add_button = By.xpath("//a[contains(@ng-click,'$ctrl.openSelection()')]");
	By menuBody_element_label;
	By menuBody_addElement_button;

	By submenuHeader_viewOrTable_list = By.id("viewDropdown");
	By submenuHeader_viewOrTableListOption_link; // a[@role='menuitem'][normalize-space()='Views']
	By submenuHeader_search_textBox = By.xpath("//tables-selection-panel//input[@ng-model='$ctrl.searchKey']");
	By submenuBody_element_checkBox;
	By submenuBody_done_button = By.className("done-button");

	By body_insightName_textBox = By.xpath("//input[@ng-model='component.title']");
	By body_iframe = By.xpath("//iframe[@title='Legacy Web']");

	By popup_chooseAVisualization_visualizationType_text;

	//// Functions
	public NewUI_AllContent_Dashboard_AnalyzeInsight(WebDriver driver) {
		this.driver = driver;
	}

	public void addTableorSchemaToInsight(String name) {
		ElementActions.switchToIframe(driver, body_iframe);
		addTableorViewToInsight("Table", name);
		ElementActions.switchToDefaultContent(driver);
	}

	public void addVieworBusinessSchemaToInsight(String name) {
		ElementActions.switchToIframe(driver, body_iframe);
		addTableorViewToInsight("View", name);
		ElementActions.switchToDefaultContent(driver);
	}

	private void addTableorViewToInsight(String tableOrView, String name) {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.click(driver, menuHeader_add_button);
		ElementActions.click(driver, submenuHeader_viewOrTable_list);

		switch (tableOrView) {
		case "Table":
			submenuHeader_viewOrTableListOption_link = By.xpath("//a[@role='menuitem'][normalize-space()='Tables']");
			break;
		case "View":
			submenuHeader_viewOrTableListOption_link = By.xpath("//a[@role='menuitem'][normalize-space()='Views']");
			break;
		default:
			break;
		}
		ElementActions.click(driver, submenuHeader_viewOrTableListOption_link);

		ElementActions.type(driver, submenuHeader_search_textBox, name);
		submenuBody_element_checkBox = By.xpath(
				"//*[@id='selection-panel']//span[@title='" + name + "']/../../../..//label[@class='check-box']");
		ElementActions.click(driver, submenuBody_element_checkBox);
		ElementActions.click(driver, submenuBody_done_button);
	}

	public void addColumnToInsight(String tableName, String columnName) {
		ElementActions.switchToIframe(driver, body_iframe);
		ElementActions.type(driver, menuHeader_search_textBox, columnName);

		menuBody_element_label = By.xpath("//*[@id='tables-panel']//node[contains(@class,'table')]//span[@title='"
				+ tableName + "']/following::node[contains(@class,'column')]//span[@title='" + columnName + "']");
		ElementActions.hover(driver, menuBody_element_label);

		menuBody_addElement_button = By.xpath("//*[@id='tables-panel']//node[contains(@class,'table')]//span[@title='"
				+ tableName + "']/following::node[contains(@class,'column')]//span[@title='" + columnName
				+ "']/../../../..//i[contains(@class,'fa-plus-circle')]");
		ElementActions.click(driver, menuBody_addElement_button);
		ElementActions.switchToDefaultContent(driver);
	}

	public String setInsightName() {
		ElementActions.switchToIframe(driver, body_iframe);
		String newInsightName = "Automation" + "_Insight_" + String.valueOf(System.currentTimeMillis());
		ElementActions.click(driver, body_insightName_textBox);
		ElementActions.type(driver, body_insightName_textBox, newInsightName);
		ElementActions.switchToDefaultContent(driver);
		return newInsightName;
	}

	public void selectVisualization(String visualizationType) {
		ElementActions.switchToIframe(driver, body_iframe);
		popup_chooseAVisualization_visualizationType_text = By.xpath(
				"//div[@id='send-dashboard-modal']//div[@class='ng-binding'][text()='" + visualizationType + "']/../a");
		ElementActions.click(driver, popup_chooseAVisualization_visualizationType_text);
		ElementActions.switchToDefaultContent(driver);
	}
}
