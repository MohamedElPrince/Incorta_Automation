package test.gui.scheduler;

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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

@Epic("ScheduledSchemaLoadTest")

public class ScheduledSchemaLoadTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Skeleton mainPage;
	SchemaLoads schema_Schedule_View_Status;
	Login loginPage;
	// Declaring public variables that will be shared between tests

	// String SchemaName;

	// Admin user // Per-created schema // Per-created schema schedule job with
	// diff. name. //here i open existing schema job created and edit its recurrence
	// option, then save the change and open the job back to see if the change
	// reflected and saved or not

	@Test(priority = 1, description = "C77127 - Firefox: Fresh Installation: Testing that the user can edit a Created Schema Job")
	@Description("When I navigate to the schema load list page, And I select a  pre-created schema and edit it, Then  scheduled job can be edit and saved")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_Can_be_Edited() {

		schema_Schedule_View_Status = new SchemaLoads(driver);
		schema_Schedule_View_Status.Navigate_toURL();

		// schema_Schedule_View_Status.Assert_SchemaJob_Created(testDataReader.getCellData("SchemaJobName_C77127"),
		// testDataReader.getCellData("SchemaName")); to be deleted"Check schemaJob name
		// available or not"

		schema_Schedule_View_Status.select_schemaName(testDataReader.getCellData("SchemaJobName_C77127"),
				testDataReader.getCellData("SchemaName"));

		schema_Schedule_View_Status.Select_schema_scheduler_radio_button("Weekly");
		schema_Schedule_View_Status.Select_SchemaSchedule_Weekly_day_CheckBox("Tue");

		schema_Schedule_View_Status.Schema_Schedule_Click_Save_Changes_button_Button();

		schema_Schedule_View_Status.Navigate_toURL();
		schema_Schedule_View_Status.Assert_SchemaJob_Created(testDataReader.getCellData("SchemaJobName_C77127"),
				testDataReader.getCellData("SchemaName"));
		schema_Schedule_View_Status.select_schemaName(testDataReader.getCellData("SchemaJobName_C77127"),
				testDataReader.getCellData("SchemaName"));
		schema_Schedule_View_Status.Assert_SchemaScheduler_Radio_Button_selected("Weekly");
		schema_Schedule_View_Status.Assert_SchemaSchedule_Weekly_day_CheckBox("Tue");
	}

	// Admin user
	// Per-created schema
	// Per-created schema schedule job with diff. name "Schema Delete Action".

	@Test(priority = 2, description = "C77128 - Firefox: Fresh Installation: Testing that the user can delete a Created Schema Job")
	@Description("When I navigate to the schema load list page, And I select a  pre-created schema delete it, Then  scheduled job deleted successfully ")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_Can_be_Deleted() {

		schema_Schedule_View_Status = new SchemaLoads(driver);
		schema_Schedule_View_Status.Navigate_toURL();

		schema_Schedule_View_Status.select_schemaNameCheckBox(testDataReader.getCellData("SchemaJobName_C77128"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");

		schema_Schedule_View_Status.Select_Schema_Schedule_Click_Delete_Button();

		schema_Schedule_View_Status.Assert_SchemaJob_Deleted(testDataReader.getCellData("SchemaJobName_C77128"),
				testDataReader.getCellData("SchemaName"));

	}

	// Admin user
	// Per-created schema
	// Per-created schema schedule job with diff. name "Schema Change Status" and
	// it`s state is Active

	@Test(priority = 3, description = "C77129 - Firefox: Fresh Installation: Testing that the user can Suspend a Schema job")
	@Description("When I navigate to the schema load list page, And I changed the target schemajob status, Then  scheduled job status changed successfully ")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_Can_Change_status_state() {

		schema_Schedule_View_Status = new SchemaLoads(driver);
		schema_Schedule_View_Status.Navigate_toURL();

		// schema_Schedule_View_Status.Schedule_Schema_JobStatus_Check(testDataReader.getCellData("SchemaName"),testDataReader.getCellData("SchemaJobName_C77129"),
		// "Active");// to check the status o the created job"to be deleted"

		schema_Schedule_View_Status.ChangeJobStatus_Filter("All");// to See All SchemaJobs

		schema_Schedule_View_Status.Schedule_Schema_JobStatus_Change(testDataReader.getCellData("SchemaName"),
				testDataReader.getCellData("SchemaJobName_C77129"), "Active");

		schema_Schedule_View_Status.Schedule_Schema_JobStatus_Check(testDataReader.getCellData("SchemaName"),
				testDataReader.getCellData("SchemaJobName_C77129"), "Suspended");

	}

	@Test(priority = 4, description = "C77130 - Firefox: Fresh Installation: Check Schema Jobs menu")
	@Description("When I navigate to the schema load list page, And I cleck on schemajob status list, Then  All ststus list appears")
	@Severity(SeverityLevel.NORMAL)
	public void ALLScheduler_Schema_Status_Filters_verification() {

		schema_Schedule_View_Status = new SchemaLoads(driver);
		schema_Schedule_View_Status.Navigate_toURL();

		schema_Schedule_View_Status.Verify_jobStatusOptionIsDisplayed("All");
		schema_Schedule_View_Status.Verify_jobStatusOptionIsDisplayed("Active");
		schema_Schedule_View_Status.Verify_jobStatusOptionIsDisplayed("Suspended");
		schema_Schedule_View_Status.Verify_jobStatusOptionIsDisplayed("Completed");

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