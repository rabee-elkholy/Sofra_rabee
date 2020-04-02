package com.androdu.sofra.data.models.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesGeneralResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private CitiesPage citiesPage;


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

    public CitiesPage getCitiesPage() {
        return citiesPage;
    }

    public void setCitiesPage(CitiesPage citiesPage) {
        this.citiesPage = citiesPage;
    }
}
