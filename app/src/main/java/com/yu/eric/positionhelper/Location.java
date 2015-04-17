package com.yu.eric.positionhelper;

/**
 * Created by lliyu on 4/17/2015.
 */
public class Location {

    private double latitude;
    private double longitude;
    private String time;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Location(LocationSensor locationSensor){
        latitude = locationSensor.getLatitude();
        longitude = locationSensor.getLongitude();
        time = locationSensor.getTime();
    }
    public Location(){}

}
