package pageObjectModels.data;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.browserActionLibrary.BrowserActions;
import com.shaftEngine.elementActionLibrary.ElementActions;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class DataSources {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	String url = System.getProperty("incortaRoot") + testDataReader.getCellData("URL_data_dataSources");

	//// Elements
	By header_dataSourcesTabHeader_link = By
			.xpath("//*[@id='content']//div[contains(@class,'secHeaderTitle')]//a[normalize-space(.)='Data Sources']");
	// header_dataSources_label;
	// body_tableHeader_label;
	By body_name_link;
	// body_database_label;
	// body_permission_label;
	// body_testConnection_link;
	// body_owner_label;

	By popup_addNewDatasource_dataSource_list = By
			.xpath("//select[@ng-model='$parent.dataSource.properties.database']");
	By popup_addNewDatasource_dataSourceName_textBox = By.xpath("//input[@ng-model='$parent.dataSource.name']");
	By popup_addNewDatasource_username_textBox = By
			.xpath("//input[@ng-model='$parent.dataSource.properties[property.name]'][@name='user']");
	By popup_addNewDatasource_password_textBox = By
			.xpath("//input[@ng-model='$parent.dataSource.properties[property.name]'][@name='password']");
	By popup_addNewDatasource_connectionPool_textBox = By
			.xpath("//input[@placeholder='Connection Pool'][@inputmode='numeric']");
	By popup_addNewDatasource_connectionString_textBox = By
			.xpath("//textarea[@ng-model='$parent.dataSource.properties[property.name]'][@name='connection']");
	By popup_addNewDatasource_addDataSource_button = By
			.xpath("//button[@ng-click='updateDataSource(dataSource, dataSourceForm)']");
	By popup_addNewDatasource_cancel_button,
			popup_newDataSource_done_button = By.xpath("//button[@ng-click='modal.closeMe()']");

	By popup_newDataSource_headerName_label = By.xpath("//span[@ng-if='!newDataSource']");
	
	By body_ownername_link;

	//// Functions
	public DataSources(WebDriver driver) {
		this.driver = driver;
	}

	public void Navigate_toURL() {
		BrowserActions.navigateToURL(driver, url);
	}

	public void Assert_dataSourcesTabIsSelected() {
		Assertions.assertElementAttribute(driver, header_dataSourcesTabHeader_link, "class", "selectedTab", true);
	}

	public void Assert_nameIsDisplayed(String name) {
		body_name_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		Assertions.assertElementExists(driver, body_name_link, true);
	}
	// Assert_databaseIsDisplayed
	// Assert_permissionIsDisplayed
	// Assert_testConnectionIsDisplayed
	// Assert_ownerIsDisplayed

	// Assert_connectionIsWorking

	// Click_name

	public String AddDataSource(String dataSourceType) {
		String dataSourceName;
		String username;
		String password;
		String connectionPool;
		String connectionString;

		String columnName = testDataReader.getColumnNameUsingRowNameAndCellData("DataSourceType", dataSourceType);

		dataSourceName = "Automation" + "_" + dataSourceType.replaceAll(" ", "_").toLowerCase() + "_"
				+ String.valueOf(System.currentTimeMillis());
		username = testDataReader.getCellData("DataSourceUsername", columnName);
		password = testDataReader.getCellData("DataSourcePassword", columnName);
		connectionPool = testDataReader.getCellData("DataSourceConnectionPool", columnName);
		connectionString = testDataReader.getCellData("DataSourceConnectionString", columnName);

		// perform actions
		ElementActions.select(driver, popup_addNewDatasource_dataSource_list, dataSourceType);
		ElementActions.type(driver, popup_addNewDatasource_dataSourceName_textBox, dataSourceName);
		ElementActions.type(driver, popup_addNewDatasource_username_textBox, username);
		ElementActions.type(driver, popup_addNewDatasource_password_textBox, password);
		ElementActions.type(driver, popup_addNewDatasource_connectionPool_textBox, connectionPool);
		ElementActions.type(driver, popup_addNewDatasource_connectionString_textBox, connectionString);
		ElementActions.click(driver, popup_addNewDatasource_addDataSource_button);
		return dataSourceName;

	}

	public void Assert_dataSourceCreationWasSuccessful(String dataSourceName) {
		Assertions.assertElementAttribute(driver, popup_newDataSource_headerName_label, "text", dataSourceName, true);
		ElementActions.click(driver, popup_newDataSource_done_button);
	}
	
	public void Assert_DSnameAndOwnerIsDisplayed(String name, String ownername) {
		body_name_link = By
				.xpath("//div[contains(@class,'usersPanel')]//div[contains(@class,'userName') and contains(.,'" + name
						+ "')]/p");
		Assertions.assertElementExists(driver, body_name_link, true);
		
		body_ownername_link = By
				.xpath("//div[contains(@class,'userName ') and contains(.,'" + name + "')]/following-sibling::div/p[@class=\"ng-binding\"][contains(.,'"+ ownername +"')]");
		Assertions.assertElementExists(driver, body_ownername_link, true);
		
	}

}
