package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Lifecycle {

	public static Response resp;
	
	public static Response checkout(String checkoutJson,String projectName,String apiKey) {
		
		 resp=given()
				.contentType(ContentType.JSON)
				.body(checkoutJson)
				.when()
				.post(Resources.checkoutResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
	
	public static Response commit(String commitJson,String projectName,String apiKey) {
		
		 resp=given()
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
		 
		 return resp;
	}


	public static Response revert(String revertJson, String projectName, String apiKey) {
		resp=given()
				.contentType(ContentType.JSON)
				.body(revertJson)
				.expect()
				.statusCode(200)
				.when()
				.post(Resources.revertResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		return resp;
	}


	public static Response synchronize(String synchronizeJson, String projectName, String apiKey) {
		resp=given()
				.contentType(ContentType.JSON)
				.body(synchronizeJson)
				.expect()
				.statusCode(200)
				.when()
				.post(Resources.synchronizeResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		return resp;
	}
}
