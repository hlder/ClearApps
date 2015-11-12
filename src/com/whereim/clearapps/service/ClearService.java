package com.whereim.clearapps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whereim.clearapps.R;
import com.whereim.clearapps.bean.PackageBean;
import com.whereim.clearapps.utils.CmdUtils;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class ClearService extends Service{
	private String clearSuccess,clearFail;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		clearSuccess=getString(R.string.toast_clear_success);
		clearFail=getString(R.string.toast_clear_fail);
		clearApps();
	}
	
	private void clearApps() {
    	List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
    	Map<String, PackageBean> map=CmdUtils.getWhiteApps(this);
    	List<String> cmds=new ArrayList<String>();
        for (int i = 0; i < packages.size(); i++) {
        	PackageInfo packageInfo = packages.get(i);
        	if("com.whereim.clearapps".equals(packageInfo.applicationInfo.packageName)){
        		continue;
        	}
        	if(map.get(packageInfo.applicationInfo.packageName)==null){//不在白名单，需要清理
        		cmds.add("am force-stop "+packageInfo.packageName+" \n");
        	}
		}
        CmdUtils.doCmd(cmds,handler);
	}
	 private Handler handler=new Handler(){
	    	public void handleMessage(android.os.Message msg) {
	    		super.handleMessage(msg);
	    		switch (msg.what) {
				case 0://成功
					Toast.makeText(ClearService.this, clearSuccess, Toast.LENGTH_SHORT).show();
					break;
				case 1://失败
					Toast.makeText(ClearService.this, clearFail, Toast.LENGTH_SHORT).show();
					break;
				}
	    		ClearService.this.stopSelf();
	    	}
	    };
}