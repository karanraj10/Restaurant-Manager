package com.project.restaurantmanager.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.project.restaurantmanager.Data.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestuarantSQLite extends SQLiteOpenHelper {
    public static final String DB = "restaurants";

    public RestuarantSQLite(@Nullable Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DB+"(rid integer primary key,name text,city text,address text,contact integer,image text,roption integer,starttime integer,endtime integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB);
        onCreate(db);
    }

    public boolean insertRestaurantData(int rid,String name,String city,String address,int contact, String image,int ropt,int starttime,int endtime)
    {SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("rid", rid);
        contentValues.put("name", name);
        contentValues.put("city",city );
        contentValues.put("address", address);
        contentValues.put("contact", contact);
        contentValues.put("image", image);
        contentValues.put("roption",ropt);
        contentValues.put("starttime",starttime);
        contentValues.put("endtime",endtime);

        db.insert(DB, null, contentValues);
        db.close();
        return true;

    }

    public List<Restaurant> getRestaurantData()
    {
        List<Restaurant> restaurantList = new ArrayList<>();

        SQLiteDatabase ob = this.getReadableDatabase();

        Cursor cursor = ob.rawQuery("SELECT * FROM restaurants",null);

        if(cursor.moveToFirst())
        {
            do
            {
                Restaurant restaurant = new Restaurant(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(0),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getInt(8));

                restaurantList.add(restaurant);
            }
            while (cursor.moveToNext());
        }

        return restaurantList;
    }
}
