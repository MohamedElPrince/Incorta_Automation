package tests.cli.certification;

import java.util.Arrays;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.validationsLibrary.Assertions;

import pageObjectModels.api.CLI;
import pageObjectModels.api.Python;

public class ExportDashboardTest {

	String metadataDBType = "derby"; // oracle | mysql | derby [if derby need to close incorta first]
	String hostname = "72.55.136.10";
	int sshPortNumber = 5022;
	String username = "incorta";
	String keyFileFolderName = System.getProperty("testDataFolderPath");
	String keyFileName = "iWebQALast.key";

	@Test
	public void test_exportAllDashboards() {
		CLI cli_instance = new CLI(hostname, sshPortNumber, username, keyFileFolderName, keyFileName);
		Python py_instance = new Python(cli_instance, "http://qa.incorta.com:1230/incorta", "demo", "admin", "admin",
				false, "IncortaAnalytics_Automation/bin");

		String response = py_instance.performPythonCommand("export_dashboards", Arrays.asList("*"));
		Assertions.assertEquals("([\\s\\S]*" + "Exported to" + "[\\s\\S]*)", response, true);

	}

	@BeforeClass // Set-up method, to be run once before the first test
	public void beforeClass() {
	}

	@AfterClass(alwaysRun = true) // Tear-down method, to be run once after the last test
	public void afterClass() {
		ReportManager.getFullLog();
	}

	@AfterMethod
	public void afterMethod() {
		ReportManager.getTestLog();
	}
}
