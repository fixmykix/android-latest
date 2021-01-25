package com.app.fixmykix.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GetAddressListResponse {

    @SerializedName("data")
    private List<AddressDataItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<AddressDataItem> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "GetAddressListResponse{" +
                        "data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}