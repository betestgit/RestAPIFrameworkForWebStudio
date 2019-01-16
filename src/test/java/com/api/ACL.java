package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ACL {
	
	public static Response resp;
	
	public static Response checkUserEntry(String userName,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				//.param("subscriptionId", subscriptionId)
				.when()
				.get(Resources.checkUserEntryResource(),apiKey,userName)	
				.then()
				.statusCode(200)
				.extract()
				.response();	
		return resp;
	}
	
	
	public static Response fetchAuthenticationDetails(String apiKey) {
		
		 resp=given()
					.accept(ContentType.JSON)
					.when()
					.get(Resources.fetchAuthenticationResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
		 
		 return resp;
	}
	
	public static Response updateAuthenticationDetails(String updateAuthenticationJson,String apiKey) {
		 resp=given()
				.contentType(ContentType.JSON)
				.body(updateAuthenticationJson)
				.when()
				.put(Resources.updateAuthenticationDetailsResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		 
		 return resp;
			
	}
	
	public static Response fetchACL(String projectName,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchACLResource(),apiKey,projectName)
				.then()
				.statusCode(200)
				.extract()
				.response();
		return resp;
	}
	
	public static Response updateACL(String updateJson,String apiKey) {
		
		resp=given()
				.contentType(ContentType.JSON)
				.body(updateJson)
				.when()
				.put(Resources.updateACLResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
}
