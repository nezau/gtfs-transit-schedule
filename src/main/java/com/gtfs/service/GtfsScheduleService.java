package com.gtfs.service;

import com.gtfs.model.RouteModel;
import com.gtfs.model.StopModel;
import com.gtfs.model.StopTimeModel;
import com.gtfs.model.TripModel;
import com.gtfs.util.GtfsCsvParser;
import com.gtfs.util.TimesUtil;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class GtfsScheduleService {

    public void getScheduleService(String stopId, int numberOfBuses, String time) {
        List<RouteModel> routes = GtfsCsvParser.readRoutesCSV();
        List<StopModel> stops = GtfsCsvParser.readStopsCSV();
        List<StopTimeModel> stopTimes = GtfsCsvParser.readStopTimesCSV();
        List<TripModel> trips = GtfsCsvParser.readTripsCSV();

        // Filter stopTimes for the specified stop and time
        List<StopTimeModel> stopTimesFromStop = TimesUtil.getAbsoluteTimes(getStopTimesFromStop(stopId, stopTimes, time));

        // Get trips and routes filtered by stop times
        List<TripModel> tripsFromStop = getTripsFromStopTimes(stopTimesFromStop, trips);
        List<RouteModel> routesFromTrips = getRoutesFromTrips(tripsFromStop, routes);

        // Print schedules
        printSchedule(routesFromTrips, stopTimesFromStop, tripsFromStop);
    }

    private StopModel findStopById(String stopId, List<StopModel> stops) {
        return stops.stream()
                .filter(s -> s.getStopId().equals(stopId))
                .findFirst()
                .orElse(null);
    }

    private List<StopTimeModel> getStopTimesFromStop(String stopId, List<StopTimeModel> stopTimes, String time) {
        // Convert the given time to LocalTime for easier comparison
        return stopTimes.stream()
                .filter(st -> st.getStopId().equals(stopId))
                .collect(Collectors.toList());
    }

    private List<TripModel> getTripsFromStopTimes(List<StopTimeModel> stopTimes, List<TripModel> trips) {
        Set<String> tripIds = stopTimes.stream()
                .map(StopTimeModel::getTripId)
                .collect(Collectors.toSet());

        return trips.stream()
                .filter(trip -> tripIds.contains(trip.getTripId()))
                .collect(Collectors.toList());
    }

    private List<RouteModel> getRoutesFromTrips(List<TripModel> trips, List<RouteModel> routes) {
        Set<String> routeIds = trips.stream()
                .map(TripModel::getRouteId)
                .collect(Collectors.toSet());

        return routes.stream()
                .filter(route -> routeIds.contains(route.getRouteId()))
                .collect(Collectors.toList());
    }

    private void printSchedule(List<RouteModel> routes, List<StopTimeModel> stopTimes, List<TripModel> trips) {
        routes.forEach(route -> {
            System.out.print(route.getRouteName() + " : ");
            stopTimes.stream()
                    .filter(st -> trips.stream()
                            .anyMatch(trip -> trip.getTripId().equals(st.getTripId()) && trip.getRouteId().equals(route.getRouteId())))
                    .sorted((st1, st2) -> {
                        // Sort by arrival time
                        LocalTime time1 = TimesUtil.parseArrivalTime(st1.getArrivalTime());
                        LocalTime time2 = TimesUtil.parseArrivalTime(st2.getArrivalTime());
                        return time1.compareTo(time2); // Ascending order
                    })
                    .forEach(st -> {
                        LocalTime stopTime = TimesUtil.parseArrivalTime(st.getArrivalTime());
                        if (stopTime != null) {
                            System.out.print(stopTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) + "  ");
                        }
                    });
            System.out.println();
        });
    }
}
