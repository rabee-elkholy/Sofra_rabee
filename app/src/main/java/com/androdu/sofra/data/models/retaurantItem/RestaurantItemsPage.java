package com.androdu.sofra.data.models.retaurantItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantItemsPage {
    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("last_page")
    @Expose
    private int lastPage;
    @SerializedName("data")
    @Expose
    private List<RestaurantItem> restaurantItems;
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

    public List<RestaurantItem> getRestaurantItems() {
        return restaurantItems;
    }

    public void setRestaurantItems(List<RestaurantItem> restaurantItems) {
        this.restaurantItems = restaurantItems;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
