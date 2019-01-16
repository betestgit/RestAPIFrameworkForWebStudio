package com.tests;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Lifecycle;
import com.api.Project;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class CommitArtifacts extends Login{
  
	public static Response resp;
  
	@Test(priority=1)
	public void listArtifactsReadyToBeCommitted() throws FileNotFoundException {
		
		String projectName=prop.getProperty("projectname");		
		JsonFileReader file=new JsonFileReader();
		String inputJson=file.read("input.json");
		
		
		resp=Project.listArtifactsReadyToBeCommitted(inputJson, projectName, apiKey);
		System.out.println("Worklist"+resp.asString());
		
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"));
	}
  
	
	@Test(priority=2)
	public void commitArtifacts() throws FileNotFoundException {
			
		JsonFileReader file=new JsonFileReader();
		String commitJson=file.read("commit.json");
		String projectName=prop.getProperty("be.rtibuilder.projectname");
		
		resp=Lifecycle.commit(commitJson, projectName, apiKey);
			
		Assert.assertEquals( Utilities.rawToJson(resp).get("response.responseMessage"),"Checkin Sucessful with Revision Id [10000]");
	}
	
	
}
