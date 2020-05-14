package com.example.abd_elmalek.movieapp;

/**
 * Created by Abd-Elmalek on 11/29/2016.
 */

public class MyVideos {
    String videotitle,videoimage,videokey;

    public MyVideos(String videotitle, String videoimage,String videokey) {
        this.videotitle = videotitle;
        this.videoimage = videoimage;
        this.videokey=videokey;
    }

    public String getVideokey() {
        return videokey;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public String getVideoimage() {
        return videoimage;
    }
}
