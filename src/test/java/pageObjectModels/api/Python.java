package pageObjectModels.api;

import java.util.Arrays;
import java.util.List;

import com.shaftEngine.supportActionLibrary.SSHActions;
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
		this.pathToAutomationShellScript = pathToBinFolder + "/" + automationBaseScriptFile;
		this.pathToAutomationOutputFolder = pathToBinFolder + "/" + "Automation_Output/";

	}

	public String performPythonCommand(String commandName, List<String> params) {

		automationOutputFileName = commandName + ".zip";

		String sessionVariables = incortaURL + " " + tenantName + " " + superUser + " " + superPass + " "
				+ String.valueOf(verifyCertificate);

		String strParams = String.join("\" \"", params);

		String commandVariables = String.join("\" \"",
				Arrays.asList(pathToAutomationOutputFolder + automationOutputFileName, strParams.toString()));

		String command = "bash --login -c 'bash " + pathToAutomationShellScript + " \"" + sessionVariables + "\" \""
				+ commandName + "\" \"" + commandVariables.trim() + "\"'";

		return SSHActions.performSSHcommand(instance.hostname, instance.sshPortNumber, instance.username,
				instance.keyFileFolderName, instance.keyFileName, command);
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
		SSHActions.performSSHcommand(instance.hostname, instance.sshPortNumber, instance.username,
				instance.keyFileFolderName, instance.keyFileName,
				"cd " + pathToAutomationOutputFolder + " && rm -rf *");

		String fileList = SSHActions.performSSHcommand(instance.hostname, instance.sshPortNumber, instance.username,
				instance.keyFileFolderName, instance.keyFileName, "cd " + pathToAutomationOutputFolder + " && ls");
		Assertions.assertEquals("", fileList.trim(), true);
	}

	public void assert_fileExportedSuccessfully(String response, String fileFolder, String fileName) {
		Assertions.assertEquals("([\\s\\S]*" + "Exported to" + "[\\s\\S]*)", response, true);

		String fileList = SSHActions.performSSHcommand(instance.hostname, instance.sshPortNumber, instance.username,
				instance.keyFileFolderName, instance.keyFileName, "cd " + fileFolder + " && ls");
		Assertions.assertEquals(fileName, fileList.trim(), true);
	}

	public void assert_noFileWasExported(String response, String fileFolder, String fileName) {
		Assertions.assertEquals("([\\s\\S]*" + "Exported to" + "[\\s\\S]*)", response, false);

		String fileList = SSHActions.performSSHcommand(instance.hostname, instance.sshPortNumber, instance.username,
				instance.keyFileFolderName, instance.keyFileName, "cd " + fileFolder + " && ls");
		Assertions.assertEquals(fileName, fileList.trim(), false);
	}
}
