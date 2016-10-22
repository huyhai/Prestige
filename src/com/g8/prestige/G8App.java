package com.g8.prestige;


import java.io.Serializable;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class G8App extends Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Boolean bIsLogXML = true;
	public boolean bTabletVersionMid = false;
	public boolean isHome = false;
	public int language=1;
	public String accessToken ;
	public Boolean isLogin = false;
	public Boolean clearCase = false;
	public boolean isHavePush = false;
	public static LatLng latlongDefault;
	public String addressDefault;
	public int device_width = 0;
	public int device_height = 0;
    private static Context context;	
    public boolean isMaps=false;
    public  String phoneNumber;
    public  boolean isphoneNumber=false;
    public  boolean isChoiceDate=false;

    public static Context getAppContext() {
        return G8App.context;
    }

    @Override
    public void onCreate() {
    	G8App.context = getApplicationContext();
    	super.onCreate();
    }
	public static void initImageLoader(Context context, ImageLoader imageLoader) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging().build();
		// Initialize ImageLoader with configuration.
		imageLoader.init(config);
	}


	public void setLatlongDefault(LatLng _latlongDefault) {
		latlongDefault = _latlongDefault;
	}

	public LatLng getLatlongDefault() {
		if (null == latlongDefault) {
			latlongDefault = new LatLng(12.637323, 108.042297);
		}
		return latlongDefault;
	}

	public void setAddressDefault(String addressDefault) {
		this.addressDefault = addressDefault;
	}

	public String getAddressDefault() {
		return addressDefault;
	}
	
}
