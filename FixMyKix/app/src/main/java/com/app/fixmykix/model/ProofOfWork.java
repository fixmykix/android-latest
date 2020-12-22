package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProofOfWork implements Parcelable{
    @SerializedName("url")
    private Object url;
    public final static Parcelable.Creator<ProofOfWork> CREATOR = new Parcelable.Creator<ProofOfWork>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProofOfWork createFromParcel(Parcel in) {
            return new ProofOfWork(in);
        }

        public ProofOfWork[] newArray(int size) {
            return (new ProofOfWork[size]);
        }

    }
            ;

    protected ProofOfWork(Parcel in) {
        this.url = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public ProofOfWork() {
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public ProofOfWork withUrl(Object url) {
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
