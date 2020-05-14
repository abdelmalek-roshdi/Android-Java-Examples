package com.example.viewmodelfragmentcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.containrt1, new FragmentA(), FragmentA.Tag).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.containrt2, new FragmentB(), FragmentB.Tag).commit();
        }
       /*
        TextViewModel textViewModel1 = new ViewModelProvider(this).get(TextViewModel.class);
        textViewModel1.getText().observe(this, (text)->{

        });
        */
    }
}
