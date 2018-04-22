package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Plan {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("arrivalJunctionId")
    @Expose
    private String arrivalJunctionId;

    @SerializedName("arrivalJunctionName")
    @Expose
    private String arrivalJunctionName;

    @SerializedName("departureJunctionId")
    @Expose
    private String departureJunctionId;
    @SerializedName("departureJunctionName")
    @Expose
    private String departureJunctionName;

    @SerializedName("routeId")
    @Expose
    private Integer routeId;
    @SerializedName("playerId")
    @Expose
    private Integer playerId;
    @SerializedName("reactions")
    @Expose
    private HashMap<Integer, String> reactions;

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }

    public String getDepartureJunctionId() {
    return departureJunctionId;
    }

    public void setDepartureJunctionId(String departureJunctionId) {
    this.departureJunctionId = departureJunctionId;
    }

    public String getArrivalJunctionId() {
    return arrivalJunctionId;
    }

    public void setArrivalJunctionId(String arrivalJunctionId) {
    this.arrivalJunctionId = arrivalJunctionId;
    }

    public Integer getRouteId() {
    return routeId;
    }

    public void setRouteId(Integer routeId) {
    this.routeId = routeId;
    }

    public Integer getPlayerId() {
    return playerId;
    }

    public void setPlayerId(Integer playerId) {
    this.playerId = playerId;
    }

    public HashMap<Integer, String> getReactions() {
    return reactions;
    }

    public void setReactions(HashMap reactions) {
    this.reactions = reactions;
    }

    public String getArrivalJunctionName() {
        return arrivalJunctionName;
    }

    public void setArrivalJunctionName(String arrivalJunctionName) {
        this.arrivalJunctionName = arrivalJunctionName;
    }

    public String getDepartureJunctionName() {
        return departureJunctionName;
    }

    public void setDepartureJunctionName(String departureJunctionName) {
        this.departureJunctionName = departureJunctionName;
    }
}