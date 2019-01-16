package com.tests;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utilities.Resources;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuditTrail extends Login{
	
	public static Response resp;
	
	@Test
	public void fetchAuditTrail() throws FileNotFoundException {
		
		
		String userName=prop.getProperty("be.audittrail.username");
		
		
		resp=com.api.AuditTrail.fetchAuditTrail(userName, apiKey);
			
			System.out.println(resp.asString());
			
			//Need to write a logic to validate keywords in the response like CHECKOUT/LOGIN/CREATE
								
		
	}
	
	@Test
	public void downloadAuditTrail() throws FileNotFoundException {
		
		String userName=prop.getProperty("be.audittrail.username");
		System.out.println(userName);
		
		resp=com.api.AuditTrail.downloadAuditTrail(userName, apiKey);
		
		Assert.assertEquals(resp.asString().contains("[LOGIN]"), true);

	}


}
