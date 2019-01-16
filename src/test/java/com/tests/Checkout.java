package com.tests;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Lifecycle;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class Checkout extends Login {

//  public static Response resp;
	
  @Test
  public void checkout() throws FileNotFoundException {
		
		JsonFileReader file=new JsonFileReader();
		
		String checkoutJson=file.read("checkout.json");
		String projectName=prop.getProperty("projectname");
		
		resp=Lifecycle.checkout(checkoutJson, projectName, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Checkout successful");
	}
	
}
