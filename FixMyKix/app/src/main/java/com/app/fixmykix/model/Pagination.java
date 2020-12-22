package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class Pagination{

	@SerializedName("total_objects")
	private int totalObjects;

	@SerializedName("limit")
	private int limit;

	@SerializedName("page")
	private int page;

	public int getTotalObjects(){
		return totalObjects;
	}

	public int getLimit(){
		return limit;
	}

	public int getPage(){
		return page;
	}

	@Override
 	public String toString(){
		return 
			"Pagination{" + 
			"total_objects = '" + totalObjects + '\'' + 
			",limit = '" + limit + '\'' + 
			",page = '" + page + '\'' + 
			"}";
		}
}