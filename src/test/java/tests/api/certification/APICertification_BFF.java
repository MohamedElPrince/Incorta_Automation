package tests.api.certification;

import org.testng.annotations.Test;

import com.shaft.api.RestActions;

import io.qameta.allure.Epic;

@Epic("API Certification -> BFF POC")
public class APICertification_BFF {
    private final String serviceURI = "http://35.184.27.139:9091/incorta";
    private String serviceName;
    private String requestType;
    private String argument = "";
    private final static String successStatusCode = "200";

    private String tenantName = "demo";
    private String username = "admin";
    private String password = "admin";

    private RestActions restObject = new RestActions(serviceURI);

    @Test(priority = 0, description = "TC000 - Authenticate")
    public void authenticate() {
	// Defining request parameters
	serviceName = "/bff/v1/authentication/" + tenantName + "/accessTokens";
	requestType = "POST";
	argument = "";

	// Performing Authentication
	restObject.performRequest(requestType, "201", serviceName, argument, new String[] { username, password });
    }

    @Test(priority = 1, description = "TC001 - Is User Logged In")
    public void isUserLoggedIn() {
	// Defining request parameters
	serviceName = "/service/user/isLoggedIn";
	requestType = "GET";
	argument = "";

	// Performing Request
	restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	// authenticate();

    }

    @Test(priority = 2, description = "TC002 - GET All Dashboards [BFF]")
    public void getAllDashboards() {
	serviceName = "/bff/v1/dashboards";
	requestType = "GET";
	argument = "";

	restObject.performRequest(requestType, successStatusCode, serviceName, argument);
    }

    @Test(priority = 100, description = "TC003 - Logout from incorta")
    public void logOut() {
	// Defining request parameters
	serviceName = "/authservice/logout";
	requestType = "POST";
	argument = "";

	// Performing Request
	restObject.performRequest(requestType, successStatusCode, serviceName, argument);
    }
}
