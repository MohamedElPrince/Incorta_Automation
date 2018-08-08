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
	SchemaLoads scheduledSchema;
	Skeleton mainPage;

	// Declaring public variables that will be shared between tests
	// String SchemaName;

	@Test(priority = 1, description = "C77115 - Firefox: Fresh Installation: Testing that the new Scheduler Schema UI is correctly displayed.")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then scheduler view displayed correctly.")
	@Severity(SeverityLevel.CRITICAL)
	public void SchedulerSchemaViewIsDisplayed() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Assert_schemaSchedule_popup_is_displayed();

	}

	@Test(priority = 2, description = "C77116 - Firefox: Fresh Installation: Testing that Job name Area field Functionality is appeared")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view, Job name displayed correctly.")
	@Severity(SeverityLevel.NORMAL)
	public void scheduleSchemaLoad_jobNameIsDisplayed() {


		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.assert_schemaSchedule_labelName("Job Name");

	}

	@Test(priority = 3, description = "C77208 - Firefox: Fresh Installation: Testing that when enter an exists Job name for the same schema, error message appears")
	@Description("When I navigate to the schema list page,Given that there is a pre-created job created, And I create schedule job for the same schema with the same name, then error message appeare")
	@Severity(SeverityLevel.NORMAL)
	public void schemaScheduler_jobExistsErrorIsDisplayed() {


		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Type_scheduleSchema_jobNameOrDescription("Job Name",
				testDataReader.getCellData("SchemaLoadJobName_C77208"));
		schemasPage.click_scheduleSchema_scheduleButton();

		schemasPage.Assert_scheduleSchema_jobNameDublicate_errorMessage();

	}

	// Admin User logged in , SALES Sample schema
	@Test(priority = 4, description = "C77216 - Firefox Fresh Installation: Testing that when enter an exists Job name for other schema, no error message appears")
	@Description("When I navigate to the schema list page,Given that there is a pre-created job exist with the same name, And I create schedule job for diffrent schema with the same job name, then job created sucessfully ")
	@Severity(SeverityLevel.NORMAL)
	public void schemaScheduler_withSameJobName_existsNoErrorDisplayed_forOtherSchema() {

		schemasPage.select_schemaName(testDataReader.getCellData("OtherSchema"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Type_scheduleSchema_jobNameOrDescription("Job Name",
				testDataReader.getCellData("SchemaLoadJobName_C77216"));
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();
		scheduledSchema.assert_schemaJob_created(testDataReader.getCellData("SchemaLoadJobName_C77216"),
				testDataReader.getCellData("OtherSchema"));
	}

	@Test(priority = 5, description = "C77117 - Firefox: Fresh Installation: Testing that user can't save changes when he leaves Job name blank")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler job can`t be saved without job name")
	@Severity(SeverityLevel.NORMAL)
	public void SchedulerSchema_withJobNameEmpty_CanNotBeSaved() {

schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Assert_scheduleSchema_textBox_empty("Job Name");

		schemasPage.Type_scheduleSchema_jobNameOrDescription("Description",
				testDataReader.getCellData("DescriptionText_C77117"));

		schemasPage.Assert_scheduleSchema_scheduleButton_disabled();

	}

	@Test(priority = 6, description = "C77118 - Firefox: Fresh Installation Testing that Description field functionality is appeared")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view, Description displayed correctly.")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_DescriptionIsDisplayed() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.assert_schemaSchedule_labelName("Description");

	}

	@Test(priority = 7, description = "C77119 - Firefox: Fresh Installation: Testing that the Scheduler is working with Daily recurrence")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Daily recurrence")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_dailyRecurrence() {


		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		schemasPage.Assert_scheduleSchema_radioButton_selected("Daily");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();
		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Daily");

	}

	@Test(priority = 8, description = "C77120 - Firefox: Fresh Installation: Testing that the Scheduler is working with Weekly recurrence")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Weekly recurrence")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_SchemaView_weeklyRecurrence() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Select_scheduleSchema_radioButton("Weekly");
		schemasPage.Select_scheduleSchema_weeklyDay_CheckBox("Mon");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Weekly");
		scheduledSchema.Assert_scheduleSchema_weeklyDay_CheckBox("Mon");

	}

	@Test(priority = 9, description = "C77121 - Firefox: Fresh Installation: Testing that the Scheduler is working with Monthly recurrence")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Monthly recurrence")
	@Severity(SeverityLevel.NORMAL)
	public void scheduleSchema_monthlyRecurrence() {


		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Select_scheduleSchema_radioButton("Monthly");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Monthly");

	}

	@Test(priority = 10, description = "C77122 - Firefox: Fresh Installation: Testing that the Scheduler is working with No recurrence")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with No recurrence")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_schemaView_noRecurrence() {


		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		schemasPage.Select_scheduleSchema_radioButton("No Recurrence");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("No Recurrence");

	}

	@Test(priority = 11, description = "C77123 - Firefox: Fresh Installation: Testing that the user can create New Schema Job - Full Load")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Full Load")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_schemaView_fullLoad() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");

		schemasPage.Select_scheduleSchema_radioButton("Daily");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();

		schemasPage.select_scheduleSchema_loadType_dropDownMenu("Full");
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Daily");
		scheduledSchema.Assert_scheduleSchema_loadType_dropDownMenu("Full");

	}

	@Test(priority = 12, description = "C77124 - Firefox: Fresh Installation: Testing that the user can create New Schema Job - Incremental Load")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Incremental Load")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_schemaView_incrementalLoad() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		schemasPage.Select_scheduleSchema_radioButton("Daily");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.select_scheduleSchema_loadType_dropDownMenu("Incremental");
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Daily");
		scheduledSchema.Assert_scheduleSchema_loadType_dropDownMenu("Incremental");

	}

	@Test(priority = 13, description = "C77125 - Firefox: Fresh Installation: Testing that the user can create New Schema Job - Staging")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Staging")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_schemaView_stagingLoad() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		schemasPage.Select_scheduleSchema_radioButton("Daily");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.select_scheduleSchema_loadType_dropDownMenu("Staging");
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);

		scheduledSchema.assert_schedulerSchema_radioButton_selected("Daily");
		scheduledSchema.Assert_scheduleSchema_loadType_dropDownMenu("Staging");

	}

	@Test(priority = 14, description = "C77126 - Firefox: Fresh Installation: Testing that the user can create New Schema Job -Snapshot")
	@Description("When I navigate to the schema list page, And I select a schema and select schedule, Then in scheduler view check that Scheduler is working with Snapshot")
	@Severity(SeverityLevel.NORMAL)
	public void Scheduler_schemaView_snapshotLoad() {

		schemasPage.select_schemaName(testDataReader.getCellData("SchemaName"));

		mainPage = new Skeleton(driver);
		mainPage.Click_actions();
		mainPage.Select_fromDropdownMenu("Schedule");
		schemasPage.Select_scheduleSchema_radioButton("Daily");

		String NewSchduleJobName = schemasPage.autoType_scheduleSchema_jobName();
		schemasPage.select_scheduleSchema_loadType_dropDownMenu("Snapshot");
		schemasPage.click_scheduleSchema_scheduleButton();

		scheduledSchema = new SchemaLoads(driver);
		scheduledSchema.Navigate_toURL();

		scheduledSchema.assert_schemaJob_created(NewSchduleJobName,
				testDataReader.getCellData("SchemaName"));
		scheduledSchema.select_schemaName(NewSchduleJobName);
		scheduledSchema.assert_schedulerSchema_radioButton_selected("Daily");
		scheduledSchema.Assert_scheduleSchema_loadType_dropDownMenu("Snapshot");

	}
	
	
	public void navigateToSchemaListPage() {
		
		schemasPage = new SchemaList(driver);
		schemasPage.Navigate_toURL();
		schemasPage.Assert_schemaListTabIsSelected();
		
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
		
		 navigateToSchemaListPage();

	}

}
