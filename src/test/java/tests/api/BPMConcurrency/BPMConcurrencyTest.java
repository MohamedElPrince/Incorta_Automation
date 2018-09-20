package tests.api.BPMConcurrency;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.shaftEngine.ioActionLibrary.ExcelFileManager;
import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.restAssuredActionLibrary.RestActions;
import com.shaftEngine.validationsLibrary.Assertions;
import com.shaftEngine.validationsLibrary.Verifications;

import io.restassured.response.Response;

public class BPMConcurrencyTest {
	ExcelFileManager testDataReader;
	private final static String successStatusCode = "200";
	private String serviceURI = "";
	RestActions restObject;

	String tenantName = "";
	String username = "";
	String password = "";
	String fullName = "";

	String dashboardID = "";
	String[] insightIDs;

	@Factory(dataProvider = "dataProviderMethod")
	public BPMConcurrencyTest(String tenantName, String username, String password, String fullName) {
		this.tenantName = tenantName;
		this.username = username;
		this.password = password;
		this.fullName = fullName;

		restObject = new RestActions();
	}

	@DataProvider(parallel = true)
	public static Object[][] dataProviderMethod() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "BPMConcurrency/TestData.xlsx");
		ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

		String tenantName;
		String username;
		String password;
		String fullName;

		Object[][] testData = new Object[testDataReader.getLastColumnNumber()][4];

		for (int i = 0; i < testDataReader.getLastColumnNumber(); i++) {
			tenantName = testDataReader.getCellData("Tenant", "Data" + (i + 1));
			username = testDataReader.getCellData("Username", "Data" + (i + 1));
			password = testDataReader.getCellData("Password", "Data" + (i + 1));
			fullName = testDataReader.getCellData("FullName", "Data" + (i + 1));

			testData[i][0] = tenantName;
			testData[i][1] = username;
			testData[i][2] = password;
			testData[i][3] = fullName;
		}

		return testData;
	}

	@Test(description = "TC001 - Login to incorta via API")
	public void login() {
		// Defining request parameters
		String serviceName = "/authservice/login";
		String requestType = "POST";
		String argument = "tenant=" + tenantName + "&user=" + username + "&pass=" + password;

		// Performing Authentication
		restObject.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
	}

	@Test(description = "TC002 - Is User Logged In", dependsOnMethods = { "login" })
	public void isUserLoggedIn() {
		// Defining request parameters
		String serviceName = "/service/user/isLoggedIn";
		String requestType = "GET";
		String argument = "";

		// Performing Request
		Response response = restObject.performRequest(requestType, successStatusCode, serviceURI, serviceName,
				argument);
		Assertions.assertEquals(fullName, restObject.getResponseJSONValue(response, "user.name"), true);
	}

	@Test(description = "TC003 - Load Schema", dependsOnMethods = { "isUserLoggedIn" })
	public void loadSchema() {
		// Defining request parameters
		String serviceName = "/service/schema/loadData";
		String requestType = "POST";
		String argument = "name=" + testDataReader.getCellData("SchemaName")
				+ "&incremental=false&snapshot=false&staging=false&testRun=";

		// Performing Request
		restObject.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
	}

	@Test(description = "TC004 - Wait for schema to finish loading", dependsOnMethods = { "loadSchema" })
	public void waitForSchemaLoad() {
		// Defining request parameters
		String serviceName = "/service/schema/getSchemaStatus";
		String requestType = "GET";
		String argument = "schemaId=" + testDataReader.getCellData("SchemaID");

		// Performing Request and waiting for a certain state to be reported
		Boolean isLoaded = false;
		while (isLoaded) {
			try {
				Response response = restObject.performRequest(requestType, successStatusCode, serviceURI, serviceName,
						argument);
				Assertions.assertEquals("Loading Finished", restObject.getResponseJSONValue(response, "lastLoadState"),
						true);
				isLoaded = true;
			} catch (AssertionError e) {

			}
		}
	}

	@Test(description = "TC005 - Get Dashboard", dependsOnMethods = { "waitForSchemaLoad" })
	public void getDashboard() {
		// Defining request parameters
		String serviceName = "/service/catalogreport/getByGUID";
		String requestType = "GET";
		String argument = "guid=" + testDataReader.getCellData("DashboardGUID");

		// Performing Request
		Response response = restObject.performRequest(requestType, successStatusCode, serviceURI, serviceName,
				argument);
		Assertions.assertEquals(testDataReader.getCellData("DashboardName"),
				restObject.getResponseXMLValue(response, "response.report.@name"), true);

		dashboardID = restObject.getResponseXMLValue(response, "response.report.@id");

		insightIDs = restObject.getResponseXMLValue(response, "**.findAll {it.@autoRefresh == 'true' }.@id").split(",");

		Verifications.verifyEquals(testDataReader.getCellData("NumberOfInsights"), insightIDs.length, true);
	}

	@Test(description = "TC006 - Get Insight", dependsOnMethods = {
			"getDashboard" }, invocationCount = 1, threadPoolSize = 1, groups = { "parallel" })
	public void getInsight() {
		// Defining request parameters
		String serviceName = "/service/viewer";
		String requestType = "POST";

		for (int i = 0; i < insightIDs.length - 1; i++) {
			String argument = "outputFormat=json&layout=" + dashboardID + "#" + insightIDs[i].trim();

			// Performing Request
			restObject.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
		}
	}

	@BeforeClass
	public void beforeClass() {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "BPMConcurrency/TestData.xlsx");
		testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

		serviceURI = testDataReader.getCellData("serviceURI");
	}

	@AfterClass
	public void getFullLog() {
		ReportManager.getFullLog();
	}

}
