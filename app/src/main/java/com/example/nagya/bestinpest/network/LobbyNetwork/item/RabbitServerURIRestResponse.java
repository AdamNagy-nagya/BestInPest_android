package com.example.nagya.bestinpest.network.LobbyNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nagya on 25/03/2018.
 */

public class RabbitServerURIRestResponse {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
