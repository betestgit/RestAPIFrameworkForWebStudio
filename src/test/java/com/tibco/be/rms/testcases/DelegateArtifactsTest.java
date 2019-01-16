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

public class DelegateArtifactsTest extends BaseClass {

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
	public void delegateArtifactToTechnicalUser() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Verify API to revert artifact changes");
		ExtentTestManager.getTest().assignCategory("DelegateArtifactsTest");

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

		resp = Worklist.delegate(delegateBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Revisions [10000] successfully delegated to role [Technical-User]");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Delegated artifact successfully to user "+technicalUsername);

		Authentication.logout(apiKey);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Logged out using admin");

	}

	@Test(priority = 2)
	public void approveArtifactUsingTechnicalUser() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Verify API to approve delegated artifact");
		ExtentTestManager.getTest().assignCategory("DelegateArtifactsTest");

		resp = Authentication.login(technicalUsername, technicalPassword, forceLogin);
		apiKey = Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Logged in using "+technicalUsername);
		
		// Get worklist items
		resp = Worklist.getWorklistItems(apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.data.record[0].revisionId"), 10000);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched worklist items:" + resp.asString());

		// Approve the changes
		resp = Worklist.approve(dtApproveBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Approved DT successfully");

	}

	@AfterClass
	public void cleanup() {

		Authentication.logout(apiKey);
		System.out.println("Logout successful...");
		EngineUtil.killEngine("be-rms");
	}

}
