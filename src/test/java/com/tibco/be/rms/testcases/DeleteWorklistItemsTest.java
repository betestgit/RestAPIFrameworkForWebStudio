package com.tibco.be.rms.testcases;

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

public class DeleteWorklistItemsTest extends BaseClass {

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
	public void deleteWorklistItemsNotYetApprovedRejected() {
		ExtentTestManager.getTest().setDescription(
				"This test case is used to test API to delete worklist items which are not yet approved or rejected");
		ExtentTestManager.getTest().assignCategory("DeleteWorklistItemsTest");

		// Fetch virtual rule function
		resp = Artifact.fetchArtifact(projectName, dtPath, dtExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched Virtual Rule Function:" + resp.asString());

		// Create DT based on VRF
		resp = Artifact.saveArtifact(dtBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created DT successfully");

		// Fetch rule template
		resp = Artifact.fetchArtifact(projectName, rtiBuilderPath, rtiBuilderExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched builder type Rule template:" + resp.asString());

		// Create builder type RTI
		resp = Artifact.saveArtifact(rtiBuilderBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created builder RTI successfully");

		// Fetch rule template
		resp = Artifact.fetchArtifact(projectName, rtiViewPath, rtiViewExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched view type Rule template:" + resp.asString());

		// Create view based RTI
		resp = Artifact.saveArtifact(rtiViewBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created View based RTI successfully");

		// Lists the artifact ready to be committed
		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"));
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[1].artifactPath"));
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[2].artifactPath"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "Artifacts found in the commit list");

		// Commit the changes
		resp = Lifecycle.commit(deleteCommitArtifactBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Checkin Sucessful with Revision Id [10000]");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Commit the changes");

		// Get worklist items
		resp = Worklist.getWorklistItems(apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.data.record[0].revisionId"), 10000);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetch the worklist items:" + resp.asString());

		resp = Worklist.deleteWorklistItem(deleteWorklistItemsBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"No Revisions were deleted. Revision(s) [10000] having artifacts which are not yet approved/rejected cannot be deleted.");
		ExtentTestManager.getTest().log(LogStatus.INFO,
				"Worklist item can not be deleted as it not in approved/rejected status");

	}

	@Test(priority = 2)
	public void deleteApprovedWorklistItems() {
		ExtentTestManager.getTest()
				.setDescription("This test case is used to test API to delete worklist items which are approved");
		ExtentTestManager.getTest().assignCategory("DeleteWorklistItemsTest");

		// Approve the changes
		resp = Worklist.approve(deleteCreateApproveBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Approved all 3 artifacts");

		// Delete worklist item
		resp = Worklist.deleteWorklistItem(deleteWorklistItemsBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Revision(s) [10000] successfully deleted.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Worklist item deleted succcessfully");

		resp = Worklist.fetchWorklistItemDetails("10000", apiKey);
		Assert.assertFalse(resp.asString().contains("revisionId"));

	}

	@AfterClass
	public void cleanup() {

		Authentication.logout(apiKey);
		System.out.println("Logout successful...");

		EngineUtil.killEngine("be-rms");
	}
}
