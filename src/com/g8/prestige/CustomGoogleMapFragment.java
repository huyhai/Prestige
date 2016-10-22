package com.g8.prestige;

import com.g8.prestige.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.g8.common.Utilities;
import com.g8.libs.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class CustomGoogleMapFragment extends Fragment implements OnInfoWindowClickListener {
	protected GoogleMap mMap;
	public static LatLng mCurrentPoint;
	public static Boolean isSetMaker = false;
	protected CustomInfoWindowAdapter1 mapAdapter;
	protected SupportMapFragment mMapFragment;
	protected ArrayList<Marker> listMarker;
	protected Marker mCurrentMarker;
	protected View mVIew;
	private Marker mClickedMarker;
	public static Boolean isFilter = false;
	public String addressLocal = null;
	public LatLng latlogLocal = null;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// ((LinearLayout) mVIew.findViewById(R.id.mapviewLinearLayout))
		// .removeAllViews();
	}

	public void moveToPoints(final List<LatLng> listBounds, final Boolean isMark, final ArrayList<String> listAddress) {
		final View mapView = mMapFragment.getView();
		// try {
		if (mapView.getViewTreeObserver().isAlive()) {

			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation")
				@SuppressLint("NewApi")
				// We check which build version we are using.
				@Override
				public void onGlobalLayout() {
					LatLngBounds.Builder builder = new LatLngBounds.Builder();
					// listMarker = new ArrayList<Marker>();
					for (int i = 0; i < listBounds.size(); i++) {
						if (isMark) {
							// listMarker.add(mMap.addMarker(new
							// MarkerOptions().title(listAddress.get(i)).position(listBounds.get(i))));
							mClickedMarker = mMap.addMarker(new MarkerOptions().title(listAddress.get(i)).position(listBounds.get(i)));
							mClickedMarker.showInfoWindow();
						}
						builder.include(listBounds.get(i));
					}
					if (listBounds.size() > 0) {
						LatLngBounds bounds = builder.build();
						if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
							mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						} else {
							mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
						}
						mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
					}

				}
			});
		}

		ArrayList<String> listAddress1 = new ArrayList<String>();
		for (int i = 0; i < listAddress.size(); i++) {
			listAddress1.add(listAddress.get(i));
		}

		mapAdapter = new CustomInfoWindowAdapter1(getActivity(), listAddress1, true);
		mMap.setInfoWindowAdapter(mapAdapter);
	}

	/*
	 * handle google api
	 */
	public String makeFindLocationUrl(String key) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps/api/geocode/json");
		urlString.append("?address=");// from
		urlString.append(key);
		urlString.append("&sensor=false");
		Log.d("xxx", "URL=" + urlString.toString());
		return urlString.toString().trim().replace(" ", "%20");
	}

	public void showListLocation(final String key, final LatLng mCurrentPoint, final String srcAddress) {
		try {
			((CustomFragmentActivity) getActivity()).showProgressBar();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getListLocation(key, mCurrentPoint, false, srcAddress);
			}
		}).start();
	}

	public void getListLocation(String key, LatLng mCurrentPoint, Boolean isSingleAddress, String srcAddress) {
		// connect to map web service
		ArrayList<Address> listLocation = new ArrayList<Address>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(this.makeFindLocationUrl(key));
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = null;
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			reader.close();
			String result = sb.toString();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray routeArray = jsonObject.getJSONArray("results");

			for (int i = 0; i < routeArray.length(); i++) {
				Address modelNote = new Address(Locale.getDefault());
				JSONObject note = routeArray.getJSONObject(i);
				modelNote.setAddressLine(0, note.getString("formatted_address"));
				JSONObject geometry = note.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				modelNote.setLongitude(location.getDouble("lng"));
				modelNote.setLatitude(location.getDouble("lat"));
				listLocation.add(modelNote);
			}
			if (!isSingleAddress) {
				showLocationPicker(listLocation, mCurrentPoint, srcAddress);
			} else {
				getLatLongFromFristAddress(listLocation);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			((CustomFragmentActivity) getActivity()).hideProgressBar();
		}
	}

	onSetAddressCallBack setAddressCalback;
	Boolean isDrawPath = false;

	public ArrayList<Address> getListAddress(String key, LatLng mCurrentPoint, String srcAddress, onSetAddressCallBack _setAddressCalback, Boolean _isDrawPath) {
		// connect to map web service
		setAddressCalback = _setAddressCalback;
		isDrawPath = _isDrawPath;
		ArrayList<Address> listLocation = new ArrayList<Address>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(this.makeFindLocationUrl(key));
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = null;
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			reader.close();
			String result = sb.toString();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray routeArray = jsonObject.getJSONArray("results");
			for (int i = 0; i < routeArray.length(); i++) {
				Address modelNote = new Address(Locale.getDefault());
				JSONObject note = routeArray.getJSONObject(i);
				modelNote.setAddressLine(0, note.getString("formatted_address"));
				JSONObject geometry = note.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				modelNote.setLongitude(location.getDouble("lng"));
				modelNote.setLatitude(location.getDouble("lat"));
				listLocation.add(modelNote);
			}
			showLocationPicker(listLocation, mCurrentPoint, srcAddress);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Utilities.showDialogNoBack(getActivity(), "Cannot find path to your location!!").show();
				}
			});
		} finally {
			((CustomFragmentActivity) getActivity()).hideProgressBar();
		}
		return listLocation;
	}

	public void showLocationPicker(final List<Address> results, final LatLng mCurrentPoint, final String srcAddress) {
		final ArrayList<String> listAddressPoint = new ArrayList<String>();
		listAddressPoint.add(srcAddress);
		this.setMakerMap(srcAddress, mCurrentPoint);
		// Check input
		if (results.size() == 0) {
			Utilities.showDialogNoBack(getActivity(), "Address not math").show();
			return;
		}
		// Do not build dialog if the activity is finishing
		if (getActivity().isFinishing()) {
			return;
		}
		// Convert the list of results to display strings
		final String[] items = getAddressStringArray(results);
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Display alert
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Did you mean:");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int itemIndex) {
						// Display the position
						if (isDrawPath) {
							listAddressPoint.add(items[itemIndex]);
							displayLocation(results.get(itemIndex), mCurrentPoint, listAddressPoint);
						}
						LatLng latlogChoise = new LatLng(results.get(itemIndex).getLatitude(), results.get(itemIndex).getLongitude());
						setAddressCalback.setTextEdit(items[itemIndex], latlogChoise);
						dialogInterface.cancel();
					}
				});
				builder.show();
			}
		});
	}

	public interface onSetAddressCallBack {
		void setTextEdit(String address, LatLng position);
	}

	public LatLng getLatLongFromFristAddress(final List<Address> results) {
		LatLng point = new LatLng(results.get(0).getLatitude(), results.get(0).getLongitude());
		doAfterGetLatLongFromAddress(point);
		return point;
	}

	public void doAfterGetLatLongFromAddress(LatLng point) {
	}

	public String[] getAddressStringArray(List<Address> results) {
		// Declare
		ArrayList<String> result = new ArrayList<String>();
		String[] resultType = new String[0];
		// Iterate over addresses
		for (int i = 0; i < results.size(); i++) {
			// Get the data
			String formattedAddress = results.get(i).getAddressLine(0);
			if (formattedAddress == null)
				formattedAddress = "";
			try {
				formattedAddress = new String(formattedAddress.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				Log.e("utf8", "conversion", e);
			}
			result.add(formattedAddress);
		}
		// Return
		if (result.size() == 0) {
			return null;
		} else {
			return (String[]) result.toArray(resultType);
		}
	}

	public void displayLocation(Address address, final LatLng mCurrentPoint, final ArrayList<String> listAddress) {
		((CustomFragmentActivity) getActivity()).showProgressBar();
		final LatLng destGeoPoint = new LatLng(address.getLatitude(), address.getLongitude());

		if (null != destGeoPoint) {

			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					DrawPath(mCurrentPoint, destGeoPoint, listAddress);
				}
			}).start();
		}
	}

	@SuppressWarnings("unused")
	private String duaration;
	protected void DrawPath(final LatLng srcPoint, final LatLng destPoint, final ArrayList<String> listAddress) {
		// connect to map web service

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(this.makeUrl(srcPoint, destPoint));
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = null;
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			reader.close();
			String result = sb.toString();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray routeArray = jsonObject.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			// demo get direction
			JSONArray legs = routes.getJSONArray("legs");
			// Grab first leg
			JSONObject leg = legs.getJSONObject(0);
			JSONObject durationObject = leg.getJSONObject("duration");
			 duaration=durationObject.getString("text");
			JSONArray steps = leg.getJSONArray("steps");
			// ///
			final int numSteps = steps.length();
			for (int i = 0; i < numSteps; i++) {
				// Get the individual step
				final JSONObject step = steps.getJSONObject(i);
				// Strip html from google directions and set as turn instruction
			}
			// end demo
			final List<LatLng> pointToDraw = this.decodePoly(encodedString);
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					drawPath(pointToDraw);
					List<LatLng> listPoint = new ArrayList<LatLng>();
					listPoint.add(srcPoint);
					listPoint.add(destPoint);
					moveToPoints(listPoint, true, listAddress);
				}
			});

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Utilities.showDialogNoBack(getActivity(), "Cannot find path to your location!!").show();
				}
			});
		} finally {
			((CustomFragmentActivity) getActivity()).hideProgressBar();
		}
	}

	public void drawPath(List<LatLng> listPoint) {
		// List<LatLng> list = new ArrayList<LatLng>();
		// for (int i = 0; i < listPoint.size(); i++) {
		// list.add(new LatLng(listPoint.get(i).getLatitudeE6() / 1E6,
		// listPoint.get(i).getLongitudeE6() / 1E6));
		// }
		mMap.addPolyline(new PolylineOptions().addAll(listPoint).color(Color.BLUE).width(6));
	}

	public List<LatLng> decodePoly(final String encoded) {
		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			// GeoPoint p = new GeoPoint((int) ((lat / 1E5) * 1E6), (int) ((lng
			// / 1E5) * 1E6));
			LatLng a = new LatLng((lat / 1E5), (lng / 1E5));
			poly.add(a);
		}
		return poly;
	}

	public String makeUrl(final LatLng src, final LatLng dest) {
		// TODO Auto-generated method stub
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(src.latitude));
		urlString.append(",");
		urlString.append(Double.toString(src.longitude));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(dest.latitude));
		urlString.append(",");
		urlString.append(Double.toString(dest.longitude));
		urlString.append("&sensor=false");
		Log.d("xxx", "URL=" + urlString.toString());
		return urlString.toString();
	}

	public void startLocationDetail(final int position, final LatLng point) {
	}

	public void initDefaultPoint(LatLng point, Boolean isMove) {
		try {
			mCurrentMarker = mMap.addMarker(new MarkerOptions().position(point).title("Current Position").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			if (isMove) {

				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, mMap.getCameraPosition().zoom));
			}
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void setMakerMap(final String address, final LatLng position) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mMap.clear();
				mClickedMarker = mMap.addMarker(new MarkerOptions().title(address).position(position));
				mClickedMarker.showInfoWindow();
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 11));
			}
		});
	}

	/*
	 * end handle google api
	 */
	class CustomInfoWindowAdapter1 implements InfoWindowAdapter {

		// These a both viewgroups containing an ImageView with id "badge" and
		// two
		// TextViews with id
		// "title" and "snippet".
		private final View mWindow;
		private Activity mContext;
		private ArrayList<String> mData;
		@SuppressWarnings("unused")
		private Boolean isEnableDetailView = false;
		private RelativeLayout rlAllInfo;
		private RelativeLayout rlOto;

		CustomInfoWindowAdapter1(Activity context, ArrayList<String> _data, Boolean isEnableDetailView) {
			this.mContext = context;
			this.mData = _data;
			this.isEnableDetailView = isEnableDetailView;
			mWindow = mContext.getLayoutInflater().inflate(R.layout.custom_info_window, null);
		}

		@Override
		public View getInfoWindow(Marker marker) {
			render(marker, mWindow);
			return mWindow;
		}

		@Override
		public View getInfoContents(Marker marker) {
			return null;
		}

		@SuppressWarnings("unused")
		private String note;

		private void render(final Marker marker, View view) {
			// ArrayList<String> note = new ArrayList<String>();
			for (int i = 0; i < mData.size(); i++) {
				if (marker.getTitle().equals(mData.get(i))) {
					note = mData.get(i);
					break;
				}

				// Log.v("HAI", "break");
			}
			TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
			TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
			rlAllInfo = (RelativeLayout) view.findViewById(R.id.rlAllInfo);
			rlOto = (RelativeLayout) view.findViewById(R.id.rlOto);
			LayoutParams lp=rlAllInfo.getLayoutParams();
			lp.width=(int) (Utilities.getGlobalVariable(mContext).device_width/2.5);
			lp.height=Utilities.getGlobalVariable(mContext).device_height/16;
			rlAllInfo.setLayoutParams(lp);
			LayoutParams lprlOto=rlOto.getLayoutParams();
			lprlOto.width=(int) (Utilities.getGlobalVariable(mContext).device_width/9);
			lprlOto.height=Utilities.getGlobalVariable(mContext).device_height/16;
			rlOto.setLayoutParams(lprlOto);

			if (null != mData && !marker.equals(mCurrentMarker)) {
				SpannableString titleText;
				if (mData != null) {
					titleText = new SpannableString(note);
					titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
					tvAddress.setText(titleText);
					tvTime.setText(duaration);
				} else {
					tvAddress.setText("");
				}
			}
		}
	}

	// public class AddressModel implements Serializable {
	// private String name;
	// private String time;
	//
	// public AddressModel(String _name, String _des) {
	// name = _name;
	// time = _des;
	//
	// }
	//
	// public AddressModel() {
	//
	// }
	//
	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// public String gettime() {
	// return time;
	// }
	//
	// public void settime(String des) {
	// this.time = des;
	// }
	//
	// }

	@SuppressWarnings("unused")
	class CustomInfoWindowAdapter implements InfoWindowAdapter {

		private final View mWindow;
		private Activity mContext;
		private ArrayList<String> mData;
		private Boolean isEnableDetailView = false;
		int position;

		CustomInfoWindowAdapter(Activity context, ArrayList<String> _data, Boolean isEnableDetailView) {
			this.mContext = context;
			this.mData = _data;
			this.isEnableDetailView = isEnableDetailView;
			mWindow = mContext.getLayoutInflater().inflate(R.layout.custom_info_window, null);
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// render(marker, mWindow);
			return mWindow;
		}

		@Override
		public View getInfoContents(Marker marker) {
			return null;
		}

		public void clearImgload() {
		}

		// private void render(final Marker marker, View view) {
		// // StoreFieldModel note = null;
		// for (int i = 0; i < mData.size(); i++) {
		// if (marker.equals(listMarker.get(i))) {
		// note = mData.get(i);
		// position = i;
		// break;
		// }
		// }
		// view.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// }
		// });
		//
		// TextView titleUi = ((TextView) view.findViewById(R.id.title));
		// titleUi.setTypeface(null, Typeface.BOLD);
		// TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
		// RatingBar rtView = (RatingBar) view.findViewById(R.id.rtView);
		// ImageView imgBusiness = (ImageView)
		// view.findViewById(R.id.imgBusiness);
		// }
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
	}

	public interface LocationCallBack {
		void callback(LatLng position, String result);
	}

	public class LoadLocation extends AsyncTask<LatLng, Object, Object> {

		private LocationCallBack mCallBack;
		private LatLng mPosition;

		public LoadLocation(LocationCallBack callback) {
			// TODO Auto-generated constructor stub
			mCallBack = callback;
			((CustomFragmentActivity) getActivity()).showProgressBar();
		}

		@Override
		protected Object doInBackground(LatLng... params) {
			// TODO Auto-generated method stub
			mPosition = params[0];
			return getLocationName(mPosition);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			((CustomFragmentActivity) getActivity()).hideProgressBar();
			mCallBack.callback(mPosition, updateLocation(String.valueOf(result)));
		}

	}

	public String makeUrl(LatLng point) {
		StringBuilder urlString = new StringBuilder();

		urlString.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
		urlString.append(point.latitude + "," + point.longitude);
		urlString.append("&sensor=false");
		Log.d("xxx", "URL=" + urlString.toString());
		return urlString.toString().trim().replace(" ", "%20");
	}

	public String getLocationName(LatLng key) {
		// connect to map web service
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(this.makeUrl(key));
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = null;
			is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			reader.close();
			String result = sb.toString();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray routeArray = jsonObject.getJSONArray("results");
			JSONObject note = routeArray.getJSONObject(0);
			return note.getString("formatted_address");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public String updateLocation(String location) {
		try {
			location = new String(location.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Throwable e) {
			location = "";
			e.printStackTrace();
		}
		return location;
	}

	public LatLng getCurrentLocation() {
		// check if GPS enabled
		GPSTracker gps = new GPSTracker(getActivity());
		LatLng srcGeoPoint;
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			srcGeoPoint = new LatLng(latitude, longitude);
		} else {
			// LocationFragment.gps.showSettingsAlert();
			double latitude = 10.77306;
			double longitude = 106.69833;
			srcGeoPoint = new LatLng(latitude, longitude);
		}
		return srcGeoPoint;
	}
}