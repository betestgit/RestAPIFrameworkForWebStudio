package com.tests;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeployDT extends Login{

	
	
	@Test(priority=1)
	public void checkout() throws FileNotFoundException {
	
		JsonFileReader file=new JsonFileReader();
		String checkoutJson=file.read("checkout.json");
		String projectName=prop.getProperty("projectname");
		
		Response resp=given()
				.contentType(ContentType.JSON)
				.body(checkoutJson)
				.when()
				.post(Resources.checkoutResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();

		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Checkout successful");
	}
	
	@Test(priority=2)
	public void fetchVRF() throws FileNotFoundException {	
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.artifactpath");
		String artifactExtension=prop.getProperty("be.dt.artifactextension");		
		
		Response resp=given()
		.accept(ContentType.JSON)
		.param("projectName", 	projectName)
		.param("artifactPath", artifactPath)
		.param("artifactExtension", artifactExtension)
		.when()
		.get(Resources.fetchArtifactResource(),apiKey)
		.then()
		.statusCode(200)
		.extract()
		.response();
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
				
		
	}

	@Test(priority=3)
	public void createDT() {		
		
		JsonFileReader file=new JsonFileReader();
		String save=file.read("createDT.json");
		
		Response resp=given()
				.accept(ContentType.JSON)
				.body(save)
				.when()
				.post(Resources.saveArtifactResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Save successfully completed");
				
	}
	
	
	
	@Test(priority=4)
	public void validateDT() throws FileNotFoundException {
		
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.validate.artifactpath");
		String artifactExtension=prop.getProperty("be.dt.validate.artifactextension");		
		String artifactType=prop.getProperty("be.dt.validate.artifacttype");
		
		Response resp=given()
		.accept(ContentType.JSON)
		.param("projectName", 	projectName)
		.param("artifactPath", artifactPath)
		.param("artifactExtension", artifactExtension)
		.param("artifactType", artifactType)
		.when()
		.get(Resources.validateResource(),apiKey)
		.then()
		.statusCode(200)
		.extract()
		.response();
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		
		
	}
	
	
	@Test(priority=5)
	public void listArtifactsReadyToBeCommitted() throws FileNotFoundException {
		
		String projectName=prop.getProperty("projectname");		
		JsonFileReader file=new JsonFileReader();
		String inputjson=file.read("input.json");
		
		Response resp=given()
				.accept(ContentType.JSON)
				.body(inputjson)
				.when()
				.post(Resources.commitableArtifactsResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"));
	}
  
	
	@Test(priority=6)
	public void commitDT() throws FileNotFoundException {
			
		JsonFileReader file=new JsonFileReader();
		String commitJson=file.read("commitDT.json");
		String projectName=prop.getProperty("projectname");
		
		Response resp=given()
				.contentType(ContentType.JSON)
				.body(commitJson)
				.expect()
				.statusCode(200)
				.when()
				.post(Resources.commitResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
			
		Assert.assertEquals( Utilities.rawToJson(resp).get("response.responseMessage"),"Checkin Sucessful with Revision Id [10000]");
	}
	

	@Test(priority=10)
	public void fetchWorklistItems() {
		
		Response resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchWorklistResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();

		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.data.record[0].revisionId"),10000);
	}
	
	@Test(priority=11)
	public void approveRTI() {
		
		JsonFileReader file=new JsonFileReader();
		String approveJson=file.read("approve.json");
		
		Response resp=given()
				.accept(ContentType.JSON)
				.body(approveJson)
				.when()
				.post(Resources.approveResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
				
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		
	}
	
	@Test(priority=12)
	
	public void deployRTI() {
		
		JsonFileReader file=new JsonFileReader();
		String approveJson=file.read("deploy.json");
	
		Response resp=given()
			.accept(ContentType.JSON)
			.body(approveJson)
			.when()
			.post(Resources.deployResource(),apiKey)
			.then()
			.statusCode(200)
			.extract()
			.response();		

		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
	}
}
