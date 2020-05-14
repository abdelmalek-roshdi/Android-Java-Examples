package com.example.abd_elmalek.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Abd-Elmalek on 11/25/2016.
 */

public class FavoriteMovieDataSource {
    private static FavoriteMovieDataSource instance;
    private SQLiteDatabase database;
    private FavoriatesDatabase favoriatesDatabase;
    String id,title,overview,rating,postpath,releasedate,backpost;
    private boolean fav;

    public static FavoriteMovieDataSource getInstance(Context context)    {
        if (instance == null) {
            instance = new FavoriteMovieDataSource();
            instance.favoriatesDatabase = new FavoriatesDatabase(context);
            instance.open();
        }
        return instance;
    }
    public void open(){database=favoriatesDatabase.getWritableDatabase();}

    private ContentValues getContentvaluesFromMovies(MyMovies myMovies){
        ContentValues values = new ContentValues();
        values.put(FavoriatesDatabase.title,myMovies.getTitle());
        values.put(FavoriatesDatabase.id,myMovies.getId());
        values.put(FavoriatesDatabase.rating,myMovies.getReating());
        values.put(FavoriatesDatabase.overview,myMovies.getOverview());
        values.put(FavoriatesDatabase.postpath,myMovies.getPostpath());
        values.put(FavoriatesDatabase.releasdate,myMovies.getReleasedate());
        values.put(FavoriatesDatabase.backpost,myMovies.getBackpost());
      return values;
    }

    private MyMovies getMymoviesfromcursor (Cursor cursor){
        id =cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.id));
        title =cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.title));
        postpath =cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.postpath));
        overview =cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.overview));
        releasedate =cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.releasdate));
        rating =cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.rating));
        backpost=cursor.getString(cursor.getColumnIndex(FavoriatesDatabase.backpost));
        return new MyMovies(overview,releasedate,rating,postpath,title,id,backpost);
    }


    public void insertfavMovielist (MyMovies favMovies){
        ContentValues values =getContentvaluesFromMovies(favMovies);
        database.insert(FavoriatesDatabase.TABLENAME,null,values);

    }
    public boolean CheckIsFavforitebyPostpath(ArrayList<MyMovies> myMovies,int position){
    try {
        Cursor mycursor = database.query(FavoriatesDatabase.TABLENAME, null,FavoriatesDatabase.postpath + "=?",new String[] {myMovies.get(position).getPostpath()},null,null,null);
        if (mycursor.getCount() != 0){
            fav= true;

        }else{
            fav= false;
        }
    }catch (Exception e){}

    return fav;

    }

       public void markAsFav(MyMovies movies) {

        ContentValues contentValues = getContentvaluesFromMovies(movies);
        database.update(FavoriatesDatabase.TABLENAME, contentValues
                , FavoriatesDatabase.id + " =? ", new String[]{String.valueOf(movies.getId())});
    }

    public ArrayList<MyMovies> getFav() {

        ArrayList<MyMovies> FAV = new ArrayList<MyMovies>();

        Cursor cursor = database.query(FavoriatesDatabase.TABLENAME,
                null, null , null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyMovies myMovies = getMymoviesfromcursor(cursor);
            FAV.add(myMovies);
            cursor.moveToNext();
        }

        return FAV;
    }

    public void deleteMovieswithTitle(String title){
        database.delete(FavoriatesDatabase.TABLENAME , FavoriatesDatabase.title + "=?"
                , new String[]{String.valueOf(title)});

    }

}
