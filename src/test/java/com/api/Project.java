package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Project {
	
	public static Response resp;
	
	public static Response listAvailableProjects(String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.listAvailableProjects(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
		public static Response listAvailableArtifactsOfProject(String projectName,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.listAvailableArtifactsOfProject(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
		}
		
		
		public static Response listArtifactsReadyToBeCommitted(String inputJson,String projectName,String apiKey) {
			resp=given()
					.accept(ContentType.JSON)
					.body(inputJson)
					.when()
					.post(Resources.commitableArtifactsResource(),apiKey,projectName)
					.then()
					.statusCode(200)
					.extract()
					.response();
			
			return resp;
		}

		public static Response listArtifactsNeedsSynchronization(String projectJson, String projectName,String apiKey) {
			 resp=given()
						.accept(ContentType.JSON)
						.body(projectJson)
						.when()
						.post(Resources.listArtifactsForSynchronizationResource(),apiKey,projectName)
						.then()
						.statusCode(200)
						.extract()
						.response();
				
			return resp;
		}
	
	public static Response generateDeployable(String projectName,String apiKey) {

		resp=given()
					.accept(ContentType.JSON)
					.when()
					.get(Resources.generateDeployableResource(),apiKey,projectName)
					.then()
					.statusCode(200)
					.extract()
					.response();
		return resp;
	}
	
	
	public static Response manageLocks(String projectName, String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.when()
					.get(Resources.manageLocksResource(),apiKey,projectName)
					.then()
					.statusCode(200)
					.extract()
					.response();
		return resp;
	}

	

	

}
