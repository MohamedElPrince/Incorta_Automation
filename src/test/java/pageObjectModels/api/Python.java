package pageObjectModels.api;

import java.util.Arrays;
import java.util.List;

import com.shaftEngine.supportActionLibrary.SSHActions;

public class Python {
	//// Variables
	String incortaURL, tenantName, superUser, superPass;
	boolean verifyCertificate;

	String pathToBinFolder, pathToAutomationShellScript;
	String automationBaseScriptFile = "Automation_Base.sh";
	CLI instance;

	//// Functions
	public Python(CLI instance, String incortaURL, String tenantName, String superUser, String superPass,
			boolean verifyCertificate, String relativePathToBinFolder) {
		this.incortaURL = incortaURL;
		this.tenantName = tenantName;
		this.superUser = superUser;
		this.superPass = superPass;
		this.verifyCertificate = verifyCertificate;
		this.instance = instance;

		this.pathToBinFolder = this.instance.getAbsolutePathToFile("IncortaAnalytics_Automation/bin");
		this.pathToAutomationShellScript = pathToBinFolder + "/" + automationBaseScriptFile;
	}

	public String performPythonCommand(String commandName, List<String> params) {
		String sessionVariables = incortaURL + " " + tenantName + " " + superUser + " " + superPass + " "
				+ String.valueOf(verifyCertificate);

		String strParams = String.join("\" \"", params);

		String commandVariables = String.join("\" \"",
				Arrays.asList(pathToBinFolder + "/Automation_Output/" + commandName + ".zip", strParams.toString()));

		String command = "bash --login -c 'bash " + pathToAutomationShellScript + " \"" + sessionVariables + "\" \""
				+ commandName + "\" \"" + commandVariables.trim() + "\"'";

		return SSHActions.performSSHcommand(instance.hostname, instance.sshPortNumber, instance.username,
				instance.keyFileFolderName, instance.keyFileName, command);
	}

}
