package com.example.nagya.bestinpest.Game.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameObject {

@SerializedName("criminalId")
@Expose
private Integer criminalId;
@SerializedName("criminalSteps")
@Expose
private List<CriminalStep> criminalSteps = null;
@SerializedName("detectiveSteps")
@Expose
private List<DetectiveStep> detectiveSteps = null;
@SerializedName("id")
@Expose
private Integer id;
@SerializedName("players")
@Expose
private List<Player> players = null;
@SerializedName("round")
@Expose
private Integer round;
@SerializedName("turn")
@Expose
private String turn;

public Integer getCriminalId() {
return criminalId;
}

public void setCriminalId(Integer criminalId) {
this.criminalId = criminalId;
}

public List<CriminalStep> getCriminalSteps() {
return criminalSteps;
}

public void setCriminalSteps(List<CriminalStep> criminalSteps) {
this.criminalSteps = criminalSteps;
}

public List<DetectiveStep> getDetectiveSteps() {
return detectiveSteps;
}

public void setDetectiveSteps(List<DetectiveStep> detectiveSteps) {
this.detectiveSteps = detectiveSteps;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public List<Player> getPlayers() {
return players;
}

public void setPlayers(List<Player> players) {
this.players = players;
}

public Integer getRound() {
return round;
}

public void setRound(Integer round) {
this.round = round;
}

public String getTurn() {
return turn;
}

public void setTurn(String turn) {
this.turn = turn;
}

}