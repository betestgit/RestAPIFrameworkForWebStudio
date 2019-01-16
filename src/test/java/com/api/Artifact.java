package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Artifact {
	
	public static Response resp;
	
public static Response fetchArtifact(String projectName,String artifactPath,String artifactExtension,String apiKey) {
		
		resp=given()
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
		
		return resp;
	}
	
	public static Response saveArtifact(String saveJson,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.body(saveJson)
				.when()
				.post(Resources.saveArtifactResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
	public static Response deleteArtifact(String deleteJson,String apiKey) {
		
		 resp=given()
					.accept(ContentType.JSON)
					.body(deleteJson)
					.expect()
					.statusCode(200)
					.when()
					.delete(Resources.deleteArtifactResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
					
		 return resp;
	}
	
	public static Response renameArtifact(String projectName, String artifactPath, String artifactExtension,
			String artifactType, String artifactRenameToPath, String implementsPath, String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.param("projectName", 	projectName)
					.param("artifactPath", artifactPath)
					.param("artifactExtension", artifactExtension)
					.param("artifactType", artifactType)
					.param("artifactRenameToPath", artifactRenameToPath)
					.param("implementsPath", implementsPath) 
					.when()
					.put(Resources.renameResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
					
		return resp;
	}
	
	
	public static Response importArtifact() {
	return resp;
	}
	
	public static Response exportArtifact() {
		return resp;
	}
	
	
	public static Response lock(String lock, String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.body(lock)
					.when()
					.post(Resources.lockResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
		return resp;
	}

	public static Response unlock(String unlock, String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.body(unlock)
					.when()
					.post(Resources.unlockResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
				    .response();

		return resp;
	}
	
	public static Response showArguments(String projectName,String artifactPath,String artifactExtension,String artifactType,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.showArgumentResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
	
	public static Response validateArtifact(String projectName,String artifactPath,String artifactExtension,String artifactType,String apiKey) {
		
		resp=given()
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
		
		return resp;
				
	}
	
	
	public static Response analyzerValues(String projectName,String artifactPath,String artifactExtension,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.param("projectName", 	projectName)
				.param("artifactPath", artifactPath)
				.param("artifactExtension", artifactExtension)
				.when()
				.get(Resources.analyzerValuesResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	

	public static Response analyzeDT(String projectName,String artifactPath, String artifactExtension,String currentPage,String apiKey) {
	
		resp=given()
				.accept(ContentType.JSON)
				.param("projectName", 	projectName)
				.param("artifactPath", artifactPath)
				.param("artifactExtension", artifactExtension)
				.param("currentPage", currentPage)
				.when()
				.get(Resources.analyzeDTResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}

	public static Response compareArtifact(String projectName,String artifactPath,String artifactExtension,String revisionId,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.param("projectName", 	projectName)
				.param("artifactPath", artifactPath)
				.param("artifactExtension", artifactExtension)
				.param("revisionId", revisionId)
				.when()
				.get(Resources.compareArtifactResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
	public static Response artifactHistory(String projectName,String artifactPath,String artifactExtension,String apiKey) {
		resp=given()
		.accept(ContentType.JSON)
		.param("projectName", 	projectName)
		.param("artifactPath", artifactPath)
		.param("artifactExtension", artifactExtension)
		.when()
		.get(Resources.artifactHistoryResource(),apiKey)
		.then()
		.statusCode(200)
		.extract()
		.response();

		return resp;
	
	}
	
	public static Response analyzerCoverage(String json, String apiKey ) {
		resp=given()
				.accept(ContentType.JSON)
				.body(json)
				.when()
				.post(Resources.analyzerCoverageResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		return resp;
	}
}