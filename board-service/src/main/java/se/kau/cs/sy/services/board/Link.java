package se.kau.cs.sy.services.board;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@RelationshipProperties
public class Link {
    @Id
    @GeneratedValue
    private Long id;

    @Property("transportType")
    private String transportType;

    @TargetNode
    private Station target;

    public Link() {}
    public Link(String transportType, Station target) {
        this.transportType = transportType;
        this.target = target;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTransportType() { return transportType; }
    public void setTransportType(String transportType) { this.transportType = transportType; }
    public Station getTarget() { return target; }
    public void setTarget(Station target) { this.target = target; }
} 