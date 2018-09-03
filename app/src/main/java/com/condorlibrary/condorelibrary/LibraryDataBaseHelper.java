package com.condorlibrary.condorelibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LibraryDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Library_db";
    private static final int DATABASE_VERSION = 1;
    private static final String class_name="LibraryDataBaseHelper";

    public LibraryDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(class_name, "database creation");
        sqLiteDatabase.execSQL(LibraryDatabase.Library.STUDENT_DATABASE_CREATE);
        sqLiteDatabase.execSQL(LibraryDatabase.Library.ITEM_DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(LibraryDatabase.Library.STUDENT_DATABASE_DROP);
        sqLiteDatabase.execSQL(LibraryDatabase.Library.ITEM_DATABASE_DROP);
        Log.i(class_name, "two tables dropped");
        //create two tablws
        onCreate(sqLiteDatabase);
    }
}