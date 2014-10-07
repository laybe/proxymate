package org.laybe.proxymate.android.modules;

import javax.inject.Singleton;

import org.laybe.proxymate.android.events.EventBus;
import org.laybe.proxymate.android.models.User;
import org.laybe.proxymate.android.services.LoginManager;
import org.laybe.proxymate.android.services.SessionManager;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module (
		complete = false,
		library = true
		)
public class LoginModule {

	@Provides @Singleton
	SessionManager provideSessionManager(@ForApplication Context context, @ForApplication EventBus eventBus) {
		return new SessionManager(context, eventBus);
	}
	
	@Provides @Singleton
	LoginManager provideLoginManager(@ForApplication Context context, SessionManager sessionManager) {
		return new LoginManager(context, sessionManager);
	}
	
	@Provides
	User provideUser(LoginManager loginManager) {
		return loginManager.getUser();
	}

}
