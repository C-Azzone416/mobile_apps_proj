package com.example.final_project.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.example.final_project.data.dbModels.ToDoLists;

import java.util.ArrayList;


//manages List Table from DB
public class ListTableHelper {

    public DbHelper mHelper;

    public ListTableHelper(Context context){
        mHelper = new DbHelper(context);

    }

    public void createList(String listName){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.ListTable.COL_LIST_TITLE, listName);
        db.insertWithOnConflict(DbContract.ListTable.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

    }

    public  ArrayList<ToDoLists> getLists(){

        ArrayList<ToDoLists>mArrayLists= new ArrayList<ToDoLists>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DbContract.ListTable.TABLE_NAME,
                new String[]{DbContract.ListTable.LIST_ID, DbContract.ListTable.COL_LIST_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DbContract.ListTable.COL_LIST_TITLE);
            String listName = cursor.getString(idx);

            idx = cursor.getColumnIndex((DbContract.ListTable.LIST_ID));
            Integer listId = cursor.getInt(idx);

            mArrayLists.add(new ToDoLists(listId, listName));
        }

        cursor.close();
        db.close();

        return mArrayLists;
    }

    public void updateListName(Integer id, String newName){
        //TODO:  UPDATE IF IMPLEMENTING UPDATE LIST NAME
    }

    public  void deleteList(Integer id){
        //TODO:  DELETE LIST
    }


}
