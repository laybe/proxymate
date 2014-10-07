package org.laybe.proxymate.android.fragments.presenters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public interface FragmentPresenter {

	void onAttach(Activity activity);

	void onCreate(Bundle savedInstanceState);
	
	View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	void onActivityCreated(Bundle savedInstanceState);

	void onViewStateRestored();
	
	void onStart();
	
	void onResume();
	
	void onPause();
	
	void onStop();
	
	void onDestroyView();
	
	void onDestroy();
	
	void onDetach();

	void onSaveInstanceState(Bundle outState);

	void onConfigurationChanged(Configuration newConfig);

	void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

	boolean onOptionsItemSelected(MenuItem item);
	
	void onActivityResult(int requestCode, int resultCode, Intent data);

}
