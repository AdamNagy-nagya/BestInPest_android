package com.example.nagya.bestinpest.Lobby.item;

/**
 * Created by nagya on 04/03/2018.
 */

import android.util.Log;

import java.util.List;

import com.example.nagya.bestinpest.Lobby.item.Leader;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LobbyRestItem {

    public LobbyRestItem(){
        Log.e("RESTITEM", "CREATE");
    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("leader")
    @Expose
    private Leader leader;
    @SerializedName("maxPlayerNumber")
    @Expose
    private Integer maxPlayerNumber;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("criminal")
    @Expose
    private Object criminal;
    @SerializedName("players")
    @Expose
    private List<Object> players = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public Integer getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public void setMaxPlayerNumber(Integer maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getCriminal() {
        return criminal;
    }

    public void setCriminal(Object criminal) {
        this.criminal = criminal;
    }

    public List<Object> getPlayers() {
        return players;
    }

    public void setPlayers(List<Object> players) {
        this.players = players;
    }
}
