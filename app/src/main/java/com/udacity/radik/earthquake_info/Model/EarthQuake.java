package com.udacity.radik.earthquake_info.Model;

import java.net.URL;

/**
 * Created by Radik on 31.03.2018.
 */

public class EarthQuake {
    private Double magnitude;
    private String location;
    private String distance;
    private String date;
    private String time;
    private URL url;

    public EarthQuake() {

    }

    public EarthQuake(Double magnitude, String location, String distance, String date, String time, URL url) {
        this.magnitude = magnitude;
        this.location = location;
        this.distance = distance;
        this.date = date;
        this.url = url;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
