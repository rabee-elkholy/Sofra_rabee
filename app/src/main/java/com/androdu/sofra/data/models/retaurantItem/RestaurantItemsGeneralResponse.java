package com.androdu.sofra.data.models.retaurantItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantItemsGeneralResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestaurantItemsPage restaurantItemsPage;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public RestaurantItemsPage getRestaurantItemsPage() {
        return restaurantItemsPage;
    }

    public void setRestaurantItemsPage(RestaurantItemsPage restaurantItemsPage) {
        this.restaurantItemsPage = restaurantItemsPage;
    }
}
