package com.example.abd_elmalek.movieapp;

/**
 * Created by Abd-Elmalek on 11/29/2016.
 */

public class MyReviews {
    String author,content;

    public MyReviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
