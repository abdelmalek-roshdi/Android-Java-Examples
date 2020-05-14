package com.example.recyclerviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.recyclerviewdemo.MainActivity.getBitmapFromURL;

public class UserRecyclerViewAdapter  extends RecyclerView.Adapter<UserRecyclerViewAdapter.MyViewHolder> {

    private Activity context;
    private List<UserModel> userList;
    public static UserViewModel model;
    ImageView img;
    UserRecyclerViewAdapter(Activity context, List<UserModel> userList){
        this.context = context;
        this.userList = userList;
        model = new ViewModelProvider((ViewModelStoreOwner) context).get(UserViewModel.class);
        model.setList(userList);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(userList.get(position).getName());
        holder.description.setText(userList.get(position).getMobile());
        //holder.img.setImageResource(userList.get(position).getProfile_imge());
        holder.cardView.setOnClickListener((view)->{
            if(context.getResources().getBoolean(R.bool.twoPane)){
                model.setCake(userList.get(position));
            }else {
                model.setCake(userList.get(position));
                context.startActivity(new Intent(context,DetailsActivity.class));

            }
        });
    }
    public  void removeUserItem(int pos){
        userList.remove(pos);
        model.setList(userList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        CardView cardView;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cakeImg);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.myCard);

        }
    }


}
