package com.condorlibrary.condorelibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public  class LibraryDatabase {

    public LibraryDatabase() {
    }

    public static class Library implements BaseColumns {
        //Table student
        public static final String STUDENT_TABLE_NAME = "student";
        public static final String STUDENT_ID = "_id";
        public static final String STUDENT_FORENAME = "forename";
        public static final String STUDENT_SURNAME = "surname";
        public static final String STUDENT_EMAIL = "email";
        public static final String STUDENT_PASSWORD = "password";

        //Item table
        public static final String ITEM_TABLE_NAME = "item";
        public static final String ITEM_ID = "_id";
        public static final String ITEM_NAME = "name";
        public static final String ITEM_DESC = "description";
        public static final String ITEM_COST = "cost";

        private SQLiteDatabase database;
        private LibraryDataBaseHelper dbHelper;

        public Library(Context context) {
            dbHelper = new LibraryDataBaseHelper(context);
            database = dbHelper.getWritableDatabase();
        }

        public static final String STUDENT_DATABASE_CREATE = "create table if not exists " + STUDENT_TABLE_NAME + "(" + STUDENT_ID + " integer primary key autoincrement," + STUDENT_FORENAME + " text not null, " + STUDENT_SURNAME + " text not null," + STUDENT_EMAIL + " text not null, " + STUDENT_PASSWORD + " text not null)";
        public static final String STUDENT_DATABASE_DROP = "drop table if exists" + STUDENT_TABLE_NAME;
        public static final String ITEM_DATABASE_CREATE = "create table if not exists " + ITEM_TABLE_NAME + "(" + ITEM_ID + " integer primary key autoincrement," + ITEM_NAME + " text not null, " + ITEM_DESC + " text not null," + ITEM_COST + " integer not null)";
        public static final String ITEM_DATABASE_DROP = "drop table if exists" + ITEM_TABLE_NAME;


        public long InsertStudentDetails(String fName, String sName, String email, String password) {

            ContentValues values = new ContentValues();
            values.put(STUDENT_FORENAME, fName);
            values.put(STUDENT_SURNAME, sName);
            values.put(STUDENT_EMAIL, email);
            values.put(STUDENT_PASSWORD, password);
            return database.insert(STUDENT_TABLE_NAME, null, values);
        }

        public void UpdateStudentDetails(){

        }
        public long InsertItemDetails(String itemName, String description, int cost) {

            ContentValues values = new ContentValues();
            values.put(ITEM_NAME, itemName);
            values.put(ITEM_DESC, description);
            values.put(ITEM_COST, cost);
            return database.insert(ITEM_TABLE_NAME, null, values);
        }

        public boolean CheckStudentExists(String email,String password){

            String[] columns={STUDENT_FORENAME};
            String selection=STUDENT_EMAIL+"=? and "+STUDENT_PASSWORD + "=?";
            String[] selectionArgs= {email,password};
            Cursor cursor=database.query(STUDENT_TABLE_NAME,columns, selection, selectionArgs,null ,null ,null );
            int cursorCount=cursor.getCount();
            cursor.close();
            database.close();
            return cursorCount>0;
        }

        public int UpdateBookItem(String bookName,int cost){

            ContentValues values=new ContentValues();
            //values.put(ITEM_NAME, bookName);
            values.put(ITEM_COST,cost );
            String where=ITEM_NAME+" =?";
            String whereArgs[]={bookName};
            return database.update(ITEM_TABLE_NAME,values ,where ,whereArgs );
        }
        public int UpdateAccount(String forename,String surname,String email){

            ContentValues values=new ContentValues();
            values.put(STUDENT_FORENAME, forename);
            values.put(STUDENT_SURNAME,surname );
            String where = STUDENT_EMAIL+"= ?";
            String whereArgs[] = {email};
            return database.update(STUDENT_TABLE_NAME, values, where, whereArgs);
        }

        public Cursor getAllItemsList(){

            String[] columns={ITEM_NAME,ITEM_COST};

            Cursor cursor= database.query(ITEM_TABLE_NAME, columns, null, null, null, null, null);
            int count=cursor.getCount();
            if(count>0){
                Log.d("LibraryDatabase","items are there");
            }
            return cursor;
        }

        public int DeleteItem(String itemName){

            String where = ITEM_NAME+"=?";
            String[] whereArgs ={itemName};
            return database.delete(ITEM_TABLE_NAME, where, whereArgs);
        }
    }
}