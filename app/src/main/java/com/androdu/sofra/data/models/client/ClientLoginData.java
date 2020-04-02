
package com.androdu.sofra.data.models.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientLoginData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private Client client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
