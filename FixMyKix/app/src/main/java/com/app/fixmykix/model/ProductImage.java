package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class ProductImage{

	@SerializedName("url")
	private Object url;

	public Object getUrl(){
		return url;
	}
}