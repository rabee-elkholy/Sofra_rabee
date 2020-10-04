package com.androdu.sofra.data.models.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantsPage {
    @SerializedName("last_page")
    @Expose
    private int lastPage;
    @SerializedName("data")
    @Expose
    private List<Restaurant> restaurantsList;

    public List<Restaurant> getRestaurantsList() {
        return restaurantsList;
    }


    public int getLastPage() {
        return lastPage;
    }

}
