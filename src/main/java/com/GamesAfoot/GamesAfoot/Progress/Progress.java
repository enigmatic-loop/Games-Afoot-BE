package com.GamesAfoot.GamesAfoot.Progress;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer huntId;

    private Integer currentLocationIndex;

    private ArrayList<Integer> visitedLocations;

    private String nextHint;

    private Progress() {}

    public Progress(Integer id, Integer userId, Integer huntId, Integer currentLocationIndex, ArrayList<Integer> visitedLocations, String nextHint) {
        this.id = id;
        this.userId = userId;
        this.huntId = huntId;
        this.currentLocationIndex = currentLocationIndex;
        this.visitedLocations = visitedLocations;
        this.nextHint = nextHint;
    }

    @Override
    public String toString() {
        return String.format(
                """
                id: %d,
                userId: %d,
                huntId: %d,
                currentLocationIndex: %d,
                visitedLocations: %s,
                nextHint: %s
                """,
                id, userId, huntId, currentLocationIndex, visitedLocations, nextHint
        );
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getUserId() {
        return this.userId;
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
