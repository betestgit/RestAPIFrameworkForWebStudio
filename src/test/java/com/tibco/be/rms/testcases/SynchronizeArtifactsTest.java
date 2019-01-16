package com.tibco.be.rms.testcases;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.api.Authentication;
import com.api.Lifecycle;
import com.api.Project;
import com.api.Worklist;
import com.relevantcodes.extentreports.LogStatus;
import com.tibco.be.rms.listeners.BaseClass;
import com.tibco.be.rms.listeners.ExtentTestManager;
import com.utilities.EngineUtil;
import com.utilities.Utilities;
import com.utilities.XMLUtil;

public class SynchronizeArtifactsTest extends BaseClass {

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

			} else if (checkEngineStarted() == 2) {
				System.out.println("Engine did not start...Please try again");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void synchronizeArtifact() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Create DT, Commit and approve it");
		ExtentTestManager.getTest().assignCategory("SynchronizeArtifactsTest");

		// login using business user
		resp = Authentication.login(businessUsername, businessUserPassword, forceLogin);
		apiKey = Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");
		System.out.println("api key:"+apiKey);
		
		// Checkout using business user
		checkout();

		// Logout using business user
		Authentication.logout(apiKey);

		// login using admin user
		resp = Authentication.login(adminUsername, adminPassword, forceLogin);
		apiKey = Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");

		// checkout project using admin user
		checkout();

		// Fetch virtual rule function
		resp = Artifact.fetchArtifact(projectName, dtPath, dtExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched Virtual Rule Function:" + resp.asString());

		// Create DT based on VRF
		resp = Artifact.saveArtifact(dtBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created DT successfully");

		// Lists the artifact ready to be committed
		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "DT is present in commit list:" + resp.asString());

		// Commit the changes
		resp = Lifecycle.commit(dtCommitBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Checkin Sucessful with Revision Id [10000]");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Commited changes successfully");

		// Get worklist items
		resp = Worklist.getWorklistItems(apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.data.record[0].revisionId"), 10000);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched worklist items:" + resp.asString());

		// Approve the changes
		resp = Worklist.approve(dtApproveBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Approved DT successfully");

		// Logout using admin user
		Authentication.logout(apiKey);

		// login using admin user
		resp = Authentication.login(businessUsername, businessUserPassword, forceLogin);
		apiKey = Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Logged in using "+businessUsername);
		
		// List the artifacts that needs synchronization
		resp = Project.listArtifactsNeedsSynchronization(projectBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"),
				"/Virtual_RF/Applicant_Simple_Test");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Artifact found in synchronization list:"+resp.asString());
		
		//Synchronize the artifacts
		resp = Lifecycle.synchronize(synchronizeBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Artifact synchronized successfully");

	}

	@AfterClass
	public void cleanup() {

		
		Authentication.logout(apiKey);
		System.out.println("Logout successful...");
		EngineUtil.killEngine("be-rms");
	}

}
