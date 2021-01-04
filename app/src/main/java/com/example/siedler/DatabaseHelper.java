package com.example.siedler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "siedler";
    private static final String COL0 = "id";
    private static final String COL1 = "numbers";
    private static final String COL2 = "players";
    private static final String COL3 = "date";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " +TABLE_NAME+ " ( " +
                COL0+ " integer primary key autoincrement, " +
                COL1+ " text," +
                COL2+ " text," +
                COL3+ " text" +
                " ) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "drop table if exists " +TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public long add(String numbers, String players, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, numbers);
        contentValues.put(COL2, players);
        contentValues.put(COL3, date);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor readAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " +TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor read(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " +TABLE_NAME+ "where " +COL0+ " = '" +id+ "'";
        return db.rawQuery(query, null);
    }
}
