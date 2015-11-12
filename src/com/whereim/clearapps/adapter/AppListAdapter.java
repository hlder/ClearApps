package com.whereim.clearapps.adapter;

import java.util.List;
import java.util.Map;

import com.whereim.clearapps.R;
import com.whereim.clearapps.bean.PackageBean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams") 
public class AppListAdapter extends BaseAdapter{
	private List<PackageInfo> packages;
	private Context context;
	private LayoutInflater inflater;
	private Bitmap loadImg;
	private OnClickListener onClickListener=null;
	private Map<String, PackageBean> whiteApps;//白名单
	
	private String addToWhite,rmFromWhite;
	
	public AppListAdapter(Activity context,List<PackageInfo> packages,Map<String, PackageBean> whiteApps) {
		this.context=context;
		this.packages=packages;
		this.whiteApps=whiteApps;
		inflater=context.getLayoutInflater();
		addToWhite=context.getString(R.string.add_to_white);
		rmFromWhite=context.getString(R.string.rm_from_white);
		
		loadImg=BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.view_app_info, null);
			vh=new ViewHolder();
			vh.imageView=(ImageView) convertView.findViewById(R.id.imageView);
			vh.textView=(TextView) convertView.findViewById(R.id.textView);
			vh.addToWite=(TextView) convertView.findViewById(R.id.addToWite);
			vh.addToWite.setOnClickListener(onClickListener);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		PackageInfo info=packages.get(position);
		if(info!=null&&info.applicationInfo!=null){
			ApplicationInfo app=info.applicationInfo;
			vh.imageView.setImageDrawable(app.loadIcon(context.getPackageManager()));
			vh.textView.setText(app.loadLabel(context.getPackageManager()).toString());
			if(whiteApps!=null){
				PackageBean bean = whiteApps.get(info.applicationInfo.packageName);
				if(bean!=null){//表示在白名单中
					vh.addToWite.setText(rmFromWhite);
//					vh.addToWite.setBackgroundColor(removeColor);
//					vh.addToWite.setBackgroundDrawable(removeDrawable);
					vh.addToWite.setBackgroundResource(R.drawable.btn_remove);
				}else{//不在白名单中
					vh.addToWite.setText(addToWhite);
//					vh.addToWite.setBackgroundColor(addColor);
//					vh.addToWite.setBackgroundDrawable(addDrawable);
					vh.addToWite.setBackgroundResource(R.drawable.btn_add);
				}
			}else{
				vh.addToWite.setText(rmFromWhite);
			}
		}else{
			vh.imageView.setImageBitmap(loadImg);
			vh.textView.setText("null");
		}
		vh.addToWite.setTag(info);
		return convertView;
	}
	
	private class ViewHolder{
		ImageView imageView;
		TextView textView;
		TextView addToWite;
	}
	
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	@Override
	public int getCount() {
		return packages.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	
	@Override
	public long getItemId(int position) {
		return 0;
	}
}
