package com.example.android.quakereport;

public class Earthquake {
        private double magnitude;
        private String location;
        private long timeInMiliSeconds;
        String url;


    public long getTimeInMiliSeconds() {
        return timeInMiliSeconds;
    }

    public Earthquake(double magnitude, String location, long timeInMiliSeconds ,String url){
            this.timeInMiliSeconds = timeInMiliSeconds;
            this.location = location;
            this.magnitude = magnitude;
            this.url =url;
        }

    public String getUrl() {
        return url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

}
