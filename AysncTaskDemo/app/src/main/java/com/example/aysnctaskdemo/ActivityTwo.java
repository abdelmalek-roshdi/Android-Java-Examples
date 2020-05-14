package com.example.aysnctaskdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;


public class ActivityTwo extends AppCompatActivity {
    List<UserModel> models;
    MyHandler handler;
    TextView nameTextView,idTextView,phoneTextView;
    ImageView profileimageView;
    Button nextBtn,prevBtn;
    ImageDownloader imageDownloader;
    static int currentPostion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        init();
        String url = "https://api.myjson.com/bins/cj2br";
        //new ContactsJsonDownloader().execute(url);
        handler = new MyHandler();

        contactsThread.start();
        handleActions();
    }
        Thread contactsThread = new Thread(()->{
            String url = "https://api.myjson.com/bins/cj2br";
           models = getUserModels(url);
           Message m = Message.obtain();
           m.obj = models;
           handler.sendMessage(m);
        });

    void init(){
        nameTextView = findViewById(R.id.nameTextView);
        idTextView = findViewById(R.id.idTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        profileimageView = findViewById(R.id.profileImageView);
        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.prevBtn);
        imageDownloader = new ImageDownloader();
    }
    void handleActions(){
        nextBtn.setOnClickListener((view)->{
            if (currentPostion >= 0 && currentPostion < 3){
                currentPostion++;
                nameTextView.setText(models.get(currentPostion).getName());
                idTextView.setText(models.get(currentPostion).getId());
                phoneTextView.setText(models.get(currentPostion).getMobile());
                new ImageDownloader().execute(models.get(currentPostion).getProfile_imge());
            }
        });
        prevBtn.setOnClickListener((view)->{
            if (currentPostion > 0 && currentPostion <= 3){
                currentPostion-- ;
                nameTextView.setText(models.get(currentPostion).getName());
                idTextView.setText(models.get(currentPostion).getId());
                phoneTextView.setText(models.get(currentPostion).getMobile());
                new ImageDownloader().execute(models.get(currentPostion).getProfile_imge());
            }
        });
    }
    private class MyHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<UserModel> mList = (List<UserModel>) msg.obj;

            nameTextView.setText(mList.get(0).getName());
            idTextView.setText(mList.get(0).getId());
            phoneTextView.setText(mList.get(0).getMobile());
            imageDownloader.execute(mList.get(0).getProfile_imge());
        }
    }

    private class ContactsJsonDownloader extends AsyncTask<String,Void, List<UserModel>>{

        @Override
        protected List<UserModel> doInBackground(String... strings) {
            return getUserModels(strings[0]);
        }

        @Override
        protected void onPostExecute(List<UserModel> userModels) {
            super.onPostExecute(userModels);
        }
    }

    public class ImageDownloader extends AsyncTask<String,Void, Bitmap>{

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
            Toast.makeText(ActivityTwo.this,"image downloaded successfully",Toast.LENGTH_SHORT).show();
            profileimageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private List<UserModel> getUserModels(String string) {
        ArrayList<UserModel> contactList = new ArrayList<>();

        String jsonStr = makeServiceCall(string);
        JSONArray contacts = null;
        try {
            contacts = new JSONArray(jsonStr);

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("name");
                String mobile = c.getString("mobile");
                String profile_imge = c.getString("profile_imge");
                UserModel contact = new UserModel(id,name,mobile,profile_imge);
                contactList.add(contact);

            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return contactList;
    }


    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public  Bitmap getBitmapFromURL(String string) {
        Bitmap result = null;
        try {
            URL url = new URL(string);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
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
