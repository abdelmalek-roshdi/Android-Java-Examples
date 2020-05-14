package com.example.abd_elmalek.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abd-Elmalek on 11/29/2016.
 */

public class VideosListAdapter extends BaseAdapter {
    ArrayList<MyVideos> myVideoses;
    LayoutInflater inflater;
    Context context;

    public VideosListAdapter(Context context, ArrayList<MyVideos> myVideoses) {
        this.context = context;
        this.myVideoses = myVideoses;
        if (inflater==null){inflater=(LayoutInflater.from(context));}
        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return myVideoses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder viewholder;
        if (convertView==null){
            convertView =  inflater.inflate(R.layout.video_item,parent,false);
        }
        viewholder=new Viewholder();
        viewholder.imageView =(ImageView)convertView.findViewById(R.id.Custom_Vimage);
        viewholder.textView =(TextView)convertView.findViewById(R.id.Custom_Vtext);
        Picasso.with(context).load("http://img.youtube.com/vi/"+myVideoses.get(position).getVideokey()+"/maxresdefault.jpg").error(R.drawable.placeholder2).into((viewholder.imageView));
        viewholder.textView.setText(myVideoses.get(position).getVideotitle());


        return convertView;

    }
    static class Viewholder{
        ImageView imageView;
        TextView textView;
    }
}
