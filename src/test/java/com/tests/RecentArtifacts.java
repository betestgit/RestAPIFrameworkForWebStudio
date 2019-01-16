package com.tests;



import java.io.FileNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Workspace;
import com.utilities.Utilities;
import io.restassured.response.Response;

public class RecentArtifacts extends Login {

	public static Response resp;
	
	@Test(priority=1)
	public void addToRecentlyOpenedArtifact() throws FileNotFoundException {
	
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.recent.artifactPath");
		
		resp=Workspace.addToRecentlyOpenedArtifact(projectName,artifactPath,apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Add to Recently Opened Artifacts successful");
	
				
	}

	@Test(priority=2)
	public void fetchRecentlyOpenedArtifacts() {
		
		resp=Workspace.fetchRecentlyOpenedArtifacts(apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"),0);
	}
}
