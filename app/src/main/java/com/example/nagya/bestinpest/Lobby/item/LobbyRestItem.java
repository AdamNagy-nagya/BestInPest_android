package com.example.nagya.bestinpest.Lobby.item;

/**
 * Created by nagya on 04/03/2018.
 */

import android.util.Log;

import java.util.List;

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
    @SerializedName("player")
    @Expose
    private Player player;
    @SerializedName("maxPlayerNumber")
    @Expose
    private Integer maxPlayerNumber;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("criminal")
    @Expose
    private Player criminal;
    @SerializedName("players")
    @Expose
    private List<Player> players = null;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public Player getCriminal() {
        return criminal;
    }

    public void setCriminal(Player criminal) {
        this.criminal = criminal;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
