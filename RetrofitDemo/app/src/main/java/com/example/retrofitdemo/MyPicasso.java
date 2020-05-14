package com.example.retrofitdemo;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class MyPicasso {
    private MyPicasso ourInstance;
    private Picasso built;
    Context context;
    public  MyPicasso getInstance() {
        if (ourInstance ==null){
            ourInstance = new MyPicasso(context);
        }
        return ourInstance;
    }

    public MyPicasso(Context context) {
        this.context = context;
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context,Integer.MAX_VALUE));
        built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);

    }
    public Picasso getPicasso(){
        return built;
    }
}
