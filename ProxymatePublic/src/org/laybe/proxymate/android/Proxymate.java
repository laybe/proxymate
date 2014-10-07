package org.laybe.proxymate.android;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.laybe.proxymate.android.controllers.AppController;
import org.laybe.proxymate.android.modules.AppModule;

import dagger.ObjectGraph;
import android.app.Application;

public class Proxymate extends Application {

	private ObjectGraph applicationGraph;

	@Inject
	AppController appController;

	@Override
	public void onCreate() {
		super.onCreate();

		applicationGraph = ObjectGraph.create(getModules().toArray());
		applicationGraph.inject(this);
	}


	/**
	 * A list of modules to use for the application graph. Subclasses can override this method to
	 * provide additional modules provided they call {@code super.getModules()}.
	 */
	protected List<Object> getModules() {
		return Arrays.<Object>asList(new AppModule(this));
	}

    public ObjectGraph createScopedGraph(Object... modules) {
        return applicationGraph.plus(modules);
    }

}
