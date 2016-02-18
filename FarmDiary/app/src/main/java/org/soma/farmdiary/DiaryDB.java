package org.soma.farmdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lminjae on 2016. 2. 19..
 */
public class DiaryDB extends SQLiteOpenHelper {
    final static String TBL_NAME = "diary";

    final static String TAG_DATE = "date";
    final static String TAG_TYPE = "type";
    final static String TAG_DATA = "data";

    public DiaryDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + TAG_DATE + " TEXT, " + TAG_TYPE + " TEXT, " + TAG_DATA + " TEXT);");

        Insert(db, "상추", "심었다");
        Insert(db, "상추", "썩었다");

        Insert(db, "해바라기", "심었다");
        Insert(db, "해바라기", "싹났다");
        Insert(db, "해바라기", "자라버렸다");
        Insert(db, "해바라기", "해보더라");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Insert(SQLiteDatabase db, String type, String data) {
        ContentValues values = new ContentValues();
        values.put(TAG_DATE, String.valueOf(new Date()));
        values.put(TAG_TYPE, type);
        values.put(TAG_DATA, data);
        db.insert(TBL_NAME, null, values);
    }

    public void Insert(String type, String data) {
        Insert(getWritableDatabase(), type, data);
    }

    public void Update(SQLiteDatabase db, String date, String type, String data) {
        ContentValues values = new ContentValues();
        values.put(TAG_DATE, String.valueOf(new Date()));
        values.put(TAG_TYPE, type);
        values.put(TAG_DATA, data);
        db.update(TBL_NAME, values, "date=?", new String[]{date});
    }

    public void Update(String date, String type, String data) {
        Update(getWritableDatabase(), date, type, data);
    }

    public void Delete(SQLiteDatabase db, String date) {
        db.delete(TBL_NAME, "date=?", new String[]{date});
    }

    public void Delete(String date) {
        Delete(getWritableDatabase(), date);
    }

    public void Select(SQLiteDatabase db, List<Map<String, String>> lst) {
        Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);

        while (c.moveToNext()) {
            Map<String, String> m = new HashMap();
            m.put(TAG_DATE, c.getString(c.getColumnIndex(TAG_DATE)));
            m.put(TAG_TYPE, c.getString(c.getColumnIndex(TAG_TYPE)));
            m.put(TAG_DATA, c.getString(c.getColumnIndex(TAG_DATA)));
            lst.add(m);
        }
    }

    public void Select(List<Map<String, String>> lst) {
        Select(getReadableDatabase(), lst);
    }
}
