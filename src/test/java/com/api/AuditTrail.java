package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuditTrail {

	public static Response resp;
	
	public static Response fetchAuditTrail(String userName,String apiKey) {
		
		resp=	given()
				.accept(ContentType.JSON)
				.param("userName", 	userName)
				.when()
				.get(Resources.fetchAuditTrailResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
			
		return resp;
	}
	
	public static Response downloadAuditTrail(String userName,String apiKey) {
		
		resp=given()
				.accept(ContentType.JSON)
				.param("userName", 	userName)
				.param("logFile", "true")
				.when()
				.get(Resources.fetchAuditTrailResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}
}
