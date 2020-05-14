package com.example.creationcounter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView counterText;
    public static final String Count = "mCount";
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counterText = findViewById(R.id.counterText);
        if (savedInstanceState != null){
            count = savedInstanceState.getInt(Count);
            int newCount = count++;
            counterText.setText(String.valueOf(count));
            savedInstanceState.putInt(Count,newCount);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Count,count);
    }


}
