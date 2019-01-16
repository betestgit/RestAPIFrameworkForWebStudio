package com.tibco.be.rms.listeners;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import com.api.Artifact;
import com.api.Lifecycle;
import com.constant.NotificationProperties;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.XMLUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseClass extends ReportBaseClass{

	private static final String CONTAIN_NAME = "//*[contains(@name,'%s')]";

	public Properties prop;
	public static String apiKey;
	public static String adminUsername;
	public static String adminPassword;
	public static String businessUsername;
	public static String businessUserPassword;
	public static String technicalUsername;
	public static String technicalPassword;
	public static String forceLogin;
	public static Response resp;

	public static JsonFileReader file;
	public static String checkoutJson;
	public static String projectName;
	public static String dtPath;
	public static String dtExtension;
	public static String rtiBuilderPath;
	public static String rtiBuilderExtension;
	public static String rtiViewPath;
	public static String rtiViewExtension;
	public static String projectBody;
	public static String dtBody;
	public static String dtCommitBody;
	public static String dtApproveBody;
	public static String dtRejectBody;
	public static String dtRevertBody;
	public static String rtiBuilderBody;
	public static String rtiBuilderCommitBody;
	public static String rtiBuilderApproveBody;
	public static String rtiBuilderRejectBody;
	public static String rtiBuilderRevertbody;
	public static String rtiViewBody;
	public static String rtiViewCommitBody;
	public static String rtiViewApproveBody;
	public static String rtiViewRejectBody;
	public static String rtiViewModifyBody;
	public static String rtiViewRevertBody;
	public static String delegateBody;
	public static String deleteArtifactsBody;
	public static String sourceProjectLocation;
	public static String destinationProjectLocation;
	public static String cddSourcePath;
	public static String cddDestPath;
	public static String beHome;
	public static String deleteCommitBody;
	public static String deleteArtifactsApproveBody;
	public static String dtDeletePath;
	public static String dtDeleteExtension;
	public static String rtiBuilderDeletePath;
	public static String rtiBuilderDeleteExtension;
	public static String rtiViewDeletePath;
	public static String rtiViewDeleteExtension;
	public static String deleteProjectBody;
	public static String addGroupBody;
	public static String notifyPreferenceBody;
	public static String groupName;
	public static String groupType;
	public static String lockArtifactBody;
	public static String deleteCommitArtifactBody;
	public static String deleteCreateApproveBody;
	public static String dtRenameArtifactPath;
	public static String dtRenameArtifactExtension;
	public static String dtRenameArtifactType;
	public static String dtArtifactRenameToPath;
	public static String dtRenameImplementsPath;
	public static String dtModifyBody;
	public static String rtiBuilderModifyBody;

	public static String rtibuilderRenameArtifactPath;
	public static String rtibuilderRenameArtifactExtension;
	public static String rtibuilderRenameArtifactType;
	public static String rtibuilderArtifactRenameToPath;
	public static String rtibuilderRenameImplementsPath;

	public static String rtiviewRenameArtifactPath;
	public static String rtiviewRenameArtifactExtension;
	public static String rtiviewRenameArtifactType;
	public static String rtiviewArtifactRenameToPath;
	public static String rtiviewRenameImplementsPath;
	public static String updatePreferencesBody;
	public static String updateAuthenticationBody;
	public static String updateACLBody;
	public static String synchronizeBody;
	public static String deleteWorklistItemsBody;
	public static String dtAnalyzerPath;
	public static String dtAnalyzerExtension;
	
	private Map<NotificationProperties, String> notificationPropertiesMap = new LinkedHashMap<NotificationProperties, String>();

	@BeforeSuite
	public void loadProperties() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"D:\\RestAssuredDemo\\RMSRestAPI\\src\\test\\resources\\env2.properties");
		prop.load(fis);

		RestAssured.baseURI = prop.getProperty("host");

		adminUsername = prop.getProperty("adminuser");
		adminPassword = prop.getProperty("adminpassword");
		businessUsername=prop.getProperty("businessuser");
		businessUserPassword=prop.getProperty("businesspassword");
		technicalUsername=prop.getProperty("techuser");
		technicalPassword=prop.getProperty("techpassword");
		forceLogin = prop.getProperty("forceLogin");
		projectName = prop.getProperty("projectname");

		dtPath = prop.getProperty("be.ws.dt.approve.artifactPath");
		dtExtension = prop.getProperty("be.ws.dt.approve.artifactExtension");
		rtiBuilderPath = prop.getProperty("be.ws.rtibuilder.approve.artifactPath");
		rtiBuilderExtension = prop.getProperty("be.ws.rtibuilder.approve.artifactExtension");
		rtiViewPath = prop.getProperty("be.ws.rtiview.approve.artifactPath");
		rtiViewExtension = prop.getProperty("be.ws.rtiview.approve.artifactExtension");
		sourceProjectLocation = prop.getProperty("sourceProjLocation");
		destinationProjectLocation = prop.getProperty("reportProjLocation");
		cddSourcePath = prop.getProperty("cddSourcePath");
		cddDestPath = prop.getProperty("cddDestPath");
		beHome = prop.getProperty("beHome");
		dtDeletePath = prop.getProperty("be.ws.dt.delete.artifactPath");
		dtDeleteExtension = prop.getProperty("be.ws.dt.delete.artifactExtension");
		rtiBuilderDeletePath = prop.getProperty("be.ws.rtibuilder.delete.artifactPath");
		rtiBuilderDeleteExtension = prop.getProperty("be.ws.rtibuilder.delete.artifactExtension");
		rtiViewDeletePath = prop.getProperty("be.ws.rtiview.delete.artifactPath");
		rtiViewDeleteExtension = prop.getProperty("be.ws.rtiview.delete.artifactExtension");
		groupName = prop.getProperty("be.group.groupName");
		groupType = prop.getProperty("be.group.groupType");

		dtRenameArtifactPath = prop.getProperty("be.ws.dt.rename.artifactPath");
		dtRenameArtifactExtension = prop.getProperty("be.ws.dt.rename.artifactExtension");
		dtRenameArtifactType = prop.getProperty("be.ws.dt.rename.artifactType");
		dtArtifactRenameToPath = prop.getProperty("be.ws.dt.rename.artifactRenameToPath");
		dtRenameImplementsPath = prop.getProperty("be.ws.dt.rename.implementsPath");

		rtibuilderRenameArtifactPath = prop.getProperty("be.ws.rtibuilder.rename.artifactPath");
		rtibuilderRenameArtifactExtension = prop.getProperty("be.ws.rtibuilder.rename.artifactExtension");
		rtibuilderRenameArtifactType = prop.getProperty("be.ws.rtibuilder.rename.artifactType");
		rtibuilderArtifactRenameToPath = prop.getProperty("be.ws.rtibuilder.rename.artifactRenameToPath");
		rtibuilderRenameImplementsPath = prop.getProperty("be.ws.rtibuilder.rename.implementsPath");

		rtiviewRenameArtifactPath = prop.getProperty("be.ws.rtiview.rename.artifactPath");
		rtiviewRenameArtifactExtension = prop.getProperty("be.ws.rtiview.rename.artifactExtension");
		rtiviewRenameArtifactType = prop.getProperty("be.ws.rtiview.rename.artifactType");
		rtiviewArtifactRenameToPath = prop.getProperty("be.ws.rtiview.rename.artifactRenameToPath");
		rtiviewRenameImplementsPath = prop.getProperty("be.ws.rtiview.rename.implementsPath");
		
		
		dtAnalyzerPath=prop.getProperty("be.ws.dt.analyzer.artifactPath");
		dtAnalyzerExtension=prop.getProperty("be.ws.dt.analyzer.artifactExtension");

		
			
		file = new JsonFileReader();
		checkoutJson = file.read("checkout.json");
		projectBody = file.read("input.json");

		dtBody = file.read("createDT.json");
		dtCommitBody = file.read("commitDTForApprove.json");
		dtApproveBody = file.read("dtApprove.json");
		dtRejectBody = file.read("dtReject.json");
		dtModifyBody=file.read("modifyDT.json");
		dtRevertBody=file.read("revertDT.json");
		
		rtiBuilderBody = file.read("createBuilderTypeRTI.json");
		rtiBuilderCommitBody = file.read("commitBuilderRTIForApprove.json");
		rtiBuilderApproveBody = file.read("rtiBuilderApprove.json");
		rtiBuilderRejectBody=file.read("rtiBuilderReject.json");
		rtiBuilderModifyBody=file.read("modifyBuilderTypeRTI.json");
		rtiBuilderRevertbody=file.read("revertBuilderRTI.json");
		
		rtiViewBody = file.read("createViewTypeRTI.json");
		rtiViewCommitBody = file.read("commitViewRTIForApprove.json");
		rtiViewApproveBody = file.read("rtiViewApprove.json");
		rtiViewRejectBody = file.read("rtiViewReject.json");
		rtiViewModifyBody=file.read("modifyViewTypeRTI.json");
		rtiViewRevertBody=file.read("revertViewRTI.json");
		
		deleteArtifactsBody = file.read("deleteArtifact.json");
		
		deleteProjectBody = file.read("deleteProject.json");
		addGroupBody = file.read("addGroup.json");
		notifyPreferenceBody = file.read("updateNotificationPreference.json");

		lockArtifactBody = file.read("lock.json");

		updatePreferencesBody = file.read("updatePreferences.json");
		
		deleteCommitArtifactBody=file.read("deleteCreateCommit.json");
		
		deleteCreateApproveBody=file.read("deleteCreateApprove.json");

		updateAuthenticationBody=file.read("updateAuthenticationDetails.json");
		
		updateACLBody=file.read("updateACL.json");
		
		synchronizeBody=file.read("synchronize.json");
		
		delegateBody=file.read("delegate.json");
		
		deleteWorklistItemsBody=file.read("deleteWorklistItems.json");
	}

	protected void updateCdd(String cddPath,final String classPath) {
		// Updates RMS cdd
		for (NotificationProperties notificationProperty : NotificationProperties.values()) {

			String propertyName = notificationProperty.getPropertyName();
			try {
				if (propertyName.equals("ws.scs.rootURL")) {
					XMLUtil.setValue(cddPath, classPath, String.format(CONTAIN_NAME, propertyName));
				}else {				

				XMLUtil.setValue(cddPath, prop.getProperty(propertyName),
						String.format(CONTAIN_NAME, propertyName));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	

	public static int checkEngineStarted() throws InterruptedException {
		System.out.println("Inside check engine start");
		int flag = 0;
		Thread.sleep(5000);
		String data = "";

		while (flag != 1) {
			try {
				data = new String(Files.readAllBytes(
						Paths.get("D:\\Installation\\BE_HOME\\550GA\\be\\5.5\\rms\\bin\\logs\\RMSLogs.log")));
				if (data.contains("Channel server for HTTP Channel[Port:5000] successfully started")) {
					flag = 1;

				} else if (data.contains("Stopped services.") || data.contains("Disconnecting from metaspace")) {
					flag = 2;
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return flag;
	}

	protected void checkout() {
		// checkout
		System.out.println("API key inside checkout:"+apiKey);
		resp = Lifecycle.checkout(checkoutJson, projectName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Checkout successful");
		System.out.println("Checkout successful...");
	}

	protected void deleteProject() {
		resp = Artifact.deleteArtifact(deleteProjectBody, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),
				"Artifacts deleted successfully");
	}

	

}
