package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ServiceImage implements Parcelable {
    @SerializedName("url")
    private String url;
    public final static Parcelable.Creator<ServiceImage> CREATOR = new Parcelable.Creator<ServiceImage>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ServiceImage createFromParcel(Parcel in) {
            return new ServiceImage(in);
        }

        public ServiceImage[] newArray(int size) {
            return (new ServiceImage[size]);
        }

    };

    protected ServiceImage(Parcel in) {
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ServiceImage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ServiceImage withUrl(String url) {
        this.url = url;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }
}
