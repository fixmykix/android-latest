package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartOderDetailsModel {

    @SerializedName("order_number")
    private String orderNumber;

    @SerializedName("ups_status")
    private String upsStatus;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUpsStatus() {
        return upsStatus;
    }

    public void setUpsStatus(String upsStatus) {
        this.upsStatus = upsStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("id")
    private int id;


    @SerializedName("order_details")
    private List<OrderDetailsItem> orderDetailsItems;

    public List<OrderDetailsItem> getOrderDetailsItems() {
        return orderDetailsItems;
    }
}
