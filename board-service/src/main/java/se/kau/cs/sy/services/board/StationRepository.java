package se.kau.cs.sy.services.board;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface StationRepository extends Neo4jRepository<Station, Long> {
    // Find all stations reachable from a start station via allowed transport types (one hop)
    @Query("""
        MATCH (start:Station)-[l:LINKS_TO]->(target:Station)
        WHERE id(start) = $startId AND l.transportType IN $transportTypes
        RETURN DISTINCT target
    """)
    List<Station> findReachableStations(Long startId, List<String> transportTypes);

    // Find the shortest path between two stations (any transport type)
    @Query("""
        MATCH (from:Station), (to:Station),
        p = shortestPath((from)-[:LINKS_TO*..20]->(to))
        WHERE id(from) = $fromId AND id(to) = $toId
        UNWIND nodes(p) AS station
        RETURN DISTINCT station
    """)
    List<Station> findShortestPath(Long fromId, Long toId);

    @Query("""
        MATCH (start:Station)
        WHERE id(start) IN $startIds
        CALL apoc.path.expandConfig(start, {
          relationshipFilter: 'LINKS_TO>',
          labelFilter: 'Station',
          sequence: $transportSequence
        }) YIELD path
        WITH last(nodes(path)) AS target
        RETURN DISTINCT target
    """)
    List<Station> findReachableByTransportSequence(List<Long> startIds, List<String> transportSequence);
} 