package com.tests;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Group;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class Groups extends Login{
 
	public static Response resp;
	
	@Test(priority=2)
	public void fetchGroups() {		
		
		resp=Group.fetchGroup(apiKey);
		System.out.println(resp.asString());
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[1].name"),"Business Rules");
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[2].name"),"Decision Tables");
	}

	@Test(priority=1)
	public void addGroup() throws FileNotFoundException {
				
		JsonFileReader file=new JsonFileReader();
		String saveJson=file.read("addGroup.json");
		
		String groupName=prop.getProperty("be.group.groupName");
		
		resp=Group.addGroup(saveJson, groupName, apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Group Successfully Added");
	
		
		
	}
	
	@Test(priority=3)
	public void fetchGroupArtifacts() throws FileNotFoundException {
			
		String groupName=prop.getProperty("be.group.groupName");
		String groupType=prop.getProperty("be.group.groupType");
		
		
		resp=Group.fetchGroupArtifacts(groupType, groupName, apiKey);
		//Need to check the response here before automating(5.4.1/5.5.0/5.6.0)
		
	//	Assert.assertNotNull(path.getString("response.data.record[0].artifactPath"));
			
	}
	
	@Test(priority=4)
	public void deleteGroup() throws FileNotFoundException {
		
		String groupName=prop.getProperty("be.group.groupName");
		
		resp=Group.deleteGroup(groupName, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getString("response.responseMessage"),"Group Successfully Deleted");
		
	}
	
	
}
