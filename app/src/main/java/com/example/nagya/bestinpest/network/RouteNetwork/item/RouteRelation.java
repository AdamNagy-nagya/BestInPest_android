package com.example.nagya.bestinpest.network.RouteNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteRelation {

@SerializedName("relationId")
@Expose
private String relationId;
@SerializedName("relationName")
@Expose
private String relationName;
@SerializedName("headsign")
@Expose
private String headsign;
@SerializedName("textColor")
@Expose
private String textColor;
@SerializedName("backgroundColor")
@Expose
private String backgroundColor;

public String getRelationId() {
return relationId;
}

public void setRelationId(String relationId) {
this.relationId = relationId;
}

public String getRelationName() {
return relationName;
}

public void setRelationName(String relationName) {
this.relationName = relationName;
}

public String getHeadsign() {
return headsign;
}

public void setHeadsign(String headsign) {
this.headsign = headsign;
}

public String getTextColor() {
return textColor;
}

public void setTextColor(String textColor) {
this.textColor = textColor;
}

public String getBackgroundColor() {
return backgroundColor;
}

public void setBackgroundColor(String backgroundColor) {
this.backgroundColor = backgroundColor;
}

}