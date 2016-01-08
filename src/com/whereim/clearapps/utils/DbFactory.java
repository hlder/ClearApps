package com.whereim.clearapps.utils;

import com.hld.library.frame.DBmanager;
import com.hld.library.frame.db.DbUtils;

import android.content.Context;


public class DbFactory {
	public static DbUtils getDb(Context ctx){
		return DBmanager.create(ctx, "clearapps");
	}
}
