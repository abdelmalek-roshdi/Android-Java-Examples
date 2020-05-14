package com.example.abd_elmalek.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abd-Elmalek on 11/29/2016.
 */

public class MyReviewListAdapter extends BaseAdapter {
    ArrayList<MyReviews> myReviewses;
    LayoutInflater inflater;
    Context context;

    public MyReviewListAdapter(ArrayList<MyReviews> myReviewses, Context context) {
        this.myReviewses = myReviewses;
        this.context = context;
        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return myReviewses.size();
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

      if (convertView==null){
          convertView =inflater.inflate(R.layout.review_item,parent,false);
      }
        TextView textView = (TextView)convertView.findViewById(R.id.review_text);
        textView.setText("\n"+"\n"+"Reviews : - "+"\n"+"\n"+"Author : "+ myReviewses.get(position).getAuthor()+"\n"+myReviewses.get(position).getContent());


        return convertView;
    }



}
