package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServiceRequest implements Parcelable {
    @SerializedName("id")
    private Long id;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("status")
    private String status;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private Image image;
    @SerializedName("price")
    private Long price;
    @SerializedName("artist_service_ids")
    private ArrayList<String> artistServiceIds = null;
    @SerializedName("service_info")
    private ArrayList<Service> Service = null;
    @SerializedName("artist_id")
    private Long artistId;
    @SerializedName("user_info")
    private UserData UserData;
    @SerializedName("artist_info")
    private Artist Artist;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    public final static Parcelable.Creator<ServiceRequest> CREATOR = new Parcelable.Creator<ServiceRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ServiceRequest createFromParcel(Parcel in) {
            return new ServiceRequest(in);
        }

        public ServiceRequest[] newArray(int size) {
            return (new ServiceRequest[size]);
        }

    };

    protected ServiceRequest(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.userId = ((Long) in.readValue((Long.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((Image) in.readValue((Image.class.getClassLoader())));
        this.price = ((Long) in.readValue((Long.class.getClassLoader())));
        in.readList(this.artistServiceIds, (java.lang.String.class.getClassLoader()));
        in.readList(this.Service, (Service.class.getClassLoader()));
        this.artistId = ((Long) in.readValue((Long.class.getClassLoader())));
        this.UserData = ((UserData) in.readValue((UserData.class.getClassLoader())));
        this.Artist = ((Artist) in.readValue((Artist.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ServiceRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceRequest withId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ServiceRequest withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceRequest withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ServiceRequest withImage(Image image) {
        this.image = image;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ServiceRequest withPrice(Long price) {
        this.price = price;
        return this;
    }

    public ArrayList<String> getArtistServiceIds() {
        return artistServiceIds;
    }

    public void setArtistServiceIds(ArrayList<String> artistServiceIds) {
        this.artistServiceIds = artistServiceIds;
    }

    public ServiceRequest withArtistServiceIds(ArrayList<String> artistServiceIds) {
        this.artistServiceIds = artistServiceIds;
        return this;
    }

    public ArrayList<Service> getService() {
        return Service;
    }

    public void setService(ArrayList<Service> Service) {
        this.Service = Service;
    }

    public ServiceRequest withService(ArrayList<Service> Service) {
        this.Service = Service;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public ServiceRequest withArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }

    public UserData getUserData() {
        return UserData;
    }

    public void setUserData(UserData UserData) {
        this.UserData = UserData;
    }

    public ServiceRequest withUserData(UserData UserData) {
        this.UserData = UserData;
        return this;
    }

    public Artist getArtist() {
        return Artist;
    }

    public void setArtist(Artist Artist) {
        this.Artist = Artist;
    }

    public ServiceRequest withArtist(Artist Artist) {
        this.Artist = Artist;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ServiceRequest withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ServiceRequest withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(status);
        dest.writeValue(description);
        dest.writeValue(image);
        dest.writeValue(price);
        dest.writeList(artistServiceIds);
        dest.writeList(Service);
        dest.writeValue(artistId);
        dest.writeValue(UserData);
        dest.writeValue(Artist);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return 0;
    }
}
