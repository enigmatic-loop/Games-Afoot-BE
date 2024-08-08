package com.GamesAfoot.models;

import jakarta.persistence.*;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "huntId", nullable = false, updatable = false)
    private Integer huntId;

    @Column(name = "targetLocationIndex", nullable = false)
    private Integer targetLocationIndex;

    @Column(name = "gameComplete", nullable = false)
    private Boolean gameComplete;

    private Progress() {}

    public Progress(Integer id, Integer userId, Integer huntId, Integer targetLocationIndex, Boolean gameComplete) {
        this.id = id;
        this.userId = userId;
        this.huntId = huntId;
        this.targetLocationIndex = targetLocationIndex;
        this.gameComplete = gameComplete;
    }

    @Override
    public String toString() {
        return String.format(
                """
                id: %d,
                userId: %d,
                huntId: %d,
                targetLocationIndex: %d,
                gameComplete: %b
                """,
                id, userId, huntId, targetLocationIndex, gameComplete
        );
    }

    // Getters
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

    public Boolean getGameComplete() {
        return this.gameComplete;
    }


    // Setters
    public void setTargetLocationIndex(Integer index) {
        this.targetLocationIndex = index;
    }

    public void setGameComplete(Boolean complete) {
        this.gameComplete = complete;
    }

}
