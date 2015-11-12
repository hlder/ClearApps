package com.whereim.clearapps.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fljr.frame.EventBus;
import com.fljr.frame.eventbus.EventBusListener;
import com.fljr.frame.fragment.FragmentTabHostFrame;
import com.whereim.clearapps.AppActivity;
import com.whereim.clearapp.R;
import com.whereim.clearapps.bean.PackageBean;
import com.whereim.clearapps.fragment.AppListFragment;
import com.whereim.clearapps.params.EventParams;
import com.whereim.clearapps.utils.DbFactory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressLint("InflateParams") 
public class SettingActivity extends AppActivity implements OnClickListener,EventBusListener{
	private List<PackageInfo> allList;
	private List<PackageInfo> userList;
	private List<PackageInfo> sysList;
	
	private FragmentTabHostFrame tabHost;
	private AppListFragment userApp;
	private AppListFragment sysApp;
	private AppListFragment allApp;
	private View titleView;
	private View btnWhite;
	private Map<String, PackageBean> whiteApps;//白名单
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setTitle(getString(R.string.set));
		titleView=getLayoutInflater().inflate(R.layout.view_app_set_title, null);
		setTitleRightView(titleView);
		init();
		EventBus.register(this, EventParams.ACTION_REMOVE_APP);
	}
	private void init() {
		whiteApps=listToMap(DbFactory.getDb(this).findAll(PackageBean.class));
		
		btnWhite=titleView.findViewById(R.id.btnWhite);
		btnWhite.setOnClickListener(this);
		
		userList=new ArrayList<PackageInfo>();
		sysList=new ArrayList<PackageInfo>();
		allList= getPackageManager().getInstalledPackages(0);
		parserList();
		
		allApp=new AppListFragment(allList,whiteApps);
		userApp=new AppListFragment(userList,whiteApps);
		sysApp=new AppListFragment(sysList,whiteApps);
		
		tabHost=(FragmentTabHostFrame) findViewById(R.id.tabHost);
		tabHost.addTab(tabHost.newTabSpec().setIndicator("应用软件"), userApp);
		tabHost.addTab(tabHost.newTabSpec().setIndicator("系统软件"), sysApp);
		tabHost.addTab(tabHost.newTabSpec().setIndicator("所有软件"), allApp);
		tabHost.setTextSelectedColor(Color.RED);
	}
	private Map<String, PackageBean> listToMap(List<PackageBean> list) {
		Map<String, PackageBean> temp=new HashMap<String, PackageBean>();
		for (int i = 0; i < list.size(); i++) {
			PackageBean bean=list.get(i);
			temp.put(bean.getPackageName(), bean);
		}
		return temp;
	}
	
	public List<PackageInfo> parserList() {
		PackageInfo temp=null;
		for (int i = 0; i < allList.size(); i++) {
			PackageInfo info=allList.get(i);
			if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                //非系统应用
        		if(!"com.whereim.clearapps".equals(info.packageName)){
        			userList.add(info);
        		}else{
        			temp=info;
        		}
            } else {
                //系统应用
            	sysList.add(info);
            }
		}
		if(temp!=null){
			allList.remove(temp);
		}
		return userList;
	}
	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.btnWhite:
			intent.setClass(this, WhiteAppActivity.class);
			break;
		}
		startActivity(intent);
	}
	@Override
	public void onEvent(String action, Object obj) {
		whiteApps.remove(obj);
		EventBus.post(EventParams.ACTION_DATA_CHANAGE, obj);
	}
}