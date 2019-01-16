package com.constant;

public enum NotificationProperties {
	
	//ROOT_LOCATION ("examples/standard/WebStudio"),
	
	ROOT_LOCATION_XPATH("ws.scs.rootURL"),
	
	NOTIFY_ENABLED_XPATH("ws.notify.enabled"),
	
	NOTIFY_MAIL_DOMAIN_XPATH("ws.notify.mail.domain"),

	CC_EMAIL_XPATH("ws.notify.mail.receiver.cc.emails"),

	MAIL_PROTOCOL_XPATH("ws.notify.prop.MAIL_PROTOCOL"),
	
	SERVER_HOST_XPATH("ws.notify.prop.MAIL_SERVER_HOST"),
	
	SERVER_PORT_XPATH("ws.notify.prop.MAIL_SERVER_PORT"),
	
	SENDER_EMAIL_XPATH("ws.notify.prop.SENDER_EMAIL"),
	
	SENDER_USERNAME_XPATH("ws.notify.prop.SENDER_USERNAME"),
	
	SENDER_PASSWORD_XPATH("ws.notify.prop.SENDER_PASSWORD");
	
	String propertyName;
	
	NotificationProperties(final String propertyName) {
		
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
