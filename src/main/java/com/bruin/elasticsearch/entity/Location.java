package com.bruin.elasticsearch.entity;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/16
 */
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private double lat;
    private double lon;

    public Location(){}

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
