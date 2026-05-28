package com.example.starvault.model;

public class Celebrity {
    private int id;
    private String fullName;
    private String photoUrl;
    private float score;
    private static int sequence = 0;

    public Celebrity(String fullName, String photoUrl, float score) {
        this.id = ++sequence;
        this.fullName = fullName;
        this.photoUrl = photoUrl;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public float getScore() {
        return score;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setScore(float score) {
        this.score = score;
    }
}