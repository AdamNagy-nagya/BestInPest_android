package com.example.nagya.bestinpest.network.GameNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StepRecommendation {

    @SerializedName("arrivalJunctionId")
    @Expose
    private String arrivalJunctionId;
    @SerializedName("departureJunctionId")
    @Expose
    private String departureJunctionId;

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

}