package se.kau.cs.sy.services.board;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.List;

@Node("Station")
public class Station {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "LINKS_TO")
    private List<Link> links;

    public Station() {}
    public Station(String name) {
        this.name = name;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Link> getLinks() { return links; }
    public void setLinks(List<Link> links) { this.links = links; }
} 