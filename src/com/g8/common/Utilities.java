package com.g8.common;


import com.g8.libs.WorldUtilities;
import com.g8.prestige.G8App;

import android.app.Activity;

/**
 * The Class Utilities.
 */
public final class Utilities extends WorldUtilities {

	public static G8App getGlobalVariable(final Activity act) {

		try {
			if (act != null) {
				return ((G8App) act.getApplication());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
