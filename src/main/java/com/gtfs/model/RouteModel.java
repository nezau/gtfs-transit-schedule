package com.gtfs.model;

public class RouteModel {
    private String routeId;
    private String routeName;

    public RouteModel(String routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }
}
