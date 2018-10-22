package tests.bff;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.shaft.api.RestActions;

import io.restassured.response.Response;

public class Dashboards_bff_Test {

	private final String serviceURI = "http://35.184.27.139:9091/incorta";
	private String serviceName;
	private String requestType;
	private String argument = "";
	private final static String successStatusCode = "200";
	JsonObject body = new JsonObject();

	String tenantName = "demobff";
	String username = "admin";
	String password = "admin";
	String name;
	String id;

	RestActions restObject;

	@Test(priority = 0, description = "TC000 - Authenticate")
	public void authenticate() {
		serviceName = "/bff/v1/authentication/" + tenantName + "/accessTokens";
		requestType = "POST";
		argument = "";
		restObject.performRequest(requestType, "201", serviceName, argument, new String[] { username, password });
	}

	@Test(priority = 1, description = "TC001 - List All Dashboards")
	public void get_update_Dashboards() {
		serviceName = "/bff/v1/dashboards";
		requestType = "GET";
		argument = "";
		updateDashboards(restObject.performRequest(requestType, successStatusCode, serviceName, argument));
	}

	public void updateDashboards(Response response) {

		List<Map<String, String>> dashboards = response.jsonPath().getList("dashboards");

		dashboards.forEach((dashboard) -> {
			name = dashboard.get("name");
			id = dashboard.get("guid");

			serviceName = "/bff/v1/dashboards/" + id;
			requestType = "PATCH";
			argument = "";

			body.addProperty("name", name + "_18oct");
			restObject.performRequest(requestType, successStatusCode, serviceName, argument, body);
		});
	}

	@BeforeClass
	public void beforeClass() {
		restObject = new RestActions(serviceURI);
	}
}
