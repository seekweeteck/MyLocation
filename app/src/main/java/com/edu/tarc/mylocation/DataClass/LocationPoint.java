package com.edu.tarc.mylocation.DataClass;

import android.location.Location;

/**
 * Created by Tarc on 3/14/2016.
 */
public class LocationPoint {
    private String id;

    public Location getPoint() {
        return point;
    }

    public void setPoint(Location point) {
        this.point = point;
    }

    Location point;
    private double latitude;
    private double longitude;
    private String name;

    public LocationPoint() {
        this.point = new Location("");
    }

    public LocationPoint(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.point = new Location(name);
        this.point.setLatitude(latitude);
        this.point.setLongitude(longitude);
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
