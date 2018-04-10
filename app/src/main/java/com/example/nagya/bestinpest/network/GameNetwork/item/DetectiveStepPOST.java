package com.example.nagya.bestinpest.network.GameNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetectiveStepPOST {

    @SerializedName("arrivalJunctionId")
    @Expose
    private String arrivalJunctionId;

    @SerializedName("departureJunctionId")
    @Expose
    private String departureJunctionId;

    @SerializedName("playerId")
    @Expose
    private Integer playerId;

    @SerializedName("routeId")
    @Expose
    private Integer routeId;

    public String getArrivalJunctionId() {
    return arrivalJunctionId;
    }

    public void setArrivalJunctionId(String arrivalJunctionId) {
        this.arrivalJunctionId = arrivalJunctionId;
    }

    public String getDepartureJunctionId() {
    return departureJunctionId;
    }

    public void setDepartureJunctionId(String departureJunctionId) {
        this.departureJunctionId = departureJunctionId;
    }

    public Integer getPlayerId() {
    return playerId;
    }

    public void setPlayerId(Integer playerId) {
    this.playerId = playerId;
    }


    public Integer getRouteId() {
    return routeId;
    }

    public void setRouteId(Integer routeId) {
    this.routeId = routeId;
    }

}