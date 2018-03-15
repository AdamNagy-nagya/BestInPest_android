package com.example.nagya.bestinpest.Lobby.item;

/**
 * Created by nagya on 04/03/2018.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LobbyRestItem {

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
    @SerializedName("criminalId")
    @Expose
    private Integer criminal;
    @SerializedName("players")
    @Expose
    private List<Player> players = null;
    @SerializedName("passwordSet")
    @Expose
    private Boolean passwordSet;

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

    public Integer getCriminal() {
        return criminal;
    }

    public void setCriminal(Integer criminal) {
        this.criminal = criminal;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Boolean getPasswordSet() {
        return passwordSet;
    }

    public void setPasswordSet(Boolean passwordSet) {
        this.passwordSet = passwordSet;
    }

}