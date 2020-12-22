package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesItem implements Parcelable {

	@SerializedName("parent_name")
	private Object parentName;

	@SerializedName("children")
	private List<ChildrenItem> children;

	@SerializedName("parent_id")
	private Object parentId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("services")
	private List<Object> services;

	@SerializedName("type")
	private String type;

	protected CategoriesItem(Parcel in) {
		name = in.readString();
		id = in.readInt();
		type = in.readString();
	}

	public static final Creator<CategoriesItem> CREATOR = new Creator<CategoriesItem>() {
		@Override
		public CategoriesItem createFromParcel(Parcel in) {
			return new CategoriesItem(in);
		}

		@Override
		public CategoriesItem[] newArray(int size) {
			return new CategoriesItem[size];
		}
	};

	public Object getParentName(){
		return parentName;
	}

	public List<ChildrenItem> getChildren(){
		return children;
	}

	public Object getParentId(){
		return parentId;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public List<Object> getServices(){
		return services;
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
		parcel.writeString(name);
		parcel.writeInt(id);
		parcel.writeString(type);
	}
}