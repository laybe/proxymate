package org.laybe.proxymate.android.utils;

import android.os.Looper;

public class Utils {

	public static boolean isMainThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}
}
