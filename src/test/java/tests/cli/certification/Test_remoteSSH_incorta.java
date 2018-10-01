package tests.cli.certification;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.io.ReportManager;
import com.shaft.support.SSHActions;

public class Test_remoteSSH_incorta {

	@Test
	public void test_executeRemoteSSH() {
		String hostname = "72.55.136.10";
		int sshPortNumber = 5022;
		String username = "incorta";
		String keyFileFolderName = System.getProperty("testDataFolderPath");
		String keyFileName = "iWebQALast.key";
		SSHActions sshObject = new SSHActions(hostname, sshPortNumber, username, keyFileFolderName, keyFileName);

		// SSH commands

		// incorta installation folder
		// > ./stop.sh
		String stopIncorta = "bash --login -c 'cd IncortaAnalytics_Automation && ./stop.sh'";

		// > ./start.sh
		String startIncorta = "bash --login -c 'cd IncortaAnalytics_Automation && ./start.sh'";

		// > cat tomcat.pid //get PID for tomcat server
		String getIncortaPID = "bash --login -c 'cd IncortaAnalytics_Automation && cat tomcat.pid'";

		// incorta installation folder > tmt
		// > ./tmt.sh --export tenantName tenantName.zip -cf [export tenant with data]
		// > ./tmt.sh --import tenantName.zip -cf [import tenant with data]
		// > ./tmt.sh --remove tenantName
		// > ./tmt.sh --list
		String listIncortaTenants = "bash --login -c 'cd IncortaAnalytics_Automation/tmt && ./tmt.sh --list'";

		// Supporting commands
		// > lsof -i:<port number> //get PID of running incorta instance
		// > ps -ef | grep <PID> //get installation directory from PID

		// > cat /proc/<PID>/cmdline //get command of a running instance using PID,
		// return contains "No such file or directory" if no running instance was found
		String getCommandOfRunningIncortaInstance;

		// https://docs.google.com/document/d/1MSDnGHarQ595pp9Dk4DBTbCQcNeUBQnifT-DP-J7k6g/edit

		// Stop Incorta
		String incortaPID = sshObject.performSSHcommand(getIncortaPID).trim();

		getCommandOfRunningIncortaInstance = "cat /proc/" + incortaPID + "/cmdline";
		String commandOfRunningIncortaInstance = sshObject.performSSHcommand(getCommandOfRunningIncortaInstance);

		if (commandOfRunningIncortaInstance.contains("No such file or directory")) {
			ReportManager.log("Incorta instance is already stopped.");
		} else {
			sshObject.performSSHcommand(stopIncorta);
		}

		// perform tmt commands as needed ++insert needed check to confirm that incorta
		// has been completely stopped

		sshObject.performSSHcommand(listIncortaTenants);

		// Start Incorta
		incortaPID = sshObject.performSSHcommand(getIncortaPID).trim();

		getCommandOfRunningIncortaInstance = "cat /proc/" + incortaPID + "/cmdline";
		commandOfRunningIncortaInstance = sshObject.performSSHcommand(getCommandOfRunningIncortaInstance);

		if (commandOfRunningIncortaInstance.contains("No such file or directory")) {
			sshObject.performSSHcommand(startIncorta);
		} else {
			ReportManager.log("Incorta instance is already started.");

		}

	}

	@BeforeClass // Set-up method, to be run once before the first test
	public void beforeClass() {

	}

	@AfterClass(alwaysRun = true) // Tear-down method, to be run once after the last test
	public void afterClass() {
		ReportManager.getFullLog();
	}

	@AfterMethod
	public void afterMethod() {
		ReportManager.getTestLog();
	}
}
