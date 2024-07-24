package com.GamesAfoot.GamesAfoot.Progress;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer huntId;

    private Integer currentLocationIndex;

    private ArrayList<Integer> visitedLocations;

    private String nextHint;

    private Progress() {}

    public Progress(Integer id, Integer huntId, Integer currentLocationIndex, ArrayList<Integer> visitedLocations, String nextHint) {
        this.id = id;
        this.huntId = huntId;
        this.currentLocationIndex = currentLocationIndex;
        this.visitedLocations = visitedLocations;
        this.nextHint = nextHint;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getHuntId() {
        return this.huntId;
    }

    public Integer getCurrentLocationIndex() {
        return this.currentLocationIndex;
    }

    public ArrayList<Integer> getVisitedLocations() {
        return this.visitedLocations;
    }

    public String getNextHint() {
        return this.nextHint;
    }
}
