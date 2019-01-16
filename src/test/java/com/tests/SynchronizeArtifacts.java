package com.tests;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.Authentication;
import com.api.Lifecycle;
import com.api.Project;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import com.utilities.Resources;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SynchronizeArtifacts{
	
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
	 
	
	
	@Test(priority=1)
	public void listArtifactsNeedsSynchronization() throws FileNotFoundException {

		
		JsonFileReader file=new JsonFileReader();
		String projectJson=file.read("input.json");
		
		String projectName=prop.getProperty("projectname");
		
		
		resp=Project.listArtifactsNeedsSynchronization(projectJson,projectName,apiKey);	
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[0].artifactPath"),"/Rule_Templates/Applicant_Prescreen_RTI");
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[1].artifactPath"),"/Rule_Templates/SpecialOffersRTI");
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.data.record[2].artifactPath"),"/Virtual_RF/Applicant_Simple_Test");
			
		
	}
 
	@Test(priority=2)
	public void synchronize() throws FileNotFoundException {

		JsonFileReader file=new JsonFileReader();
		String synchronizeJson=file.read("synchronize.json");
		
		String projectName=prop.getProperty("projectname");
		
		resp=Lifecycle.synchronize(synchronizeJson,projectName,apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"),0);
	}
	
	 @AfterTest
	 public void logout() {
		
		Authentication.logout(apiKey);
		
	 }
}
