package com.tests;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Worklist;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import io.restassured.response.Response;

public class DeployArtifacts extends Login {
	
	public static Response resp;	

	@Test(priority=1)
	
	public void deployArtifacts() {
		
		JsonFileReader file=new JsonFileReader();
		String deployJson=file.read("deploy.json");
	
		resp=Worklist.deploy(deployJson, apiKey);

		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
	}
}
