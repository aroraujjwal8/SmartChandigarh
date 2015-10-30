package com.smartchd.smartchandigarh.data;


public class TrafficData {
    private double start_latitude, start_longitude, end_latitude, end_longitude;
    private String congestionType,startLocation,endLocation;
    private int congestion_level;

    public TrafficData(double start_latitude, double start_longitude, double end_latitude,
                                double end_longitude, String congestionType, int congestion_level){
        this.start_latitude = start_latitude;
        this.start_longitude = start_longitude;

        this.end_latitude = end_latitude;
        this.end_longitude = end_longitude;

        this.congestionType = congestionType;

        this.congestion_level = congestion_level;
    }


    public double getStart_latitude() {
        return start_latitude;
    }


    public double getStart_longitude() {
        return start_longitude;
    }

    public double getEnd_latitude() {
        return end_latitude;
    }

    public String getCongestionType() {
        return congestionType;
    }

    public int getCongestion_level() {
        return congestion_level;
    }

    public double getEnd_longitude() {
        return end_longitude;
    }


    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
}
