package com.example.startedservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button, boundServiceBtn;
    Intent downloadIntent;
    public static final String downloadKey = "download";
    MyBoundService myBoundService;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) iBinder;
                 myBoundService = myBinder.getService();
                Toast.makeText(MainActivity.this ,myBoundService.getCurrentTime(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        init();
        handleActions();
    }

    private void handleActions() {
        button.setOnClickListener((view)->{
            downloadIntent = new Intent();
            downloadIntent.putExtra(downloadKey, editText.getText().toString());
            //MainActivity.this.startService(downloadIntent);

            PendingIntentService.enqueueWork(MainActivity.this,PendingIntentService.class,104,downloadIntent);
            finish();
        });
        boundServiceBtn.setOnClickListener((view)->{
            bindService(new Intent(MainActivity.this, MyBoundService.class),serviceConnection, Context.BIND_AUTO_CREATE);
        });
    }

    private void init() {
        editText = findViewById(R.id.urlEditText);
        button = findViewById(R.id.downloadBtn);
        boundServiceBtn = findViewById(R.id.boundServiceBtn);
    }

}
