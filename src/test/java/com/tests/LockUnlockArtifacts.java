package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.api.Project;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Properties;

public class LockUnlockArtifacts extends Login {
	
	public static Response resp;
	
	@Test(priority=1)
	public void manageLocks() throws FileNotFoundException {
		
		String projectName=prop.getProperty("projectname");
		
		resp=Project.manageLocks(projectName,apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);	
		
	}
  
	@Test(priority=2)
	public void lockArtifact() {
		
		JsonFileReader file=new JsonFileReader();
		String lock=file.read("lock.json");
		
		resp=Artifact.lock(lock,apiKey);
				
		Assert.assertTrue(Utilities.rawToJson(resp).getBoolean("response.data.record[0].artifactLockResponse.lockAcquired"));
				
	}
	
	@Test(priority=3)
	public void unlockArtifact() {
	
		JsonFileReader file=new JsonFileReader();
		String unlock=file.read("lock.json");
		
		resp=Artifact.unlock(unlock,apiKey);
		Assert.assertTrue(Utilities.rawToJson(resp).getBoolean("response.data.record[0].artifactLockResponse.lockReleased"));			
		
	}
}
