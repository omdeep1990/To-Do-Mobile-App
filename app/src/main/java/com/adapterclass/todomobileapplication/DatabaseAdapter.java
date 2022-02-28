package com.adapterclass.todomobileapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.type.DateTime;

public class DatabaseAdapter {
    private final String DB_NAME = "Avengers";
    private final String TABLE_NAME = "Notes";
    private final int DB_VERSION = 1;

    //TODO: Initializing the column names
    private final String ROW_ID = "rowId";
    private final String TITLE = "title";
    private final String MESSAGE = "message";
    private final String DATETIME = "dateTime";


    //TODO: CREATE TABLE Notes (rowId Integer AUTOINCREAMENT PRIMARY KEY, title text, message text);

    private String sqlQuery = "CREATE TABLE "+TABLE_NAME+" ("+ROW_ID+" "+"INTEGER PRIMARY KEY AUTOINCREMENT"+", "+TITLE+" text"+", "+MESSAGE+" text"+", "+DATETIME+")";

    MyDbHelper myDbHelper;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseAdapter(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    public DatabaseAdapter openDatabase(){
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        return this;
    }

    public  void insertData(Context context, String title, String message, String dateTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(MESSAGE, message);
        contentValues.put(DATETIME, dateTime);
        long insertedRow = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (insertedRow > 0){
            showLongToast(context, insertedRow+" data is successfully inserted");
        }else {
            showLongToast(context, "Something went wrong");
        }
    }

    private void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public Cursor getAllData(){
        String[] colList = new String[]{ROW_ID, TITLE, MESSAGE, DATETIME};
        return sqLiteDatabase.query(TABLE_NAME, colList, null, null, null, null, null);
    }

    public void deleteSingleRecord(Context context, String rowId){
        //TODO: DELETE * FROM STUDENT Where RowDI = 101;
        int deletedItems = sqLiteDatabase.delete(TABLE_NAME, ROW_ID+" = "+rowId, null);
        if (deletedItems > 0 ){
            showLongToast(context, deletedItems+" Record deleted");
        }else {
            showLongToast(context, "Something went wrong.");
        }
    }

//    public void updateRecord(Context context, String title, String message, String rowId, String dateTime){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TITLE, title);
//        contentValues.put(MESSAGE, message);
//        contentValues.put(DATETIME, dateTime);
//        int updatedRow = sqLiteDatabase.update(TABLE_NAME, contentValues, ROW_ID+" = "+rowId, null);
//        if (updatedRow > 0){
//            showLongToast(context, updatedRow+" data is successfully updated");
//        }else {
//            showLongToast(context, "Something went wrong");
//        }
//    }






    public void updateRecord(Context context, String title, String message, String dateTime, String rowId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(MESSAGE, message);
        contentValues.put(DATETIME, dateTime);
        int updatedRow = sqLiteDatabase.update(TABLE_NAME, contentValues, ROW_ID+" = "+rowId, null);
        if (updatedRow > 0){
            Toast.makeText(context, updatedRow+" data is successfully updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, updatedRow+" Something  Went Wrong", Toast.LENGTH_SHORT).show();

        }
    }







    public void deleteAllRecords(Context context){
        int deletedItems = sqLiteDatabase.delete(TABLE_NAME, null, null);
        if (deletedItems > 0 ){
            showLongToast(context, deletedItems+" Record deleted");
        }else {
            showLongToast(context, "Something went wrong.");
        }
    }


    class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sqlQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
