package com.androdu.sofra.data.models.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantReviewsPage {
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
    private List<RestaurantReview> restaurantReviewList;
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

    public List<RestaurantReview> getRestaurantReviewList() {
        return restaurantReviewList;
    }

    public void setRestaurantReviewList(List<RestaurantReview> restaurantReviewList) {
        this.restaurantReviewList = restaurantReviewList;
    }
}
