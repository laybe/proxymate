package org.laybe.proxymate.android.fragments.modules;

import javax.inject.Singleton;

import org.laybe.proxymate.android.activities.modules.MainActivityModule;
import org.laybe.proxymate.android.fragments.FragmentView;
import org.laybe.proxymate.android.fragments.MainFragment;
import org.laybe.proxymate.android.fragments.MainLoggedInFragment;
import org.laybe.proxymate.android.fragments.MainVisitorFragment;
import org.laybe.proxymate.android.fragments.presenters.FragmentPresenter;
import org.laybe.proxymate.android.fragments.presenters.MainFragmentPresenter;
import org.laybe.proxymate.android.fragments.presenters.MainLoggedInFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module (
		injects = {
				MainLoggedInFragment.class,
				MainVisitorFragment.class,
		},
		addsTo = MainActivityModule.class
		)
public class MainLoggedInFragmentModule {

	private MainFragment view;
	
	private MainFragmentPresenter presenter;

	public MainLoggedInFragmentModule(MainFragment view) {
		this.view = view;
	}

	@Provides @Singleton
	FragmentView provideFragmentView() {
		return view;
	}
	
	@Provides @Singleton
	MainFragment provideMainFragmentView() {
		return view;
	}

	@Provides @Singleton
	FragmentPresenter provideFragmentPresenter(MainLoggedInFragmentPresenter presenter) {
		return initPresenter(presenter);
	}

	@Provides @Singleton
	MainFragmentPresenter provideMainFragmentPresenter(MainLoggedInFragmentPresenter presenter) {
		return initPresenter(presenter);
	}
	
	MainLoggedInFragmentPresenter initPresenter(MainLoggedInFragmentPresenter presenter) {
		if (this.presenter == null)
			this.presenter = presenter;
		return presenter;		
	}

}
