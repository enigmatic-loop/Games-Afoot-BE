package com.GamesAfoot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "location_clues", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "clue")
    private List<String> clues;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hunt_id", nullable = false)
    @JsonBackReference
    private Hunt hunt;

    // Default constructor
    public Location() {}

    // Parameterized constructor
    public Location(String name, String latitude, String longitude, String description, List<String> clues) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.clues = clues;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getClues() {
        return clues;
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public Hunt getHunt() {
        return hunt;
    }

    public void setHunt(Hunt hunt) {
        this.hunt = hunt;
    }

    @Override
    public String toString() {
        return String.format(
                "Location{id=%d, name='%s', latitude='%s', longitude='%s', description='%s', clues=%s}",
                id, name, latitude, longitude, description, clues
        );
    }
}
