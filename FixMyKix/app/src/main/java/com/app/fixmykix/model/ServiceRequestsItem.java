package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceRequestsItem implements Parcelable {

    @SerializedName("image")
    private Image image;

    @SerializedName("artist_info")
    private ArtistInfo artistInfo;

    @SerializedName("description")
    private String description;

    @SerializedName("service_info")
    private List<ServiceInfoItem> serviceInfo;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("artist_id")
    private int artistId;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("artist_service_ids")
    private List<String> artistServiceIds;

    @SerializedName("user_info")
    private UserInfo userInfo;

    @SerializedName("price")
    private int price;

    @SerializedName("id")
    private int id;

    @SerializedName("status")
    private String status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @SerializedName("order_id")
    private int orderId;


    protected ServiceRequestsItem(Parcel in) {
        description = in.readString();
        createdAt = in.readString();
        artistId = in.readInt();
        updatedAt = in.readString();
        userId = in.readInt();
        serviceInfo = in.createTypedArrayList(CREATOR1);
        artistServiceIds = in.createStringArrayList();
        price = in.readInt();
        id = in.readInt();
        status = in.readString();
        orderId = in.readInt();
    }

    public static final Creator<ServiceRequestsItem> CREATOR = new Creator<ServiceRequestsItem>() {
        @Override
        public ServiceRequestsItem createFromParcel(Parcel in) {
            return new ServiceRequestsItem(in);
        }

        @Override
        public ServiceRequestsItem[] newArray(int size) {
            return new ServiceRequestsItem[size];
        }
    };

    public static final Creator<ServiceInfoItem> CREATOR1 = new Creator<ServiceInfoItem>() {
        @Override
        public ServiceInfoItem createFromParcel(Parcel in) {
            return new ServiceInfoItem(in);
        }

        @Override
        public ServiceInfoItem[] newArray(int size) {
            return new ServiceInfoItem[size];
        }
    };

    public Image getImage() {
        return image;
    }

    public ArtistInfo getArtistInfo() {
        return artistInfo;
    }

    public String getDescription() {
        return description;
    }

    public List<ServiceInfoItem> getServiceInfo() {
        return serviceInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getArtistServiceIds() {
        return artistServiceIds;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "ServiceRequestsItem{" +
                        "image = '" + image + '\'' +
                        ",artist_info = '" + artistInfo + '\'' +
                        ",description = '" + description + '\'' +
                        ",service_info = '" + serviceInfo + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",artist_id = '" + artistId + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",artist_service_ids = '" + artistServiceIds + '\'' +
                        ",user_info = '" + userInfo + '\'' +
                        ",price = '" + price + '\'' +
                        ",id = '" + id + '\'' +
                        ",status = '" + status + '\'' +
                        ",orderid = '" + orderId + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(createdAt);
        parcel.writeInt(artistId);
        parcel.writeString(updatedAt);
        parcel.writeInt(userId);
        parcel.writeTypedList(serviceInfo);
        parcel.writeStringList(artistServiceIds);
        parcel.writeInt(price);
        parcel.writeInt(id);
        parcel.writeString(status);
        parcel.writeInt(orderId);
    }
}