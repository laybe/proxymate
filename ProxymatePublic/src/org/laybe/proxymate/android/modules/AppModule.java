package org.laybe.proxymate.android.modules;

import javax.inject.Singleton;

import org.laybe.proxymate.android.Proxymate;
import org.laybe.proxymate.android.controllers.AppController;
import org.laybe.proxymate.android.controllers.AppControllerImpl;
import org.laybe.proxymate.android.events.EventBus;
import org.laybe.proxymate.android.events.EventBusImpl;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module (
		injects = Proxymate.class,
		includes = LoginModule.class
		)
public class AppModule {

	private final Proxymate app;

	public AppModule(Proxymate app) {
		this.app = app;
	}

	@Provides @Singleton
	AppController provideAppController(AppControllerImpl appController) {
		return appController;
	}
	
	@Provides @Singleton @ForApplication 
	Context provideApplicationContext() {
		return app;
	}

	@Provides @Singleton @ForApplication
	EventBus provideEventBus() {
		return new EventBusImpl(de.greenrobot.event.EventBus.getDefault());
	}

}
