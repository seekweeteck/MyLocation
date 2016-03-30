package com.edu.tarc.mylocation.DataClass;

/**
 * Created by Tarc on 3/14/2016.
 */
public class LocationPoint {
    private int id;
    private double latitude;
    private double longitude;
    private String name;

    public LocationPoint() {
    }

    public LocationPoint(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
