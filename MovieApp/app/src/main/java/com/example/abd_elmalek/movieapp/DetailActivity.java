package com.example.abd_elmalek.movieapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements Serializable{
   int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FragmentDetails details =new FragmentDetails();
        Intent sentIntent =getIntent();
        Bundle sentbundle =sentIntent.getExtras();
        details.setArguments(sentbundle);

        getSupportFragmentManager().beginTransaction().add(R.id.activity_detail,details,"").commit();


    }
}
