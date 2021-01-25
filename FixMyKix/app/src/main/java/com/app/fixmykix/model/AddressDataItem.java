package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class AddressDataItem {

	@SerializedName("zip")
	private String zip;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("addressable_id")
	private int addressableId;

	@SerializedName("addressable_type")
	private String addressableType;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	public String getZip(){
		return zip;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCity(){
		return city;
	}

	public String getStreet(){
		return street;
	}

	public int getAddressableId(){
		return addressableId;
	}

	public String getAddressableType(){
		return addressableType;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getState(){
		return state;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"zip = '" + zip + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",city = '" + city + '\'' + 
			",street = '" + street + '\'' + 
			",addressable_id = '" + addressableId + '\'' + 
			",addressable_type = '" + addressableType + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			"}";
		}
}