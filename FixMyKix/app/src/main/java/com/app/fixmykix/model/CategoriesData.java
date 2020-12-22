package com.app.fixmykix.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesData{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	public List<CategoriesItem> getCategories(){
		return categories;
	}
}