package com.g8.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.g8.common.ConstantValue;
import com.g8.common.Utilities;
import com.g8.control.UserFunctions;
import com.g8.libs.SessionManager;
import com.g8.prestige.CustomFragmentActivity;
import com.g8.prestige.MainActivity;
import com.g8.prestige.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeAc extends Fragment implements OnClickListener {
	private LinearLayout horizontalOuterLayout;
	private HorizontalScrollView horizontalScrollview;
	private int scrollMax;
	private int scrollPos = 0;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;
	private TimerTask faceAnimationSchedule;
	private Button clickedButton = null;
	private Timer scrollTimer = null;
	private Timer clickTimer = null;
	private Timer faceTimer = null;
	private Boolean isFaceDown = true;

	private Button btnRight;
	private Button btnLeft;
	private RelativeLayout rlLogo;
	private static RelativeLayout rlGirl;
	// private RelativeLayout rlLogin;
	private static TextView tvDes;
	private static RelativeLayout rlLoginEd;
	private RelativeLayout rlLock;
	private RelativeLayout rlLoginBtn;
	private RelativeLayout edUser1;
	private RelativeLayout edPass1;
	private EditText edUser;
	private EditText edPass;
	private CheckBox cbRemember;
	private ImageView imgLogo;
	private SessionManager session1;

	// private static ImtvLoginageView btnLogin;
	// private static TextView ;

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_ac, container, false);
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		tvDes.setText(Html.fromHtml(UserFunctions.getInstance()
				.getContactModel().getAbout_US()));
		CustomFragmentActivity.imageLoader2.displayImage(UserFunctions
				.getInstance().getContactModel().getImage_Logo(), imgLogo,
				CustomFragmentActivity.options);
		session1 = new SessionManager(getActivity());
		MainActivity.mDrawerLayout
				.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		// rlLogin.setOnClickListener(this);
		rlLoginBtn.setOnClickListener(this);

		LayoutParams lrlimgLogop = imgLogo.getLayoutParams();
		lrlimgLogop.width = (int) (Utilities.getGlobalVariable(getActivity()).device_width / 4);
		lrlimgLogop.height = (int) (Utilities.getGlobalVariable(getActivity()).device_height / 2.5);
		imgLogo.setLayoutParams(lrlimgLogop);

		LayoutParams lp = rlLogo.getLayoutParams();
		lp.height = (int) (Utilities.getGlobalVariable(getActivity()).device_height / 2.9);
		rlLogo.setLayoutParams(lp);
