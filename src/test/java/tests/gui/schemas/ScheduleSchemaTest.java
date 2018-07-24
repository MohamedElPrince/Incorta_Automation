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
import pageObjectModels.scheduler.SchemaLoads;
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
	SchemaLoads schema_Status;
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
	public void Scheduler_SchemaView_JobName() {

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
	@Description("When I navigate to the schema list page,Given that there is a pre-created job created, And I create schedule job for the same schema with the same name, then error message appeare")
	@Severity(SeverityLevel.NORMAL)
	public void SchemaScheduler_Job_Exists_Error_Check() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Type_Schema_Schedule_jobNameOrDescription("Job Name", testDataReader.getCellData("SchemaLoadJobName"));
		schemasPage.Schema_Schedule_Click_Schedule_Button();

		schemasPage.Assert_dublicate_Schema_Schedule_job_Name_Error_Message();

	}

	// Admin User logged in , SALES Sample schema
	@Test(priority = 4, description = "C77216 - Firefox Fresh Installation: Testing that when enter an exists Job name for other schema, no error message appears")
	@Description("When I navigate to the schema list page,Given that there is a pre-created job exist with the same name, And I create schedule job for diffrent schema with the same job name, then job created sucessfully ")
	@Severity(SeverityLevel.NORMAL)
	public void SchemaScheduler_Exists_Same_JobName() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName", "Data2"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Type_Schema_Schedule_jobNameOrDescription("Job Name", testDataReader.getCellData("SchemaLoadJobName"));
		schemasPage.Schema_Schedule_Click_Schedule_Button();

		schema_Status = new SchemaLoads(driver);
		schema_Status.Navigate_toURL();
		schema_Status.Assert_SchemaJob_Created(testDataReader.getCellData("SchemaLoadJobName"),
				testDataReader.getCellData("SchemaName", "Data2"));

	}

	@Test(priority = 5, description = "C77117 - Firefox: Fresh Installation: Testing that user can't save changes when he leaves Job name blank")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler job can`t be saved without job name")
	@Severity(SeverityLevel.NORMAL)
	public void SchedulerSchema_check_schedule_button_with_jobName_empty() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Assert_schemaSchedule_lable_textbox_empty("Job Name");
	
		schemasPage.Type_Schema_Schedule_jobNameOrDescription("Description", "Test schdule button");
		
		schemasPage.Assert_Schedule_button_disabled();
		
		
	}

	@Test(priority = 6, description = "C77118 - Firefox: Fresh Installation Testing that Description field functionality is appeared")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view, Description displayed correctly.")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_Description() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Assert_schemaSchedule_label_contents("Description");

	}

	
	@Test(priority = 7, description = "C77119 - Firefox: Fresh Installation: Testing that the Scheduler is working with Daily recurrence")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Daily recurrence")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_Daily_recurrence() {

		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		schemasPage.Assert_SchemaScheduler_Radio_Button_selected("Daily");
		
		
		String NewSchduleJobName = schemasPage.Auto_Type_Schedule_Schema_JobName();
		schemasPage.Schema_Schedule_Click_Schedule_Button();
		
		
		
		schema_Status = new SchemaLoads(driver);
		schema_Status.Navigate_toURL();
		schema_Status.Assert_SchemaJob_Created(NewSchduleJobName,testDataReader.getCellData("SchemaName"));
		schema_Status.select_schemaName(NewSchduleJobName);
		schema_Status.Assert_SchemaScheduler_Radio_Button_selected("Daily");
		
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
