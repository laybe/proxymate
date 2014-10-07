package org.laybe.proxymate.android.fragments.presenters;

import org.laybe.proxymate.R;
import android.view.MenuItem;

public class MainVisitorFragmentPresenter extends MainBaseFragmentPresenter {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerTogglePresenter.onOptionsItemSelected(item))
			return true;
		
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_login:
			login();
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void login() {
		switchProfile();
	}
}
