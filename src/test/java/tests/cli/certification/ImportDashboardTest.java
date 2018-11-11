package tests.cli.certification;

import java.util.Arrays;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pageObjectModels.api.CLI;
import pageObjectModels.api.Python;

@Epic("CLI Certification -> Import dashboard")
public class ImportDashboardTest {

    // String metadataDBType = "derby"; // oracle | mysql | derby [if derby need to
    // close incorta first]
    private String hostname = "35.184.27.139";
    private int sshPortNumber = 22;
    private int apiPortNumber = 9091;
    private String username = "incorta";
    private String keyFileFolderName = System.getProperty("testDataFolderPath");
    private String keyFileName = "newAutomationEnvironment.key";

    private String installationDirectory = "IncortaAnalytics_Typical_Mysql/IncortaNode"; // issue
    private String tenantName = "cli";
    private String tenantUsername = "admin";
    private String tenantPassword = "admin";

    private CLI cli_instance;
    private Python py_instance;

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

}
