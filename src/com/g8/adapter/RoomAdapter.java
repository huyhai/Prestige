package com.g8.adapter;


import java.util.ArrayList;

import com.g8.common.Utilities;
import com.g8.control.UserFunctions.AdsModel;
import com.g8.control.UserFunctions.RoomModel;
import com.g8.prestige.CustomFragmentActivity;
import com.g8.prestige.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoomAdapter extends BaseAdapter {
	private Activity ac;
	private ArrayList<RoomModel> listData;

	static class ViewHolder {
		RelativeLayout button2;
		TextView tv1;
	}

	public RoomAdapter(final Activity _ac, ArrayList<RoomModel> arrayList) {
		this.listData = arrayList;
		this.ac = _ac;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(ac);
			convertView = inflater.inflate(R.layout.room_item, null);
			holder.button2 = (RelativeLayout) convertView.findViewById(R.id.button2);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv1.setText(listData.get(position).getRoomNumber());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((CustomFragmentActivity)ac).setRooom(listData.get(position).getRoomNumber());
				
			}
		});
		return convertView;
	}

}
