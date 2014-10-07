package org.laybe.proxymate.android.fragments.presenters;

import org.laybe.proxymate.R;

import android.view.MenuItem;

public class MainLoggedInFragmentPresenter extends MainBaseFragmentPresenter {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (drawerTogglePresenter.onOptionsItemSelected(item))
			return true;
		
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_switch_profile:
			switchProfile();
			return true;
		case R.id.action_logout:
			logout();
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	void logout() {
		loginManager.logout();
	}
	
}
