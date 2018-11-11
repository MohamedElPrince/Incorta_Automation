package tests.api.certification;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.io.ReportManager;
import com.shaft.validation.Assertions;

import io.qameta.allure.Epic;
import io.restassured.response.Response;

@Epic("API Certification -> POC")
public class APICertification {
    private final String serviceURI = "http://35.184.27.139:1230/incorta";
    private String serviceName;
    private String requestType;
    private String argument = "";
    private Response response;
    private final static String successStatusCode = "200";

    private String tenantName = "demo";
    private String username = "admin";
    private String password = "admin";

    RestActions restObject = new RestActions(serviceURI);

    @Test(priority = 1, description = "TC001 - Login to incorta via API")
    public void login() {
	// Defining request parameters
	serviceName = "/authservice/login";
	requestType = "POST";
	argument = "tenant=" + tenantName + "&user=" + username + "&pass=" + password;

	// Performing Authentication
	restObject.performRequest(requestType, successStatusCode, serviceName, argument);
    }

    @Test(priority = 2, description = "TC002 - Is User Logged In", dependsOnMethods = { "login" })
    public void isUserLoggedIn() {
	// Defining request parameters
	serviceName = "/service/user/isLoggedIn";
	requestType = "GET";
	argument = "";

	// Performing Request
	response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	// authenticate();
	// restObject.assertResponseJSONContainsValue(response, "users.name", "Super
	// User");

    }

    // @Test(priority = 3, description = "TC003 - GET Users", dependsOnMethods = {
    // "isUserLoggedIn" })
    public void getUsers() {
	// Defining request parameters
	serviceName = "/service/user/getUsers";
	requestType = "GET";
	argument = "pageSize=1000";

	// Performing Request
	response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	Assertions.assertEquals(restObject.getResponseJSONValue(response, "users.name"), "Super User", true);
    }

    @Test(priority = 4, description = "TC004 - Load Schema", dependsOnMethods = { "isUserLoggedIn" })
    public void loadSchema() {
	// Defining request parameters
	serviceName = "/service/schema/loadData";
	requestType = "POST";
	argument = "name=Audit&incremental=false&snapshot=false&staging=false&testRun=";

	// Performing Request
	response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
    }

    @Test(priority = 5, description = "TC005 - Wait for schema to finish loading", dependsOnMethods = { "loadSchema" })
    public void waitForSchemaLoad() {
	// Defining request parameters
	serviceName = "/service/schema/getSchemaStatus";
	requestType = "GET";
	argument = "schemaId=100";

	// Performing Request and waiting for a certain state to be reported
	Boolean isLoaded = false;
	while (isLoaded) {
	    try {
		response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
		Assertions.assertEquals(restObject.getResponseJSONValue(response, "lastLoadState"), "Loading Finished",
			true);
		isLoaded = true;
	    } catch (AssertionError e) {

	    }
	}
    }

    @Test(priority = 6, description = "TC006 - Get loading time", dependsOnMethods = { "waitForSchemaLoad" })
    public void getLoadingTime() {
	// Defining request parameters
	serviceName = "/service/schema/getSchemaStatus";
	requestType = "GET";
	argument = "schemaId=100";

	// Performing Request
	response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	ReportManager.log("Reported Load Time: [" + restObject.getResponseJSONValue(response, "loadTime") + "].");
    }

    @Test(priority = 7, description = "TC007 - Get Dashboard", dependsOnMethods = { "waitForSchemaLoad" })
    public void getDashboard() {
	// Defining request parameters
	serviceName = "/service/catalogreport/getByGUID";
	requestType = "GET";
	argument = "guid=4375a80c-e51b-4088-bdf5-82dea28f34e7";

	// Performing Request
	response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	Assertions.assertEquals(restObject.getResponseXMLValue(response, "response.report.@name"), "Audit Dashboard",
		true);
    }

    @Test(priority = 8, description = "TC007 - Get Insights", dependsOnMethods = {
	    "getDashboard" }, invocationCount = 100, threadPoolSize = 10, groups = { "parallel" })
    public void getInsights() {
	// Defining request parameters
	serviceName = "/service/viewer";
	requestType = "POST";
	List<String> arguments = Arrays.asList("outputFormat=json&layout=101#5bc2ab2f-b546-e092-cec0-7f4ad3e28693",
		"outputFormat=json&layout=101#6ed1f578-81cd-e983-563e-1ca014ec9134",
		"outputFormat=json&layout=101#a5f4ecf0-9604-d8dc-64da-e0f73140954a",
		"outputFormat=json&layout=101#6b8854f1-186d-518a-25d3-0c7bd5df9d7a",
		"outputFormat=json&layout=101#f6ed2345-1fef-7031-c214-0e887d23d15f",
		"outputFormat=json&layout=101#2a0ffb27-4e2a-1d93-ad77-5a527392bd64",
		"outputFormat=json&layout=101#60daff43-efb5-a0b1-eb4c-3aa5a4de490e",
		"outputFormat=json&layout=101#cbfd1170-4e08-0a49-6b88-db7d413bd6d5");

	// Performing Requests
	arguments.forEach(new Consumer<String>() {
	    public void accept(String argument) {
		response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	    }
	});
    }

    @Test(priority = 100, description = "TC009 - Logout from incorta")
    public void logOut() {
	// Defining request parameters
	serviceName = "/authservice/logout";
	requestType = "POST";
	argument = "";

	// Performing Request
	restObject.performRequest(requestType, successStatusCode, serviceName, argument);
    }
}
