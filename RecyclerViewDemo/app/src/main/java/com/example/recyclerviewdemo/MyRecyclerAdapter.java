package com.example.recyclerviewdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private Activity context;
    private List<Cake> cakeList;
    public static CakeViewModel model;
    MyRecyclerAdapter(Activity context, List<Cake> cakeList){
        this.context = context;
        this.cakeList = cakeList;
        model = new ViewModelProvider((ViewModelStoreOwner) context).get(CakeViewModel.class);
        model.setList(cakeList);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(cakeList.get(position).getTitle());
        holder.description.setText(cakeList.get(position).getDescription());
        holder.img.setImageResource(cakeList.get(position).imagResourceId);
        holder.cardView.setOnClickListener((view)->{
            if(context.getResources().getBoolean(R.bool.twoPane)){
                model.setCake(cakeList.get(position));
            }else {
                model.setCake(cakeList.get(position));
                context.startActivity(new Intent(context,DetailsActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return cakeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
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
