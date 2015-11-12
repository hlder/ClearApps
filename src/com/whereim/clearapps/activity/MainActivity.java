package com.whereim.clearapps.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whereim.clearapp.R;
import com.whereim.clearapps.bean.PackageBean;
import com.whereim.clearapps.utils.CmdUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	private List<String> cmds;
	private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog=new ProgressDialog(this);
        dialog.setMessage("正在强杀,请稍等...");
        dialog.setCancelable(false);
    }
    public void onClick(View view) {
    	switch (view.getId()) {
		case R.id.btn_clear_apps:
			clearApps();
			break;
		case R.id.btn_set:
			Intent intent=new Intent(this,SettingActivity.class);
			startActivity(intent);
			break;
		}
	}
    
    private void clearApps() {
    	List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
    	Map<String, PackageBean> map=CmdUtils.getWhiteApps(this);
    	cmds=new ArrayList<String>();
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
        dialog.show();
	}
    
    private Handler handler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		super.handleMessage(msg);
    		switch (msg.what) {
			case 0://成功
				Toast.makeText(MainActivity.this, "清理成功", Toast.LENGTH_SHORT).show();
				break;
			case 1://失败
				Toast.makeText(MainActivity.this, "清理失败", Toast.LENGTH_SHORT).show();
				break;
			}
    		dialog.dismiss();
    	}
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}