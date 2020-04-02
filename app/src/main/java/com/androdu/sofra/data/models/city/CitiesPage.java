package com.androdu.sofra.data.models.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesPage {
    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("data")
    @Expose
    private List<City> citiesList;
    @SerializedName("next_page_url")
    @Expose
    private String nextPageUrl;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<City> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }
}
