package com.example.startedservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

public class MyBroadCaastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         context.startActivity(new Intent(context ,ImageActivity.class).putExtra("URI",intent.getStringExtra("URI")).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


    }
}
