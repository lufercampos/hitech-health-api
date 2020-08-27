package com.hitechhealth.vo;

public class ConfigAppVO {
	
	private int sessionTime = 20;
	private String urlClient = null;
	
	public int getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(int sessionTime) {
		this.sessionTime = sessionTime;
	}

	public String getUrlClient() {
		return urlClient;
	}

	public void setUrlClient(String urlClient) {
		this.urlClient = urlClient;
	}
	
}
