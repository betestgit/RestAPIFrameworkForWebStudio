package com.tests;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class CreateRTIs extends Login{
	public static Response resp;	
	
	@Test(priority=1)
	public void fetchBuilderTemplate() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("be.rtibuilder.projectname");
		String artifactPath=prop.getProperty("be.rtibuilder.artifactpath");
		String artifactExtension=prop.getProperty("be.rtibuilder.artifactextension");		
		
		resp=Artifact.fetchArtifact(projectName, artifactPath, artifactExtension, apiKey);
	
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
				
		
	}

	@Test(priority=2)
	public void createBuilderRTI() {		
		
		JsonFileReader file=new JsonFileReader();
		String saveJson=file.read("createBuilderTypeRTI.json");
		
		resp=Artifact.saveArtifact(saveJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Save successfully completed");
				
	}
	
	
	@Test(priority=3)
	public void fetchViewRuleTemplate() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("be.rtiview.projectname");
		String artifactPath=prop.getProperty("be.rtiview.artifactpath");
		String artifactExtension=prop.getProperty("be.rtiview.artifactextension");		
		
		resp=Artifact.fetchArtifact(projectName, artifactPath, artifactExtension, apiKey);
		
		System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
				
		
	}

	@Test(priority=4)
	public void createViewRTI() {		
		
		JsonFileReader file=new JsonFileReader();
		String saveJson=file.read("createViewTypeRTI.json");
		
		resp=Artifact.saveArtifact(saveJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Save successfully completed");
				
	}
	
	
}
