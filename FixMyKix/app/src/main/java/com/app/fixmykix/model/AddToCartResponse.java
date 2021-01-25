package com.app.fixmykix.model;

public class AddToCartResponse {
    private AddToCartDataItem data;
    private String message;
    private boolean status;

    public AddToCartDataItem getAddToCartDataItem() {
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
                "AddToCartResponse{" +
                        "data = '" + data + '\'' +
                        ",message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
