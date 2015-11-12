package com.whereim.clearapps.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whereim.clearapps.bean.PackageBean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CmdUtils {
	public static void doCmd(final List<String> cmds,final Handler handler){
		new Thread(){
			public void run() {
				try {
					Process process=Runtime.getRuntime().exec("su");
					BufferedReader info=new BufferedReader(new InputStreamReader(process.getInputStream()));
//					BufferedReader error=new BufferedReader(new InputStreamReader(process.getErrorStream()));
					DataOutputStream os = new DataOutputStream(process.getOutputStream());
					for (int i = 0; i < cmds.size(); i++) {
						String temp=cmds.get(i);
						if(temp!=null&&!"".equals(temp)&&!"null".equals(temp)){
							os.writeBytes(temp+" \n");
							Log.d("dddd", "命令:"+temp);
						}
					}
					os.flush();
					os.close();
					
					String line="";
					do{
						line=info.readLine();
						Log.d("dddd", "读取:"+line);
					}while(line!=null);
					
					Log.d("dddd", "waitFor");
					int i=process.waitFor();
					Log.d("dddd", "执行结果:"+i);
					Message msg=handler.obtainMessage();
					msg.what=0;
					handler.sendMessage(msg);
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}catch(SecurityException e){
					e.printStackTrace();
				}catch(NullPointerException e){
					e.printStackTrace();
				}catch(IndexOutOfBoundsException e){
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg=handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
				return;
			};
		}.start();
	}
	
	
	
	
	public static Map<String, PackageBean> getWhiteApps(Context ctx){
		Map<String, PackageBean> map=new HashMap<String, PackageBean>();
		List<PackageBean> list=DbFactory.getDb(ctx).findAll(PackageBean.class);
		for (int i = 0; i < list.size(); i++) {
			PackageBean bean=list.get(i);
			map.put(bean.getPackageName(), bean);
		}
		return map;
	}
}