package com.example.nagya.bestinpest.network.RouteNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

@SerializedName("time")
@Expose
private Integer time;
@SerializedName("predictedTime")
@Expose
private Object predictedTime;
@SerializedName("relationName")
@Expose
private String relationName;
@SerializedName("relationId")
@Expose
private String relationId;
@SerializedName("headsign")
@Expose
private String headsign;
@SerializedName("tripId")
@Expose
private String tripId;

public Integer getTime() {
return time;
}

public void setTime(Integer time) {
this.time = time;
}

public Object getPredictedTime() {
return predictedTime;
}

public void setPredictedTime(Object predictedTime) {
this.predictedTime = predictedTime;
}

public String getRelationName() {
return relationName;
}

public void setRelationName(String relationName) {
this.relationName = relationName;
}

public String getRelationId() {
return relationId;
}

public void setRelationId(String relationId) {
this.relationId = relationId;
}

public String getHeadsign() {
return headsign;
}

public void setHeadsign(String headsign) {
this.headsign = headsign;
}

public String getTripId() {
return tripId;
}

public void setTripId(String tripId) {
this.tripId = tripId;
}

}