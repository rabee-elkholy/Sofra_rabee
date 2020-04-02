
package com.androdu.sofra.data.models.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientLoginGeneralResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ClientLoginData data;

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


    public ClientLoginData getData() {
        return data;
    }

    public void setData(ClientLoginData data) {
        this.data = data;
    }
}
