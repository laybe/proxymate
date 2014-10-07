package org.laybe.proxymate.android.fragments;

import java.util.Arrays;
import java.util.List;

import org.laybe.proxymate.R;
import org.laybe.proxymate.android.fragments.modules.MainLoggedInFragmentModule;

import android.app.Fragment;

public class MainLoggedInFragment extends MainBaseFragment {

	@Override
	protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainLoggedInFragmentModule(this));
	}
	
	@Override
	protected Fragment getFragmentForDrawerPosition(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new NewsFragment();
			break;
		case 1:
			fragment = new WhatNowFragment();
			break;
		case 2:
			fragment = new CalendarFragment();
			break;
		case 3:
			fragment = new ProfileFragment();
			break;
		default:
			break;
		}
		return fragment;
	}
	
	@Override
	protected int getDrawerItemsArrayId() {
		return R.array.logged_in_drawer_items;
	}

	@Override
	protected int getOptionsMenuId() {
		return R.menu.main_logged_in;
	}

}
