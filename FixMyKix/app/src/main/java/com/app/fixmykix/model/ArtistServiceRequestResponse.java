package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class ArtistServiceRequestResponse{

	@SerializedName("data")
	private ServiceRequestsDataModel serviceRequestsDataModel;

	@SerializedName("status")
	private boolean status;

	public ServiceRequestsDataModel getServiceRequestsDataModel(){
		return serviceRequestsDataModel;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ArtistServiceRequestResponse{" + 
			"data = '" + serviceRequestsDataModel + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}