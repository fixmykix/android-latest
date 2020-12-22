package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class CategoriesResponse{

	@SerializedName("data")
	private CategoriesData categoriesData;

	@SerializedName("status")
	private boolean status;

	public CategoriesData getCategoriesData(){
		return categoriesData;
	}

	public boolean isStatus(){
		return status;
	}
}