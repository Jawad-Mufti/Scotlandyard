package se.kau.cs.sy.services.board;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryBoardService {
    
    private Map<Long, Station> stations = new ConcurrentHashMap<>();
    private Map<Long, Map<Long, List<String>>> adjacencyList = new ConcurrentHashMap<>();
    private long nextStationId = 0;
    
    public void clearData() {
        stations.clear();
        adjacencyList.clear();
        nextStationId = 0;
    }
    
    public void addStation(String name) {
        Station station = new Station();
        station.setId(nextStationId);
        station.setName(name);
        station.setLinks(new ArrayList<>());
        stations.put(nextStationId, station);
        adjacencyList.put(nextStationId, new HashMap<>());
        nextStationId++;
    }
    
    public void addLink(Long fromId, Long toId, String transportType) {
        if (stations.containsKey(fromId) && stations.containsKey(toId)) {
            // Add to adjacency list
            adjacencyList.get(fromId).computeIfAbsent(toId, k -> new ArrayList<>()).add(transportType);
            
            // Add to Station object
            Station fromStation = stations.get(fromId);
            Link link = new Link();
            link.setTransportType(transportType);
            link.setTarget(stations.get(toId));
            fromStation.getLinks().add(link);
        }
    }
    
    public List<Station> findReachableStations(Long startId, String transportType) {
        List<Station> reachable = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();
        
        queue.offer(startId);
        visited.add(startId);
        
        while (!queue.isEmpty()) {
            Long currentId = queue.poll();
            Map<Long, List<String>> neighbors = adjacencyList.get(currentId);
            
            if (neighbors != null) {
                for (Map.Entry<Long, List<String>> entry : neighbors.entrySet()) {
                    Long neighborId = entry.getKey();
                    List<String> transportTypes = entry.getValue();
                    
                    if (!visited.contains(neighborId) && transportTypes.contains(transportType)) {
                        reachable.add(stations.get(neighborId));
                        visited.add(neighborId);
                        queue.offer(neighborId);
                    }
                }
            }
        }
        
        return reachable;
    }
    
    public List<Station> findShortestPath(Long fromId, Long toId) {
        if (!stations.containsKey(fromId) || !stations.containsKey(toId)) {
            return new ArrayList<>();
        }
        
        Map<Long, Long> previous = new HashMap<>();
        Map<Long, Integer> distance = new HashMap<>();
        PriorityQueue<Long> queue = new PriorityQueue<>((a, b) -> 
            Integer.compare(distance.getOrDefault(a, Integer.MAX_VALUE), 
                          distance.getOrDefault(b, Integer.MAX_VALUE)));
        
        // Initialize
        for (Long stationId : stations.keySet()) {
            distance.put(stationId, Integer.MAX_VALUE);
        }
        distance.put(fromId, 0);
        queue.offer(fromId);
        
        while (!queue.isEmpty()) {
            Long currentId = queue.poll();
            
            if (currentId.equals(toId)) {
                break;
            }
            
            Map<Long, List<String>> neighbors = adjacencyList.get(currentId);
            if (neighbors != null) {
                for (Long neighborId : neighbors.keySet()) {
                    int newDistance = distance.get(currentId) + 1;
                    if (newDistance < distance.getOrDefault(neighborId, Integer.MAX_VALUE)) {
                        distance.put(neighborId, newDistance);
                        previous.put(neighborId, currentId);
                        queue.offer(neighborId);
                    }
                }
            }
        }
        
        // Reconstruct path
        List<Station> path = new ArrayList<>();
        Long currentId = toId;
        while (currentId != null) {
            path.add(0, stations.get(currentId));
            currentId = previous.get(currentId);
        }
        
        return path;
    }
    
    public List<Station> getAllStations() {
        return new ArrayList<>(stations.values());
    }
    
    public long getStationCount() {
        return stations.size();
    }
    
    public void generateLargeBoard(int numberOfStations) {
        clearData();
        
        String[] stationNames = {
            "Baker Street", "Oxford Circus", "Piccadilly Circus", "Leicester Square", "Covent Garden",
            "Trafalgar Square", "Charing Cross", "Embankment", "Waterloo", "London Bridge",
            "Bank", "Liverpool Street", "Moorgate", "Old Street", "Angel",
            "King's Cross", "Euston", "Warren Street", "Goodge Street", "Tottenham Court Road",
            "Holborn", "Russell Square", "King's Cross St Pancras", "Farringdon", "Barbican",
            "Moorgate", "Liverpool Street", "Aldgate", "Tower Hill", "Monument"
        };
        
        String[] transportTypes = {"TAXI", "BUS", "UNDERGROUND"};
        Random random = new Random(42); // Same seed for reproducible results
        
        // Create stations
        for (int i = 0; i < numberOfStations; i++) {
            addStation("Station_" + i + "_" + stationNames[i % stationNames.length]);
        }
        
        // Create links
        for (int i = 0; i < numberOfStations; i++) {
            int numConnections = 2 + random.nextInt(4); // 2-5 connections per station
            
            for (int j = 0; j < numConnections; j++) {
                int targetIndex = random.nextInt(numberOfStations);
                if (targetIndex != i) {
                    String transportType = transportTypes[random.nextInt(transportTypes.length)];
                    addLink((long) i, (long) targetIndex, transportType);
                }
            }
        }
        
        System.out.println("In-memory large board generated with " + numberOfStations + " stations");
    }
} 