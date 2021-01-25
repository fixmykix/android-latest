package com.app.fixmykix.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceRequestsDataModel {

	@SerializedName("service_requests")
	private List<ServiceRequestsItem> serviceRequests;

	@SerializedName("pagination")
	private Pagination pagination;

	public List<ServiceRequestsItem> getServiceRequests(){
		return serviceRequests;
	}

	public Pagination getPagination(){
		return pagination;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"service_requests = '" + serviceRequests + '\'' + 
			",pagination = '" + pagination + '\'' + 
			"}";
		}
}