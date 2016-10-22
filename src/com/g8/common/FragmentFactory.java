package com.g8.common;




import com.g8.ui.BookedFragment;
import com.g8.ui.HomeAc;
import com.g8.ui.HomeFragment;

import android.support.v4.app.Fragment;

public class FragmentFactory {

	public static Fragment getFragmentByKey(final int key) {
		/**
		 * All class in app will int below with key_value and return Fragment by
		 * name class fragment
		 */
		switch (key) {
		case ConstantValue.HOME:
			return new HomeFragment();
		case ConstantValue.HOMEFIRST:
			return new HomeAc();
		case ConstantValue.BOOKEDLIST:
			return new BookedFragment();
//		case ConstantValue.CONTACT:
//			return new Contact();
//		case ConstantValue.PROFILE:
//			return new Profile();
//		case ConstantValue.LOGIN:
//			return new Login();
//		case ConstantValue.REWARDS:
//			return new Rewards();
//		case ConstantValue.RESERVATION:
//			return new Reservation();
//		case ConstantValue.AD:
//			return new Ad();
//		case ConstantValue.FORGOTPASS:
//			return new ForgotPass();
//		case ConstantValue.SHOWMORE:
//			return new ShowMore();
//		case ConstantValue.VIEWIMAGE:
//			return new ViewImages();
//		case ConstantValue.FEEDBACK:
//			return new Feedback();
		default:
			return null;
		}
	}

}
