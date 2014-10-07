package org.laybe.proxymate.android.fragments;

import org.laybe.proxymate.android.widgets.DrawerTogglePresenter;

import android.widget.AdapterView.OnItemClickListener;

public interface MainFragment extends FragmentView {

	DrawerTogglePresenter getDrawerTogglePresenter();

	void displayView(int position);

	void setDrawerListItemClickListener(OnItemClickListener listener);

	void setDrawerTitleAndHideActionBarIcons();

	void setNewTitleAndShowActionBarIcons();

}
