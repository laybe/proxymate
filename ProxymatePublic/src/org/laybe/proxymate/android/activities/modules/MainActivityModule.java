package org.laybe.proxymate.android.activities.modules;

import javax.inject.Singleton;

import org.laybe.proxymate.android.activities.ActivityView;
import org.laybe.proxymate.android.activities.MainActivity;
import org.laybe.proxymate.android.activities.MainActivityImpl;
import org.laybe.proxymate.android.activities.presenters.ActivityPresenter;
import org.laybe.proxymate.android.activities.presenters.MainActivityPresenter;
import org.laybe.proxymate.android.activities.presenters.MainActivityPresenterImpl;
import org.laybe.proxymate.android.fragments.MainLoggedInFragment;
import org.laybe.proxymate.android.fragments.MainVisitorFragment;
import org.laybe.proxymate.android.models.User;
import org.laybe.proxymate.android.modules.AppModule;
import org.laybe.proxymate.android.modules.ForActivity;

import android.app.Fragment;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module (
		injects = MainActivityImpl.class,
		addsTo = AppModule.class
		)
public class MainActivityModule {

	private MainActivityImpl view;

	public MainActivityModule(MainActivityImpl view) {
		this.view = view;
	}

	@Provides @Singleton @ForActivity Context provideActivityContext() {
		return view;
	}

	@Provides
	ActivityView provideActivityView() {
		return view;
	}
	
	@Provides @Singleton
	MainActivity provideView() {
		return view;
	}

	@Provides @Singleton
	ActivityPresenter providePresenter(MainActivityPresenter presenter) {
		return presenter;
	}

	@Provides @Singleton
	MainActivityPresenter providePresenter(MainActivityPresenterImpl presenter) {
		return presenter;
	}

	@Provides
	Fragment provideContentFragment(User user) {
		if (user == null)
			return new MainVisitorFragment();
		else
			return new MainLoggedInFragment();
	}

}
