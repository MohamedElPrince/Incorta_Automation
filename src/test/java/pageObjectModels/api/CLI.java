package pageObjectModels.api;

import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.supportActionLibrary.SSHActions;

public class CLI {
	//// Variables
	String hostname, username, keyFileFolderName, keyFileName, installationDirectory;
	int sshPortNumber;

	//// Commands
	String stopIncorta, startIncorta, getIncortaPID;

	public CLI(String hostname, int sshPortNumber, String username, String keyFileFolderName, String keyFileName,
			String installationDirectory) {
		this.hostname = hostname;
		this.sshPortNumber = sshPortNumber;
		this.username = username;
		this.keyFileFolderName = keyFileFolderName;
		this.keyFileName = keyFileName;
		this.installationDirectory = installationDirectory;

		// initialize commands
		stopIncorta = "bash --login -c 'cd " + installationDirectory + " && ./stop.sh'";
		startIncorta = "bash --login -c 'cd " + installationDirectory + " && ./start.sh'";
		getIncortaPID = "bash --login -c 'cd " + installationDirectory + " && cat tomcat.pid'";
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
