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
        List<StopTimeModel> stopTimes = GtfsCsvParser.readStopTimesCSV();
        List<TripModel> trips = GtfsCsvParser.readTripsCSV();

        List<StopTimeModel> stopTimesFromStop = TimesUtil.getAbsoluteTimes(getStopTimesFromStop(stopId, stopTimes, time));

        List<TripModel> tripsFromStop = getTripsFromStopTimes(stopTimesFromStop, trips);
        List<RouteModel> routesFromTrips = getRoutesFromTrips(tripsFromStop, routes);

        printSchedule(routesFromTrips, stopTimesFromStop, tripsFromStop, numberOfBuses);
    }

    private StopModel findStopById(String stopId, List<StopModel> stops) {
        return stops.stream()
                .filter(s -> s.getStopId().equals(stopId))
                .findFirst()
                .orElse(null);
    }

    private List<StopTimeModel> getStopTimesFromStop(String stopId, List<StopTimeModel> stopTimes, String time) {
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

    private LocalTime parseArrivalTime(String arrivalTimeStr) {
        return TimesUtil.parseArrivalTime(arrivalTimeStr);
    }

    private void printSchedule(List<RouteModel> routes, List<StopTimeModel> stopTimes, List<TripModel> trips, int numberOfBuses) {
        routes.forEach(route -> {
            System.out.print(route.getRouteName() + " : ");

            List<LocalTime> times = stopTimes.stream()
                    .filter(st -> trips.stream()
                            .anyMatch(trip -> trip.getTripId().equals(st.getTripId())
                                    && trip.getRouteId().equals(route.getRouteId())))
                    .map(st -> parseArrivalTime(st.getArrivalTime()))
                    .filter(Objects::nonNull)
                    .sorted()
                    .limit(numberOfBuses)
                    .toList();

            times.forEach(time ->
                    System.out.print(time.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) + "  ")
            );
            System.out.println();
        });
    }
}
