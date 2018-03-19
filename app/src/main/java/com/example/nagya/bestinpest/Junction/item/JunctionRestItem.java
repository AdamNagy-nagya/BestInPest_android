package com.example.nagya.bestinpest.Junction.item;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JunctionRestItem {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("stops")
@Expose
private List<Stop> stops = null;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public List<Stop> getStops() {
return stops;
}

public void setStops(List<Stop> stops) {
this.stops = stops;
}

    @Override
    public String toString() {
        return name.toString();
    }
}