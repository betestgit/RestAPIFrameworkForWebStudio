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

public class RevertArtifactsTest extends BaseClass {

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
	public void revertDT() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Verify API to revert artifact changes");
		ExtentTestManager.getTest().assignCategory("RevertArtifactsTest");

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

		resp = Artifact.saveArtifact(dtModifyBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "DT modified successfully");
		
		resp = Lifecycle.revert(dtRevertBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Selected artifacts reverted successfully.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "DT reverted successfully");
	}

	@Test(priority = 2)
	public void revertBuilderRTI() throws FileNotFoundException {
		ExtentTestManager.getTest().setDescription("Verify API to revert artifact changes");
		ExtentTestManager.getTest().assignCategory("RevertArtifactsTest");

		// Fetch rule template
		resp = Artifact.fetchArtifact(projectName, rtiBuilderPath, rtiBuilderExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched builder type Rule template:" + resp.asString());
		
		// Create builder type RTI
		resp = Artifact.saveArtifact(rtiBuilderBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created builder RTI successfully");

		// Lists the artifact ready to be committed
		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"));
		ExtentTestManager.getTest().log(LogStatus.INFO,
				"Builder type RTI is present in commit list:" + resp.asString());

		// Commit the changes
		resp = Lifecycle.commit(rtiBuilderCommitBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Checkin Sucessful with Revision Id [10001]");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Commited changes successfully");

		resp = Artifact.saveArtifact(rtiBuilderModifyBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Builder RTI modified successfully");
		
		resp = Lifecycle.revert(rtiBuilderRevertbody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Selected artifacts reverted successfully.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Builder RTI reverted successfully");

	}

	@Test(priority = 3)
	public void revertViewRTI() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Verify API to revert artifact changes");
		ExtentTestManager.getTest().assignCategory("RevertArtifactsTest");

		// Fetch rule template
		resp = Artifact.fetchArtifact(projectName, rtiViewPath, rtiViewExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched view type Rule template:" + resp.asString());

		// Create view based RTI
		resp = Artifact.saveArtifact(rtiViewBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "View based RTI created successfully");

		// Lists the artifact ready to be committed
		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "View based RTI is present in commit list:" + resp.asString());

		// Commit the changes
		resp = Lifecycle.commit(rtiViewCommitBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Checkin Sucessful with Revision Id [10002]");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Commited changes successfully");

		resp = Artifact.saveArtifact(rtiViewModifyBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "View RTI modified successfully");
		
		resp = Lifecycle.revert(rtiViewRevertBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Selected artifacts reverted successfully.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "View RTI reverted successfully");
	}

	@AfterClass
	public void cleanup() {

		Authentication.logout(apiKey);
		System.out.println("Logout successful...");

		EngineUtil.killEngine("be-rms");
	}

}
