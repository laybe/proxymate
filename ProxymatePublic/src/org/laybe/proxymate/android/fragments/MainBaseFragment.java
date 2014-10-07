package org.laybe.proxymate.android.fragments;

import javax.inject.Inject;

import org.laybe.proxymate.R;
import org.laybe.proxymate.android.fragments.presenters.MainFragmentPresenter;
import org.laybe.proxymate.android.widgets.DrawerToggle;
import org.laybe.proxymate.android.widgets.DrawerTogglePresenter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.InjectView;

public abstract class MainBaseFragment extends BaseFragment implements MainFragment {

	@Inject
	MainFragmentPresenter presenter;

	@InjectView(R.id.drawer_layout)
	DrawerLayout drawerLayout;

	@InjectView(R.id.left_drawer)
	ListView drawerList;

	// nav drawer title
	private CharSequence drawerTitle;

	// used to store app title
	private CharSequence title;
		
	protected String[] drawerItemsTitles;
	protected DrawerToggle drawerToggle;
	
	protected abstract Fragment getFragmentForDrawerPosition(int position);
	protected abstract int getDrawerItemsArrayId();
	
	private void setTitle(CharSequence title) {
		this.title = title;
		getActivity().setTitle(title);
	}
		
	@Override
	public DrawerTogglePresenter getDrawerTogglePresenter() {
		return drawerToggle;
	}
	
	@Override
	public int getViewId() {
		return R.layout.fragment_main;
	}

	@Override
	public void setView(Bundle savedInstanceState) {
		super.setView(savedInstanceState);
		
		final Activity activity = getActivity();

		title = activity.getTitle();
		drawerTitle = activity.getTitle();

		drawerItemsTitles = getResources().getStringArray(getDrawerItemsArrayId());

		// Set the adapter for the list view
		drawerList.setAdapter(new ArrayAdapter<String>(activity,
				R.layout.list_item_drawer, drawerItemsTitles));

		// enabling action bar app icon and behaving it as toggle button
		activity.getActionBar().setDisplayHomeAsUpEnabled(true);
		activity.getActionBar().setHomeButtonEnabled(true);
		
		drawerToggle = new DrawerToggle(activity, drawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				);
		drawerLayout.setDrawerListener(drawerToggle);
		
		if (savedInstanceState == null)
			// on first time display view for first nav item
			displayView(0);
		
	}

	/** Diplaying fragment view for selected nav drawer list item */
	@Override
	public void displayView(int position) {

		// update the main content by replacing fragments
		Fragment fragment = getFragmentForDrawerPosition(position);

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			drawerList.setItemChecked(position, true);
			drawerList.setSelection(position);
			setTitle(drawerItemsTitles[position]);
			drawerLayout.closeDrawer(drawerList);
		} else {
			// error in creating fragment
			Log.e(MainBaseFragment.class.getName(), "Error in creating fragment");
		}
	}
	
	@Override
	public void setDrawerListItemClickListener(OnItemClickListener listener) {
		// Set the list's click listener
		drawerList.setOnItemClickListener(listener);
	}

	@Override
	public void setDrawerTitleAndHideActionBarIcons() {
		getActivity().setTitle(drawerTitle);
		// calling onPrepareOptionsMenu() to hide action bar icons
		getActivity().invalidateOptionsMenu();
	}
	
	@Override
	public void setNewTitleAndShowActionBarIcons() {
		getActivity().setTitle(title);
		// calling onPrepareOptionsMenu() to show action bar icons
		getActivity().invalidateOptionsMenu();
	}

}
