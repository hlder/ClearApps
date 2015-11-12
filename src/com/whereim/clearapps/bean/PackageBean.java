package com.whereim.clearapps.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * °×Ãûµ¥±í
 * @author User
 */
@Table(name="whiteApp")
public class PackageBean { 
	@Id(column="id")
	private int id;
	private String packageName;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}