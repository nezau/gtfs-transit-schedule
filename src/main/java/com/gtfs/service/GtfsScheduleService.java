package com.gtfs.service;

import com.gtfs.model.RouteModel;
import com.gtfs.model.StopModel;
import com.gtfs.model.StopTimeModel;
import com.gtfs.model.TripModel;
import com.gtfs.util.GtfsCsvParser;

import java.util.List;

public class GtfsScheduleService {


    public void getScheduleService(String stopId, int numberOfBuses, String time) {
        List<RouteModel> routes = GtfsCsvParser.readRoutesCSV();
        List<StopModel> stops = GtfsCsvParser.readStopsCSV();
        List<StopTimeModel> stopTimes = GtfsCsvParser.readStopTimesCSV();
        List<TripModel> trips = GtfsCsvParser.readTripsCSV();

        StopModel stop = stops.stream()
                .filter(s -> s.getStopId().equals(stopId))
                .findFirst()
                .orElse(null);

        if (stop == null) {
            System.err.println("Stop with ID " + stopId + " not found.");
            return;
        }

        System.out.println("Schedule for stop: " + stop.getStopName());

    }
}
