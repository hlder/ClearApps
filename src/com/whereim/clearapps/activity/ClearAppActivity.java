package com.whereim.clearapps.activity;

import com.fljr.frame.EventBus;
import com.fljr.frame.eventbus.EventBusListener;
import com.whereim.clearapp.R;
import com.whereim.clearapps.params.EventParams;
import com.whereim.clearapps.service.ClearService;
import com.whereim.clearapps.view.ClearAnimView;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ClearAppActivity extends Activity implements EventBusListener{
	private ClearAnimView clearAnimView;
	private TextView txtStatus;
	private String status="";
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear);
		EventBus.register(this,EventParams.ACTION_CLEAR_OVER);
		
		txtStatus=(TextView) findViewById(R.id.txtStatus);
		clearAnimView=(ClearAnimView) findViewById(R.id.clearAnimView);
		clearAnimView.setOnClickListener(onClickListener);
		findViewById(R.id.btnSet).setOnClickListener(onClickListener);
		findViewById(R.id.btnWhite).setOnClickListener(onClickListener);
	}
	private OnClickListener onClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.clearAnimView:
				clearAnimView.startAnim();
				txtStatus.setText(""+getString(R.string.btn_clearing));
				Intent intent=new Intent(ClearAppActivity.this,ClearService.class);
				startService(intent);
				break;
			case R.id.btnSet:
				Intent intent2=new Intent(ClearAppActivity.this,SettingActivity.class);
				startActivity(intent2);
				break;
			case R.id.btnWhite:
				Intent intent3=new Intent(ClearAppActivity.this,WhiteAppActivity.class);
				startActivity(intent3);
				break;
			}
		}
	};
	private Runnable finshRunnable=new Runnable() {
		@Override
		public void run() {
			clearAnimView.stopAnim();
			txtStatus.setText(status);
		}
	};
	
	@Override
	public void onEvent(String action, Object obj) {
		if(action!=null&& action.equals(EventParams.ACTION_CLEAR_OVER)&&obj instanceof Boolean){
			runOnUiThread(finshRunnable);
			if((Boolean) obj){
				status=""+getString(R.string.toast_clear_success);
			}else{
				status=""+getString(R.string.msg_clear_fail);
			}
		}
	}
}