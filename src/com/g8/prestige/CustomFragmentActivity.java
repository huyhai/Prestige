package com.g8.prestige;

import com.g8.prestige.R;
import com.g8.ui.HomeAc;
import com.g8.ui.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.g8.adapter.BannerAdapter;
import com.g8.adapter.RoomAdapter;
import com.g8.common.ConstantValue;
import com.g8.common.FragmentFactory;
import com.g8.common.Utilities;
import com.g8.control.UserFunctions;
import com.g8.interfaceapp.PassDataInterface;
import com.g8.interfaceapp.onImageSet;
import com.g8.libs.DatePickerDialogFragment;
import com.g8.libs.SessionManager;
import com.google.android.gcm.GCMRegistrar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressWarnings("unused")
public class CustomFragmentActivity extends FragmentActivity implements
		PassDataInterface, OnClickListener {
	protected ArrayList<Integer> mListView;
	private Map<String, Object> passData;
	private static final String DATE_FRAGMENT = "date_fragment";
	private static final String MAP_FRAGMENT = "map_fragment";
	public FrameLayout frMain;
	private RelativeLayout pdBar;
	private ProgressBar pdBarMain;
	public Boolean isShowDialog = false;
	protected Fragment mFragment;
	public static Boolean isShowProgressBar = false;
	protected Boolean isOnActivityResult = false;
	public Boolean clearCasch = false;
	public static DisplayImageOptions options;
	public static ImageLoader imageLoader2;
	private int mTitleRes;
	protected Fragment mFrag;
	private Bundle savedInstanceState;
	public boolean isNewpage = false;
	public static Context cont;
	private SessionManager session;
	private TextView tvRoomValue;
	private static RelativeLayout rl;
	private RelativeLayout rlBottom;
	private TextView tvChay;
	private RelativeLayout rlLogin;
	private TextView tvLogin1;
	private ImageView btnLogin;

	private static TextView tvGroValue;
	private static TextView tvRMValue;
	private static TextView tvStatusValue;
	private int currentApiVersion;
	private HorizontalScrollView hr;

	public CustomFragmentActivity(int titleRes) {
		System.out.println("tinhvc mTitleRes: " + mTitleRes);
		mTitleRes = titleRes;
	}

	@SuppressLint("InlinedApi")
	@SuppressWarnings("static-access")
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		session = new SessionManager(CustomFragmentActivity.this);
		int languageCode = session.getLanguage();
		if (languageCode == 1) {
			Utilities.getGlobalVariable(CustomFragmentActivity.this).language = 1;
			Locale locale = new Locale("en");
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			CustomFragmentActivity.this.getApplicationContext().getResources()
					.updateConfiguration(config, null);
			// Main.setText("en");
		} else {
			Utilities.getGlobalVariable(CustomFragmentActivity.this).language = 2;
			Locale locale = new Locale("zh-TW");
			Locale.setDefault(locale);

			Configuration config = new Configuration();
			config.locale = locale;
			CustomFragmentActivity.this.getApplicationContext().getResources()
					.updateConfiguration(config, null);
			// Main.setText("zh-TW");
		}
		this.setContentView(R.layout.activity_main);
		cont = CustomFragmentActivity.this.getApplicationContext();
		if (this.mListView == null) {
			this.mListView = new ArrayList<Integer>();
		}

		this.frMain = (FrameLayout) this.findViewById(R.id.frame_container);
		this.pdBar = (RelativeLayout) this.findViewById(R.id.pdBar);
		this.pdBarMain = (ProgressBar) this.findViewById(R.id.pdBarMain);
		tvLogin1 = (TextView) this.findViewById(R.id.tvLogin1);
		btnLogin = (ImageView) this.findViewById(R.id.btnLogin);
		tvChay = (TextView) this.findViewById(R.id.tvChay);
		tvStatusValue = (TextView) this.findViewById(R.id.tvStatusValue);
		tvRMValue = (TextView) this.findViewById(R.id.tvRMValue);
		tvGroValue = (TextView) this.findViewById(R.id.tvGroValue);
		rlLogin = (RelativeLayout) this.findViewById(R.id.rlLogin);
		// hr=(HorizontalScrollView)this.findViewById(R.id.hrtTV);
		// hr.setHorizontalScrollBarEnabled(false);
		tvChay.setText(UserFunctions.getInstance().getContactModel()
				.getPromotion_Message());
		// tvChay.setMovementMethod(new ScrollingMovementMethod());
		// startAutoScrolling();
		tvChay.setEnabled(true); // Thanks to Romain Guy
		tvChay.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				LayoutParams params = v.getLayoutParams();
				params.width = right - left;
				params.height = bottom - top;
				// params.weight = 0;
				v.removeOnLayoutChangeListener(this);
				v.setLayoutParams(params);
			}
		});
		this.clearCasch = Utilities
				.getGlobalVariable(CustomFragmentActivity.this).clearCase;
		tvRoomValue = (TextView) findViewById(R.id.tvRoomValue);
		rl = (RelativeLayout) this.findViewById(R.id.rl);
		rlBottom = (RelativeLayout) this.findViewById(R.id.rlBottom);

		rl.setOnClickListener(this);
		rlLogin.setOnClickListener(this);
		tvRoomValue.setOnClickListener(this);

		LayoutParams lrlBottomp = rlBottom.getLayoutParams();
		lrlBottomp.height = (int) (Utilities
				.getGlobalVariable(CustomFragmentActivity.this).device_height / 9);
		rlBottom.setLayoutParams(lrlBottomp);
		LayoutParams lprrlLogin = rlLogin.getLayoutParams();
		lprrlLogin.width = (int) (Utilities
				.getGlobalVariable(CustomFragmentActivity.this).device_width / 7);
		// lprrlLogin.height=(int)
		// (Utilities.getGlobalVariable(HomeAc.this).device_height/2.2);
		rlLogin.setLayoutParams(lprrlLogin);
		if (Utilities.getGlobalVariable(CustomFragmentActivity.this).isHavePush == false) {
			getSettingPush(CustomFragmentActivity.this);
		}

		imageLoader2 = com.nostra13.universalimageloader.core.ImageLoader
				.getInstance();

		Utilities.getGlobalVariable(this).initImageLoader(this, imageLoader2);
		// options = new
		// DisplayImageOptions.Builder().showStubImage(R.drawable.photo_none).showImageForEmptyUri(R.drawable.photo_none).showImageOnFail(R.drawable.photo_none).cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		if (clearCasch) {
			imageLoader2.clearDiscCache();
			imageLoader2.clearMemoryCache();
		}
		if (Utilities.getGlobalVariable(CustomFragmentActivity.this).isLogin) {
			btnLogin.setBackgroundResource(R.drawable.ic_back);
			tvLogin1.setText("Go Back");
		}
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
	}

	private Timer scrollTimer = null;
	private TimerTask scrollerSchedule;

	private int scrollPos = 0;

	public void startAutoScrolling() {
		if (scrollTimer == null) {
			scrollTimer = new Timer();
			final Runnable Timer_Tick = new Runnable() {
				public void run() {
					moveScrollView();
				}

			};

			if (scrollerSchedule != null) {
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(Timer_Tick);
				}
			};

			scrollTimer.schedule(scrollerSchedule, 30, 30);
		}
	}

	private void moveScrollView() {
		scrollPos = (int) (hr.getScrollX() + 1.0);
		hr.scrollTo(scrollPos, 0);
		Log.v("HAI", "hai");

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

	// public void setSlidingMenu(Fragment fragment, Boolean isShowMenu) {
	// setTitle(mTitleRes);
	//
	// // set the Behind View
	// setBehindContentView(R.layout.sliding_menu_view_root_layout);
	// if (savedInstanceState == null) {
	// FragmentTransaction t =
	// this.getSupportFragmentManager().beginTransaction();
	// mFrag = fragment;
	// t.replace(R.id.menu_frame, mFrag);
	// t.commit();
	// } else {
	// mFrag = (ListFragment)
	// this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
	// }
	//
	// // customize the SlidingMenu
	// SlidingMenu sm = getSlidingMenu();
	// sm.setShadowWidthRes(R.dimen.margin_15);
	// sm.setShadowDrawable(R.drawable.shadow);
	// sm.setBehindOffsetRes(R.dimen.margin_60);
	// sm.setFadeDegree(0.35f);
	// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	// sm.setSlidingEnabled(isShowMenu);
	// // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	// }

	private static String groNo;

	public static void setBook(int pos) {
		UserFunctions.getInstance().getListGRO().get(pos)
				.setStatus_Booking("1");
		tvStatusValue.setText("Booked");
	}

	public static void setInfo(int pos) {
		if (!(UserFunctions.getInstance().getListGRO().size() == 0)) {
			if (UserFunctions.getInstance().getListGRO().get(pos)
					.getStatus_Booking().equals("1")) {
				tvStatusValue.setText("Booked");
				rl.setEnabled(false);
			} else {
				tvStatusValue.setText("Available");
			}

			tvRMValue.setText(UserFunctions.getInstance().getUserModel()
					.getIdUser());
			tvGroValue.setText(UserFunctions.getInstance().getListGRO()
					.get(pos).getFullName()
					+ " ("
					+ UserFunctions.getInstance().getListGRO().get(pos)
							.getGroID() + ")");
			groNo = UserFunctions.getInstance().getListGRO().get(pos)
					.getGroID();
		}

	}

	public void getSettingPush(Context context) {
		Utilities.getGlobalVariable(this).isHavePush = true;
		/**
		 * Function get push from server
		 */
	}

	public void resetListFragment() {
		while (mListView.size() > 0) {
			mListView.remove(mListView.size() - 1);
		}
	}

	/**
	 * get string from bitmap
	 * 
	 * @param bitmap
	 * @param check
	 * @return
	 */
	public String getStringFromBitmap(final Bitmap bitmap, final String check) {

		String s = null;
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (check.equalsIgnoreCase("PNG")) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
			} else {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
			}
			bytes = baos.toByteArray();
			s = Base64.encodeToString(bytes, 1);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return s;
	}

	// Read bitmap
	public Bitmap getBitmapFromURI(Context cont, Uri selectedImage) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 4;
		Bitmap bm = null;
		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = cont.getContentResolver().openAssetFileDescriptor(
					selectedImage, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bm = BitmapFactory.decodeFileDescriptor(
						fileDescriptor.getFileDescriptor(), null, opt);
				fileDescriptor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}

	/**
	 * end
	 */

	public void startFragment(final int key) {
		// && (key != ConstantValue.SETTING_SIGNUP_FRAGMENT)
		if (!this.mListView.contains(key)) {
			this.mListView.add(key);
		}
		fragmentTransition(key);

	}

	public void replaceFragment(final int key, final Boolean isAnimation) {
		// && (key != ConstantValue.SETTING_SIGNUP_FRAGMENT)
		// // change release map to here
		// if (mFragment instanceof CustomGoogleMapFragment) {
		// releaseMap();
		// }
		if (!this.mListView.contains(key)) {
			this.mListView.add(key);
		}
		if (isAnimation) {
			this.flipit(key, true);
		} else {
			fragmentTransition(key);
		}
	}

	public void replaceFragmentPeer(final int key, final Boolean isAnimation,
			Boolean peer) {
		// && (key != ConstantValue.SETTING_SIGNUP_FRAGMENT)
		// // change release map to here
		// if (mFragment instanceof CustomGoogleMapFragment) {
		// releaseMap();
		// }

		if (peer) {
			if (!this.mListView.contains(key)) {
				this.mListView.add(key);
			}
		} else {
			this.mListView.add(key);
		}
		if (peer) {
			this.mListView.remove(mListView.size() - 1);
			this.mListView.add(key);
		}
		if (isAnimation) {
			this.flipit(key, true);
		} else {
			fragmentTransition(key);
		}
	}

	public void backToFragment(final int key, final Boolean isAnimation) {

		if (isAnimation) {
			this.flipit(key, false);
		} else {
			fragmentTransition(key);
		}

	}

	public void returnToFragment(final int key, final Boolean isAnimation) {

		for (int i = mListView.size() - 1; i >= 0; i--) {
			if (mListView.get(i) == key) {
				break;
			}
			mListView.remove(mListView.size() - 1);
		}

		if (isAnimation) {
			this.flipit(key, false);
		} else {
			fragmentTransition(key);
		}

	}

	FragmentTransaction fragmentTS;

	public void fragmentTransition(final int key) {
		System.gc();
		imageLoader2.stop();
		imageLoader2.clearMemoryCache();
		onFragmentChange();
		if (mFragment instanceof CustomGoogleMapFragment) {
			// releaseMap();
		}
		if (null != mFragment) {
			fragmentTS.remove(mFragment);
		}
		fragmentTS = this.getSupportFragmentManager().beginTransaction();
		mFragment = FragmentFactory.getFragmentByKey(key);
		// fragmentTS.setCustomAnimations(R.anim.slide_in_left,
		// R.anim.slide_out_right);
		fragmentTS.replace(R.id.frame_container, mFragment);
		// fragmentTS.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTS.commitAllowingStateLoss();
	}

	public Fragment findFragment() {
		return mFragment;
	}

	/**
	 * Gets the picture.
	 */
	public void getPicture() {
		isOnActivityResult = true;
		this.startActivityForResult(new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
				ConstantValue.UPLOAD_PHOTO);
	}

	public void showDatePickerDialog(Fragment fragment, Boolean isDatepicker) {
		if (!isShowDialog) {
			isShowDialog = true;
			DatePickerDialogFragment newFragment = new DatePickerDialogFragment(
					fragment, isDatepicker);
			newFragment.show(getSupportFragmentManager(), DATE_FRAGMENT);
		}
	}

	public Object getPassedValue(final String key) {

		// TODO Auto-generated method stub
		if (null != passData && passData.containsKey(key)) {
			return this.passData.get(key);
		}
		return -1;
	}

	public void setPassValue(final String key, final Object value) {

		// TODO Auto-generated method stub
		if (this.passData == null) {
			this.passData = new HashMap<String, Object>();
		}
		this.passData.put(key, value);

	}

	// public static Boolean isSignUpCompany = false;

	@Override
	public void onBackPressed() {
		if (this.mListView.size() > 1) {
			Boolean isAnimation = false;
			this.mListView.remove(this.mListView.size() - 1);
			// resetListFragment();
			this.backToFragment(this.mListView.get(this.mListView.size() - 1),
					isAnimation);
			// this.backToFragment(this.mListView.get(0), isAnimation);
		} else {
			// if(isNewpage){
			// finish();
			// isNewpage=false;
			// }else{
			session = new SessionManager(CustomFragmentActivity.this);
			session.saveLanguage(Utilities
					.getGlobalVariable(CustomFragmentActivity.this).language);
			// Log.v("HAI", msg)
			((Activity) CustomFragmentActivity.this).finish();
			System.gc();
			System.exit(0);
			// Utilities.showDialogExit(CustomFragmentActivity.this, "Warning!",
			// "Do you want to exit?", CustomFragmentActivity.this);
			// }

		}
		return;
	}

	/**
	 * Process display view after makeup
	 * 
	 */
	public void recycle(ImageView iv) {
		Drawable drawable = iv.getDrawable();
		if (drawable instanceof BitmapDrawable) {

			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			iv.setImageBitmap(null);
			iv.setImageDrawable(null);
			iv.postInvalidate();

			if (bitmap != null)
				bitmap.recycle();
			bitmap = null;
		}
	}

	private Boolean isRun = true;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isRun = true;
		isOnActivityResult = false;
		IntentFilter intentGetKeySend = new IntentFilter();
		intentGetKeySend.addAction(UserFunctions.BOOKGRO);
		intentGetKeySend.addAction(UserFunctions.GETGRO1);
		CustomFragmentActivity.this
				.registerReceiver(receiver, intentGetKeySend);

	}

	/**
	 * dangtb view rotate animation
	 */
	private Interpolator accelerator = new AccelerateInterpolator();

	private Interpolator decelerator = new DecelerateInterpolator();

	private void flipit(final int key, final Boolean isForward) {

		float visToInvisFrom;
		float visToInvisTo;
		final float invisToVisFrom;
		final float invisToVisTo;
		if (isForward) {
			visToInvisFrom = 0f;
			visToInvisTo = 90f;
			invisToVisFrom = -90f;
			invisToVisTo = 0f;
		} else {
			visToInvisFrom = 0f;
			visToInvisTo = -90f;
			invisToVisFrom = 90f;
			invisToVisTo = 0f;
		}
		ObjectAnimator visToInvis = ObjectAnimator.ofFloat(this.frMain,
				"rotationY", visToInvisFrom, visToInvisTo);
		final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(this.frMain,
				"rotationY", invisToVisFrom, invisToVisTo);
		invisToVis.setDuration(250);
		// invisToVis.setInterpolator(decelerator);
		visToInvis.setDuration(250);
		// visToInvis.setInterpolator(accelerator);
		visToInvis.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(final Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(final Animator anim) {
				fragmentTransition(key);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						invisToVis.start();
					}
				}, 1);
			}
		});
		invisToVis.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(final Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(final Animator animation) {

			}

		});
		visToInvis.start();
	}

	public void showProgressBar() {
		pdBar.setVisibility(View.VISIBLE);
		frMain.setEnabled(false);
		isShowProgressBar = true;
		final InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(frMain.getWindowToken(), 0);
	}

	public void hideProgressBar() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pdBar.setVisibility(View.GONE);
				frMain.setEnabled(true);
				isShowProgressBar = false;
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (isShowProgressBar)
			return false;
		return super.dispatchTouchEvent(ev);
	}

	public static void saveObJectToFile(Object objectToWrite, Context con,
			String _fileName) {
		String fileName = _fileName;
		try {
			FileOutputStream fos = con.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			ObjectOutputStream os;
			os = new ObjectOutputStream(fos);
			os.writeObject(objectToWrite);
			os.close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		isRun = false;
		super.onPause();
		// releaseMap();
	}

	// /**
	// * Release mapview when not use
	// */
	// public void releaseMap() {
	// try {
	// FragmentTransaction trans =
	// getSupportFragmentManager().beginTransaction();
	// SupportMapFragment frag = ((SupportMapFragment)
	// this.getSupportFragmentManager().findFragmentById(R.id.mapMain));
	// if (null != frag) {
	// trans.remove(frag);
	// }
	// trans.commitAllowingStateLoss();
	// } catch (Throwable e) {
	// e.printStackTrace();
	// }
	// }

	public void onFragmentChange() {

	}

	/**
	 * [Explain the description for this method here].
	 * 
	 * @param requestCode
	 *            the request code
	 * @param resultCode
	 *            the result code
	 * @param data
	 *            the data
	 * @see android.app.Activity#onActivityResult(int, int,
	 *      android.content.Intent)
	 */
	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == ConstantValue.UPLOAD_PHOTO) {
				try {
					Uri selectedImageUri = data.getData();
					String selectedImagePath = getPath(selectedImageUri);
					((onImageSet) findFragment()).setimage(selectedImageUri,
							selectedImagePath);
				} catch (Exception e) {
					e.printStackTrace();
					this.calldialog();
				}
			} else if (requestCode == ConstantValue.UPLOAD_PHOTO_CAMERA) {
				try {
					String selectedImagePath = data
							.getStringExtra(ConstantValue.RESULT);
					((onImageSet) findFragment()).setimage(null,
							selectedImagePath);
				} catch (Exception e) {
					e.printStackTrace();
					this.calldialog();
				}
			}
		} else if (resultCode == RESULT_CANCELED) {
			if (requestCode == 99) {

			}
		}
	}

	/**
	 * Calldialog.
	 */
	public void calldialog() {
		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Your Selection is not a photo").setNeutralButton(
				"OK", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						// Do nothing.\

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Gets the path.
	 * 
	 * @param uri
	 *            the uri
	 * @return the path
	 */
	@SuppressWarnings("deprecation")
	public String getPath(final Uri uri) {

		String[] projection = { MediaStore.Images.Media.DATA };

		Cursor cursor = this.managedQuery(uri, projection, null, null, null);

		int columnIndex = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();

		return cursor.getString(columnIndex);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			CustomFragmentActivity.this.unregisterReceiver(receiver);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		isRun = false;
		super.onStop();
	}

	private Boolean isCallingFace = false;

	private Bitmap getBitmapFromAsset(String strName) throws IOException {
		AssetManager assetManager = getAssets();

		InputStream istr = assetManager.open(strName);
		Bitmap bitmap = BitmapFactory.decodeStream(istr);

		return bitmap;
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case android.R.id.home:
	// toggle();
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // getSupportMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	/**
	 * function show dialog login
	 */
	private Dialog dialogSearch;
	private RelativeLayout rlAll;
	private RelativeLayout rlCancel;
	private RelativeLayout rlOk;
	private TextView tvConfirm;

	public void showDialog1() {
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			dialogSearch = new Dialog(this);
			dialogSearch.getWindow();
			dialogSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialogSearch.setContentView(R.layout.dialog_confirm);
			dialogSearch.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			// dialog.setTitle("Title...");

			this.rlAll = (RelativeLayout) dialogSearch
					.findViewById(R.id.rlAllCf);
			this.rlCancel = (RelativeLayout) dialogSearch
					.findViewById(R.id.rlCancel);
			this.rlOk = (RelativeLayout) dialogSearch.findViewById(R.id.rlOk);
			this.tvConfirm = (TextView) dialogSearch
					.findViewById(R.id.tvConfirm);
			rlOk.setOnClickListener(this);
			rlCancel.setOnClickListener(this);
			LayoutParams lprlGalery = this.rlAll.getLayoutParams();
			lprlGalery.width = (int) (Utilities.getGlobalVariable(this).device_width / 4);
			lprlGalery.height = (int) (Utilities.getGlobalVariable(this).device_height / 3.5);
			this.rlAll.setLayoutParams(lprlGalery);
			this.tvConfirm.setText("Confirm your booking?\nYou booked GRO "
					+ tvGroValue.getText().toString().trim() + "\n(RM No."
					+ tvRMValue.getText().toString().trim() + ")"
					+ " from room " + tvRoomValue.getText().toString().trim()
					+ ".");
			dialogSearch.show();
			dialogSearch.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					isShowDialog = false;
				}
			});
		}

	}

	/**
	 * function show dialog login
	 */
	private Dialog dialog;
	private RelativeLayout tbAll;
	private GridView gvNum;
	private RelativeLayout rlClear;
	private RelativeLayout rlDone;

	@SuppressLint("InlinedApi")
	public void showDialogNoti() {
		// custom dialog
		if (!isShowDialog) {
			isShowDialog = true;
			dialog = new Dialog(this);
			dialog.getWindow();
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// dialog.requestWindowFeature(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
			// dialog.requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			dialog.setContentView(R.layout.dialog_numpad);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
			WindowManager.LayoutParams wmlp = dialog.getWindow()
					.getAttributes();

			wmlp.gravity = Gravity.TOP | Gravity.LEFT;
			wmlp.x = MainActivity.heght - (MainActivity.heght / 4); // x
																	// position
			wmlp.y = 35; // y position
			// dialog.setTitle("Title...");

			this.tbAll = (RelativeLayout) dialog.findViewById(R.id.tbAll);
			gvNum = (GridView) dialog.findViewById(R.id.gvNum);
			this.rlClear = (RelativeLayout) dialog.findViewById(R.id.rlClear);
			this.rlDone = (RelativeLayout) dialog.findViewById(R.id.rlDone);

			this.rlClear.setOnClickListener(this);
			this.rlDone.setOnClickListener(this);
			RoomAdapter adapter = new RoomAdapter(CustomFragmentActivity.this,
					UserFunctions.getInstance().getlistRoom());
			gvNum.setAdapter(adapter);

			LayoutParams lprlGalery = this.tbAll.getLayoutParams();
			lprlGalery.width = (int) (Utilities.getGlobalVariable(this).device_width / 5);
			lprlGalery.height = (int) (Utilities.getGlobalVariable(this).device_height / 2);
			this.tbAll.setLayoutParams(lprlGalery);

			dialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					isShowDialog = false;
				}
			});

			dialog.show();
		}

	}

	public void setRooom(String r) {
		tvRoomValue.setText(r);
	}

	public static boolean ischooseLogin = true;

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int requestCode = 0;
		// if (v == btnChoiceExis) {
		// dialogSelectImage.dismiss();
		// getPicture();
		// }
		if (null != intent) {
			CustomFragmentActivity.this.startActivityForResult(intent,
					requestCode);
		}
		if (v == rlLogin) {
			if (!Utilities.getGlobalVariable(CustomFragmentActivity.this).isHome) {
				HomeAc.set();
				if (!ischooseLogin) {
					btnLogin.setBackgroundResource(R.drawable.ic_back);
					tvLogin1.setText("Go Back");

				} else {
					btnLogin.setBackgroundResource(R.drawable.ic_login);
					tvLogin1.setText("Login");
				}
				// replaceFragment(ConstantValue.HOME, false);
			} else {
				onBackPressed();
			}
		}

		if (v == rlClear) {
			tvRoomValue.setText("00");
		}
		if (v == rlDone) {
			dialog.dismiss();
		}
		if (v == rl) {
			if (tvRoomValue.getText().toString().trim().equals("00")) {
				Toast.makeText(CustomFragmentActivity.this, "Choosen Room",
						Toast.LENGTH_SHORT).show();
			} else {
				showDialog1();
			}

		}
		if (v == tvRoomValue) {
			showDialogNoti();

		}
		if (v == rlCancel) {
			dialogSearch.dismiss();

		}
		if (v == rlOk) {
			if (tvStatusValue.getText().toString().equals("Available")) {
				getGRO();
			}

			dialogSearch.dismiss();

		}

	}

	private void getGRO() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "bookGRO"));
		params.add(new BasicNameValuePair("GROid", groNo));
		params.add(new BasicNameValuePair("RMid", UserFunctions.getInstance()
				.getUserModel().getIdUser()));
		params.add(new BasicNameValuePair("Roomid", tvRoomValue.getText()
				.toString().trim()));
