package com.GamesAfoot.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hunt")
public class Hunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startLatitude")
    private String startLatitude;

    @Column(name = "startLongitude")
    private String startLongitude;

    @Column(name = "distance")
    private String distance;

    @Column(name = "numSites")
    private String numSites;

    @Column(name = "gameType")
    private String gameType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hunt", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private List<Location> locations;

    // Default constructor
    public Hunt() {}

    // Parameterized constructor
    public Hunt(String startLatitude, String startLongitude, String distance, String numSites, String gameType, List<Location> locations) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.distance = distance;
        this.numSites = numSites;
        this.gameType = gameType;
        this.locations = locations;
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

    public void addLocation(Location location) {
        locations.add(location);
        location.setHunt(this);  // Ensure bidirectional relationship is maintained
    }

    @Override
    public String toString() {
        return "Hunt{" +
                "id=" + id +
                ", startLatitude='" + startLatitude + '\'' +
                ", startLongitude='" + startLongitude + '\'' +
                ", distance='" + distance + '\'' +
                ", numSites='" + numSites + '\'' +
                ", gameType='" + gameType + '\'' +
                ", locations=" + locations +
                '}';
    }
}
