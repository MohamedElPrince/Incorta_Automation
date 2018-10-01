package tests.gui.scheduler;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.browser.BrowserFactory;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.login.NewUI_Login;
import pageObjectModels.main.Skeleton;
import pageObjectModels.scheduler.SchemaLoads;

@Epic("ScheduledSchemaLoadTest")

public class ScheduledSchemaLoadTest {

	// Declaring web-driver and excel reader instances
	WebDriver driver;
	ExcelFileManager testDataReader;

	// Declaring Page Objects that will be used throughout the test
	Skeleton mainPage;
	SchemaLoads scheduledSchema;
	NewUI_Login loginPage;
	// Declaring public variables that will be shared between tests

	// String SchemaName;

	// Admin user // Per-created schema // Per-created schema schedule job with
	// diff. name. //here i open existing schema job created and edit its recurrence
	// option, then save the change and open the job back to see if the change
	// reflected and saved or not

	@Test(priority = 1, description = "C77127 - Firefox: Fresh Installation: Testing that the user can edit a Created Schema Job")
	@Description("When I navigate to the schema load list page, And I select a  pre-created schema and edit it, Then  scheduled job can be edit and saved")
	@Severity(SeverityLevel.NORMAL)
	public void ScheduledSchemaCanBeEdited() {

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		// schema_Schedule_View_Status.Assert_SchemaJob_Created(testDataReader.getCellData("SchemaJobName_C77127"),
		// testDataReader.getCellData("SchemaName")); to be deleted"Check schemaJob name
		// available or not"

		scheduledSchema.select_schemaName(testDataReader.getCellData("SchemaJobName_C77127"),
				testDataReader.getCellData("SchemaName"));

		scheduledSchema.Select_schedulerSchema_radioButton("Weekly");
		scheduledSchema.select_scheduleSchema_weeklyDay_CheckBox("Tue");

		scheduledSchema.Click_scheduleSchema_saveChanges_button();

		scheduledSchema.Navigate_toURL();
		scheduledSchema.assert_schemaJob_created(testDataReader.getCellData("SchemaJobName_C77127"),
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(testDataReader.getCellData("SchemaJobName_C77127"),
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Weekly");
		scheduledSchema.Assert_scheduleSchema_weeklyDay_CheckBox("Tue");
	}

	// Admin user
	// Per-created schema
	// Per-created schema schedule job with diff. name "Schema Delete Action".

	@Test(priority = 2, description = "C77128 - Firefox: Fresh Installation: Testing that the user can delete a Created Schema Job")
	@Description("When I navigate to the schema load list page, And I select a  pre-created schema delete it, Then  scheduled job deleted successfully ")
	@Severity(SeverityLevel.NORMAL)
	public void ScheduledSchemaCanJobBeDeleted() {

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.select_schemaNameCheckBox(testDataReader.getCellData("SchemaJobName_C77128"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Delete selection");

		scheduledSchema.Click_scheduleSchema_deleteButton();

		scheduledSchema.Assert_schemaJob_deleted(testDataReader.getCellData("SchemaJobName_C77128"),
				testDataReader.getCellData("SchemaName"));

	}

	// Admin user
	// Per-created schema
	// Per-created schema schedule job with diff. name "Schema Change Status" and
	// it`s state is Active

	@Test(priority = 3, description = "C77129 - Firefox: Fresh Installation: Testing that the user can Suspend a Schema job")
	@Description("When I navigate to the schema load list page, And I changed the target schemajob status, Then  scheduled job status changed successfully ")
	@Severity(SeverityLevel.NORMAL)
	public void ScheduledSchemaStatusCanBeChange() {

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		// schema_Schedule_View_Status.Schedule_Schema_JobStatus_Check(testDataReader.getCellData("SchemaName"),testDataReader.getCellData("SchemaJobName_C77129"),
		// "Active");// to check the status o the created job"to be deleted"

		scheduledSchema.changeJobStatusFilter("All");// to See All SchemaJobs

		scheduledSchema.Change_scheduleSchema_JobStatus_(testDataReader.getCellData("SchemaName"),
				testDataReader.getCellData("SchemaJobName_C77129"), "Active");

		scheduledSchema.Assert_scheduleSchema_jobStatus(testDataReader.getCellData("SchemaName"),
				testDataReader.getCellData("SchemaJobName_C77129"), "Suspended");

	}

	@Test(priority = 4, description = "C77130 - Firefox: Fresh Installation: Check Schema Jobs menu")
	@Description("When I navigate to the schema load list page, And I cleck on schemajob status list, Then  All ststus list appears")
	@Severity(SeverityLevel.NORMAL)
	public void ALLScheduledSchema_statusFilters_verification() {

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.verify_jobStatusOptionIsDisplayed("All");
		scheduledSchema.verify_jobStatusOptionIsDisplayed("Active");
		scheduledSchema.verify_jobStatusOptionIsDisplayed("Suspended");
		scheduledSchema.verify_jobStatusOptionIsDisplayed("Completed");

	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "scheduleSchema/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
		driver = BrowserFactory.getBrowser(testDataReader);

		loginPage = new NewUI_Login(driver);
		loginPage.navigate_toURL();
		loginPage.userLogin(testDataReader.getCellData("Tenant"), testDataReader.getCellData("Username"),
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