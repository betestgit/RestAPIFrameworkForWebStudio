package com.tibco.be.rms.testcases;

import org.testng.annotations.Test;
import com.api.Artifact;
import com.api.Authentication;
import com.api.Project;
import com.relevantcodes.extentreports.LogStatus;
import com.tibco.be.rms.listeners.BaseClass;
import com.tibco.be.rms.listeners.ExtentTestManager;
import com.utilities.EngineUtil;
import com.utilities.Utilities;
import com.utilities.XMLUtil;

import java.io.FileNotFoundException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class LockUnlockTest extends BaseClass {

	@BeforeClass
	public void setUp() {

		try {

			Utilities.copy(sourceProjectLocation,
					destinationProjectLocation + "\\" + getClass().getName() + "\\" + projectName);

			Utilities.copy(beHome + "\\rms\\bin\\RMS.cdd", cddDestPath + "\\" + getClass().getName() + "\\RMS.cdd");

			updateCdd(cddDestPath + "\\" + getClass().getName() + "\\RMS.cdd",
					destinationProjectLocation + "\\" + getClass().getName());

			XMLUtil.addNodeToXML(cddDestPath + "\\" + getClass().getName() + "\\RMS.cdd");

			EngineUtil.startEngine(beHome + "\\rms\\bin\\be-rms.exe -c " + cddDestPath + "\\" + getClass().getName()
					+ "\\RMS.cdd" + " RMS.ear -n RMSLogs", null, beHome + "\\rms\\bin");

			if (checkEngineStarted() == 1) {
				System.out.println("Engine has started successfully...");
				resp = Authentication.login(adminUsername, adminPassword, forceLogin);
				apiKey = Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");
				System.out.println("Login succcessful" + apiKey);
				checkout();
			} else if (checkEngineStarted() == 2) {
				System.out.println("Engine did not start...Please try again");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void manageLocks() throws FileNotFoundException {
		
		ExtentTestManager.getTest().setDescription("Verify API to check if locking is enabled for project");
		ExtentTestManager.getTest().assignCategory("LockUnlockTest");

		resp = Project.manageLocks(projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Locking is enabled for a project");

	}

	@Test(priority = 2)
	public void lockArtifact() {
	
		ExtentTestManager.getTest().setDescription("Verify API to lock artifact of project");
		ExtentTestManager.getTest().assignCategory("LockUnlockTest");

		resp = Artifact.lock(lockArtifactBody, apiKey);
		Assert.assertTrue(
				Utilities.rawToJson(resp).getBoolean("response.data.record[0].artifactLockResponse.lockAcquired"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "Locked artifact successfully");
	}

	@Test(priority = 3)
	public void unlockArtifact() {
		ExtentTestManager.getTest().setDescription("Verify API to unlock artifact of project");
		ExtentTestManager.getTest().assignCategory("LockUnlockTest");

		resp = Artifact.unlock(lockArtifactBody, apiKey);
		Assert.assertTrue(
				Utilities.rawToJson(resp).getBoolean("response.data.record[0].artifactLockResponse.lockReleased"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "Unlocked artifact successfully");
	}

	@AfterClass
	public void cleanup() {
		Authentication.logout(apiKey);
		System.out.println("Logout successful...");

		EngineUtil.killEngine("be-rms");

	}
}
