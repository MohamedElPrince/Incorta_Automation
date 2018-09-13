package pageObjectModels.api;

import com.shaftEngine.ioActionLibrary.ReportManager;
import com.shaftEngine.supportActionLibrary.SSHActions;

public class CLI {
	//// Variables
	String hostname, username, keyFileFolderName, keyFileName, installationDirectory;
	int sshPortNumber;

	String dockerName = "", dockerUsername = "";

	//// Commands
	String stopIncorta, startIncorta, getIncortaPID;

	public CLI(String hostname, int sshPortNumber, String username, String keyFileFolderName, String keyFileName,
			String installationDirectory) {

		initializeVariables(hostname, sshPortNumber, username, keyFileFolderName, keyFileName, installationDirectory);
		initializeCommands();
	}

	public CLI(String hostname, int sshPortNumber, String username, String keyFileFolderName, String keyFileName,
			String dockerName, String dockerUsername, String installationDirectory) {

		initializeVariables(hostname, sshPortNumber, username, keyFileFolderName, keyFileName, installationDirectory);
		initializeCommands();

		this.dockerName = dockerName;
		this.dockerUsername = dockerUsername;
	}

	private void initializeVariables(String hostname, int sshPortNumber, String username, String keyFileFolderName,
			String keyFileName, String installationDirectory) {
		this.hostname = hostname;
		this.sshPortNumber = sshPortNumber;
		this.username = username;
		this.keyFileFolderName = keyFileFolderName;
		this.keyFileName = keyFileName;
		this.installationDirectory = installationDirectory;
	}

	private void initializeCommands() {
		// initialize commands
		stopIncorta = "bash --login -c 'cd " + installationDirectory + " && ./stop.sh'";
		startIncorta = "bash --login -c 'cd " + installationDirectory + " && ./start.sh'";
		getIncortaPID = "bash --login -c 'cd " + installationDirectory + " && cat tomcat.pid'";
	}

	//// Functions
	protected String performCommand(String command) {
		if (!dockerName.equals("")) {
			return SSHActions.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName,
					dockerName, dockerUsername, command).trim();
		} else {
			return SSHActions
					.performSSHcommand(hostname, sshPortNumber, username, keyFileFolderName, keyFileName, command)
					.trim();
		}
	}

	public String getIncortaPID() {
		return performCommand(getIncortaPID);
	}

	public String getIcortaRunningDirectory() {
		String getIcortaRunningDirectory = "pwdx " + getIncortaPID();
		return performCommand(getIcortaRunningDirectory);
	}

	public void startIncorta() {
		if (getIcortaRunningDirectory().contains("No such process")) {
			performCommand(startIncorta);
		} else {
			ReportManager.log("Incorta instance is already started.");
		}
	}

	public void stopIncorta() {
		if (getIcortaRunningDirectory().contains("No such process")) {
			ReportManager.log("Incorta instance is already stopped.");
		} else {
			performCommand(stopIncorta);
		}
	}

	public String getAbsolutePathToFile(String relativePathToFile) {
		return performCommand("'cd " + relativePathToFile + " && pwd'");
	}
}
