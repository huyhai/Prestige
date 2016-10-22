package com.g8.adapter;

import java.util.ArrayList;

import com.g8.common.Utilities;
import com.g8.control.UserFunctions.AllGROModel;
import com.g8.control.UserFunctions.GROModel;
import com.g8.libs.TouchImageView;
import com.g8.prestige.CustomFragmentActivity;
import com.g8.prestige.MainActivity;
import com.g8.prestige.R;
import com.g8.ui.ViewImages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookedAdapter extends PagerAdapter {

	static class ViewHolder {
	}

	@SuppressWarnings("unused")
	private ArrayList<AllGROModel> listBanner = new ArrayList<AllGROModel>();
	private LayoutInflater inflater;
	private TouchImageView imgBanner;
	private Activity act;

	public BookedAdapter(final Activity _act, ArrayList<AllGROModel> _listBanner) {
		this.listBanner = _listBanner;
		this.inflater = _act.getLayoutInflater();
		act = _act;
	}

	public BookedAdapter() {
		// act = _act;
	}
	public void setData(ArrayList<AllGROModel> _listBanner) {
		this.listBanner = _listBanner;
	}

	@Override
	public void destroyItem(final View container, final int position,
			final Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(final View container) {
	}

	@Override
	public int getCount() {
		return this.listBanner.size();
	}

	@Override
	public Object instantiateItem(final View view, final int position) {

		final View imageLayout = this.inflater.inflate(
				R.layout.booked_layout, null);
		imgBanner = (TouchImageView) imageLayout.findViewById(R.id.imgBanner);
		// tvHeight= (TextView) imageLayout.findViewById(R.id.tvHeight);
		// tvLanguage= (TextView) imageLayout.findViewById(R.id.tvLanguage);
		// tvNameValue= (TextView) imageLayout.findViewById(R.id.tvNameValue);
		// tvNationality= (TextView)
		// imageLayout.findViewById(R.id.tvNationality);
		// tvWeight= (TextView) imageLayout.findViewById(R.id.tvWeight);
		((ViewPager) view).addView(imageLayout, 0);

		CustomFragmentActivity.imageLoader2.displayImage(
				listBanner.get(position).getImages(), imgBanner,
				CustomFragmentActivity.options);
		// tvHeight.setText(listBanner.get(position).getHeight()+" m");
		// tvLanguage.setText(listBanner.get(position).getLanguage());
		// tvNameValue.setText(listBanner.get(position).getFullName());
		// tvNationality.setText(listBanner.get(position).getNationality());
		// tvWeight.setText(listBanner.get(position).getWeight()+" kg");
		// CustomFragmentActivity.setInfo(position);
		// imgBanner.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent i =new Intent(act,ViewImages.class);
		// i.putExtra("A", listBanner.get(position).getImages());
		// act.startActivity(i);
		//
		// }
		// });
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(final View view, final Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(final Parcelable state, final ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(final View container) {
	}

	public void recycleImg() {
		// Utilities.recycle(imgBanner);
	}

	public void reset(final ArrayList<AllGROModel> lish) {
//		ca.runOnUiThread(new Runnable() {
//			public void run() {
				listBanner.clear();
				listBanner.addAll(lish);
				this.notifyDataSetChanged();
				
//			}
//		});
	}

}
