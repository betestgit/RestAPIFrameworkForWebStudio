package com.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.Authentication;
import com.api.Lifecycle;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CheckoutUsingJdoe  {
	
	
	public Properties prop;
	public static String apiKey;
	public static String username;
	public static String password;
	public static String forceLogin;
	public static Response resp;


	  public void getData() {
		 prop=new Properties();
		 try {
			FileInputStream fis=new FileInputStream("D:\\RestAssuredDemo\\RMSRestAPI\\src\\test\\resources\\env.properties");
			prop.load(fis);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}  
		 
	  }
	 
	 @BeforeTest
	 public void login() {
		 getData();
		 RestAssured.baseURI=prop.getProperty("host");		 
		 username=prop.getProperty("username1");
		 password=prop.getProperty("password1");
		 forceLogin=prop.getProperty("forceLogin");
		 		 
		 resp=Authentication.login(username, password, forceLogin);
			
	     apiKey=Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");
	     
		 
	 
	 }
	 
	 @AfterTest
	 public void logout() {
		
		Authentication.logout(apiKey);
		
	 }
 

	
	  @Test
	  public void checkout() throws FileNotFoundException {
			
			JsonFileReader file=new JsonFileReader();
			
			String checkoutJson=file.read("checkout.json");
			String projectName=prop.getProperty("projectname");
			
			resp=Lifecycle.checkout(checkoutJson, projectName, apiKey);
			
			Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Checkout successful");
		}
	
}
