package com.tibco.be.rms.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.Authentication;
import com.api.NotificationPreference;
import com.relevantcodes.extentreports.LogStatus;
import com.tibco.be.rms.listeners.BaseClass;
import com.tibco.be.rms.listeners.ExtentTestManager;
import com.utilities.EngineUtil;
import com.utilities.Utilities;
import com.utilities.XMLUtil;

public class NotificationPreferencesTest extends BaseClass {
	
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

	
	@Test(priority=2)
	public void fetchNotificationPreferences() {

		ExtentTestManager.getTest().setDescription("Verify API to fetch preferences");
		ExtentTestManager.getTest().assignCategory("NotificationPreferencesTest");

		resp=NotificationPreference.fetchNotificationPreferences(apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0); 
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched notification preferences successfully");
		
	}
	
	@Test(priority=1)
	public void updateNotificationPreferences() {

		ExtentTestManager.getTest().setDescription("Verify API to update preferences");
		ExtentTestManager.getTest().assignCategory("NotificationPreferencesTest");
		
		resp=NotificationPreference.updateNotificationPreferences(notifyPreferenceBody,apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getString("response.responseMessage"), "Notification Preferences successfully updated");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Notification preferences successfully updated");
	}
	
	
	@AfterClass
	public void cleanup() {
		Authentication.logout(apiKey);
		System.out.println("Logout successful...");

		EngineUtil.killEngine("be-rms");
	}
	

}
