package com.g8.services;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	/*
	 * class Service lay log lat
	 */
	// string TAG de ghi log
	private static final String TAG = "HAI";
	double log;
	double lat;
	// new 1 timer
	Timer time = new Timer();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");

		/*
		 * star Asysctask lay longlat, day la thread chay ngam ko dung UI nen
		 * phai dung AsysTask
		 */
		new MyAsyncTask().execute("");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		// because we do not want to stop the service unless we explicitly say
		// so.

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		// time.cancel();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Log.d(TAG, "onStart");

	}

	private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

		@Override
		protected Double doInBackground(String... params) {
			time.scheduleAtFixedRate(task, 100, 2000);
			// lay log lat sau moi 300000 milisecon (5 phut)
			return null;
		}

		protected void onPostExecute(Double result) {
		}

		protected void onProgressUpdate(Integer... progress) {
		}

	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			Log.v("HAI", "in thread");
			// ham lay log lat hien tai cua nguoi dung
//			if (Utilities.checkInternetConnection(MyService.this)) {
//				LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//				Criteria criteria = new Criteria();
//				String bestProvider = locationManager.getBestProvider(criteria, false);
//				Location location = locationManager.getLastKnownLocation(bestProvider);
//				try {
//					lat = location.getLatitude();
//					log = location.getLongitude();
////					Log.v("HAI", log + " " + lat);
//				} catch (NullPointerException e) {
//					lat = -1.0;
//					log = -1.0;
//				}
//				putParams();
//			}

		}

	};

	/*
	 * ham dua gia tri log lat lay dc len services
	 */
	public void putParams() {

		JSONObject jsonObjSend = new JSONObject();

		try {
			jsonObjSend.put("method", "mobile_get_status");
			// goi ham co ten update_loglat tren service
			jsonObjSend.put("id", "g8");
			JSONObject params = new JSONObject();
			params.put("longitude", log);
			params.put("latitde", lat);
			// truyen long lat len
			JSONArray jArray = new JSONArray();
			jArray.put(params);
			jsonObjSend.put("params", jArray);
			jsonObjSend.put("jsonrpc", "2.0");
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		UserControllerLib.getInstance().call(MyService.this.getApplicationContext(), jsonObjSend, true, false, "asd");
	}

}
