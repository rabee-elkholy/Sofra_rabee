package com.androdu.sofra.data.models.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionsPage {
    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("data")
    @Expose
    private List<Region> regionList;
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

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }
}
