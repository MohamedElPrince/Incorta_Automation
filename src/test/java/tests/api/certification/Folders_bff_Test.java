package tests.api.certification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.shaft.api.RestActions;

public class Folders_bff_Test {
	private final String serviceURI = "http://35.184.27.139:9091/incorta";
	private String serviceName;
	private String requestType;
	private String argument = "";
	private final static String successStatusCode = "200";
	JsonObject body = new JsonObject();

	String tenantName = "demobff";
	String username = "admin";
	String password = "admin";

	RestActions restObject;
	
	@Test(priority = 0, description = "TC000 - Authenticate")
	public void authenticate() {
		// Defining request parameters
		serviceName = "/bff/v1/authentication/" + tenantName + "/accessTokens";
		requestType = "POST";
		argument = "";
		// Performing Authentication
		restObject.performRequest(requestType, "201", serviceName ,argument,
				new String[] { username, password });
	}
	
	@Test(priority = 1, description = "TC001 - List All Folders")
	public void getFolders() {
		// Defining request parameters
		serviceName = "/bff/v1/folders";
		requestType = "GET";
		argument = "";

		// Performing Request
		restObject.performRequest(requestType, successStatusCode,serviceName, argument);
	}
	
	@Test(priority = 2, description = "TC002 - Create Folder")
	public void createFolder() {
		// Defining request parameters
		serviceName = "/bff/v1/folders";
		requestType = "POST";
		argument = "";
		// Performing Request
		body.addProperty("name", "BFF_Automation_Folder" + String.valueOf(System.currentTimeMillis()));
		body.addProperty("parentId", "112");
		restObject.performRequest(requestType, "201", serviceName, argument, body);
	}
	
	@Test(priority = 3, description = "TC003 - Get Folder")
	public void createDashboard() {
		// Defining request parameters
		serviceName = "/bff/v1/folders/476";
		requestType = "GET";
		argument = "";
		// Performing Request
		restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	}
	
	@Test(priority = 4, description = "TC004 - update folder")
	public void updateDashboard() {
		// Defining request parameters
				serviceName = "/bff/v1/folders/478";
				requestType = "PATCH";
				argument = "";
				// Performing Request
				body.addProperty("name", "test");
				restObject.performRequest(requestType, successStatusCode, serviceName, argument, body);
	}
	
	@Test(priority = 5, description = "TC005 - Delete folder")
	public void deleteDashboard() {
		// Defining request parameters
		serviceName = "/bff/v1/folders/476";
		requestType = "DELETE";
		argument = "";
		// Performing Request
		restObject.performRequest(requestType, "204", serviceName, argument);
	}
	
	
	@BeforeClass
	public void beforeClass() {
		restObject = new RestActions(serviceURI);
	}

}
