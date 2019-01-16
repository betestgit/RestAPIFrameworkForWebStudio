package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Workspace {

	public static Response resp;

	public static Response listOfCheckedOutProjects(String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.when()
					.get(Resources.listCheckedoutProjectResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
		return resp;
	}
	
	public static Response fetchRecentlyOpenedArtifacts(String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.when()
					.get(Resources.recentlyOpenedArtifactResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
		return resp;
	}
	
	
	public static Response addToRecentlyOpenedArtifact(String projectName, String artifactPath, String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.param("projectName", 	projectName)
					.param("artifactPath", artifactPath)
					.when()
					.put(Resources.addToRecentlyItemsResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();		
		
		return resp;
	}

	public static Response fetchUserRolesForDelegation(String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchUserRolesForDelegationResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();		
	
		return resp;
	}

	
	public static Response fetchProjectAvailableForLockManagement(String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.listProjectForLockManagementResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();		
	
		return resp;
	}
	
	public static Response checkDeployable(String projectName,String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.param("projectName", projectName)
				.when()
				.get(Resources.checkDeployableResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();		
	
		return resp;
	}
	
	public static Response about(String apiKey) {	
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.aboutResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();		
	
		return resp;
	}
}
