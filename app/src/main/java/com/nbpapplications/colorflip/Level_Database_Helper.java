package com.nbpapplications.colorflip;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

/**
 * Created by nimitpatel on 7/31/17.
 */


public class Level_Database_Helper extends SQLiteOpenHelper {
    public static final String TITLE = "level_database";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_LEVEL_INSTRUCTION = "level_instruction";
    public static final String COLUMN_LEVEL_DESIGN = "level_design";
    public static final String COLUMN_LEVEL_CLEARED = "level_cleared";


    public static final String DATABASE_NAME = "comments.db";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE =
            "create table " + TITLE + " (" + COLUMN_LEVEL + " int, "
                    + COLUMN_LEVEL_INSTRUCTION + " varchar, " + COLUMN_LEVEL_DESIGN
                    + " varchar, " + COLUMN_LEVEL_CLEARED
                    + " int);";

    private Context m_context;

    public Level_Database_Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        m_context = context;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        String[] level_instruction = m_context.getResources().getStringArray(R.array.level_instruction);
        String[] level_design = m_context.getResources().getStringArray(R.array.level_design);

        for (int i = 1; i <= 25; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Level_Database_Helper.COLUMN_LEVEL, i);
            contentValues.put(Level_Database_Helper.COLUMN_LEVEL_INSTRUCTION, level_instruction[i - 1]);
            contentValues.put(Level_Database_Helper.COLUMN_LEVEL_DESIGN, level_design[i - 1]);

            if (i == 1) {
                contentValues.put(Level_Database_Helper.COLUMN_LEVEL_CLEARED, 1);
            }else{
                contentValues.put(Level_Database_Helper.COLUMN_LEVEL_CLEARED, 0);
            }

            db.insert(Level_Database_Helper.TITLE, null, contentValues);
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(Level_Database_Helper.class.getName(), "Upgrading database form " +
                "version " + oldVersion + " to " + newVersion + ", which will destroy" +
                "all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TITLE);

        onCreate(db);
    }

}