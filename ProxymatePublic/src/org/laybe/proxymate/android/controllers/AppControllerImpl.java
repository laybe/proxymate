package org.laybe.proxymate.android.controllers;

import javax.inject.Inject;

import org.laybe.proxymate.android.events.EventBus;
import org.laybe.proxymate.android.events.RestartCurrentAndTerminateOthersActivitiesEvent;
import org.laybe.proxymate.android.events.SessionUpdatedEvent;
import org.laybe.proxymate.android.modules.ForApplication;

public class AppControllerImpl implements AppController {

	private EventBus eventBus;

	@Inject
	public AppControllerImpl(@ForApplication EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(this);
	}
	
	public void onEvent(SessionUpdatedEvent event) {
		doRestartCurrentActivityAndTerminateOthers();
	}
	
	private void doRestartCurrentActivityAndTerminateOthers() {
		eventBus.post(new RestartCurrentAndTerminateOthersActivitiesEvent());
	}
}
