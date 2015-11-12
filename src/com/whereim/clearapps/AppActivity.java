package com.whereim.clearapps;

import android.graphics.Color;
import android.os.Bundle;

import com.fljr.frame.fragment.FragmentActivityFrame;

public class AppActivity extends FragmentActivityFrame{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		setTitleBackgroundColor(Color.parseColor("#15db99"));
	}
}