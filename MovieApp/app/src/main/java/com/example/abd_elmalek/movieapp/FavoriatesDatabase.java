package com.example.abd_elmalek.movieapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Abd-Elmalek on 11/25/2016.
 */

public class FavoriatesDatabase extends SQLiteOpenHelper {

    public static final String title="title";
    public static final String id ="id";
    public static final String rating="rating";
    public static final String overview="overview";
    public static final String postpath="postpath";
    public static final String releasdate="date";
    public static final String is_fav = "is_fav";
    public static final String backpost = "backpost";

    public static final int DBversion = 8;
    public static final String dbName="favoritedatabase";
    public static final String TABLENAME ="favTable";

    public FavoriatesDatabase(Context context) {
        super(context, dbName, null, DBversion);
    }


    String Create_Fav_Table = " CREATE TABLE " + TABLENAME + " ( "
            + is_fav + " INTEGER,"
            + id + " integer primary key autoincrement,"
            + title + " TEXT NOT NULL,"
            + rating + " TEXT NOT NULL,"
            + backpost + " TEXT NOT NULL,"
            + overview + " TEXT,"
            + postpath + " TEXT NOT NULL,"
            + releasdate + " TEXT NOT NULL "+");";
    @Override
    public void onCreate(SQLiteDatabase db) {



        db.execSQL(Create_Fav_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

     db.execSQL(" drop table if exists " + TABLENAME);
        onCreate(db);

    }

}
