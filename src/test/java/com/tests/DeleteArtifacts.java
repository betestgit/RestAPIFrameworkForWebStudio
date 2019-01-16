package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;

import com.api.Artifact;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteArtifacts extends Login{
	
	public static Response resp;
	
	@Test(priority=1)
	public void deleteArtifacts() {
		
		JsonFileReader file=new JsonFileReader();
		String deleteJson=file.read("deleteArtifact.json");
		
		resp=Artifact.deleteArtifact(deleteJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Artifacts deleted successfully");
		
				
	}
	
	
	@Test(priority=2)
	public void fetchDT() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.rename.artifactRenameToPath");
		String artifactExtension=prop.getProperty("be.dt.rename.artifactExtension");		
		
		resp=Artifact.fetchArtifact(projectName, artifactPath, artifactExtension, apiKey);
		
		//System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data"),"Artifact /Virtual_RF/Applicant_Simple_Rename has been marked for deletion.");			
		
	}
	@Test(priority=3)
	public void fetchBuilderRTI() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.rtibuilder.rename.artifactRenameToPath");
		String artifactExtension=prop.getProperty("be.rtibuilder.rename.artifactExtension");		
		
		resp=Artifact.fetchArtifact(projectName, artifactPath, artifactExtension, apiKey);
		
		//System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data"),"Artifact /Rule_Templates/SpecialOffersRTI_Rename has been marked for deletion.");			
		
	}
	@Test(priority=4)
	public void fetchViewRTI() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.rtiview.rename.artifactRenameToPath");
		String artifactExtension=prop.getProperty("be.rtiview.rename.artifactExtension");		
		
		resp=Artifact.fetchArtifact(projectName, artifactPath, artifactExtension, apiKey);
		
		//System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data"),"Artifact /Rule_Templates/Applicant_Prescreen_RTI_Rename has been marked for deletion.");			
		
	}

 
}
