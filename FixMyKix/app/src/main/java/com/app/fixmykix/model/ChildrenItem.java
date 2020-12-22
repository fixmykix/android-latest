package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ChildrenItem implements Parcelable {

    @SerializedName("parent_name")
    private String parentName;

    @SerializedName("children")
    private List<ChildrenItem> children;

    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("services")
    private List<ServiceResponse> services;

    @SerializedName("type")
    private String type;


    protected ChildrenItem(Parcel in) {
        parentName = in.readString();
        children = in.createTypedArrayList(ChildrenItem.CREATOR);
        parentId = in.readInt();
        name = in.readString();
        id = in.readInt();
        type = in.readString();
        services = in.createTypedArrayList(ChildrenItem.CREATOR1);

    }

    public static final Creator<ChildrenItem> CREATOR = new Creator<ChildrenItem>() {
        @Override
        public ChildrenItem createFromParcel(Parcel in) {
            return new ChildrenItem(in);
        }

        @Override
        public ChildrenItem[] newArray(int size) {
            return new ChildrenItem[size];
        }
    };

    public static final Creator<ServiceResponse> CREATOR1 = new Creator<ServiceResponse>() {
        @Override
        public ServiceResponse createFromParcel(Parcel in) {
            return new ServiceResponse(in);
        }

        @Override
        public ServiceResponse[] newArray(int size) {
            return new ServiceResponse[size];
        }
    };

    public String getParentName() {
        return parentName;
    }

    public List<ChildrenItem> getChildren() {
        return children;
    }

    public int getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<ServiceResponse> getServices() {
        return services;
    }

    public String getType() {
        return type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(parentName);
        parcel.writeTypedList(children);
        parcel.writeInt(parentId);
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeTypedList(services);
    }
}