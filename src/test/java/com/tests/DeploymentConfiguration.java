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

public class DeploymentConfiguration extends Login{
  
	public static Response resp;
	
	@Test(priority=1)
	public void addDeploymentConfig() throws FileNotFoundException {
				
		JsonFileReader file=new JsonFileReader();
		String saveJson=file.read("addDeployConfig.json");		
			
		resp=DeploymentConfig.addDeploymentConfig(saveJson, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Deployment Config successfully created");
	
		
		
	}
	
	
	@Test(priority=2)
	public void fetchDeploymentConfig() {
	
		resp=DeploymentConfig.fetchDeploymentConfig(apiKey);
		
		System.out.println("Response:"+resp.asString());
	
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[0].name"),"AutomationQA");
	}
	
	@Test(priority=3)
	public void updateDeploymentConfig() throws IOException {
		
		String projectName=prop.getProperty("projectname");
		String configName=prop.getProperty("configName");
		
		JsonFileReader file=new JsonFileReader();
		String updateDeployJson=file.read("updateDeployConfig.json");
		
		resp=DeploymentConfig.updateDeploymentConfig(updateDeployJson, projectName, configName, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Deployment Config successfully updated");

	}
	
	@Test(priority=4)
	public void deleteDeploymentConfig() {
		
		String projectName=prop.getProperty("projectname");
		String configName=prop.getProperty("configName");
		
		resp=DeploymentConfig.deleteDeploymentConfig(projectName, configName, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"), "Deployment Config successfully deleted");
	}

	
}
