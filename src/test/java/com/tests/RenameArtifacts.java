package com.tests;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.api.Project;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import io.restassured.response.Response;

public class RenameArtifacts extends Login{
 
	public static Response resp;
	
	@Test(priority=1)
	public void renameDT() {
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.rename.artifactPath");
		String artifactExtension=prop.getProperty("be.dt.rename.artifactExtension");
		String artifactType=prop.getProperty("be.dt.rename.artifactType");
		String artifactRenameToPath=prop.getProperty("be.dt.rename.artifactRenameToPath");
		String implementsPath=prop.getProperty("be.dt.rename.implementsPath");		
		
		resp=Artifact.renameArtifact(projectName,artifactPath,artifactExtension,artifactType,artifactRenameToPath,implementsPath,apiKey);
		System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Rename completed successfully.");
	
	}
	
	
	@Test(priority=2)
	public void renameBuilderRTI() {
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.rtibuilder.rename.artifactPath");
		String artifactExtension=prop.getProperty("be.rtibuilder.rename.artifactExtension");
		String artifactType=prop.getProperty("be.rtibuilder.rename.artifactType");
		String artifactRenameToPath=prop.getProperty("be.rtibuilder.rename.artifactRenameToPath");
		String implementsPath=prop.getProperty("be.rtibuilder.rename.implementsPath");
		
		
		resp=Artifact.renameArtifact(projectName, artifactPath, artifactExtension, artifactType, artifactRenameToPath, implementsPath, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Rename completed successfully.");
	
	}
	
	@Test(priority=3)
	public void renameViewRTI() {
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.rtiview.rename.artifactPath");
		String artifactExtension=prop.getProperty("be.rtiview.rename.artifactExtension");
		String artifactType=prop.getProperty("be.rtiview.rename.artifactType");
		String artifactRenameToPath=prop.getProperty("be.rtiview.rename.artifactRenameToPath");
		String implementsPath=prop.getProperty("be.rtiview.rename.implementsPath");
		
		
		resp=Artifact.renameArtifact(projectName, artifactPath, artifactExtension, artifactType, artifactRenameToPath, implementsPath, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Rename completed successfully.");
	
	}
	
	@Test(priority=4)
	public void listArtifactsReadyToBeCommitted() throws FileNotFoundException {
		
		String projectName=prop.getProperty("projectname");		
		JsonFileReader file=new JsonFileReader();
		String inputJson=file.read("input.json");
		
		
		resp=Project.listArtifactsReadyToBeCommitted(inputJson, projectName, apiKey);
				
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[1].artifactPath"), "/Virtual_RF/Applicant_Simple_Rename");
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[3].artifactPath"), "/Rule_Templates/SpecialOffersRTI_Rename");
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[5].artifactPath"), "/Rule_Templates/Applicant_Prescreen_RTI_Rename");
	}
  
	
}
