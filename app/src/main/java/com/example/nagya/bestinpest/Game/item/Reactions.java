package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reactions {

@SerializedName("{playerId}")
@Expose
private String playerId;

public String getPlayerId() {
return playerId;
}

public void setPlayerId(String playerId) {
this.playerId = playerId;
}

}