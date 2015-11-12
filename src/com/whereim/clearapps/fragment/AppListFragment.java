package com.whereim.clearapps.fragment;

import java.util.List;
import java.util.Map;

import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fljr.frame.EventBus;
import com.fljr.frame.Fragment;
import com.fljr.frame.eventbus.EventBusListener;
import com.whereim.clearapp.R;
import com.whereim.clearapps.adapter.AppListAdapter;
import com.whereim.clearapps.bean.PackageBean;
import com.whereim.clearapps.params.EventParams;
import com.whereim.clearapps.utils.DbFactory;

public class AppListFragment extends Fragment implements EventBusListener{
	private List<PackageInfo> list;
	private ListView listView;
	private AppListAdapter adapter;

	private Map<String, PackageBean> whiteApps;//白名单
	
	public AppListFragment(List<PackageInfo> list,Map<String, PackageBean> whiteApps) {
		this.list=list;
		this.whiteApps=whiteApps;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		listView=new ListView(getActivity());
		listView.setBackgroundColor(Color.WHITE);
		adapter=new AppListAdapter(getActivity(), list,whiteApps);
		adapter.setOnClickListener(onClickListener);
		listView.setAdapter(adapter);

		EventBus.register(this, EventParams.ACTION_DATA_CHANAGE);
		return listView;
	}
	
	private OnClickListener onClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {//添加或取消白名单
			if(v.getTag()==null){
				return;
			}
			PackageInfo info=(PackageInfo) v.getTag();
			PackageBean bean = whiteApps.get(info.applicationInfo.packageName);
			if(bean!=null){//存在白名单
				//从白名单中移除
				DbFactory.getDb(getActivity()).deleteByWhere(PackageBean.class, "packageName='"+bean.getPackageName()+"'");
				whiteApps.remove(bean.getPackageName());
				
				Toast.makeText(getActivity(), getString(R.string.msg_remove_success)+":"+info.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(), Toast.LENGTH_SHORT).show();
			}else{
				bean=new PackageBean();
				bean.setPackageName(info.applicationInfo.packageName);
				DbFactory.getDb(getActivity()).save(bean);
				whiteApps.put(bean.getPackageName(), bean);
				Toast.makeText(getActivity(), getString(R.string.msg_add_success)+":"+info.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(), Toast.LENGTH_SHORT).show();
			}
			adapter.notifyDataSetChanged();
		}
	};
	@Override
	public void onEvent(String action, Object obj) {
		handler.sendEmptyMessage(1);
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			adapter.notifyDataSetChanged();
		}
	};
}