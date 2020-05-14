package com.example.abd_elmalek.movieapp;

import java.io.Serializable;

/**
 * Created by Abd-Elmalek on 10/21/2016.
 */

public class MyMovies implements Serializable{

    private String overview;
    private String releasedate;
    private String reating;
    private String postpath;
    private String title;
    private String id;
    private String backpost;

    public MyMovies(String overview, String releasedate, String reating, String postpath, String title,String id,String backpost) {
        this.overview = overview;
        this.releasedate = releasedate;
        this.reating = reating;
        this.postpath = postpath;
        this.title = title;
        this.id=id;
        this.backpost=backpost;
    }

    public String getBackpost() {
        return backpost;
    }

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public String getReating() {
        return reating;
    }

    public String getPostpath() {
        return postpath;
    }

    public String getTitle() {
        return title;
    }
}
