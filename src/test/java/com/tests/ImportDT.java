package com.tests;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;


import io.restassured.response.Response;

public class ImportDT extends Login {

	@Test(priority=1)
	public void importDT() throws FileNotFoundException {
	
		
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.import.artifactpath");
		String artifactExtension=prop.getProperty("be.dt.import.artifactextension");
		String artifactType=prop.getProperty("be.dt.import.artifacttype");
		String resourceName=prop.getProperty("be.dt.import.resourcename");
		String resourceFolderPath=prop.getProperty("be.dt.import.resourcefolderpath");
		String overwrite=prop.getProperty("be.dt.import.overwrite");
		String selectResource=prop.getProperty("be.dt.import.selectresource");
		
		
		Response resp=given()
				.contentType("multipart/form-data")
				.multiPart("projectName", projectName)
				.multiPart("artifactPath", artifactPath)
				.multiPart("artifactExtension", artifactExtension)
				.multiPart("artifactType", artifactType)
				.multiPart("resourceName", resourceName)
				.multiPart("resourceFolderPath", resourceFolderPath)
				.multiPart("overwrite", overwrite)
				.multiPart("selectResource", selectResource)
				.when()
				.post(Resources.importDTResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Save successfully completed");
	}
	
}
