package pageObjectModels.api;

import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.supportActionLibrary.SSHActions;

public class CLI {
	//// Variables
	String hostname, username, keyFileFolderName, keyFileName;
	int sshPortNumber;

	//// Commands
	String stopIncorta = "bash --login -c 'cd IncortaAnalytics_Automation && ./stop.sh'";
	String startIncorta = "bash --login -c 'cd IncortaAnalytics_Automation && ./start.sh'";
	String getIncortaPID = "bash --login -c 'cd IncortaAnalytics_Automation && cat tomcat.pid'";

	public CLI(String hostname, int sshPortNumber, String username, String keyFileFolderName, String keyFileName) {
		this.hostname = hostname;
		this.sshPortNumber = sshPortNumber;
		this.username = username;
		this.keyFileFolderName = keyFileFolderName;
		this.keyFileName = keyFileName;
	}

	//// Functions
	public String getIncortaPID() {
		return SSHActions
				.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName, getIncortaPID)
				.trim();
	}

	public String getIcortaRunningDirectory() {
		String getIcortaRunningDirectory = "pwdx " + getIncortaPID();
		return SSHActions.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName,
				getIcortaRunningDirectory);
	}

	public void startIncorta() {
		if (getIcortaRunningDirectory().contains("No such process")) {
			SSHActions.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName,
					startIncorta);
		} else {
			ReportManager.log("Incorta instance is already started.");
		}
	}

	public void stopIncorta() {
		if (getIcortaRunningDirectory().contains("No such process")) {
			ReportManager.log("Incorta instance is already stopped.");
		} else {
			SSHActions.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName,
					stopIncorta);
		}
	}

	public String getAbsolutePathToFile(String relativePathToFile) {
		return SSHActions.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName,
				"cd " + relativePathToFile + " && pwd").trim();
	}
}
