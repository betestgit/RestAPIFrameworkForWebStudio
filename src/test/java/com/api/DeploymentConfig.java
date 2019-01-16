package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeploymentConfig {
	public static Response resp;
	
	public static Response fetchDeploymentConfig(String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchDeploymentConfigResource(),apiKey)	
				.then()
				.statusCode(200)
				.extract()
				.response();	
		return resp;
	}
	
	
	public static Response addDeploymentConfig(String saveJson, String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.body(saveJson)
				.expect()
				.statusCode(200)
				.when()
				.post(Resources.addDeploymentConfigResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
	
		 return resp;
	}
	
	public static Response updateDeploymentConfig(String updateJson,String projectName,String configName,String apiKey) {
		 resp=given()
				.contentType(ContentType.JSON)
				.body(updateJson)
				.param("name", configName)
				.param("projectName",projectName)
				.when()
				.put(Resources.updateDeploymentConfigResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		 
		 return resp;
			
	}
	
	public static Response deleteDeploymentConfig(String projectName,String configName,String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.param("name", configName)
					.param("projectName",projectName)
					.when()
					.delete(Resources.deleteDeploymentConfigResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
			
		 return resp;
	}
	
	
	
}
