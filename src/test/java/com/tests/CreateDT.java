package com.tests;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class CreateDT extends Login {

	public static Response resp;
	
	@Test(priority=1)
	public void fetchVRF() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.artifactpath");
		String artifactExtension=prop.getProperty("be.dt.artifactextension");		
		
		resp=Artifact.fetchArtifact(projectName, artifactPath, artifactExtension, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
				
		
	}


	@Test(priority=2)
	public void createDT() {		
		
		JsonFileReader file=new JsonFileReader();
		String saveJson=file.read("createDT.json");
		
		resp=Artifact.saveArtifact(saveJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Save successfully completed");
				
	}
}
