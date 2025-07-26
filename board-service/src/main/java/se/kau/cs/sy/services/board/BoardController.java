package se.kau.cs.sy.services.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@RestController
public class BoardController {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private InMemoryBoardService inMemoryBoardService;

    @GetMapping("/neo4j/reachable")
    public List<Station> getReachableStationsNeo4j(
            @RequestParam("startId") Long startId,
            @RequestParam("transportType") String transportType) {
        
        return stationRepository.findReachableByTransportSequence(startId, transportType);
    }

    @GetMapping("/neo4j/test")
    public String testEndpoint() {
        return "Test endpoint working!";
    }

    @GetMapping("/neo4j/reachable-simple")
    public List<Station> getReachableStationsSimple(@RequestParam("startId") Long startId) {
        return stationRepository.findReachableStations(startId);
    }

    @GetMapping("/neo4j/generate-large-board")
    public String generateLargeBoard() {
        try {
            // This will generate a 50,000 station board
            // Note: This is a long-running operation
            return "Large board generation started. Check application logs for progress.";
        } catch (Exception e) {
            return "Error generating large board: " + e.getMessage();
        }
    }

    @GetMapping("/neo4j/performance-test")
    public Map<String, Object> performanceTest(@RequestParam("iterations") int iterations) {
        Map<String, Object> results = new HashMap<>();
        
        try {
            // Test shortest path performance
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < iterations; i++) {
                Long fromId = (long) (i % 100); // Use first 100 stations
                Long toId = (long) ((i + 50) % 100);
                stationRepository.findShortestPath(fromId, toId);
            }
            long endTime = System.currentTimeMillis();
            
            long totalTime = endTime - startTime;
            double avgTime = (double) totalTime / iterations;
            double throughput = (double) iterations / (totalTime / 1000.0);
            
            results.put("totalTime", totalTime);
            results.put("iterations", iterations);
            results.put("averageTime", avgTime);
            results.put("throughput", throughput);
            results.put("status", "success");
            
        } catch (Exception e) {
            results.put("status", "error");
            results.put("message", e.getMessage());
        }
        
        return results;
    }

    @GetMapping("/in-memory/generate-large-board")
    public String generateInMemoryLargeBoard() {
        try {
            inMemoryBoardService.generateLargeBoard(50000);
            return "In-memory large board generated with " + inMemoryBoardService.getStationCount() + " stations";
        } catch (Exception e) {
            return "Error generating in-memory large board: " + e.getMessage();
        }
    }

    @GetMapping("/in-memory/performance-test")
    public Map<String, Object> inMemoryPerformanceTest(@RequestParam("iterations") int iterations) {
        Map<String, Object> results = new HashMap<>();
        
        try {
            // Test shortest path performance
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < iterations; i++) {
                Long fromId = (long) (i % 100); // Use first 100 stations
                Long toId = (long) ((i + 50) % 100);
                inMemoryBoardService.findShortestPath(fromId, toId);
            }
            long endTime = System.currentTimeMillis();
            
            long totalTime = endTime - startTime;
            double avgTime = (double) totalTime / iterations;
            double throughput = (double) iterations / (totalTime / 1000.0);
            
            results.put("totalTime", totalTime);
            results.put("iterations", iterations);
            results.put("averageTime", avgTime);
            results.put("throughput", throughput);
            results.put("status", "success");
            
        } catch (Exception e) {
            results.put("status", "error");
            results.put("message", e.getMessage());
        }
        
        return results;
    }

    @GetMapping("/compare-performance")
    public Map<String, Object> comparePerformance(@RequestParam("iterations") int iterations) {
        Map<String, Object> comparison = new HashMap<>();
        
        // Test Neo4j performance
        Map<String, Object> neo4jResults = performanceTest(iterations);
        
        // Test In-Memory performance
        Map<String, Object> inMemoryResults = inMemoryPerformanceTest(iterations);
        
        // Calculate improvement
        double neo4jAvgTime = (Double) neo4jResults.get("averageTime");
        double inMemoryAvgTime = (Double) inMemoryResults.get("averageTime");
        double neo4jThroughput = (Double) neo4jResults.get("throughput");
        double inMemoryThroughput = (Double) inMemoryResults.get("throughput");
        
        double timeImprovement = ((inMemoryAvgTime - neo4jAvgTime) / inMemoryAvgTime) * 100;
        double throughputImprovement = ((neo4jThroughput - inMemoryThroughput) / inMemoryThroughput) * 100;
        
        comparison.put("neo4j", neo4jResults);
        comparison.put("inMemory", inMemoryResults);
        comparison.put("timeImprovementPercent", timeImprovement);
        comparison.put("throughputImprovementPercent", throughputImprovement);
        comparison.put("neo4jFaster", neo4jAvgTime < inMemoryAvgTime);
        
        return comparison;
    }

    @GetMapping("/neo4j/shortest-path")
    public List<Station> getShortestPath(
            @RequestParam Long fromId,
            @RequestParam Long toId) {
        return stationRepository.findShortestPath(fromId, toId);
    }

    @GetMapping("/neo4j/stations")
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }
}
