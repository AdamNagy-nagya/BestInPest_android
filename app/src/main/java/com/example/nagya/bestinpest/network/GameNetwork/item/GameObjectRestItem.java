package com.example.nagya.bestinpest.network.GameNetwork.item;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameObjectRestItem {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("criminalId")
@Expose
private Integer criminalId;
@SerializedName("turn")
@Expose
private String turn;
@SerializedName("round")
@Expose
private Integer round;
@SerializedName("players")
@Expose
private List<Player> players = null;
@SerializedName("criminalSteps")
@Expose
private List<Object> criminalSteps = null;
@SerializedName("detectiveSteps")
@Expose
private List<Object> detectiveSteps = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getCriminalId() {
return criminalId;
}

public void setCriminalId(Integer criminalId) {
this.criminalId = criminalId;
}

public String getTurn() {
return turn;
}

public void setTurn(String turn) {
this.turn = turn;
}

public Integer getRound() {
return round;
}

public void setRound(Integer round) {
this.round = round;
}

public List<Player> getPlayers() {
return players;
}

public void setPlayers(List<Player> players) {
this.players = players;
}

public List<Object> getCriminalSteps() {
return criminalSteps;
}

public void setCriminalSteps(List<Object> criminalSteps) {
this.criminalSteps = criminalSteps;
}

public List<Object> getDetectiveSteps() {
return detectiveSteps;
}

public void setDetectiveSteps(List<Object> detectiveSteps) {
this.detectiveSteps = detectiveSteps;
}

}