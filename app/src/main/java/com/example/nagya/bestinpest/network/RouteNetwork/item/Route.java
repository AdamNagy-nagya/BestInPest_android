package com.example.nagya.bestinpest.network.RouteNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("departure")
@Expose
private Stop departure;
@SerializedName("arrival")
@Expose
private Stop arrival;
@SerializedName("type")
@Expose
private String type;
@SerializedName("relations")
@Expose
private List<RouteRelation> relations = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Stop getDeparture() {
return departure;
}

public void setDeparture(Stop departure) {
this.departure = departure;
}

public Stop getArrival() {
return arrival;
}

public void setArrival(Stop arrival) {
this.arrival = arrival;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public List<RouteRelation> getRelations() {
return relations;
}

public void setRelations(List<RouteRelation> relations) {
this.relations = relations;
}

}