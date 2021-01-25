package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class IpAddress{

	@SerializedName("mask_addr")
	private long maskAddr;

	@SerializedName("family")
	private int family;

	@SerializedName("addr")
	private long addr;

	public long getMaskAddr(){
		return maskAddr;
	}

	public int getFamily(){
		return family;
	}

	public long getAddr(){
		return addr;
	}

	@Override
 	public String toString(){
		return 
			"IpAddress{" + 
			"mask_addr = '" + maskAddr + '\'' + 
			",family = '" + family + '\'' + 
			",addr = '" + addr + '\'' + 
			"}";
		}
}