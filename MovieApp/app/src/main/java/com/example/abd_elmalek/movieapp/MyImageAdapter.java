package com.example.abd_elmalek.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
/**
 * Created by Abd-Elmalek on 10/21/2016.
 */
public class MyImageAdapter extends BaseAdapter {
LayoutInflater inflater ;
    ImageView imageView ;
    ArrayList<MyMovies> allmovies =new ArrayList<>();
    public MyImageAdapter(ArrayList<MyMovies> allmovies, Context mContext) {
        this.allmovies = allmovies;
        this.mContext = mContext;
        inflater=(LayoutInflater.from(mContext));
    }
    private Context mContext;
    public int getCount() {
        return allmovies.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
           convertView=inflater.inflate(R.layout.grid_item , parent,false);
        }
        Viewholder viewholder =new Viewholder();
        viewholder.imageView= (ImageView) convertView.findViewById(R.id.image_view);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185//"+allmovies.get(position).getPostpath()).error(R.drawable.placeholder2).into((viewholder.imageView));
            return convertView;
    }
    static class Viewholder{
        ImageView imageView;

    }
}
