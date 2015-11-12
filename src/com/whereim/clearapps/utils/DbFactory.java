package com.whereim.clearapps.utils;

import android.content.Context;

import com.fljr.frame.DBmanager;
import com.fljr.frame.db.DbUtils;

public class DbFactory {
	public static DbUtils getDb(Context ctx){
		return DBmanager.create(ctx, "clearapps");
	}
}
