package org.laybe.proxymate.android.activities.presenters;

import android.os.Bundle;

public interface ActivityPresenter {

	void onCreate(Bundle savedInstanceState);
	
	void onRestart();
	
	void onStart();
	
	void onResume();
	
	void onPause();
	
	void onStop();
	
	void onDestroy();
	
	void onSaveInstanceState(Bundle outState);

}
