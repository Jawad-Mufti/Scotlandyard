package se.kau.cs.sy.services.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class LargeBoardGenerator {

    @Autowired
    private StationRepository stationRepository;

    private static final String[] TRANSPORT_TYPES = {"TAXI", "BUS", "UNDERGROUND"};
    private static final String[] STATION_NAMES = {
        "Baker Street", "Oxford Circus", "Piccadilly Circus", "Leicester Square", "Covent Garden",
        "Trafalgar Square", "Charing Cross", "Embankment", "Waterloo", "London Bridge",
        "Bank", "Liverpool Street", "Moorgate", "Old Street", "Angel",
        "King's Cross", "Euston", "Warren Street", "Goodge Street", "Tottenham Court Road",
        "Holborn", "Russell Square", "King's Cross St Pancras", "Farringdon", "Barbican",
        "Moorgate", "Liverpool Street", "Aldgate", "Tower Hill", "Monument"
    };

    public void generateLargeBoard(int numberOfStations) {
        System.out.println("Generating large board with " + numberOfStations + " stations...");
        
        // Create stations
        List<Station> stations = new ArrayList<>();
        for (int i = 0; i < numberOfStations; i++) {
            Station station = new Station();
            station.setName("Station_" + i + "_" + STATION_NAMES[i % STATION_NAMES.length]);
            station.setLinks(new ArrayList<>());
            stations.add(station);
        }

        // Create links between stations (each station connects to 2-5 other stations)
        Random random = new Random(42); // Fixed seed for reproducible results
        for (int i = 0; i < numberOfStations; i++) {
            Station currentStation = stations.get(i);
            int numConnections = 2 + random.nextInt(4); // 2-5 connections per station
            
            for (int j = 0; j < numConnections; j++) {
                int targetIndex = random.nextInt(numberOfStations);
                if (targetIndex != i) {
                    Link link = new Link();
                    link.setTransportType(TRANSPORT_TYPES[random.nextInt(TRANSPORT_TYPES.length)]);
                    link.setTarget(stations.get(targetIndex));
                    currentStation.getLinks().add(link);
                }
            }
        }

        // Save all stations in batches
        int batchSize = 1000;
        for (int i = 0; i < stations.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, stations.size());
            List<Station> batch = stations.subList(i, endIndex);
            stationRepository.saveAll(batch);
            System.out.println("Saved batch " + (i / batchSize + 1) + " of " + ((stations.size() - 1) / batchSize + 1));
        }

        System.out.println("Large board generation completed! Created " + numberOfStations + " stations.");
        
        // Print some statistics
        long totalStations = stationRepository.count();
        System.out.println("Total stations in database: " + totalStations);
    }

    public void clearDatabase() {
        System.out.println("Clearing database...");
        stationRepository.deleteAll();
        System.out.println("Database cleared.");
    }
} 