package com.app.fixmykix.model;

public class AddToCartDataItem {
    private String totalPrice;
    private long orderNumber;
    private String addressId;
    private String createdAt;
    private String description;
    private String turnaroundTime;
    private String orderDate;
    private String updatedAt;
    private int userId;
    private int id;
    private String approximateTotalPrice;
    private String paymentDate;
    private String status;
    private String upsStatus;

    public String getTotalPrice() {
        return totalPrice;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public String getTurnaroundTime() {
        return turnaroundTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getApproximateTotalPrice() {
        return approximateTotalPrice;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public String getUpsStatus() {
        return upsStatus;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "total_price = '" + totalPrice + '\'' +
                        ",order_number = '" + orderNumber + '\'' +
                        ",address_id = '" + addressId + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",description = '" + description + '\'' +
                        ",turnaround_time = '" + turnaroundTime + '\'' +
                        ",order_date = '" + orderDate + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",id = '" + id + '\'' +
                        ",approximate_total_price = '" + approximateTotalPrice + '\'' +
                        ",payment_date = '" + paymentDate + '\'' +
                        ",status = '" + status + '\'' +
                        ",ups_status = '" + upsStatus + '\'' +
                        "}";
    }
}
