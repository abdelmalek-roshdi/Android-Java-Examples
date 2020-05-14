package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Cake> cakeList;
    RecyclerView recyclerView;
    DetailsFragment detailsFragment;
    List<UserModel> models;
    MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

       // MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(MainActivity.this,cakeList);
       // recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       // recyclerView.setAdapter(myRecyclerAdapter);

        if(getResources().getBoolean(R.bool.twoPane)){
            if(detailsFragment==null){
                detailsFragment = new DetailsFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.containerLayout, detailsFragment, DetailsFragment.Tag).commit();
            }else {
                getSupportFragmentManager().beginTransaction().add(R.id.containerLayout,getSupportFragmentManager().findFragmentByTag(DetailsFragment.Tag),DetailsFragment.Tag).commit();
            }
        }
    }
    Thread contactsThread = new Thread(()->{
        String url = "https://api.myjson.com/bins/cj2br";
        models = getUserModels(url);
        Message m = Message.obtain();
        m.obj = models;
        handler.sendMessage(m);
    });

    private void init() {
        recyclerView = findViewById(R.id.myRecyclerView);
        cakeList = Arrays.asList(new Cake("cake1","desc 1",R.drawable.cake1),
                new Cake("cake2","desc 2",R.drawable.cake2),
                new Cake("cake3","desc 3",R.drawable.cake3),
                new Cake("cake4","desc 4",R.drawable.cake4),
                new Cake("cake5","desc 5",R.drawable.cake5),
                new Cake("cake6","desc 6",R.drawable.cake6),
                new Cake("cake7","desc 7",R.drawable.cake7),
                new Cake("cake8","desc 8",R.drawable.cake8),
                new Cake("cake9","desc 9",R.drawable.cake9),
                new Cake("cake10","desc 10",R.drawable.cake10),
                new Cake("cake1","desc 1",R.drawable.cake1),
                new Cake("cake2","desc 2",R.drawable.cake2),
                new Cake("cake3","desc 3",R.drawable.cake3),
                new Cake("cake4","desc 4",R.drawable.cake4),
                new Cake("cake5","desc 5",R.drawable.cake5),
                new Cake("cake6","desc 6",R.drawable.cake6),
                new Cake("cake7","desc 7",R.drawable.cake7),
                new Cake("cake8","desc 8",R.drawable.cake8),
                new Cake("cake9","desc 9",R.drawable.cake9),
                new Cake("cake10","desc 10",R.drawable.cake10));
        handler = new MyHandler();

        contactsThread.start();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<UserModel> mList = (List<UserModel>) msg.obj;
            UserRecyclerViewAdapter myRecyclerAdapter = new UserRecyclerViewAdapter(MainActivity.this, mList);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(myRecyclerAdapter);
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                    new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                                target) {
                            return false;
                        }
                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                            myRecyclerAdapter.removeUserItem(viewHolder.getAdapterPosition());
                            myRecyclerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        }
                    };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);

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

    public static Bitmap getBitmapFromURL(String string) {
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
