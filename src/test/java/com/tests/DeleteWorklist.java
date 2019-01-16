package com.tests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Worklist;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteWorklist extends Login {

	public static Response resp;
	
	
	@Test(priority=2)
	public void fetchDetailsOfWorklistItem() {
		
		String worklistItemId="10000";
		
		resp=Worklist.fetchWorklistItemDetails(worklistItemId, apiKey);
		
		Assert.assertFalse(resp.asString().contains("revisionId"));
		
	}
	
	@Test(priority=1)
	public void delete() {
				
		JsonFileReader file=new JsonFileReader();
		String deleteJson=file.read("deleteWorklistItem.json");
		
		resp=Worklist.deleteWorklistItem(deleteJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Revision(s) [10000] successfully deleted.");
	}
}
