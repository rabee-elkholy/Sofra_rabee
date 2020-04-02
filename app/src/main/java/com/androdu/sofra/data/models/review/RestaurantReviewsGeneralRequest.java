
package com.androdu.sofra.data.models.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantReviewsGeneralRequest {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestaurantReviewsPage data;

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


    public RestaurantReviewsPage getData() {
        return data;
    }

    public void setData(RestaurantReviewsPage data) {
        this.data = data;
    }
}
