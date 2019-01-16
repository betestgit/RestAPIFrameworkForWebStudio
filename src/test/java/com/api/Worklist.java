package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Worklist {
	
	public static Response resp;
	
	public static Response getWorklistItems(String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchWorklistResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		return resp;
	}
	
	public static Response fetchWorklistItemDetails(String worklistItemId,String apiKey) {

		 resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchWorklistItemDetailsResource(),apiKey,worklistItemId)
				.then()
				.statusCode(200)
				.extract()
				.response();
		 return resp;
	}
	
	public static Response deleteWorklistItem(String deleteJson,String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.body(deleteJson)
					.when()
					.delete(Resources.deleteWorklistResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
		 return resp;
	}

	public static Response approve(String approveJson, String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.body(approveJson)
				.when()
				.post(Resources.approveResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
	
	public static Response deploy(String deployJson, String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.body(deployJson)
				.when()
				.post(Resources.approveResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}

	public static Response reject(String rejectJson, String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.body(rejectJson)
				.when()
				.post(Resources.approveResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}

	public static Response delegate(String delegateJson, String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.body(delegateJson)
				.when()
				.put(Resources.delegateResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}

}
