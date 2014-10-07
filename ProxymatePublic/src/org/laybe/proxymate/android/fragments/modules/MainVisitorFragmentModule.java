package org.laybe.proxymate.android.fragments.modules;

import javax.inject.Singleton;

import org.laybe.proxymate.android.activities.modules.MainActivityModule;
import org.laybe.proxymate.android.fragments.FragmentView;
import org.laybe.proxymate.android.fragments.MainFragment;
import org.laybe.proxymate.android.fragments.MainVisitorFragment;
import org.laybe.proxymate.android.fragments.presenters.FragmentPresenter;
import org.laybe.proxymate.android.fragments.presenters.MainFragmentPresenter;
import org.laybe.proxymate.android.fragments.presenters.MainVisitorFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module (
		injects = {
				MainVisitorFragment.class,
		},
		addsTo = MainActivityModule.class
		)
public class MainVisitorFragmentModule {

	private MainFragment view;

	private MainFragmentPresenter presenter;
	
	public MainVisitorFragmentModule(MainFragment view) {
		this.view = view;
	}

	@Provides @Singleton
	FragmentView provideFragmentView() {
		return view;
	}
	
	@Provides @Singleton
	MainFragment provideView() {
		return view;
	}

	@Provides @Singleton
	FragmentPresenter provideFragmentPresenter(MainVisitorFragmentPresenter presenter) {
		return initPresenter(presenter);
	}

	@Provides @Singleton
	MainFragmentPresenter provideMainFragmentPresenter(MainVisitorFragmentPresenter presenter) {
		return initPresenter(presenter);
	}

	MainVisitorFragmentPresenter initPresenter(MainVisitorFragmentPresenter presenter) {
		if (this.presenter == null)
			this.presenter = presenter;
		return presenter;
	}
}
