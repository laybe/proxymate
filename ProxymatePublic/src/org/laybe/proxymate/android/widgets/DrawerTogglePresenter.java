package org.laybe.proxymate.android.widgets;

import org.laybe.proxymate.android.widgets.DrawerToggle.DrawerOpenedClosedListener;

import android.content.res.Configuration;
import android.view.MenuItem;

public interface DrawerTogglePresenter {

	void syncState();
	
	void onConfigurationChanged(Configuration newConfig);
	
	boolean onOptionsItemSelected(MenuItem item);
	
	void setDrawerOpenedClosedListener(DrawerOpenedClosedListener listener);
	
}
