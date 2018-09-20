package tests.cli.certification;

import java.util.Arrays;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaftEngine.ioActionLibrary.ReportManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.api.CLI;
import pageObjectModels.api.Python;

@Epic("incorta CLI > Export Dashboard")
public class ImportDashboardTest {

	// String metadataDBType = "derby"; // oracle | mysql | derby [if derby need to
	// close incorta first]

	// http://35.184.27.139:9091/incorta/new/
	String hostname = "35.184.27.139";
	int sshPortNumber = 22;
	int apiPortNumber = 9091;
	String username = "incorta";
	String keyFileFolderName = System.getProperty("testDataFolderPath");
	String keyFileName = "newAutomationEnvironment.key";

	String installationDirectory = "IncortaAnalytics_Typical_Mysql/IncortaNode"; // issue
	String tenantName = "cli";
	String tenantUsername = "admin";
	String tenantPassword = "admin";

	CLI cli_instance;
	Python py_instance;

	@Test(priority = 1, description = "C84789 - Linux:MySQL:Testing that user can import dashboard without overwrite option")
	@Description("When I import a dashboard using the specified parameters, then it will be imported successfully.")
	@Severity(SeverityLevel.CRITICAL)
	public void test_importDashboardWithoutOverrideOption() {
		String response = py_instance.performPythonCommand("import_dashboards",
				Arrays.asList("Automation_Bashboards/demo_Dashboard_with_bookmarks_04_09_2018.zip", "false"));
		// create folder for importing dashboards
		py_instance.assert_fileImportedSuccessfully(response);
	}

	@BeforeClass // Setup method, to be run once before the first test
	public void beforeClass() {
		cli_instance = new CLI(hostname, sshPortNumber, username, keyFileFolderName, keyFileName,
				installationDirectory);
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
