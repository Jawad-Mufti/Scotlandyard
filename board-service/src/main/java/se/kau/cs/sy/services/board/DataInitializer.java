package se.kau.cs.sy.services.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LargeBoardGenerator largeBoardGenerator;

    @Override
    public void run(String... args) throws Exception {
        // Check if we should generate a large board for performance testing
        boolean generateLargeBoard = Arrays.asList(args).contains("--large-board");
        
        if (generateLargeBoard) {
            System.out.println("Generating large board for performance testing...");
            largeBoardGenerator.clearDatabase();
            largeBoardGenerator.generateLargeBoard(50000);
            System.out.println("Large board generation completed!");
        } else if (stationRepository.count() == 0) {
            System.out.println("Initializing test data for Neo4j...");
            initializeTestData();
            System.out.println("Test data initialized successfully!");
        } else {
            System.out.println("Database already contains data, skipping initialization.");
        }
    }

    private void initializeTestData() {
        // Create stations
        Station station1 = new Station();
        station1.setName("Baker Street");
        
        Station station2 = new Station();
        station2.setName("Oxford Circus");
        
        Station station3 = new Station();
        station3.setName("Piccadilly Circus");
        
        Station station4 = new Station();
        station4.setName("Leicester Square");
        
        Station station5 = new Station();
        station5.setName("Covent Garden");

        // Create links between stations
        Link link1to2 = new Link();
        link1to2.setTransportType("TAXI");
        link1to2.setTarget(station2);
        
        Link link1to3 = new Link();
        link1to3.setTransportType("BUS");
        link1to3.setTarget(station3);
        
        Link link2to3 = new Link();
        link2to3.setTransportType("UNDERGROUND");
        link2to3.setTarget(station3);
        
        Link link2to4 = new Link();
        link2to4.setTransportType("TAXI");
        link2to4.setTarget(station4);
        
        Link link3to4 = new Link();
        link3to4.setTransportType("BUS");
        link3to4.setTarget(station4);
        
        Link link3to5 = new Link();
        link3to5.setTransportType("UNDERGROUND");
        link3to5.setTarget(station5);
        
        Link link4to5 = new Link();
        link4to5.setTransportType("TAXI");
        link4to5.setTarget(station5);

        // Set links for each station
        station1.setLinks(Arrays.asList(link1to2, link1to3));
        station2.setLinks(Arrays.asList(link2to3, link2to4));
        station3.setLinks(Arrays.asList(link3to4, link3to5));
        station4.setLinks(Arrays.asList(link4to5));
        station5.setLinks(Arrays.asList());

        // Save all stations
        List<Station> savedStations = stationRepository.saveAll(Arrays.asList(
            station1, station2, station3, station4, station5
        ));

        System.out.println("Created " + savedStations.size() + " stations with transportation links");
        
        // Print station IDs for testing
        for (Station station : savedStations) {
            System.out.println("Station: " + station.getName() + " (ID: " + station.getId() + ")");
        }
    }
} 