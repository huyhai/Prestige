package com.g8.prestige;

import com.g8.prestige.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.g8.adapter.RoomAdapter;
import com.g8.common.ConstantValue;
import com.g8.common.Utilities;
import com.g8.control.UserFunctions;
import com.g8.interfaceapp.LocationCallBack;
import com.g8.libs.SessionManager;
import com.g8.push.CommonUtilities;
import com.g8.push.WakeLocker;
import com.g8.ui.HomeAc;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

public class SplashScreen extends Activity {

	/** The b active event. */
	protected boolean bActiveEvent = true;
	/** The i time display splash. */
	protected int iTimeDisplaySplash = 500;
	/** The is active app. */
	private boolean isActiveApp = false;
	private SessionManager session;
	AsyncTask<Void, Void, Void> mRegisterTask;
	private int currentApiVersion;

	// private MySQLiteHelper helper=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_layout);
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		session = new SessionManager(SplashScreen.this);
		Utilities.getGlobalVariable(SplashScreen.this).device_height = height;
		Utilities.getGlobalVariable(SplashScreen.this).device_width = width;

		// // Thread for displaying the SplashScreen
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int iCount = 0;
					while ((SplashScreen.this.bActiveEvent && (iCount < SplashScreen.this.iTimeDisplaySplash))
							|| !SplashScreen.this.isActiveApp) {
						sleep(100);
						if (SplashScreen.this.bActiveEvent) {
							iCount += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
					e.printStackTrace();
				} finally {
					SplashScreen.this.finish();

					final Intent itTab = new Intent().setClass(
							SplashScreen.this.getApplicationContext(),
							MainActivity.class);
					// itTab.putExtra("indexTab", 0);
					// itTab.putExtra(ConstantValue.INDEX_CLASS,
					// ConstantValue.LOGIN_ANIMATION);
					SplashScreen.this.startActivity(itTab);
				}
			}
		};
		currentApiVersion = android.os.Build.VERSION.SDK_INT;

		final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		// This work only for android 4.4+
		if (currentApiVersion >= 19) {

			getWindow().getDecorView().setSystemUiVisibility(flags);
			// Code below is for case when you press Volume up or Volume down.
			// Without this after pressing valume buttons navigation bar will
			// show up and don't hide
			final View decorView = getWindow().getDecorView();
			decorView
					.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

						@Override
						public void onSystemUiVisibilityChange(int visibility) {
							if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
								decorView.setSystemUiVisibility(flags);
							}
						}
					});
		}
		splashThread.start();
		// isActiveApp = true;
		try {
			doGcmRegistration();
		} catch (UnsupportedOperationException e) {
			callContact();
			// Toast.makeText(this, "not support", Toast.LENGTH_SHORT).show();
		}
	}

	public static String regId;

	private void doGcmRegistration() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		regId = GCMRegistrar.getRegistrationId(this);
		// GCMRegistrar.unregister(getApplicationContext());
		// regId = "1232";
		if (regId.equals("")) {
			GCMRegistrar.register(getApplicationContext(),
					CommonUtilities.SENDER_ID);
		}
		callContact();
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (currentApiVersion >= 19 && hasFocus) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	private void checkLogin() {
		if (session.isLoggedIn()) {
			HashMap<String, String> user = session.getUserLogin();
			String name = user.get(SessionManager.KEY_NAME);
			String pass = user.get(SessionManager.KEY_PASS);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("tag", "login"));
			params.add(new BasicNameValuePair("username", name));
			params.add(new BasicNameValuePair("pass", pass));
			UserFunctions.getInstance().callLoginUser(SplashScreen.this,
					params, false);
			// Log.v("HAI", name+pass);

		} else {
			isActiveApp = true;
		}

	}

	private void callContact() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "about"));
		UserFunctions.getInstance().callContact(SplashScreen.this, params,
				false);
	}

	private void getGRO() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "getGRO"));
		UserFunctions.getInstance().getGRO(SplashScreen.this, params, false);
	}
	private void getAllGRO() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "getAllGRO"));
		UserFunctions.getInstance().getAllGRO(SplashScreen.this, params, false);
	}
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(UserFunctions.LOGINUSER)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					Utilities.getGlobalVariable(SplashScreen.this).isLogin = true;
					isActiveApp = true;
				} else {
					Utilities.getGlobalVariable(SplashScreen.this).isLogin = false;
					isActiveApp = true;
				}
			}
			// if
			// (intent.getAction().equalsIgnoreCase(ServerUtilities.ADDDEVICE))
			// {
			// if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
			// callContact();
			// } else {
			// callContact();
			// // Utilities.showDialogNoBack(SplashScreen.this,
			// UserFunctions.getInstance().getMessage()).show();
			// }
			// }
			if (intent.getAction().equalsIgnoreCase(UserFunctions.CONTACT)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					// setLocation();
					getGRO();
				} else {
					// setLocation();
					getGRO();
					// Utilities.showDialogNoBack(SplashScreen.this,
					// UserFunctions.getInstance().getMessage()).show();
				}
			}
			if (intent.getAction().equalsIgnoreCase(UserFunctions.GETGRO)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					getAllGRO();
				} else {
					getAllGRO();

				}
			}
			if (intent.getAction().equalsIgnoreCase(UserFunctions.GETALLGRO)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					checkLogin();
				} else {
					checkLogin();

				}
			}
		}
	};

	public void setLocation() {
		Utilities.getGPSLocation(SplashScreen.this, locationCallback);
	}

	LocationCallBack locationCallback = new LocationCallBack() {
		@Override
		public void callback(LatLng position, String result) {
			Utilities.getGlobalVariable(SplashScreen.this).setLatlongDefault(
					position);
			Utilities.getGlobalVariable(SplashScreen.this).setAddressDefault(
					result);
			checkLogin();
		}
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			// unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		try {
			SplashScreen.this.unregisterReceiver(receiver);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		IntentFilter intentGetKeySend = new IntentFilter();
		intentGetKeySend.addAction(UserFunctions.LOGINUSER);
		intentGetKeySend.addAction(UserFunctions.CONTACT);
		intentGetKeySend.addAction(UserFunctions.GETGRO);
		intentGetKeySend.addAction(UserFunctions.GETALLGRO);
		SplashScreen.this.registerReceiver(receiver, intentGetKeySend);
	}

}
