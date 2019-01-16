package com.tests;

import static io.restassured.RestAssured.given;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.api.*;

import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AccessControlAPIs extends Login{
  
	public static Response resp;
	
	@Test(enabled=false)
	public  void checkUserEntry() throws FileNotFoundException {
		
		String username=prop.getProperty("be.acl.username");
		String subscriptionId=prop.getProperty("be.acl.subscriptionid");
	
		resp=ACL.checkUserEntry(username,apiKey);
		
		Assert.assertNotNull(Utilities.rawToJson(resp).getString("response.data.record[0].authEntry[0]"));
	}
	
	@Test(priority=1)
	public void fetchAuthenticationDetails() {
	
		resp=ACL.fetchAuthenticationDetails(apiKey);
		
		System.out.println("Response:"+resp.asString());
	
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0]"));
	}
	
	@Test(priority=2)
	public void updateAuthenticationDetails() throws IOException {
			
		JsonFileReader file=new JsonFileReader();
		String updateAuthenticationJson=file.read("updateAuthenticationDetails.json");
		
		resp=ACL.updateAuthenticationDetails(updateAuthenticationJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Users updated successfully");

	}

	@Test
	public void fetchACL() throws IOException {
		
		String projectName=prop.getProperty("projectname");
		
		resp=ACL.fetchACL(projectName, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		
	}
	
	@Test
	public void updateACL() throws IOException {
		
		JsonFileReader file=new JsonFileReader();
		String updateJson=file.read("updateACL.json");

		 resp=ACL.updateACL(updateJson, apiKey);
		 
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Acl updated successfully");
	}
}
