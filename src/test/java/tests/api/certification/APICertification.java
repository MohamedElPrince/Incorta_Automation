package tests.api.certification;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.restAssuredActionLibrary.RestActions;

import io.restassured.response.Response;

public class APICertification {
//	private final String serviceURI = "http://35.184.27.139:1230/incorta";
	private final String serviceURI = "http://35.184.27.139:9080/incorta";
	private String serviceName;
	private String requestType;
	private String argument = "";
	private Response response;
	private final static String successStatusCode = "200";

	@Test(priority = 0, description = "TC000 - Authenticate")
	public void authenticate() {
		// Defining request parameters
		String tenantName = "demo";
		serviceName = "/bff/v1/authentication/" + tenantName + "/accessTokens";
		requestType = "POST";
		//argument = "user=admin&pass=admin";

		// Performing Authentication
		RestActions.performRequest(requestType, "201", serviceURI, serviceName, argument);
	}

	@Test(priority = 1, description = "TC001 - Login to incorta via API")
	public void login() {
		// Defining request parameters
		serviceName = "/authservice/login";
		requestType = "POST";
		argument = "tenant=demo&user=admin&pass=admin";

		// Performing Authentication
		RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
	}

	@Test(priority = 2, description = "TC002 - Is User Logged In")
	public void isUserLoggedIn() {
		// Defining request parameters
		serviceName = "/service/user/isLoggedIn";
		requestType = "GET";
		argument = "";

		// Performing Request
		response = RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
		// authenticate();

	}

	@Test(priority = 3, description = "TC003 - GET Users")
	public void getUsers() {
		// Defining request parameters
		serviceName = "/service/user/getUsers";
		requestType = "GET";
		argument = "pageSize=1000";

		// Performing Request
		response = RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
		RestActions.assertResponseJSONContainsValue(response, "users.name", "Super User");
	}

	@Test(priority = 4, description = "TC004 - Load Schema")
	public void loadSchema() {
		// Defining request parameters
		serviceName = "/service/schema/loadData";
		requestType = "POST";
		argument = "name=Audit&incremental=false&snapshot=false&staging=false&testRun=";

		// Performing Request
		response = RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
	}

	@Test(priority = 5, description = "TC005 - Wait for schema to finish loading")
	public void waitForSchemaLoad() {
		// Defining request parameters
		serviceName = "/service/schema/getSchemaStatus";
		requestType = "GET";
		argument = "schemaId=100";

		// Performing Request and waiting for a certain state to be reported
		Boolean isLoaded = false;
		while (isLoaded) {
			try {
				response = RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName,
						argument);
				RestActions.assertResponseJSONContainsValue(response, "lastLoadState", "Loading Finished");
				isLoaded = true;
			} catch (AssertionError e) {

			}
		}
	}

	@Test(priority = 6, description = "TC006 - Get loading time")
	public void getLoadingTime() {
		// Defining request parameters
		serviceName = "/service/schema/getSchemaStatus";
		requestType = "GET";
		argument = "schemaId=100";

		// Performing Request
		response = RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
		ReportManager.log("Reported Load Time: [" + RestActions.getResponseJSONValue(response, "loadTime") + "].");
	}

	@Test(priority = 100, description = "TC005 - Logout from incorta")
	public void logOut() {
		// Defining request parameters
		serviceName = "/authservice/logout";
		requestType = "POST";
		argument = "";

		// Performing Request
		RestActions.performRequest(requestType, successStatusCode, serviceURI, serviceName, argument);
	}

	@AfterMethod
	public void attachTestLog() {
		ReportManager.getTestLog();
	}

	@AfterClass
	public void getFullLog() {
		ReportManager.getFullLog();
	}

}
