package com.whereim.clearapps.receiver;

import com.whereim.clearapps.service.ClearService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenOffReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("dddd", "action:"+intent.getAction());
		context.startService(new Intent(context,ClearService.class));
	}
}