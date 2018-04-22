package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recommendation {

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
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("receiverPlayerId")
    @Expose
    private Integer receiverPlayerId;
    @SerializedName("senderPlayerId")
    @Expose
    private Integer senderPlayerId;

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

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }

    public Integer getReceiverPlayerId() {
    return receiverPlayerId;
    }

    public void setReceiverPlayerId(Integer receiverPlayerId) {
    this.receiverPlayerId = receiverPlayerId;
    }

    public Integer getSenderPlayerId() {
    return senderPlayerId;
    }

    public void setSenderPlayerId(Integer senderPlayerId) {
    this.senderPlayerId = senderPlayerId;
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