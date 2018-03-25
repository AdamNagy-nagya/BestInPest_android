package com.example.nagya.bestinpest.network.RabbitMq.item;

import com.example.nagya.bestinpest.Lobby.item.LobbyRestItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nagya on 25/03/2018.
 */

public class LobbiesRabbitMqItem {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("object")
    @Expose
    private List<LobbyRestItem> object = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LobbyRestItem> getObject() {
        return object;
    }

    public void setObject(List<LobbyRestItem> object) {
        this.object = object;
    }


}
