package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectiveStep {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("plans")
@Expose
private HashMap<Integer,Plan> plans= null;
@SerializedName("recommendations")
@Expose
private List<Recommendation> recommendations = null;
@SerializedName("round")
@Expose
private Integer round;


public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public HashMap<Integer, Plan> getPlans() {
return plans;
}

public void setPlans(HashMap plans) {
this.plans = plans;
}

public List<Recommendation> getRecommendations() {
return recommendations;
}

public void setRecommendations(List<Recommendation> recommendations) {
this.recommendations = recommendations;
}

public Integer getRound() {
return round;
}

public void setRound(Integer round) {
this.round = round;
}

}