package com.example.nagya.bestinpest.Lobby.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

@SerializedName("name")
@Expose
private String name;

public Player(){}
public Player(String name){
    this.name= name;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}