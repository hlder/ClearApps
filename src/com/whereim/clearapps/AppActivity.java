package com.whereim.clearapps;

import android.os.Bundle;

import com.hld.library.frame.fragment.FragmentActivityFrame;
import com.whereim.clearapp.R;

public class AppActivity extends FragmentActivityFrame{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
//		setTitleBackgroundColor(Color.parseColor("#15db99"));
//		setTitleBackgroundColor(R.color.titleView);
		setTitleBackground(R.color.titleView);
	}
}