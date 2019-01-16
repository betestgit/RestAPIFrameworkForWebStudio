package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.utilities.Utilities;
import com.utilities.Resources;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ArtifactHistory  extends Login{
	
	@Test
	public void fetchArtifactHistory() {
				
		String projectName="CreditCardApplication";
		String artifactPath="/Rule_Templates/SpecialOffersRTI";
		String artifactExtension="ruletemplateinstance";		
				
		Response resp=given()
		.accept(ContentType.JSON)
		.param("projectName", 	projectName)
		.param("artifactPath", artifactPath)
		.param("artifactExtension", artifactExtension)
		.when()
		.get(Resources.historyResource(),apiKey)
		.then()
		.statusCode(200)
		.extract()
		.response();
		
		System.out.println(resp.asString());
	Assert.assertNotNull(Utilities.rawToJson(resp).get("response.data.record[0].revisionId"));
		

	}
	
}
