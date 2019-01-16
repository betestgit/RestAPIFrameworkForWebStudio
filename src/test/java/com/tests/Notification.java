package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.NotificationPreference;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;
import io.restassured.response.Response;

public class Notification extends Login{
	
	public static Response resp;
	
	@Test
	public void fetchNotificationPreferences() {
		
		resp=NotificationPreference.fetchNotificationPreferences(apiKey);
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
	}
	
	@Test
	public void updateNotificationPreferences() {
		
		JsonFileReader file=new JsonFileReader();
		String notifyJson=file.read("updateNotificationPreference.json");
		
		resp=NotificationPreference.updateNotificationPreferences(notifyJson,apiKey);

		Assert.assertEquals(Utilities.rawToJson(resp).getString("response.responseMessage"), "Notification Preferences successfully updated");
	}
}
