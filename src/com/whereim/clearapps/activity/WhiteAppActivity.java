package com.whereim.clearapps.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hld.library.frame.EventBus;
import com.whereim.clearapps.AppActivity;
import com.whereim.clearapp.R;
import com.whereim.clearapps.adapter.AppListAdapter;
import com.whereim.clearapps.bean.PackageBean;
import com.whereim.clearapps.params.EventParams;
import com.whereim.clearapps.utils.DbFactory;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ����
 * @author User
 */
public class WhiteAppActivity extends AppActivity{
	private ListView listView;
	private List<PackageInfo> apps;
	private TextView textView;
	private AppListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_white_app);
		showTitleBackButton();
		setTitle(getString(R.string.whiteapp));
		listView=(ListView) findViewById(R.id.listView);
		textView=(TextView) findViewById(R.id.textView);
		
		List<PackageBean> list = DbFactory.getDb(this).findAll(PackageBean.class);
		Map<String, PackageBean> map=new HashMap<String, PackageBean>();
		for (int i = 0; i <list.size(); i++) {
			PackageBean bean=list.get(i);
			map.put(bean.getPackageName(), bean);
		}
		
		apps=new ArrayList<PackageInfo>();
		List<PackageInfo> allApps= getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < allApps.size(); i++) {
			PackageInfo info=allApps.get(i);
			PackageBean bean=map.get(info.applicationInfo.packageName);
			if(bean!=null){
				apps.add(info);
			}
		}
		
		if(apps.size()>0){
			adapter=new AppListAdapter(this,apps,null);
			listView.setAdapter(adapter);
			adapter.setOnClickListener(onClickListener);
		}else{//�����
			listView.setVisibility(View.INVISIBLE);
			textView.setVisibility(View.VISIBLE);
		}
	}
	private OnClickListener onClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v.getTag()!=null){
				PackageInfo info=(PackageInfo) v.getTag();
				apps.remove(info);
//				DbFactory.getDb(WhiteAppActivity.this).deleteById(PackageBean.class, info.getId());
				DbFactory.getDb(WhiteAppActivity.this).deleteByWhere(PackageBean.class, "packageName='"+info.applicationInfo.packageName+"'");
				if(adapter!=null){
					adapter.notifyDataSetChanged();
				}
				EventBus.post(EventParams.ACTION_REMOVE_APP, info.applicationInfo.packageName);
			}
		}
	};
}