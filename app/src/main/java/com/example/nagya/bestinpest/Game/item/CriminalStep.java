package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CriminalStep {

@SerializedName("arrivalJunctionId")
@Expose
private String arrivalJunctionId;
@SerializedName("departureJunctionId")
@Expose
private String departureJunctionId;
@SerializedName("id")
@Expose
private Integer id;
@SerializedName("round")
@Expose
private Integer round;
@SerializedName("routeId")
@Expose
private Integer routeId;
@SerializedName("type")
@Expose
private String type;
@SerializedName("visible")
@Expose
private Boolean visible;

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

public Integer getRound() {
return round;
}

public void setRound(Integer round) {
this.round = round;
}

public Integer getRouteId() {
return routeId;
}

public void setRouteId(Integer routeId) {
this.routeId = routeId;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Boolean getVisible() {
return visible;
}

public void setVisible(Boolean visible) {
this.visible = visible;
}

}