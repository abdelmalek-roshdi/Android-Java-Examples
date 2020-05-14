package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQliteHelper extends SQLiteOpenHelper {

    public static final String phone ="phone";
    public static final String message ="message";
    public static final int DBVersion = 1;
    public static final String dbName="phoneBookDB";
    public static final String TABLENAME ="contactData";

    String Create_Contact_Table = " CREATE TABLE " + TABLENAME + " ( "
            + phone + " TEXT primary key ,"
            + message + " TEXT NOT NULL "
            +");";

    private SQliteHelper(@Nullable Context context) {
        super(context, dbName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_Contact_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" drop table if exists " + TABLENAME);
        onCreate(sqLiteDatabase);
    }

    static class SQLAdapter {
        private static  SQLAdapter instance;
        private static SQliteHelper dbHelper;
        SQLiteDatabase database;
        Cursor cursor;

        public static SQLAdapter getInstance(Context context)    {
            if (instance == null) {
                instance = new SQLAdapter();
                instance.dbHelper = new SQliteHelper(context);
                instance.open();
            }
            return instance;
        }
        private void open(){database = dbHelper.getWritableDatabase();}

        private ContentValues getContentValuesFromContactData(ContactData contactData){
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQliteHelper.phone, contactData.getPhone());
            contentValues.put(SQliteHelper.message, contactData.getMsg());
            return contentValues;
        }
        public boolean insertIntoContacts(ContactData contactData){
            ContentValues values = getContentValuesFromContactData(contactData);
            long result = database.insert(TABLENAME,null, values);
            return result != -1;
        }
        public ContactData getContactDataByPhoneNumber(String mphone){
            cursor = database.query(TABLENAME,new String[]{SQliteHelper.phone, SQliteHelper.message},SQliteHelper.phone+" = ? ", new String[]{mphone},null,null,null);
            cursor.moveToFirst();
            return  getContactDataFromCursor(cursor);
        }

        private ContactData getContactDataFromCursor (Cursor cursor){
            String msg, mphone;
            msg = cursor.getString(cursor.getColumnIndex(SQliteHelper.message));
            mphone = cursor.getString(cursor.getColumnIndex(SQliteHelper.phone));
            return new ContactData(msg, mphone);
        }
        public void close(){
            database.close();
            cursor.close();
            dbHelper.close();
            instance = null;
        }



    }
}
