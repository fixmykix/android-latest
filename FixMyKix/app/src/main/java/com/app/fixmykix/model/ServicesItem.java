package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ServicesItem implements Parcelable {

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

	protected ServicesItem(Parcel in) {
		updatedAt = in.readString();
		isPopular = in.readByte() != 0;
		name = in.readString();
		createdAt = in.readString();
		id = in.readInt();
		type = in.readString();
	}

	public static final Creator<ServicesItem> CREATOR = new Creator<ServicesItem>() {
		@Override
		public ServicesItem createFromParcel(Parcel in) {
			return new ServicesItem(in);
		}

		@Override
		public ServicesItem[] newArray(int size) {
			return new ServicesItem[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(updatedAt);
		parcel.writeByte((byte) (isPopular ? 1 : 0));
		parcel.writeString(name);
		parcel.writeString(createdAt);
		parcel.writeInt(id);
		parcel.writeString(type);
	}
}