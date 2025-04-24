package com.gtfs.model;

public class StopModel {
    private String stopId;
    private String stopName;

    public StopModel(String stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
    }

    public String getStopId() {
        return stopId;
    }

    public String getStopName() {
        return stopName;
    }
}

