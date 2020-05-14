package com.example.retrofitdemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.MyViewHolder> {

    private Activity context;
    private List<UserModel> userList;
    private Picasso mPicasso;
    UserRecyclerViewAdapter(Activity context, List<UserModel> userList){
        this.context = context;
        this.userList = userList;

        /*
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context,Integer.MAX_VALUE));
        mPicasso = builder.build();
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
         */
        mPicasso = new MyPicasso(context).getPicasso();


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(userList.get(position).getName());
        holder.description.setText(userList.get(position).getMobile());
        mPicasso.with(context).load(userList.get(position).getProfile_imge()).into(holder.img);
        holder.cardView.setOnClickListener((view)->{

        });
    }
    public  void removeUserItem(int pos){
        userList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        CardView cardView;
        ImageView img;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cakeImg);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.myCard);

        }
    }


}
