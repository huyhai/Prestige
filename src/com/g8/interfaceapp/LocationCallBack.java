package com.g8.interfaceapp;

import com.google.android.gms.maps.model.LatLng;

public interface LocationCallBack {
	void callback(LatLng position, String result);
}