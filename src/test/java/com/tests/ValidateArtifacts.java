package com.tests;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.Artifact;
import com.api.Project;
import com.utilities.Utilities;
import io.restassured.response.Response;

public class ValidateArtifacts extends Login{
	public static Response resp;
	
	@Test(priority=1)
	public void generateDeployable() throws FileNotFoundException {
				
	
		String projectName=prop.getProperty("projectname");
		
		resp=Project.generateDeployable(projectName, apiKey);
		
		Assert.assertEquals(Utilities.rawToJson(resp).get("response.responseMessage"),"Deployable generated successfully.");
		
		
	}
	
	
	
	
	@Test(priority=2)
	public void validateBuilderRTI() throws FileNotFoundException {
	
	
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.rtibuilder.validate.artifactpath");
		String artifactExtension=prop.getProperty("be.rtibuilder.validate.artifactextension");		
		String artifactType=prop.getProperty("be.rtibuilder.validate.artifacttype");
	
		resp=Artifact.validateArtifact(projectName, artifactPath, artifactExtension, artifactType, apiKey);
	
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
	
	
	}


	@Test(priority=3)
	public void validateViewRTI() throws FileNotFoundException {
	
	
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.rtiview.validate.artifactpath");
		String artifactExtension=prop.getProperty("be.rtiview.validate.artifactextension");		
		String artifactType=prop.getProperty("be.rtiview.validate.artifacttype");
	
		resp=Artifact.validateArtifact(projectName, artifactPath, artifactExtension, artifactType, apiKey);
	
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
	
	
	}

	@Test(priority=4)
	public void validateDT() throws FileNotFoundException {
	
	
		String projectName=prop.getProperty("projectname");
		String artifactPath=prop.getProperty("be.dt.validate.artifactpath");
		String artifactExtension=prop.getProperty("be.dt.validate.artifactextension");		
		String artifactType=prop.getProperty("be.dt.validate.artifacttype");
		
		resp=Artifact.validateArtifact(projectName, artifactPath, artifactExtension, artifactType, apiKey);
	
		Assert.assertEquals(Utilities.rawToJson(resp).getInt("response.status"), 0);
	
	}
	
}
