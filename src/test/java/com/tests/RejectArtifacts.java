package com.tests;


import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.api.Lifecycle;
import com.api.Worklist;
import com.utilities.JsonFileReader;
import com.utilities.Utilities;


public class RejectArtifacts extends Login{
  
	
	@Test(priority=1)
	public void commitArtifacts() throws FileNotFoundException {
			
		JsonFileReader file=new JsonFileReader();
		String commitJson=file.read("commitForReject.json");
		String projectName=prop.getProperty("be.rtibuilder.projectname");
		
		resp=Lifecycle.commit(commitJson, projectName, apiKey);
		
	Assert.assertEquals( Utilities.rawToJson(resp).get("response.responseMessage"),"Checkin Sucessful with Revision Id [10001]");
	}
	

	
	@Test(priority=2)
	public void fetchWorklistItems() {
		
		resp=Worklist.getWorklistItems(apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.data.record[0].revisionId"),10001);
	}
	
	@Test(priority=3)
	public void reject() {
		
		JsonFileReader file=new JsonFileReader();
		String rejectJson=file.read("reject.json");
		
		resp=Worklist.reject(rejectJson,apiKey);
				
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
		
	}
}
