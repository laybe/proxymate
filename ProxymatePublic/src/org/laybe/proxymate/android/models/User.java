package org.laybe.proxymate.android.models;

public class User {

	/**
	 * Universal Unique Identifier
	 */
	private String uuid;

	private String name;
	
	private String sessionId;
	
	public User() {
	}
	
	public User(String uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
