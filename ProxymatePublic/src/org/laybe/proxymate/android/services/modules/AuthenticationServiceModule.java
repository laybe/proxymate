package org.laybe.proxymate.android.services.modules;

import org.laybe.proxymate.android.modules.AppModule;
import org.laybe.proxymate.android.services.AuthenticationService;

import dagger.Module;

@Module (
		injects = AuthenticationService.class,
		addsTo = AppModule.class)
public class AuthenticationServiceModule {
}
