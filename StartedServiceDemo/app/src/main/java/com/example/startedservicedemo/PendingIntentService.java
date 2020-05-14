package com.example.startedservicedemo;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import static com.example.startedservicedemo.MainActivity.downloadKey;

public class PendingIntentService extends JobIntentService {
    String StorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    OutputStream outStream = null;
    Intent outIntent;
    MyBroadCaastReciever broadcastReceiver;

    public PendingIntentService() {

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        IntentFilter intentFilter = new IntentFilter(downloadKey);
        broadcastReceiver = new MyBroadCaastReciever();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);


        String url = intent.getStringExtra(downloadKey);
        Bitmap bitmap = getBitmapFromURL(url);
        //String extension = url.substring(url.lastIndexOf("."));
        String extension = ".jpg";
        String fileName = "myImage"+extension;

        File file = new File(StorageDirectory, "//" + fileName);
        if (file.exists()) {
            file.delete();
            file = new File(StorageDirectory, "//" + fileName);
            Log.e("file exist", "" + file + ",Bitmap= " + fileName);
        }




        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

            outStream.flush();
            outStream.close();
            outIntent = new Intent();
            outIntent.setAction(downloadKey);
            outIntent.putExtra("URI",String.valueOf(Uri.fromFile(file)));
            //sendBroadcast(outIntent);
            LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);

            //unregisterReceiver(broadcastReceiver);


        } catch (IOException e) {
            e.printStackTrace();
            unregisterReceiver(broadcastReceiver);
        }
    }





    private class ImageDownloader extends AsyncTask<String,Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap b = getBitmapFromURL(strings[0]);
            return b;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //Toast.makeText(MainActivity.this,"image downloaded successfully",Toast.LENGTH_SHORT).show();
            //img.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private   Bitmap getBitmapFromURL(String string) {
        Bitmap result = null;
        try {
            URL url = new URL(string);
            HttpsURLConnection httpURLConnection =(HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            result = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
