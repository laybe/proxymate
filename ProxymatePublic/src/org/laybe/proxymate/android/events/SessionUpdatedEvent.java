package org.laybe.proxymate.android.events;

import org.laybe.proxymate.android.models.User;

public class SessionUpdatedEvent {

	private User user;
	
	public SessionUpdatedEvent(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
}
