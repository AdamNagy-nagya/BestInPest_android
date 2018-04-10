package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plans {

@SerializedName("{playerId}")
@Expose
private PlayerId playerId;

public PlayerId getPlayerId() {
return playerId;
}

public void setPlayerId(PlayerId playerId) {
this.playerId = playerId;
}

}