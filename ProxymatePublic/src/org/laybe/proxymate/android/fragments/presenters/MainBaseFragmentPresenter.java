package org.laybe.proxymate.android.fragments.presenters;

import javax.inject.Inject;

import org.laybe.proxymate.android.fragments.MainFragment;
import org.laybe.proxymate.android.idmanager.MockSwitchProfileActivity;
import org.laybe.proxymate.android.models.AuthDetails;
import org.laybe.proxymate.android.services.LoginManager;
import org.laybe.proxymate.android.widgets.DrawerToggle.DrawerOpenedClosedListener;
import org.laybe.proxymate.android.widgets.DrawerTogglePresenter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public abstract class MainBaseFragmentPresenter extends BaseFragmentPresenter implements MainFragmentPresenter {
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			MainBaseFragmentPresenter.this.view.displayView(position);
		}
	}
	
	static final int SWITCH_PROFILE_REQUEST = 1;

	@Inject
	MainFragment view;
	
	@Inject
	LoginManager loginManager;
	
	protected DrawerTogglePresenter drawerTogglePresenter;

	void switchProfile() {
		Intent intent=new Intent(activityContext, MockSwitchProfileActivity.class);
		view.startActivityForResult(intent, SWITCH_PROFILE_REQUEST);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		view.setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
				
		view.setDrawerListItemClickListener(new DrawerItemClickListener());
		
		drawerTogglePresenter = view.getDrawerTogglePresenter();
		drawerTogglePresenter.setDrawerOpenedClosedListener(new DrawerOpenedClosedListener() {
			
			@Override
			public void onDrawerOpened(View drawerView) {
				view.setDrawerTitleAndHideActionBarIcons();
			}
			
			@Override
			public void onDrawerClosed(View drawerView) {
				view.setNewTitleAndShowActionBarIcons();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerTogglePresenter.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		// Pass any configuration change to the drawer toggle
		drawerTogglePresenter.onConfigurationChanged(newConfig);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == SWITCH_PROFILE_REQUEST) {

			if (resultCode == Activity.RESULT_OK) {
				String profileName = data.getStringExtra("profileSelected");
				String publicKey = data.getStringExtra("publicKey");
				String idAuthenticatorURI = data.getStringExtra("idAuthenticator");

				AuthDetails authenticationDetails = new AuthDetails(profileName, publicKey, idAuthenticatorURI);

				loginManager.login(authenticationDetails);
			}

			if (resultCode == Activity.RESULT_CANCELED) {
				// TODO
			}
		}
	}

}
