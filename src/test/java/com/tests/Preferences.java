package com.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Preference;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;

import io.restassured.response.Response;

public class Preferences extends Login {
	
	public static Response resp;
	

	@Test(priority=1)
	public void updatePreferences() throws IOException {
		
		JsonFileReader file=new JsonFileReader();
		String updateJson=file.read("updatePreferences.json");
		
		resp=Preference.updatePreferences(updateJson,apiKey);
		
	Assert.assertEquals(Utilities.rawToJson(resp).getString("response.responseMessage"),"User Preferences successfully updated");
	}
	
	@Test(priority=2)
	public void fetchPreferences() {
		
		resp=Preference.fetchPreferences(apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
				
		
	}
}
