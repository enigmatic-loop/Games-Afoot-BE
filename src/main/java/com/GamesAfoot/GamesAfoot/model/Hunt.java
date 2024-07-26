package com.GamesAfoot.GamesAfoot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hunt")
public class Hunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_latitude", nullable = false)
    private String startLatitude;

    @Column(name = "start_longitude", nullable = false)
    private String startLongitude;

    @Column(nullable = false)
    private String distance;

    @Column(name = "num_sites", nullable = false)
    private String numSites;

    @Column(name = "game_type", nullable = false)
    private String gameType;

    @OneToMany(mappedBy = "hunt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStartLatitude() { return startLatitude; }
    public void setStartLatitude(String startLatitude) { this.startLatitude = startLatitude; }

    public String getStartLongitude() { return startLongitude; }
    public void setStartLongitude(String startLongitude) { this.startLongitude = startLongitude; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public String getNumSites() { return numSites; }
    public void setNumSites(String numSites) { this.numSites = numSites; }

    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public List<Location> getLocations() { return locations; }
    public void setLocations(List<Location> locations) { this.locations = locations; }
}
