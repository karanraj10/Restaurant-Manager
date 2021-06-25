package com.project.restaurantmanager.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.project.restaurantmanager.Data.FoodItems;

import java.util.ArrayList;
import java.util.List;

public class FoodSQLite extends SQLiteOpenHelper {
    public static final String DB_NAME = "foods";

    public FoodSQLite(@Nullable Context context) {
        super(context,DB_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table foods " +
                        "(ino integer primary key,name text,price integer,image text,rid integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS foods");
        onCreate(db);
    }

    public boolean insertFoodData (int ino,String name,int price, String image,int rid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ino", ino);
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("image", image);
        contentValues.put("rid", rid);
        db.insert(DB_NAME, null, contentValues);
        db.close();
        return true;
    }

    public List<FoodItems> getFoodData(int rid)
    {
        List<FoodItems> foodList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM foods WHERE rid="+rid;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                FoodItems contact = new FoodItems(cursor.getString(1),cursor.getDouble(2),cursor.getString(3));
                foodList.add(contact);
            } while (cursor.moveToNext());
        }


        return foodList;
    }
}
