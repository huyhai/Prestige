package com.g8.libs;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
//	content://media/external/images/media/7296
//		/storage/sdcard0/DCIM/Camera/IMG_20131109_234826.jpg
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "Wiim";
	
	// All Shared Preferences Keys
	
	// User name (make variable public to access from outside)
	public static final String KEY_URI = "uri";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_PATH = "path";
	public static final String KEY_NAME = "name";
	public static final String KEY_PASS = "pass";
	public static final String KEY_ISLOGIN= "log";
	public static final String KEY_LANGUAGE = "lan";
	
	// Constructor
	@SuppressLint("CommitPrefEdits")
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void saveLink(String uri, String path){
		editor.putString(KEY_URI, uri);
		editor.putString(KEY_PATH, path);
		editor.commit();
	}	
	
	public void saveLogin(String name, String pass,boolean islog){
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_PASS, pass);
		editor.putBoolean(KEY_ISLOGIN, islog);
		editor.commit();
	}	
	public void saveLanguage(Integer name){
		editor.putInt(KEY_LANGUAGE, name);
		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
//	public void checkLogin(){
//		// Check login status
//		if(!this.isLoggedIn()){
//			// user is not logged in redirect him to Login Activity
//			Intent i = new Intent(_context, LoginActivity.class);
//			// Closing all the Activities
//			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			
//			// Add new Flag to start new Activity
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			
//			// Staring Login Activity
//			_context.startActivity(i);
//		}
//		
//	}
	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> pic = new HashMap<String, String>();
		// user name
		pic.put(KEY_URI, pref.getString(KEY_URI, null));
		
		// user email id
		pic.put(KEY_PATH, pref.getString(KEY_PATH, null));
		
		// return user
		return pic;
	}
	
	public HashMap<String, String> getUserLogin(){
		HashMap<String, String> pic = new HashMap<String, String>();
		pic.put(KEY_NAME, pref.getString(KEY_NAME, null));
		pic.put(KEY_PASS, pref.getString(KEY_PASS, null));
		
		return pic;
	}
	public int getLanguage(){
		
		return pref.getInt(KEY_LANGUAGE, 1);
	}
	
	/**
	 * Clear session details
	 * */
//	public void logoutUser(){
//		// Clearing all data from Shared Preferences
//		editor.clear();
//		editor.commit();
//		
//		// After logout redirect user to Loing Activity
//		Intent i = new Intent(_context, LoginActivity.class);
//		// Closing all the Activities
//		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		
//		// Add new Flag to start new Activity
//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		
//		// Staring Login Activity
//		_context.startActivity(i);
//	}
	
	/**
	 * Quick check for login
	 * **/
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_ISLOGIN, false);
	}
}
