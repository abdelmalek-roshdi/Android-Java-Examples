package com.example.fragmentscomunication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Communicator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.containrt1, new FragmentA(), FragmentA.Tag).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.containrt2, new FragmentB(), FragmentB.Tag).commit();
        }
    }

    @Override
    public void respond(String msg) {
        Objects.requireNonNull((FragmentB) getSupportFragmentManager().findFragmentByTag(FragmentB.Tag)).handleActions();
    }
}
