package org.laybe.proxymate.android.fragments.presenters;

import javax.inject.Inject;

import org.laybe.proxymate.android.events.EventBus;
import org.laybe.proxymate.android.fragments.FragmentView;
import org.laybe.proxymate.android.models.User;
import org.laybe.proxymate.android.modules.ForActivity;
import org.laybe.proxymate.android.modules.ForApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragmentPresenter implements FragmentPresenter {

	@Inject
	FragmentView view;

	@Inject
	User user;

	@Inject @ForApplication
	EventBus appEventBus;

	@Inject @ForActivity
	Context activityContext;

	@Override
	public void onAttach(Activity activity) {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return view.inflateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		view.setView(savedInstanceState);
	}

	@Override
	public void onViewStateRestored() {
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroyView() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onDetach() {
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {		
        view.inflateMenu(menu, inflater);
	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
