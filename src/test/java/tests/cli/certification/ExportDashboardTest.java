package tests.cli.certification;

import java.util.Arrays;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.io.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.api.CLI;
import pageObjectModels.api.Python;

@Epic("incorta CLI > Export Dashboard")
public class ExportDashboardTest {

	// String metadataDBType = "derby"; // oracle | mysql | derby [if derby need to
	// close incorta first]
	String hostname = "35.184.27.139";
	int sshPortNumber = 22;
	int apiPortNumber = 9080;
	String username = "incorta";
	String keyFileFolderName = System.getProperty("testDataFolderPath");
	String keyFileName = "newAutomationEnvironment.key";

	String dockerName = "analytics-mysql";
	String dockerUsername = "incorta";

	String installationDirectory = "home/incorta/IncortaAnalytics_Analytics_Mysql/IncortaNode"; // issue
	String tenantName = "cli";
	String tenantUsername = "admin";
	String tenantPassword = "admin";

	CLI cli_instance;
	Python py_instance;

	@Test(priority = 1, description = "C83334 - MYSQL: Export a dashboard is working from CLI")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.CRITICAL)
	public void test_exportDashboardWithAttachments() {
		// String response = py_instance.performPythonCommand("export_dashboards",
		// Arrays.asList("*"));
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "*"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 2, description = "C83335 - MYSQL: Export a dashboard with Bookmarks")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportDashboardWithBookmarks() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "false", "*"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 3, description = "C83336 - MYSQL: Export a dashboard with Scheduled Jobs")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportDashboardWithScheduledJobs() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("false", "true", "*"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 4, description = "C83337 - MYSQL: Export a dashboard Without Bookmarks or Scheduled Jobs")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportDashboardWithoutBookmarksOrScheduledJobs() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("false", "false", "*"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	// requires a dashboard that has been created under a specific folder, should be
	// read from test data file, and prepared with the import tenant action
	@Test(priority = 5, description = "C83338 - MYSQL: Export a dashboard created under a specific folder")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportDashboardUnderFolder() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboard"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	// requires two dashboards that have been created under a specific subfolder,
	// should be read from test data file, and prepared with the import tenant
	// action
	@Test(priority = 6, description = "C83340 - MYSQL: Export Many dashboards created under a specific path")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportMultipleDashboardUnderSubFolder() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationSubFolder/*"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	// requires three dashboards that have been created should be read from test
	// data file, and prepared with the import tenant
	// action
	@Test(priority = 7, description = "C83342 - MYSQL: Export many dashboards at the same time")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportMultipleDashboardsExplicitly() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboard",
						"/AutomationFolder/AutomationSubFolder/AutomationDashboardA",
						"/AutomationFolder/AutomationSubFolder/AutomationDashboardB"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 8, description = "C83343 - MYSQL: Export a dashboard that doesn't exsist")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportNonExistingDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "NonExistingDashboard"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 9, description = "C83345 - MYSQL: Export a dashboard having a long name")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportLongNameDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments", Arrays.asList("true",
				"true",
				"/AutomationFolder/AutomationDashboardAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 10, description = "C83347 - MYSQL: Export a dashboard having Special characters")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportSpecialCharactersDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboard&%@"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 11, description = "C83350 - MYSQL: Export a dashboard having Arabic Characters")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportArabicCharactersDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboardأوتوميشن"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 12, description = "C83351 - MYSQL: Export a dashboard having French characters")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportFrenchCharactersDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboardâêîôû"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 13, description = "C83352 - MYSQL: Export a dashboard having Chinese characters")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportChineseCharactersDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboard自动化"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@Test(priority = 14, description = "C83353 - MYSQL: Export a dashboard having numbers")
	@Description("When I export a dashboard using the specified parameters, then it will be exported successfully.")
	@Severity(SeverityLevel.NORMAL)
	public void test_exportNumbersDashboard() {
		String response = py_instance.performPythonCommand("export_dashboards_with_attachments",
				Arrays.asList("true", "true", "/AutomationFolder/AutomationDashboard1234"));
		py_instance.assert_fileExportedSuccessfully(response, py_instance.getAutomationOutputFileName());
	}

	@BeforeClass // Setup method, to be run once before the first test
	public void beforeClass() {
		cli_instance = new CLI(hostname, sshPortNumber, username, keyFileFolderName, keyFileName, dockerName,
				dockerUsername, installationDirectory);
		py_instance = new Python(cli_instance, "http://" + hostname + ":" + apiPortNumber + "/incorta", tenantName,
				tenantUsername, tenantPassword, false, installationDirectory + "/bin");
	}

	@BeforeMethod // Setup method, to be run once before every test
	public void beforeMethod() {
		// delete automation output directory and recreate it
		py_instance.cleanAutomationOutputDirectory();
	}

	@AfterMethod // Tear-down method, to be run once after every test
	public void afterMethod() {
		ReportManager.getTestLog();
	}

	@AfterClass(alwaysRun = true) // Tear-down method, to be run once after the last test
	public void afterClass() {
		ReportManager.getFullLog();
	}
}
