package com.tibco.be.rms.testcases;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.ACL;
import com.api.Authentication;
import com.api.Group;
import com.relevantcodes.extentreports.LogStatus;
import com.tibco.be.rms.listeners.BaseClass;
import com.tibco.be.rms.listeners.ExtentTestManager;
import com.utilities.EngineUtil;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Utilities;
import com.utilities.XMLUtil;

public class ACLTest extends BaseClass {

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

	@Test(enabled=false)
	public  void checkUserEntry() throws FileNotFoundException {

		ExtentTestManager.getTest().setDescription("Verify API to check user entry");
		ExtentTestManager.getTest().assignCategory("ACLTest");
		
		resp=ACL.checkUserEntry(adminUsername,apiKey);		
		Assert.assertNotNull(Utilities.rawToJson(resp).getString("response.data.record[0].authEntry[0]"));
	}
	
	@Test(priority=1)
	public void fetchAuthenticationDetails() {
		
		ExtentTestManager.getTest().setDescription("Verify API to fetch authentication details");
		ExtentTestManager.getTest().assignCategory("ACLTest");
		
		resp=ACL.fetchAuthenticationDetails(apiKey);	
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0]"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched authentication details:"+resp.asString());
	}
	
	@Test(priority=2)
	public void updateAuthenticationDetails() throws IOException {
		
		ExtentTestManager.getTest().setDescription("Verify API to update authentication details");
		ExtentTestManager.getTest().assignCategory("ACLTest");
		
		resp=ACL.updateAuthenticationDetails(updateAuthenticationBody, apiKey);		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Users updated successfully");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Updated authentication details successfully");
	}

	@Test
	public void fetchACL() throws IOException {
		
		ExtentTestManager.getTest().setDescription("Verify API to fetch ACL details");
		ExtentTestManager.getTest().assignCategory("ACLTest");
			
		resp=ACL.fetchACL(projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched ACL details:"+resp.asString());
	}
	
	@Test
	public void updateACL() throws IOException {
		
		ExtentTestManager.getTest().setDescription("Verify API to update ACL details");
		ExtentTestManager.getTest().assignCategory("ACLTest");
		
		resp=ACL.updateACL(updateACLBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Acl updated successfully");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Updated ACL successfully");
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
