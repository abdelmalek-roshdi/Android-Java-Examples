package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    DetailsFragment detailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (getResources().getBoolean(R.bool.twoPane)){
                finish();
        }else {
            if (detailsFragment == null){
                getSupportFragmentManager().beginTransaction().add(R.id.containerLayout,new DetailsFragment(),DetailsFragment.Tag).commit();
            }else {
                getSupportFragmentManager().beginTransaction().add(R.id.containerLayout, getSupportFragmentManager().findFragmentByTag(DetailsFragment.Tag),DetailsFragment.Tag).commit();
            }
        }
    }
}
