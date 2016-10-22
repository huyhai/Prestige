package com.g8.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.g8.adapter.BannerAdapter;
import com.g8.common.ConstantValue;
import com.g8.common.Utilities;
import com.g8.control.UserFunctions;
import com.g8.control.UserFunctions.GROModel;
import com.g8.libs.ExtendedViewPager;
import com.g8.prestige.CustomFragmentActivity;
import com.g8.prestige.MainActivity;
import com.g8.prestige.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener {
	private static LinearLayout rl;
	// private DirectionalViewPager pagerAdBanner;
	private Button btnLeft;
	private Button btnRight;
	public static BannerAdapter adBannerAdapter;
	public static TextView tvNameValue;
	private TextView tvNationality;
	private TextView tvHeight;
	private TextView tvWeight;
	private TextView tvLanguage;
//	public static ExtendedViewPager mViewPager;
	public static OnPageChangeListener pageChangeListener;
	private RelativeLayout rlLish;
	public static ArrayList<GROModel> listImagesModelNew = new ArrayList<UserFunctions.GROModel>();

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		adBannerAdapter = new BannerAdapter(getActivity(), listImagesModelNew);
		// pagerAdBanner.setAdapter(adBannerAdapter);
		mViewPager.setAdapter(adBannerAdapter);
		// btnLeft.setOnClickListener(this);
		// btnRight.setOnClickListener(this);
		btnLeft.setVisibility(View.GONE);
		btnRight.setVisibility(View.GONE);
		initBanner();
		Utilities.getGlobalVariable(getActivity()).isHome = true;
		getGRO();

		pageChangeListener = new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int position) {
				pos = position;
				if (!(UserFunctions.getInstance().getListGRO().size() == 0)) {
					tvHeight.setText(UserFunctions.getInstance().getListGRO()
							.get(pos).getHeight()
							+ " m");
					tvLanguage.setText(UserFunctions.getInstance().getListGRO()
							.get(pos).getLanguage());
					tvNameValue.setText(UserFunctions.getInstance()
							.getListGRO().get(pos).getFullName());
					tvNationality.setText(UserFunctions.getInstance()
							.getListGRO().get(pos).getNationality());
					tvWeight.setText(UserFunctions.getInstance().getListGRO()
							.get(pos).getWeight()
							+ " kg");
				}

			}
		};
		mViewPager.setOnPageChangeListener(pageChangeListener);
		// if (!(UserFunctions.getInstance().getListGRO().size() == 0)) {
		//
		// }
		pageChangeListener.onPageSelected(0);
		rlLish.setOnClickListener(this);
	}

	private void getGRO() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "getRoom"));
		UserFunctions.getInstance().getRoom(getActivity(), params, false);
		// Log.v("HAI","call");
	}

	// private boolean isLoadAgaint;
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equalsIgnoreCase(UserFunctions.GETRROOM)) {
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
				} else {
				}
			}
			if (intent.getAction().equalsIgnoreCase(UserFunctions.GETGRO)) {
				MainActivity.mDrawerLayout.closeDrawers();
				if (intent.getExtras().getBoolean(ConstantValue.IS_SUCCESS)) {
					// isLoadAgaint = true;
					listImagesModelNew.remove(pos);
					adBannerAdapter.notifyDataSetChanged();
					mViewPager.setAdapter(adBannerAdapter);
					if (pos < 1) {
						pageChangeListener.onPageSelected(0);
						mViewPager.setCurrentItem(0);
					} else {
						pageChangeListener.onPageSelected(pos - 1);
						mViewPager.setCurrentItem(pos);
					}

				} else {
					CustomFragmentActivity.setBook(0);
					mViewPager.setCurrentItem(pos);
				}
			}
		}
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		rlLish.setVisibility(View.VISIBLE);
		IntentFilter intentGetKeySend = new IntentFilter();
		intentGetKeySend.addAction(UserFunctions.GETRROOM);
		intentGetKeySend.addAction(UserFunctions.GETGRO);
		getActivity().registerReceiver(receiver, intentGetKeySend);
	}

	private void initBanner() {
		// if (!(UserFunctions.getInstance().getListGRO().size() == 0)) {

		// adBannerAdapter = new BannerAdapter(getActivity(), UserFunctions
		// .getInstance().getListGRO());
		// // pagerAdBanner.setAdapter(adBannerAdapter);
		// mViewPager.setAdapter(adBannerAdapter);
		// if(isLoadAgaint&&)
		listImagesModelNew.clear();
		listImagesModelNew.addAll(UserFunctions.getInstance().getListGRO());
		adBannerAdapter.notifyDataSetChanged();
		// if (pos < 1) {
		// mViewPager.setCurrentItem(0);
		//
		// } else {
		// mViewPager.setCurrentItem(pos);
		// }

		// setadapter();
		// mViewPager.setCurrentItem(UserFunctions.getInstance().getListGRO().size());

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (Utilities.getGlobalVariable(getActivity()).device_width / 60),
				(int) (Utilities.getGlobalVariable(getActivity()).device_height / 98.46));
		params.weight = 1.0f;
		// tvHeight.setText(UserFunctions.getInstance().getListGRO().get(pos)
		// .getHeight()
		// + " m");
		// tvLanguage.setText(UserFunctions.getInstance().getListGRO()
		// .get(pos).getLanguage());
		// tvNameValue.setText(UserFunctions.getInstance().getListGRO()
		// .get(pos).getFullName());
		// tvNationality.setText(UserFunctions.getInstance().getListGRO()
		// .get(pos).getNationality());
		// tvWeight.setText(UserFunctions.getInstance().getListGRO().get(pos)
		// .getWeight()
		// + " kg");
		// } else {
		// adBannerAdapter = new BannerAdapter(getActivity(), UserFunctions
		// .getInstance().getListGRO());
		// // pagerAdBanner.setAdapter(adBannerAdapter);
		// mViewPager.setAdapter(adBannerAdapter);
		// }
		// LinearLayout lnTv;
		// listButton = new ArrayList<Button>();
		// for (int i = 0; i < ExList.size(); i++) {
		// Button btnChoise = new Button(getActivity());
		// if (i == 0) {
		// btnChoise.setBackgroundResource(R.drawable.icon_choise);
		// } else {
		// btnChoise.setBackgroundResource(R.drawable.icon_not_choise);
		// }
		// params.leftMargin = 5;
		// btnChoise.setLayoutParams(params);
		// listButton.add(btnChoise);
		// lnChoise.addView(btnChoise);
		// }
		// setAutoPager(0);
	}

	// Handler handler = new Handler();
	//
	// public void setAutoPager(final int index) {
	// handler.removeMessages(0);
	// handler.postDelayed(new Runnable() {
	// public void run() {
	// if (index < ExList.size() - 1) {
	// pagerAdBanner.setCurrentItem(index + 1);
	// } else if (index == ExList.size() - 1) {
	// pagerAdBanner.setCurrentItem(0);
	// }
	// }
	// }, 3000);
	// }

	TextView tvRoomValue;

	private void initView(View view) {
		// pagerAdBanner = (DirectionalViewPager) view
		// .findViewById(R.id.pagerAdBanner);
		rl = (LinearLayout) view.findViewById(R.id.rl);
		btnLeft = (Button) view.findViewById(R.id.btnLeft);
		btnRight = (Button) view.findViewById(R.id.btnRight);

		tvHeight = (TextView) view.findViewById(R.id.tvHeight);
		tvLanguage = (TextView) view.findViewById(R.id.tvLanguage);
		tvNameValue = (TextView) view.findViewById(R.id.tvNameValue);
		tvNationality = (TextView) view.findViewById(R.id.tvNationality);
		tvWeight = (TextView) view.findViewById(R.id.tvWeight);
		mViewPager = (ExtendedViewPager) view.findViewById(R.id.view_pager);
		rlLish = (RelativeLayout) getActivity().findViewById(R.id.rlLish);

	}

	public static void setmargin() {
		rl.setPadding(MainActivity.heght + 15, 15, 0, 0);
		// notifyDataSetChanged();
		// notifyAll();
	}

	public static void setmarginoff() {
		rl.setPadding(10, 15, 0, 0);
		// notifyDataSetChanged();
		// notifyAll();
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == rlLish) {
			((CustomFragmentActivity)getActivity()).replaceFragment(ConstantValue.BOOKEDLIST, false);
		}
		// if (arg0 == btnRight) {
		// pagerAdBanner.setCurrentItem(pos + 1);
		// }

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Utilities.getGlobalVariable(getActivity()).isHome = false;
		rlLish.setVisibility(View.GONE);
		try {
			getActivity().unregisterReceiver(receiver);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static int pos = 0;

}
