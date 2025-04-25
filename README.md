# GTFS Transit Schedule App

This Java application parses GTFS files and displays upcoming bus schedules for a specified stop.

## How to run
Currently, the application is run using a compiled .jar file.

### Requirements
- Java 17 or higher
- Maven

### Build
```bash
mvn clean package
```

### Run

```bash
java -jar target/gtfs-schedule-viewer.jar "stop_id" "number_of_next_buses" relative/absolute
```

## Current Features
- Parses GTFS CSV files from the resources folder

- Displays arrival times for a specified stop

- Groups arrivals by route ID

- Sorts arrival times per route in chronological order

## Planned improvements
- Implement support for relative time filtering

### Optimization
- Optimize memory usage by applying filters during CSV reading
- Improve stream pipelining
