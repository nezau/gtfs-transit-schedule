package com.gtfs.model;

public class TripModel {
    private String tripId;
    private String routeId;

    public TripModel(String tripId, String routeId) {
        this.tripId = tripId;
        this.routeId = routeId;
    }

    public String getTripId() {
        return tripId;
    }

    public String getRouteId() {
        return routeId;
    }
}
