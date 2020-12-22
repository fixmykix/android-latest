package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class ServicesItem{

	@SerializedName("image")
	private Image image;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("is_popular")
	private boolean isPopular;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	public Image getImage(){
		return image;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public boolean isIsPopular(){
		return isPopular;
	}

	public String getName(){
		return name;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}
}