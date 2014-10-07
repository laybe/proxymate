package org.laybe.proxymate.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public interface FragmentView {

	View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	void inflateMenu(Menu menu, MenuInflater inflater);

	void setView(Bundle savedInstanceState);

	void setHasOptionsMenu(boolean hasOptionsMenu);

	void startActivityForResult(Intent intent, int requestCode);

}
