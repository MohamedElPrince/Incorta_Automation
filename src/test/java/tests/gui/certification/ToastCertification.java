package tests.gui.certification;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.io.ExcelReader;
import com.shaftEngine.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.modules.content.Content_AllContent;
import pageObjectModels.modules.login.Login_Login;

@Epic("incorta Certification Path.")
public class ToastCertification {
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelReader testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login_Login loginPage;
	Content_AllContent allContentPage;

	@Test(priority = 1, description = "TC001 - Login using Admin Account.")
	@Description("When I navigate to the login page, And I login using valid credentials Then all content tab is selected.")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUsingAdmin() {
		loginPage = new Login_Login(driver);
		loginPage.Navigate_toURL();
		loginPage.Login(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
		allContentPage = new Content_AllContent(driver);
		allContentPage.Assert_allContentTabIsSelected();
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "toastCertification/TestData.xlsx");
		testDataReader = new ExcelReader(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);
	}

	@AfterMethod
	public void afterMethod() {
		ReportManager.getTestLog();
	}

	@AfterClass
	public void afterClass() {
		BrowserFactory.closeAllDrivers();
		ReportManager.getFullLog();
	}

}
