package com.example.nagya.bestinpest.Lobby.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LobbyCreatingPOST {

@SerializedName("maxPlayerNumber")
@Expose
private Integer maxPlayerNumber;
@SerializedName("name")
@Expose
private String name;
@SerializedName("password")
@Expose
private String password;
@SerializedName("player")
@Expose
private Leader player;

public LobbyCreatingPOST(){}

public LobbyCreatingPOST(Integer maxPlayerNumber,String name, String password, Leader player){
    this.maxPlayerNumber= maxPlayerNumber;
    this.name= name;
    this.password= password;
    this.player = player;
}

public Integer getMaxPlayerNumber() {
return maxPlayerNumber;
}

public void setMaxPlayerNumber(Integer maxPlayerNumber) {
this.maxPlayerNumber = maxPlayerNumber;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public Leader getPlayer() {
return player;
}

public void setPlayer(Leader player) {
this.player = player;
}

}