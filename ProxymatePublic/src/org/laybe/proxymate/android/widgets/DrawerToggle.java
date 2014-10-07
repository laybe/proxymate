package org.laybe.proxymate.android.widgets;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.MenuItem;
import android.view.View;

public class DrawerToggle implements DrawerListener, DrawerTogglePresenter {

	public static interface DrawerOpenedClosedListener {
		void onDrawerClosed(View drawerView);
		void onDrawerOpened(View drawerView);
	}
	
	private final ActionBarDrawerToggle toggle;
	
	private DrawerOpenedClosedListener drawerOpenedClosedListener;
	
	public DrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
		
		drawerOpenedClosedListener = new DrawerOpenedClosedListener() {
			@Override
			public void onDrawerClosed(View drawerView) {
			}
			@Override
			public void onDrawerOpened(View drawerView) {
			}
		};
		
		toggle = new ActionBarDrawerToggle(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes) {
			public void onDrawerClosed(View drawerView) {
				drawerOpenedClosedListener.onDrawerClosed(drawerView);
			}

			public void onDrawerOpened(View drawerView) {
				drawerOpenedClosedListener.onDrawerOpened(drawerView);
			}
		};
	}
	
	@Override
	public void setDrawerOpenedClosedListener(DrawerOpenedClosedListener listener) {
		this.drawerOpenedClosedListener = listener;
	}

	@Override
	public void syncState() {
		toggle.syncState();		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		toggle.onConfigurationChanged(newConfig);		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return toggle.onOptionsItemSelected(item);
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		toggle.onDrawerSlide(drawerView, slideOffset);
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		toggle.onDrawerOpened(drawerView);
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		toggle.onDrawerClosed(drawerView);
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		toggle.onDrawerStateChanged(newState);
	}
	
}
