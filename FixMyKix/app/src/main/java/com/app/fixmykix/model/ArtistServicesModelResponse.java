package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class ArtistServicesModelResponse{

	@SerializedName("data")
	private ArtistServiceData artistServiceData;

	@SerializedName("status")
	private boolean status;

	public ArtistServiceData getArtistServiceData(){
		return artistServiceData;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ArtistServicesModelResponse{" + 
			"data = '" + artistServiceData + '\'' +
			",status = '" + status + '\'' + 
			"}";
		}
}