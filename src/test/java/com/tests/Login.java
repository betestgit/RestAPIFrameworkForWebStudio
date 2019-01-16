package com.tests;

import org.testng.annotations.Test;

import com.api.Authentication;
import com.utilities.Utilities;
import com.utilities.Resources;

import java.io.FileInputStream;

import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


public class Login {
	
	public Properties prop;
	public static String apiKey;
	public static String username;
	public static String password;
	public static String forceLogin;
	public static Response resp;
	//String baseURI="http://localhost:8090";
	
	 @BeforeSuite
	  public void getData() {
		 prop=new Properties();
		 try {
			FileInputStream fis=new FileInputStream("D:\\RestAssuredDemo\\RMSRestAPI\\src\\test\\resources\\env.properties");
			prop.load(fis);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}  
		 
	  }
	 
	 @BeforeClass
	 public void login() {
		 getData();
		 RestAssured.baseURI=prop.getProperty("host");		 
		 username=prop.getProperty("username");
		 password=prop.getProperty("password");
		 forceLogin=prop.getProperty("forceLogin");
		 		 
		 resp=Authentication.login(username, password, forceLogin);
			
	     apiKey=Utilities.rawToJson(resp).getString("response.data.record[0].apiToken");
	     System.out.println("Inside login");
		 
	 
	 }
	 
	 @AfterClass
	 public void logout() {
		
		Authentication.logout(apiKey);
		System.out.println("Inside logout");
	 }
 

}
