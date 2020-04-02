package com.androdu.sofra.data.models.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionsGeneralResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RegionsPage regionsPage;


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

    public RegionsPage getRegionsPage() {
        return regionsPage;
    }

    public void setRegionsPage(RegionsPage regionsPage) {
        this.regionsPage = regionsPage;
    }
}
