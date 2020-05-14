package com.example.recyclerviewdemo;

public class Cake {
    private String title, description;
    int imagResourceId;

    public Cake() {
    }

    public Cake(String title, String description, int imagResourceId) {
        this.title = title;
        this.description = description;
        this.imagResourceId = imagResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagResourceId() {
        return imagResourceId;
    }

    public void setImagResourceId(int imagResourceId) {
        this.imagResourceId = imagResourceId;
    }
}
