package com.GamesAfoot.GamesAfoot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "location_clues", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "clue")
    private List<String> clues;

    @ManyToOne
    @JoinColumn(name = "hunt_id")
    private Hunt hunt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getClues() { return clues; }
    public void setClues(List<String> clues) { this.clues = clues; }

    public Hunt getHunt() { return hunt; }
    public void setHunt(Hunt hunt) { this.hunt = hunt; }
}
