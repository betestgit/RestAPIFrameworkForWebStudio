package com.utilities;

public class Resources {

	public static String getLoginResource() {
		
		String loginResource="/ws/api/{userName}/login.json";
		return loginResource;
	}
	public static String getLogoutResource() {
		
		String logoutResource="/ws/api/{token}/logout.json";
		return logoutResource;
	}
	
	public static String updatePreferencesResource() {
		String updatePreferencesResource="/ws/api/{token}/preferences/update.json";
		return updatePreferencesResource;
	}
	
	public static String fetchPreferencesResource() {
		String fetchPreferencesResource="/ws/api/{token}/preferences.json";
		return fetchPreferencesResource;
	}
	
	public static String fetchAuditTrailResource() {
		String fetchAuditTrailResource="/ws/api/{token}/auditTrail.json";
		return fetchAuditTrailResource;
	}
	
	public static String fetchGroupResource() {
		String fetchGroupResource="/ws/api/{apiToken}/groups.json";
		return fetchGroupResource;
	}
	
	
	public static String addGroupResource() {
		String addGroupResource="/ws/api/{token}/groups/{groupName}/add.json";
		return addGroupResource;
	}
	
	public static String fetchGroupArtifactResource() {
		String fetchGroupArtifactResource="/ws/api/{token}/groups/{groupName}/artifacts.json";
		return fetchGroupArtifactResource;
	}
	public static String deleteGroupResource() {
		String deleteGroupResource="/ws/api/{token}/groups/{groupName}/delete.json";
		return deleteGroupResource;
	}
	public static String fetchNotificationResource() {
		String fetchNotificationResource="/ws/api/{apiToken}/notifyPreferences.json";
		return fetchNotificationResource;
	}
	public static String updateNotificationResource() {
		String updateNotificationResource="/ws/api/{apiToken}/notifyPreferences/update.json";
		return updateNotificationResource;
	}
	public static String checkUserEntryResource() {
		String checkUserEntryResource="/ws/api/{apiToken}/users/{username}.json";
		return checkUserEntryResource;
	}
	public static String fetchAuthenticationResource() {
		String fetchAuthenticationResource="/ws/api/{apiToken}/userData.json";
		return fetchAuthenticationResource;
	}
	
	public static String fetchACLResource() {
		String fetchACLResource="/ws/api/{apiToken}/projects/{projectName}/aclData.json";
		return fetchACLResource;
	}
	
	public static String updateACLResource() {
		String updateACLResource="/ws/api/{apiToken}/aclData/update.json";
		return updateACLResource;
		
	}
	
	public static String updateAuthenticationDetailsResource() {
		String updateAuthenticationDetailsResource="/ws/api/{apiToken}/userData/update.json";
		return updateAuthenticationDetailsResource;
	}
	
	public static String checkoutResource() {
		String checkoutResource="/ws/api/{token}/projects/{projectName}/checkout.json";
		return checkoutResource;
	}
	
	public static String fetchArtifactResource() {
		String fetchArtifactResource="/ws/api/{token}/artifact.json";
		return fetchArtifactResource;
	}
	
	public static String saveArtifactResource() {
		String saveArtifactResource="/ws/api/{token}/artifact/save.json";
		return saveArtifactResource;
	}
	
	public static String validateResource() {
		String validateResource="/ws/api/{token}/artifact/validate.json";
		return validateResource;
	}
	public static String commitableArtifactsResource() {
		String commitableArtifactsResource="/ws/api/{apiToken}/projects/{projectName}/committables.json";
		return commitableArtifactsResource;
	}
	public static String commitResource() {
		String commitResource="/ws/api/{token}/projects/{projectName}/commit.json";
		return commitResource;
	}
	public static String fetchWorklistResource() {
		String fetchWorklistResource="/ws/api/{token}/worklist.json";
		return fetchWorklistResource;
	}
	
	public static String approveResource() {
		String approveResource="/ws/api/{token}/worklist/statusChange.json";
		return approveResource;
	}
	public static String deployResource() {
		String deployResource="/ws/api/{token}/worklist/statusChange.json";
		return deployResource;
	}
	
	public static String lockResource() {
		String lockResource="/ws/api/{token}/artifact/lock.json";
		return lockResource;
	}
	
