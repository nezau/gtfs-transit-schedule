package com.gtfs.model;

public class StopTimeModel {
    private String tripId;
    private String stopId;
    private String arrivalTime;
    private String departureTime;

    public StopTimeModel(String tripId, String stopId, String arrivalTime, String departureTime) {
        this.tripId = tripId;
        this.stopId = stopId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getTripId() {
        return tripId;
    }

    public String getStopId() {
        return stopId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }
}

