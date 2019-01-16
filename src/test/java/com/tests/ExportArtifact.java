package com.tests;

import static io.restassured.RestAssured.given;


import org.testng.annotations.Test;
import com.utilities.Resources;
import io.restassured.http.ContentType;


public class ExportArtifact  extends Login{
	
  @Test
  public void exportDT() {
	  String projectName=prop.getProperty("projectname");
	  String artifactPath=prop.getProperty("be.dt.export.artifactpath");
	  String artifactExtension=prop.getProperty("be.dt.export.artifactextension");
	  String artifactType=prop.getProperty("be.dt.export.artifacttype");
	  
		byte [] expectedValue=given()
				.accept(ContentType.JSON)
				.param("projectName", projectName)
				.param("artifactPath", artifactPath)
				.param("artifactExtension", artifactExtension)
				.param("artifactType", artifactType)
				.when()
				.get(Resources.exportResource(),apiKey)
				.asByteArray();	
	
		System.out.println(expectedValue.toString());
  }
}
