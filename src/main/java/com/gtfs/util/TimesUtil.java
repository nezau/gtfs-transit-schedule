package com.gtfs.util;

import com.gtfs.model.StopTimeModel;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TimesUtil {

    public static LocalTime parseArrivalTime(String arrivalTime) {
        DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            return LocalTime.parse(arrivalTime, formattedTime);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid time format: " + arrivalTime);
            return null;
        }
    }

    public static List<StopTimeModel> getAbsoluteTimes(List<StopTimeModel> stopTimes) {
//        LocalTime currentTime = LocalTime.now();
        LocalTime currentTime = LocalTime.of(12,0);
        LocalTime limitTime = currentTime.plusHours(2);

        return stopTimes.stream()
            .filter(st -> {
                String arrivalTime = st.getArrivalTime();
                LocalTime stopTime = parseArrivalTime(arrivalTime);

                if (stopTime == null) {
                    return false;
                }

                return stopTime.isAfter(currentTime) && stopTime.isBefore(limitTime);
            })
            .toList();
    }

    public static List<Long> getRelativeTimes(List<StopTimeModel> stopTimes) {
        List<Long> relativeTimes = new ArrayList<>();
//        LocalTime currentTime = LocalTime.now();
        LocalTime currentTime = LocalTime.of(12,0);

        for (StopTimeModel st : stopTimes) {
            String arrivalTime = st.getArrivalTime();

            LocalTime stopTime = parseArrivalTime(arrivalTime);
            if (stopTime == null) {
                continue;
            }

            Duration duration = Duration.between(currentTime, stopTime);

            if(duration.isNegative()) {
                duration = duration.plusDays(1);
            }

            long differenceMinutes = duration.toMinutes();
            relativeTimes.add(differenceMinutes);
        }
        return relativeTimes;
    }
}