//		params.add(new BasicNameValuePair("token", SplashScreen.regId));
		UserFunctions.getInstance().bookGRP(CustomFragmentActivity.this,
				params, true);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(UserFunctions.BOOKGRO)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					showDialogNoBack(
							CustomFragmentActivity.this,
							UserFunctions.getInstance().getMessage()
									+ " added GRO "
									+ HomeFragment.tvNameValue.getText())
							.show();
					MainActivity.mDrawerLayout.closeDrawers();
				} else {
					Utilities.showDialogNoBack(CustomFragmentActivity.this,
							UserFunctions.getInstance().getMessage()).show();
				}
			}
			if (intent.getAction().equalsIgnoreCase(UserFunctions.GETGRO1)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					if (HomeFragment.listImagesModelNew.size() > UserFunctions
							.getInstance().getListGRO().size()) {
						// co thang book
						HomeFragment.listImagesModelNew.clear();
						HomeFragment.listImagesModelNew.addAll(UserFunctions
								.getInstance().getListGRO());
						HomeFragment.adBannerAdapter.notifyDataSetChanged();

						HomeFragment.mViewPager
								.setAdapter(HomeFragment.adBannerAdapter);
						try {
							HomeFragment.pageChangeListener
									.onPageSelected(HomeFragment.pos);
							HomeFragment.mViewPager
									.setCurrentItem(HomeFragment.pos);
						} catch (Exception e) {
							HomeFragment.pageChangeListener
									.onPageSelected(HomeFragment.pos - 1);
							HomeFragment.mViewPager
									.setCurrentItem(HomeFragment.pos);
						}
						// if (HomeFragment.pos < 1) {
						// HomeFragment.pageChangeListener.onPageSelected(0);
						// HomeFragment.mViewPager.setCurrentItem(0);
						// } else {
						// HomeFragment.pageChangeListener
						// .onPageSelected(HomeFragment.pos - 1);
						// HomeFragment.mViewPager
						// .setCurrentItem(HomeFragment.pos);
						// }
					} else {
						// co thang them
						HomeFragment.listImagesModelNew.clear();
						HomeFragment.listImagesModelNew.addAll(UserFunctions
								.getInstance().getListGRO());
						HomeFragment.adBannerAdapter.notifyDataSetChanged();

						HomeFragment.mViewPager
								.setAdapter(HomeFragment.adBannerAdapter);
						HomeFragment.pageChangeListener
								.onPageSelected(HomeFragment.pos);
						HomeFragment.mViewPager
								.setCurrentItem(HomeFragment.pos);
					}

				} else {
					Utilities.showDialogNoBack(CustomFragmentActivity.this,
							UserFunctions.getInstance().getMessage()).show();
				}
			}
		}
	};

	private ArrayList<Integer> listDu = new ArrayList<Integer>();
	private int sizeofLishLocal;

	public AlertDialog showDialogNoBack(final Context context,
			final String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setNeutralButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
//						List<NameValuePair> params = new ArrayList<NameValuePair>();
//						params.add(new BasicNameValuePair("tag", "getGRO"));
//						UserFunctions.getInstance().getGRO(
//								CustomFragmentActivity.this, params, true);
//						dialog.dismiss();

					}
				});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.setCanceledOnTouchOutside(false);
		return alert;
	}

}
