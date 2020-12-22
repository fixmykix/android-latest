package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class GetCartItemsResponse {

    @SerializedName("data")
    private CartOderDetailsModel data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public CartOderDetailsModel getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}