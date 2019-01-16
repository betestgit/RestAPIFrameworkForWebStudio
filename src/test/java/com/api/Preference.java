package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Preference {
	
	public static Response resp;

	public static Response updatePreferences(String updateJson, String apiKey) {
		 resp=given()
					.contentType(ContentType.JSON)
					.body(updateJson)
					.when()
					.put(Resources.updatePreferencesResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();		
					
		return resp;
	}

	public static Response fetchPreferences(String apiKey) {
		 resp=given()
					.accept(ContentType.JSON)
					.when()
					.get(Resources.fetchPreferencesResource(),apiKey)
					.then()
					.extract()
					.response();
		return resp;
	}
	
}
