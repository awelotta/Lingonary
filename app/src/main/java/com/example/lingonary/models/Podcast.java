package com.example.lingonary.models;

public class Podcast {
    private String title;
    private String description;

    public Podcast(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
