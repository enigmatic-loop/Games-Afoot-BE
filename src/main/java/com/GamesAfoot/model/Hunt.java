package com.GamesAfoot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hunt")
public class Hunt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_latitude")
    private String startLatitude;

    @Column(name = "start_longitude")
    private String startLongitude;

    @Column(name = "distance")
    private String distance;

    @Column(name = "num_sites")
    private String numSites;

    @Column(name = "game_type")
    private String gameType;

    @OneToMany(mappedBy = "hunt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations;

    // Default constructor
    public Hunt() {}

    // Parameterized constructor
    public Hunt(String startLatitude, String startLongitude, String distance, String numSites, String gameType) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.distance = distance;
        this.numSites = numSites;
        this.gameType = gameType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getNumSites() {
        return numSites;
    }

    public void setNumSites(String numSites) {
        this.numSites = numSites;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return String.format(
                """
                id: %d,
                startLatitude: %s,
                startLongitude: %s,
                distance: %s,
                numSites: %s,
                gameType: %s
                """,
                id, startLatitude, startLongitude, distance, numSites, gameType
        );
    }
}
