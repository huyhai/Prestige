package com.g8.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		/*
		 * start service khi khoi dong app
		 */
		Log.v("HAI", "onReceiv111e");
		 Intent myIntent = new Intent(context, MyService.class);
		 context.startService(myIntent);
		Log.v("HAI", "onReceive");

	}
}