package com.tests;



import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.api.Lifecycle;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class Revert extends Login {
 
	public static Response resp;
	
	
	@Test(priority=1)
	public void modifyExistingRTI() {		
		
		JsonFileReader file=new JsonFileReader();
		String saveJson=file.read("modifyBuilderRTI.json");
		
		resp=Artifact.saveArtifact(saveJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Save successfully completed");
				
	}
	
	@Test(priority=2)
	public void revert() throws FileNotFoundException {
				
		JsonFileReader file=new JsonFileReader();
		String revertJson=file.read("revert.json");
	
		String projectName=prop.getProperty("projectname");
		
		resp=Lifecycle.revert(revertJson,projectName,apiKey);
				
	     Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Selected artifacts reverted successfully.");
	}
	
	
  
	
	
}
