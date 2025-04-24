package com.gtfs.util;

import com.gtfs.model.RouteModel;
import com.gtfs.model.StopModel;
import com.gtfs.model.StopTimeModel;
import com.gtfs.model.TripModel;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GtfsCsvParser {

    public static List<String[]> readCSV(String filePath) {
        List<String[]> allData = new ArrayList<>();
        try(InputStream inputStream = GtfsCsvParser.class.getClassLoader().getResourceAsStream(filePath)) {
            if(inputStream == null) {
                System.err.println("File not found: " + filePath);
                return allData;
            }
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            allData = csvReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    // Convert CSV data to RouteModel
    public static List<RouteModel> readRoutesCSV() {
        List<RouteModel> routes = new ArrayList<>();
        List<String[]> allData = readCSV("routes.txt");

        for (String[] row : allData) {
            String routeId = row[0];
            String routeName = row[2];
            routes.add(new RouteModel(routeId, routeName));
        }
        return routes;
    }

    // Convert CSV data to StopModel
    public static List<StopModel> readStopsCSV() {
        List<StopModel> stops = new ArrayList<>();
        List<String[]> allData = readCSV("stops.txt");

        for (String[] row : allData) {
            String stopId = row[0];
            String stopName = row[2];
            stops.add(new StopModel(stopId, stopName));
        }
        return stops;
    }

    // Convert CSV data to StopTimeModel
    public static List<StopTimeModel> readStopTimesCSV() {
        List<StopTimeModel> stopTimes = new ArrayList<>();
        List<String[]> allData = readCSV("stop_times.txt");

        for (String[] row : allData) {
            String tripId = row[0];
            String stopId = row[3];
            String arrivalTime = row[1];
            String departureTime = row[2];
            stopTimes.add(new StopTimeModel(tripId, stopId, arrivalTime, departureTime));
        }
        return stopTimes;
    }

    // Convert CSV data to TripModel
    public static List<TripModel> readTripsCSV() {
        List<TripModel> trips = new ArrayList<>();
        List<String[]> allData = readCSV("trips.txt");

        for (String[] row : allData) {
            String tripId = row[2];
            String routeId = row[0];
            trips.add(new TripModel(tripId, routeId));
        }
        return trips;
    }

    public static void printCSV(List<String[]> data) {
        for (String[] row : data) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }
}
