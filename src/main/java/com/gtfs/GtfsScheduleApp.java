package com.gtfs;

import com.gtfs.service.GtfsScheduleService;
import com.gtfs.util.GtfsCsvParser;

public class GtfsScheduleApp {
    public static void main(String[] args) {
        System.out.println("Welcome to GTFS Schedule App!");

        GtfsCsvParser.readCSV("routes.txt");

        if(args.length > 3) {
            System.out.println("Use format: busTrips stop_id number_of_next_buses relative/absolute");
            return;
        }

        String stopId = args[0];
        int numberOfBuses = Integer.parseInt(args[1]);
        String time = args[2].toLowerCase();

        if (!time.equals("relative") && !time.equals("absolute")) {
            System.out.println("Time must be 'relative' or 'absolute'");
            return;
        }

        GtfsScheduleService gtfsScheduleService = new GtfsScheduleService();
        gtfsScheduleService.getScheduleService(stopId, numberOfBuses, time);

        System.out.println();

    }
}