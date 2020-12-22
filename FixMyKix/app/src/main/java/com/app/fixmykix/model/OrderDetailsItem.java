package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsItem{

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("product_image")
	private ProductImage productImage;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("description")
	private Object description;

	@SerializedName("id")
	private int id;

	@SerializedName("artist_service_id")
	private int artistServiceId;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("artist_service")
	private ArtistService artistService;

	public int getQuantity(){
		return quantity;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public ProductImage getProductImage(){
		return productImage;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public int getArtistServiceId(){
		return artistServiceId;
	}

	public int getOrderId(){
		return orderId;
	}

	public ArtistService getArtistService(){
		return artistService;
	}
}