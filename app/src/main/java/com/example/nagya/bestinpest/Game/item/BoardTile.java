package com.example.nagya.bestinpest.Game.item;

public class BoardTile {


    private String id;
    private String name;
    private int numberOfStops;
    private double lat;
    private double lon;

    public BoardTile(String id, String name, int numberOfStops, double lat, double lon){
        this.id= id;
        this.name = name;
        this.numberOfStops= numberOfStops;
        this.lat = lat;
        this.lon = lon;
    }

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

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


}
