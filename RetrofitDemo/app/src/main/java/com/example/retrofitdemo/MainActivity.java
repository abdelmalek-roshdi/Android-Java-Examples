package com.example.retrofitdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public  static final String BaseURL = "https://api.myjson.com";
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    List<UserModel> modelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL).addConverterFactory(GsonConverterFactory.create()).build();
         apiInterface = retrofit.create(ApiInterface.class);
         Call<List<UserModel>> call = apiInterface.geAllUsers();
         call.enqueue(new Callback<List<UserModel>>() {
             @Override
             public void onResponse(@NonNull Call<List<UserModel>> call,@NonNull Response<List<UserModel>> response) {
                 modelList = response.body();
                 UserRecyclerViewAdapter myRecyclerAdapter = new UserRecyclerViewAdapter(MainActivity.this, modelList);
                 recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                 recyclerView.setAdapter(myRecyclerAdapter);
                 ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                         new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                             @Override
                             public boolean onMove(@NonNull RecyclerView recyclerView,@NonNull RecyclerView.ViewHolder viewHolder,@NonNull RecyclerView.ViewHolder
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

             @Override
             public void onFailure(@NonNull Call<List<UserModel>> call,@NonNull Throwable t) {

             }
         });


    }

    private void init() {
        recyclerView = findViewById(R.id.myRecyclerView);
    }


}
