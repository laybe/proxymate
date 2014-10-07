package org.laybe.proxymate.android.activities;

import java.util.Arrays;
import java.util.List;

import org.laybe.proxymate.R;
import org.laybe.proxymate.android.activities.modules.MainActivityModule;

public class MainActivityImpl extends BaseActivity implements MainActivity {
	
	@Override
	protected int getLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected int getContainer() {
		return R.id.main_layout;
	}

	@Override
	protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainActivityModule(this));
	}
	
}