//		LayoutParams lprlGirl = rlGirl.getLayoutParams();
//		lprlGirl.height = (int) (Utilities.getGlobalVariable(getActivity()).device_height / 2);
//		rlGirl.setLayoutParams(lprlGirl);

		LayoutParams lprlLock = rlLock.getLayoutParams();
		lprlLock.width = (int) (Utilities.getGlobalVariable(getActivity()).device_width / 15);
		lprlLock.height = (int) (Utilities.getGlobalVariable(getActivity()).device_height / 13);
		rlLock.setLayoutParams(lprlLock);
		LayoutParams lprlLoginBtn = rlLoginBtn.getLayoutParams();
		lprlLoginBtn.width = (int) (Utilities.getGlobalVariable(getActivity()).device_width / 6);
		lprlLoginBtn.height = lprlLock.height;
		rlLoginBtn.setLayoutParams(lprlLoginBtn);

		LayoutParams lpedUser = edUser1.getLayoutParams();
		lpedUser.width = (int) (Utilities.getGlobalVariable(getActivity()).device_width / 3.5);
		lpedUser.height = (Utilities.getGlobalVariable(getActivity()).device_height / 12);
		edUser1.setLayoutParams(lpedUser);

		LayoutParams lpedPass = edPass1.getLayoutParams();
		lpedPass.width = lpedUser.width;
		lpedPass.height = (Utilities.getGlobalVariable(getActivity()).device_height / 12);
		edPass1.setLayoutParams(lpedPass);

		w = Utilities.getGlobalVariable(getActivity()).device_width / 4;
		// horizontalTextView = (TextView)
		// findViewById(R.id.horizontal_textview_id);
		horizontalScrollview.setHorizontalScrollBarEnabled(false);
		// horizontalScrollview.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// scrollPos=horizontalScrollview.getScrollX();
		// Log.v("HAI", "onTouch");
		//
		// return false;
		// }
		// });
		addImagesToView();

		ViewTreeObserver vto = horizontalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				horizontalOuterLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}
		});
		// btnLeft.setOnClickListener(this);
		// btnRight.setOnClickListener(this);
		btnLeft.setVisibility(View.GONE);
		btnRight.setVisibility(View.GONE);

	}

	private void initView(View view) {
		btnRight = (Button) view.findViewById(R.id.btnRight);
		btnLeft = (Button) view.findViewById(R.id.btnLeft);
		horizontalScrollview = (HorizontalScrollView) view
				.findViewById(R.id.horiztonal_scrollview_id);
		horizontalOuterLayout = (LinearLayout) view
				.findViewById(R.id.horiztonal_outer_layout_id);
		rlLogo = (RelativeLayout) view.findViewById(R.id.rlLogo);
		rlGirl = (RelativeLayout) view.findViewById(R.id.rlGirl);
		// rlLogin = (RelativeLayout) view.findViewById(R.id.rlLogin);
		tvDes = (TextView) view.findViewById(R.id.tvDes);
		rlLoginEd = (RelativeLayout) view.findViewById(R.id.rlLoginEd);
		rlLock = (RelativeLayout) view.findViewById(R.id.rlLock);
		rlLoginBtn = (RelativeLayout) view.findViewById(R.id.rlLoginBtn);
		edUser1 = (RelativeLayout) view.findViewById(R.id.rledUser1);
		edPass1 = (RelativeLayout) view.findViewById(R.id.rledpass1);
		cbRemember = (CheckBox) view.findViewById(R.id.cb);
		imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
		edUser = (EditText) view.findViewById(R.id.edUser);
		edPass = (EditText) view.findViewById(R.id.edPass);
		imgLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// horizontalScrollview.smoothScrollTo(scrollMax, 0);

			}
		});

	}

	int actualWidth;

	public void getScrollMaxAmount() {
		actualWidth = (int) (horizontalOuterLayout.getMeasuredWidth() - (w * UserFunctions
				.getInstance().getListAllGRO().size()));
		Log.v("HAI", horizontalOuterLayout.getMeasuredWidth() + "");
		Log.v("HAI", actualWidth + "");
		scrollMax = actualWidth;
	}

	public void startAutoScrolling() {
		if (scrollTimer == null) {
			scrollTimer = new Timer();
			final Runnable Timer_Tick = new Runnable() {
				public void run() {
					moveScrollView();
					if (horizontalScrollview.getScrollX() >= scrollMax) {
						addImagesToView();

						scrollMax += actualWidth;
						Log.v("HAI", "aaa" + scrollPos + " " + scrollMax);
					}
				}
			};

			if (scrollerSchedule != null) {
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask() {
				@Override
				public void run() {
					getActivity().runOnUiThread(Timer_Tick);
				}
			};

			scrollTimer.schedule(scrollerSchedule, 30, 30);
		}
	}


	public void moveScrollView() {
		scrollPos = (int) (horizontalScrollview.getScrollX() + 1.0);
		horizontalScrollview.scrollTo(scrollPos, 0);
		Log.d("HAI", scrollPos + "");

	}

	int w;

	/** Adds the images to view. */
	public void addImagesToView() {
		for (int i = 0; i < UserFunctions.getInstance().getListAllGRO().size(); i++) {
			final ImageView imageButton = new ImageView(getActivity());
			imageButton.setScaleType(ScaleType.FIT_XY);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
					350);
			params.setMargins(5, 10, 5, 10);
			imageButton.setLayoutParams(params);
			LayoutParams lp = imageButton.getLayoutParams();
			lp.width = (int) (Utilities.getGlobalVariable(getActivity()).device_width / 3.5);
//			lp.height = (int) (Utilities.getGlobalVariable(getActivity()).device_height / 2);

			imageButton.setLayoutParams(lp);
			CustomFragmentActivity.imageLoader2.displayImage(UserFunctions
					.getInstance().getListAllGRO().get(i).getImages(),
					imageButton, CustomFragmentActivity.options);
			imageButton.setTag(i);
			imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (isFaceDown) {
						if (clickTimer != null) {
							clickTimer.cancel();
							clickTimer = null;
						}
						// clickedButton = (Button) arg0;
						// stopAutoScrolling();
						// clickedButton.startAnimation(scaleFaceUpAnimation());
						// clickedButton.setSelected(true);
						clickTimer = new Timer();

						if (clickSchedule != null) {
							clickSchedule.cancel();
							clickSchedule = null;
						}

						clickSchedule = new TimerTask() {
							public void run() {
								startAutoScrolling();
							}
						};

						clickTimer.schedule(clickSchedule, 1500);
					}
				}
			});

			horizontalOuterLayout.addView(imageButton);

		}

	}

	// public Animation scaleFaceUpAnimation() {
	// Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
	// Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
	// 0.5f);
	// scaleFace.setDuration(500);
	// scaleFace.setFillAfter(true);
	// scaleFace.setInterpolator(new AccelerateInterpolator());
	// Animation.AnimationListener scaleFaceAnimationListener = new
	// Animation.AnimationListener() {
	// @Override
	// public void onAnimationStart(Animation arg0) {
	// // horizontalTextView.setText(nameArray[(Integer) clickedButton
	// // .getTag()]);
	// isFaceDown = false;
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation arg0) {
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation arg0) {
	// if (faceTimer != null) {
	// faceTimer.cancel();
	// faceTimer = null;
	// }
	//
	// faceTimer = new Timer();
	// if (faceAnimationSchedule != null) {
	// faceAnimationSchedule.cancel();
	// faceAnimationSchedule = null;
	// }
	// faceAnimationSchedule = new TimerTask() {
	// @Override
	// public void run() {
	// faceScaleHandler.sendEmptyMessage(0);
	// }
	// };
	//
	// faceTimer.schedule(faceAnimationSchedule, 750);
	// }
	// };
	// scaleFace.setAnimationListener(scaleFaceAnimationListener);
	// return scaleFace;
	// }

	// private Handler faceScaleHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// if (clickedButton.isSelected() == true)
	// clickedButton.startAnimation(scaleFaceDownAnimation(500));
	// }
	// };
	//
	// public Animation scaleFaceDownAnimation(int duration) {
	// Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f,
	// Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
	// 0.5f);
	// scaleFace.setDuration(duration);
	// scaleFace.setFillAfter(true);
	// scaleFace.setInterpolator(new AccelerateInterpolator());
	// Animation.AnimationListener scaleFaceAnimationListener = new
	// Animation.AnimationListener() {
	// @Override
	// public void onAnimationStart(Animation arg0) {
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation arg0) {
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation arg0) {
	// // horizontalTextView.setText("");
	// isFaceDown = true;
	// }
	// };
	// scaleFace.setAnimationListener(scaleFaceAnimationListener);
	// return scaleFace;
	// }

	public void stopAutoScrolling() {
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer = null;
		}
	}

	public void onDestroy() {
		MainActivity.mDrawerLayout
				.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		clearTimerTaks(clickSchedule);
		clearTimerTaks(scrollerSchedule);
		clearTimerTaks(faceAnimationSchedule);
		clearTimers(scrollTimer);
		clearTimers(clickTimer);
		clearTimers(faceTimer);

		clickSchedule = null;
		scrollerSchedule = null;
		faceAnimationSchedule = null;
		scrollTimer = null;
		clickTimer = null;
		faceTimer = null;

		super.onDestroy();
		try {
			getActivity().unregisterReceiver(receiver);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void clearTimers(Timer timer) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void clearTimerTaks(TimerTask timerTask) {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	private void callLogin() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "login"));
		params.add(new BasicNameValuePair("username", edUser.getText()
				.toString().trim()));
		params.add(new BasicNameValuePair("pass", edPass.getText().toString()
				.trim()));
		UserFunctions.getInstance().callLoginUser(getActivity(), params, true);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(UserFunctions.LOGINUSER)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					Utilities.getGlobalVariable(getActivity()).isLogin = true;
					((CustomFragmentActivity) getActivity())
							.resetListFragment();
					// if (!UserFunctions.getInstance().getUserModel()
					// .getMailling().equals("0")) {
					// ss.saveMailling(true);
					// }
					// if (ss.isMailling()) {
					((CustomFragmentActivity) getActivity()).replaceFragment(
							ConstantValue.HOME, false);
					// } else {
					// ac.replaceFragment(ConstantValue.HOME_FRAGMENT, false);
					// ac.replaceFragment(ConstantValue.MAILING, false);
					// }

					if (cbRemember.isChecked()) {
						session1.saveLogin(edUser.getText().toString().trim(),
								edPass.getText().toString().trim(), true);
					}
					// Utilities.showDialogNoBack(getActivity(),
					// UserFunctions.getInstance().getMessage()).show();
				} else {
					Utilities.getGlobalVariable(getActivity()).isLogin = false;
					Utilities.showDialogNoBack(getActivity(),
							UserFunctions.getInstance().getMessage()).show();
				}
			}
		}
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		IntentFilter intentGetKeySend = new IntentFilter();
		intentGetKeySend.addAction(UserFunctions.LOGINUSER);
		getActivity().registerReceiver(receiver, intentGetKeySend);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == btnLeft) {
			horizontalScrollview.scrollTo(
					(int) horizontalScrollview.getScrollX() - 100,
					(int) horizontalScrollview.getScrollX());
		}
		if (arg0 == btnRight) {

			horizontalScrollview.scrollTo(
					(int) horizontalScrollview.getScrollX() + 100,
					(int) horizontalScrollview.getScrollY());
		}
		if (arg0 == rlLoginBtn) {
			// ((CustomFragmentActivity) getActivity()).replaceFragment(
			// ConstantValue.HOME, false);
			callLogin();
		}

	}

	public static void set() {

		if (CustomFragmentActivity.ischooseLogin) {
			rlGirl.setVisibility(View.GONE);
			tvDes.setVisibility(View.GONE);
			rlLoginEd.setVisibility(View.VISIBLE);
			CustomFragmentActivity.ischooseLogin = false;

		} else {
			rlGirl.setVisibility(View.VISIBLE);
			tvDes.setVisibility(View.VISIBLE);
			rlLoginEd.setVisibility(View.GONE);
			CustomFragmentActivity.ischooseLogin = true;
		}
	}
}
