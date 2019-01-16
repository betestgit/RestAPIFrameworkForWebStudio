package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Worklist;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class ApproveArtifacts extends Login {
  public static Response resp;
  

	@Test(priority=1)
	public void fetchWorklistItems() {
		
		resp=Worklist.getWorklistItems(apiKey);

		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.data.record[0].revisionId"),10000);
	}
	
	@Test(priority=2)
	public void approveArtifacts() {
		
		JsonFileReader file=new JsonFileReader();
		String approveJson=file.read("approve.json");
		
		resp=Worklist.approve(approveJson, apiKey);
				
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		
	}
}
