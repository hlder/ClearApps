package com.whereim.clearapps.adapter;

import java.util.List;
import java.util.Map;

import com.whereim.clearapps.bean.PackageBean;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.view.View;
import android.view.ViewGroup;

public class WhiteAppAdatr extends AppListAdapter{
	public WhiteAppAdatr(Activity context, List<PackageInfo> packages,
			Map<String, PackageBean> whiteApps) {
		super(context, packages, whiteApps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
