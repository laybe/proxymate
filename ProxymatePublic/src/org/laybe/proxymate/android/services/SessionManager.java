package org.laybe.proxymate.android.services;

import javax.inject.Inject;

import org.laybe.proxymate.android.events.EventBus;
import org.laybe.proxymate.android.events.SessionUpdatedEvent;
import org.laybe.proxymate.android.models.User;
import org.laybe.proxymate.android.modules.ForApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	public static final String SHARED_PREFERENCES_NAME = "org.laybe.proxymate.PREFERENCES";

	public static final String SHARED_PREFERENCES_TAG_UUID = "uuid";
	public static final String SHARED_PREFERENCES_TAG_USER_NAME = "user_name";
	public static final String SHARED_PREFERENCES_TAG_SESSION_ID = "session_id";

	private final Context context;
	private final EventBus eventBus;
	
	@Inject
	public SessionManager(@ForApplication Context context, EventBus eventBus) {
		this.context = context;
		this.eventBus = eventBus;
	}

	User getUser() {
		
		User user = null;
		
		SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 
				Context.MODE_PRIVATE);
		
		String uuid = sharedpreferences.getString(SHARED_PREFERENCES_TAG_UUID, null);
		String userName = sharedpreferences.getString(SHARED_PREFERENCES_TAG_USER_NAME, null);
		String sessionId = sharedpreferences.getString(SHARED_PREFERENCES_TAG_SESSION_ID, null);
		
		if (uuid != null) {
			user = new User();
			user.setUuid(uuid);
			user.setName(userName);
			user.setSessionId(sessionId);
		}

		return user;
	}
	
	void setUser(User user) {

		// TODO If other user is already logged in, notify listeners that user has changed (so that they can save old user data).

		String sessionId = user.getSessionId();

		if (sessionId != null && !sessionId.trim().equals(""))	
			setNewUser(user);
		else
			clearUser();
	}

	void clearUser() {

		SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 
				Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();

		editor.remove(SHARED_PREFERENCES_TAG_UUID);
		editor.remove(SHARED_PREFERENCES_TAG_USER_NAME);
		editor.remove(SHARED_PREFERENCES_TAG_SESSION_ID);

		editor.commit();
		
		notifySessionUpdateToObservers(null);
	}

	private void setNewUser(User user) {
	
		String uuid = user.getUuid();
		String userName = user.getName();
		String sessionId = user.getSessionId();

		SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 
				Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();

		editor.putString(SHARED_PREFERENCES_TAG_UUID, uuid);
		editor.putString(SHARED_PREFERENCES_TAG_USER_NAME, userName);
		editor.putString(SHARED_PREFERENCES_TAG_SESSION_ID, sessionId);
		
		editor.commit();
		
		notifySessionUpdateToObservers(user);
	}
	
	private void notifySessionUpdateToObservers(User user) {
		eventBus.post(new SessionUpdatedEvent(user));
	}
}
