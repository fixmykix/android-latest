package com.app.fixmykix.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ArtistServiceData {

	@SerializedName("pagination")
	private Pagination pagination;

	@SerializedName("services")
	private List<ServicesItem> services;

	public Pagination getPagination(){
		return pagination;
	}

	public List<ServicesItem> getServices(){
		return services;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"pagination = '" + pagination + '\'' + 
			",services = '" + services + '\'' + 
			"}";
		}
}