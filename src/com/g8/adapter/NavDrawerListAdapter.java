package com.g8.adapter;

import com.g8.prestige.R;
import com.g8.ui.HomeAc;

import java.util.ArrayList;

import com.g8.common.Utilities;
import com.g8.model.NavDrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {

	private Activity context;
	private RelativeLayout rlLock;
	private RelativeLayout rlLoginBtn;
	private RelativeLayout rlRoomNo;
	private RelativeLayout rlGRONo;
	private RelativeLayout rlRMNo;
	private RelativeLayout rlStatus;

	public NavDrawerListAdapter(Activity context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}

		// ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		rlLock = (RelativeLayout) convertView.findViewById(R.id.rlLock);
		rlLoginBtn = (RelativeLayout) convertView.findViewById(R.id.rlLoginBtn);

		rlRoomNo = (RelativeLayout) convertView.findViewById(R.id.rlRoomNo);
		rlGRONo = (RelativeLayout) convertView.findViewById(R.id.rlGRONo);
		rlRMNo = (RelativeLayout) convertView.findViewById(R.id.rlRMNo);
		rlStatus = (RelativeLayout) convertView.findViewById(R.id.rlStatus);

		rlRoomNo.setPadding(0,
				Utilities.getGlobalVariable(context).device_height / 15, 0,
				Utilities.getGlobalVariable(context).device_height / 15);
		rlGRONo.setPadding(0, Utilities.getGlobalVariable(context).device_height / 33, 0,  Utilities.getGlobalVariable(context).device_height / 33);
		rlRMNo.setPadding(0, Utilities.getGlobalVariable(context).device_height / 33, 0,  Utilities.getGlobalVariable(context).device_height / 33);
		rlStatus.setPadding(0, Utilities.getGlobalVariable(context).device_height / 33, 0,  Utilities.getGlobalVariable(context).device_height / 33);

		LayoutParams lprlLock = rlLock.getLayoutParams();
		lprlLock.width = (int) (Utilities.getGlobalVariable(this.context).device_width / 15);
		lprlLock.height = (int) (Utilities.getGlobalVariable(this.context).device_height / 13);
		rlLock.setLayoutParams(lprlLock);
		LayoutParams lprlLoginBtn = rlLoginBtn.getLayoutParams();
		lprlLoginBtn.width = (int) (Utilities.getGlobalVariable(this.context).device_width / 6);
		lprlLoginBtn.height = lprlLock.height;
		rlLoginBtn.setLayoutParams(lprlLoginBtn);

		// displaying count
		// check whether it set visible or not

		return convertView;
	}

}
