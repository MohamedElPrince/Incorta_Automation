package tests.gui.schemas;

import org.testng.annotations.Test;

import com.shaftEngine.browserActionLibrary.BrowserFactory;
import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.login.Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.schemas.SchemaList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

@Epic("ScheduleSchema Test")

public class ScheduleSchemaTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Login loginPage;
	SchemaList schemasPage;
	Skeleton mainPage;

	// Declaring public variables that will be shared between tests
	// String SchemaName;

	@Test(priority = 1, description = "C77115 - Firefox: Fresh Installation: Testing that the new Scheduler Schema UI is correctly displayed.")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then scheduler view displayed correctly.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchedulerSchemaViewCheck() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Assert_schemaSchedule_popup_is_displayed();

	}

	@Test(priority = 2, description = "C77116 - Firefox: Fresh Installation: Testing that Job name Area field Functionality is appeared")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view, Job name displayed correctly.")
	@Severity(SeverityLevel.NORMAL)
	public void SchedulerSchemaViewJobNameCheck() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Assert_schemaSchedule_label_contents("Job Name");

	}

	
	
	@Test(priority = 3, description = "C77208 - Firefox: Fresh Installation: Testing that when enter an exists Job name for the same schema, error message appears")
	@Description("When I navigate to the schema list page,Given that there is a pre-created job created, And I create schedule job for the same schema with the same name, then ")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_Schema_Job_Exists_Error_Check() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		
		schemasPage.Schema_Schedule_Type_data("Job Name",testDataReader.getCellData("SchemaLoadJobName"));
		schemasPage.Schema_Schedule_Click_Schedule_Button();
		
		schemasPage.Assert_dublicate_Schema_Schedule_job_Name_Error_Message();
		
		
		
		
		

	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "scheduleSchema/TestData.xlsx");
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

	@BeforeMethod
	public void beforeMethod() {
	}

}
