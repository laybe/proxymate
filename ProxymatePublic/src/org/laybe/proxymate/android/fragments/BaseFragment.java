package org.laybe.proxymate.android.fragments;

import java.util.List;

import javax.inject.Inject;

import org.laybe.proxymate.android.Proxymate;
import org.laybe.proxymate.android.activities.BaseActivity;
import org.laybe.proxymate.android.fragments.presenters.FragmentPresenter;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements FragmentView {

	private ObjectGraph fragmentGraph;

	@Inject
	FragmentPresenter presenter;

	protected abstract List<Object> getModules();
	protected abstract int getViewId();
	protected abstract int getOptionsMenuId();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof BaseActivity)
			fragmentGraph = ((BaseActivity) activity).createScopedGraph(getModules().toArray());
		else
			fragmentGraph = ((Proxymate) activity.getApplication()).createScopedGraph(getModules().toArray());

		fragmentGraph.inject(this);

		presenter.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return presenter.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getViewId(), container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		presenter.onActivityCreated(savedInstanceState);
	}

	@Override
	public void setView(Bundle savedInstanceState) {
		ButterKnife.inject(this, getView());
	}

	@Override
	public void onStart() {
		super.onStart();
		presenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.onResume();
	}

	@Override
	public void onPause() {
		presenter.onPause();
		super.onPause();
	}

	@Override
	public void onStop() {
		presenter.onStop();
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		presenter.onDestroyView();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		presenter.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		presenter.onDetach();
		fragmentGraph = null;
		super.onDetach();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		presenter.onSaveInstanceState(outState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		presenter.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void inflateMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(getOptionsMenuId(), menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (presenter.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		presenter.onActivityResult(requestCode, resultCode, data);
	}

}
