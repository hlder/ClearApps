package com.whereim.clearapps.activity;

import com.whereim.clearapp.R;
import com.whereim.clearapps.view.ClearAnimView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ClearAppActivity extends Activity {
	private ClearAnimView clearAnimView;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);
		clearAnimView=(ClearAnimView) findViewById(R.id.clearAnimView);
		clearAnimView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearAnimView.startAnim();
			}
		});
	}
	
}