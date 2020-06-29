package com.example.fatchimage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DB_NAME = "groceryy";
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private static final String TAG = "DatabaseHandler";

    public static final String CART_TABLE = "cart11";
    public static final String COLUMN_ID = "product_id";
    public static final String COLUMN_QTY = "qty";
    public static final String COLUMN_IMAGE = "product_image";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_NAME = "product_name";


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "DatabaseHandler: database created !");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL("CREATE TABLE " + CART_TABLE + " (" + COLUMN_ID + " INTEGER, " + COLUMN_QTY + " DOUBLE, "
                + COLUMN_IMAGE + " TEXT, " + COLUMN_PRICE + " DOUBLE, " + COLUMN_DESC + " TEXT, " + COLUMN_NAME + " TEXT);");
        Log.d(TAG, "onCreate: table created !");

    }

    public ArrayList<String> readData() {
        ArrayList<String> list = new ArrayList<>();
        db = getReadableDatabase();
        String query = "SELECT " + COLUMN_DESC + " FROM " + CART_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
            Log.d(TAG, "readData: " + name);
            list.add(name);
            cursor.moveToNext();
        }
        return list;
    }

    public ArrayList<HashMap<String, String>> cartall() {
        ArrayList<HashMap<String, String>> map = new ArrayList<>();
        db = getReadableDatabase();
        String query = "SELECT * FROM " + CART_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            hashMap.put(COLUMN_QTY, cursor.getString(cursor.getColumnIndex(COLUMN_QTY)));
            hashMap.put(COLUMN_IMAGE, cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            hashMap.put(COLUMN_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            hashMap.put(COLUMN_DESC, cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
            hashMap.put(COLUMN_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            map.add(hashMap);
            cursor.moveToNext();
        }

        return map;


    }

    public boolean isInCart(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return true;

        return false;
    }

    public boolean setdatarow(HashMap<String, String> map) {
        db = getWritableDatabase();
        int id = 0;
        String sql = "SELECT " + COLUMN_ID + " FROM " + CART_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            if (map.get("product_id").equals(name)) {
                id = 1;
            }
            cursor.moveToNext();
        }

        if (id == 1) {
            db.execSQL("update " + CART_TABLE + " set " + COLUMN_QTY + " = '" + map.get("qty") + "' where " + COLUMN_ID + "=" + map.get("product_id"));
        } else {

            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, map.get("product_id"));
            values.put(COLUMN_QTY, map.get("qty"));
            values.put(COLUMN_IMAGE, map.get("product_image"));
            values.put(COLUMN_PRICE, map.get("price"));
            values.put(COLUMN_DESC, map.get("description"));
            values.put(COLUMN_NAME, map.get("product_name"));

            db.insert(CART_TABLE, null, values);
            Log.d(TAG, "setdatarow: one row inserted !");

        }

        return true;

    }

    public boolean removeitemfromcart(String id) {
        db = getReadableDatabase();
        String sql = "DELETE FROM " + CART_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        db.execSQL(sql);
        return true;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
