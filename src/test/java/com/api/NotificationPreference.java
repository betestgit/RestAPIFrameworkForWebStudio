package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class NotificationPreference {
	public static Response resp;

	public static Response fetchNotificationPreferences(String apiKey) {
		resp=given()
				.accept(ContentType.JSON)
				.when()
				.get(Resources.fetchNotificationResource(),apiKey)
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		return resp;
	}

	public static Response updateNotificationPreferences(String notifyJson, String apiKey) {
		 resp=given()
					.contentType(ContentType.JSON)
					.body(notifyJson)
					.expect()
					.statusCode(200)
					.when()
					.put(Resources.updateNotificationResource(),apiKey)
					.then()
					.statusCode(200)
					.extract()
					.response();
		return resp;
	}
}
