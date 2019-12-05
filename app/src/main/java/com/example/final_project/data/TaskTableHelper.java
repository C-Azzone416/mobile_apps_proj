package com.example.final_project.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.data.dbModels.Tasks;

import java.util.ArrayList;


//manages TaskTable from DB
public class TaskTableHelper {
    public DbHelper mHelper;

    public TaskTableHelper(Context context){
        mHelper = new DbHelper(context);
    }

    public void createTask(String taskName, Integer listId){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.TaskTable.COL_TODO_ITEMS, taskName);
        values.put(DbContract.TaskTable.COL_TODO_ADDRESS, "");
        values.put(DbContract.TaskTable.COL_TODO_NOTES, "");
        values.put(DbContract.TaskTable.COL_IS_CHECKED, 0);
        values.put(DbContract.TaskTable.COL_IS_PRIORITY, 0);
        values.put(DbContract.TaskTable.FKEY_LIST_ID, listId);
        db.insertWithOnConflict(DbContract.TaskTable.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public ArrayList<Tasks> getTasks(Integer listId){
        ArrayList<Tasks> mTasksArray = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //better way
        String selectQuery = "SELECT * FROM " + DbContract.TaskTable.TABLE_NAME + " WHERE " +
                DbContract.TaskTable.FKEY_LIST_ID + " = " + listId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        //commented out code below works but is naive method

//        Cursor cursor = db.query(DbContract.TaskTable.TABLE_NAME,
//                new String[]{DbContract.TaskTable.TASK_ID,
//                        DbContract.TaskTable.FKEY_LIST_ID,
//                        DbContract.TaskTable.COL_TODO_ITEMS,
//                        DbContract.TaskTable.COL_IS_CHECKED,
//                        DbContract.TaskTable.COL_IS_PRIORITY,
//                        DbContract.TaskTable.COL_TODO_ADDRESS,
//                        DbContract.TaskTable.COL_TODO_NOTES,},
//                null, null, null, null, null);
//        while(cursor.moveToNext()){
//            int idx = cursor.getColumnIndex(DbContract.TaskTable.FKEY_LIST_ID);
//            Integer getListId = cursor.getInt(idx);
//            if(getListId != listId){
//                cursor.moveToNext();
//            }
//            else{

            while (cursor.moveToNext()) {

            Integer idx = cursor.getColumnIndex(DbContract.TaskTable.TASK_ID);
            Integer taskId = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_IS_PRIORITY);
            Integer isPriority = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_TODO_ITEMS);
            String taskName = cursor.getString(idx);

            mTasksArray.add(new Tasks(taskId, taskName, isPriority));
            }

        cursor.close();
        db.close();

        return mTasksArray;
    }

    //TODO:  NEED TO HAVE A WAY TO UPDATE DB INFO




}
