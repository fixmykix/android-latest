package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShopingCategory implements Parcelable {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("services")
    @Expose
    private ArrayList<Service> services = null;
    public final static Parcelable.Creator<ShopingCategory> CREATOR = new Parcelable.Creator<ShopingCategory>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShopingCategory createFromParcel(Parcel in) {
            return new ShopingCategory(in);
        }

        public ShopingCategory[] newArray(int size) {
            return (new ShopingCategory[size]);
        }

    };

    protected ShopingCategory(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.services, (Service.class.getClassLoader()));
    }

    public ShopingCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopingCategory withId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopingCategory withName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public ShopingCategory withServices(ArrayList<Service> services) {
        this.services = services;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(services);
    }

    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
