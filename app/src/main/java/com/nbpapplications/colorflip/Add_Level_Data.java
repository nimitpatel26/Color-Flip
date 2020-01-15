package com.nbpapplications.colorflip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

/**
 * Created by nimitpatel on 7/29/17.
 */

public class Add_Level_Data {
    private static SQLiteDatabase database;
    private static Level_Database_Helper dbHelper;
    private  String[] allColumns =
            {Level_Database_Helper.COLUMN_LEVEL,
            Level_Database_Helper.COLUMN_LEVEL_INSTRUCTION,
            Level_Database_Helper.COLUMN_LEVEL_DESIGN,
            Level_Database_Helper.COLUMN_LEVEL_CLEARED};

    public Add_Level_Data(Context context){

        dbHelper = new Level_Database_Helper(context);
    }

    private static void openWrite() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    private void openRead() throws  SQLException{
        database = dbHelper.getReadableDatabase();
    }

    private static void close(){
        dbHelper.close();
    }


    public String getLevelInfo(int level){
        Cursor cursor = getCursor(level);
        String s = cursor.getString(1);
        close();
        return s;
    }

    public String getLevelDesign(int level){
        Cursor cursor = getCursor(level);
        String s = cursor.getString(2);
        close();
        return s;
    }

    public int getLevelCleared(int level){
        Cursor cursor = getCursor(level);
        int b = cursor.getInt(3);
        close();
        return b;
    }

    public void setLevelCleared(int level){
        openWrite();
        String sqlStatement = "UPDATE " + Level_Database_Helper.TITLE + " SET "
                + Level_Database_Helper.COLUMN_LEVEL_CLEARED + " = " + Integer.toString(1)
                + " WHERE " + Level_Database_Helper.COLUMN_LEVEL + " = " + Integer.toString(level);

        database.execSQL(sqlStatement);
        close();
    }

    private Cursor getCursor(int level){
        openRead();

        Cursor cursor = database.query(
                Level_Database_Helper.TITLE,
                allColumns,
                Level_Database_Helper.COLUMN_LEVEL + "=" + Integer.toString(level),
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        return cursor;
    }


}


