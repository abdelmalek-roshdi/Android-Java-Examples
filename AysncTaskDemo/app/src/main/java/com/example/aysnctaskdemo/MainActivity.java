package com.example.aysnctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    Button downloadBtn, nextBtn;
    String imageUrlStr ="https://images.ladbible.com/thumbnail?type=jpeg&url=http://beta.ems.ladbiblegroup.com/s3/content/6f7259f45da4c2ce070d6ee96b1bd61d.png&quality=70&width=720";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleAction();
    }
    void init(){
        image = findViewById(R.id.image);
        downloadBtn = findViewById(R.id.downloadBtn);
        nextBtn = findViewById(R.id.nextBtn);
    }
    void handleAction(){
        downloadBtn.setOnClickListener((view)->{

            new ImageDownloader().execute(imageUrlStr);

        });
        nextBtn.setOnClickListener((view)->{
            startActivity(new Intent(MainActivity.this, ActivityTwo.class));
        });
    }


   public class ImageDownloader extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromURL(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Toast.makeText(MainActivity.this,"image downloaded successfully",Toast.LENGTH_SHORT).show();
            image.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public  Bitmap getBitmapFromURL(String string) {
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
