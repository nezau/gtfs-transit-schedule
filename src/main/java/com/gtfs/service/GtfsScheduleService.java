package com.gtfs.service;

import com.gtfs.model.RouteModel;
import com.gtfs.model.StopModel;
import com.gtfs.model.StopTimeModel;
import com.gtfs.model.TripModel;
import com.gtfs.util.GtfsCsvParser;
import com.gtfs.util.TimesUtil;

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

//        System.out.println("Schedule for stop: " + stop.getStopName());

        List<StopTimeModel> stopTimesForStop = stopTimes.stream().filter(st -> st.getStopId().equals(stopId)).toList();

        if (stopTimesForStop.isEmpty()) {
            System.err.println("No stop times found for stop ID: " + stopId);
        } else {
            System.out.println("Found stop times for stop ID: " + stopId);
            for (StopTimeModel stopTime : stopTimesForStop) {
                System.out.println("Stop Time: " + stopTime.getArrivalTime());
            }
        }

        List<StopTimeModel> absoluteTimes = TimesUtil.getAbsoluteTimes(stopTimes);
        List<Long> relativeTimes = TimesUtil.getRelativeTimes(absoluteTimes);
        for (StopTimeModel at : absoluteTimes) {
            System.out.println("Stop Times until two hours after current time:" + at.getArrivalTime());
        }
        for (Long rt : relativeTimes) {
            System.out.println("Stop Times until two hours after current time:" + rt);
        }



    }
}
