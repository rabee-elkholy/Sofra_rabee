package com.androdu.sofra.data.models.restaurantCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantCategoriesGeneralResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<RestaurantCategory> restaurantCategories;


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


    public List<RestaurantCategory> getRestaurantCategories() {
        return restaurantCategories;
    }

    public void setRestaurantCategories(List<RestaurantCategory> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }
}
