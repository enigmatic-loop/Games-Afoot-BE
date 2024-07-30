package com.GamesAfoot.model;

import jakarta.persistence.*;
import java.util.ArrayList;

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
    private ArrayList<String> clues;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hunt_id", nullable = false)
    private Hunt hunt;

    public Location() {}

    public Location(String name, String latitude, String longitude, String description, ArrayList<String> clues) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.clues = clues;
    }

    @Override
    public String toString() {
        return String.format(
                """
                id: %d,
                name: %s,
                latitude: %s,
                longitude: %s,
                description: %s,
                clues: %s
                """,
                id, name, latitude, longitude, description, clues.toString()
        );
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

    public ArrayList<String> getClues() {
        return clues;
    }

    public void setClues(ArrayList<String> clues) {
        this.clues = clues;
    }

    public Hunt getHunt() {
        return hunt;
    }

    public void setHunt(Hunt hunt) {
        this.hunt = hunt;
    }
}
