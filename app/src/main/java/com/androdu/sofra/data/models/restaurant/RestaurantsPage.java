package com.androdu.sofra.data.models.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantsPage {
    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("last_page")
    @Expose
    private int lastPage;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("data")
    @Expose
    private List<Restaurant> restaurantsList;
    @SerializedName("next_page_url")
    @Expose
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

     public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<Restaurant> getRestaurantsList() {
        return restaurantsList;
    }

    public void setRestaurantsList(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
