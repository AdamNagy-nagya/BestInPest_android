package com.example.nagya.bestinpest.Lobby.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leader {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("junction")
@Expose
private Object junction;
@SerializedName("name")
@Expose
private String name;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Object getJunction() {
return junction;
}

public void setJunction(Object junction) {
this.junction = junction;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}