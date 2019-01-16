package com.api;

import static io.restassured.RestAssured.given;

import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Authentication {
	
	public static Response resp;
	
	public static Response login(String username,String password, String forceLogin) {
		
		  resp=given().
				 accept(ContentType.JSON).
				 param("password", 	password).
				 param("forceLogin", forceLogin).
				 when().
				 get(Resources.getLoginResource(),username).		 
				 then().
				 assertThat().
				 statusCode(200).
				 extract().
				 response();
		return resp;
	}
	
	public static Response logout(String apiKey) {
		
		resp= given().
			accept(ContentType.JSON).
			when().
			get(Resources.getLogoutResource(),apiKey).
			then().
			statusCode(200)
			.extract()
			.response();
		 return resp;
		 
	}

}
