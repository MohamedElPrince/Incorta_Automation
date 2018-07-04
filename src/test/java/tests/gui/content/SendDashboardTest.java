package tests.gui.content;

import org.testng.annotations.Test;
import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.content.AllContent;
import pageObjectModels.content.AllContent_Dashboard;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

public class SendDashboardTest {
	
	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login loginPage;
	AllContent allContentPage;
	Skeleton mainPage;
	AllContent_Dashboard dashboardPage;

	// Declaring public variables that will be shared between tests

	@Test(priority = 1, description = "C76805 - Chrome: Fresh Installation : Testing that the new 'Send Dashboard' Screen is correctly displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Send Dashboard screen will open")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SendDashboard_Screen_IsOpened()
	{		
		dashboardPage.scheduleSendDashboard_assert_ScreenIsOpened();
	}
	
	@Test(priority = 2, description = "C76806 - Chrome: Fresh Installation: Testing that 'Subject' field is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that Subject field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_SubjectField_Displayed_SendDashboard() 
	{		
		dashboardPage.sendDashboard_assert_subjectField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Subject");
	}
	
	@Test(priority = 3, description = "C76807 - Chrome: Fresh Installation: Testing that 'Body' field is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that body field exist.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_BodyField_Displayed_SendDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_bodyField_exist();
		dashboardPage.sendDashboard_assert_labelsName_exist("Body");
	}
  	
	@Test(priority = 4, description = "C76808 - Chrome: Fresh Installation: Testing that 'Hide Notification Text' Check box is displayed in 'Send Dashboard' window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then I'll find that 'Hide Notification Text' field exist along with a tooltip and a checkbox is Unchecked.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_HideNotificationTextField_Displayed_SendDashBoard() 
	{		
		dashboardPage.sendDashboard_assert_HideNotificationText_checkbox_Unchecked();
		dashboardPage.sendDashboard_assert_labelsName_exist("Hide Notification Text");
		dashboardPage.sendDashboard_assert_HideNotificationText_toolTipIsDisplayed();
	}
	
	@Test(priority = 5, description = "C76809 - Chrome: Fresh Installation: Testing that when selecting HTML Option no File name can be added")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select HTML option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_HTML_Option_InSendDashBoard() 
	{
		dashboardPage.sendDashboard_selectOutputFormat("html");
		dashboardPage.sendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.sendDashboard_assert_FileNameFieldExist();
		dashboardPage.sendDashboard_assert_TypeOfEmailDescription("html");
		dashboardPage.sendDashboard_assert_AppendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
	}
	
	@Test(priority = 6, description = "C76810 - Chrome: Fresh Installation: Verify that when Selecting 'XLSX', the option of fixed file name is displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select XLSX option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_XLSX_Option_InSendDashBoard() 
	{		
		dashboardPage.sendDashboard_selectOutputFormat("xlsx");
		dashboardPage.sendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.sendDashboard_assert_FileNameFieldExist();
		dashboardPage.sendDashboard_assert_AppendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
		dashboardPage.sendDashboard_assert_TypeOfEmailDescription("xlsx");
	}

	@Test(priority = 7, description = "C76811 - Chrome: Fresh Installation: Verify that when selecting 'CSV', the option of fixed file name is displayed")
	@Description("When I navigate to the target dashboard, and I click on send dashboard and I select CSV option. Then I'll find that type option is selected and file name field is displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Selecting_CSV_Option_InSendDashBoard()
	{		
		dashboardPage.sendDashboard_selectOutputFormat("csv");
		dashboardPage.sendDashboard_assert_labelsName_exist("File Name");
		dashboardPage.sendDashboard_assert_FileNameFieldExist();
		dashboardPage.sendDashboard_assert_TypeOfEmailDescription("csv");
		dashboardPage.sendDashboard_assert_AppendTimestamp_checkbox_checked();
		dashboardPage.sendDashboard_assert_dashboardNameIsFileName(testDataReader.getCellData("DashboardName"));
	}
	
	@Test(priority = 8, description = "C76716 - Chrome: Fresh Installation: Verify that 'To' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'To' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_To_MailReceipents_Displayed()
	{				
		dashboardPage.sendDashboard_assert_labelsName_exist("To");
		dashboardPage.sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("To");
	}
	
	@Test(priority = 9, description = "C76717 - Chrome: Fresh Installation: Verify that 'Cc' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'Cc' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Cc_MailReceipents_Displayed()
	{		
		dashboardPage.sendDashboard_assert_labelsName_exist("Cc");
		dashboardPage.sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Cc");
	}
	
	@Test(priority = 10, description = "C76718 - Chrome: Fresh Installation: Verify that 'Bcc' section is displayed in 'Send Dashboard' Window")
	@Description("When I navigate to the target dashboard, and I click on send dashboard. Then Mail Receipents 'Bcc' and a plus sign beside it will be displayed.")
	@Severity(SeverityLevel.NORMAL)
	public void Assert_Bcc_MailReceipents_Displayed() 
	{		
		dashboardPage.sendDashboard_assert_labelsName_exist("Bcc");
		dashboardPage.sendDashboard_assert_MailRecipientsType_plusSignIsDisplayed("Bcc");
	}
	
	@BeforeMethod
	public void beforeMethod(){
		allContentPage = new AllContent(driver);
		allContentPage.Navigate_toURL();
		allContentPage.Assert_allContentTabIsSelected();

		mainPage = new Skeleton(driver);
		mainPage.SearchForContentAndOpenResult(testDataReader.getCellData("DashboardName"));

		dashboardPage = new AllContent_Dashboard(driver);
		dashboardPage.Assert_dashboardName(testDataReader.getCellData("DashboardName"));
		
		mainPage.Click_export();
		mainPage.Select_fromDropdownMenu("Send");
	}
	
	@BeforeClass
  	public void beforeClass() {
		System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "sendDashboard/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		loginPage = new Login(driver);
		loginPage.Navigate_toURL();
		loginPage.UserLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
				testDataReader.getCellData("Password"));
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