	public static String unlockResource() {
		String unlockResource="/ws/api/{token}/artifact/unlock.json";
		return unlockResource;
	}
	public static String manageLocksResource() {
		String manageLocksResource="/ws/api/{token}/projects/{projectName}/artifactLocks.json";
		return manageLocksResource;
	}
	public static String synchronizeResource() {
		String synchronizeResource="/ws/api/{token}/projects/{projectName}/synchronize.json";
		return synchronizeResource;
	}
	public static String listArtifactsForSynchronizationResource() {
		String listArtifactsForSynchronizationResource ="/ws/api/{token}/projects/{projectName}/synchronizables.json";
		return listArtifactsForSynchronizationResource;
	}
	public static String exportResource() {
		String exportResource="/ws/api/{token}/artifact/export.json";
		return exportResource;
	}
	public static String importDTResource() {
		String importDTResource="/ws/api/{token}/decisiontable/import.json";
		return importDTResource;
	}
	
	public static String generateDeployableResource() {
		String generateDeployableResource="/ws/api/{token}/projects/{projectName}/generateDeployable.json";
		return generateDeployableResource;
	}
	public static String renameResource() {
		String renameResource="/ws/api/{token}/artifact/rename.json";
		return renameResource;
	}
	
	public static String revertResource() {
		String revertResource="/ws/api/{token}/projects/{projectName}/revert.json";
		return revertResource;
	}
	public static String recentlyOpenedArtifactResource() {
		String recentlyOpenedArtifactResource="/ws/api/{token}/recentlyOpened.json";
		return recentlyOpenedArtifactResource;
	}
	public static String addToRecentlyItemsResource() {
		String addToRecentlyItemsResource="/ws/api/{token}/recentlyOpened/add.json";
		return addToRecentlyItemsResource; 
	}
	public static String historyResource() {
		String historyResource="/ws/api/{token}/artifact/history.json";
		return historyResource;
	}
	public static String deleteWorklistResource() {
		String deleteWorklistResource="/ws/api/{token}/worklist/delete.json";
		return deleteWorklistResource;
	}
	public static String deleteArtifactResource() {
		String deleteArtifactResource="/ws/api/{token}/artifact/delete.json";
		return deleteArtifactResource;
	}
	public static String fetchWorklistItemDetailsResource() {
		String fetchWorklistItemDetailsResource="/ws/api/{token}/worklist/{worlistItemId}/details.json";
		return fetchWorklistItemDetailsResource;
	}
	public static String listAvailableProjects() {
		return "/ws/api/{token}/projects.json";
	}
	public static String listAvailableArtifactsOfProject() {
		return "/ws/api/{token}/projects/{projectName}/artifacts.json";
	}
	public static String showArgumentResource() {
		
		return "/ws/api/{token}/decisiontable/arguments";
	}
	
	public static String analyzerValuesResource() {
		return "/ws/api/{token}/decisiontable/analyzerValues.json";
	}
	
	public static String analyzeDTResource() {
		
		return "/ws/api/{token}/decisiontable/analyze.json";
	}
	
	public static String compareArtifactResource() {
		return "/ws/api/{token}/artifact/compare.json";
	}
	public static String artifactHistoryResource() {
		
		return "/ws/api/{token}/artifact/history.json";
	}
	public static String analyzerCoverageResource() {
		
		return "/ws/api/{token}/decisiontable/coverage";
	}
	public static String listCheckedoutProjectResource() {
		
		return "ws/api/{token}/workspace.json";
	}
	public static String fetchUserRolesForDelegationResource() {
		
		return "/ws/api/{token}/delegateRoles.json";
	}
	public static String listProjectForLockManagementResource() {
		
		return "/ws/api/{token}/projectsLockMgmt.json";
	}
	public static String checkDeployableResource() {
	
		return "/ws/api/{token}/checkDeployable.json";
	}
	
	public static String aboutResource() {
		return "/ws/api/{token}/about.json";
	}
	public static String delegateResource() {
	
		return "/ws/api/{token}/worklist/delegate.json";
	}
	public static String fetchDeploymentConfigResource() {
	
		return "/ws/api/{token}/deployConfigs.json";
	}
	public static String addDeploymentConfigResource() {
		
		return "/ws/api/{token}/deployConfigs/add.json";
	}
	public static String updateDeploymentConfigResource() {
		
		return "/ws/api/{token}/deployConfigs/update.json";
	}
	
	public static String deleteDeploymentConfigResource() {
		return "/ws/api/{token}/deployConfigs/delete.json";
	}
}

