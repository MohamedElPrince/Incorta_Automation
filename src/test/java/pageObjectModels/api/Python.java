package pageObjectModels.api;

import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;

import com.shaftEngine.validationsLibrary.Assertions;

public class Python {
	//// Variables
	String incortaURL, tenantName, superUser, superPass;
	boolean verifyCertificate;

	String pathToBinFolder, pathToAutomationShellScript;
	String automationBaseScriptFile = "Automation_Base.sh";
	CLI instance;

	String pathToAutomationOutputFolder;
	String automationOutputFileName;

	//// Functions
	public Python(CLI instance, String incortaURL, String tenantName, String superUser, String superPass,
			boolean verifyCertificate, String relativePathToBinFolder) {
		this.incortaURL = incortaURL;
		this.tenantName = tenantName;
		this.superUser = superUser;
		this.superPass = superPass;
		this.verifyCertificate = verifyCertificate;
		this.instance = instance;

		this.pathToBinFolder = this.instance.getAbsolutePathToFile(relativePathToBinFolder);

		// this.pathToBinFolder = relativePathToBinFolder;
		this.pathToAutomationShellScript = pathToBinFolder + FileSystems.getDefault().getSeparator()
				+ automationBaseScriptFile;
		this.pathToAutomationOutputFolder = pathToBinFolder + FileSystems.getDefault().getSeparator()
				+ "Automation_Output/";

	}

	public String performPythonCommand(String commandName, List<String> params) {

		automationOutputFileName = commandName + ".zip";

		String sessionVariables = incortaURL + " " + tenantName + " " + superUser + " " + superPass + " "
				+ String.valueOf(verifyCertificate);

		String strParams = String.join("\" \"", params);

		String commandVariables = String.join("\" \"",
				Arrays.asList(pathToAutomationOutputFolder + automationOutputFileName, strParams.toString()));

		// String command = "bash --login -c 'bash " + pathToAutomationShellScript + "
		// \"" + sessionVariables + "\" \""
		// + commandName + "\" \"" + commandVariables.trim() + "\"'";

		String command = "'bash " + pathToAutomationShellScript + " \"" + sessionVariables + "\" \"" + commandName
				+ "\" \"" + commandVariables.trim() + "\"'";

		return instance.performCommand(command);
	}

	/**
	 * @return the pathToAutomationOutputFolder
	 */
	public String getPathToAutomationOutputFolder() {
		return pathToAutomationOutputFolder;
	}

	/**
	 * @return the automationOutputFileName
	 */
	public String getAutomationOutputFileName() {
		return automationOutputFileName;
	}

	public void cleanAutomationOutputDirectory() {
		instance.performCommand("'cd " + pathToAutomationOutputFolder + " && rm -rf *'");
		String fileList = instance.performCommand("'cd " + pathToAutomationOutputFolder + " && ls'");
		Assertions.assertEquals("", fileList.trim(), true);
	}

	public void assert_fileExportedSuccessfully(String response, String fileName) {
		Assertions.assertEquals("([\\s\\S]*" + "Exported to" + "[\\s\\S]*)", response, true);
		String fileList = instance.performCommand("'cd " + pathToAutomationOutputFolder + " && ls'");
		Assertions.assertEquals(fileName, fileList.trim(), true);
	}

	public void assert_fileImportedSuccessfully(String response) {
		Assertions.assertEquals("([\\s\\S]*" + "importedObjects" + "[\\s\\S]*)", response, true);
	}

	public void assert_noFileWasExported(String response, String fileName) {
		Assertions.assertEquals("([\\s\\S]*" + "Exported to" + "[\\s\\S]*)", response, false);
		String fileList = instance.performCommand("'cd " + pathToAutomationOutputFolder + " && ls'");
		Assertions.assertEquals(fileName, fileList.trim(), false);
	}
}
