package pageObjectModels.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.validationsLibrary.Assertions;

public class NewUI_Content_Folder {
	//// Variables
	WebDriver driver;
	ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

	//// Elements
	By body_dashboardName;
	
	//// Functions
	public NewUI_Content_Folder(WebDriver driver) {
		this.driver = driver;
	}
	
	public void Assert_DashboardExist(String DashboardName) {
		body_dashboardName = By.xpath("//div[@class='inc-card-title']//span[text()= '" + DashboardName + "']");
		Assertions.assertElementExists(driver, body_dashboardName, true);
	}
	
	public void Assert_DashboardNotExist(String DashboardName) {
		body_dashboardName = By.xpath("//div[@class='inc-card-title']//span[text()= '" + DashboardName + "']");
		Assertions.assertElementExists(driver, body_dashboardName, false);
	}

}
