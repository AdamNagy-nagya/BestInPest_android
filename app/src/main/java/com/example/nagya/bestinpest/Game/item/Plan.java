package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("departureJunctionId")
@Expose
private String departureJunctionId;
@SerializedName("arrivalJunctionId")
@Expose
private String arrivalJunctionId;
@SerializedName("routeId")
@Expose
private Integer routeId;
@SerializedName("playerId")
@Expose
private Integer playerId;
@SerializedName("reactions")
@Expose
private Reactions reactions;

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

public Reactions getReactions() {
return reactions;
}

public void setReactions(Reactions reactions) {
this.reactions = reactions;
}

}