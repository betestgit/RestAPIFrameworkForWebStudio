package com.tibco.be.rms.testcases;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.Authentication;
import com.api.Group;
import com.relevantcodes.extentreports.LogStatus;
import com.tibco.be.rms.listeners.BaseClass;
import com.tibco.be.rms.listeners.ExtentTestManager;
import com.utilities.EngineUtil;
import com.utilities.Utilities;
import com.utilities.Utilities;
import com.utilities.XMLUtil;

public class GroupTest extends BaseClass {

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
	public void addGroup() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Verify addGroup API");

		ExtentTestManager.getTest().assignCategory("GroupTest");

		resp = Group.addGroup(addGroupBody, groupName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Group Successfully Added");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Group created successfully");
	}

	@Test(priority = 2)
	public void fetchGroups() {
		ExtentTestManager.getTest().setDescription("Verify API to fetch all groups");
		ExtentTestManager.getTest().assignCategory("GroupTest");
		resp = Group.fetchGroup(apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[1].name"), "Business Rules");
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[2].name"), "Decision Tables");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched all the groups successfully:"+resp.asString());
	}

	@Test(priority = 3)
	public void fetchGroupArtifacts() throws FileNotFoundException {
		ExtentTestManager.getTest().setDescription("Verify API to fetch artifacts of a specified group");
		ExtentTestManager.getTest().assignCategory("GroupTest");
		resp = Group.fetchGroupArtifacts(groupType, groupName, apiKey);
		// Need to check the response here before automating(5.4.1/5.5.0/5.6.0)

		// Assert.assertNotNull(path.getString("response.data.record[0].artifactPath"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched group artifacts:"+resp.asString());

	}

	@Test(priority = 4)
	public void deleteGroup() throws FileNotFoundException {
		ExtentTestManager.getTest().setDescription("Verify API to delete the group");
		ExtentTestManager.getTest().assignCategory("GroupTest");
		resp = Group.deleteGroup(groupName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getString("response.responseMessage"),
				"Group Successfully Deleted");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Group deleted successfully");

	}

	@AfterClass
	public void cleanup() {

		deleteProject();
		System.out.println("Project deleted from workspace...");
		Authentication.logout(apiKey);
		System.out.println("Logout successful...");

		EngineUtil.killEngine("be-rms");
	}

}
