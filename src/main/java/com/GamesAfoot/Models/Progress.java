package com.GamesAfoot.Models;

import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "huntId")
    private Integer huntId;

    @Column(name = "targetLocationIndex")
    private Integer targetLocationIndex;


    @Column(name = "foundLocations")
    private ArrayList<Object> foundLocations;

    @Column(name = "nextHint")
    private String nextHint;

    private Progress() {}

    public Progress(Integer id, Integer userId, Integer huntId, Integer targetLocationIndex, ArrayList<Object> foundLocations, String nextHint) {
        this.id = id;
        this.userId = userId;
        this.huntId = huntId;
        this.targetLocationIndex = targetLocationIndex;
        this.foundLocations = foundLocations;
        this.nextHint = nextHint;
    }

    @Override
    public String toString() {
        return String.format(
                """
                id: %d,
                userId: %d,
                huntId: %d,
                targetLocationIndex: %d,
                foundLocations: %s,
                nextHint: %s
                """,
                id, userId, huntId, targetLocationIndex, foundLocations, nextHint
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

    public Integer getTargetLocationIndex() {
        return this.targetLocationIndex;
    }

    public ArrayList<Object> getFoundLocations() {
        return this.foundLocations;
    }

    public String getNextHint() {
        return this.nextHint;
    }
}
