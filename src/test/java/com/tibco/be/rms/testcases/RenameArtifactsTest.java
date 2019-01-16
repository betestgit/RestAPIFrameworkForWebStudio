package com.tibco.be.rms.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

public class RenameArtifactsTest extends BaseClass {

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

	@Test(priority = 1, description = "Create DT and rename it")
	public void renameDT() {

		ExtentTestManager.getTest().setDescription("Create DT and rename it");
		ExtentTestManager.getTest().assignCategory("RenameArtifactsTest");

		resp = Artifact.fetchArtifact(projectName, dtPath, dtExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched Virtual Rule Function:"+resp.asString());
		
		// Create DT based on VRF
		resp = Artifact.saveArtifact(dtBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created DT successfully");
		
		resp = Artifact.renameArtifact(projectName, dtRenameArtifactPath, dtRenameArtifactExtension,
				dtRenameArtifactType, dtArtifactRenameToPath, dtRenameImplementsPath, apiKey);
		System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Rename completed successfully.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Renamed DT successfully");

		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"),
				"/Virtual_RF/Applicant_Simple_Rename");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Artifact found in commit list");

	}

	@Test(priority = 2, description = "Create builder type RTI and rename it")
	public void renameBuilderRTI() {

		ExtentTestManager.getTest().setDescription("Create based type RTI and rename it");
		ExtentTestManager.getTest().assignCategory("RenameArtifactsTest");

		// Fetch rule template
		resp = Artifact.fetchArtifact(projectName, rtiBuilderPath, rtiBuilderExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched builder type Rule template:"+resp.asString());
		
		// Create builder type RTI
		resp = Artifact.saveArtifact(rtiBuilderBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Created builder RTI successfully");
		
		resp = Artifact.renameArtifact(projectName, rtibuilderRenameArtifactPath, rtibuilderRenameArtifactExtension,
				rtibuilderRenameArtifactType, rtibuilderArtifactRenameToPath, rtibuilderRenameImplementsPath, apiKey);	
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Rename completed successfully.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Renamed Builder RTI successfully");

		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[1].artifactPath"),
				"/Rule_Templates/SpecialOffersRTI_Rename");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Artifact found in commit list");
	}

	@Test(priority = 3, description = "Create view type RTI and rename it")
	public void renameViewRTI() {

		ExtentTestManager.getTest().setDescription("Create view type RTI and rename it");
		ExtentTestManager.getTest().assignCategory("RenameArtifactsTest");

		// Fetch rule template
		resp = Artifact.fetchArtifact(projectName, rtiViewPath, rtiViewExtension, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched view type Rule template:" + resp.asString());
		

		// Create view based RTI
		resp = Artifact.saveArtifact(rtiViewBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Save successfully completed");
		ExtentTestManager.getTest().log(LogStatus.INFO, "View based RTI created successfully");
		
		
		resp = Artifact.renameArtifact(projectName, rtiviewRenameArtifactPath, rtiviewRenameArtifactExtension,
				rtiviewRenameArtifactType, rtiviewArtifactRenameToPath, rtiviewRenameImplementsPath, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Rename completed successfully.");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Renamed Builder RTI successfully");
		
		resp = Project.listArtifactsReadyToBeCommitted(projectBody, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[2].artifactPath"),
				"/Rule_Templates/Applicant_Prescreen_RTI_Rename");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Artifact found in commit list");
	}

	@AfterClass
	public void cleanup() {

		Authentication.logout(apiKey);
		System.out.println("Logout successful...");
		EngineUtil.killEngine("be-rms");
	}

}
