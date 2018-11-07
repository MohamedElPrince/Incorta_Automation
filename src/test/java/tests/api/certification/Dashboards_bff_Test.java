package tests.api.certification;

import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.shaft.api.RestActions;
import com.shaft.io.ExcelFileManager;
import com.shaft.io.ReportManager;

import io.restassured.response.Response;

public class Dashboards_bff_Test {

    ExcelFileManager testDataReader;

    private final String serviceURI = System.getProperty("incortaRoot");
    // private final String serviceURI = "http://35.184.27.139:9091/incorta/";
    private String serviceName;
    private String serviceName_authentication = "bff/v1/authentication/";
    private String serviceName_folders = "bff/v1/folders";
    private String serviceName_dashboards = "bff/v1/dashboards";
    private String requestType;
    private String argument = "";
    private final static String successStatusCode = "200";
    JsonObject body = new JsonObject();

    String tenantName;
    String username;
    String password;
    // String tenantName = "demobff";
    // String username = "admin";
    // String password = "admin";
    String name;
    String id;
    List<Integer> foldersIds;
    String namePostfix = "_BFFupdated";

    int totalFoldersCounter = 0;
    int totalDashboardsCounter = 0;

    RestActions restObject;

    @Test(priority = 0, description = "TC000 - Authenticate")
    public void authenticate() {
	serviceName = serviceName_authentication + tenantName + "/accessTokens";
	requestType = "POST";
	argument = "";
	restObject.performRequest(requestType, "201", serviceName, argument, new String[] { username, password });
    }

    @Test(priority = 1, description = "TC001 - Get All folders & sub folders, then get all dashboards at all folders & update their names")
    public void get_update_Dashboards_FromAllFolders() {
	getAllFolders();
	totalFoldersCounter = foldersIds.size();
	serviceName = serviceName_dashboards;
	requestType = "GET";
	argument = "";
	updateDashboards(restObject.performRequest(requestType, successStatusCode, serviceName, argument));
	for (int i = 0; i < foldersIds.size(); i++) {
	    serviceName = serviceName_dashboards;
	    requestType = "GET";
	    argument = "folderId=" + (foldersIds.get(i)).toString();
	    Response response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	    if (!response.jsonPath().getList("dashboards").isEmpty()) {
		updateDashboards(response);
	    }
	}
    }

    public void getAllFolders() {
	serviceName = serviceName_folders;
	requestType = "GET";
	argument = "";
	Response response = requestAllFolders(serviceName, requestType, argument);
	foldersIds = response.jsonPath().getList("folders.id");

	if (!foldersIds.isEmpty()) {
	    for (int i = 0; i < foldersIds.size(); i++) {
		serviceName = serviceName_folders;
		requestType = "GET";
		argument = "id=" + (foldersIds.get(i)).toString();
		List<Integer> tempList = requestAllFolders(serviceName, requestType, argument).jsonPath().getList("folders.id");
		if (!tempList.isEmpty()) {
		    foldersIds.addAll(tempList);
		}
	    }
	}
    }

    public Response requestAllFolders(String serviceName, String requestType, String argument) {
	Response response = restObject.performRequest(requestType, successStatusCode, serviceName, argument);
	return response;
    }

    public void updateDashboards(Response response) {

	List<Map<String, String>> dashboards = response.jsonPath().getList("dashboards");

	dashboards.forEach((dashboard) -> {
	    name = dashboard.get("name");
	    id = dashboard.get("guid");

	    serviceName = serviceName_dashboards + "/" + id;
	    requestType = "PATCH";
	    argument = "";

	    body.addProperty("name", name + namePostfix);
	    restObject.performRequest(requestType, successStatusCode, serviceName, argument, body);
	});

	totalDashboardsCounter += dashboards.size();
    }

    @BeforeClass
    public void beforeClass() {
	restObject = new RestActions(serviceURI);
	System.setProperty("testDataFilePath", System.getProperty("testDataFolderPath") + "dashboards_ScreenshotsComparer/TestData.xlsx");
	testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	tenantName = testDataReader.getCellData("Tenant");
	username = testDataReader.getCellData("Username");
	password = testDataReader.getCellData("Password");
    }

    @AfterMethod
    public void afterMethod() {
	ReportManager.getTestLog();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
	ReportManager.log("Total Folders Crawled: [" + totalFoldersCounter + "], and Total Dashboards Crawled: [" + totalDashboardsCounter + "].");
	ReportManager.getFullLog();
    }
}
