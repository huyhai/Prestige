package com.g8.ui;

import com.g8.prestige.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Login extends Fragment {
	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_ac, container, false);
		initView(view);
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		
	}
}
