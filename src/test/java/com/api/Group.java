package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Group {
	public static Response resp;
	
	public static Response fetchGroup(String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchGroupResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
			
		return resp;
	}

	public static Response addGroup(String saveJson,String groupName,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.body(saveJson)
				.expect()
				.statusCode(200)
				.when()
				.post(Resources.addGroupResource(),apiKey,groupName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
	public static Response fetchGroupArtifacts(String groupType,String groupName,String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.param("groupType", groupType)
					.expect()
					.statusCode(200)
					.when()
					.get(Resources.fetchGroupArtifactResource(),apiKey,groupName)
					.then()
					.statusCode(200)
					.extract()
					.response();
			
		 return resp;
	}
	
	public static Response deleteGroup(String groupName,String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.expect()
					.statusCode(200)
					.when()
					.delete(Resources.deleteGroupResource(),apiKey,groupName)
					.then()
					.statusCode(200)
					.extract()
					.response();
			
		 return resp;
	}
}
