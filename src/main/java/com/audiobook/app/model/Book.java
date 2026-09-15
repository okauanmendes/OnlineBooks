package com.audiobook.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String audioFileKey;
    private String textFileKey;
    private Integer durationSeconds;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean premium = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudioFileKey() {
        return audioFileKey;
    }

    public void setAudioFileKey(String audioFileKey) {
        this.audioFileKey = audioFileKey;
    }

    public String getTextFileKey() {
        return textFileKey;
    }

    public void setTextFileKey(String textFileKey) {
        this.textFileKey = textFileKey;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
