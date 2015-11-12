package com.whereim.clearapps.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.view.Window;

import com.whereim.clearapps.R;
import com.whereim.clearapps.bean.PackageBean;
import com.whereim.clearapps.utils.DbFactory;

public class WelComeActivity extends Activity{
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		new Thread(){
			public void run() {
				fristStart();
				Intent intent=new Intent(WelComeActivity.this,MainActivity.class);
				startActivity(intent);
				WelComeActivity.this.finish();
			};
		}.start();
	}
	
	
	
	
	private void fristStart() {
		boolean flag=getSharedPreferences("com.whereim.clearapps", 0).getBoolean("frist", true);
		if(flag){//首次进入软件，将系统软件全部加入白名单
			insertSysApps();
			getSharedPreferences("com.whereim.clearapps", 0).edit().putBoolean("frist", false).commit();
		}
		
	}
	private void insertSysApps() {
		List<PackageInfo> allApps= getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < allApps.size(); i++) {
			PackageInfo info=allApps.get(i);
			if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				PackageBean bean=new PackageBean();
				bean.setPackageName(info.applicationInfo.packageName);
				DbFactory.getDb(this).save(bean);
			}
		}
	}
}
